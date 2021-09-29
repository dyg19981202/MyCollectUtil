package cn.dfordog.basezego

import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import cn.dfordog.basezego.config.BuildConfig
import cn.dfordog.basezego.config.IZeGoVideoConfig
import cn.dfordog.basezego.config.IZeGoVideoConfig.isListener
import im.zego.zegoexpress.ZegoExpressEngine
import im.zego.zegoexpress.ZegoMediaPlayer
import im.zego.zegoexpress.callback.IZegoEventHandler
import im.zego.zegoexpress.constants.*
import im.zego.zegoexpress.entity.*
import org.json.JSONObject
import java.util.ArrayList

// TODO: 2021/9/29 猜想： 音频和视频应该单独写
abstract class BaseZeGoActivity<T: ViewDataBinding>(private val layoutID: Int): AppCompatActivity(){

    protected lateinit var binding: T
    protected val TAG = "ZegoActivity"

    private lateinit var engine: ZegoExpressEngine
    private lateinit var showMsg: TextView
    private lateinit var user: ZegoUser
    private val streamID = "Android-4561234851"
    private val roomID = "room1"
    private var msgType = 0
    private var userID = ""

    private fun initBinding(){
        binding = DataBindingUtil.setContentView(this,layoutID)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        engine = ZegoExpressEngine.createEngine(
            BuildConfig.ZE_GO_APP_ID, BuildConfig.ZE_GO_APP_SIGN, true, ZegoScenario.GENERAL,
            application, null)
        initBinding()
        initEngineAndUser()
        getVoicePermission()
        requestPermission()
        if(!isListener){
            listenerRoom()
        }

        listenClick()

    }

    override fun onResume() {
        super.onResume()
        engine = ZegoExpressEngine.createEngine(
            BuildConfig.ZE_GO_APP_ID, BuildConfig.ZE_GO_APP_SIGN, true, ZegoScenario.GENERAL,
            application, null)

        if(!isListener){
            listenerRoom()
        }

    }


    /**
     * 获取音频权限
     */
    private fun getVoicePermission(){
        val permissionNeeded = arrayOf(
            "android.permission.RECORD_AUDIO"
        )

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(
                    this,
                    "android.permission.RECORD_AUDIO"
                ) !== PackageManager.PERMISSION_GRANTED
            ) {
                requestPermissions(permissionNeeded, 101)
            }
        }
    }


    /**
     * 登录房间
     */
    protected fun loginRoom(){
        val user = ZegoUser("user1")
        engine.loginRoom(roomID,user)
    }


    /**
     * 推送流
     */
    protected fun pushStream(){
        engine.startPublishingStream(streamID)
    }

    /**
     * 拉流
     */
    protected fun playStream(){
        engine.startPlayingStream(streamID,null)
    }

    /**
     * 停止推流或拉流
     */
    protected fun setCloseStream(type: Int){
        when (type) {
            0 -> { //推流
                engine.stopPublishingStream()
            }
            1 -> {
                engine.stopPlayingStream(streamID)
            }
            else -> {
                engine.stopPublishingStream()
                engine.stopPlayingStream(streamID)
            }
        }
    }

    /**
     * 退出房间
     */
    private fun logoutRoom(){
        engine.logoutRoom(roomID)
    }


    /**
     * 发送广播消息
     */
    protected fun sendBroadcastMessage(msg: String){
        engine.sendBroadcastMessage(roomID,msg
        ) { errorCode, messageID ->
            Log.e(TAG,"广播消息: errCode: ${errorCode},msgID: $messageID")
        }
    }


    /**
     * 发送弹幕消息
     */
    protected fun sendBarrageMessage(msg: String){
        engine.sendBarrageMessage(roomID,msg
        ) { errorCode, messageID ->
            Log.e(TAG,"弹幕消息: errCode: ${errorCode},msgID: $messageID")
        }
    }

    /**
     * 发送自定义消息
     * 发送自定义信令，`toUserList` 中指定的用户才可以通过 onIMSendCustomCommandResult 收到此信令
     * 若 `toUserList` 参数传 `null` 则 SDK 将发送该信令给房间内所有用户
     */
    protected fun sendCustomCommand(msg: String,toUserList: ArrayList<ZegoUser>?){
        engine.sendCustomCommand(roomID, msg, toUserList) { errorCode ->
            //发送消息结果成功或失败的处理
            Log.e(TAG, "自定义消息: errCode: $errorCode")
        }
    }

    private lateinit var player: ZegoMediaPlayer

    /**
     * 创建媒体播放器
     */
    protected fun createPlayer(){
        player = engine.createMediaPlayer()
        player.enableRepeat(true) //是否重复播放
        Log.d(TAG, "createMediaPlayer create sucess")
    }

    /**
     * 加载媒体资源
     *
     * 可传本地资源的绝对路径或者网络资源的 URL
     * @param path 本地资源路径或网络资源的 URL
     * @param callback 资源加载结果的通知
     */
    private fun loadResource(sourcePath: String){
        player.loadResource(sourcePath) { errorCode ->
            if (errorCode == 0) {
                Log.d(TAG, "onLoadResourceCallback: success");
            } else {
                Log.d(TAG, "onLoadResourceCallback: errorCode = $errorCode");
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        setCloseStream(-1)
        logoutRoom()
        ZegoExpressEngine.destroyEngine(null)
    }


    /**
     * 初始化即构和用户
     */
    private fun initEngineAndUser() {
        userID = ("Android_" + Build.MODEL).replace(" ".toRegex(), "_")
        user = ZegoUser(userID)
    }

    /**
     * 请求权限
     */
    private fun requestPermission() {
        val PERMISSIONS_STORAGE = arrayOf(
            "android.permission.CAMERA",
            "android.permission.RECORD_AUDIO"
        )
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(
                    this,
                    "android.permission.CAMERA"
                ) != PackageManager.PERMISSION_GRANTED
                || ContextCompat.checkSelfPermission(
                    this,
                    "android.permission.RECORD_AUDIO"
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                requestPermissions(PERMISSIONS_STORAGE, 101)
            }
        }
    }

    private var videoRoomID = "room02"

    /**
     * 登录房间
     */
    protected fun loginVideoRoom() {
        //login room
        engine.loginRoom(videoRoomID, user)
        //enable the camera
        engine.enableCamera(true)
        //enable the microphone
        engine.muteMicrophone(false)
        //enable the speaker
        engine.muteSpeaker(false)
    }

    /**
     * 显示当前手机拍摄视频
     */
    protected fun showVideo(viewID: View){
        val previewCanvas = ZegoCanvas(viewID)
        // 将视图模式设置为 ASPECT_FIT
        //ASPECT_FIT	等比缩放，可能有黑边。
        //ASPECT_FILL	等比缩放填充整个 View，可能有部分被裁减。
        //SCALE_TO_FILL	填充整个 View，图像可能被拉伸。
        previewCanvas.viewMode = ZegoViewMode.ASPECT_FIT
        // 开始预览
        engine.setVideoMirrorMode(ZegoVideoMirrorMode.NO_MIRROR)
        engine.videoConfig = IZeGoVideoConfig.fixedVertical()
        engine.startPreview(previewCanvas)
    }

    private val videoStreamID = "Android-4561234581"

    /**
     * 推送视频流
     */
    protected fun pushVideoStream(){
        engine.startPublishingStream(videoStreamID)
    }

    /**
     * 拉取视频流
     */
    protected fun playVideoStream(viewID: View){
        engine.startPlayingStream(videoStreamID, ZegoCanvas(viewID))
    }



    /**
     * 监听登录房间后的事件回调
     */
    private fun listenerRoom(): Boolean{
        engine.setEventHandler(MyIZegoEvent())
        isListener = true
        return isListener
    }

    inner class MyIZegoEvent: IZegoEventHandler(){

        //房间状态更新回调
        override fun onRoomStateUpdate(
            roomID: String?,
            state: ZegoRoomState?,
            errorCode: Int,
            extendedData: JSONObject?
        ) {
            super.onRoomStateUpdate(roomID, state, errorCode, extendedData)
            Log.e(TAG,"房间状态更新")
        }

        //用户状态更新
        override fun onRoomUserUpdate(
            roomID: String?,
            updateType: ZegoUpdateType?,
            userList: ArrayList<ZegoUser>?
        ) {
            super.onRoomUserUpdate(roomID, updateType, userList)
            Log.e(TAG,"用户状态更新")
        }

        //流状态更新
        override fun onRoomStreamUpdate(
            roomID: String?,
            updateType: ZegoUpdateType?,
            streamList: ArrayList<ZegoStream>?,
            extendedData: JSONObject?
        ) {
            super.onRoomStreamUpdate(roomID, updateType, streamList, extendedData)
            Log.e(TAG,"流状态更新")
        }

        //推流状态更新回调
        override fun onPublisherStateUpdate(
            streamID: String?,
            state: ZegoPublisherState?,
            errorCode: Int,
            extendedData: JSONObject?
        ) {
            super.onPublisherStateUpdate(streamID, state, errorCode, extendedData)
            Log.e(TAG,"推流状态更新回调")
        }

        //拉流状态更新回调
        override fun onPlayerStateUpdate(
            streamID: String?,
            state: ZegoPlayerState?,
            errorCode: Int,
            extendedData: JSONObject?
        ) {
            super.onPlayerStateUpdate(streamID, state, errorCode, extendedData)
            Log.e(TAG,"拉流状态更新回调")
        }

        /**
         * 接收房间广播消息通知
         *
         * @param roomID 房间 ID
         * @param messageList 收到的消息列表
         */
        override fun onIMRecvBroadcastMessage(
            roomID: String?,
            messageList: ArrayList<ZegoBroadcastMessageInfo>
        ) {
            super.onIMRecvBroadcastMessage(roomID, messageList)
            val message = messageList[messageList.size - 1].message
            val user = messageList[messageList.size - 1].fromUser.userID
            Log.e(TAG,"接收到广播消息: $message,$user")
        }


        /**
         * 接收房间弹幕消息通知
         *
         * @param roomID 房间 ID
         * @param messageList 收到的消息列表
         */
        override fun onIMRecvBarrageMessage(
            roomID: String?,
            messageList: ArrayList<ZegoBarrageMessageInfo>
        ) {
            super.onIMRecvBarrageMessage(roomID, messageList)
            val message = messageList[messageList.size - 1].message
            val user = messageList[messageList.size - 1].fromUser.userID
            Log.e(TAG,"接收到弹幕消息: $message,$user")
        }

        /**
         * 接收自定义信令通知
         *
         * @param roomID 房间 ID
         * @param fromUser 信令的发送人
         * @param command 信令内容
         */
        override fun onIMRecvCustomCommand(roomID: String?, fromUser: ZegoUser, command: String) {
            super.onIMRecvCustomCommand(roomID, fromUser, command)
            Log.e(TAG,"接收到自定义消息: $command")
        }

    }

    protected abstract fun listenClick()


}
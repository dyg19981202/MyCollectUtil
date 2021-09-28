package cn.dfordog.basezego

import android.content.pm.PackageManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import androidx.core.content.ContextCompat
import cn.dfordog.basezego.config.BuildConfig.ZE_GO_APP_ID
import cn.dfordog.basezego.config.BuildConfig.ZE_GO_APP_SIGN
import im.zego.zegoexpress.ZegoExpressEngine
import im.zego.zegoexpress.callback.IZegoEventHandler
import im.zego.zegoexpress.callback.IZegoIMSendBroadcastMessageCallback
import im.zego.zegoexpress.constants.*
import im.zego.zegoexpress.entity.ZegoStream
import im.zego.zegoexpress.entity.ZegoUser
import org.json.JSONObject
import java.util.ArrayList
import im.zego.zegoexpress.callback.IZegoIMSendCustomCommandCallback

import java.lang.Compiler.command
import im.zego.zegoexpress.entity.ZegoBroadcastMessageInfo
import im.zego.zegoexpress.entity.ZegoBarrageMessageInfo


class ZegoActivity : AppCompatActivity() {

    private lateinit var engine: ZegoExpressEngine
    private val streamID = "Android-4561234851"
    private val roomID = "room1"
    private var msgType = 0
    private lateinit var showMsg: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_zego)

        getVoicePermission()

        showMsg = findViewById(R.id.showMsg)
        engine = ZegoExpressEngine.createEngine(ZE_GO_APP_ID, ZE_GO_APP_SIGN,true, ZegoScenario.GENERAL,
            application, null)

        findViewById<Button>(R.id.loginRoom).setOnClickListener {
            loginRoom()
        }

        listenerRoom()

        findViewById<Button>(R.id.publish).setOnClickListener {
            pushStream()
        }

        findViewById<Button>(R.id.playing).setOnClickListener {
            playStream()
        }

        findViewById<Button>(R.id.sendMsg).setOnClickListener {
            when(msgType){
                0 -> sendBroadcastMessage("这是广播消息")
                1 -> sendBarrageMessage("这是弹幕消息")
                2 -> sendCustomCommand("这是自定义消息",null)
            }
            msgType++
            if(msgType == 3){
                msgType = 0
            }
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
    private fun loginRoom(){
        val user = ZegoUser("user1")
        engine.loginRoom(roomID,user)
    }


    /**
     * 监听登录房间后的事件回调
     */
    private fun listenerRoom(){
        engine.setEventHandler(MyIZegoEvent())
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
            Log.e("ZegoActivity: ","房间状态更新")
        }

        //用户状态更新
        override fun onRoomUserUpdate(
            roomID: String?,
            updateType: ZegoUpdateType?,
            userList: ArrayList<ZegoUser>?
        ) {
            super.onRoomUserUpdate(roomID, updateType, userList)
            Log.e("ZegoActivity: ","用户状态更新")
        }

        //流状态更新
        override fun onRoomStreamUpdate(
            roomID: String?,
            updateType: ZegoUpdateType?,
            streamList: ArrayList<ZegoStream>?,
            extendedData: JSONObject?
        ) {
            super.onRoomStreamUpdate(roomID, updateType, streamList, extendedData)
            Log.e("ZegoActivity: ","流状态更新")
        }

        //推流状态更新回调
        override fun onPublisherStateUpdate(
            streamID: String?,
            state: ZegoPublisherState?,
            errorCode: Int,
            extendedData: JSONObject?
        ) {
            super.onPublisherStateUpdate(streamID, state, errorCode, extendedData)
            Log.e("ZegoActivity: ","推流状态更新回调")
        }

        //拉流状态更新回调
        override fun onPlayerStateUpdate(
            streamID: String?,
            state: ZegoPlayerState?,
            errorCode: Int,
            extendedData: JSONObject?
        ) {
            super.onPlayerStateUpdate(streamID, state, errorCode, extendedData)
            Log.e("ZegoActivity: ","拉流状态更新回调")
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
            showMsg.text = messageList[0].message.toString()
            Log.e("ZegoActivity: ","接收到广播消息: $message,$user")
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
            Log.e("ZegoActivity: ","接收到弹幕消息: $message,$user")
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
            Log.e("ZegoActivity: ","接收到自定义消息: $command")
        }


    }


    /**
     * 推送流
     */
    private fun pushStream(){
        engine.startPublishingStream(streamID)
    }

    /**
     * 拉流
     */
    private fun playStream(){
        engine.startPlayingStream(streamID)
    }

    /**
     * 停止推流或拉流
     */
    private fun setCloseStream(type: Int){
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
    private fun sendBroadcastMessage(msg: String){
        engine.sendBroadcastMessage(roomID,msg
        ) { errorCode, messageID ->
            Log.e("ZegoActivity","广播消息: errCode: ${errorCode},msgID: $messageID")
        }
    }


    /**
     * 发送弹幕消息
     */
    private fun sendBarrageMessage(msg: String){
        engine.sendBarrageMessage(roomID,msg
        ) { errorCode, messageID ->
            Log.e("ZegoActivity","弹幕消息: errCode: ${errorCode},msgID: $messageID")
        }
    }

    /**
     * 发送自定义消息
     * 发送自定义信令，`toUserList` 中指定的用户才可以通过 onIMSendCustomCommandResult 收到此信令
     * 若 `toUserList` 参数传 `null` 则 SDK 将发送该信令给房间内所有用户
     */
    private fun sendCustomCommand(msg: String,toUserList: ArrayList<ZegoUser>?){
        engine.sendCustomCommand(roomID, msg, toUserList) { errorCode ->
            //发送消息结果成功或失败的处理
            Log.e("ZegoActivity", "自定义消息: errCode: $errorCode")
        }
    }


    /**
     * 创建媒体播放器
     */
    private fun createPlayer(){
        val player = engine.createMediaPlayer()

    }

    override fun onDestroy() {
        super.onDestroy()
        setCloseStream(-1)
        logoutRoom()
        ZegoExpressEngine.destroyEngine(null)
    }
}
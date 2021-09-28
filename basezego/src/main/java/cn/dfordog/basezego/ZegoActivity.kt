package cn.dfordog.basezego

import android.content.pm.PackageManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.core.content.ContextCompat
import cn.dfordog.basezego.config.BuildConfig.ZE_GO_APP_ID
import cn.dfordog.basezego.config.BuildConfig.ZE_GO_APP_SIGN
import im.zego.zegoexpress.ZegoExpressEngine
import im.zego.zegoexpress.callback.IZegoEventHandler
import im.zego.zegoexpress.constants.*
import im.zego.zegoexpress.entity.ZegoStream
import im.zego.zegoexpress.entity.ZegoUser
import org.json.JSONObject
import java.util.ArrayList

class ZegoActivity : AppCompatActivity() {

    private lateinit var engine: ZegoExpressEngine

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_zego)

        getVoicePermission()

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
        engine.loginRoom("room1",user)
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
    }


    /**
     * 推送流
     */
    private fun pushStream(){
        engine.startPublishingStream("Android-4561234851")
    }

    /**
     * 拉流
     */
    private fun playStream(){
        engine.startPlayingStream("Android-4561234851")
    }


    /**
     * 创建媒体播放器
     */
    private fun createPlayer(){
        val player = engine.createMediaPlayer()

    }
}
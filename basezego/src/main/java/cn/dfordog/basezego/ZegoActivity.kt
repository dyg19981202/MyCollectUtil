package cn.dfordog.basezego

import android.content.Intent
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
import cn.dfordog.basezego.databinding.ActivityZegoBinding
import im.zego.zegoexpress.ZegoExpressEngine
import im.zego.zegoexpress.ZegoMediaPlayer
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


class ZegoActivity : BaseZeGoActivity<ActivityZegoBinding>(R.layout.activity_zego) {


    private var msgType = 0

    override fun listenClick() {

        binding.loginRoom.setOnClickListener {
            loginRoom()
        }


        binding.publish.setOnClickListener {
            pushStream()
        }

        binding.playing.setOnClickListener {
            playStream()
        }

        binding.sendMsg.setOnClickListener {
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

        binding.toVideo.setOnClickListener {
            startActivity(Intent(this,ZegoVideoActivity::class.java))
        }
    }
}
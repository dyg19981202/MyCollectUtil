package cn.dfordog.basezego


import android.os.Bundle
import cn.dfordog.basezego.config.BuildConfig
import cn.dfordog.basezego.databinding.ActivityZegoVideoBinding
import im.zego.zegoexpress.ZegoExpressEngine
import im.zego.zegoexpress.constants.ZegoScenario

class ZegoVideoActivity :
    BaseZeGoActivity<ActivityZegoVideoBinding>(R.layout.activity_zego_video) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        loginVideoRoom()
        showVideo(binding.pushVideo)
    }


    override fun listenClick() {
        binding.startPush.setOnClickListener {
            pushVideoStream()
        }

        binding.startPull.setOnClickListener {
            playVideoStream(binding.pullVideo)
        }
    }



}
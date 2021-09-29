package cn.dfordog.basezego.config

import im.zego.zegoexpress.constants.ZegoVideoConfigPreset
import im.zego.zegoexpress.entity.ZegoVideoConfig
import im.zego.zegoexpress.internal.ZegoExpressEngineInternalImpl

/**
 * 视频配置类
 * <p>
 *     设置视频视图模式前需先停止预览或停止拉流，否则无法生效。
 * </p>
 */
object IZeGoVideoConfig {

    private const val CAPTURE_HEIGHT = 640
    private const val CAPTURE_WIDTH = 360
    private const val ENCODE_HEIGHT = 640
    private const val ENCODE_WIDTH = 360
    private const val FPS = 30
    private const val BITRATE = 1200 //码率 600 kbps

    // 设置视频配置
    fun setIVideoConfig() =
        //使用预设值
//        ZegoVideoConfig(ZegoVideoConfigPreset.PRESET_1080P)
        // 自定义值
        ZegoVideoConfig().apply {
            captureHeight = CAPTURE_HEIGHT
            captureWidth = CAPTURE_WIDTH
            encodeHeight = ENCODE_HEIGHT
            encodeWidth = ENCODE_WIDTH
            fps = FPS
            bitrate = BITRATE

        }

    /**
     * 固定竖屏
     */
    fun fixedVertical() =
        ZegoVideoConfig().apply {
            setEncodeResolution(360,640)
        }




}
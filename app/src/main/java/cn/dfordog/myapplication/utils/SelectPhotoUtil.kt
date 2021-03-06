package cn.dfordog.myapplication.utils

import android.app.Activity
import android.content.Context
import android.content.pm.ActivityInfo
import android.graphics.Color
import android.util.Log
import androidx.core.content.ContextCompat
import cn.dfordog.myapplication.GlideCacheEngine
import cn.dfordog.myapplication.GlideEngine
import cn.dfordog.myapplication.R
import cn.dfordog.myapplication.SelectPhotoActivity
import cn.dfordog.myapplication.adapter.GridImageAdapter
import com.luck.picture.lib.PictureSelector
import com.luck.picture.lib.config.PictureConfig
import com.luck.picture.lib.config.PictureMimeType
import com.luck.picture.lib.entity.LocalMedia
import com.luck.picture.lib.listener.OnResultCallbackListener
import com.luck.picture.lib.style.PictureCropParameterStyle
import com.luck.picture.lib.style.PictureParameterStyle
import com.luck.picture.lib.style.PictureWindowAnimationStyle
import com.yalantis.ucrop.view.OverlayView
import java.lang.ref.WeakReference

object SelectPhotoUtil {
    private lateinit var mWindowAnimationStyle: PictureWindowAnimationStyle
    private const val maxSelectNum = 5
    private val selectedImages = mutableListOf<String>() //保存已经选中的图片
    private val selectedVideos = mutableListOf<String>() //保存已经选中的视频

    /**
     * 选择图片(含有拍照功能)
     * @param activity 需要的活动
     * @param chooseMode 全部.PictureMimeType.ofAll()、图片.ofImage()、视频.ofVideo()、音频.ofAudio()
     * @param mAdapter 显示已经被选择的适配器
     */
    fun selectPhoto(activity: Activity,chooseMode: Int,mAdapter: GridImageAdapter){

        getDefaultStyle(activity)
        mWindowAnimationStyle = PictureWindowAnimationStyle()
        mAdapter.setSelectMax(maxSelectNum)
        // 进入相册 以下是例子：不需要的api可以不写
        PictureSelector.create(activity)
            .openGallery(chooseMode) // 全部.PictureMimeType.ofAll()、图片.ofImage()、视频.ofVideo()、音频.ofAudio()
            .imageEngine(GlideEngine.createGlideEngine()) // 外部传入图片加载引擎，必传项
            .theme(R.style.picture_WeChat_style) // 主题样式设置 具体参考 values/styles   用法：R.style.picture.white.style v2.3.3后 建议使用setPictureStyle()动态方式
            .isWeChatStyle(true) // 是否开启微信图片选择风格
            .isUseCustomCamera(false) // 是否使用自定义相机
            .setLanguage(-1) // 设置语言，默认中文
            .setPictureStyle(mPictureParameterStyle) // 动态自定义相册主题
            .setPictureCropStyle(mCropParameterStyle) // 动态自定义裁剪主题
            .setPictureWindowAnimationStyle(mWindowAnimationStyle) // 自定义相册启动退出动画
            .isWithVideoImage(true) // 图片和视频是否可以同选
            .maxSelectNum(maxSelectNum) // 最大图片选择数量
            //.minSelectNum(1)// 最小选择数量
            //.minVideoSelectNum(1)// 视频最小选择数量，如果没有单独设置的需求则可以不设置，同用minSelectNum字段
            .maxVideoSelectNum(maxSelectNum) // 视频最大选择数量，如果没有单独设置的需求则可以不设置，同用maxSelectNum字段
            .imageSpanCount(4) // 每行显示个数
            .isReturnEmpty(true) // 未选择数据时点击按钮是否可以返回
            //.isAndroidQTransform(false)// 是否需要处理Android Q 拷贝至应用沙盒的操作，只针对.isCompress(false); && .isEnableCrop(false);有效,默认处理
            .loadCacheResourcesCallback(GlideCacheEngine.createCacheEngine()) // 获取图片资源缓存，主要是解决华为10部分机型在拷贝文件过多时会出现卡的问题，这里可以判断只在会出现一直转圈问题机型上使用
            .setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED) // 设置相册Activity方向，不设置默认使用系统
            .isOriginalImageControl(false) // 是否显示原图控制按钮，如果设置为true则用户可以自由选择是否使用原图，裁剪功能将会失效
            .isDisplayOriginalSize(true) // 是否显示原文件大小，isOriginalImageControl true有效
            .isEditorImage(false) //是否编辑图片
            //.cameraFileName("test.png")    // 重命名拍照文件名、注意这个只在使用相机时可以使用，如果使用相机又开启了压缩或裁剪 需要配合压缩和裁剪文件名api
            //.renameCompressFile("test.png")// 重命名压缩文件名、 注意这个不要重复，只适用于单张图压缩使用
            //.renameCropFileName("test.png")// 重命名裁剪文件名、 注意这个不要重复，只适用于单张图裁剪使用
            .selectionMode(PictureConfig.MULTIPLE) // 多选 or 单选
            .isSingleDirectReturn(false) // 单选模式下是否直接返回，PictureConfig.SINGLE模式下有效
            .isPreviewImage(true) // 是否可预览图片
            .isPreviewVideo(true) // 是否可预览视频
            //.querySpecifiedFormatSuffix(PictureMimeType.ofJPEG())// 查询指定后缀格式资源
            .isEnablePreviewAudio(true) // 是否可播放音频
            .isCamera(true) // 是否显示拍照按钮
            //.isMultipleSkipCrop(false)// 多图裁剪时是否支持跳过，默认支持
            .isZoomAnim(true) // 图片列表点击 缩放效果 默认true
            //.imageFormat(PictureMimeType.PNG)// 拍照保存图片格式后缀,默认jpeg
            .setCameraImageFormat(PictureMimeType.PNG) // 相机图片格式后缀,默认.jpeg
            .setCameraVideoFormat(PictureMimeType.MP4) // 相机视频格式后缀,默认.mp4
            .setCameraAudioFormat(PictureMimeType.AMR) // 录音音频格式后缀,默认.amr
            .isEnableCrop(false) // 是否裁剪
            .isCompress(true) // 是否压缩
            .compressQuality(80) // 图片压缩后输出质量 0~ 100
            .synOrAsy(true) //同步false或异步true 压缩 默认同步
            //.queryMaxFileSize(10)// 只查多少M以内的图片、视频、音频  单位M
            //.compressSavePath(getPath())//压缩图片保存地址
            //.sizeMultiplier(0.5f)// glide 加载图片大小 0~1之间 如设置 .glideOverride()无效 注：已废弃
            //.glideOverride(160, 160)// glide 加载宽高，越小图片列表越流畅，但会影响列表图片浏览的清晰度 注：已废弃
            .withAspectRatio(16,9) // 裁剪比例 如16:9 3:2 3:4 1:1 可自定义
            .hideBottomControls(false) // 是否显示uCrop工具栏，默认不显示
            .isGif(true) // 是否显示gif图片
            .freeStyleCropEnabled(false) // 裁剪框是否可拖拽
            .freeStyleCropMode(OverlayView.DEFAULT_FREESTYLE_CROP_MODE) // 裁剪框拖动模式
            .isCropDragSmoothToCenter(false) // 裁剪框拖动时图片自动跟随居中
            .circleDimmedLayer(false) // 是否圆形裁剪
            //.setCircleDimmedColor(ContextCompat.getColor(this, R.color.app_color_white))// 设置圆形裁剪背景色值
            //.setCircleDimmedBorderColor(ContextCompat.getColor(getApplicationContext(), R.color.app_color_white))// 设置圆形裁剪边框色值
            //.setCircleStrokeWidth(3)// 设置圆形裁剪边框粗细
            .showCropFrame(false) // 是否显示裁剪矩形边框 圆形裁剪时建议设为false
            .showCropGrid(false) // 是否显示裁剪矩形网格 圆形裁剪时建议设为false
            .isOpenClickSound(false) // 是否开启点击声音
            .selectionData(mAdapter.data) // 是否传入已选图片
            //.isDragFrame(false)// 是否可拖动裁剪框(固定)
            //.videoMaxSecond(15)
            //.videoMinSecond(10)
            .isPreviewEggs(false) // 预览图片时 是否增强左右滑动图片体验(图片滑动一半即可看到上一张是否选中)
            //.cropCompressQuality(90)// 注：已废弃 改用cutOutQuality()
            .cutOutQuality(90) // 裁剪输出质量 默认100
            .minimumCompressSize(100) // 小于100kb的图片不压缩
            //.cropWH()// 裁剪宽高比，设置如果大于图片本身宽高则无效
            //.rotateEnabled(true) // 裁剪是否可旋转图片
            //.scaleEnabled(true)// 裁剪是否可放大缩小图片
            //.videoQuality()// 视频录制质量 0 or 1
            //.recordVideoSecond()//录制视频秒数 默认60s
            //.setOutputCameraPath("/CustomPath")// 自定义拍照保存路径  注：已废弃
            //.forResult(PictureConfig.CHOOSE_REQUEST);//结果回调onActivityResult code
            .forResult(MyResultCallback(chooseMode,mAdapter))
    }

    private lateinit var mPictureParameterStyle: PictureParameterStyle
    private lateinit var mCropParameterStyle: PictureCropParameterStyle

    /**
     * 设置默认风格
     */
    private fun getDefaultStyle(context: Context) {
        // 相册主题
        mPictureParameterStyle = PictureParameterStyle()
        // 是否改变状态栏字体颜色(黑白切换)
        mPictureParameterStyle.isChangeStatusBarFontColor = false
        // 是否开启右下角已完成(0/9)风格
        mPictureParameterStyle.isOpenCompletedNumStyle = false
        // 是否开启类似QQ相册带数字选择风格
        mPictureParameterStyle.isOpenCheckNumStyle = false
        // 相册状态栏背景色
        mPictureParameterStyle.pictureStatusBarColor = Color.parseColor("#393a3e")
        // 相册列表标题栏背景色
        mPictureParameterStyle.pictureTitleBarBackgroundColor = Color.parseColor("#393a3e")
        // 相册列表标题栏右侧上拉箭头
        mPictureParameterStyle.pictureTitleUpResId = R.drawable.picture_icon_arrow_up
        // 相册列表标题栏右侧下拉箭头
        mPictureParameterStyle.pictureTitleDownResId = R.drawable.picture_icon_arrow_down
        // 相册文件夹列表选中圆点
        mPictureParameterStyle.pictureFolderCheckedDotStyle = R.drawable.picture_orange_oval
        // 相册返回箭头
        mPictureParameterStyle.pictureLeftBackIcon = R.drawable.picture_icon_back
        // 标题栏字体颜色
        mPictureParameterStyle.pictureTitleTextColor =
            ContextCompat.getColor(context, R.color.picture_color_white)
        // 相册右侧取消按钮字体颜色  废弃 改用.pictureRightDefaultTextColor和.pictureRightDefaultTextColor
        mPictureParameterStyle.pictureCancelTextColor =
            ContextCompat.getColor(context, R.color.picture_color_white)
        // 相册列表勾选图片样式
        mPictureParameterStyle.pictureCheckedStyle = R.drawable.picture_checkbox_selector
        // 相册列表底部背景色
        mPictureParameterStyle.pictureBottomBgColor =
            ContextCompat.getColor(context, R.color.picture_color_grey)
        // 已选数量圆点背景样式
        mPictureParameterStyle.pictureCheckNumBgStyle = R.drawable.picture_num_oval
        // 相册列表底下预览文字色值(预览按钮可点击时的色值)
        mPictureParameterStyle.picturePreviewTextColor =
            ContextCompat.getColor(context, R.color.picture_color_fa632d)
        // 相册列表底下不可预览文字色值(预览按钮不可点击时的色值)
        mPictureParameterStyle.pictureUnPreviewTextColor =
            ContextCompat.getColor(context, R.color.picture_color_white)
        // 相册列表已完成色值(已完成 可点击色值)
        mPictureParameterStyle.pictureCompleteTextColor =
            ContextCompat.getColor(context, R.color.picture_color_fa632d)
        // 相册列表未完成色值(请选择 不可点击色值)
        mPictureParameterStyle.pictureUnCompleteTextColor =
            ContextCompat.getColor(context, R.color.picture_color_white)
        // 预览界面底部背景色
        mPictureParameterStyle.picturePreviewBottomBgColor =
            ContextCompat.getColor(context, R.color.picture_color_grey)
        // 外部预览界面删除按钮样式
        mPictureParameterStyle.pictureExternalPreviewDeleteStyle = R.drawable.picture_icon_delete
        // 原图按钮勾选样式  需设置.isOriginalImageControl(true); 才有效
        mPictureParameterStyle.pictureOriginalControlStyle =
            R.drawable.picture_original_wechat_checkbox
        // 原图文字颜色 需设置.isOriginalImageControl(true); 才有效
        mPictureParameterStyle.pictureOriginalFontColor =
            ContextCompat.getColor(context, R.color.app_color_white)
        // 外部预览界面是否显示删除按钮
        mPictureParameterStyle.pictureExternalPreviewGonePreviewDelete = true
        // 设置NavBar Color SDK Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP有效
        mPictureParameterStyle.pictureNavBarColor = Color.parseColor("#393a3e")
        //        // 自定义相册右侧文本内容设置
//        mPictureParameterStyle.pictureRightDefaultText = "";
//        // 自定义相册未完成文本内容
//        mPictureParameterStyle.pictureUnCompleteText = "";
//        // 自定义相册完成文本内容
//        mPictureParameterStyle.pictureCompleteText = "";
//        // 自定义相册列表不可预览文字
//        mPictureParameterStyle.pictureUnPreviewText = "";
//        // 自定义相册列表预览文字
//        mPictureParameterStyle.picturePreviewText = "";
//
//        // 自定义相册标题字体大小
//        mPictureParameterStyle.pictureTitleTextSize = 18;
//        // 自定义相册右侧文字大小
//        mPictureParameterStyle.pictureRightTextSize = 14;
//        // 自定义相册预览文字大小
//        mPictureParameterStyle.picturePreviewTextSize = 14;
//        // 自定义相册完成文字大小
//        mPictureParameterStyle.pictureCompleteTextSize = 14;
//        // 自定义原图文字大小
//        mPictureParameterStyle.pictureOriginalTextSize = 14;

        // 裁剪主题
        mCropParameterStyle = PictureCropParameterStyle(
            ContextCompat.getColor(context, R.color.app_color_grey),
            ContextCompat.getColor(context, R.color.app_color_grey),
            Color.parseColor("#393a3e"),
            ContextCompat.getColor(context, R.color.app_color_white),
            mPictureParameterStyle.isChangeStatusBarFontColor
        )
    }


    /**
     * 获取当前已经选中的文件路径
     * @param chooseMode
     */
    fun getSelectFilePath(chooseMode: Int): List<String>{
        return when (chooseMode) {
            PictureMimeType.ofImage() -> {
                selectedImages
            }
            PictureMimeType.ofVideo() -> {
                selectedVideos
            }
            else -> { mutableListOf() }
        }
    }


    /**
     * 返回结果回调
     */
    private class MyResultCallback(val chooseMode: Int,adapter: GridImageAdapter?) :
        OnResultCallbackListener<LocalMedia> {
        private val mAdapterWeakReference: WeakReference<GridImageAdapter?> = WeakReference(adapter)
        override fun onResult(result: List<LocalMedia>) {
            for (media in result) {
                Log.i("aaaaa", "是否压缩:" + media.isCompressed)
                Log.i("aaaaa", "压缩:" + media.compressPath)
                Log.i("aaaaa", "原图:" + media.path)
                Log.i("aaaaa", "绝对路径:" + media.realPath)
                Log.i("aaaaa", "是否裁剪:" + media.isCut)
                Log.i("aaaaa", "裁剪:" + media.cutPath)
                Log.i("aaaaa", "是否开启原图:" + media.isOriginal)
                Log.i("aaaaa", "原图路径:" + media.originalPath)
                Log.i(
                    "aaaaa",
                    "Android Q 特有Path:" + media.androidQToPath
                )
                Log.i(
                    "aaaaa",
                    "宽高: " + media.width + "x" + media.height
                )
                Log.i("aaaaa", "Size: " + media.size)
                // TODO 可以通过PictureSelectorExternalUtils.getExifInterface();方法获取一些额外的资源信息，如旋转角度、经纬度等信息

                when(chooseMode){
                    PictureMimeType.ofImage() -> {
                        selectedImages.add(media.compressPath)
                    }
                    PictureMimeType.ofVideo() -> {
                        selectedVideos.add(media.compressPath)
                    }
                }
            }
            if (mAdapterWeakReference.get() != null) {
                mAdapterWeakReference.get()!!.setList(result)
                mAdapterWeakReference.get()!!.notifyDataSetChanged()
            }


        }

        override fun onCancel() {
            Log.i("aaaaa", "PictureSelector Cancel")
        }

    }
}
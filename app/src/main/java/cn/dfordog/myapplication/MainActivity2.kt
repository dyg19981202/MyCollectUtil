package cn.dfordog.myapplication

import android.Manifest
import android.content.Context
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import cn.dfordog.myapplication.adapter.GridImageAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.luck.picture.lib.PictureMediaScannerConnection
import com.luck.picture.lib.PictureSelector
import com.luck.picture.lib.config.PictureConfig
import com.luck.picture.lib.config.PictureMimeType
import com.luck.picture.lib.entity.LocalMedia
import com.luck.picture.lib.listener.OnCallbackListener
import com.luck.picture.lib.listener.OnResultCallbackListener
import com.luck.picture.lib.manager.PictureCacheManager
import com.luck.picture.lib.permissions.PermissionChecker
import com.yalantis.ucrop.view.OverlayView
import java.lang.ref.WeakReference

class MainActivity2 : AppCompatActivity() {


    private lateinit var  mAdapter: GridImageAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)

        if (PermissionChecker.checkSelfPermission(
                this,
                Manifest.permission.CAMERA
            )
        ) {
            //PictureCacheManager.deleteCacheDirFile(getContext(), PictureMimeType.ofImage());
            PictureCacheManager.deleteAllCacheDirFile(
                this
            ) { absolutePath ->
                PictureMediaScannerConnection(this, absolutePath)
                Log.i(
                    "aaaaaaa",
                    "刷新图库:$absolutePath"
                )
            }
        } else {
            PermissionChecker.requestPermissions(
                this, arrayOf(Manifest.permission.CAMERA),
                PictureConfig.APPLY_STORAGE_PERMISSIONS_CODE
            )
        }

        clearCache()
        val mRecyclerView: RecyclerView = findViewById(R.id.mRecyclerView)
        val manager = FullyGridLayoutManager(
            this,
            4, GridLayoutManager.VERTICAL, false
        )
        mRecyclerView.layoutManager = manager

        mAdapter = GridImageAdapter(this, GridImageAdapter.onAddPicClickListener(){

        })
        mRecyclerView.adapter = mAdapter

        findViewById<Button>(R.id.main2Choose).setOnClickListener {
// 单独拍照

            // 单独拍照
            PictureSelector.create(this)
                .openCamera(PictureMimeType.ofAll()) // 单独拍照，也可录像或也可音频 看你传入的类型是图片or视频
                .theme(R.style.picture_default_style) // 主题样式设置 具体参考 values/styles
                .imageEngine(GlideEngine.createGlideEngine()) // 外部传入图片加载引擎，必传项
//                .setPictureStyle(mPictureParameterStyle) // 动态自定义相册主题
//                .setPictureCropStyle(mCropParameterStyle) // 动态自定义裁剪主题
//                .setPictureWindowAnimationStyle(mWindowAnimationStyle) // 自定义相册启动退出动画
                .maxSelectNum(1) // 最大图片选择数量
                .minSelectNum(1) // 最小选择数量
                .isUseCustomCamera(false) // 是否使用自定义相机
                .loadCacheResourcesCallback(GlideCacheEngine.createCacheEngine()) // 获取图片资源缓存，主要是解决华为10部分机型在拷贝文件过多时会出现卡的问题，这里可以判断只在会出现一直转圈问题机型上使用
                //.querySpecifiedFormatSuffix(PictureMimeType.ofPNG())// 查询指定后缀格式资源
//                .selectionMode(if (cb_choose_mode.isChecked()) PictureConfig.MULTIPLE else PictureConfig.SINGLE) // 多选 or 单选
                .selectionMode(PictureConfig.SINGLE) // 多选 or 单选
                //.cameraFileName("test.png")// 使用相机时保存至本地的文件名称,注意这个只在拍照时可以使用
                //.renameCompressFile("test.png")// 重命名压缩文件名、 注意这个不要重复，只适用于单张图压缩使用
                //.renameCropFileName("test.png")// 重命名裁剪文件名、 注意这个不要重复，只适用于单张图裁剪使用
                .isPreviewImage(true) // 是否可预览图片
                .isPreviewVideo(true) // 是否可预览视频
                .isEnablePreviewAudio(true) // 是否可播放音频
                .isCamera(true) // 是否显示拍照按钮
                .isEnableCrop(false) // 是否裁剪
                .isCompress(true) // 是否压缩
                .compressQuality(60) // 图片压缩后输出质量
                .glideOverride(160, 160) // glide 加载宽高，越小图片列表越流畅，但会影响列表图片浏览的清晰度
                .withAspectRatio(16, 9) // 裁剪比例 如16:9 3:2 3:4 1:1 可自定义
                .hideBottomControls(false) // 是否显示uCrop工具栏，默认不显示
                .isGif(true) // 是否显示gif图片
                .freeStyleCropEnabled(false) // 裁剪框是否可拖拽
                .freeStyleCropMode(OverlayView.DEFAULT_FREESTYLE_CROP_MODE) // 裁剪框拖动模式
                .isCropDragSmoothToCenter(false) // 裁剪框拖动时图片自动跟随居中
                .circleDimmedLayer(false) // 是否圆形裁剪
                //.setCircleDimmedColor(ContextCompat.getColor(this, R.color.app_color_white))// 设置圆形裁剪背景色值
                //.setCircleDimmedBorderColor(ContextCompat.getColor(this, R.color.app_color_white))// 设置圆形裁剪边框色值
                //.setCircleStrokeWidth(3)// 设置圆形裁剪边框粗细
                .showCropFrame(false) // 是否显示裁剪矩形边框 圆形裁剪时建议设为false
                .showCropGrid(false) // 是否显示裁剪矩形网格 圆形裁剪时建议设为false
                .isOpenClickSound(false) // 是否开启点击声音
                .selectionData(mAdapter.getData()) // 是否传入已选图片
                .isPreviewEggs(false) //预览图片时 是否增强左右滑动图片体验(图片滑动一半即可看到上一张是否选中)
                //.cropCompressQuality(90)// 废弃 改用cutOutQuality()
                .cutOutQuality(90) // 裁剪输出质量 默认100
                .minimumCompressSize(100) // 小于100kb的图片不压缩
                //.cropWH()// 裁剪宽高比，设置如果大于图片本身宽高则无效
                //.rotateEnabled() // 裁剪是否可旋转图片
                //.scaleEnabled()// 裁剪是否可放大缩小图片
                //.videoQuality()// 视频录制质量 0 or 1
                //.forResult(PictureConfig.CHOOSE_REQUEST);//结果回调onActivityResult code
//                .forResult(MyResultCallback(mAdapter))
                .forResult(MyResultCallback1(this,findViewById(R.id.testImg)))
        }

    }


    /**
     * 返回结果回调
     */
    private class MyResultCallback(adapter: GridImageAdapter?) :
        OnResultCallbackListener<LocalMedia> {
        private val mAdapterWeakReference: WeakReference<GridImageAdapter?>
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
            }
            if (mAdapterWeakReference.get() != null) {
                mAdapterWeakReference.get()!!.setList(result)
                mAdapterWeakReference.get()!!.notifyDataSetChanged()
            }
        }

        override fun onCancel() {
            Log.i("aaaaa", "PictureSelector Cancel")
        }

        init {
            mAdapterWeakReference = WeakReference<GridImageAdapter?>(adapter)
        }
    }


    /**
     * 返回结果回调
     */
    private class MyResultCallback1(val context: Context,val iv: ImageView) :
        OnResultCallbackListener<LocalMedia> {
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

                Glide.with(context)
                    .load(
                        if (PictureMimeType.isContent(media.path) && !media.isCut && !media.isCompressed) Uri.parse(
                            media.path
                        ) else media.path
                    )
                    .centerCrop()
                    .placeholder(R.color.app_color_f6)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(iv)
            }

        }

        override fun onCancel() {
            Log.i("aaaaa", "PictureSelector Cancel")
        }

    }


    private fun clearCache() {
        // 清空图片缓存，包括裁剪、压缩后的图片 注意:必须要在上传完成后调用 必须要获取权限
        if (PermissionChecker.checkSelfPermission(
                this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            )
        ) {
            //PictureCacheManager.deleteCacheDirFile(getContext(), PictureMimeType.ofImage());
            PictureCacheManager.deleteAllCacheDirFile(
                this
            ) { absolutePath ->
                PictureMediaScannerConnection(this, absolutePath)
                Log.i(
                    "aaaaaaa",
                    "刷新图库:$absolutePath"
                )
            }
        } else {
            PermissionChecker.requestPermissions(
                this, arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
                PictureConfig.APPLY_STORAGE_PERMISSIONS_CODE
            )
        }
    }
}
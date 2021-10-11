package cn.dfordog.baseretrofit.utils

import android.annotation.TargetApi
import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.os.Build
import android.util.DisplayMetrics
import android.view.Display
import android.view.View
import android.view.WindowManager
import java.lang.Exception


const val STATUS_BAR_HEIGHT = "status_bar_height"
const val DIMEN = "dimen"
const val ANDROID = "android"

/**
 * 获取状态栏高度
 * @param context 上下文
 */
fun getStatusBarHeight(context:Context): Int{

    var result = 0
    val resourceId = context.resources.getIdentifier(STATUS_BAR_HEIGHT, DIMEN, ANDROID)

    if(resourceId > 0){
        result = context.resources.getDimensionPixelOffset(resourceId)
    }

    return result
}

/**
 * 获取导航栏高度
 */
fun getNavBarHeight(windowManager: WindowManager,context: Context): Int{
    var statusHeight = 0
    if(isNavigationBarShow(windowManager)){
        try {
            val clazz = Class.forName("com.android.internal.R\$dimen")
            val `object` = clazz.newInstance()
            val heightStr = clazz.getField("navigation_bar_height")[`object`].toString()
            val height = heightStr.toInt()
            //dp--->px
            statusHeight = context.resources.getDimensionPixelSize(height)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    return statusHeight
}


/**
 * 横屏可通过 widthPixels - widthPixels2 > 0 来判断底部导航栏是否存在
 * @param windowManager
 * @return true表示有虚拟导航栏 false没有虚拟导航栏
 */
@TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
fun isNavigationBarShow(windowManager: WindowManager): Boolean {
    val defaultDisplay: Display = windowManager.defaultDisplay
    //获取屏幕高度
    val outMetrics = DisplayMetrics()
    defaultDisplay.getRealMetrics(outMetrics)
    val heightPixels = outMetrics.heightPixels
    //宽度
    val widthPixels = outMetrics.widthPixels


    //获取内容高度
    val outMetrics2 = DisplayMetrics()
    defaultDisplay.getMetrics(outMetrics2)
    val heightPixels2 = outMetrics2.heightPixels
    //宽度
    val widthPixels2 = outMetrics2.widthPixels
    return heightPixels - heightPixels2 > 0 || widthPixels - widthPixels2 > 0
}

/**
 * 设置虚拟导航栏颜色
 */
fun setWindowStatusBarColor(activity: Activity,colorResId: Int) {
    try {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            activity.window.navigationBarColor = activity.resources.getColor(colorResId)
        }
    } catch (e:Exception) {
        e.printStackTrace()
    }
}


fun setAndroidNativeLightStatusBar(activity: Activity, dark: Boolean) {
    val decor = activity.window.decorView
    if (dark) {
        decor.systemUiVisibility =
            View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
    } else {
        decor.systemUiVisibility =
            View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
    }
}

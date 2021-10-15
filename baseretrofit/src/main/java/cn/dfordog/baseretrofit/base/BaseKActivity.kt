package cn.dfordog.baseretrofit.base

import android.annotation.SuppressLint
import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.content.pm.ActivityInfo
import android.content.res.TypedArray
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.View
import android.view.Window
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import cn.dfordog.baseretrofit.utils.ToastUtils


abstract class BaseKActivity : AppCompatActivity() {
//    var onkeyLoginDialog: OnkeyLoginDialog? = null
    private lateinit var inputMethodManager: InputMethodManager
//    protected var dialog1: CustomProgressDialog? = null
    protected var dialog: ProgressDialog? = null
    protected lateinit var mContext: Context

    @SuppressLint("ResourceType")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(layoutId())
        mContext = this
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT //禁止横屏
        //设置沉浸式状态栏
//        if (useImmersionBar()) {
//            //状态栏透明
//            immersionBar {
//            }
//        } else {
//            //状态栏白色
//            immersionBar {
//                //只适合纯色状态栏
//                fitsSystemWindows(true)
//                statusBarColor(R.color.color_main)
//                statusBarDarkFont(true, 0.2f)
////                keyboardEnable(true)  //解决软键盘与底部输入框冲突问题，默认为false，还有一个重载方法，可以指定软键盘mode
////                keyboardMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN)  //单独指定软键盘模式
//            }
//        }
        inputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
//        dialog1 = CustomProgressDialog(act)
        initView()
//        EventBus.getDefault().register(this)

        App.instances.addActivity(this)
        if (Build.VERSION.SDK_INT == Build.VERSION_CODES.O && isTranslucentOrFloating) {
            /**
             * 去掉设置屏幕方向，要不8.0会崩溃
             * Only fullscreen activities can request orientation
             */
        } else {
            requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        }
        initData()
        initDialog()
    }

    /**
     *  加载布局
     */
    abstract fun layoutId(): Int

    /**
     * 初始化 View
     */
    abstract fun initView()

    /**
     * 初始化数据
     */
    abstract fun initData()

    open fun useImmersionBar() = false
    open fun useEventBus() = false

    override fun onDestroy() {
        super.onDestroy()
//        EventBus.getDefault().unregister(this)
    }

    fun showToast(msg: String) {
        ToastUtils.showShort(msg)
    }

    @JvmOverloads
    fun gotoActivity(clz: Class<*>, isCloseCurrentActivity: Boolean = false, ex: Bundle? = null) {
        val intent = Intent(this, clz)
        if (ex != null) intent.putExtras(ex)
        startActivity(intent)
        if (isCloseCurrentActivity) {
            finish()
        }
    }

    protected fun hideSoftKeyboard(view: View) {
        inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
    }

    protected fun showSoftKeyboard(view: View) {
        inputMethodManager.showSoftInput(view, InputMethodManager.SHOW_FORCED)
    }

    val isTranslucentOrFloating: Boolean
        get() {
            var isTranslucentOrFloating = false
            try {
                val styleableRes =
                    Class.forName("com.android.internal.R\$styleable").getField("Window")
                        .get(null) as IntArray
                val ta = obtainStyledAttributes(styleableRes)
                val m = ActivityInfo::class.java.getMethod(
                    "isTranslucentOrFloating",
                    TypedArray::class.java
                )
                m.isAccessible = true
                isTranslucentOrFloating = m.invoke(null, ta) as Boolean
                m.isAccessible = false
            } catch (e: Exception) {
                e.printStackTrace()
            }

            return isTranslucentOrFloating
        }

    protected fun initDialog() {
        dialog = ProgressDialog(this)
        dialog?.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog?.setCanceledOnTouchOutside(false)
        dialog?.setProgressStyle(ProgressDialog.STYLE_SPINNER)
        dialog?.setMessage("请求网络中...")
    }

    protected fun showDialog() {
        if (dialog != null && !(dialog?.isShowing ?: false))
            dialog?.show()
    }

    protected fun hideDialog() {
        if (dialog != null && dialog?.isShowing ?: false)
            dialog?.dismiss()
    }
}
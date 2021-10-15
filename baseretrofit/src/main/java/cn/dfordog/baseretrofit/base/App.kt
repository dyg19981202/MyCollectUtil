package cn.dfordog.baseretrofit.base

//import com.pgyersdk.crash.PgyCrashManager
import android.app.Activity
import android.app.Application
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.os.StrictMode
import android.os.StrictMode.VmPolicy
import android.widget.ImageView
import cn.dfordog.baseretrofit.utils.Utils
import java.lang.ref.WeakReference
import java.util.*


class App : Application() {
    var mActivityStack: Stack<WeakReference<Activity>>? = null
    override fun onCreate() {
        super.onCreate()
        instances = this
        Utils.init(this)
        initOkGo()
        initSmartRefresh()
        val builder = VmPolicy.Builder()
        StrictMode.setVmPolicy(builder.build())
        builder.detectFileUriExposure()
        initNineGridView()
    }

    companion object {
        lateinit var instances: App
    }


    private fun initOkGo() {

    }

    fun initSmartRefresh() {

    }

    /**
     * 添加Activity到栈
     *
     * @param activity
     */
    fun addActivity(activity: Activity) {
        if (mActivityStack == null) {
            mActivityStack = Stack()
        }
        mActivityStack?.add(WeakReference(activity))
    }

    /**
     * 关闭指定类名的所有Activity
     *
     * @param cls
     */
    fun finishActivity(cls: Class<*>) {
        if (mActivityStack != null) {
            // 使用迭代器进行安全删除
            val it = mActivityStack!!.iterator()
            while (it.hasNext()) {
                val activityReference = it.next()
                val activity = activityReference.get()
                // 清理掉已经释放的activity
                if (activity == null) {
                    it.remove()
                    continue
                }
                if (activity.javaClass == cls) {
                    it.remove()
                    activity.finish()
                }
            }
        }
    }

    /**
     * 退出应用程序
     */
    fun exitApp() {
        try {
            finishAllActivity()
            // 退出JVM,释放所占内存资源,0表示正常退出
            System.exit(0)
            // 从系统中kill掉应用程序
            android.os.Process.killProcess(android.os.Process.myPid())
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

    /**
     * 结束所有Activity
     */
    fun finishAllActivity() {
        if (mActivityStack != null) {
            for (activityReference in mActivityStack!!) {
                val activity = activityReference.get()
                if (activity != null) {
                    activity.finish()
                }
            }
            mActivityStack?.clear()
        }
    }

    private fun initNineGridView() {

    }
}
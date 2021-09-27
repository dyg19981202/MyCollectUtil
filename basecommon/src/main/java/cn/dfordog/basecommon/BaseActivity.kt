package cn.dfordog.basecommon

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding

abstract class BaseActivity<T: ViewDataBinding>(private val layoutId: Int) : AppCompatActivity(){

    protected lateinit var binding: T
    private lateinit var mLoadingDialog: LoadingDialog
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mLoadingDialog = LoadingDialog(this,true)
        initBinding()
        initData()
    }


    private fun initBinding(){
        binding = DataBindingUtil.setContentView(this,layoutId)
    }

//    protected abstract fun getViewId(): Int

    protected abstract fun initData()

    protected fun showLoadingDialog(){
        if (!mLoadingDialog.isShowing) {
            mLoadingDialog.show()
        }
    }


    open fun disLoading() {
        if (mLoadingDialog.isShowing) {
            mLoadingDialog.dismiss()
        }
    }
}
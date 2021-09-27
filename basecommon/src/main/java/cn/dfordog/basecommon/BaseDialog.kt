package cn.dfordog.basecommon

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.view.LayoutInflater
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding

abstract class BaseDialog<VDM : ViewDataBinding?> @JvmOverloads constructor(
    context: Context,
    themeResId: Int = R.style.BaseDialogStyle
) :
    Dialog(context, themeResId) {
    protected var mBinding: VDM?
    override fun dismiss() {
        super.dismiss()
        if (mBinding != null) {
            mBinding!!.unbind()
        }
    }

    abstract val layoutId: Int

    abstract fun initView()
    abstract fun initData()

    init {
        mBinding = DataBindingUtil.bind(LayoutInflater.from(context).inflate(layoutId, null, false))
        if (mBinding != null) {
            setContentView(mBinding!!.root)
        }
        initView()
        initData()
    }
}
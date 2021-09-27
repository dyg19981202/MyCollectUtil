package cn.dfordog.basecommon

import android.content.Context
import android.content.DialogInterface
import android.graphics.drawable.AnimationDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import cn.dfordog.basecommon.BaseDialog
import cn.dfordog.basecommon.databinding.DialogLoadingBinding

class LoadingDialog(context: Context,isCancel: Boolean) : BaseDialog<DialogLoadingBinding>(context, R.style.LoadingDialog) {
    var progressWheel: ImageView? = null
    private var animationDrawable: AnimationDrawable? = null
    override val layoutId: Int
        get() = R.layout.dialog_loading

    override fun initView() {
        progressWheel = findViewById(R.id.progress_wheel)
        animationDrawable = progressWheel!!.background as AnimationDrawable
        animationDrawable!!.start()
    }

    override fun setOnDismissListener(listener: DialogInterface.OnDismissListener?) {
        super.setOnDismissListener(listener)
        if (null != animationDrawable && animationDrawable!!.isRunning) {
            animationDrawable!!.stop()
        }
    }

    override fun initData() {}

    init {
        setCancelable(isCancel)
        setCanceledOnTouchOutside(false)
    }
}

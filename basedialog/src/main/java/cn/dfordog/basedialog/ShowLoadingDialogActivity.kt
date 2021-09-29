package cn.dfordog.basedialog

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import cn.dfordog.basecommon.BaseActivity
import cn.dfordog.basedialog.databinding.ActivityShowLoadingDialogBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class ShowLoadingDialogActivity : BaseActivity<ActivityShowLoadingDialogBinding>(R.layout.activity_show_loading_dialog) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding.showDialog.setOnClickListener {
            showLoadingDialog()
            GlobalScope.launch(Dispatchers.IO) {
                Log.e("ShowDialog: ","创建协程，开启等待窗口")
                delay(3000)
                Log.e("ShowDialog: ","阻塞结束，结束等待窗口")
                disLoading()
            }
        }

        binding.dismissDialog.setOnClickListener {
            disLoading()
        }
    }



    override fun initData() {

    }

    override fun listenClick() {

    }
}
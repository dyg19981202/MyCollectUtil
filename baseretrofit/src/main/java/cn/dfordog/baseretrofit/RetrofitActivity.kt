package cn.dfordog.baseretrofit

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import cn.dfordog.baseretrofit.databinding.ActivityRetrofitBinding
import com.hjq.toast.ToastUtils

class RetrofitActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRetrofitBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this,R.layout.activity_retrofit)

        // 初始化 Toast 框架
        ToastUtils.init(application)
    }
}
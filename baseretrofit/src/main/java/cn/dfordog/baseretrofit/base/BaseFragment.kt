package cn.dfordog.baseretrofit.base

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner


abstract class BaseFragment<T: ViewDataBinding,K: BaseViewModel>(
    private val layoutId: Int
    ): Fragment() {

    protected lateinit var binding: T
    protected lateinit var viewModel: K
    private lateinit var launcher: ActivityResultLauncher<Array<String>>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        launcher =
            registerForActivityResult(
                ActivityResultContracts.RequestMultiplePermissions()
            ) {
                //TODO 当前需要进行判断权限是否通过 若通过则进行下一步操作
                //TODO 无法确定是否适合在基类中添加 权限请求
                for(a in it){
                    if(a.value == false){
                        Log.e("refusePermission: ",a.key)
                    }else{
                        Log.e("agreePermission: ",a.key)
                    }
                }
            }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater,layoutId,container,false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewModel = ViewModelProvider(getViewModelOwner()).get(viewModelClass())

        initData()
        listenClick()


    }

    protected abstract fun initData()
    protected abstract fun listenClick()

    protected abstract fun getViewModelOwner(): ViewModelStoreOwner
    protected abstract fun viewModelClass(): Class<K>

    /**
     * 开始请求权限
     */
    protected fun startPermissionLaunch(array: Array<String>){
        launcher.launch(array)
    }

}
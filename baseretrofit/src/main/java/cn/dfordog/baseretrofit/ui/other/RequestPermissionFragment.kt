package cn.dfordog.baseretrofit.ui.other

import android.Manifest
import androidx.lifecycle.ViewModelStoreOwner
import cn.dfordog.baseretrofit.R
import cn.dfordog.baseretrofit.base.BaseFragment
import cn.dfordog.baseretrofit.databinding.RequestPermissionFragmentBinding
import cn.dfordog.baseretrofit.viewmodel.RequestPermissionViewModel

class RequestPermissionFragment :
    BaseFragment<RequestPermissionFragmentBinding,RequestPermissionViewModel>(R.layout.request_permission_fragment) {

    override fun getViewModelOwner(): ViewModelStoreOwner = this

    override fun viewModelClass(): Class<RequestPermissionViewModel> = RequestPermissionViewModel::class.java

    override fun initData() {

    }


    override fun listenClick() {

        binding.requestBtn.setOnClickListener {
//            startPermissionLaunch(arrayOf(
//                Manifest.permission.CAMERA,
//                Manifest.permission.READ_EXTERNAL_STORAGE,
//                Manifest.permission.WRITE_EXTERNAL_STORAGE))


//            fragmentManager?.beginTransaction()?.add(R.id.showDialog,SelectSexDialog())?.commit()
            fragmentManager?.let { it1 -> SelectSexDialog().show(it1,"aaaa") }
        }

    }

}
package cn.dfordog.baseretrofit.ui.other

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
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
            startPermissionLaunch(arrayOf(
                Manifest.permission.CAMERA,
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE))
        }

    }

}
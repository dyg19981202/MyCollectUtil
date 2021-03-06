package cn.dfordog.baseretrofit.ui.login

import android.content.Intent
import android.content.res.ColorStateList
import android.graphics.Color
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import cn.dfordog.baseretrofit.R
import cn.dfordog.baseretrofit.databinding.LoginFragmentBinding
import cn.dfordog.baseretrofit.ui.CircleDelActivity
import cn.dfordog.baseretrofit.utils.LogUtils
import cn.dfordog.baseretrofit.utils.RegexUtil
import cn.dfordog.baseretrofit.utils.RegexUtil.REGEX_TYPE_WORD_NUM
import cn.dfordog.baseretrofit.viewmodel.LoginViewModel
import com.hjq.toast.ToastUtils
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class LoginFragment : Fragment() {

    companion object {
        fun newInstance() = LoginFragment()
    }

    private lateinit var viewModel: LoginViewModel
    private lateinit var binding: LoginFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater,R.layout.login_fragment,container,false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(LoginViewModel::class.java)

        viewModel.getWeather().observe(viewLifecycleOwner,{
            lifecycleScope.launch(Dispatchers.IO){
                Log.e("LoginFragment: ",it.msg + "," + it.data.toString())
            }
        })


//        lifecycleScope.launch(Dispatchers.Main){
//            viewModel.weather().observe(viewLifecycleOwner,{
//                Log.e("LoginFragment: ",it.reason)
//                if(it.error_code == 0){
//                    LogUtils.e(it.result)
//                }
//            })
//        }

        binding.realInputUsername.addTextChangedListener {
            when {
                it!!.length > 20 -> {
                    setHelperText("??????????????????")
                }
                RegexUtil.regex(it.toString(),REGEX_TYPE_WORD_NUM) -> {
                    setHelperText("??????????????????????????????")
                }
                else -> {
                    binding.inputUsername.isHelperTextEnabled = false
                    binding.inputUsername.boxStrokeColor = Color.RED
                    binding.inputUsername.hintTextColor = (ColorStateList.valueOf(Color.RED))
                }
            }
        }

        binding.goLogin.setOnClickListener {
//            viewModel.city = "??????"
//            lifecycleScope.launch(Dispatchers.Main){
//                viewModel.weather().observe(viewLifecycleOwner){
//                    if(it.error_code == 0){
//                        Log.e("????????????",it.toString())
//                    }else{
//                        ToastUtils.show("????????????: ${it.reason}")
//                    }
//                }
//            }
//            findNavController().navigate(R.id.action_loginFragment_to_requestPermissionFragment)

            startActivity(Intent(requireContext(),CircleDelActivity::class.java))

        }
    }

    private fun setHelperText(str: String){
        binding.inputUsername.helperText = str
        binding.inputUsername.setHelperTextColor(ColorStateList.valueOf(Color.RED))
        binding.inputUsername.isErrorEnabled = true
        binding.inputUsername.boxStrokeColor = Color.RED
        binding.inputUsername.hintTextColor = (ColorStateList.valueOf(Color.RED))
    }

}
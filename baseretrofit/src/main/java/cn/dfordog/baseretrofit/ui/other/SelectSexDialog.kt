package cn.dfordog.baseretrofit.ui.other

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import cn.dfordog.baseretrofit.R
import cn.dfordog.baseretrofit.databinding.DialogSelectSexBinding


class SelectSexDialog : DialogFragment() {

    private lateinit var binding: DialogSelectSexBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater,R.layout.dialog_select_sex,container,false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        //初始化window相关表现
        val window = dialog?.window
        //设备背景为透明（默认白色）
        window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        //设置window宽高(单位px)
        window?.attributes?.width = dip2px(requireContext(),339F)
        window?.attributes?.height = dip2px(requireContext(),255F)
        //设置window位置
        window?.attributes?.gravity = Gravity.BOTTOM//底部

        binding.selectSexMale.setOnClickListener {
            binding.selectSexMale.setBackgroundResource(R.drawable.shape_selected_sex_bg)
            binding.selectSexSecret.setBackgroundResource(R.drawable.shape_no_selected_sex_bg)
            binding.selectSexFemale.setBackgroundResource(R.drawable.shape_no_selected_sex_bg)
        }

        binding.selectSexFemale.setOnClickListener {
            binding.selectSexMale.setBackgroundResource(R.drawable.shape_no_selected_sex_bg)
            binding.selectSexSecret.setBackgroundResource(R.drawable.shape_no_selected_sex_bg)
            binding.selectSexFemale.setBackgroundResource(R.drawable.shape_selected_sex_bg)
        }

        binding.selectSexSecret.setOnClickListener {
            binding.selectSexMale.setBackgroundResource(R.drawable.shape_no_selected_sex_bg)
            binding.selectSexSecret.setBackgroundResource(R.drawable.shape_selected_sex_bg)
            binding.selectSexFemale.setBackgroundResource(R.drawable.shape_no_selected_sex_bg)
        }
    }
}

/**
 * 根据手机的分辨率从 dip 的单位 转成为 px(像素)
 */
fun dip2px(context: Context, dpValue: Float): Int {
    val scale: Float = context.getResources().getDisplayMetrics().density
    return (dpValue * scale + 0.5f).toInt()
}

/**
 * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
 */
fun px2dip(context: Context, pxValue: Float): Int {
    val scale: Float = context.getResources().getDisplayMetrics().density
    return (pxValue / scale + 0.5f).toInt()
}
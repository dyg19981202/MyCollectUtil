package cn.dfordog.baseretrofit.ui

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import cn.dfordog.baseretrofit.R

class NewestFragment : Fragment() {

    companion object {
        fun newInstance() = NewestFragment()
    }

    private lateinit var viewModel: NewestViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.newest_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(NewestViewModel::class.java)
        // TODO: Use the ViewModel
    }

}
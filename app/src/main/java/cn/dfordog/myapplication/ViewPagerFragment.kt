package cn.dfordog.myapplication

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TableLayout
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import cn.dfordog.myapplication.adapter.CollectionAdapter
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class ViewPagerFragment : Fragment(){

    private val tabLayout = mutableListOf("动态","课表","点赞","","")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return layoutInflater.inflate(R.layout.fragment_view_pager2,container,false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val adapter = CollectionAdapter(this)
        val mineTab = requireActivity().findViewById<TabLayout>(R.id.mineTab)
        requireActivity().findViewById<ViewPager2>(R.id.mineVP).adapter = adapter


        val mineVP = requireActivity().findViewById<ViewPager2>(R.id.mineVP)
        TabLayoutMediator(mineTab,mineVP){ tab,position ->
            tab.text = tabLayout[position]
        }.attach()
    }
}
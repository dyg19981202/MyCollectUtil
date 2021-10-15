package cn.dfordog.baseretrofit.ui

import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import cn.dfordog.baseretrofit.R
import cn.dfordog.baseretrofit.base.BaseKActivity
import cn.dfordog.baseretrofit.utils.AppBarStateChangeListener
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.tabs.TabLayout
import kotlinx.android.synthetic.main.activity_circle_del.*

class CircleDelActivity : BaseKActivity() {
//    var peopleAdapter: CirclePeopleAdapter? = null
    var mFragments: MutableList<Fragment> = java.util.ArrayList()
    var selectBeans: MutableList<SelectBean>? = null
    override fun layoutId(): Int {
        return R.layout.activity_circle_del
    }

    override fun initData() {

    }

    override fun initView() {
        selectBeans = ArrayList()
        val topic1 = SelectBean()
        topic1.title = "最新"
        val topic2 = SelectBean()
        topic2.title = "热门"
        val topic3 = SelectBean()
        topic3.title = "课程"
        (selectBeans as ArrayList<SelectBean>).add(topic1)
        (selectBeans as ArrayList<SelectBean>).add(topic2)
        (selectBeans as ArrayList<SelectBean>).add(topic3)
        initViewPager()
        app_barlayout.post {
            val layoutParams = app_barlayout.layoutParams as CoordinatorLayout.LayoutParams
            val behavior = layoutParams.behavior as AppBarLayout.Behavior
            behavior.setDragCallback(object : AppBarLayout.Behavior.DragCallback() {
                override fun canDrag(appBarLayout: AppBarLayout): Boolean {
                    return true
                }
            })
        }

        app_barlayout.addOnOffsetChangedListener(object : AppBarStateChangeListener() {
            override fun onStateChanged(appBarLayout: AppBarLayout, state: State) {
                if (state == State.EXPANDED) {
                    //展开状态
                    rel_title.visibility = View.VISIBLE
                    toolbar.visibility = View.GONE
                } else if (state == State.COLLAPSED) {
                    //折叠状态
                    rel_title.visibility = View.GONE
                    toolbar.visibility = View.VISIBLE
                } else {
                    //中间状态
                    toolbar.visibility = View.GONE
                }
            }
        })
        im_back.setOnClickListener {
            finish()
        }
        iv_finish.setOnClickListener {
            finish()
        }
    }

    fun initViewPager() {
        mFragments = java.util.ArrayList()
        for (i in selectBeans?.indices!!) {
            if ("最新" == selectBeans!!.get(i).title) {
                val mainFragment = NewestFragment()
//                val bundle = Bundle()
//                bundle.putString("shopType", "0")
//                mainFragment.arguments = bundle
                mFragments.add(mainFragment)
            } else if ("热门" == selectBeans!!.get(i).title) {
                val mainFragment = NewestFragment()
                mFragments.add(mainFragment)
            } else if ("课程" == selectBeans!!.get(i).title) {
                val mainFragment = NewestFragment()
                mFragments.add(mainFragment)
            }
        }
        var adapter = CircleDelTab(
            mContext,
            supportFragmentManager,
            mFragments,
            selectBeans
        )
        vp_Content.setAdapter(adapter)
        tablayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                setUpTabBadge(adapter)
                val vline = tab.customView!!.findViewById(R.id.v_line) as View
                val tv_tabName = (tab.customView!!.findViewById(R.id.tv_title) as TextView).apply {
                    setTextSize(16f)
                    getPaint().setFakeBoldText(true)
                    setTextColor(ContextCompat.getColor(mContext, R.color.c333333))
                }
                vline.setBackgroundResource(R.drawable.shape_line)
            }

            override fun onTabUnselected(tab: TabLayout.Tab) {
            }

            override fun onTabReselected(tab: TabLayout.Tab) {

            }
        })
        vp_Content.setOffscreenPageLimit(mFragments.size)
        tablayout.setupWithViewPager(vp_Content)
        tablayout.setSelectedTabIndicatorHeight(0)
    }

    private fun setUpTabBadge(mPagerAdapter: CircleDelTab) {
        for (i in 0 until mFragments.size) {
            val tab = tablayout.getTabAt(i)
            // 更新Badge前,先remove原来的customView,否则Badge无法更新
            val customView = tab!!.getCustomView()
            if (customView != null) {
                val parent = customView!!.getParent()
                if (parent != null) {
                    (parent as ViewGroup).removeView(customView)
                }
            }
            // 更新CustomView
            tab.setCustomView(mPagerAdapter.getTabItemView(i))
        }

    }
}





package cn.dfordog.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.LinearLayout
import androidx.viewpager2.widget.ViewPager2
import cn.dfordog.myapplication.adapter.CollectionAdapter
import com.google.android.material.tabs.TabLayoutMediator

class ViewPager2Activity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_pager2)


        val container = findViewById<LinearLayout>(R.id.viewPagerContainer)
        supportFragmentManager.beginTransaction().add(R.id.viewPagerContainer,ViewPagerFragment()).commit()


    }
}
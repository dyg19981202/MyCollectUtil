package cn.dfordog.baseretrofit.ui;



import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.fragment.app.FragmentTransaction;


import java.util.List;

import cn.dfordog.baseretrofit.R;


class CircleDelTab extends FragmentPagerAdapter {
    //声明一个全局变量来接收
    private List<Fragment> mdata;
    List<SelectBean> list;
    private Context mContext;
    private FragmentManager fragmentManager;

    public CircleDelTab(Context mContext, FragmentManager fm, List<Fragment> mdata, List<SelectBean> list) {
        super(fm);
        this.mdata = mdata;
        this.list = list;
        this.mContext = mContext;
        this.fragmentManager = fm;
    }

    @Override
    public Fragment getItem(int position) {
        return mdata.get(position);
    }

    @Override
    public int getCount() {
        return mdata != null ? mdata.size() : 0;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return list.get(position).getTitle();
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {

    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    public View getTabItemView(int position) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_search, null);
        TextView tv_title = (TextView) view.findViewById(R.id.tv_title);
        LinearLayout lin = view.findViewById(R.id.v_line);
//        vline.setBackgroundColor(ContextCompat.getColor(mContext, R.color.white));
        tv_title.setTextColor(ContextCompat.getColor(mContext, R.color.c66202D3D));
        tv_title.setText(list.get(position).getTitle());
        return view;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        Fragment fragment = getItem(position);
        if (!fragment.isAdded()) { // 如果fragment还没有added
            FragmentTransaction ft = fragmentManager.beginTransaction();
            ft.add(fragment, fragment.getClass().getSimpleName());
//            ft.commit();
            ft.commitAllowingStateLoss();
            fragmentManager.executePendingTransactions();//同步的方式添加Fragment
        }
        if (fragment.getView().getParent() == null) {
            container.addView(fragment.getView()); // 为viewpager增加布局
        }
        return fragment;
    }

}



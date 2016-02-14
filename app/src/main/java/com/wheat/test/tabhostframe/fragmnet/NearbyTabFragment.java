package com.wheat.test.tabhostframe.fragmnet;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.wheat.test.tabhostframe.R;
import com.wheat.test.tabhostframe.adapter.FragmentAdapter;
import com.wheat.test.tabhostframe.utils.ActionBarHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wheat on 16/2/14.
 */
public class NearbyTabFragment extends Fragment {

    private View mParentView;

    //Tab菜单，主界面上面的tab切换菜单
    private TabLayout mTabLayout;

    //v4中的ViewPager控件
    private ViewPager mViewPager;

    //将ToolBar与TabLayout结合放入AppBarLayout
    private Toolbar mToolbar;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mParentView = inflater.inflate(R.layout.nearby_fragment, container, false);
        mTabLayout = (TabLayout) mParentView.findViewById(R.id.tab_layout);
        mViewPager = (ViewPager) mParentView.findViewById(R.id.view_pager);
        mToolbar = (Toolbar) mParentView.findViewById(R.id.tool_bar);
        init();
        return mParentView;
    }

    private void init() {

        //初始化ToolBar
//        ((AppCompatActivity) getActivity()).setSupportActionBar(mToolbar);
//        mToolbar.setTitle("主页功能");

        ActionBarHelper.CustomViewTitle  title = new ActionBarHelper.CustomViewTitle(getActivity(), mToolbar);
        title.setTitle("主页功能");
        title.addToTarget();

//

        //初始化TabLayout的title数据集
        List<String> titles = new ArrayList<>();
        titles.add("Tab1");
        titles.add("Tab2");
        titles.add("Tab3");
        //初始化TabLayout的title
        mTabLayout.addTab(mTabLayout.newTab().setText(titles.get(0)));
        mTabLayout.addTab(mTabLayout.newTab().setText(titles.get(1)));
        mTabLayout.addTab(mTabLayout.newTab().setText(titles.get(2)));
        //初始化ViewPager的数据集
        List<Fragment> fragments = new ArrayList<>();
        fragments.add(new NearTab1Fragment());
        fragments.add(new GoodsTabFragment());
        fragments.add(new MessageFragment());
        //创建ViewPager的adapter
        FragmentAdapter adapter = new FragmentAdapter(getFragmentManager(), fragments, titles);
        mViewPager.setAdapter(adapter);
        //千万别忘了，关联TabLayout与ViewPager
        //同时也要覆写PagerAdapter的getPageTitle方法，否则Tab没有title
        mTabLayout.setupWithViewPager(mViewPager);
        mTabLayout.setTabsFromPagerAdapter(adapter);
    }

}

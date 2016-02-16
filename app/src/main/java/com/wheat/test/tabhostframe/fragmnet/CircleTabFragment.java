package com.wheat.test.tabhostframe.fragmnet;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.wheat.test.tabhostframe.R;
import com.wheat.test.tabhostframe.utils.ActionBarHelper;
import com.wheat.test.tabhostframe.view.tab.FragmentTabHost;

/**
 * Created by wheat on 16/2/14.
 */
public class CircleTabFragment extends Fragment {

    private View mParentView;

    private RecyclerView mRecyclerView;

    //将ToolBar与TabLayout结合放入AppBarLayout
    private Toolbar mToolbar;

    private FragmentTabHost mTabHost;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mParentView = inflater.inflate(R.layout.circle_fragment, container, false);
        findView();
        init();
        return mParentView;
    }

    private void findView() {
        mTabHost = (FragmentTabHost) mParentView.findViewById(android.R.id.tabhost);
        mTabHost.setup(getActivity(), getChildFragmentManager(), R.id.container);
        mToolbar = (Toolbar) mParentView.findViewById(R.id.tool_bar);
    }

    private void init() {
        final Class[] tabCls = new Class[]{Circle1Fragment.class, MineFragment.class};

        final String[] tabTags = new String[]{"nearby", "wander"};
        final String[] tabTitles = new String[]{
                "nearby",
                "wander"};

        for (int i = 0; i < tabTags.length; i++) {
            mTabHost.addTab(mTabHost.newTabSpec(tabTags[i]).setIndicator(tabTags[i]),
                    tabCls[i], null);
        }

        ActionBarHelper.CustomTabLayout title = new ActionBarHelper.CustomTabLayout(getActivity(), mToolbar);
        title.setItems(tabTitles, tabTags);
        title.setOnTabSelectedListener(new ActionBarHelper.CustomTabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(String tag) {
                mTabHost.setCurrentTabByTag(tag);
            }
        });
        title.addToTarget();
    }

}

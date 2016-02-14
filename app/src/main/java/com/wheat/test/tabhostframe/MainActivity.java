package com.wheat.test.tabhostframe;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.wheat.test.tabhostframe.fragmnet.CircleTabFragment;
import com.wheat.test.tabhostframe.fragmnet.GoodsTabFragment;
import com.wheat.test.tabhostframe.fragmnet.MessageFragment;
import com.wheat.test.tabhostframe.fragmnet.MineFragment;
import com.wheat.test.tabhostframe.fragmnet.NearbyTabFragment;
import com.wheat.test.tabhostframe.view.FragmentTabHost;

public class MainActivity extends AppCompatActivity implements RadioGroup.OnCheckedChangeListener {

    private FragmentTabHost mTabHost;

    public String[] tabTags = new String[]{"nearby", "discover", "rainbow", "message", "mine"};
    public Class[] tabCls = new Class[]{NearbyTabFragment.class, CircleTabFragment.class,
            GoodsTabFragment.class, MessageFragment.class, MineFragment.class};

    private TextView msgCount;
    private View msgCountLayout;
    /**
     * 底部切换标签
     */
    public RadioButton mTabNearby, mTabDiscover, mTabRainbow, mTabMessage, mTabMy;
    public RadioGroup mTabBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViews();
        initTabHost();
        initEvent();
    }

    private void initTabHost() {
        mTabHost = (FragmentTabHost) findViewById(android.R.id.tabhost);
        mTabHost.setup(this, getSupportFragmentManager(), R.id.container);

        for (int i = 0; i < tabTags.length; i++) {
            mTabHost.addTab(mTabHost.newTabSpec(tabTags[i]).setIndicator(tabTags[i]),
                    tabCls[i], null);
        }
    }


    private void initEvent() {
        mTabBar.setOnCheckedChangeListener(this);
    }

    protected void findViews() {
        mTabBar = (RadioGroup) findViewById(R.id.radioMenu);
        mTabNearby = (RadioButton) findViewById(R.id.rb_near_people);
        mTabDiscover = (RadioButton) findViewById(R.id.rb_discovery);
        mTabRainbow = (RadioButton) findViewById(R.id.rb_rainbow);
        mTabMessage = (RadioButton) findViewById(R.id.rb_msg);
        mTabMy = (RadioButton) findViewById(R.id.rb_my);
        msgCount = (TextView) findViewById(R.id.msgCount);
        msgCountLayout = findViewById(R.id.msgCountLayout);
        mTabNearby.setChecked(true);
    }

    @Override
    public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {
        int index = 0;
        switch (checkedId) {
            case R.id.rb_near_people:
                index = 0;
                break;
            case R.id.rb_discovery:
                index = 1;
                break;
            case R.id.rb_rainbow:
                index = 2;
                break;
            case R.id.rb_msg:
                index = 3;
                break;
            case R.id.rb_my:
                index = 4;
                break;
            default:
                break;
        }
        mTabHost.setCurrentTab(index);
    }


}

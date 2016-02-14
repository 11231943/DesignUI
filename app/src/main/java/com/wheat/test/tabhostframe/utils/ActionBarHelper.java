package com.wheat.test.tabhostframe.utils;

import android.content.Context;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.wheat.test.tabhostframe.R;
import com.wheat.test.tabhostframe.view.TabLayout;

/**
 * Created by wheat on 16/2/14.
 */
public class ActionBarHelper {
    protected static class CustomViewBase {
        Context mContext;
        ActionBar mActionBar;
        Toolbar mToolbar;
        ViewGroup.LayoutParams mLayoutParams;
        View mCustomView;

        public CustomViewBase(Context context, Toolbar toolbar) {
            mContext = context;
            mToolbar = toolbar;

            Toolbar.LayoutParams layoutParams = new Toolbar.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            layoutParams.gravity = Gravity.CENTER;

            mLayoutParams = layoutParams;
        }

        public CustomViewBase(Context context, ActionBar actionBar) {
            mContext = context;
            mActionBar = actionBar;

            ActionBar.LayoutParams layoutParams = new ActionBar.LayoutParams(
                    ActionBar.LayoutParams.WRAP_CONTENT,
                    ActionBar.LayoutParams.MATCH_PARENT);
            layoutParams.gravity = Gravity.CENTER;

            mLayoutParams = layoutParams;
        }

        protected void setCustomView(View customView) {
            mCustomView = customView;
        }

        public void addToTarget() {
            if (mActionBar != null) {
                mActionBar.setDisplayShowCustomEnabled(true);
                mActionBar.setCustomView(mCustomView, (ActionBar.LayoutParams) mLayoutParams);
            } else {
                mToolbar.addView(mCustomView, mLayoutParams);
            }
        }
    }

    public static class CustomViewTitle extends CustomViewBase {
        private TextView mTitleView;

        public CustomViewTitle(Context context, Toolbar toolbar) {
            super(context, toolbar);
            View container = View.inflate(context, R.layout.custom_view_title, null);
            mTitleView = (TextView) container.findViewById(R.id.title);
            setCustomView(container);
        }

        public CustomViewTitle(Context context, ActionBar actionBar) {
            super(context, actionBar);

            View container = View.inflate(context, R.layout.custom_view_title, null);
            mTitleView = (TextView) container.findViewById(R.id.title);
            setCustomView(container);
        }

        public void setTitle(int resId) {
            mTitleView.setText(resId);
        }

        public void setTitle(String title) {
            mTitleView.setText(title);
        }

        public void setTitle(String title, int color) {
            mTitleView.setTextColor(color);
            mTitleView.setText(title);
        }

    }


    public static class CustomTabLayout extends CustomViewBase {

        private final TabLayout mTabLayout;
        private OnTabSelectedListener mOnTabSelectedListener;

        public CustomTabLayout(Context context, Toolbar toolbar) {
            super(context, toolbar);

            mTabLayout = (TabLayout) LayoutInflater.from(context)
                    .inflate(R.layout.toolbar_tab, toolbar, false);
            setCustomView(mTabLayout);
        }

        public CustomTabLayout(Context context, ActionBar actionBar) {
            super(context, actionBar);

            mTabLayout = (TabLayout) LayoutInflater.from(context)
                    .inflate(R.layout.toolbar_tab, null);
            setCustomView(mTabLayout);
        }

        public void setItems(String[] items, String[] tags) {
            for (int i = 0; i < items.length; i++) {
                mTabLayout.addTab(mTabLayout.newTab().setText(items[i]).setTag(tags[i]));
            }
            mTabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
                @Override
                public void onTabSelected(TabLayout.Tab tab) {
                    if (mOnTabSelectedListener != null) {
                        mOnTabSelectedListener.onTabSelected((String) tab.getTag());
                    }
                }

                @Override
                public void onTabUnselected(TabLayout.Tab tab) {

                }

                @Override
                public void onTabReselected(TabLayout.Tab tab) {

                }
            });
        }

        public void setOnTabSelectedListener(OnTabSelectedListener onTabSelectedListener) {
            mOnTabSelectedListener = onTabSelectedListener;
        }

        public interface OnTabSelectedListener {
            void onTabSelected(String tag);
        }

    }

}

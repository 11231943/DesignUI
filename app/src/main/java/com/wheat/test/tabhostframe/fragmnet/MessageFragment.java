package com.wheat.test.tabhostframe.fragmnet;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.wheat.test.tabhostframe.R;
import com.wheat.test.tabhostframe.utils.ActionBarHelper;
import com.wheat.test.tabhostframe.view.widget.scroller.CustomView;

/**
 * Created by wheat on 16/2/14.
 */
public class MessageFragment extends Fragment {
    private View mParentView;
    private Toolbar mToolbar;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mParentView = inflater.inflate(R.layout.message_fragment, container, false);
        mToolbar = (Toolbar) mParentView.findViewById(R.id.tool_bar);
        init();
        return mParentView;
    }

    private void init() {
        ActionBarHelper.CustomViewTitle title = new ActionBarHelper.CustomViewTitle(getActivity(), mToolbar);
        title.setTitle("消息列表");
        title.addToTarget();

    }

}

package com.wheat.test.tabhostframe.fragmnet;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.wheat.test.tabhostframe.R;

/**
 * Created by wheat on 16/2/14.
 */
public class MessageFragment extends Fragment {
    private View mParentView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mParentView = inflater.inflate(R.layout.message_fragment, container, false);
        return mParentView;
    }

}

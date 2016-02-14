package com.wheat.test.tabhostframe.fragmnet;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.wheat.test.tabhostframe.R;
import com.wheat.test.tabhostframe.adapter.RecyclerViewAdapter;

/**
 * Created by wheat on 16/2/14.
 */
public class NearTab1Fragment extends Fragment {

    private View mParentView;

    private RecyclerView mRecyclerView;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mParentView = inflater.inflate(R.layout.near1_fragment, container, false);
        mRecyclerView = (RecyclerView) mParentView.findViewById(R.id.recycler_view);
        return mParentView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerView.setAdapter(new RecyclerViewAdapter(getActivity()));
    }
}

package com.wheat.test.tabhostframe.fragmnet;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.wheat.test.tabhostframe.R;
import com.wheat.test.tabhostframe.view.side.SideMenu;

/**
 * Created by wheat on 16/2/14.
 */
public class MineFragment extends Fragment {
    private View mParentView;

    private SideMenu sideMenu;

    private Button button;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mParentView = inflater.inflate(R.layout.mine_fragment, container, false);
        sideMenu = (SideMenu) mParentView.findViewById(R.id.sideMenu);
        button =(Button) mParentView.findViewById(R.id.mMenu);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toggleMenu(v);
            }
        });
        return mParentView;
    }

    public void toggleMenu(View view) {
        sideMenu.toggle();
    }

}

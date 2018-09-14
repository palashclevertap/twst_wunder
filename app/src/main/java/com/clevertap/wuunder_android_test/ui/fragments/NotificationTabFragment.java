package com.clevertap.wuunder_android_test.ui.fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.clevertap.wuunder_android_test.R;

/**
 * Created by palashjain on 11/09/18.
 */

public class NotificationTabFragment extends Fragment {


    public NotificationTabFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_notification_tab, null);
        init(rootView);


        return rootView;
    }

    private void init(View rootView) {

    }
}

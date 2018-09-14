package com.clevertap.wuunder_android_test.ui.activities.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import butterknife.ButterKnife;

/**
 * Created by palashjain on 11/09/18.
 */

public abstract class BaseActionBarActivity extends AppCompatActivity {


    private final String TAG = BaseActionBarActivity.class.getSimpleName();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getActivityLayout());
        ButterKnife.bind(this);

    }

    protected abstract int getActivityLayout();

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }


}

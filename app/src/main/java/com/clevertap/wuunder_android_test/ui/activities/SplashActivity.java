package com.clevertap.wuunder_android_test.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;

import com.clevertap.wuunder_android_test.R;
import com.clevertap.wuunder_android_test.ui.activities.base.BaseActionBarActivity;

/**
 * Created by palashjain on 11/09/18.
 */

public class SplashActivity extends BaseActionBarActivity {

    private static final String TAG = SplashActivity.class.getSimpleName();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    public void run() {

                            Intent intent = new Intent(SplashActivity.this,
                                    HomeActivity.class);
                            startActivity(intent);
                            finish();

                    }
                }, 2000);
            }






    @Override
    protected int getActivityLayout() {
        return R.layout.activity_splash;
    }


    // Fetches reg id from shared preferences
    // and displays on the screen





}



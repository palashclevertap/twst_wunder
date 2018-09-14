package com.clevertap.wuunder_android_test.ui.activities;


import android.app.AlertDialog;
import android.app.FragmentManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;


import com.clevertap.wuunder_android_test.R;
import com.clevertap.wuunder_android_test.controllers.utils.LocationUtils;
import com.clevertap.wuunder_android_test.controllers.contants.PConstants;
import com.clevertap.wuunder_android_test.controllers.utils.Utilities;
import com.clevertap.wuunder_android_test.ui.activities.base.BaseActionBarActivity;
import com.clevertap.wuunder_android_test.ui.fragments.CarsTabFragment;
import com.clevertap.wuunder_android_test.ui.fragments.MapsPlotTabFragment;
import com.clevertap.wuunder_android_test.ui.fragments.NotificationTabFragment;
import com.clevertap.wuunder_android_test.ui.fragments.ProfileTabFragment;
import com.google.android.gms.maps.model.LatLng;


import butterknife.Bind;
import butterknife.OnClick;


/**
 * Created by palashjain on 11/09/18.
 */

public class HomeActivity extends BaseActionBarActivity  {

    @Bind(R.id.rl_maps_tab)
    RelativeLayout mMapsRelativeLayout;

    @Bind(R.id.rl_car_tab)
    RelativeLayout mCarsTabRelativeLayout;

    @Bind(R.id.rl_notification_tab)
    RelativeLayout mNotificationsTabRelativeLayout;

    @Bind(R.id.rl_profile_tab)
    RelativeLayout mProfiletabRelativeLayout;

    @Bind(R.id.offer_icon_view)
    TextView mOfferIconView;

    @Bind(R.id.offer_tab_label)
    TextView mOfferTabLabel;

    @Bind(R.id.home_icon_view)
    TextView mHomeIconView;

    @Bind(R.id.home_tab_label)
    TextView mHomeTabLabel;

    @Bind(R.id.bag_icon_view)
    TextView mBagIconView;

    @Bind(R.id.bag_label)
    TextView mBagTabLabel;

    @Bind(R.id.more_icon_view)
    TextView mMoreIconView;

    @Bind(R.id.more_tab_label)
    TextView mMoreTabLabel;


    @Bind(R.id.mainLayoutRL)
    RelativeLayout mainLayoutRL;


    @Bind(R.id.bottomLayoutLL)
    LinearLayout bottomLayoutLL;


    private int currentTab = PConstants.CAR_TAB;
    private Handler addTabHandler;
    private CarsTabFragment carsTabFragment;
    private MapsPlotTabFragment mapsPlotTabFragment;
    private NotificationTabFragment notificationTabFragment;
    private ProfileTabFragment profileTabFragment;

    private Runnable addTabRunnable = new Runnable() {
        @Override
        public void run() {
            addSelectedFragmentByTabId(currentTab);
        }
    };

    public HomeActivity() {
        super();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        addTabHandler = new Handler();
        addTabHandler.post(addTabRunnable);
        getCurrentLocation();

        mHomeIconView.setTextColor(getResources().getColor(R.color.colorAccent));
        mHomeTabLabel.setTextColor(getResources().getColor(R.color.colorAccent));


    }

    private void getCurrentLocation() {
        if (Build.VERSION.SDK_INT < 23) {
            LocationUtils utils = new LocationUtils();
            if (utils.checkGpsIsEnable(this)) {

                LatLng latLng = utils.getlocation(this);


            } else {
                AlertDialog.Builder builder = new AlertDialog.Builder(this).setMessage("You need to enable your gps ").setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);

                        startActivity(intent);

                    }
                }).setCancelable(false);
                builder.show();
            }
        } else {

            if (Utilities.checkGPSPermission(this)) {

                LocationUtils utils = new LocationUtils();
                if (utils.checkGpsIsEnable(this)) {

                    LatLng latLng = utils.getlocation(this);



                } else {
                    AlertDialog.Builder builder = new AlertDialog.Builder(this).setMessage("You need to enable your gps ").setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                            startActivity(intent);

                        }
                    }).setCancelable(false);
                    builder.show();
                }
            }
        }

    }




    @Override
    protected int getActivityLayout() {
        return R.layout.activity_home;
    }





    @OnClick(R.id.rl_maps_tab)
    public void mapsTab() {

        currentTab = PConstants.MAP_TAB;
        removeFragment();
        addTabHandler = new Handler();
        addTabHandler.post(addTabRunnable);


        mOfferIconView.setTextColor(getResources().getColor(R.color.colorAccent));
        mOfferTabLabel.setTextColor(getResources().getColor(R.color.colorAccent));

        mHomeIconView.setTextColor(getResources().getColor(R.color.colorWhite));
        mHomeTabLabel.setTextColor(getResources().getColor(R.color.colorWhite));

        mBagIconView.setTextColor(getResources().getColor(R.color.colorWhite));
        mBagTabLabel.setTextColor(getResources().getColor(R.color.colorWhite));

        mMoreIconView.setTextColor(getResources().getColor(R.color.colorWhite));
        mMoreTabLabel.setTextColor(getResources().getColor(R.color.colorWhite));
    }


    @OnClick(R.id.rl_car_tab)
    public void carTab() {

        currentTab = PConstants.CAR_TAB;
        removeFragment();
        addTabHandler = new Handler();
        addTabHandler.post(addTabRunnable);

        mHomeIconView.setTextColor(getResources().getColor(R.color.colorAccent));
        mHomeTabLabel.setTextColor(getResources().getColor(R.color.colorAccent));


        mOfferIconView.setTextColor(getResources().getColor(R.color.colorWhite));
        mOfferTabLabel.setTextColor(getResources().getColor(R.color.colorWhite));

        mBagIconView.setTextColor(getResources().getColor(R.color.colorWhite));
        mBagTabLabel.setTextColor(getResources().getColor(R.color.colorWhite));

        mMoreIconView.setTextColor(getResources().getColor(R.color.colorWhite));
        mMoreTabLabel.setTextColor(getResources().getColor(R.color.colorWhite));

    }


    @OnClick(R.id.rl_notification_tab)
    public void notificationTab() {
        currentTab = PConstants.NOTIFICATION_TAB;
        removeFragment();
        addTabHandler = new Handler();
        addTabHandler.post(addTabRunnable);


        mHomeIconView.setTextColor(getResources().getColor(R.color.colorWhite));
        mHomeTabLabel.setTextColor(getResources().getColor(R.color.colorWhite));


        mOfferIconView.setTextColor(getResources().getColor(R.color.colorWhite));
        mOfferTabLabel.setTextColor(getResources().getColor(R.color.colorWhite));

        mBagIconView.setTextColor(getResources().getColor(R.color.colorAccent));
        mBagTabLabel.setTextColor(getResources().getColor(R.color.colorAccent));

        mMoreIconView.setTextColor(getResources().getColor(R.color.colorWhite));
        mMoreTabLabel.setTextColor(getResources().getColor(R.color.colorWhite));
    }

    @OnClick(R.id.rl_profile_tab)
    public void profileTab() {
        currentTab = PConstants.PROFILE_TAB;
        removeFragment();
        addTabHandler = new Handler();
        addTabHandler.post(addTabRunnable);


        mHomeIconView.setTextColor(getResources().getColor(R.color.colorWhite));
        mHomeTabLabel.setTextColor(getResources().getColor(R.color.colorWhite));

        mOfferIconView.setTextColor(getResources().getColor(R.color.colorWhite));
        mOfferTabLabel.setTextColor(getResources().getColor(R.color.colorWhite));

        mBagIconView.setTextColor(getResources().getColor(R.color.colorWhite));
        mBagTabLabel.setTextColor(getResources().getColor(R.color.colorWhite));

        mMoreIconView.setTextColor(getResources().getColor(R.color.colorAccent));
        mMoreTabLabel.setTextColor(getResources().getColor(R.color.colorAccent));

    }


    private void addSelectedFragmentByTabId(int position) {
        switch (position) {
            case PConstants.CAR_TAB:
                addCarFragment();
                break;
            case PConstants.MAP_TAB:
                addMapTabFragment();
                break;
            case PConstants.NOTIFICATION_TAB:

                addNotificationFragment();
                break;
            case PConstants.PROFILE_TAB:
                addProfileFragment();
                break;
        }
    }

    private void addCarFragment() {
        carsTabFragment = (CarsTabFragment) getSupportFragmentManager().findFragmentByTag(CarsTabFragment.class.getSimpleName());
        if (carsTabFragment == null) {
            carsTabFragment = new CarsTabFragment();
        } else {
        }
        if (carsTabFragment.isAdded()) {
            showFragment(carsTabFragment);
        } else {
            addFragments(carsTabFragment, false, false, true, CarsTabFragment.class.getSimpleName(), R.id.fl_tab_container);
        }
    }

    private void addMapTabFragment() {
        mapsPlotTabFragment = (MapsPlotTabFragment) getSupportFragmentManager().
                findFragmentByTag(MapsPlotTabFragment.class.getSimpleName());
        if (mapsPlotTabFragment == null) {
            mapsPlotTabFragment = new MapsPlotTabFragment();
        }
        if (mapsPlotTabFragment.isAdded()) {
            showFragment(mapsPlotTabFragment);
        } else {
            addFragments(mapsPlotTabFragment, false, false, true,
                    MapsPlotTabFragment.class.getSimpleName(), R.id.fl_tab_container);
        }

    }

    private void addNotificationFragment() {
        notificationTabFragment = (NotificationTabFragment) getSupportFragmentManager().findFragmentByTag(NotificationTabFragment.class.getSimpleName());
        if (notificationTabFragment == null) {
            notificationTabFragment = new NotificationTabFragment();
        }

        if (notificationTabFragment.isAdded()) {
            showFragment(notificationTabFragment);
        } else {
            addFragments(notificationTabFragment, false, false, true,
                    NotificationTabFragment.class.getSimpleName(), R.id.fl_tab_container);
        }
    }

    private void addProfileFragment() {
        profileTabFragment = (ProfileTabFragment) getSupportFragmentManager().findFragmentByTag(ProfileTabFragment.class.getSimpleName());
        if (profileTabFragment == null) {
            profileTabFragment = new ProfileTabFragment();
        }

        if (profileTabFragment.isAdded()) {
            showFragment(profileTabFragment);
        } else {
            addFragments(profileTabFragment, false, false, true,
                    ProfileTabFragment.class.getSimpleName(), R.id.fl_tab_container);
        }
    }


    private void addFragments(Fragment fragment, boolean isAnimate, boolean addToBackStack, boolean isAdd, String fragmentTag, int containerId) {

        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        if (isAnimate)
            fragmentTransaction.setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_left, android.R.anim.slide_in_left, android.R.anim.slide_out_right);
        if (isAdd)
            fragmentTransaction.add(containerId, fragment, fragmentTag);
        else
            fragmentTransaction.replace(containerId, fragment, fragmentTag);
        if (addToBackStack)
            fragmentTransaction.addToBackStack(fragmentTag);
        fragmentTransaction.commit();

    }

    private void showFragment(Fragment fragment) {
        //CCLog.e("");
        hideFragments();
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.show(fragment).commit();

    }

    private void hideFragments() {
        //CCLog.e("");
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        if (getSupportFragmentManager().getFragments() != null)
            for (Fragment fragment : getSupportFragmentManager().getFragments()) {
                if (fragment != null) {
                    fragmentTransaction.hide(fragment);
                }
            }
        fragmentTransaction.commitAllowingStateLoss();
    }

    private void removeFragment() {
        //CCLog.e("");
        try {
            FragmentManager fm = getFragmentManager();
            for (int i = 0; i <= fm.getBackStackEntryCount(); i++) {
                fm.popBackStackImmediate();
            }

            hideFragments();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case Utilities.PERMISSION_REQUEST_CODE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    LocationUtils utils = new LocationUtils();
                    if (utils.checkGpsIsEnable(this)) {

                        LatLng latLng = utils.getlocation(this);




                    } else {
                        AlertDialog.Builder builder = new AlertDialog.Builder(this).setMessage("You need to enable your gps ").setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);

                                startActivity(intent);

                            }
                        }).setCancelable(false);
                        builder.show();
                    }
                }

                break;
        }
    }
}





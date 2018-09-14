package com.clevertap.wuunder_android_test.controllers.utils;

import android.content.Context;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;

/**
 * Created by palashjain on 11/09/18.
 */

public class LocationUtils implements LocationListener {

    private LocationManager locationManager;
    private Location location;
    public boolean netwrokEnabled, gpsEnabled;
    private double longitude = 0.0, latt = 0.0;
    private LatLng latLng;

    public static void showToastLong(Context context, String message) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show();
    }

    public static void showToastShort(Context context, String message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }

    public static void showInfoLog(String message) {
        Log.i("location", message);
    }

    public static void showErrorLog(String message) {
        Log.e("location", message);
    }

    public static void showDebugLog(String message) {
        Log.d("location", message);
    }





    public LatLng getlocation(final Context context) {


        Criteria criteria = new Criteria();
        String provider = locationManager.getBestProvider(criteria, false);
        ContextCompat.checkSelfPermission(context, "android.permission.ACCESS_COARSE_LOCATION");
        ContextCompat.checkSelfPermission(context, "android.permission.ACCESS_FINE_LOCATION");



        if (gpsEnabled) {
            if (location == null) {
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1, 10, this);
                location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                if (locationManager != null) {
                    location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                    if (location != null) {
                        longitude = location.getLongitude();
                        latt = location.getLatitude();
                        latLng = new LatLng(latt, longitude);
                    }
                }
            }
        }
        if (netwrokEnabled) {
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1, 10, this);
            if (locationManager != null) {
                location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                if (location != null) {
                    longitude = location.getLongitude();
                    latt = location.getLatitude();
                    latLng = new LatLng(latt, longitude);
                }
            }
        }

        LocationUtils.showErrorLog(latt + " " + longitude);


        return latLng;

    }

    public double getLongitude() {
        double longitude = 0.0;
        if (latLng != null) {
            longitude = latLng.longitude;
        }

        return longitude;
    }

    public double getLatt() {
        double latitude = 0.0;
        if (latLng != null) {
            latitude = latLng.latitude;
        }

        return latitude;
    }


    public boolean checkGpsIsEnable(final Context context) {
        latLng = new LatLng(latt, longitude);
        locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        gpsEnabled = locationManager.isProviderEnabled(locationManager.GPS_PROVIDER);
        netwrokEnabled = locationManager
                .isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        Criteria criteria = new Criteria();
        String provider = locationManager.getBestProvider(criteria, false);
        ContextCompat.checkSelfPermission(context, "android.permission.ACCESS_COARSE_LOCATION");
        ContextCompat.checkSelfPermission(context, "android.permission.ACCESS_FINE_LOCATION");

        location = locationManager.getLastKnownLocation(provider);

        if (!gpsEnabled && !netwrokEnabled) {
            //no network provider

            return false;
        } else {
            return true;
        }

    }


    @Override
    public void onLocationChanged(Location location) {

    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

    @Override
    public void onProviderEnabled(String s) {

    }

    @Override
    public void onProviderDisabled(String s) {

    }
}
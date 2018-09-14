package com.clevertap.wuunder_android_test.controllers.utils;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by palashjain on 11/09/18.
 */

public class Utilities {
    Context mcontext;
    public final static int PERMISSION_REQUEST_CODE=10111;
    public static ProgressDialog progressDialog;
    public static int stackCount;

    public Utilities(Context context) {
        this.mcontext = context;
    }










    public static void showProgressDialog(Activity activity, String text, Boolean isCancelable) {
        if (activity == null) {
            return;
        }

        try {
            if(progressDialog == null){
                progressDialog = new ProgressDialog(activity);
                progressDialog.setMessage(text);
                progressDialog.setProgressDrawable(new ColorDrawable(Color.parseColor("#FF4081")));
                progressDialog.setCancelable(isCancelable);
                progressDialog.show();
            }
            else {
                progressDialog.setMessage(text);
                progressDialog.setProgressDrawable(new ColorDrawable(Color.parseColor("#FF4081")));
                progressDialog.setCancelable(isCancelable);
                progressDialog.show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void dismissProgressDialog() {

        try {
            if (progressDialog != null && progressDialog.isShowing())
                progressDialog.dismiss();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }
        progressDialog = null;

    }
    public static void callSnackbar(RelativeLayout main_relative_layout, String text, int color) {
        Snackbar snackbar = Snackbar.make(main_relative_layout, text, Snackbar.LENGTH_SHORT);
        View view = snackbar.getView();
        TextView tv = (TextView) view.findViewById(android.support.design.R.id.snackbar_text);
        tv.setTextColor(color);
        snackbar.show();
    }









    public static boolean checkGPSPermission(Context context) {
        if (Build.VERSION.SDK_INT < 23) {
            Toast.makeText(context, "lower version", Toast.LENGTH_SHORT).show();
            return false;
        } else {
            //  Toast.makeText(context, "marshmallow or higher", Toast.LENGTH_SHORT).show();
            if (ContextCompat.checkSelfPermission(context, android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                // Toast.makeText(context, "permission granted", Toast.LENGTH_SHORT).show();
                return true;
            } else {
                // Toast.makeText(context, "no permission granted", Toast.LENGTH_SHORT).show();
                if (ActivityCompat.shouldShowRequestPermissionRationale((Activity) context, Manifest.permission.ACCESS_FINE_LOCATION)) {
                    // Toast.makeText(context, "GPS permission allows us to access location data. Please allow in App Settings for additional functionality.", Toast.LENGTH_LONG).show();
                    showGpsPermissionDialog(context);
                    //  ActivityCompat.requestPermissions((Activity) context, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, LocationActivity.PERMISSION_REQUEST_CODE);
                } else {
                    ActivityCompat.requestPermissions((Activity) context, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, PERMISSION_REQUEST_CODE);
                }
            }
        }
        return false;
    }

    private static void showGpsPermissionDialog(final Context context){
        final AlertDialog.Builder permissionDialog = new AlertDialog.Builder(context);
        permissionDialog.setMessage("Please grant location Permission, so we can show related information");
        permissionDialog.setNegativeButton("Allow", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                ActivityCompat.requestPermissions((Activity) context, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, PERMISSION_REQUEST_CODE);
            }
        });

        permissionDialog.setPositiveButton("Deny", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
                showGpsPermissionDialog(context);
            }
        });

        permissionDialog.show();
    }



}

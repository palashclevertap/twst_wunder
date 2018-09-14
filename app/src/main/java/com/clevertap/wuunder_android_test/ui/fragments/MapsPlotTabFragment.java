package com.clevertap.wuunder_android_test.ui.fragments;


import android.Manifest;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.clevertap.wuunder_android_test.controllers.application.AppController;
import com.clevertap.wuunder_android_test.controllers.utils.PermissionClass;
import com.clevertap.wuunder_android_test.models.MarketMappingModel;
import com.google.android.gms.maps.GoogleMap.InfoWindowAdapter;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.clevertap.wuunder_android_test.R;
import com.clevertap.wuunder_android_test.models.PlacemarkData;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;

import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.AppSettingsDialog;
import pub.devrel.easypermissions.EasyPermissions;

/**
 * Created by palashjain on 11/09/18.
 */

public class MapsPlotTabFragment extends Fragment implements OnMapReadyCallback,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        com.google.android.gms.location.LocationListener, EasyPermissions.PermissionCallbacks {


    final long period = 15000;
    GoogleApiClient mGoogleApiClient;
    LocationRequest mLocationRequest;
    ArrayList<PlacemarkData> getRequestedContactSubModels = new ArrayList<>();

    ArrayList<MarketMappingModel> markerList = new ArrayList<>();
    FrameLayout mMainFrameLayout;
    double lat, longitude;
    SharedPreferences mSharedPreferences;
    boolean onPauseOfActivity = false;
    private GoogleMap mMap;
    private Timer myTimer;
    private boolean isFirstTime = false;

    public MapsPlotTabFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_maps_tab, container, false);






        // Setting a custom info window adapter for the google map

        if (EasyPermissions.hasPermissions(getActivity(), PermissionClass.location)) {
            SupportMapFragment mapFragment = (SupportMapFragment) this.getChildFragmentManager()
                    .findFragmentById(R.id.map);
            mapFragment.getMapAsync(this);
        } else {
            readStorageState();
        }

        return rootView;
    }

    private void apiCallForNearBy() {

        // Tag used to cancel the request
        String tag_json_obj = "json_obj_req";

        String url = "https://s3-us-west-2.amazonaws.com/wunderbucket/locations.json";



        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET,
                url, (JSONObject) null,
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        Log.e("res", response.toString());


                        try {
                            JSONArray placemark = response.getJSONArray("placemarks");


                            for (int i = 0; i <placemark.length() ; i++) {


                                PlacemarkData placemarkData = new PlacemarkData();

                                JSONObject jsonObject = placemark.getJSONObject(i);
                                placemarkData.setAddress(jsonObject.getString("address"));
                                placemarkData.setEngineType(jsonObject.getString("engineType"));
                                placemarkData.setExterior(jsonObject.getString("exterior"));
                                placemarkData.setFuel(jsonObject.getInt("fuel"));
                                placemarkData.setInterior(jsonObject.getString("interior"));
                                placemarkData.setName(jsonObject.getString("name"));
                                placemarkData.setVin(jsonObject.getString("vin"));



                                JSONArray cordinates = jsonObject.getJSONArray("coordinates");

                                placemarkData.setLocLat((Double) cordinates.get(0));
                                Log.e("lat", String.valueOf(placemarkData.getLocLat()));
                                placemarkData.setLocLong((Double) cordinates.get(1));
                                Log.e("lat 2", String.valueOf(placemarkData.getLocLong()));

                                getRequestedContactSubModels.add(placemarkData);

                            }

                            Log.e("size"," " + getRequestedContactSubModels.size());














                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                        setMultipleMarker();


                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.e("Error: " + error.getMessage());
                // hide the progress dialog

            }
        });

// Adding request to request queue
        AppController.getInstance().addToRequestQueue(jsonObjReq, tag_json_obj);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);

        Log.e("","");

        //Initialize Google Play Services
        buildGoogleApiClient();
        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        mMap.setMyLocationEnabled(true);

        apiCallForNearBy();

    }

    protected synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(getActivity())
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
        mGoogleApiClient.connect();
    }

    @Override
    public void onConnected(Bundle bundle) {

        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(1000);
        mLocationRequest.setFastestInterval(1000);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
        if (ContextCompat.checkSelfPermission(getActivity(),
                Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
        }
    }

    @Override
    public void onConnectionSuspended(int i) {
    }


    @Override
    public void onLocationChanged(Location location) {

        MarkerOptions mp = new MarkerOptions();
        mp.position(new LatLng(location.getLatitude(), location.getLongitude()));
        if (!isFirstTime) {
            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(
                    new LatLng(location.getLatitude(), location.getLongitude()), 16));
            isFirstTime = true;
        }

        lat = location.getLatitude();
        longitude = location.getLongitude();


    }

    private void setMultipleMarker() {

        BitmapDescriptor icon = BitmapDescriptorFactory.fromResource(R.drawable.green_pin_arrow);

        mMap.clear();
        for (int i = 0; i < getRequestedContactSubModels.size(); i++) {
            Marker marker;

            marker = mMap.addMarker(new MarkerOptions()
                    .position(new LatLng(getRequestedContactSubModels.get(i).getLocLat(),
                            getRequestedContactSubModels.get(i).getLocLong()))
                    .icon(icon));




                MarketMappingModel model = new MarketMappingModel();
                model.setMarker(marker);
                model.setNearByData(getRequestedContactSubModels.get(i));
                markerList.add(model);
                placePopUpWindow();
            }
        }





    private void placePopUpWindow() {

        mMap.setInfoWindowAdapter(new InfoWindowAdapter() {

            // Use default InfoWindow frame
            @Override
            public View getInfoWindow(Marker arg0) {
                return null;
            }

            // Defines the contents of the InfoWindow
            @Override
            public View getInfoContents(Marker marker) {

                PlacemarkData nearBy = null;
                for (int j = 0; j < markerList.size(); j++) {
                    if (marker.getId().equalsIgnoreCase(markerList.get(j).getMarker().getId())) {
                        nearBy = markerList.get(j).getNearByData();
                        break;
                    }
                }

                // Getting view from the layout file info_window_layout
                View v = getActivity().getLayoutInflater().inflate(R.layout.view_map_pop_up_window, null);

                TextView user_name_text_view = (TextView) v.findViewById(R.id.user_name_text_view);
                TextView address_text_view = (TextView) v.findViewById(R.id.address_text_view);
              TextView  engineType_text_view = (TextView) v.findViewById(R.id.engineType_text_view);
              TextView exterior_text_view = (TextView) v.findViewById(R.id.exterior_text_view);
              TextView interior_text_view = (TextView) v.findViewById(R.id.interior_text_view);

                user_name_text_view.setText(nearBy.getName());
                address_text_view.setText(nearBy.getAddress());
                engineType_text_view.setText(nearBy.getEngineType());
                exterior_text_view.setText(nearBy.getExterior());
                interior_text_view.setText(nearBy.getInterior());


                // Returning the view containing InfoWindow contents
                return v;

            }
        });


    }


    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

    }




    @Override
    public void onPause() {
        super.onPause();
        onPauseOfActivity = true;


    }

    @Override
    public void onResume() {
        super.onResume();
        onPauseOfActivity = false;
    }


    @AfterPermissionGranted(PermissionClass.PERMISSION_LOCATION)
    public void readStorageState() {
        if (EasyPermissions.hasPermissions(getActivity(), PermissionClass.location)) {

            SupportMapFragment mapFragment = (SupportMapFragment) this.getChildFragmentManager()
                    .findFragmentById(R.id.map);
            mapFragment.getMapAsync(this);

        } else {
            EasyPermissions.requestPermissions(this, getString(R.string.rationale_location),
                    PermissionClass.PERMISSION_LOCATION, PermissionClass.location);
        }

    }

    @Override
    public void onPermissionsDenied(int requestCode, List<String> perms) {
        new AppSettingsDialog.Builder(this, getString(R.string.rationale_ask_again))
                .setTitle(getString(R.string.rationale_ask_again))
                .setPositiveButton(getString(R.string.setting)).setNegativeButton(getString(R.string.cancel), null /* click listener */)
                .setRequestCode(PermissionClass.PERMISSION_LOCATION).build().show();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
        Log.e("hello", "granted result");
    }

    @Override
    public void onPermissionsGranted(int requestCode, List<String> perms) {
        Log.e("hello", "granted");

    }

}


package com.clevertap.wuunder_android_test.ui.fragments;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.clevertap.wuunder_android_test.R;
import com.clevertap.wuunder_android_test.controllers.application.AppController;
import com.clevertap.wuunder_android_test.models.PlacemarkData;
import com.clevertap.wuunder_android_test.ui.adapters.CarsTabRecyclerViewAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by palashjain on 11/09/18.
 */

public class CarsTabFragment extends Fragment {


    RecyclerView mCarsTabRecyclerView;
CarsTabRecyclerViewAdapter carsTabRecyclerViewAdapter;


    ArrayList<PlacemarkData> getRequestedContactSubModels = new ArrayList<>();


    public CarsTabFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_car_tab, null);
        init(rootView);


        carsTabRecyclerViewAdapter = new CarsTabRecyclerViewAdapter(getActivity(), getRequestedContactSubModels);
        mCarsTabRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mCarsTabRecyclerView.setAdapter(carsTabRecyclerViewAdapter);



        apiCallForGettingApiData();

        return rootView;
    }

    private void apiCallForGettingApiData() {

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


                        carsTabRecyclerViewAdapter.notifyDataSetChanged();

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

    private void init(View rootView) {

     mCarsTabRecyclerView = (RecyclerView) rootView.findViewById(R.id.cars_data_recycler_view);

    }


    @Override
    public void onResume() {
        super.onResume();

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

}

package com.clevertap.wuunder_android_test.ui.adapters;

import android.app.Activity;
import android.content.SharedPreferences;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.clevertap.wuunder_android_test.R;
import com.clevertap.wuunder_android_test.models.PlacemarkData;

import java.util.ArrayList;

/**
 * Created by palashjain on 12/09/18.
 */

public class CarsTabRecyclerViewAdapter extends RecyclerView.Adapter<CarsTabRecyclerViewAdapter.MyViewHolder> {
    Activity activity;
    ArrayList<PlacemarkData> getRequestedContactSubModels = new ArrayList<>();


    public CarsTabRecyclerViewAdapter(FragmentActivity fragmentActivity, ArrayList<PlacemarkData> getNewRequestedContactSubModels) {
        this.activity = fragmentActivity;
        this.getRequestedContactSubModels = getNewRequestedContactSubModels;

    }

    @Override
    public CarsTabRecyclerViewAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.view_car, parent, false);


        return new CarsTabRecyclerViewAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final CarsTabRecyclerViewAdapter.MyViewHolder holder, final int position) {

holder.coordinates_text_view.setText("Coordinates: " + getRequestedContactSubModels.get(position).getLocLat() + ", " + getRequestedContactSubModels.get(position).getLocLong());
        holder.fuel_text_view.setText("Fuel: " +getRequestedContactSubModels.get(position).getFuel());
        holder.vin_text_view.setText("Vin: " + getRequestedContactSubModels.get(position).getVin());
        holder.interior_text_view.setText("Interior: " + getRequestedContactSubModels.get(position).getInterior());
        holder.exterior_text_view.setText("Exterior: " + getRequestedContactSubModels.get(position).getExterior());
        holder.engineType_text_view.setText("Engine Type: " + getRequestedContactSubModels.get(position).getEngineType());
        holder.address_text_view.setText("Address: "+ getRequestedContactSubModels.get(position).getAddress());
        holder.car_name_text_view.setText("Name: " + getRequestedContactSubModels.get(position).getName());


    }

    @Override
    public int getItemCount() {
        return getRequestedContactSubModels.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView coordinates_text_view,fuel_text_view,vin_text_view,interior_text_view,
                exterior_text_view,engineType_text_view,address_text_view,car_name_text_view;



        public MyViewHolder(View view) {
            super(view);

            coordinates_text_view = (TextView) view.findViewById(R.id.coordinates_text_view);
            fuel_text_view = (TextView) view.findViewById(R.id.fuel_text_view);
            vin_text_view = (TextView) view.findViewById(R.id.vin_text_view);
            interior_text_view = (TextView) view.findViewById(R.id.interior_text_view);
            exterior_text_view = (TextView) view.findViewById(R.id.exterior_text_view);
            engineType_text_view = (TextView) view.findViewById(R.id.engineType_text_view);
            address_text_view = (TextView) view.findViewById(R.id.address_text_view);
            car_name_text_view = (TextView) view.findViewById(R.id.car_name_text_view);


        }
    }
}
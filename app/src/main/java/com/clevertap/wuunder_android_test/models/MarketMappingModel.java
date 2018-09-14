package com.clevertap.wuunder_android_test.models;

import com.clevertap.wuunder_android_test.models.PlacemarkData;
import com.google.android.gms.maps.model.Marker;

import java.io.Serializable;

/**
 * Created by palashjain on 13/09/18.
 */

public class MarketMappingModel implements Serializable {

        Marker marker;
        PlacemarkData nearByData;

public Marker getMarker() {
        return marker;
        }

public void setMarker(Marker marker) {
        this.marker = marker;
        }

    public PlacemarkData getNearByData() {
        return nearByData;
    }

    public void setNearByData(PlacemarkData nearByData) {
        this.nearByData = nearByData;
    }
}

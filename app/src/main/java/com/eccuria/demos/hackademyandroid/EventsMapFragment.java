package com.eccuria.demos.hackademyandroid;


import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.HashMap;


/**
 * A simple {@link Fragment} subclass.
 *
 */
public class EventsMapFragment extends SupportMapFragment {
    private App app;
    private GoogleMap mMap;
    private HashMap<Marker, Place> markers;

    @Override
    public void onCreate(Bundle savedInstance) {
        super.onCreate(savedInstance);
        markers = new HashMap<Marker, Place>();
        app = (App)getActivity().getApplicationContext();
        setUpMapIfNeeded();
    }

    private void setUpMapIfNeeded() {
        if (mMap == null) {
            mMap = getMap();
            if (mMap != null) {
                mMap.setMyLocationEnabled(true);
            }
        }
    }

    public void addMarkersToMap() {
        setUpMapIfNeeded();
        ArrayList<Place> allTheEvents = app.getPlaces();

        mMap.clear();
        markers.clear();

        for (Place place : allTheEvents) {
            LatLng location = new LatLng(place.getLat(), place.getLng());
            MarkerOptions options = new MarkerOptions()
                    .position(location)
                    .title(place.getName())
                    .snippet(place.getAddress());

            Marker marker = mMap.addMarker(options);
            markers.put(marker, place);
        }
    }
}

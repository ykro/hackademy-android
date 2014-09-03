package com.eccuria.demos.hackademyandroid;

import android.app.Activity;
import android.app.Application;
import android.support.v4.app.Fragment;
import android.util.Log;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by ykro on 9/2/14.
 */
public class App extends Application {
    private String TAG;
    private RequestQueue requestQueue;
    private ArrayList<Place> places = new ArrayList<Place>();
    private final static String PLACES_API_KEY = "AIzaSyCOV9-w6ftbimJrzZY3KdeI4qlV-MlpnYU";
    @Override
    public void onCreate() {
        super.onCreate();
        TAG = getPackageName();
        requestQueue = Volley.newRequestQueue(this);
    }

    public void apiRequest(final Activity caller, String query){
        Response.Listener<JSONObject> successListener =
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray results = response.getJSONArray("results");

                            for (int i = 0; i < results.length(); i++) {
                                JSONObject JSONPlace = results.getJSONObject(i);
                                Place newPlace = new Place();
                                newPlace.setName(JSONPlace.getString("name"));
                                newPlace.setAddress(JSONPlace.getString("formatted_address"));
                                newPlace.setLat(JSONPlace.getJSONObject("geometry").getJSONObject("location").getDouble("lat"));
                                newPlace.setLng(JSONPlace.getJSONObject("geometry").getJSONObject("location").getDouble("lng"));

                                places.add(newPlace);
                            }
                        } catch (JSONException ex) {
                            Log.e(TAG,Log.getStackTraceString(ex));
                        }


                        if (caller instanceof MainActivity) {
                            MainActivity a = (MainActivity)caller;
                            Fragment[] fragments = ((MainActivity) caller).getFragments();
                            EventsListFragment list = (EventsListFragment)fragments[0];
                            EventsMapFragment map = (EventsMapFragment)fragments[1];

                            list.showEventsOnList();
                            map.addMarkersToMap();
                        }
                    }
                };

        String url = "https://maps.googleapis.com/maps/api/place/textsearch/json?query=" + query + "&key=" + PLACES_API_KEY;
        JsonObjectRequest request = new JsonObjectRequest(url, null, successListener, null);
        requestQueue.add(request);
    }

    public void clearResults() {
        places.clear();
    }

    public String getTAG() {
        return TAG;
    }

    public ArrayList<Place> getPlaces() {
        return places;
    }
}

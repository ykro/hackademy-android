package com.eccuria.demos.hackademyandroid;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.SimpleAdapter;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;



/**
 * A simple {@link Fragment} subclass.
 *
 */
public class EventsListFragment extends ListFragment {
    private App app;
    private String query;
    private SimpleAdapter adapter;
    private EditText editTextQuery;
    private ProgressBar progressBar;
    private final static String NAME_KEY = "name";
    private final static String ADDRESS_KEY = "address";
    private List<Map<String, String>> events = new ArrayList<Map<String, String>>();

    @Override
    public void onCreate(Bundle savedInstnace) {
        super.onCreate(savedInstnace);

    }

    public void showEventsOnList() {
        ArrayList<Place> allTheEvents = app.getPlaces();
        Map<String, String> event;
        for (Place place : allTheEvents) {
            event = new HashMap<String, String>(2);
            event.put(NAME_KEY, place.getName());
            event.put(ADDRESS_KEY, place.getAddress());
            events.add(event);
        }
        progressBar.setVisibility(View.GONE);
        getListView().setVisibility(View.VISIBLE);
        adapter.notifyDataSetChanged();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        adapter = new SimpleAdapter(inflater.getContext(), events,
                android.R.layout.simple_list_item_2,
                new String[] {NAME_KEY, ADDRESS_KEY },
                new int[] {android.R.id.text1, android.R.id.text2 });

        setListAdapter(adapter);

        View view = inflater.inflate(R.layout.fragment_events_list, container, false);
        progressBar = (ProgressBar) view.findViewById(R.id.progressBar);
        editTextQuery = (EditText)view.findViewById(R.id.etQuery);
        return view;
    }

    public void getQueryText(View v){
        app = (App)getActivity().getApplicationContext();
        try {
            query = URLEncoder.encode(editTextQuery.getText().toString(), "UTF-8");

            editTextQuery.setText("");
            clear();

            progressBar.setVisibility(View.VISIBLE);
            app.apiRequest(getActivity(), query);
        } catch (UnsupportedEncodingException e) {
            Log.e(app.getTAG(), Log.getStackTraceString(e));
        }
    }

    public void clear(){
        app.clearResults();
        events.clear();
        adapter.notifyDataSetChanged();
    }

}

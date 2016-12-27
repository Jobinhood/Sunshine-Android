package com.example.android.sunshine;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

/**
 * A fragment containing a simple view with the forecast for the
 * next 7 days.
 */
public class ForecastFragment extends Fragment {

    private ArrayAdapter<String> mForecastAdapter;

    public ForecastFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        // Inflate the menu; this adds items to the action bar if it is present.
        inflater.inflate(R.menu.forecast_fragment, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_refresh) {
            Log.d("CREATION","Tapped Refresh Button");
            updateWeather(); // Async call to get weather from API
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        mForecastAdapter = new ArrayAdapter<String>(
                getActivity(), // Current context
                R.layout.list_item_forecast, // ID of list item layout
                R.id.list_item_forecast_textview, // Id of textview to populate
                new ArrayList<String>() // Forecast Data
        );

        View rootView = inflater.inflate(R.layout.fragment_main, container, false);

        // Get a reference to the ListView, and attach this adapter to it.
        ListView listView = (ListView) rootView.findViewById(R.id.listview_forecast);
        listView.setAdapter(mForecastAdapter);


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                String forecast = mForecastAdapter.getItem(position);
                // Create a Toast
                //Toast.makeText(getActivity(), forecast, Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getActivity(), DetailActivity.class)
                        .putExtra(Intent.EXTRA_TEXT, forecast);
                startActivity(intent);
            }
        });



        return rootView;
    }

    private void updateWeather() {
        FetchWeatherTask weatherTask = new FetchWeatherTask(getActivity(), mForecastAdapter);
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
        String location = prefs.getString(getString(R.string.pref_location_key),
                getString(R.string.pref_location_default));
        weatherTask.execute(location);
    }

    @Override
    public void onStart() {
        super.onStart();
        updateWeather();
    }

}


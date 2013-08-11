package org.nando.bestPrices;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesClient;
import com.google.android.gms.location.LocationClient;
import com.google.android.gms.location.LocationListener;

import org.nando.bestPrices.adapters.LazyAdapter;
import org.nando.bestPrices.pojo.LocationPojo;
import org.nando.bestPrices.pojo.PetrolStation;
import org.nando.bestPrices.tasks.FetchDataTask;
import org.nando.bestPrices.tasks.LocationTask;

import java.net.URLEncoder;
import java.util.List;

public class ActivityDiesel extends BaseActivityFragment
        implements GooglePlayServicesClient.ConnectionCallbacks,
        GooglePlayServicesClient.OnConnectionFailedListener, LocationListener, View.OnClickListener {

    private TextView errorMessage;
    private ListView listView;

    private EditText editText;

    private PetrolStation pojo;

    private Location location;
    private LocationClient locationClient;





    @Override
    public  View  onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View rootView = inflater.inflate(R.layout.activity_diesel,container,false);

        errorMessage = (TextView) rootView.findViewById(R.id.errorMessage);
        listView = (ListView) rootView.findViewById(R.id.listView);
        editText = (EditText) rootView.findViewById(R.id.editText);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                PetrolStation pojo = (PetrolStation) adapterView.getItemAtPosition(i);
                String address = pojo.address;
                Uri uri = Uri.parse("geo:0,0?q="+address);
                Intent sendIntent = new Intent(Intent.ACTION_VIEW,uri);
                startActivity(sendIntent);
            }
        });
        Button nearestButton = (Button) rootView.findViewById(R.id.nearestPetrolButtonDiesel);
        Button searchButton = (Button) rootView.findViewById(R.id.searchButtonDiesel);
        nearestButton.setOnClickListener(this);
        searchButton.setOnClickListener(this);

        return rootView;
    }

    public void onResume() {
        super.onResume();
        setupLocationClientIfNeeded();
        locationClient.connect();
    }

    public  void onPause() {
        super.onPause();
        locationClient.disconnect();
    }




    public void fetchData(View view) {
        String location = editText.getText().toString();
        if(location == null || location.isEmpty()) {
            String text = "Please make sure to put a postcode or suburb name";
            int duration = Toast.LENGTH_SHORT;
            Toast toast = Toast.makeText(getActivity(),text,duration);
            toast.show();
        }
        else {
            String url = prepareUrl(location);
            errorMessage.setText("");
            listView.setAdapter(null);
            FetchDataTask task = new FetchDataTask(this);
            task.execute(url);
        }
    }

    public void pressNearMeButton(View view) {
        location = locationClient.getLastLocation();
        LocationTask task = new LocationTask(this);
        task.execute(location);

    }

    private String prepareUrl(String location) {
        try {
            location = URLEncoder.encode(location,"UTF-8");

        } catch (Exception e) {
            e.printStackTrace();
        }
        return "http://fuelprices.racq.com.au/fuelSearch/performSearch.aspx?_=1&Location="+location+"&FuelType=Diesel&SearchType=Advanced&MapName=";
    }

    public void handleErrorMessage(String errMsg) {
        errorMessage.setBackgroundColor(Color.RED);
        errorMessage.setText(errMsg);
        errorMessage.setIncludeFontPadding(true);
    }

    public void placeResults(List<PetrolStation> list) {
        if(list != null) {
            LazyAdapter adapter = new LazyAdapter(getActivity(),android.R.layout.simple_list_item_1,list);
            listView.setAdapter(adapter);

        }
    }

    public void placeLocationResults(LocationPojo pojo) {
        String url = prepareUrl(pojo.postcode);
        editText.setText(pojo.locality);
        errorMessage.setText("");
        listView.setAdapter(null);
        FetchDataTask task = new FetchDataTask(this);
        task.execute(url);

    }

    private void setupLocationClientIfNeeded() {
        if(locationClient == null) {
            locationClient = new LocationClient(getActivity(),this,this);
        }
    }

    @Override
    public void onConnected(Bundle bundle) {

    }

    @Override
    public void onDisconnected() {

    }

    @Override
    public void onLocationChanged(Location location) {

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

    }

    @Override
    public void onClick(View view) {
        if(R.id.nearestPetrolButtonDiesel == view.getId()) {
            this.pressNearMeButton(view);
        }
        if(R.id.searchButtonDiesel == view.getId()) {
            this.fetchData(view);
        }

    }
}

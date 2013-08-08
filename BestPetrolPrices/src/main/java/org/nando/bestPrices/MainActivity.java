package org.nando.bestPrices;

import android.content.Intent;
import android.graphics.Color;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesClient;
import com.google.android.gms.location.LocationClient;
import com.google.android.gms.location.LocationListener;

import org.nando.bestPrices.adapters.LazyAdapter;
import org.nando.bestPrices.adapters.StableArrayAdapter;
import org.nando.bestPrices.pojo.LocationPojo;
import org.nando.bestPrices.pojo.PetrolStation;
import org.nando.bestPrices.tasks.FetchDataTask;
import org.nando.bestPrices.tasks.LocationTask;


import java.net.URLEncoder;
import java.util.List;

public class MainActivity extends Activity implements GooglePlayServicesClient.ConnectionCallbacks,
        GooglePlayServicesClient.OnConnectionFailedListener, LocationListener {

    private TextView errorMessage;
    private ListView listView;

    private EditText editText;

    private PetrolStation pojo;

    private Location location;
    private LocationClient locationClient;






    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        errorMessage = (TextView) findViewById(R.id.errorMessage);
        listView = (ListView) findViewById(R.id.listView);
        editText = (EditText) findViewById(R.id.editText);
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
    }

    protected void onResume() {
        super.onResume();
        setupLocationClientIfNeeded();
        locationClient.connect();
    }

    protected void onPause() {
        super.onPause();
        locationClient.disconnect();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    public void fetchData(View view) {
        String location = editText.getText().toString();
        String url = prepareUrl(location);
        errorMessage.setText("");
        listView.setAdapter(null);
        FetchDataTask task = new FetchDataTask(this);
        task.execute(url);
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
        return "http://fuelprices.racq.com.au/fuelSearch/performSearch.aspx?_=1&Location="+location+"&FuelType=Unleaded&SearchType=Advanced&MapName=";
    }

    public void handleErrorMessage(String errMsg) {
        errorMessage.setBackgroundColor(Color.RED);
        errorMessage.setText(errMsg);
        errorMessage.setIncludeFontPadding(true);
    }

    public void placeResults(List<PetrolStation> list) {
        if(list != null) {
            LazyAdapter adapter = new LazyAdapter(this,android.R.layout.simple_list_item_1,list);
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
            locationClient = new LocationClient(getApplicationContext(),this,this);
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

}

package org.nando.bestPrices;

import android.graphics.Color;
import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.webkit.WebView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import org.nando.bestPrices.adapters.StableArrayAdapter;
import org.nando.bestPrices.pojo.PetrolStation;
import org.nando.bestPrices.tasks.FetchDataTask;

import java.util.List;

public class MainActivity extends Activity {

    private TextView errorMessage;
    private ListView listView;

    private EditText editText;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        errorMessage = (TextView) findViewById(R.id.errorMessage);
        listView = (ListView) findViewById(R.id.listView);
        editText = (EditText) findViewById(R.id.editText);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    public void fetchData(View view) {
        String location = editText.getText().toString();
        String url = "http://fuelprices.racq.com.au/fuelSearch/performSearch.aspx?_=1&Location="+location+"&FuelType=Unleaded&SearchType=Advanced&MapName=";
        errorMessage.setText("");
        listView.setAdapter(null);
        FetchDataTask task = new FetchDataTask(this);
        task.execute(url);


    }

    public void handleErrorMessage(String errMsg) {
        errorMessage.setBackgroundColor(Color.RED);
        errorMessage.setText(errMsg);
        errorMessage.setIncludeFontPadding(true);
    }

    public void placeResults(List<PetrolStation> list) {
        if(list != null) {
            StableArrayAdapter adapter = new StableArrayAdapter(this,android.R.layout.simple_list_item_1,list);
            listView.setAdapter(adapter);

        }
    }
    
}

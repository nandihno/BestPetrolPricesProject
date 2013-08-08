package org.nando.bestPrices.tasks;

import android.os.AsyncTask;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.nando.bestPrices.MainActivity;
import org.nando.bestPrices.pojo.PetrolStation;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by fernandoMac on 9/07/13.
 */
public class FetchDataTask extends AsyncTask<String,Void,List<PetrolStation>> {

    private MainActivity mainActivity;

    public FetchDataTask(MainActivity anActivity) {
        mainActivity = anActivity;
    }

    protected List<PetrolStation> doInBackground(String... strings) {
        List<PetrolStation> list = new ArrayList<PetrolStation>();
        try {
            HttpClient client = new DefaultHttpClient();
            HttpGet get = new HttpGet(strings[0]);
            HttpResponse response = client.execute(get);
            if(response.getStatusLine().getStatusCode() == 200) {
                HttpEntity entity = response.getEntity();
                String html = EntityUtils.toString(entity);
                Document doc = Jsoup.parse(html);
                Element element = doc.getElementById("fuelPriceResults");
                Elements brandsClass = element.select("td.brandName");
                Elements priceClass = element.select("td.price");
                Elements addressClass = element.select("td.address");
                Elements colectedClass = element.select("td.collected");


                for(int i =0; i < brandsClass.size(); i++) {
                    PetrolStation petrolStation = new PetrolStation();
                    Element brand = brandsClass.get(i);
                    Element address = addressClass.get(i);
                    Element price = priceClass.get(i);
                    Element collected = colectedClass.get(i);
                    petrolStation.brand = brand.text();
                    petrolStation.address = address.text();
                    petrolStation.collectionTime = collected.text();
                    petrolStation.price = price.text();
                    list.add(petrolStation);
                }

            }

        } catch(Exception e) {
            mainActivity.handleErrorMessage(e.getMessage());

        }
        return list;
    }

    protected void onPostExecute(List<PetrolStation> list) {
        if(list != null) {
            this.mainActivity.placeResults(list);
        }
        else {
            mainActivity.handleErrorMessage("Please enter data");
        }
    }

}

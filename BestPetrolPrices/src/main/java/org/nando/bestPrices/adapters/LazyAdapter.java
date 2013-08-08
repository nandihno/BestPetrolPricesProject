package org.nando.bestPrices.adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.TextView;

import org.nando.bestPrices.R;
import org.nando.bestPrices.pojo.PetrolStation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by fernandoMac on 8/08/13.
 */
public class LazyAdapter extends ArrayAdapter<PetrolStation> {


    private List<PetrolStation> data = new ArrayList<PetrolStation>();
    private static LayoutInflater inflater = null;
    HashMap<PetrolStation,Integer> map = new HashMap<PetrolStation, Integer>();


    public LazyAdapter(Context context, int textViewResourceId,List<PetrolStation> list) {
        super(context,textViewResourceId,list);
        data = list;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        for(int i =0; i  < list.size(); i++) {
            map.put(list.get(i),i);
        }

    }


    @Override
    public int getCount() {
       return data.size();
    }

    public boolean hasStableIds() {
        return true;

    }

    @Override
    public long getItemId(int position) {
        PetrolStation pojo = getItem(position);
        return map.get(pojo);
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        View vi = view;
        if(view == null) {
            vi = inflater.inflate(R.layout.list_row,null);
        }
        TextView brand = (TextView) vi.findViewById(R.id.brand);
        TextView price = (TextView) vi.findViewById(R.id.price);
        TextView address = (TextView) vi.findViewById(R.id.address);
        TextView collectionTime = (TextView) vi.findViewById(R.id.collectionTime);
        PetrolStation petrolStation = new PetrolStation();
        petrolStation = data.get(position);
        brand.setText(petrolStation.brand);
        price.setText(petrolStation.price);
        address.setText(petrolStation.address);
        collectionTime.setText(petrolStation.collectionTime);
        return vi;
    }
}

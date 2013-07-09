package org.nando.bestPrices.adapters;

import android.content.Context;
import android.widget.ArrayAdapter;

import org.nando.bestPrices.pojo.PetrolStation;

import java.util.HashMap;
import java.util.List;

/**
 * Created by fernandoMac on 9/07/13.
 */
public class StableArrayAdapter extends ArrayAdapter<PetrolStation> {
    HashMap<PetrolStation,Integer> map = new HashMap<PetrolStation, Integer>();

    public StableArrayAdapter(Context context,int textViewResourceId, List<PetrolStation> list) {
        super(context,textViewResourceId,list);
        for(int i =0; i  < list.size(); i++) {
            map.put(list.get(i),i);
        }
    }

    public long getItemId(int position) {
        PetrolStation pojo = getItem(position);
        return map.get(pojo);
    }

    public boolean hasStableIds() {
        return true;

    }
}

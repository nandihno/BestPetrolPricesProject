package org.nando.bestPrices;

import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;

import org.nando.bestPrices.pojo.LocationPojo;
import org.nando.bestPrices.pojo.PetrolStation;

import java.util.List;

/**
 * Created by fernandoMac on 11/08/13.
 */
public abstract class BaseActivityFragment extends Fragment {

    public abstract void placeResults(List<PetrolStation> list);

    public abstract void handleErrorMessage(String errMsg);

    public abstract void placeLocationResults(LocationPojo pojo);



}

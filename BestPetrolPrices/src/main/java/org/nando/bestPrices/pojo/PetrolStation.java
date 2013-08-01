package org.nando.bestPrices.pojo;

/**
 * Created by fernandoMac on 9/07/13.
 */
public class PetrolStation {

    public String brand;
    public String price;
    public String address;
    public String collectionTime;
    public boolean errorMessage;

    @Override
    public String toString() {
        return brand +"\n Price: "+price+"\n Time:"+collectionTime;
    }
}

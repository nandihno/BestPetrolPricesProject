package org.nando.bestPrices.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import org.nando.bestPrices.Activity95;
import org.nando.bestPrices.ActivityDiesel;
import org.nando.bestPrices.ActivityE10;
import org.nando.bestPrices.ActivityUnleaded;

import java.util.Locale;

/**
 * Created by fernandoMac on 11/08/13.
 */
public class SectionsPagerAdapter extends FragmentPagerAdapter {

    public SectionsPagerAdapter(FragmentManager fm) {
        super(fm);
    }


    @Override
    public Fragment getItem(int i) {
        switch (i) {
            case 0:
                return new ActivityUnleaded();
            case 1:
                return new ActivityE10();
            case 2:
                return new Activity95();
            case 3:
                return new ActivityDiesel();
        }
        return null;
    }

    @Override
    public int getCount() {
        return 4;
    }

    public CharSequence getPageTitle(int position) {
        Locale locale = Locale.getDefault();
        switch (position) {
            case 0:
                return "Unleaded".toUpperCase(locale);
            case 1:
                return "E-10".toUpperCase(locale);
            case 2:
                return "Ron 95".toUpperCase(locale);
            case 3:
                return  "Diesel".toUpperCase(locale);
        }
        return null;
    }
}

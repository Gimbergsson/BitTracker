package com.free.dennisg.bittrackr;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import com.free.dennisg.bittrackr.fragments.EmptyFragment;

public class MainFragmentPagerAdapter extends FragmentPagerAdapter {

    private static int NUM_ITEMS = 4;

    public MainFragmentPagerAdapter(FragmentManager fragmentManager) {
        super(fragmentManager);
    }

    @Override
    public int getCount() {
        return NUM_ITEMS;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return EmptyFragment.newInstance(0);
            case 1:
                return EmptyFragment.newInstance(1);
            case 2:
                return EmptyFragment.newInstance(2);
            case 3:
                return EmptyFragment.newInstance(3);
            default:
                return null;
        }
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return "Page " + position;
    }
}

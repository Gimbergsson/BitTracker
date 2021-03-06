package com.free.dennisg.bittrackr.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.free.dennisg.bittrackr.R;

public class LookupBlockFragment extends Fragment {

    public static LookupBlockFragment newInstance(int index) {
        LookupBlockFragment fragment = new LookupBlockFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("empty", index);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.i("**Info**", "onCreateView");

        View view = inflater.inflate(R.layout.empty_fragment, container, false);
        return view;
    }
}

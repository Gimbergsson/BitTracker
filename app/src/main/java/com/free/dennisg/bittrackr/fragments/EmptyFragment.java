package com.free.dennisg.bittrackr.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.free.dennisg.bittrackr.R;

public class EmptyFragment extends Fragment {

    public static EmptyFragment newInstance(int index) {
        EmptyFragment fragment = new EmptyFragment();
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

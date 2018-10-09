package com.as1124.googledoc.connectivity;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class BlankFragment extends Fragment {

    public static final String FRAME_TAG = "hahaha";

    public static BlankFragment getInstance(FragmentManager manager) {
        BlankFragment one;
        if (manager.findFragmentByTag(FRAME_TAG) != null) {
            one = (BlankFragment) manager.findFragmentByTag(FRAME_TAG);
        } else {
            one = new BlankFragment();
//            manager.beginTransaction().add(R.id.test_frame_container, one, FRAME_TAG).commit();
        }
        return one;
    }

    public BlankFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        android.util.Log.i("1111111", "Fragment onCreated!!!");
        this.setRetainInstance(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View topView = inflater.inflate(R.layout.fragment_blank, container, false);
        android.util.Log.i("1111111", "Fragment OnCreateView!!!");
        return topView;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        android.util.Log.i("11111", "Frame Configuration Changed!!");
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

}

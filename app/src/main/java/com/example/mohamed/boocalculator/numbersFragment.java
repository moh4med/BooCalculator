package com.example.mohamed.boocalculator;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class numbersFragment extends Fragment {
    private static String TAG=numbersFragment.class.getSimpleName();
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Log.e(TAG,"calling on attach");
    }

    public numbersFragment() {
        // Required empty public constructor
        Log.e(TAG,"calling constructor");
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.e(TAG,"calling on start");
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.e(TAG,"calling on Create");
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.e(TAG,"calling onActivityCreated");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        Log.e(TAG,"calling oncreateView");
        return inflater.inflate(R.layout.fragment_numbers, container, false);
    }


}

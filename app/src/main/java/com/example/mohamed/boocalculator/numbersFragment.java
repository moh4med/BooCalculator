package com.example.mohamed.boocalculator;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class numbersFragment extends Fragment implements View.OnClickListener {
    private static String TAG=numbersFragment.class.getSimpleName();
    numberListener MnumberListener;
    public interface numberListener{
        void OnClick(String s);
    }
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.MnumberListener=(numberListener) context;
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
        View view = inflater.inflate(R.layout.fragment_number, container, false);
        Button button0 = (Button) view.findViewById(R.id.button0);
        button0.setOnClickListener(this);
        Button button1 = (Button) view.findViewById(R.id.button1);
        button1.setOnClickListener(this);
        Button button2 = (Button) view.findViewById(R.id.button2);
        button2.setOnClickListener(this);
        Button button3 = (Button) view.findViewById(R.id.button3);
        button3.setOnClickListener(this);
        Button button4 = (Button) view.findViewById(R.id.button4);
        button4.setOnClickListener(this);
        Button button5 = (Button) view.findViewById(R.id.button5);
        button5.setOnClickListener(this);
        Button button6 = (Button) view.findViewById(R.id.button6);
        button6.setOnClickListener(this);
        Button button7 = (Button) view.findViewById(R.id.button7);
        button7.setOnClickListener(this);
        Button button8 = (Button) view.findViewById(R.id.button8);
        button8.setOnClickListener(this);
        Button button9 = (Button) view.findViewById(R.id.button9);
        button9.setOnClickListener(this);
        Button buttondot = (Button) view.findViewById(R.id.buttondot);
        buttondot.setOnClickListener(this);
        return view;
    }


    @Override
    public void onClick(View v) {
        MnumberListener.OnClick(((Button)v).getText().toString());
    }
}

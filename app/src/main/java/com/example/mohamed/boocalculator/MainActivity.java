package com.example.mohamed.boocalculator;

import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.Pair;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.mariuszgromada.math.mxparser.*;

import java.util.ArrayList;
import java.util.Random;
import java.util.Stack;

public class MainActivity extends AppCompatActivity
        implements numbersFragment.numberListener,HistoryAdapter.operationviewOnClickListener{

    private static final String INPUTSTRINGTAG ="INPUTSTRINGTAG" ;
    private static final String HISTORYTAG ="HISTORYTAGTAG" ;
    private static String TAG = MainActivity.class.getSimpleName();
    EditText inputEText;
    TextView outputTv;
    Button buttondel;
    ArrayList<operationanswerdata> OperationHistory;
    RecyclerView HistoryRecycleView;
    DrawerLayout mdrawerlayout;
    HistoryAdapter historyAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        OperationHistory=new  ArrayList<operationanswerdata>();
        outputTv = (TextView) findViewById(R.id.outputtv);
        inputEText = (EditText) findViewById(R.id.inputtv);
        buttondel=(Button)findViewById(R.id.buttondel);
        mdrawerlayout=(DrawerLayout)findViewById(R.id.my_drawer_layout);
        HistoryRecycleView=(RecyclerView) findViewById(R.id.historyrecycleview);
        if(savedInstanceState!=null){               //restore the session data
            String s=savedInstanceState.getString(INPUTSTRINGTAG);
            inputEText.setText(s);
            OperationHistory=savedInstanceState.getParcelableArrayList(HISTORYTAG);
        }
        historyAdapter=new HistoryAdapter(OperationHistory,this);
        HistoryRecycleView.setAdapter(historyAdapter);
        HistoryRecycleView.setLayoutManager(new LinearLayoutManager(this));
        inputEText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                View currentFocus = getCurrentFocus();
                if (currentFocus != null) {
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(currentFocus.getWindowToken(), 0);
                }
            }
        });
        buttondel.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                inputEText.setText("");
                return true;
            }
        });
        processOperation();
        Log.e(TAG, "ONCREATE");
    }

    @Override
    public void onNumClick(String s) {
        changeinput(s,-1);
        processOperation();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putString(INPUTSTRINGTAG,inputEText.getText().toString());
        outState.putParcelableArrayList(HISTORYTAG,OperationHistory);
        super.onSaveInstanceState(outState);
    }

    public void opClick(View v) {
        String s = ((Button) v).getText().toString();
        String input = inputEText.getText().toString();
        if (s.equals(getString(R.string.buttondeltext))) {        // delete the char from input not added delete many
            int chartodelete = inputEText.getSelectionStart() - 1;    //get start of the selection &delete it
            if (chartodelete >= 0) {
                input = input.substring(0, chartodelete) + input.substring(chartodelete + 1);
                inputEText.setText(input);
                inputEText.setSelection(chartodelete);
            }
        } else if (s.equals(getString(R.string.buttoneqtext))) {
            processOperation();
            String output = outputTv.getText().toString();
            OperationHistory.add(new operationanswerdata(input,output));
            this.historyAdapter.notifyDataSetChanged();
            inputEText.setText(output);
            inputEText.setSelection(output.length());
            outputTv.setText("");
        } else if (s.equals(getString(R.string.buttonDEGtext))) {  //not implemented yet
            //todo implement deg/rad
        } else if (s.equals(getString(R.string.buttonrandom))) {
            Random random = new Random();
            int n = random.nextInt(234234);       // till now any random
            changeinput(String.valueOf(n),-1);
        } else {
            addoperationtoinput(s);
        }
        input = inputEText.getText().toString();
        processOperation();
        inputEText.requestFocus();
    }

    private void addoperationtoinput(String s) {    //how clicked button should appear on the screen
        String newinput="";
        int offset=-1;
        if (s.equals(getString(R.string.buttonmodtext))) {
            newinput="#";
        } else if (s.equals(getString(R.string.buttonevaltext)) || s.equals(getString(R.string.buttonPitext))) {
            newinput=s;
        } else if (s.equals(getString(R.string.buttonfactorialtext))) {
            newinput="!";
        } else if (s.equals(getString(R.string.buttonsqrttext))) {
            newinput="()^(1/2)";
            offset=1;
        } else if (s.equals(getString(R.string.buttongenroottext))) {
            newinput="()^(1/())";
            offset=1;
        } else if (s.equals(getString(R.string.buttonsquaretext))) {
            newinput="^2";
        } else if (s.equals(getString(R.string.buttoncubetext))) {
            newinput="^3";
        } else if (s.equals(getString(R.string.buttoninversetext))) {
            newinput="(1/())";
            offset=4;
        } else if (s.equals(getString(R.string.buttongenpowertext))) {
            newinput="^()";
            offset=2;
        } else if (s.equals(getString(R.string.buttonbinomialtext))) {
            newinput="C(,)";
            offset=2;
        } else if (s.equals(getString(R.string.buttonplustext)) ||
                s.equals(getString(R.string.buttonminustext)) ||
                s.equals(getString(R.string.buttonmultext)) ||
                s.equals(getString(R.string.buttondivtext)) ||
                s.equals(getString(R.string.buttonrightparantext)) ||
                s.equals(getString(R.string.buttonleftparantext))) {       //+-*/()
            newinput=s;
        } else {                     //for log&trigonometric function
            newinput=s+"()";
            offset=s.length()+1;
        }
        changeinput(newinput,offset);
    }

    private void processOperation() {
        String s = inputEText.getText().toString();
        double f = evaluate(s);
        if (s.length() == 0) {
            outputTv.setText("");
        } else {
            OutputAns(f);
        }
    }
    private void changeinput(String s,int cursoroffsettochange){
        String input=inputEText.getText().toString();
        if(cursoroffsettochange<0){                     //when cursorpositionnotdetermined or messy value
            cursoroffsettochange=s.length();
        }
        int cursorposition=inputEText.getSelectionStart();
        String newInput=input.substring(0,cursorposition)+s+input.substring(cursorposition);
        inputEText.setText(newInput);
        inputEText.setSelection(cursorposition+cursoroffsettochange);
    }
    private void OutputAns(double f) {
        outputTv.setText("" + f);
    }

    private double evaluate(String s) {
        Expression ex = new Expression(s);
        return ex.calculate();
    }

    @Override
    public void operationviewOnClick(String operation) {
        inputEText.setText(operation);
        processOperation();
        mdrawerlayout.closeDrawers();
    }
}

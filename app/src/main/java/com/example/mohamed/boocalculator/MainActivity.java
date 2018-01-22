package com.example.mohamed.boocalculator;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import org.mariuszgromada.math.mxparser.*;

public class MainActivity extends AppCompatActivity implements numbersFragment.numberListener {
    private static String TAG=MainActivity.class.getSimpleName();
    TextView inputTv;
    TextView outputTv;
    boolean operationdisabled =true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        outputTv=(TextView)findViewById(R.id.outputtv);
        inputTv=(TextView)findViewById(R.id.inputtv);
        Log.e(TAG,"ONCREATE");
    }

    @Override
    public void OnClick(String s) {
        String input=inputTv.getText().toString();
        if(s=="."&&input.contains(".")){
            //pass
        }else{
            operationdisabled =false;
            inputTv.append(s);
            input=inputTv.getText().toString();
            processOperation(input);
        }

    }
    public void opClick(View v) {
        String s=((Button)v).getText().toString();
        String input=inputTv.getText().toString();
        if(s.equals(getString(R.string.buttondeltext))){    //delete last char
            if(input.length()>0){
                input=input.substring(0,input.length()-1);
                inputTv.setText(input);
                if(operationdisabled){
                    operationdisabled =false;
                }
                if(inputTv.length()==0){        //if clear screen we can't do operation
                    operationdisabled =true;
                }
            }
        }else if(s.equals(getString(R.string.buttoneqtext))){   //evaluate input&replace it with its value
            processOperation(input);
            inputTv.setText(outputTv.getText());
            outputTv.setText("");
            return;
        }else{                                                  //another operation
            if(!operationdisabled){
                inputTv.append(s);
                operationdisabled =true;
            }
        }
        processOperation(input);
    }
    private void processOperation(String s){
        double f=evaluate(s);
        while(s.length()>0&&(new Double(f).isNaN())){
            s=s.substring(0,s.length()-1);
            f=evaluate(s);
        }
        if(s.length()==0){
            outputTv.setText("");
        }else {
            OutputAns(f);
        }
    }
    private void OutputAns(double f) {
        outputTv.setText(""+f);
    }

    private double evaluate(String s) {
        Expression ex=new Expression(s);
        return ex.calculate();
    }
}

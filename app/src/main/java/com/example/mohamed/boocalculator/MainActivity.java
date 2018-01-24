package com.example.mohamed.boocalculator;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.Pair;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.mariuszgromada.math.mxparser.*;

import java.util.Random;
import java.util.Stack;

public class MainActivity extends AppCompatActivity implements numbersFragment.numberListener {
    private static String TAG = MainActivity.class.getSimpleName();
    EditText inputEText;
    TextView outputTv;
    Stack<Pair<String, String>> OperationHistory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        OperationHistory=new  Stack<Pair<String, String>>();
        outputTv = (TextView) findViewById(R.id.outputtv);
        inputEText = (EditText) findViewById(R.id.inputtv);
        inputEText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                View currentFocus = getCurrentFocus();
                if (currentFocus != null) {
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(currentFocus.getWindowToken(), 0);
                }
                inputEText.requestFocus();
            }
        });
        Log.e(TAG, "ONCREATE");
    }

    @Override
    public void onNumClick(String s) {
        inputEText.append(s);
        processOperation();
    }

    public void opClick(View v) {
        String s = ((Button) v).getText().toString();
        String input = inputEText.getText().toString();
        if (s.equals(getString(R.string.buttondeltext))) {        // delete the char from input not added delete many
            int chartodelete = inputEText.getSelectionStart() - 1;    //get start of the selection &delete it
            if (chartodelete >= 0) {
                Log.e(TAG, "" + chartodelete);
                input = input.substring(0, chartodelete) + input.substring(chartodelete + 1);
                inputEText.setText(input);
                inputEText.setSelection(chartodelete);
            }
        } else if (s.equals(getString(R.string.buttoneqtext))) {
            processOperation();
            String output = outputTv.getText().toString();
            OperationHistory.push(new Pair <String, String>(input,output));
            inputEText.setText(output);
            inputEText.setSelection(output.length());
            outputTv.setText("");
        } else if (s.equals(getString(R.string.buttonDEGtext))) {  //not implemented yet
            //pass
        } else if (s.equals(getString(R.string.buttonrandom))) {
            Random random = new Random();
            int n = random.nextInt(234234);       // till now any random
            inputEText.append("" + n);
        } else {
            addoperationtoinput(s);
        }
        input = inputEText.getText().toString();
        processOperation();
        inputEText.requestFocus();
    }

    private void addoperationtoinput(String s) {    //how clicked button should appear on the screen
        if (s.equals(getString(R.string.buttonmodtext))) {
            inputEText.append("#");
        } else if (s.equals(getString(R.string.buttonevaltext)) || s.equals(getString(R.string.buttonPitext))) {
            inputEText.append(s);
        } else if (s.equals(getString(R.string.buttonfactorialtext))) {
            inputEText.append("!");
        } else if (s.equals(getString(R.string.buttonsqrttext))) {
            inputEText.append("()^(1/2)");
            inputEText.setSelection(inputEText.getText().length() - 7);       //set cursor to point to between()
        } else if (s.equals(getString(R.string.buttongenroottext))) {
            inputEText.append("()^(1/())");
            inputEText.setSelection(inputEText.getText().length() - 8);
        } else if (s.equals(getString(R.string.buttonsquaretext))) {
            inputEText.append("^2");
        } else if (s.equals(getString(R.string.buttoncubetext))) {
            inputEText.append("^3");
        } else if (s.equals(getString(R.string.buttoninversetext))) {
            inputEText.append("1/()");
            inputEText.setSelection(inputEText.getText().length() - 1);
        } else if (s.equals(getString(R.string.buttongenpowertext))) {
            inputEText.append("^()");
            inputEText.setSelection(inputEText.getText().length() - 1);
        } else if (s.equals(getString(R.string.buttonbinomialtext))) {
            inputEText.append("C(,)");
            inputEText.setSelection(inputEText.getText().length() - 2);
        } else if (s.equals(getString(R.string.buttonplustext)) ||
                s.equals(getString(R.string.buttonminustext)) ||
                s.equals(getString(R.string.buttonmultext)) ||
                s.equals(getString(R.string.buttondivtext)) ||
                s.equals(getString(R.string.buttonrightparantext)) ||
                s.equals(getString(R.string.buttonleftparantext))) {       //+-*/()
            inputEText.append(s);
        } else {                     //for log&trigonometric function
            inputEText.append(s + "()");
            inputEText.setSelection(inputEText.getText().length() - 1);
        }
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

    private void OutputAns(double f) {
        outputTv.setText("" + f);
    }

    private double evaluate(String s) {
        Expression ex = new Expression(s);
        return ex.calculate();
    }
}

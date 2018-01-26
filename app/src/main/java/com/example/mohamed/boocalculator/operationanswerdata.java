package com.example.mohamed.boocalculator;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by CompuCity on 1/26/2018.
 */
public class operationanswerdata implements Parcelable {
    String operation;
    String answer;
    operationanswerdata(String op,String ans){
        this.answer=ans;
        this.operation=op;
    }
    operationanswerdata(Parcel in){
        String[] data = new String[2];
        in.readStringArray(data);
        this.operation = data[0];
        this.answer = data[1];
    }
    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeStringArray(new String[] {this.operation, this.answer});
    }
    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public operationanswerdata createFromParcel(Parcel in) {
            return new operationanswerdata(in);
        }

        public operationanswerdata[] newArray(int size) {
            return new operationanswerdata[size];
        }
    };
}

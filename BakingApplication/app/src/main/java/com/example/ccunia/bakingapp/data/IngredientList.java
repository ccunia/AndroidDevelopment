package com.example.ccunia.bakingapp.data;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by CCunia on 6/26/2018.
 */

public class IngredientList implements Parcelable {

    private final String TAG = IngredientList.class.getSimpleName();
    float quantity;
    String measure;
    String ingredient;

    public IngredientList(){}

    protected IngredientList(Parcel in) {
        this.quantity = in.readFloat();
        this.measure = in.readString();
        this.ingredient = in.readString();
    }

    public static final Creator<IngredientList> CREATOR = new Creator<IngredientList>() {
        @Override
        public IngredientList createFromParcel(Parcel in) {
            return new IngredientList(in);
        }

        @Override
        public IngredientList[] newArray(int size) {
            return new IngredientList[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeFloat(this.quantity);
        dest.writeString(this.measure);
        dest.writeString(this.ingredient);
    }

    public Float getQuantity() {
        return quantity;
    }
    public void setQuantity(Float  qty) {
        this.quantity = qty;
    }
    public String getMeasure(){
        return measure;
    }
    public void setMeasure(String msr){
        this.measure = msr;
    }
    public String getIngredient(){
        return ingredient;
    }
    public void setIngredient(String ingdnt){
        this.ingredient = ingdnt;
    }
}

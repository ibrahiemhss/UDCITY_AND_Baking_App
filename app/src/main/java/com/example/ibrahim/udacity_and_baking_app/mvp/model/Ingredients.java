package com.example.ibrahim.udacity_and_baking_app.mvp.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 *
 * Created by ibrahim on 29/05/18.
 */

@SuppressWarnings("unused")
public class Ingredients implements Parcelable {
    public static final Creator<Ingredients> CREATOR = new Creator<Ingredients>() {
        @Override
        public Ingredients createFromParcel(Parcel in) {
            return new Ingredients(in);
        }

        @Override
        public Ingredients[] newArray(int size) {
            return new Ingredients[size];
        }
    };
    private float quantity;
    private String measure;
    private String ingredient;

    public Ingredients(Parcel in) {
        quantity = in.readFloat();
        measure = in.readString();
        ingredient = in.readString();
    }

    public Ingredients() {

    }

    public float getQuantity() {
        return quantity;
    }

    public void setQuantity(float quantity) {
        this.quantity = quantity;
    }

    public String getMeasure() {
        return measure;
    }

    public void setMeasure(String measure) {
        this.measure = measure;
    }

    public String getIngredient() {
        return ingredient;
    }

    public void setIngredient(String ingredient) {
        this.ingredient = ingredient;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeFloat(quantity);
        parcel.writeString(measure);
        parcel.writeString(ingredient);
    }
}

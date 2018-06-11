package com.example.ibrahim.udacity_and_baking_app.mvp.model;

//TODO (62) create class Bake

import android.os.Parcel;
import android.os.Parcelable;

/**
 *
 * Created by ibrahim on 24/05/18.
 */

@SuppressWarnings("unused")
public class Bake implements Parcelable{
    //TODO (63) create variables as in json and setter & getter
    private long id;
    private String name;
    private String image;
    private BakingResponseIngredients[] ingredientsArrayList;

    private Bake(Parcel in) {
        id = in.readLong();
        name = in.readString();
        image = in.readString();
    }

    public static final Creator<Bake> CREATOR = new Creator<Bake>() {
        @Override
        public Bake createFromParcel(Parcel in) {
            return new Bake(in);
        }

        @Override
        public Bake[] newArray(int size) {
            return new Bake[size];
        }
    };

    public Bake() {

    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BakingResponseIngredients[] getIngredientsArrayList() {
        return ingredientsArrayList;
    }

    public void setIngredientsArrayList(BakingResponseIngredients[] ingredientsArrayList) {
        this.ingredientsArrayList = ingredientsArrayList;
    }


    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeLong(id);
        parcel.writeString(name);
        parcel.writeString(image);
    }
}

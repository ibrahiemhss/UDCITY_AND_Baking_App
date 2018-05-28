package com.example.ibrahim.udacity_and_baking_app.mvp.model;

import java.util.ArrayList;
 //TODO (62) create class Bake
/**
 *
 * Created by ibrahim on 24/05/18.
 */

public class Bake {
    //TODO (63) create variables as in json and setter & getter
    private long id;
    private String name;


    private String image;
    private ArrayList<String> ingredientsArrayList;

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

    public ArrayList<String> getIngredientsArrayList() {
        return ingredientsArrayList;
    }

    public void setIngredientsArrayList(ArrayList<String> ingredientsArrayList) {
        this.ingredientsArrayList = ingredientsArrayList;
    }


    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}

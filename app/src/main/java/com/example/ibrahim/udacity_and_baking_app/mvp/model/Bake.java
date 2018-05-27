package com.example.ibrahim.udacity_and_baking_app.mvp.model;

import java.util.ArrayList;

/**
 *
 * Created by ibrahim on 24/05/18.
 */

public class Bake {

    private long id;
    private String name;
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
}

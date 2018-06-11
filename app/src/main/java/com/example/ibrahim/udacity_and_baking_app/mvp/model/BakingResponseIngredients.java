package com.example.ibrahim.udacity_and_baking_app.mvp.model;

@SuppressWarnings("unused")
public class BakingResponseIngredients  {
    private float quantity;
    private String measure;
    private String ingredient;

    public float getQuantity() {
        return this.quantity;
    }

    public void setQuantity(float quantity) {
        this.quantity = quantity;
    }

    public String getMeasure() {
        return this.measure;
    }

    public void setMeasure(String measure) {
        this.measure = measure;
    }

    public String getIngredient() {
        return this.ingredient;
    }

    public void setIngredient(String ingredient) {
        this.ingredient = ingredient;
    }
}

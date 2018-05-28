package com.example.ibrahim.udacity_and_baking_app.mvp.model;

 //TODO (12)
public class BakingResponse {
    private String image;
    private float servings;
    private String name;
    private BakingResponseIngredients[] ingredients;
    private long id;
    private BakingResponseSteps[] steps;

    public String getImage() {
        return this.image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public float getServings() {
        return this.servings;
    }

    public void setServings(int servings) {
        this.servings = servings;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BakingResponseIngredients[] getIngredients() {
        return this.ingredients;
    }

    public void setIngredients(BakingResponseIngredients[] ingredients) {
        this.ingredients = ingredients;
    }

    public long getId() {
        return this.id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public BakingResponseSteps[] getSteps() {
        return this.steps;
    }

    public void setSteps(BakingResponseSteps[] steps) {
        this.steps = steps;
    }
}

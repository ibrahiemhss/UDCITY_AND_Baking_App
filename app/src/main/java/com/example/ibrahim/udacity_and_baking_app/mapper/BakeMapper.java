package com.example.ibrahim.udacity_and_baking_app.mapper;

import android.content.ContentValues;
import android.content.Context;
import android.net.Uri;
import android.util.Log;

import com.example.ibrahim.udacity_and_baking_app.data.Contract;
import com.example.ibrahim.udacity_and_baking_app.mvp.model.Bake;
import com.example.ibrahim.udacity_and_baking_app.mvp.model.BakingResponse;
import com.example.ibrahim.udacity_and_baking_app.mvp.model.BakingResponseIngredients;
import com.example.ibrahim.udacity_and_baking_app.mvp.model.BakingResponseSteps;
import com.example.ibrahim.udacity_and_baking_app.mvp.model.Ingredients;
import com.example.ibrahim.udacity_and_baking_app.mvp.model.Steps;
import com.example.ibrahim.udacity_and_baking_app.utilities.getBakeUtils;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

/**
 * Created by ibrahim on 24/05/18.
 * BakeMapper maps the response object into model objects
 */

public class BakeMapper {
    @SuppressWarnings("unused")
    @Inject
    public BakeMapper() {
    }

    /*get all values from json that come inside BakingResponse
    * and add inside object of Bake*/
    public ArrayList<Bake> mapBake(List<BakingResponse> responses) {
        //create object bakeList ArrayList from class Bake
        ArrayList<Bake> bakeList = new ArrayList<>();
        //get value from list responses
        if (responses != null) {
            for (BakingResponse bakingResponse : responses) {
                Bake myBake = new Bake();
                //pass value from list responses to myBake
                myBake.setId(bakingResponse.getId());
                myBake.setName(bakingResponse.getName());
                myBake.setImage(bakingResponse.getImage());
                myBake.setIngredientsArrayList(bakingResponse.getIngredients());
                //add all value from myBake to bakeList
                bakeList.add(myBake);

            }


        }
        return bakeList;


    }

    /*after get all values from Observable and save inside  BakingResponse
     * json have getIngredients  array inside BakingResponse
       * this method get every getIngredients array by its position inside  BakingResponse
       */

    public ArrayList<Ingredients> getIngredientsList(Context mContext, List<BakingResponse> responses, int position) {
        ArrayList<Ingredients> ingredientsList = new ArrayList<>();
        if (responses != null) {
            for (BakingResponse bakingResponse : responses) {
                //call ingredientsList method to get ingredients by its position
                ingredientsList = ingredientsList(mContext, responses.get(position).getIngredients());
            }
        }
        return ingredientsList;
    }

    /*
   * this method get every Steps array by its position inside  BakingResponse
   */
    public ArrayList<Steps> getStepsList(List<BakingResponse> responses, int position) {
        ArrayList<Steps> stepsList = new ArrayList<>();
        if (responses != null) {
            for (BakingResponse bakingResponse : responses) {
                //call stepsList method to get Steps by its position
                stepsList = stepsList(responses.get(position).getSteps());
            }
        }
        return stepsList;


    }


    private ArrayList<Ingredients> ingredientsList(Context mContext, BakingResponseIngredients[] bakingResponseIngredients) {
        ArrayList<Ingredients> bakeIngredientsList = new ArrayList<>();
        if (bakingResponseIngredients != null) {

            if (getBakeUtils.geIngredients(mContext).size() > 0) {

                Uri uri = Contract.BakeEntry.PATH_INGREDIENTS_URI;
                uri = uri.buildUpon().appendPath(null).build();
                mContext.getContentResolver().delete(uri, null, null);
                if (uri != null) {
                    Log.d("contentResolver delete", "delete success");
                }
            }
            for (BakingResponseIngredients bakingResponseIngredients1 : bakingResponseIngredients) {
                Ingredients myBakeIngredients = new Ingredients();
                myBakeIngredients.setIngredient(bakingResponseIngredients1.getIngredient());
                myBakeIngredients.setQuantity(bakingResponseIngredients1.getQuantity());
                myBakeIngredients.setMeasure(bakingResponseIngredients1.getMeasure());
                bakeIngredientsList.add(myBakeIngredients);
                ContentValues values = new ContentValues();
                values.put(Contract.BakeEntry.COL_QUANTITY, bakingResponseIngredients1.getQuantity());
                values.put(Contract.BakeEntry.COL_MEASURE, bakingResponseIngredients1.getMeasure());
                values.put(Contract.BakeEntry.COL_INGREDIENT, bakingResponseIngredients1.getIngredient());


                final Uri uriInsert = mContext.getContentResolver().insert(Contract.BakeEntry.PATH_INGREDIENTS_URI, values);
                if (uriInsert != null) {
                    Log.d("contentResolver insert", "first added success");

                }

            }
        }
        return bakeIngredientsList;

    }

    private ArrayList<Steps> stepsList(BakingResponseSteps[] bakingResponseSteps) {
        ArrayList<Steps> stepsList1 = new ArrayList<>();
        if (bakingResponseSteps != null) {
            for (BakingResponseSteps bakingResponseStep : bakingResponseSteps) {
                Steps mySteps = new Steps();
                mySteps.setDescription(bakingResponseStep.getDescription());
                mySteps.setShortDescription(bakingResponseStep.getShortDescription());
                mySteps.setId(bakingResponseStep.getId());
                mySteps.setThumbnailURL(bakingResponseStep.getThumbnailURL());
                mySteps.setVideoURL(bakingResponseStep.getVideoURL());
                stepsList1.add(mySteps);
            }
        }
        return stepsList1;
    }


}

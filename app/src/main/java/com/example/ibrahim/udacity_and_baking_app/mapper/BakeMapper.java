package com.example.ibrahim.udacity_and_baking_app.mapper;

import android.annotation.SuppressLint;

import com.example.ibrahim.udacity_and_baking_app.mvp.model.Bake;
import com.example.ibrahim.udacity_and_baking_app.mvp.model.BakeIngredients;
import com.example.ibrahim.udacity_and_baking_app.mvp.model.BakingResponse;
import com.example.ibrahim.udacity_and_baking_app.mvp.model.BakingResponseIngredients;
import com.example.ibrahim.udacity_and_baking_app.mvp.model.BakingResponseSteps;
import com.example.ibrahim.udacity_and_baking_app.mvp.model.Steps;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;

import javax.inject.Inject;

import timber.log.Timber;
 //TODO (64) create class BakeMapper
/**
 *
 * Created by ibrahim on 24/05/18.
 * BakeMapper maps the response object into model objects
 */

public class BakeMapper {
    @Inject
    public BakeMapper() {
    }
    public List<Bake> mapBake(List<BakingResponse> responses){
        //create object bakeList ArrayList from class Bake
        List<Bake> bakeList=new ArrayList<>();
        //get value from list responses
        if (responses != null) {
            for (BakingResponse bakingResponse: responses) {
                Bake myBake=new Bake();
                //pass value from list responses to myBake
                myBake.setId(bakingResponse.getId());
                myBake.setName(bakingResponse.getName());
                myBake.setImage(bakingResponse.getImage());
                myBake.setIngredientsArrayList(bakingResponse.getIngredients());
                //add all value from myBake to bakeList
                bakeList.add(myBake);
              //  bakingIngredients(bakingResponse.getIngredients());

              //  stepsList(bakingResponse.getSteps());

            }
            getIngredientsList(responses,1);


        }
        return bakeList;


    }

    public List<BakeIngredients> IngredientsList(BakingResponseIngredients[] bakingResponseIngredients){
        List<BakeIngredients> bakeIngredientsList=new ArrayList<>();
        if (bakingResponseIngredients != null) {

            for (BakingResponseIngredients bakingResponseIngredients1 : bakingResponseIngredients) {
                BakeIngredients myBakeIngredients=new BakeIngredients();
                myBakeIngredients.setIngredient(bakingResponseIngredients1.getIngredient());
                myBakeIngredients.setQuantity(bakingResponseIngredients1.getQuantity());
                myBakeIngredients.setMeasure(bakingResponseIngredients1.getMeasure());
                bakeIngredientsList.add(myBakeIngredients);
            }
        }
        return  bakeIngredientsList;

    }
public List<Steps> stepsList(BakingResponseSteps [] bakingResponseSteps){
        List <Steps> stepsList1 =new ArrayList<>();
        if(bakingResponseSteps !=null){
            for (BakingResponseSteps bakingResponseStep : bakingResponseSteps) {
                Steps mySteps=new Steps();
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


    public List<BakeIngredients> getIngredientsList(List<BakingResponse> responses,int position){
        //create object bakeList ArrayList from class Bake
        List<BakeIngredients> bakeList=new ArrayList<>();
        //get value from list responses
        if (responses != null) {
            for (BakingResponse bakingResponse: responses) {
                bakeList=  IngredientsList(responses.get(position).getIngredients());
            }

        }
        return bakeList;


    }
    public List<Bake> getStepsList(List<BakingResponse> responses,int position){
        //create object bakeList ArrayList from class Bake
        List<Bake> bakeList=new ArrayList<>();
        //get value from list responses
        if (responses != null) {
            for (BakingResponse bakingResponse: responses) {
                Bake myBake=new Bake();
                //pass value from list responses to myBake
                // bakingIngredients(bakingResponse.getIngredients());
                stepsList(responses.get(position).getSteps());


            }

        }
        return bakeList;


    }
}

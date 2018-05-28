package com.example.ibrahim.udacity_and_baking_app.mapper;

import android.annotation.SuppressLint;

import com.example.ibrahim.udacity_and_baking_app.mvp.model.Bake;
import com.example.ibrahim.udacity_and_baking_app.mvp.model.BakingResponse;

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
                //add all value from myBake to bakeList
                bakeList.add(myBake);
            }

        }
        return bakeList;


    }
}

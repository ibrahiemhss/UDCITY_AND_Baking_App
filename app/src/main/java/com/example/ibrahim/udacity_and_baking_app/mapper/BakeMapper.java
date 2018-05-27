package com.example.ibrahim.udacity_and_baking_app.mapper;

import com.example.ibrahim.udacity_and_baking_app.mvp.model.Bake;
import com.example.ibrahim.udacity_and_baking_app.mvp.model.BakingResponse;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

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
        List<Bake> bakeList=new ArrayList<>();

        if (responses != null) {
            for (BakingResponse add: responses) {
                Bake myBake=new Bake();

                myBake.setId(add.getId());
                myBake.setName(add.getName());

                bakeList.add(myBake);
            }

        }
        return bakeList;


    }
}

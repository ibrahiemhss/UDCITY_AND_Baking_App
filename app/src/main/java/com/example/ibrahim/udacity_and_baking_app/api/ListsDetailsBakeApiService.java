package com.example.ibrahim.udacity_and_baking_app.api;

import com.example.ibrahim.udacity_and_baking_app.mvp.model.BakingResponse;

import java.util.List;

import retrofit2.http.GET;
import rx.Observable;

/**
 *
 * Created by ibrahim on 31/05/18.
 */

public interface ListsDetailsBakeApiService {
    @GET("/topher/2017/May/59121517_baking/baking.json")

    Observable<List<BakingResponse>> getIngredients();

}

package com.example.ibrahim.udacity_and_baking_app.api;

import com.example.ibrahim.udacity_and_baking_app.mvp.model.BakingResponse;

import java.util.List;

import retrofit2.http.GET;
import rx.Observable;

/**
 * Created by ibrahim on 22/05/18.
 */
public interface BakeApiService {

    //using get from retrofit by specify second part of url
    @GET("/topher/2017/May/59121517_baking/baking.json")
    /*using observable from RX specify her
     <type> the type of object from BakingResponse
     */
    Observable<List<BakingResponse>> getBake();

}

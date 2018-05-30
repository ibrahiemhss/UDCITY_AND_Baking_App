package com.example.ibrahim.udacity_and_baking_app.mvp.presenter;

import android.content.Context;
import android.util.Log;

import com.example.ibrahim.udacity_and_baking_app.R;
import com.example.ibrahim.udacity_and_baking_app.api.BakeApiService;
import com.example.ibrahim.udacity_and_baking_app.base.BasePresenter;
import com.example.ibrahim.udacity_and_baking_app.mapper.BakeMapper;
import com.example.ibrahim.udacity_and_baking_app.mvp.model.Bake;
import com.example.ibrahim.udacity_and_baking_app.mvp.model.BakeIngredients;
import com.example.ibrahim.udacity_and_baking_app.mvp.model.BakingResponse;
import com.example.ibrahim.udacity_and_baking_app.mvp.view.DetailsView;
import com.example.ibrahim.udacity_and_baking_app.mvp.view.MainView;

import java.util.List;

import javax.inject.Inject;

import rx.Observable;
import rx.Observer;

/**
 * Created by ibrahim on 30/05/18.
 */

public class DetailsPresenter extends BasePresenter<DetailsView> implements Observer<List<BakingResponse>> {

    private Context mContext;
    int position=0;
    @Inject
    protected BakeApiService mApiService;
    /*inject BakeMapper */
    @Inject protected BakeMapper mBakeMapper;
    @Inject
    public DetailsPresenter(Context context) {
        mContext=context;
    }


    //pass information from this method
    public void getBakeIngredients() {
        Observable<List<BakingResponse>> bakePresenterObservable= mApiService.getBake();
        //TODO (61) implement observer to BakeResponse List<BakingResponse>
        subscribe(bakePresenterObservable,this);
        //position=pos;
    }

    @Override
    public void onCompleted() {

    }

    @Override
    public void onError(Throwable e) {
        Log.v("errorJsondd",String.valueOf(e.getMessage()));


    }



    @Override
    public void onNext(List<BakingResponse>  bakingResponses) {

        List<BakeIngredients> getIngredientsFrombakeList= mBakeMapper.getIngredientsList(bakingResponses,3);
        getView().onBakeLoaded(getIngredientsFrombakeList);

    }
}

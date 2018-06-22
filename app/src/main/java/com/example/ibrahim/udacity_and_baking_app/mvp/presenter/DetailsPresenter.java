package com.example.ibrahim.udacity_and_baking_app.mvp.presenter;

import com.example.ibrahim.udacity_and_baking_app.api.BakeApiService;
import com.example.ibrahim.udacity_and_baking_app.base.BasePresenter;
import com.example.ibrahim.udacity_and_baking_app.mapper.BakeMapper;
import com.example.ibrahim.udacity_and_baking_app.mvp.model.BakingResponse;
import com.example.ibrahim.udacity_and_baking_app.mvp.model.Ingredients;
import com.example.ibrahim.udacity_and_baking_app.mvp.model.Steps;
import com.example.ibrahim.udacity_and_baking_app.mvp.view.DetailsView;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import rx.Observable;
import rx.Observer;

/**
 *
 * Created by ibrahim on 30/05/18.
 */

@SuppressWarnings({"WeakerAccess", "unused"})
public class DetailsPresenter extends BasePresenter<DetailsView> implements Observer<List<BakingResponse>> {
    @Inject
    protected BakeApiService mApiService;
    /*inject BakeMapper */
    @Inject
    protected BakeMapper mBakeMapper;
    private int position;

    @Inject
    public DetailsPresenter() {
    }

    /*get value position from intent */
    private int getPosition() {
        return position;
    }

    /*set position */
    private void setPosition(int position) {
        this.position = position;
    }

    /**
     * @param position that com from intent from DetailsActivity
     *                 pass information to DetailsActivity from this method
     */
    public void getDetails(int position) {
        /*pass <List<BakingResponse>> to get all lists of
         BakingResponseSteps [] & BakingResponseIngredients[]
        by their position that come from intent from DetailsActivity
        */
        Observable<List<BakingResponse>> listObservable = mApiService.getBake();
        subscribeDetailsLists(listObservable, this);
        //set the value of position from the value that come from intent
        setPosition(position);
    }

    @Override
    public void onCompleted() {

    }

    @Override
    public void onError(Throwable e) {

    }

    @Override
    public void onNext(List<BakingResponse> responseList) {

        /*get list of Ingredients for bakingResponse by its position that come from intent*/
        ArrayList<Ingredients> ingredientsList = mBakeMapper.getIngredientsList(responseList, getPosition());
        /*pass the value of ingredients List into
        DetailsActivity by implements onIngredientsLoaded from DetailsView interface*/
        getView().onIngredientsLoaded(ingredientsList);

         /*get list of Steps for bakingResponse by its position that come from intent*/
        ArrayList<Steps> stepsList = mBakeMapper.getStepsList(responseList, getPosition());
        /*pass the value of Steps List into
        DetailsActivity by implements onStepsLoaded from DetailsView interface*/
        getView().onStepsLoaded(stepsList);

    }


}

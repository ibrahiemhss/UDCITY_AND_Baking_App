package com.example.ibrahim.udacity_and_baking_app.mvp.presenter;

//TODO (36) create  class MainPresenter

import android.content.Context;
import android.util.Log;

import com.example.ibrahim.udacity_and_baking_app.R;
import com.example.ibrahim.udacity_and_baking_app.api.BakeApiService;
import com.example.ibrahim.udacity_and_baking_app.base.BasePresenter;
import com.example.ibrahim.udacity_and_baking_app.data.Storage;
import com.example.ibrahim.udacity_and_baking_app.mapper.BakeMapper;
import com.example.ibrahim.udacity_and_baking_app.mvp.model.Bake;
import com.example.ibrahim.udacity_and_baking_app.mvp.model.BakingResponse;
import com.example.ibrahim.udacity_and_baking_app.mvp.view.MainView;
import com.example.ibrahim.udacity_and_baking_app.utilities.getBakeUtils;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import rx.Observable;
import rx.Observer;

/**
 * Created by ibrahim on 22/05/18.
 * get injected View that MainView
 */
//TODO (37) extends BasePresenter
//TODO (42) <MainView>
public class MainPresenter extends BasePresenter<MainView> implements Observer<List<BakingResponse>> {
    private final Context mContext;
    @SuppressWarnings("WeakerAccess")
    @Inject
    protected Storage mStorage;
    @Inject
    protected BakeApiService mApiService;
    /*inject BakeMapper */
    @SuppressWarnings("WeakerAccess")
    @Inject
    protected BakeMapper mBakeMapper;
    /*TODO (59) inject APIservice
     presenter what we can do we have provide
     the view then can also provide the APIservice not
     */
    private ArrayList<Bake> mBakeList;

    /*TODO (49) Inject  MainPresenter
    because Bakepresenter injected add Inject annotation
      * to easily inject dagger will automatically generate
      * for */
    @Inject
    public MainPresenter(Context context) {
        mContext = context;
    }

    //pass information from this method
    @SuppressWarnings("UnusedReturnValue")
    public ArrayList<Bake> geBaking() {
        //pass a message
        getView().onShowDialog(mContext.getApplicationContext().getResources().getString(R.string.loading));
  /*TODO (60) get Observable from BakingResponse
   because we have provided it up in the parent class
     */
        Observable<List<BakingResponse>> bakePresenterObservable = mApiService.getBake();
        //TODO (61) implement observer to BakeResponse List<BakingResponse>
        subscribeBakingResponse(bakePresenterObservable, this);
        return mBakeList;
    }

    @Override
    public void onCompleted() {
        //TODO (70) pass a message after completed
        getView().onShowToast();
        getView().onHideDialog(mContext.getApplicationContext().getResources().getString(R.string.loading_completed));

    }

    @Override
    public void onError(Throwable e) {
        //TODO (71) pass a message with error
        getView().onShowToast();
        getView().onHideDialog(mContext.getApplicationContext().getResources().getString(R.string.loading_error) + String.valueOf(e.getMessage()));
        Log.v("errorJson", String.valueOf(e.getMessage()));


    }

    @Override
    public void onNext(List<BakingResponse> bakingResponses) {

    /*
    * TODO (65) get list of BakeMap with  bakingResponse that will get list of bake*/
        mBakeList = mBakeMapper.mapBake(mContext, bakingResponses);
   /*pass bakeList into getView that come from basepresenter
    in MainPresenter that have specified
     as mainview so view will get MainView  */
        getView().onBakeLoaded(mBakeList);

    }

    /*list of photos to pass with List of Bake in BakeAdapter*/
    public Integer[] getImgId() {

        return new Integer[]{
                R.drawable.nutella_pie,
                R.drawable.brownies,
                R.drawable.yellow_cake,
                R.drawable.cheesecake
        };
    }

    public void getBakeFromDatabase() {
        ArrayList<Bake> bakes = getBakeUtils.getBake(mContext);
        //getView().onClearItems();
        getView().onBakeLoaded(bakes);
    }


}

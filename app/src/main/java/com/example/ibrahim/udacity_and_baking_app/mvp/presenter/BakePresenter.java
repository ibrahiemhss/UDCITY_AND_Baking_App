package com.example.ibrahim.udacity_and_baking_app.mvp.presenter;


import android.util.Log;

import com.example.ibrahim.udacity_and_baking_app.api.BakeApiService;
import com.example.ibrahim.udacity_and_baking_app.base.BasePresenter;
import com.example.ibrahim.udacity_and_baking_app.mapper.BakeMapper;
import com.example.ibrahim.udacity_and_baking_app.mvp.model.Bake;
import com.example.ibrahim.udacity_and_baking_app.mvp.model.BakingResponse;
import com.example.ibrahim.udacity_and_baking_app.mvp.view.MainView;

import java.util.List;

import javax.inject.Inject;

import rx.Observable;
import rx.Observer;

/**
 * Created by ibrahim on 22/05/18.
 * get injected View that MainView
 */
public class BakePresenter extends BasePresenter<MainView> implements Observer<List<BakingResponse>> {

  /*presenter what we can do we have provide
   the view then can also provide the APIservice not
    */
  @Inject
  protected BakeApiService mApiService;
  /*inject BakeMapper */
  @Inject protected BakeMapper mBakeMapper;
  /*because Bakepresenter injected add Inject annotation
    * to easily inject dagger will automatically generate
    * for */
  @Inject
  public BakePresenter() {
  }

  //pass information from this method
  public void geBaking() {
    //pass a message
    getView().onShowDialog("loading...");

    /*get Observable from BakingResponse because
    we have provided it up in the parent class
     */
    Observable<List<BakingResponse>> bakePresenterObservable= mApiService.getBake();
    //after get this must implement observer to BakeResponse object
    subscribe(bakePresenterObservable,this);
  }

  @Override
  public void onCompleted() {
    //pass a message after completed
    getView().onShowToast();
    getView().onHideDialog("Loading completed...");

  }

  @Override
  public void onError(Throwable e) {
    //pass a message with error
    getView().onShowToast();
    getView().onHideDialog("Loading error....."+String.valueOf(e.getMessage()));
    Log.v("errorJson",String.valueOf(e.getMessage()));


  }

  @Override
  public void onNext(List<BakingResponse> bakingResponses) {

    /*
    * get list of BakeMap with  bakingResponse that will get list of bake*/
    List<Bake> bakeList= mBakeMapper.mapBake(bakingResponses);
   /*pass bakeList into getView that come from basepresenter
    in BakePresenter that have specified
     as mainview so view will get MainView  */
    getView().onBakeLoaded(bakeList);

  }


}

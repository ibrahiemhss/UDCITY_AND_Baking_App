package com.example.ibrahim.udacity_and_baking_app.mvp.presenter;

 //TODO (36) create  class BakePresenter

import android.content.Context;
import android.util.Log;

import com.example.ibrahim.udacity_and_baking_app.R;
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
//TODO (37) extends BasePresenter
//TODO (42) <MainView>
public class BakePresenter extends BasePresenter<MainView> implements Observer<List<BakingResponse>> {
  /*TODO (59) inject APIservice
   presenter what we can do we have provide
   the view then can also provide the APIservice not
   */
  private Context mContext;
  @Inject
  protected BakeApiService mApiService;
  /*inject BakeMapper */
  @Inject protected BakeMapper mBakeMapper;
  /*TODO (49) Inject  BakePresenter
  because Bakepresenter injected add Inject annotation
    * to easily inject dagger will automatically generate
    * for */
  @Inject
  public BakePresenter(Context context) {
    mContext=context;
  }

  //pass information from this method
  public void geBaking() {
    //pass a message
    getView().onShowDialog(mContext.getApplicationContext().getResources().getString(R.string.loading));
  /*TODO (60) get Observable from BakingResponse
   because we have provided it up in the parent class
     */
    Observable<List<BakingResponse>> bakePresenterObservable= mApiService.getBake();
    //TODO (61) implement observer to BakeResponse List<BakingResponse>
    subscribe(bakePresenterObservable,this);
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
    getView().onHideDialog(mContext.getApplicationContext().getResources().getString(R.string.loading_error)+String.valueOf(e.getMessage()));
    Log.v("errorJson",String.valueOf(e.getMessage()));


  }

  @Override
  public void onNext(List<BakingResponse> bakingResponses) {

    /*
    * TODO (65) get list of BakeMap with  bakingResponse that will get list of bake*/
    List<Bake> bakeList= mBakeMapper.mapBake(bakingResponses);
   /*pass bakeList into getView that come from basepresenter
    in BakePresenter that have specified
     as mainview so view will get MainView  */
    getView().onBakeLoaded(bakeList);

  }


}

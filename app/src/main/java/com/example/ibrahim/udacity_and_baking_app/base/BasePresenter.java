package com.example.ibrahim.udacity_and_baking_app.base;

import com.example.ibrahim.udacity_and_baking_app.mvp.model.BakingResponse;
import com.example.ibrahim.udacity_and_baking_app.mvp.view.BaseView;

import java.util.List;

import javax.inject.Inject;

import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/*
 *
 * Created by ibrahim on 22/05/18.
 */

/**
* this class subscribing everything should be
* protected as far is utility base class
 * V extends  BaseView In the original this is interfase
 * */
public class BasePresenter <V extends BaseView>{

    //inject View
    @Inject protected  V mView;
    /*
    * using this can communicate with a view that already injected
    * */

    protected V getView() {
        return mView;
    }

    /**
    * protected void subscribe and pass observer
     * @param  observable save RX type T
     * @param observer the observer is part of the RX type T
     */
    protected  void  subscribe (Observable<List<BakingResponse>> observable, Observer<List<BakingResponse>> observer){

        /*
         * See {@link <a href="https://android.jlelse.eu/rxjava-schedulers-what-when-and-how-to-use-it-6cfc27293add">HTTP/1.1 documentation</a>}.
         *Schedulers are one of the main components in RxJava They are responsible for
         * performing operations of Observable on different threads
         */
        observable.subscribeOn(Schedulers.newThread())
         /*	emits a single item into a Single that emits that item
           toObservable	Observable	converts a Single into an Observable
            that emits the item*/
                .toSingle()
                .observeOn(AndroidSchedulers.mainThread())
                /*
                *subscribe observer and whatever doing then will come to
                 * that in details later anything might be required in here */
                .subscribe(observer);
    }

}

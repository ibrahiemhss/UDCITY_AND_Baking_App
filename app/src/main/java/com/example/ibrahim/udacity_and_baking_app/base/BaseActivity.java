package com.example.ibrahim.udacity_and_baking_app.base;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.CallSuper;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.example.ibrahim.udacity_and_baking_app.application.BakeApplication;
import com.example.ibrahim.udacity_and_baking_app.di.components.ApplicationComponent;

import butterknife.ButterKnife;


/*
 *
 * Created by ibrahim on 22/05/18.
 */

/**
 * the parent Activity for all activities in app
 */
public abstract class BaseActivity extends AppCompatActivity {

    //ProgressDialog to view any message wanted in the child activity
    private ProgressDialog mProgressDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
          /*this getContentView  will inherit by the
           child classes to pass their layout */
        setContentView(getContentView());
        ButterKnife.bind(this);
        onViewReady(savedInstanceState, getIntent());
    }

    /*
       noted CallSuper to force the call super for the child class so anyone
        inheriting this class he will has to do  @override super thought
        */
    @CallSuper
    //to be used by child activities
    protected void onViewReady(Bundle savedInstanceState, Intent intent) {
        resolveDaggerDependency();
        //To be used by child activities
    }

    @Override
    protected void onDestroy() {
        ButterKnife.bind(this).unbind();
        super.onDestroy();
    }

    /*method will get Dependency inside child activity*/
    protected void resolveDaggerDependency() {
    }


    /**using generally in child activities to display status of coming data
     * using here
     *{@linkplain com.example.ibrahim.udacity_and_baking_app.mvp.view.MainView}
     *{@linkplain com.example.ibrahim.udacity_and_baking_app.mvp.presenter}
     *{@linkplain  com.example.ibrahim.udacity_and_baking_app.modules.home}
     * */
    protected void showDialog(String message) {
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(this);
            mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            mProgressDialog.setCancelable(true);
        }
        mProgressDialog.setMessage(message);
        mProgressDialog.show();
    }

    protected void hideDialog() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        }
    }
    //getApplicationComponent to pass the ApplicationComponent

    protected ApplicationComponent getApplicationComponent() {
        return ((BakeApplication) getApplication()).getApplicationComponent();
    }

    /*
    * this method is mandatory to force every activity to implement it */
    protected abstract int getContentView();
}

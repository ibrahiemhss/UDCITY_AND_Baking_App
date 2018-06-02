package com.example.ibrahim.udacity_and_baking_app.base;

import android.app.ProgressDialog;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;

import com.example.ibrahim.udacity_and_baking_app.R;
import com.example.ibrahim.udacity_and_baking_app.application.BakeApplication;
import com.example.ibrahim.udacity_and_baking_app.di.components.ApplicationComponent;
import com.example.ibrahim.udacity_and_baking_app.mvp.view.StepsView;

/**
 * Created by ibrahim on 01/06/18.
 */

public abstract class BaseFragments extends Fragment {

   private ProgressDialog progressDialog;

    public abstract StepsView getSeps();

    public void showLoading() {
        showLoading(null);
    }

    @Override
    public void onResume() {
        super.onResume();
        if (getSeps() != null) {
            getSeps().resume();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (getSeps() != null) {
            getSeps().pause();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (getSeps() != null) {
            getSeps().destroy();
        }
    }
    protected void resolveDaggerDependency() {}

    public void showLoading(String msg) {
        if (progressDialog == null) {
            progressDialog = new ProgressDialog(getActivity());
        }
        progressDialog.setCancelable(false);
        String message = msg != null ? msg : getResources().getString(R.string.txt_please_wait);
        progressDialog.setMessage(message);
        progressDialog.show();
    }

    public void hideLoading() {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
    }

    public void showErrorMessage(String message) {
        showSnackbar(true, message);
    }

    private void showSnackbar(boolean isError, String message) {
        Snackbar make = Snackbar.make(getView(), message, Snackbar.LENGTH_LONG);
        if (isError) {
            make.getView().setBackgroundColor(getResources().getColor(android.R.color.holo_red_dark));
        }
        make.show();
    }

    public void showMessage(String message) {
        showSnackbar(false, message);
    }

    protected ApplicationComponent getApplicationComponent() {
        return ((BakeApplication)getActivity(). getApplication()).getApplicationComponent();
    }
}
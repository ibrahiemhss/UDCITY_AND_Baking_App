package com.example.ibrahim.udacity_and_baking_app.modules.home;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Surface;
import android.view.WindowManager;
import android.widget.Toast;

import com.example.ibrahim.udacity_and_baking_app.IdlingResource.EspressoIdlingResource;
import com.example.ibrahim.udacity_and_baking_app.R;
import com.example.ibrahim.udacity_and_baking_app.base.BaseActivity;
import com.example.ibrahim.udacity_and_baking_app.data.Contract;
import com.example.ibrahim.udacity_and_baking_app.data.SharedPrefManager;
import com.example.ibrahim.udacity_and_baking_app.di.components.DaggerMainComponents;
import com.example.ibrahim.udacity_and_baking_app.di.module.MainModule;
import com.example.ibrahim.udacity_and_baking_app.modules.AppWidget.MainWidgetProvider;
import com.example.ibrahim.udacity_and_baking_app.modules.details.DetailsActivity;
import com.example.ibrahim.udacity_and_baking_app.modules.home.adapter.BakesAdapter;
import com.example.ibrahim.udacity_and_baking_app.mvp.model.Bake;
import com.example.ibrahim.udacity_and_baking_app.mvp.presenter.MainPresenter;
import com.example.ibrahim.udacity_and_baking_app.mvp.view.MainView;
import com.example.ibrahim.udacity_and_baking_app.utilities.NetworkUtils;

import java.util.ArrayList;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

//extends BaseActivity class to get what  we need from it
//implements MainView interface to get Value of all injected View in it
@SuppressWarnings("WeakerAccess")
public class MainActivity extends BaseActivity implements MainView {

    private static final String STATE_BAKE = "state_bake";
    //bind RecyclerView
    @BindView(R.id.bake_list)
    protected RecyclerView mBake_list;
    @SuppressWarnings("unused")
    /*MainActivity will get any bake information  from this MainPresenter */
    @Inject
    protected MainPresenter mPresenter;
    private BakesAdapter mBakesAdapter;
    private ArrayList<Bake> mBakeArrayList;
    private final BakesAdapter.OnBakeClickListener onBakeClickListener = new BakesAdapter.OnBakeClickListener() {
        @Override
        public void onClick(int position) {

            launchDetailActivity(position);

        }
    };

    /**
     * just implement getContentView from
     * {@link BaseActivity#getContentView label}
     */
    @Override
    protected int getContentView() {
        return R.layout.activity_main;
    }

    //Override view method from BaseActivity
    @Override
    protected void onViewReady(Bundle savedInstanceState, Intent intent) {
        super.onViewReady(savedInstanceState, intent);

        GetListByScreenSize();
        if (savedInstanceState != null && savedInstanceState.containsKey(STATE_BAKE)) {
            mBakeArrayList = savedInstanceState.getParcelableArrayList(STATE_BAKE);
            mBakesAdapter.addBakes(mBakeArrayList);

        } else {
            GetListByScreenSize();
            EspressoIdlingResource.increment(); // stops Espresso tests from going forward

            if (NetworkUtils.isNetAvailable(MainActivity.this)) {
                /*get value from the object of MainPresenter class */
                mPresenter.geBaking();
            } else {
                mPresenter.getBakeFromDatabase();
            }
            MainWidgetProvider.sendRefreshBroadcast(MainActivity.this);
            EspressoIdlingResource.decrement(); // Tells Espresso test to resume


        }

    }

    /**
     * calling Bakeloading.downloadImage from onStart or onResume instead of in onCreate
     * to ensure there is enough time to register IdlingResource if the download is done
     * too early (i.e. in onCreate)
     */



    /*Override  resolveDaggerDependency from BaseActivity class*/
    @Override
    protected void resolveDaggerDependency() {

        /*build component*/
        DaggerMainComponents.builder()
                /*add getApplicationComponent from BaseActivity.class*/
                .applicationComponent(getApplicationComponent())
                //MainModule requiring a view being implements by that
                .mainModule(new MainModule(this))
                .build().inject(this);
    }

    //implements onShowDialog & onShowToast & onHideDialog to show message
    //implements onBakeLoaded
    @Override
    public void onBakeLoaded(ArrayList<Bake> bakeList) {
        mBakeArrayList = bakeList;
        mBakesAdapter.addBakes(mBakeArrayList);

    }

    @Override
    public void onShowDialog(String message) {

        showDialog(message);
    }

    @Override
    public void onShowToast() {

        hideDialog();
    }

    @Override
    public void onHideDialog(String message) {

        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    private void launchDetailActivity(int position) {
        Intent intent = new Intent(this, DetailsActivity.class);
        Bundle extras = new Bundle();
        extras.putInt(Contract.EXTRA_POSITION, position);
        String name = mBakeArrayList.get(position).getName();
        extras.putString(Contract.EXTRA_BAKE_NAME, name);
        SharedPrefManager.getInstance(this).setPrefDetailsPosition(position);
        SharedPrefManager.getInstance(this).setPrefBakeName(name);
        intent.putExtras(extras);
        startActivity(intent);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putParcelableArrayList(STATE_BAKE, mBakeArrayList);
        super.onSaveInstanceState(outState);
    }

    public void GetListByScreenSize() {

        assert this.getSystemService(Context.WINDOW_SERVICE) != null;
        final int rotation = ((WindowManager) this.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay().getOrientation();
        switch (rotation) {
            case Surface.ROTATION_0:
                if (isTablet()) {
                    initialiseListWithsLargeSize();
                } else {
                    initialiseListWithPhoneScreen();
                }

                break;
            case Surface.ROTATION_90:
                initialiseListWithsLargeSize();
                break;
            case Surface.ROTATION_180:
                initialiseListWithPhoneScreen();
                break;


            case Surface.ROTATION_270:
                break;
        }
    }

    public boolean isTablet() {
        return (MainActivity.this.getResources().getConfiguration().screenLayout
                & Configuration.SCREENLAYOUT_SIZE_MASK)
                >= Configuration.SCREENLAYOUT_SIZE_LARGE;
    }

    //initialiseList to show values inside mBake_list
    private void initialiseListWithPhoneScreen() {


        ButterKnife.bind(this);
        mBake_list.setHasFixedSize(true);
        mBake_list.setLayoutManager(new LinearLayoutManager(this,
                LinearLayoutManager.VERTICAL, false));
        //Pass a list of images with inflater ​​in adapter
        mBakesAdapter = new BakesAdapter(mPresenter.getImgId(), getLayoutInflater());

        mBakesAdapter.setBakeClickListener(onBakeClickListener);

        mBake_list.setAdapter(mBakesAdapter);
    }

    //initialiseList to show values inside mBake_list
    private void initialiseListWithsLargeSize() {


        ButterKnife.bind(this);
        mBake_list.setHasFixedSize(true);
        mBake_list.setLayoutManager(new GridLayoutManager(this, 2,
                GridLayoutManager.VERTICAL, false));
        //Pass a list of images with inflater ​​in adapter
        mBakesAdapter = new BakesAdapter(mPresenter.getImgId(), getLayoutInflater());

        mBakesAdapter.setBakeClickListener(onBakeClickListener);

        mBake_list.setAdapter(mBakesAdapter);
    }

}

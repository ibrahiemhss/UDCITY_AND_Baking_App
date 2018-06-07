package com.example.ibrahim.udacity_and_baking_app.modules.home;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Surface;
import android.view.WindowManager;
import android.widget.Toast;

import com.example.ibrahim.udacity_and_baking_app.di.module.MainModule;
import com.example.ibrahim.udacity_and_baking_app.modules.details.DetailsActivity;

import com.example.ibrahim.udacity_and_baking_app.R;
import com.example.ibrahim.udacity_and_baking_app.base.BaseActivity;
import com.example.ibrahim.udacity_and_baking_app.di.components.DaggerMainComponents;
import com.example.ibrahim.udacity_and_baking_app.modules.home.adapter.BakesAdapter;
import com.example.ibrahim.udacity_and_baking_app.modules.steps.fragments.StepsFragment;
import com.example.ibrahim.udacity_and_baking_app.mvp.model.Bake;
import com.example.ibrahim.udacity_and_baking_app.mvp.presenter.MainPresenter;
import com.example.ibrahim.udacity_and_baking_app.mvp.view.MainView;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

//TODO (33) extends BaseActivity class to get what  we need from it
//TODO (43) implements MainView interface to get Value of all injected View in it
@SuppressWarnings("WeakerAccess")
public class MainActivity extends BaseActivity implements MainView {

    private BakesAdapter mBakesAdapter;
    //TODO (73) bind RecyclerView
    @BindView(R.id.bake_list)
    protected RecyclerView mBake_list;
    private static final String STATE_BAKE = "state_bake";


    /**
     * just implement getContentView from
     * {@link BaseActivity#getContentView label}
     */
    @Override
    protected int getContentView() {
        return R.layout.activity_main;
    }

    /*
    *TODO (44) MainActivity will get any bake information  from this MainPresenter */
    @Inject
    protected MainPresenter mPresenter;
    private ArrayList<Bake> mBakeArrayList;



    //TODO (34) Override view method from BaseActivity
    @Override
    protected void onViewReady(Bundle savedInstanceState, Intent intent) {
        super.onViewReady(savedInstanceState, intent);

        GetListByScreenSize();
        if (savedInstanceState != null && savedInstanceState.containsKey(STATE_BAKE)) {
            mBakeArrayList = savedInstanceState.getParcelableArrayList(STATE_BAKE);
            mBakesAdapter.addBakes(mBakeArrayList);

        }else {
            GetListByScreenSize();
      /*TODO (45) get value from the object of MainPresenter class */
            mPresenter.geBaking();
        }
    }



    /*TODO (48) Override  resolveDaggerDependency from BaseActivity class*/
    @Override
    protected void resolveDaggerDependency() {

        /*TODO (54) build component*/
        DaggerMainComponents.builder()
                /*TODO (58) add getApplicationComponent from
                * BaseActivity.class*/
                .applicationComponent(getApplicationComponent())
                //MainModule requiring a view being implements by that
                .mainModule(new MainModule(this))
                .build().inject(this);
    }

    //TODO (67) implements onBakeLoaded
    @Override
    public void onBakeLoaded(ArrayList<Bake> bakeList) {
        mBakeArrayList=bakeList;
        mBakesAdapter.addBakes(mBakeArrayList);

    }
//TODO (69) implements onShowDialog & onShowToast & onHideDialog to show message

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

    private final BakesAdapter.OnBakeClickListener onBakeClickListener = new BakesAdapter.OnBakeClickListener() {
        @Override
        public void onClick(int position) {
            launchDetailActivity(position);

        }
    };


    private void launchDetailActivity(int position) {
        Intent intent = new Intent(this, DetailsActivity.class);
        intent.putExtra(DetailsActivity.EXTRA_POSITION, position);
        startActivity(intent);
    }
    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putParcelableArrayList(STATE_BAKE, mBakeArrayList);
        super.onSaveInstanceState(outState);
    }

    public void GetListByScreenSize(){

        assert ((WindowManager) this.getSystemService(Context.WINDOW_SERVICE)) != null;
        final int rotation = ((WindowManager) this.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay().getOrientation();
        switch (rotation) {
            case Surface.ROTATION_0:
                if (isTablet()) {
                    initialiseListWithsLargSize();
                } else {
                    initialiseListWithPhoneScreen();
                }

                break;
            case Surface.ROTATION_90:
                initialiseListWithsLargSize();
                break;
            case Surface.ROTATION_180:
                initialiseListWithPhoneScreen();
                break;


            case Surface.ROTATION_270:
                break;
        }
    }

    public  boolean isTablet() {
        return (MainActivity.this.getResources().getConfiguration().screenLayout
                & Configuration.SCREENLAYOUT_SIZE_MASK)
                >= Configuration.SCREENLAYOUT_SIZE_LARGE;
    }

    //TODO (75) create  initialiseList to show values inside mBake_list
    private void initialiseListWithPhoneScreen() {


        ButterKnife.bind(this);
        mBake_list.setHasFixedSize(true);
        mBake_list.setLayoutManager(new LinearLayoutManager(this,
                LinearLayoutManager.VERTICAL, false));
        //Pass a list of images with inflater ​​in adapter
        mBakesAdapter = new BakesAdapter(mPresenter.getImgId(), getLayoutInflater(),mBakeArrayList);

        mBakesAdapter.setBakeClickListener(onBakeClickListener);

        mBake_list.setAdapter(mBakesAdapter);
    }
    //TODO (75) create  initialiseList to show values inside mBake_list
    private void initialiseListWithsLargSize() {


        ButterKnife.bind(this);
        mBake_list.setHasFixedSize(true);
        mBake_list.setLayoutManager(new GridLayoutManager(this,2,
                GridLayoutManager.VERTICAL, false));
        //Pass a list of images with inflater ​​in adapter
        mBakesAdapter = new BakesAdapter(mPresenter.getImgId(), getLayoutInflater(),mBakeArrayList);

        mBakesAdapter.setBakeClickListener(onBakeClickListener);

        mBake_list.setAdapter(mBakesAdapter);
    }
}

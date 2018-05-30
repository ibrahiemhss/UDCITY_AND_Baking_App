package com.example.ibrahim.udacity_and_baking_app.modules.home;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.ibrahim.udacity_and_baking_app.modules.details.DetailsActivity;

import com.example.ibrahim.udacity_and_baking_app.R;
import com.example.ibrahim.udacity_and_baking_app.base.BaseActivity;
import com.example.ibrahim.udacity_and_baking_app.di.components.DaggerBakeComponents;
import com.example.ibrahim.udacity_and_baking_app.di.module.BakeModule;
import com.example.ibrahim.udacity_and_baking_app.modules.home.adapter.BakesAdapter;
import com.example.ibrahim.udacity_and_baking_app.mvp.model.Bake;
import com.example.ibrahim.udacity_and_baking_app.mvp.presenter.BakePresenter;
import com.example.ibrahim.udacity_and_baking_app.mvp.view.MainView;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

//TODO (33) extends BaseActivity class to get what  we need from it
//TODO (43) implements MainView interface to get Value of all injected View in it
public class MainActivity extends BaseActivity implements MainView {

    private BakesAdapter mBakesAdapter;
    //TODO (73) bind RecyclerView
    @BindView(R.id.bake_list)
    protected RecyclerView mBake_list;


    /**
     * just implement getContentView from
     * {@link BaseActivity#getContentView label}
     */
    @Override
    protected int getContentView() {
        return R.layout.activity_main;
    }

    /*
    *TODO (44) MainActivity will get any bake information  from this BakePresenter */
    @Inject
    protected BakePresenter mPresenter;

    //TODO (34) Override view method from BaseActivity
    @Override
    protected void onViewReady(Bundle savedInstanceState, Intent intent) {
        super.onViewReady(savedInstanceState, intent);
        initialiseList();
        /*TODO (45) get value from the object of BakePresenter class */
        mPresenter.geBaking();
    }

    //TODO (75) create  initialiseList to show values inside mBake_list
    private void initialiseList() {

        Integer[] imgid = {
                R.drawable.nutella_pie,
                R.drawable.brownies,
                R.drawable.yellow_cake,
                R.drawable.cheesecake
        };
        ButterKnife.bind(this);
        mBake_list.setHasFixedSize(true);
        mBake_list.setLayoutManager(new LinearLayoutManager(this,
                LinearLayoutManager.VERTICAL, false));
        //Pass a list of images with inflator ​​into adapter
        mBakesAdapter = new BakesAdapter(imgid, getLayoutInflater());

        mBakesAdapter.setBakeClickListener(onBakeClickListener);

        mBake_list.setAdapter(mBakesAdapter);
    }

    /*TODO (48) Override  resolveDaggerDependency from BaseActivity class*/
    @Override
    protected void resolveDaggerDependency() {

        /*TODO (54) build component*/
        DaggerBakeComponents.builder()
                /*TODO (58) add getApplicationComponent from
                * BaseActivity.class*/
                .applicationComponent(getApplicationComponent())
                //bakeModule requiring a view being implements by that
                .bakeModule(new BakeModule(this))
                .build().inject(this);
    }

    //TODO (67) implements onBakeLoaded
    @Override
    public void onBakeLoaded(List<Bake> bakeList) {

        mBakesAdapter.addBakes(bakeList);

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

    private BakesAdapter.OnBakeClickListener onBakeClickListener = new BakesAdapter.OnBakeClickListener() {
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
}

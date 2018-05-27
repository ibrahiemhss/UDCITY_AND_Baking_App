package com.example.ibrahim.udacity_and_baking_app.modules.home;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

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

//extends BaseActivity class to get what i want from it
//implements MainView interfase to get Value of all injected View in it
public class MainActivity extends BaseActivity implements MainView {

    private BakesAdapter mBakesAdapter;
    @BindView(R.id.bake_list) protected  RecyclerView mBake_list;


    /**
     *just implement getContentView from
     * {@link BaseActivity#getContentView label}
     */
    @Override
    protected int getContentView() {
        return R.layout.activity_main;
    }
    /*
    *MainActivity will get any bake  iformation from this BakePresenter */
    @Inject protected BakePresenter mPresenter;

    @Override
    protected void onViewReady(Bundle savedInstanceState, Intent intent) {
        super.onViewReady(savedInstanceState, intent);
        initialiseList();
        mPresenter.geBaking();
    }

    private void initialiseList() {

        ButterKnife.bind(this);
        mBake_list.setHasFixedSize(true);
        mBake_list.setLayoutManager(new LinearLayoutManager(this,
                LinearLayoutManager.VERTICAL,false));
        mBakesAdapter=new BakesAdapter(getLayoutInflater());
        mBake_list.setAdapter(mBakesAdapter);
    }

    @Override
    protected void resolveDaggerDependency() {


        DaggerBakeComponents.builder()
                .applicationComponent(getApplicationComponent())
                //bakeModule requiring a view being implements by that
                .bakeModule(new BakeModule(this))
                .build().inject(this);
    }

    @Override
    public void onBakeLoaded(List<Bake> bakeList) {

        mBakesAdapter.addBakes(bakeList);

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
}

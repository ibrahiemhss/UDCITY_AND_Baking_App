package com.example.ibrahim.udacity_and_baking_app.modules.details.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.ibrahim.udacity_and_baking_app.R;
import com.example.ibrahim.udacity_and_baking_app.base.BaseFragments;
import com.example.ibrahim.udacity_and_baking_app.di.components.DaggerDetailsComponents;
import com.example.ibrahim.udacity_and_baking_app.di.module.DetailsModule;
import com.example.ibrahim.udacity_and_baking_app.modules.details.adapter.StepsAdapter;
import com.example.ibrahim.udacity_and_baking_app.mvp.model.Ingredients;
import com.example.ibrahim.udacity_and_baking_app.mvp.model.Steps;
import com.example.ibrahim.udacity_and_baking_app.mvp.presenter.DetailsPresenter;
import com.example.ibrahim.udacity_and_baking_app.mvp.view.DetailsView;
import com.example.ibrahim.udacity_and_baking_app.mvp.view.StepsView;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by ibrahim on 01/06/18.
 */

public class StepsFragment extends BaseFragments implements DetailsView {
    private static final String TAG = "StepsFragment";
    @BindView(R.id.step_listssssss)
    protected RecyclerView mStep_list;
    private StepsAdapter mStepsAdapter;
    Unbinder unbinder;
    @Inject
    protected DetailsPresenter mPresenter;


    int pos;

    public int getPos() {
        return pos;
    }

    public void setPos(int pos) {
        this.pos = pos;
    }

    public StepsFragment() {
    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        resolveDaggerDependency();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_steps, container, false);
        unbinder = ButterKnife.bind(this, view);
        initialiseListSteps();

        return view;
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mPresenter.getBakeIngredients(getPos());

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }




    private void initialiseListSteps() {

       // ButterKnife.bind(this.getActivity());
        mStep_list.setHasFixedSize(true);
        mStep_list.setLayoutManager(new LinearLayoutManager(getActivity(),
                LinearLayoutManager.VERTICAL, false));
        mStepsAdapter = new StepsAdapter( getLayoutInflater());
        mStep_list.setAdapter(mStepsAdapter);
    }

    @Override
    public StepsView getSeps() {
        return null;
    }

    @Override
    protected void resolveDaggerDependency() {
        DaggerDetailsComponents.builder()
                .applicationComponent(getApplicationComponent())
                .detailsModule(new DetailsModule(this))
                .build().injectStepsFragment(this);



    }

    @Override
    public void onIngredientsLoaded(List<Ingredients> ingredientsList) {

    }

    @Override
    public void onStepsLoaded(List<Steps> stepsList) {
        mStepsAdapter.addSteps(stepsList);

    }
}


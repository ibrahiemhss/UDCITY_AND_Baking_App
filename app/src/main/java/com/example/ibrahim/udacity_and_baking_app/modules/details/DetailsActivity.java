package com.example.ibrahim.udacity_and_baking_app.modules.details;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.example.ibrahim.udacity_and_baking_app.R;
import com.example.ibrahim.udacity_and_baking_app.base.BaseActivity;
import com.example.ibrahim.udacity_and_baking_app.mvp.presenter.BakePresenter;
import com.example.ibrahim.udacity_and_baking_app.mvp.presenter.DetailsPresenter;

import javax.inject.Inject;

import butterknife.BindView;

/**
 *
 * Created by ibrahim on 22/05/18.
 */

public class DetailsActivity extends BaseActivity {
    @BindView(R.id.textTest) protected TextView mtextTest;

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;
    @Override
    protected int getContentView() {
        return R.layout.detials_activity;
    }

    @Inject
    protected DetailsPresenter mPresenter;

    @Override
    protected void onViewReady(Bundle savedInstanceState, Intent intent) {
        super.onViewReady(savedInstanceState, intent);
        initialisetest();
    }

    private void initialisetest() {

        Intent intent = getIntent();
        //   getIntent=intent.getStringExtra(EXTRA_POSITION)
        if (intent != null) {
            // closeOnError();

            int position = intent.getIntExtra(EXTRA_POSITION
                    , DEFAULT_POSITION);
                mPresenter.getBakeIngredients();



            mtextTest.setText(String.valueOf(position));
            if (position == DEFAULT_POSITION) {
                // EXTRA_POSITION not found in intent
                // closeOnError();
                return;
            }
        }
    }

}

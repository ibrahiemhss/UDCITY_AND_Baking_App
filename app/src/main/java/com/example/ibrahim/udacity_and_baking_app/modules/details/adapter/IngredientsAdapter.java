package com.example.ibrahim.udacity_and_baking_app.modules.details.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.ibrahim.udacity_and_baking_app.R;
import com.example.ibrahim.udacity_and_baking_app.mvp.model.Ingredients;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 *
 * Created by ibrahim on 01/06/18.
 */

public class IngredientsAdapter extends RecyclerView.Adapter<IngredientsAdapter.Holder> {

    private final LayoutInflater mLayoutInflater;
    private final List<Ingredients> mList=new ArrayList<>();
    public IngredientsAdapter( LayoutInflater inflater){
        mLayoutInflater=inflater;
    }

    @NonNull
    @Override
    public IngredientsAdapter.Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {


        View view= mLayoutInflater.inflate(R.layout.list_item_ingredients,parent,false);

        return new IngredientsAdapter.Holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull IngredientsAdapter.Holder holder, int position) {

        holder.bind(mList.get(position),position);
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }
    public void addIngredients(List<Ingredients> ingredientsList){
        mList.addAll(ingredientsList);
        notifyDataSetChanged();
    }
    @SuppressWarnings({"WeakerAccess", "unused"})
    public class Holder extends RecyclerView.ViewHolder {
        @BindView(R.id.textview_quantity)
        TextView mQuantity;
        @BindView(R.id.textview_preview_measure)
        TextView mMeasure;
        @BindView(R.id.textview_preview_ingredient)
        protected TextView mIngredient;

        private final Context mContext;

        public Holder(View itemView) {
            super(itemView);
            mContext = itemView.getContext();
            //   itemView.setOnClickListener(this);
            ButterKnife.bind(this, itemView);
        }

        public void bind(Ingredients ingredients, int position) {
            mIngredient.setText(ingredients.getIngredient());
            mMeasure.setText(ingredients.getMeasure());
            mQuantity.setText(String.valueOf(ingredients.getQuantity()));


        }

    }

}

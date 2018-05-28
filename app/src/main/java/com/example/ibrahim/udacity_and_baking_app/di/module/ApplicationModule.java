package com.example.ibrahim.udacity_and_baking_app.di.module;

import android.content.Context;

import java.util.concurrent.TimeUnit;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
//TODO (15) create interface ApplicationModule

/**
 *
 * Created by ibrahim on 22/05/18.
 */
/*
* noted as modules because anything
* that provided here*/
@Module
public class ApplicationModule {

    /* dagger is basically to
     provide any object here */
    private final String mBaseUrl;
    private final Context mContext;
     //TODO (22)
    @Inject
    public ApplicationModule(Context context,String baseUrl) {
        this.mBaseUrl = baseUrl;
        this.mContext=context;
    }

    /**TODO (20)provide GsonConverterFactory
     *   which need some dependency that can
     be passed through the conserve for through this method

     */

    @Singleton
    @Provides
    GsonConverterFactory provideGsonConverterFactory() {
        return GsonConverterFactory.create();
    }

    /**TODO (19)provide OkHttpClient
     * which need some dependency that can
     be passed through the conserve for through this method
     */

    @Singleton
    @Provides
    @Named("ok-1")
    OkHttpClient provideOkHttpClient1() {
        return new OkHttpClient.Builder()
                .connectTimeout(20, TimeUnit.SECONDS)
                .readTimeout(20, TimeUnit.SECONDS)
                .build();
    }


    /*@Singleton
    @Provides
    @Named("ok-2")
    OkHttpClient provideOkHttpClient2() {
        return new OkHttpClient.Builder()
                .connectTimeout(60, TimeUnit.SECONDS)
                .readTimeout(60, TimeUnit.SECONDS)
                .build();
    }*/

    /**TODO (21)provide RxJavaCallAdapterFactory
     *  which need some dependency that can
     be passed through the conserve for through this method

     */
    @Singleton
    @Provides
    RxJavaCallAdapterFactory provideRxJavaCallAdapterFactory() {
        return RxJavaCallAdapterFactory.create();
    }

    /** TODO (18)provide retrofit
     * which need some dependency that can
     be passed through the conserve for through this method

     *@param client
     *@param converterFactory
     *@param adapterFactory
     */
    @Singleton
    @Provides
    Retrofit provideRetrofit(@Named("ok-1") OkHttpClient client, GsonConverterFactory converterFactory, RxJavaCallAdapterFactory adapterFactory) {
        return new Retrofit.Builder()
                .baseUrl(mBaseUrl)
                .addConverterFactory(converterFactory)
                .addCallAdapterFactory(adapterFactory)
                .client(client)
                .build();
    }

   //TODO (29) Provides Context
    @Provides
    @Singleton
    Context provideContext() {
        return mContext;
    }
}

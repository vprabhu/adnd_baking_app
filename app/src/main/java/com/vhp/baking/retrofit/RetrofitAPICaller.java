package com.vhp.baking.retrofit;


import android.content.Context;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public final class RetrofitAPICaller {


    private static RetrofitAPICaller sRetrofitApiCaller;

    private static Context sContext;

    private RecipeApiService recipeApiService;

    private RetrofitAPICaller() {
        setupRetroAdapter();
    }

    public static RetrofitAPICaller getInstance(Context context) {
        sContext = context;
        if (sRetrofitApiCaller == null) {
            sRetrofitApiCaller = new RetrofitAPICaller();
        }
        return sRetrofitApiCaller;
    }

    private void setupRetroAdapter() {

        Retrofit digestRetrofit = new Retrofit.Builder()
                .baseUrl("https://d17h27t6h515a5.cloudfront.net/topher/2017/May/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        recipeApiService = digestRetrofit.create(RecipeApiService.class);
    }

    public RecipeApiService getMoviesAPIs() {
        return recipeApiService;
    }

}

package com.vhp.baking.retrofit;


import com.vhp.baking.model.Recipe;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.http.GET;

public interface RecipeApiService {

   @GET("59121517_baking/baking.json")
   Call<List<Recipe>> getRecipe();


}

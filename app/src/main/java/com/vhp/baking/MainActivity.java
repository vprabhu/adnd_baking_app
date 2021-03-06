package com.vhp.baking;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.vhp.baking.adapter.RecipeListAdapter;
import com.vhp.baking.model.Recipe;
import com.vhp.baking.retrofit.RetrofitAPICaller;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements RecipeListAdapter.RecipeOnClickHandler {


    private static final String TAG = "MainActivity";
    @BindView(R.id.recyclerView_recipe)
    RecyclerView mRecipeRecyclerView;
    private List<Recipe> mRecipeList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(MainActivity.this);

        RetrofitAPICaller.getInstance(MainActivity.this).getMoviesAPIs().getRecipe().enqueue(new Callback<List<Recipe>>() {
            @Override
            public void onResponse(Call<List<Recipe>> call, Response<List<Recipe>> response) {
                Log.d(TAG, "onResponse: " + response.body().toString());
                Log.d(TAG, "onResponse: " + response.body().size());
                mRecipeList = response.body();
                for (int i = 0; i < response.body().size(); i++) {
                    Log.d(TAG, "onResponse: " + response.body().get(i).getName());
                }
                RecipeListAdapter mRecipeListAdapter = new RecipeListAdapter(mRecipeList , MainActivity.this);
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(
                        MainActivity.this , LinearLayoutManager.VERTICAL , false
                );
                mRecipeRecyclerView.setLayoutManager(linearLayoutManager);
                mRecipeRecyclerView.setAdapter(mRecipeListAdapter);
            }

            @Override
            public void onFailure(Call<List<Recipe>> call, Throwable t) {
                Log.d(TAG, "onFailure: "+t);
            }
        });
    }

    @Override
    public void onClick(Recipe recipeInfo) {
        Intent mIntent = new Intent(MainActivity.this ,
                RecipeInfoActivity.class);
        mIntent.putExtra("Recipe" , recipeInfo);
        startActivity(mIntent);
    }
}

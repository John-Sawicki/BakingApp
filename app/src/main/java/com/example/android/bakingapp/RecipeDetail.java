package com.example.android.bakingapp;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.TextView;

import com.example.android.bakingapp.utilities.JsonUtility;
import com.example.android.bakingapp.utilities.StepAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;
import com.example.android.bakingapp.provider.IngredContract.IngredEntry;
import com.example.android.bakingapp.provider.IngredDbHelper;
import com.example.android.bakingapp.provider.IngredientService;
import static com.example.android.bakingapp.provider.IngredContract.IngredEntry.CONTENT_URI;
public class RecipeDetail extends AppCompatActivity implements StepAdapter.StepOnClickInterface {
    @BindView(R.id.detail_ingredients)TextView ingredientsText;
    @BindView(R.id.step_recycler_view)RecyclerView stepsRecyclerView;
    private StepAdapter mStepAdapter;
    private int recipeNumber, tabletStep=0;
    private SQLiteDatabase mDb;
    private static String SAVE_RECIPE = "SAVE_RECIPE", SAVE_STEP="SAVE_STEP";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_detail);
        ButterKnife.bind(this);
           //pass this value to RecipeStepDetail
        if(savedInstanceState==null){
            recipeNumber = getIntent().getIntExtra("recipeIndex", 0);
            Log.d("recDetail onCreate","if savedInst is null "+recipeNumber);
        }
        Log.d("recipeNumber",recipeNumber+"");
        LinearLayoutManager layoutManager = new LinearLayoutManager(this,
                LinearLayoutManager.VERTICAL, false);
        mStepAdapter = new StepAdapter(this);
        stepsRecyclerView.setLayoutManager(layoutManager);
        stepsRecyclerView.setAdapter(mStepAdapter);

        new getSteps().execute(recipeNumber);//only get the steps for the selected recipe from RecipeActivity
        new getIngredients().execute(recipeNumber);
        if(!getResources().getBoolean(R.bool.isPhone)&&savedInstanceState==null){//tablet && first time activity is made
            Log.d("recDetail onCreate","if savedInst is null- if statement rec#"+recipeNumber);
            FragmentManager fmAdd = getSupportFragmentManager();
            StepFragment stepFragmentOnCreate = new StepFragment();
            Bundle bundle = new Bundle();//pass the values to the fragment to use when it is first created
            bundle.putInt("recipeIndex",recipeNumber);//from getIntExtra
            bundle.putInt("stepIndex",tabletStep );//add fragment at 0, replace based on step pressed
            stepFragmentOnCreate.setArguments(bundle);
            fmAdd.beginTransaction().add(R.id.detail_fragment, stepFragmentOnCreate).commit();
        }else if(!getResources().getBoolean(R.bool.isPhone)&&savedInstanceState!=null){//tablet && activity is recreated on rotate
            recipeNumber = savedInstanceState.getInt(SAVE_RECIPE);
            tabletStep =savedInstanceState.getInt(SAVE_STEP);
            Log.d("recDetail","saved isn't null rec# "+recipeNumber+" step # "+tabletStep);//used saved step values for movie
            FragmentManager fmAdd = getSupportFragmentManager();
            StepFragment stepFragmentOnCreate = new StepFragment();
            Bundle bundle = new Bundle();//pass the values to the fragment to use when it is first created
            bundle.putInt("recipeIndex",recipeNumber);//from getIntExtra
            bundle.putInt("stepIndex",tabletStep );//onClick passing in step value, replace based on step pressed
            stepFragmentOnCreate.setArguments(bundle);
            fmAdd.beginTransaction().replace(R.id.detail_fragment, stepFragmentOnCreate).commit();
        }
    }
    @Override
    public void onClick(int index) {
        tabletStep = index; //save the step index to save the movie index on rotate
        if(getResources().getBoolean(R.bool.isPhone)){//phone has a seperate activity for step detail
            Intent intent = new Intent(RecipeDetail.this, RecipeStepDetail.class);
            Log.d("recDetail", "recipe number " +recipeNumber);
            Log.d("recDetail", "step index "+index);
            intent.putExtra("recipeIndex",recipeNumber);
            intent.putExtra("stepIndex", index);    //step number for long description and movie
            startActivity(intent);
        }else { //tablet adds the step detail on the right side
            FragmentManager fmAdd = getSupportFragmentManager();
            StepFragment stepFragmentOnCreate = new StepFragment();
            Bundle bundle = new Bundle();//pass the values to the fragment to use when it is first created
            bundle.putInt("recipeIndex",recipeNumber);//from getIntExtra
            bundle.putInt("stepIndex",index );//onClick passing in step value, replace based on step pressed
            stepFragmentOnCreate.setArguments(bundle);
            Log.d("recDetail","saved is null");
            fmAdd.beginTransaction().replace(R.id.detail_fragment, stepFragmentOnCreate).commit();
        }
    }
    public class getIngredients extends AsyncTask<Integer, Void, String >{
        String ingredients = new String();

        @Override
        protected String doInBackground(Integer... integers) {
            try{
                int recipeInt = integers[0];
                String jsonStringFromWeb = JsonUtility.getResponseFromSite(JsonUtility.JsonUrl);
                ingredients = JsonUtility.getIngredients(jsonStringFromWeb, recipeInt);
                return ingredients;
            }catch (Exception e){
                e.printStackTrace();
                return null;
            }
        }
        @Override
        protected void onPostExecute(String s) {
            ingredientsText.setText(s);
            IngredientService.startActionUpdateIngred(getApplicationContext());
            Log.d("widget", "detail onPost");
            IngredDbHelper ingredDbHelper = new IngredDbHelper(getApplicationContext());
            mDb = ingredDbHelper.getWritableDatabase(); //add current recipe values to the db
            ContentValues contentValues = new ContentValues();
            contentValues.put(IngredEntry.COLUMN_INGREDIENTS, s);//used to store the value for the widget
            Cursor cursor = mDb.query(IngredEntry.TABLE_NAME, null, null, null, null,null,null);
            if(cursor.moveToFirst()){
                mDb.update(IngredEntry.TABLE_NAME, contentValues, null, null);  //this will always run starting at the second time so only one set of ingredients is shown
                Log.d("ingredientDb","update");
            }else{
               long insert= mDb.insert(IngredEntry.TABLE_NAME, null,contentValues); //this will run once
                Log.d("ingredientDb","insert value "+insert);
            }
        }
    }
    public class getSteps extends AsyncTask<Integer, Void, String[]>{
        String[] steps = new String[20];    //there will be less than 20 steps
        @Override
        protected String[] doInBackground(Integer... integers) {
            try{
                int recipeInt = integers[0];
                String jsonStringFromWeb = JsonUtility.getResponseFromSite(JsonUtility.JsonUrl);
                steps = JsonUtility.getStepsShort(jsonStringFromWeb,recipeInt);
                return steps;
            }catch (Exception e){
                e.printStackTrace();
                return null;
            }
        }
        @Override
        protected void onPostExecute(String[] s) {
            if(s!=null){
                mStepAdapter.updateSteps(s);
            }
        }
    }
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(SAVE_RECIPE, recipeNumber);
        outState.putInt(SAVE_STEP, tabletStep);
        Log.d("recDetail saveInst ", recipeNumber+" "+tabletStep);
    }
    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        recipeNumber =savedInstanceState.getInt(SAVE_RECIPE);
        tabletStep =savedInstanceState.getInt(SAVE_STEP);
        Log.d("recDetail resInst ", recipeNumber+" "+tabletStep);
    }
}

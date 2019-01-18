package dsa.eetac.upc.edu;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    private GameApi gameApi;
    private RecyclerView listObjects;
    private Button objectsBtn;
    private Button returnObjectsBtn;
    private Button mystatsBtn;
    private Button scoreboardBtn;
    private Button unityButton;
    ProgressDialog progressDialog;
    AdapterRecycler adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        objectsBtn = findViewById(R.id.objectsBtn);
        returnObjectsBtn = findViewById(R.id.returnObjects_Btn);
        scoreboardBtn = findViewById(R.id.scoreboardBtn);
        mystatsBtn = findViewById(R.id.myStatsBtn);
        unityButton = findViewById(R.id.launchgamebtn);
        unityButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,ConfigurationActivity.class));
            }
        });
        adapter = new AdapterRecycler(this);
        listObjects = (RecyclerView) findViewById(R.id.recyclerView);
        listObjects.setHasFixedSize(true);
        listObjects.setLayoutManager(new LinearLayoutManager(MainActivity.this));
        createGameApi();
        //Progress loading
        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Loading...");
        progressDialog.setMessage("Waiting for the server");
        progressDialog.setCancelable(false);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        progressDialog.show();
        progressDialog.hide();
    }
    private void createGameApi() {
        Gson gson = new GsonBuilder().create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(GameApi.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        gameApi = retrofit.create(GameApi.class);
    }
    public void listObjectsClick(View v){
        objectsBtn.setVisibility(View.INVISIBLE);
        unityButton.setVisibility(View.INVISIBLE);
        scoreboardBtn.setVisibility(View.INVISIBLE);
        mystatsBtn.setVisibility(View.INVISIBLE);

        listObjects.setVisibility(View.VISIBLE);
        returnObjectsBtn.setVisibility(View.VISIBLE );
        gameApi.allObjects().enqueue(objectsCallBack);
        progressDialog.setTitle("Loading...");
        progressDialog.setMessage("Waiting for the server");
        progressDialog.setCancelable(false);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        progressDialog.show();

    }

    public void returnObjClick(View v){
        objectsBtn.setVisibility(View.VISIBLE);
        unityButton.setVisibility(View.VISIBLE);
        mystatsBtn.setVisibility(View.VISIBLE);
        scoreboardBtn.setVisibility(View.VISIBLE);
        listObjects.setVisibility(View.INVISIBLE);
        returnObjectsBtn.setVisibility(View.INVISIBLE );
    }

    public void scoreBoardClick(View v){
        objectsBtn.setVisibility(View.INVISIBLE);
        unityButton.setVisibility(View.INVISIBLE);
        scoreboardBtn.setVisibility(View.INVISIBLE);
        mystatsBtn.setVisibility(View.INVISIBLE);

        listObjects.setVisibility(View.VISIBLE);
        returnObjectsBtn.setVisibility(View.VISIBLE );
        gameApi.allstats().enqueue(statsCallBack);
        progressDialog.setTitle("Loading...");
        progressDialog.setMessage("Waiting for the server");
        progressDialog.setCancelable(false);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        progressDialog.show();

    }

    public void myStatsClick(View v){
        objectsBtn.setVisibility(View.INVISIBLE);
        unityButton.setVisibility(View.INVISIBLE);
        scoreboardBtn.setVisibility(View.INVISIBLE);
        mystatsBtn.setVisibility(View.INVISIBLE);

        listObjects.setVisibility(View.VISIBLE);
        returnObjectsBtn.setVisibility(View.VISIBLE );
        gameApi.mystats(1).enqueue(myStatsCallBack);
        progressDialog.setTitle("Loading...");
        progressDialog.setMessage("Waiting for the server");
        progressDialog.setCancelable(false);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        progressDialog.show();

    }

    Callback<List<GameObject>> objectsCallBack = new Callback<List<GameObject>>(){

        @Override
        public void onResponse(Call<List<GameObject>> call, Response<List<GameObject>> response) {
            if (response.isSuccessful()) {
                List<GameObject> data = new ArrayList<>();
                data.addAll(response.body());
                listObjects.setAdapter(adapter);
                adapter.addElements(data);
                progressDialog.hide();
            } else {
                Log.d("QuestionsCallback", "Code: " + response.code() + " Message: " + response.message());
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(MainActivity.this);

                alertDialogBuilder
                        .setTitle("Error")
                        .setMessage(response.message())
                        .setCancelable(false)
                        .setPositiveButton("OK", (dialog, which) -> {
                        });

                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();
            }
        }

        @Override
        public void onFailure(Call<List<GameObject>> call, Throwable t) {
            t.printStackTrace();
        }
    };

    Callback<List<Stats>> statsCallBack = new Callback<List<Stats>>(){

        @Override
        public void onResponse(Call<List<Stats>> call, Response<List<Stats>> response) {
            if (response.isSuccessful()) {
                List<Stats> data = new ArrayList<>();
                data.addAll(response.body());
                Collections.sort(data,(d1,d2)->d1.getUsername().compareTo(d2.getUsername()));
                listObjects.setAdapter(new AdapterRecyclerStats(data));
                progressDialog.hide();
            } else {
                Log.d("QuestionsCallback", "Code: " + response.code() + " Message: " + response.message());
                //Show the alert dialog
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(MainActivity.this);

                alertDialogBuilder
                        .setTitle("Error")
                        .setMessage(response.message())
                        .setCancelable(false)
                        .setPositiveButton("OK", (dialog, which) -> {
                        });

                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();
            }
        }

        @Override
        public void onFailure(Call<List<Stats>> call, Throwable t) {

        }
    };

    Callback<UserStats> myStatsCallBack = new Callback<UserStats>(){

        @Override
        public void onResponse(Call<UserStats> call, Response<UserStats> response) {
            if (response.isSuccessful()) {
                List<UserStats> data = new ArrayList<>();
                data.add(response.body());
                listObjects.setAdapter(new AdapterRecyclerUserStats(data));
                progressDialog.hide();
            } else {
                Log.d("QuestionsCallback", "Code: " + response.code() + " Message: " + response.message());
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(MainActivity.this);

                alertDialogBuilder
                        .setTitle("Error")
                        .setMessage(response.message())
                        .setCancelable(false)
                        .setPositiveButton("OK", (dialog, which) -> {
                        });

                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();
            }
        }

        @Override
        public void onFailure(Call<UserStats> call, Throwable t) {

        }
    };
}

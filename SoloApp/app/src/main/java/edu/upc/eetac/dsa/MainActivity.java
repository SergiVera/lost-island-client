package edu.upc.eetac.dsa;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    private GameApi myapirest;
    private RecyclerView listObjects;
    private Button shopBtn;
    private Button scoreboardBtn;
    private Button unityButton;

    private TextView userText;
    private TextView moneyText;
    private TextView levelText;

    private String idintent;
    private int id =1;
    private String username = "Sergi";

    ProgressDialog progressDialog;
    AdapterRecycler adapter;
    AdapterUserObjects adapterUserObjects;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
       // Intent intent = getIntent();
        /*idintent = intent.getStringExtra(LoginActivity.EXTRA_MESSAGE);
        Log.i("ID en el MainActivity: ", idintent);*/
        shopBtn = findViewById(R.id.shop_btn);
        scoreboardBtn = findViewById(R.id.score_btn);

        userText = findViewById(R.id.username_txt);
        userText.setText("Username: "+ username);
        moneyText = findViewById(R.id.money_txt);
        levelText = findViewById(R.id.level_txt);

        unityButton = findViewById(R.id.play_btn);
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
        myapirest = GameApi.createAPIRest();
        //Progress loading
        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Loading...");
        progressDialog.setMessage("Waiting for the server");
        progressDialog.setCancelable(false);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        progressDialog.show();
        myStatsLoad();
        progressDialog.hide();
    }

    public void listObjectsClick(View v) {
        myapirest.getAllObjects().enqueue(objectsCallBack);
        progressDialog.setTitle("Loading...");
        progressDialog.setMessage("Waiting for the server");
        progressDialog.setCancelable(false);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        progressDialog.show();

    }

    public void scoreBoardClick(View v) {
        myapirest.allStats().enqueue(statsCallBack);
        progressDialog.setTitle("Loading...");
        progressDialog.setMessage("Waiting for the server");
        progressDialog.setCancelable(false);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        progressDialog.show();

    }

    public void returnBtnClick(View v){
        myStatsLoad();
        progressDialog.setTitle("Loading...");
        progressDialog.setMessage("Waiting for the server");
        progressDialog.setCancelable(false);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        progressDialog.show();
    }

    public void myStatsLoad() {
        myapirest.userAttributes(id).enqueue(myStatsCallBack);
        progressDialog.setTitle("Loading...");
        progressDialog.setMessage("Waiting for the server");
        progressDialog.setCancelable(false);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        progressDialog.show();

    }

    public void deleteAntennaPart(int idGameObject){
        myapirest.deleteAntennaPart(id,idGameObject).enqueue(deleteAntennaPartCall);
    }


    public void deleteEnemyUser(int idEnemy){
        myapirest.deleteEnemyUser(id,idEnemy).enqueue(deleteEnemyUserCall);
    }

    public List<Enemy> getEnemiesUser(){
        List<Enemy> data = null;
        Callback<List<Enemy>> getEnemiesUserCall = new Callback<List<Enemy>>() {
            @Override
            public void onResponse(Call<List<Enemy>> call, Response<List<Enemy>> response) {
                if (response.isSuccessful()) {
                    data.addAll(response.body());
                    //listObjects.setAdapter(new AdapterRecyclerUserStats(data));
                   // progressDialog.hide();
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
            public void onFailure(Call<List<Enemy>> call, Throwable t) {

            }
        };
        myapirest.getEnemiesUser(id).enqueue(getEnemiesUserCall);
        return data;
    }

    public UserAttributes getAttributesUser(){
        UserAttributes data = null;
        Callback<UserAttributes> getAttributesUserCall = new Callback<UserAttributes>() {

            @Override
            public void onResponse(Call<UserAttributes> call, Response<UserAttributes> response) {
                if (response.isSuccessful()) {
                    //data = response.body();
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
            public void onFailure(Call<UserAttributes> call, Throwable t) {
                t.printStackTrace();
            }
        };
        myapirest.userAttributes(id).enqueue(getAttributesUserCall);
        return data;
    }

    public void finishUserGame(){
        myapirest.finishUserGame(id).enqueue(finishUserGameCall);
    }

    public void modifyAttributes(int idObject){
        myapirest.updateUserAttributes(id,idObject).enqueue(updateUserAttributesCall);
    }

    public void sellObjects(int idObject){
        myapirest.sellObject(id,idObject).enqueue(sellObjectCall);
    }

    public void updateEnemyUser(int idEnemy,int enemyLife){
        myapirest.updateEnemyUser(id, idEnemy,enemyLife).enqueue(updateEnemyUserCall);
    }

    public void updateCurrentHealth(int currentHealth){
        myapirest.updateCurrentHealthUser(id,currentHealth).enqueue(updateCurrentHealthUserCall);
    }

    public void updateKilledEnemiesUser(int enemiesKilled){
        myapirest.updateKilledEnemiesUser(id,enemiesKilled).enqueue(updateKilledEnemiesUserCall);
    }

    public void updatePointsUser(int points){
        myapirest.updatePointsUser(id,points).enqueue(updatePointsUserCall);
    }

    public void updateStatusUser(int idMap){
        myapirest.updateStatusUser(id, idMap).enqueue(updateStatusUserCall);
    }

    Callback<List<GameObject>> objectsCallBack = new Callback<List<GameObject>>() {

        @Override
        public void onResponse(Call<List<GameObject>> call, Response<List<GameObject>> response) {
            if (response.isSuccessful()) {
                List<GameObject> data = new ArrayList<>();
                data.addAll(response.body());
                listObjects.setAdapter(adapter);
                adapter.addElements(data,id);
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

    Callback<List<Stats>> statsCallBack = new Callback<List<Stats>>() {

        @Override
        public void onResponse(Call<List<Stats>> call, Response<List<Stats>> response) {
            if (response.isSuccessful()) {
                List<Stats> data = new ArrayList<>();
                data.addAll(response.body());
                Collections.sort(data, (d1, d2) -> d1.getUsername().compareTo(d2.getUsername()));
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

    Callback<UserAttributes> myStatsCallBack = new Callback<UserAttributes>() {

        @Override
        public void onResponse(Call<UserAttributes> call, Response<UserAttributes> response) {
            if (response.isSuccessful()) {
                UserAttributes data = response.body();
                levelText.setText("Level: " + data.getLevel());
                moneyText.setText("Points: " + data.getPoints());
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
        public void onFailure(Call<UserAttributes> call, Throwable t) {

        }
    };

    Callback<List<GameObject>> getAllObjectsUserCall = new Callback<List<GameObject>>() {
        @Override
        public void onResponse(Call<List<GameObject>> call, Response<List<GameObject>> response) {
            if (response.isSuccessful()) {
                List<GameObject> data = new ArrayList<>();
                data.addAll(response.body());
                listObjects.setAdapter(adapterUserObjects);
                adapterUserObjects.addElements(data,id);
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

        }
    };

    Callback<Void> buyObjectCall = new Callback<Void>() {

        @Override
        public void onResponse(Call<Void> call, Response<Void> response) {
            if (response.isSuccessful()) {

                //listObjects.setAdapter(new AdapterRecyclerUserStats(data));
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
        public void onFailure(Call<Void> call, Throwable t) {

        }
    };

    Callback<Void> sellObjectCall = new Callback<Void>() {
        @Override
        public void onResponse(Call<Void> call, Response<Void> response) {
            if (response.isSuccessful()) {

                //listObjects.setAdapter(new AdapterRecyclerUserStats(data));
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
        public void onFailure(Call<Void> call, Throwable t) {

        }
    };

    Callback<Void> deleteAntennaPartCall = new Callback<Void>() {


        @Override
        public void onResponse(Call<Void> call, Response<Void> response) {
            if (response.isSuccessful()) {

                //listObjects.setAdapter(new AdapterRecyclerUserStats(data));
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
        public void onFailure(Call<Void> call, Throwable t) {

        }
    };

    Callback<Void> deleteEnemyUserCall = new Callback<Void>(){

        @Override
        public void onResponse(Call<Void> call, Response<Void> response) {
            if (response.isSuccessful()) {

                //listObjects.setAdapter(new AdapterRecyclerUserStats(data));
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
        public void onFailure(Call<Void> call, Throwable t) {

        }
    };


    Callback<Void> finishUserGameCall = new Callback<Void>() {
        @Override
        public void onResponse(Call<Void> call, Response<Void> response) {
            if (response.isSuccessful()) {

                //listObjects.setAdapter(new AdapterRecyclerUserStats(data));
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
        public void onFailure(Call<Void> call, Throwable t) {

        }
    };

    Callback<Void> updateUserAttributesCall = new Callback<Void>() {
        @Override
        public void onResponse(Call<Void> call, Response<Void> response) {
            if (response.isSuccessful()) {

                //listObjects.setAdapter(new AdapterRecyclerUserStats(data));
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
        public void onFailure(Call<Void> call, Throwable t) {

        }
    };

    Callback<Void> updateEnemyUserCall = new Callback<Void>() {
        @Override
        public void onResponse(Call<Void> call, Response<Void> response) {
            if (response.isSuccessful()) {

                //listObjects.setAdapter(new AdapterRecyclerUserStats(data));
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
        public void onFailure(Call<Void> call, Throwable t) {

        }
    };

    Callback<Void> updateCurrentHealthUserCall = new Callback<Void>() {
        @Override
        public void onResponse(Call<Void> call, Response<Void> response) {
            if (response.isSuccessful()) {

                //listObjects.setAdapter(new AdapterRecyclerUserStats(data));
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
        public void onFailure(Call<Void> call, Throwable t) {

        }
    };

    Callback<Void> updateKilledEnemiesUserCall = new Callback<Void>() {
        @Override
        public void onResponse(Call<Void> call, Response<Void> response) {
            if (response.isSuccessful()) {

                //listObjects.setAdapter(new AdapterRecyclerUserStats(data));
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
        public void onFailure(Call<Void> call, Throwable t) {

        }
    };

    Callback<Void> updatePointsUserCall = new Callback<Void>() {
        @Override
        public void onResponse(Call<Void> call, Response<Void> response) {
            if (response.isSuccessful()) {

                //listObjects.setAdapter(new AdapterRecyclerUserStats(data));
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
        public void onFailure(Call<Void> call, Throwable t) {

        }
    };

    Callback<Void> updateStatusUserCall = new Callback<Void>() {
        @Override
        public void onResponse(Call<Void> call, Response<Void> response) {
            if (response.isSuccessful()) {

                //listObjects.setAdapter(new AdapterRecyclerUserStats(data));
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
        public void onFailure(Call<Void> call, Throwable t) {

        }
    } ;
}


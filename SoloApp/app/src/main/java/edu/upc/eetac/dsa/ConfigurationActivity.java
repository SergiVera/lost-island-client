package edu.upc.eetac.dsa;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.lostisland.name.UnityPlayerActivity;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ConfigurationActivity extends AppCompatActivity {
    public static Context mContext;
    private GameApi myapirest;
    private int id = 1;
    private UserAttributes data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mContext = this;

        myapirest = GameApi.createAPIRest();
        data = new UserAttributes();

        Intent intent = new Intent(mContext, UnityPlayerActivity.class);
        startActivity(intent);


    }

    public String getAttributesUser(){
        String res = null;
        Call<UserAttributes> getAttributesUserCall = myapirest.userAttributes(id);

        getAttributesUserCall.enqueue(new Callback<UserAttributes>() {
            @Override
            public void onResponse(Call<UserAttributes> call, Response<UserAttributes> response) {
                if(response.isSuccessful()){
                    data = response.body();
                    Log.i("Estoy en la data: ", String.valueOf(data.getAttack()));
                }
                else{
                    Log.i("Estoy en el else: ", response.message());
                }
            }

            @Override
            public void onFailure(Call<UserAttributes> call, Throwable t) {
                Log.e("Estoy en el failure: ", t.getMessage());
            }
        });
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            res = objectMapper.writeValueAsString(data);
            Log.i("JSON String: ", res);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return res;
    }
}
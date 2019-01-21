package edu.upc.eetac.dsa;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;

public class SplashActiviy extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_activiy);

        final SharedPreferences sharedPref =
                PreferenceManager.getDefaultSharedPreferences(this);
        boolean registered = sharedPref.getBoolean("registered",false);

        final Class dest;
        if(!registered){
            dest = LoginActivity.class;
        }else{
            dest = MainActivity.class;
        }

        final Intent intent = new Intent(this,dest);
        Thread welcomeThread = new Thread() {

            @Override
            public void run() {
                try {
                    super.run();
                    sleep(2000);  //Delay of 10 seconds
                } catch (Exception e) {

                } finally {
                    startActivity(intent);
                    finish();
                }
            }
        };
        welcomeThread.start();




    }
}

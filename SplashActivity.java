package com.dit.thecampusculinaries;

import android.app.FragmentManager;
import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import utility.PrefManager;

public class SplashActivity extends AppCompatActivity {

    private PrefManager prefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        prefs = new PrefManager(SplashActivity.this);
        hideActionBar();

        Thread timer = new Thread() {

            public void run() {

                try {
                    sleep(3000);


                } catch (Exception e) {

                } finally {

                    try {

                        if (!prefs.isLoggedIn()) {
                            Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                            startActivity(intent);
                            finish();
                        }else{
                            if(prefs.getUserType().equalsIgnoreCase("normal")) {
                                Intent intent = new Intent(SplashActivity.this, SecondActivity.class);
                                startActivity(intent);
                                finish();
                            }else  if(prefs.getUserType().equalsIgnoreCase("admin")){
                                Intent intent = new Intent(SplashActivity.this, AdminActivity.class);
                                startActivity(intent);
                                finish();
                            }
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }


                }
            }
        };

        timer.start();

    }

    private void hideActionBar() {
        setContentView(R.layout.activity_splash);
        final ActionBar abar = getSupportActionBar();
        assert abar != null;
        abar.hide();

    }
}

package com.team19.cs2340;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;

public class WelcomeScreen extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome_screen);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.welcome_screen, menu);
        return true;
    }
    
    public void gotoLogin(View v) {
		Intent intent = new Intent(WelcomeScreen.this, LogInActivity.class);
		startActivity(intent);
    }
    
    public void gotoRegister(View v) {
		Intent intent = new Intent(WelcomeScreen.this, RegistrationActivity.class);
		startActivity(intent);
    }
    
}

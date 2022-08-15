package com.mcvicar.spacewar;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(new SpaceWar(this));
    }

    protected void onDestroy(){
        super.onDestroy();
    }

    protected void onPause() {
        super.onPause();
    }
}
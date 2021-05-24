package com.trainingexample.convertapitosqlite;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.trainingexample.convertapitosqlite.data.DbHelper;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        DbHelper dbHelper = new DbHelper(this);
    }

    @Override
    protected void onResume() {
        super.onResume();

    }
}
package com.example.mysavings;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    DatabaseHelper mDatabaseHelper = new DatabaseHelper(this);
    private Button converterBTN, mySavingsBTN, optionsBTN;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mDatabaseHelper.initiateData(getString(R.string.EUR), 0.0, 1.0, 1.14, 0.9);
        mDatabaseHelper.initiateData(getString(R.string.USD), 0.0, 0.88, 1.0, 0.8);
        mDatabaseHelper.initiateData(getString(R.string.GBP), 0.0, 1.1, 1.26, 1.0);

        try {
            this.getSupportActionBar().hide();
        } catch (NullPointerException e){}

        TextView toolbartitle = findViewById(R.id.toolbar_title);
        toolbartitle.setText(R.string.Home);


        converterBTN = findViewById(R.id.converterBTN);
        mySavingsBTN = findViewById(R.id.mySavingsBTN);
        optionsBTN = findViewById(R.id.optionsBTN);

        converterBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, ConverterActivity.class);
                startActivity(i);
            }
        });

        mySavingsBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, SavingsActivity.class);
                startActivity(i);
            }
        });

        optionsBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, OptionsActivity.class);
                startActivity(i);
            }
        });
    }
}

package com.example.mysavings;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class SavingsActivity extends AppCompatActivity {

    private TextView homeBTN, converterBTN;
    private Button addBTN, mySavingsBTN, removeBTN;
    private EditText input;

    DatabaseHelper mDatabaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_savings);

        mDatabaseHelper = new DatabaseHelper(this);

        try {
            this.getSupportActionBar().hide();
        } catch (NullPointerException e){}

        TextView toolbartitle = findViewById(R.id.toolbar_title);
        toolbartitle.setText(R.string.MySavings);

        String[] arraySpinner = new String[] {
                getString(R.string.EUR), getString(R.string.USD), getString(R.string.GBP)
        };
        Spinner s = findViewById(R.id.currency);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, arraySpinner);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        s.setAdapter(adapter);

        homeBTN = findViewById(R.id.homeBTN);
        converterBTN = findViewById(R.id.converterBTN);

        homeBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(SavingsActivity.this, MainActivity.class);
                startActivity(i);
            }
        });

        converterBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(SavingsActivity.this, ConverterActivity.class);
                startActivity(i);
            }
        });

        input = findViewById(R.id.inputTXT);

        addBTN = findViewById(R.id.addBTN);
        mySavingsBTN = findViewById(R.id.mySavingsBTN);
        removeBTN = findViewById(R.id.removeBTN);

        addBTN.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Spinner mySpinner = findViewById(R.id.currency);
                String text = mySpinner.getSelectedItem().toString();
                if(input.getText().length() > 0) {
                    mDatabaseHelper.updateData(text, String.valueOf(input.getText()), true);
                    input.getText().clear();
                    Toast.makeText(getApplicationContext(), getString(R.string.SavingsAdded), Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getApplicationContext(), getString(R.string.EnterValue), Toast.LENGTH_SHORT).show();
                }
            }
        });

        removeBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Spinner mySpinner = findViewById(R.id.currency);
                String text = mySpinner.getSelectedItem().toString();
                if(input.getText().length() > 0) {
                    mDatabaseHelper.updateData(text, String.valueOf(input.getText()), false);
                    input.getText().clear();
                    Toast.makeText(getApplicationContext(), getString(R.string.SavingsRemoved), Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getApplicationContext(), getString(R.string.EnterValue), Toast.LENGTH_SHORT).show();
                }
            }
        });

        mySavingsBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(SavingsActivity.this, ListDataActivity.class);
                startActivity(i);
            }
        });

    }
}

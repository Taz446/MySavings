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

import java.text.DecimalFormat;

public class ConverterActivity extends AppCompatActivity {

    private TextView homeBTN, savingsBTN, outputTXT;
    private Button convertBTN, convertSavingsBTN;
    private EditText input;

    DatabaseHelper mDatabaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_converter);

        mDatabaseHelper = new DatabaseHelper(this);

        try {
            this.getSupportActionBar().hide();
        } catch (NullPointerException e){}

        TextView toolbartitle = findViewById(R.id.toolbar_title);
        toolbartitle.setText(R.string.Converter);

        String[] arraySpinner = new String[] {
                getString(R.string.EUR), getString(R.string.USD), getString(R.string.GBP)
        };
        Spinner c1 = findViewById(R.id.currencyOne);
        Spinner c2 = findViewById(R.id.currencyTwo);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, arraySpinner);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        c1.setAdapter(adapter);
        c2.setAdapter(adapter);

        homeBTN = findViewById(R.id.homeBTN);
        savingsBTN = findViewById(R.id.mySavingsBTN);

        homeBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ConverterActivity.this, MainActivity.class);
                startActivity(i);
            }
        });

        savingsBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ConverterActivity.this, SavingsActivity.class);
                startActivity(i);
            }
        });

        input = findViewById(R.id.inputTXT);

        convertBTN = findViewById(R.id.convertBTN);
        convertSavingsBTN = findViewById(R.id.convertSavingsBTN);

        outputTXT = findViewById(R.id.outPutTXT);

        convertBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Double ratio;
                Double value;
                Spinner currency1 = findViewById(R.id.currencyOne);
                String cr = currency1.getSelectedItem().toString();
                Spinner currency2 = findViewById(R.id.currencyTwo);
                String cr2 = currency2.getSelectedItem().toString();

                if(input.getText().length() > 0) {
                    ratio = mDatabaseHelper.getExchangeRatio(cr, cr2);
                    value = Double.parseDouble(String.valueOf(input.getText()));

                    String output = "= " + df2.format(value*ratio);

                    outputTXT.setText(output);
                    input.getText().clear();
                } else {
                    Toast.makeText(getApplicationContext(), getString(R.string.EnterValue), Toast.LENGTH_SHORT).show();
                }
            }
        });

        convertSavingsBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Double ratio;
                Double value;
                Spinner currency1 = findViewById(R.id.currencyOne);
                String cr = currency1.getSelectedItem().toString();
                Spinner currency2 = findViewById(R.id.currencyTwo);
                String cr2 = currency2.getSelectedItem().toString();

                if(input.getText().length() > 0) {
                    ratio = mDatabaseHelper.getExchangeRatio(cr, cr2);
                    value = Double.parseDouble(String.valueOf(input.getText()));

                    String output = "= " + df2.format(value*ratio);

                    outputTXT.setText(output);

                    boolean convert = mDatabaseHelper.convertSavings(cr, value, cr2, value*ratio);

                    if(convert) {
                        Toast.makeText(getApplicationContext(), getString(R.string.ConvertSuccess), Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getApplicationContext(), getString(R.string.ConvertFail), Toast.LENGTH_SHORT).show();
                    }

                } else {
                    Toast.makeText(getApplicationContext(), getString(R.string.EnterValue), Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    private static DecimalFormat df2 = new DecimalFormat("#.##");
}

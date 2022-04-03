package com.example.mysavings;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.Locale;

public class OptionsActivity extends AppCompatActivity {

    private Button confirmBTN, exitBTN;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_options);

        try {
            this.getSupportActionBar().hide();
        } catch (NullPointerException e){}

        TextView toolbartitle = findViewById(R.id.toolbar_title);
        toolbartitle.setText(R.string.Options);

        String[] arraySpinner = new String[] {
                getString(R.string.English), getString(R.string.Greek)
        };

        Spinner s = findViewById(R.id.language);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, arraySpinner);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        s.setAdapter(adapter);

        confirmBTN = findViewById(R.id.confirmBTN);
        exitBTN = findViewById(R.id.exitBTN);

        exitBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(OptionsActivity.this, MainActivity.class);
                startActivity(i);
            }
        });


        confirmBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Spinner mySpinner = findViewById(R.id.language);
                String text = mySpinner.getSelectedItem().toString();

                if (text.equals(getString(R.string.English))){
                    Locale locale = new Locale("en");
                    Locale.setDefault(locale);
                    Configuration config = getBaseContext().getResources().getConfiguration();
                    config.locale = locale;
                    getBaseContext().getResources().updateConfiguration(config,
                            getBaseContext().getResources().getDisplayMetrics());
                    Intent intent = new Intent(OptionsActivity.this, MainActivity.class);
                    startActivity(intent);
                } else {
                    Locale locale = new Locale("el");
                    Locale.setDefault(locale);
                    Configuration config = getBaseContext().getResources().getConfiguration();
                    config.locale = locale;
                    getBaseContext().getResources().updateConfiguration(config,
                            getBaseContext().getResources().getDisplayMetrics());
                    Intent intent = new Intent(OptionsActivity.this, MainActivity.class);
                    startActivity(intent);
                }
            }
        });
    }
}

package com.example.mysavings;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class ListDataActivity extends AppCompatActivity {

    private static final String TAG = "ListDataActivity";

    DatabaseHelper mDatabaseHelper = new DatabaseHelper(this);

    private ListView mListView;
    private TextView back;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_layout);

        try {
            this.getSupportActionBar().hide();
        } catch (NullPointerException e){}

        TextView toolbartitle = findViewById(R.id.toolbar_title);
        toolbartitle.setText(R.string.MySavings);

        mListView = findViewById(R.id.savingsList);
        back = findViewById(R.id.backBTN);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ListDataActivity.this, SavingsActivity.class);
                startActivity(i);
            }
        });

        populateListView();
    }

    private void populateListView() {
        Cursor cursor = mDatabaseHelper.allData();

        ArrayList<String> listData = new ArrayList<>();
        while(cursor.moveToNext()) {
            String data = cursor.getString(0) + getString(R.string.Balance) + " " + df2.format(Double.parseDouble(cursor.getString(1)));
            listData.add(data);
        }

        ListAdapter adapter = new ArrayAdapter<>(this,android.R.layout.simple_list_item_1, listData);
        mListView.setAdapter(adapter);
    }

    private static DecimalFormat df2 = new DecimalFormat("#.##");
}

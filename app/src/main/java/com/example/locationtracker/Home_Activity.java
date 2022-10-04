package com.example.locationtracker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Home_Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Button bluetooth_btn=findViewById(R.id.bluetooth_button);
        Button loc_btn=findViewById(R.id.loc_button);
        bluetooth_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Home_Activity.this,MainActivity.class));
            }
        });
        loc_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Home_Activity.this,Loc_Track_Activity.class));
            }
        });
    }
}
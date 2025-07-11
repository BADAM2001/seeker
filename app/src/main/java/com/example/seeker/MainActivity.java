package com.example.seeker;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize buttons
        Button btnBattery = findViewById(R.id.btn_battery);
        Button btnTemperature = findViewById(R.id.btn_temperature);
        Button btnLogs = findViewById(R.id.btn_logs);

        // Set click listeners
        btnBattery.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, BatteryActivity.class);
            startActivity(intent);
        });

        btnTemperature.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, TemperatureActivity.class);
            startActivity(intent);
        });

        btnLogs.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, LogsActivity.class);
            startActivity(intent);
        });
    }
}
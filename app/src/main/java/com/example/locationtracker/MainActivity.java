package com.example.locationtracker;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.BatteryManager;
import android.os.Bundle;
import android.provider.SyncStateContract;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

import java.util.Set;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private BluetoothAdapter BA;
    Button bt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BA = BluetoothAdapter.getDefaultAdapter();
        bt=findViewById(R.id.button);
        bt.setOnClickListener(this);
    }


    //Call this method to check if phone is charging
    public void PhoneCharging(){
            IntentFilter ifilter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
            Intent batteryStatus = this.registerReceiver(null, ifilter);
            int status = batteryStatus.getIntExtra(BatteryManager.EXTRA_STATUS, -1);
            boolean isCharging = status == BatteryManager.BATTERY_STATUS_CHARGING ;
            if (isCharging){
                Toast.makeText(getApplicationContext(), "Detected!! Device is charging" , Toast.LENGTH_LONG).show();
// Are we charging
                BluetoothOn();
            }else{
                Toast.makeText(getApplicationContext(), "Detected!! Device is not charging", Toast.LENGTH_LONG).show();
            }
    }


//Call this method when Charging device detected Bluetooth
    @SuppressLint("MissingPermission")
    public void BluetoothOn() {
        BluetoothManager bluetoothManager = getSystemService(BluetoothManager.class);
        BluetoothAdapter bluetoothAdapter = bluetoothManager.getAdapter();
        if (bluetoothAdapter == null) {
            Toast.makeText(getApplicationContext(), "This feature cannot work on this device because it does not support bluetooth", Toast.LENGTH_LONG).show();
            // Device doesn't support Bluetooth
        }
            else if (!BA.isEnabled()) {
                BA.enable();
                Toast.makeText(getApplicationContext(), "Bluetooth turned" , Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(getApplicationContext(), "Bluetooth is already on", Toast.LENGTH_LONG).show();
            }

    }

    @Override
    public void onClick(View view) {
        if (view==bt){
            PhoneCharging();
        }
    }
}


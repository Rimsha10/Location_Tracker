package com.example.locationtracker;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AlertDialogLayout;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.text.Html;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class Loc_Track_Activity extends AppCompatActivity implements View.OnClickListener {
    TextView latitude, longitude;
    FusedLocationProviderClient fusedLocationProviderClient;
    private Button bt;
    ImageView image;
    Dialog alertDialog;
    Integer locToken;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        locToken=0;
        image = new ImageView(this);
        image.setImageResource(R.drawable.adim);
        setContentView(R.layout.activity_loc_track);
        //latitude
        latitude = findViewById(R.id.latitude);
        //latitude
        longitude = findViewById(R.id.longitude);
        bt = findViewById(R.id.lt_locbtn);
        System.out.println(bt);
        bt.setOnClickListener(this);
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
    }

    @Override
    public void onClick(View view) {
        if (view == bt) {
            //check location permission , if denied
            if (ActivityCompat.checkSelfPermission(Loc_Track_Activity.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                if ((ActivityCompat.shouldShowRequestPermissionRationale(Loc_Track_Activity.this,
                        Manifest.permission.ACCESS_COARSE_LOCATION)) || (locToken==0)){
                    // Show an explanation to the user *asynchronously* -- don't block
                    // this thread waiting for the user's response! After the user
                    // sees the explanation, try again to request the permission.
                    Dialog builder = new Dialog(Loc_Track_Activity.this);
                    builder.setContentView(R.layout.alert_dialog);
                    //builder.getWindow().setLayout(700,1000);
                    Button alert_pos_btn=builder.findViewById(R.id.alert_pos_button);
                    alert_pos_btn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            ActivityCompat.requestPermissions(Loc_Track_Activity.this,
                                    new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},
                                    100);
                            builder.dismiss();

                        }
                    });

                    Button alert_neg_btn= builder.findViewById(R.id.alert_neg_button);
                    alert_neg_btn.setOnClickListener(new View.OnClickListener() {
                       @Override
                       public void onClick(View view) {
                           Toast.makeText(Loc_Track_Activity.this, "Sorry the app cannot work without location access", Toast.LENGTH_LONG).show();
                           builder.dismiss();
                       }
                    });
                    builder.show();
                    locToken+=1;

                } else {
                    Toast.makeText(Loc_Track_Activity.this, "Sorry the app cannot work without location access", Toast.LENGTH_LONG).show();
                }

            }//if allowed
            if (ActivityCompat.checkSelfPermission(Loc_Track_Activity.this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                fusedLocationProviderClient.getLastLocation().addOnCompleteListener(new OnCompleteListener<Location>() {
                    @Override
                    public void onComplete(@NonNull Task<Location> task) {
                        Location location = task.getResult();
                        if (location != null) {
                            try {
                                Geocoder geocoder = new Geocoder(Loc_Track_Activity.this, Locale.getDefault());
                                List<Address> addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
                                latitude.setText(Html.fromHtml("<font color='#003f7f'> Latitude: </b></font>" + addresses.get(0).getLatitude()));
                                longitude.setText(Html.fromHtml("<font color='#003f7f'> Longitude: </b></font>" + addresses.get(0).getLongitude()));
                            } catch (IOException e) {
                                e.printStackTrace();
                            }

                        } else {
                            Toast.makeText(Loc_Track_Activity.this, "This app uses your location. Please enable your location", Toast.LENGTH_LONG).show();
                        }
                    }
                });
            }
        }
    }
}
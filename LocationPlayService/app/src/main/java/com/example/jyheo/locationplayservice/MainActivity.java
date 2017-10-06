package com.example.jyheo.locationplayservice;

import android.Manifest;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.support.design.widget.Snackbar;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;

public class MainActivity extends AppCompatActivity {
    protected static final String TAG = "MainActivity";

    protected Location mLastLocation;

    protected String mLatitudeLabel;
    protected String mLongitudeLabel;
    protected TextView mLatitudeText;
    protected TextView mLongitudeText;
    protected TextView mAddressText;

    private FusedLocationProviderClient mFusedLocationClient;
    private static final int RC_LOCATION = 1;
    private static final int RC_LOCATION_UPDATE = 2;

    protected LocationCallback mLocationCallback;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mLatitudeLabel = getResources().getString(R.string.latitude_label);
        mLongitudeLabel = getResources().getString(R.string.longitude_label);
        mLatitudeText = (TextView) findViewById((R.id.latitude_text));
        mLongitudeText = (TextView) findViewById((R.id.longitude_text));
        mAddressText = (TextView) findViewById(R.id.text_address);

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        Button btn = (Button)findViewById(R.id.btn_get_last_location);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getLastLocation();
            }
        });
        btn = (Button)findViewById(R.id.btn_start_update);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startLocationUpdate();
            }
        });
        btn = (Button)findViewById(R.id.btn_stop_update);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stopLocationUpdate();
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        // Forward results to EasyPermissions
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    protected void updateUI() {
        mLatitudeText.setText(String.format(Locale.ENGLISH, "%s: %f",
                mLatitudeLabel,
                mLastLocation.getLatitude()));
        mLongitudeText.setText(String.format(Locale.ENGLISH, "%s: %f",
                mLongitudeLabel,
                mLastLocation.getLongitude()));
    }

    @SuppressWarnings("MissingPermission")
    @AfterPermissionGranted(RC_LOCATION) // automatically re-invoke this method after getting the required permission
    public void getLastLocation() {
        String[] perms = {Manifest.permission.ACCESS_FINE_LOCATION};
        if (EasyPermissions.hasPermissions(this, perms)) {
            mFusedLocationClient.getLastLocation()
                    .addOnCompleteListener(this, new OnCompleteListener<Location>() {
                        @Override
                        public void onComplete(@NonNull Task<Location> task) {
                            if (task.isSuccessful() && task.getResult() != null) {
                                mLastLocation = task.getResult();
                                updateUI();
                            } else {
                                Log.w(TAG, "getLastLocation:exception", task.getException());
                                showSnackbar(getString(R.string.no_location_detected));
                            }
                        }
                    });
        } else {
            // Do not have permissions, request them now
            EasyPermissions.requestPermissions(this,
                    "This app needs access to your location to know where you are.",
                    RC_LOCATION, perms);
        }
    }

    @SuppressWarnings("MissingPermission")
    @AfterPermissionGranted(RC_LOCATION_UPDATE)
    public void startLocationUpdate() {
        LocationRequest locRequest = new LocationRequest();
        locRequest.setInterval(10000);
        locRequest.setFastestInterval(5000);
        locRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

        mLocationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                super.onLocationResult(locationResult);

                mLastLocation = locationResult.getLastLocation();
                updateUI();
            }
        };


        String[] perms = {Manifest.permission.ACCESS_FINE_LOCATION};
        if (EasyPermissions.hasPermissions(this, perms)) {
            mFusedLocationClient.requestLocationUpdates(locRequest, mLocationCallback, Looper.myLooper());
        } else {
            // Do not have permissions, request them now
            EasyPermissions.requestPermissions(this,
                    "This app needs access to your location to know where you are.",
                    RC_LOCATION_UPDATE, perms);
        }
    }

    public void stopLocationUpdate() {
        mFusedLocationClient.removeLocationUpdates(mLocationCallback);
    }

    public void toAddress(View view) {
        try {
            Geocoder geocoder = new Geocoder(this, Locale.KOREA);
            List<Address> addresses = geocoder.getFromLocation(mLastLocation.getLatitude(),mLastLocation.getLongitude(),1);
            if (addresses.size() >0) {
                Address address = addresses.get(0);
                mAddressText.setText(String.format("\n[%s]\n[%s]\n[%s]\n[%s]",
                        address.getFeatureName(),
                        address.getThoroughfare(),
                        address.getLocality(),
                        address.getCountryName()
                ));
            }
        } catch (IOException e) {
            Log.e(TAG, "Failed in using Geocoder",e);
        }
    }

    private void showSnackbar(final String text) {
        View container = findViewById(android.R.id.content);
        if (container != null) {
            Snackbar.make(container, text, Snackbar.LENGTH_LONG).show();
        }
    }
}

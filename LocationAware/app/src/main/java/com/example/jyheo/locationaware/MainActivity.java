package com.example.jyheo.locationaware;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;

public class MainActivity extends AppCompatActivity implements SensorEventListener {

    private final static int MIN_TIME = 50;
    private final static int MIN_DIST = 100;
    private final static int MY_PERMISSIONS_REQUEST_FINE_LOCATION = 1;

    private LocationManager mLm;
    private TextView mLocationText;
    private LocationListener mLocationListener;

    private TextView mAccel;
    private TextView mGyro;
    private TextView mMagnet;

    SensorManager mSm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mLocationText = (TextView) findViewById(R.id.tvLocation);
        mAccel = (TextView) findViewById(R.id.tvAccelerometer);
        mGyro = (TextView) findViewById(R.id.tvGyroscope);
        mMagnet = (TextView) findViewById(R.id.tvMagnetic);

        mLm = (LocationManager) getSystemService(LOCATION_SERVICE);
    }

    @Override
    @AfterPermissionGranted(MY_PERMISSIONS_REQUEST_FINE_LOCATION)
    protected void onStart() {
        super.onStart();
        mLocationListener = new LocationListener() {
            public void onLocationChanged(Location location) {
                //when location changed change text
                mLocationText.setText("Location is long:" + round(location.getLongitude()) +
                        " and lat:" + round(location.getLatitude()));
            }

            public void onStatusChanged(String provider, int status, Bundle extras) {}

            public void onProviderEnabled(String provider) {}

            public void onProviderDisabled(String provider) {}
        };

        //note that LocationManager.NETWORK_PROVIDER will not work on the emulator because mock
        //location data is injected as GPS location data

        String[] perms = {Manifest.permission.ACCESS_FINE_LOCATION};
        if (EasyPermissions.hasPermissions(this, perms)) {
            // Already have permission, do the thing
            try {
                mLm.requestLocationUpdates(LocationManager.GPS_PROVIDER, MIN_TIME, MIN_DIST,
                        mLocationListener);
            } catch(SecurityException e) {  // just remove warning message!
            }
        } else {
            // Do not have permissions, request them now
            EasyPermissions.requestPermissions(this, "I need the PERMISSION!",
                    MY_PERMISSIONS_REQUEST_FINE_LOCATION, perms);
        }

        mSm = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        Sensor accelSensor = mSm.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        Sensor gyroSensor = mSm.getDefaultSensor(Sensor.TYPE_GYROSCOPE);
        Sensor magnetSensor = mSm.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
        mSm.registerListener(this, accelSensor, SensorManager.SENSOR_DELAY_NORMAL);
        mSm.registerListener(this, gyroSensor, SensorManager.SENSOR_DELAY_NORMAL);
        mSm.registerListener(this, magnetSensor, SensorManager.SENSOR_DELAY_NORMAL);

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        // Forward results to EasyPermissions
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        switch(event.sensor.getType()){
            case Sensor.TYPE_ACCELEROMETER:
                mAccel.setText("Force on x: " + event.values[0] + " on y " + event.values[1] + " on z:" + event.values[2]);
                break;
            case Sensor.TYPE_GYROSCOPE:
                mGyro.setText("Rate of rotation on x: " + event.values[0] + " on y " + event.values[1] + " on z:" + event.values[2]);
                break;
            case Sensor.TYPE_MAGNETIC_FIELD:
                mMagnet.setText("Strength on x: " + event.values[0] + " on y " + event.values[1] + " on z:" + event.values[2]);
                break;
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    @Override
    protected void onStop() {
        super.onStop();
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            mLm.removeUpdates(mLocationListener);
        }

        mSm.unregisterListener(this);
    }

    private String round(double toRound) {
        return String.format("%.2f", toRound);
    }
}

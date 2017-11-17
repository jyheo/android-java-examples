package com.example.jyheo.sensortest;

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
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import java.util.List;

import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;

import static java.lang.Math.round;

public class MainActivity extends AppCompatActivity implements LocationListener, SensorEventListener {

    private final static int MIN_TIME = 50;
    private final static int MIN_DIST = 100;
    private final static int MY_PERMISSIONS_REQUEST_FINE_LOCATION = 1;

    private LocationManager mLm;
    private TextView mLocationText;

    private TextView mAccel;
    private TextView mGyro;
    private TextView mMagnet;
    private TextView mSensorList;

    SensorManager mSm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mLocationText = (TextView) findViewById(R.id.tvLocation);
        mAccel = (TextView) findViewById(R.id.tvAccelerometer);
        mGyro = (TextView) findViewById(R.id.tvGyroscope);
        mMagnet = (TextView) findViewById(R.id.tvMagnetic);
        mSensorList = (TextView) findViewById(R.id.tvSensorList);

        mLm = (LocationManager) getSystemService(LOCATION_SERVICE);
        mSm = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        List<Sensor> sensors = mSm.getSensorList(Sensor.TYPE_ALL);
        StringBuilder sb = new StringBuilder();
        for (Sensor s : sensors)
            sb.append(s.getName()).append(":").append(s.getType()).append("\n");
        mSensorList.setText(sb);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        // Forward results to EasyPermissions
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    @Override
    @SuppressWarnings("MissingPermission")
    @AfterPermissionGranted(MY_PERMISSIONS_REQUEST_FINE_LOCATION)
    protected void onStart() {
        super.onStart();

        String[] perms = {Manifest.permission.ACCESS_FINE_LOCATION};
        if (EasyPermissions.hasPermissions(this, perms)) {
            // Already have permission, do the thing
            try {
                Location loc = mLm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                onLocationChanged(loc);
                mLm.requestLocationUpdates(LocationManager.GPS_PROVIDER, MIN_TIME, MIN_DIST,
                        this);
            } catch(SecurityException e) {  // just remove warning message!
            }
        } else {
            // Do not have permissions, request them now
            EasyPermissions.requestPermissions(this, "I need the PERMISSION!",
                    MY_PERMISSIONS_REQUEST_FINE_LOCATION, perms);
        }

        Sensor accelSensor = mSm.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        Sensor gyroSensor = mSm.getDefaultSensor(Sensor.TYPE_GYROSCOPE);
        Sensor magnetSensor = mSm.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);

        mSm.registerListener(this, accelSensor, SensorManager.SENSOR_DELAY_NORMAL);
        mSm.registerListener(this, gyroSensor, SensorManager.SENSOR_DELAY_NORMAL);
        mSm.registerListener(this, magnetSensor, SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    public void onLocationChanged(Location location) {
        //when location changed change text
        mLocationText.setText("Location is long:" + location.getLongitude() +
                " and lat:" + location.getLatitude());
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {}

    @Override
    public void onProviderEnabled(String provider) {}

    @Override
    public void onProviderDisabled(String provider) {}

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
    public void onAccuracyChanged(Sensor sensor, int accuracy) {}

    @Override
    @SuppressWarnings("MissingPermission")
    protected void onStop() {
        super.onStop();
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            mLm.removeUpdates(this);
        }

        mSm.unregisterListener(this);
    }

}

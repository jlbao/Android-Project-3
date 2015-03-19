package com.starboardland.pedometer;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.startboardland.common.Segment;
import com.startboardland.common.SegmentDAO;
import com.startboardland.common.Task;

import java.util.Date;
import java.util.Timer;

/**
 * http://web.cs.wpi.edu/~emmanuel/courses/cs528/S15/projects/project3/project3.html
 */
public class CounterActivity extends FragmentActivity implements SensorEventListener {

    GoogleMap mMap;

    GoogleMap.OnMyLocationChangeListener myLocationChangeListener;

    public LinearLayout linearLayout;

    public SegmentDAO dao;

    public float currentStepCount = 0;

    public float totalStepCount = 0;

    public float prevStepCount = -1;

    public SensorManager sensorManager;
    private TextView countView;
    boolean activityRunning;
    public Timer timer;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_map);
        countView = (TextView) findViewById(R.id.count);
        linearLayout = (LinearLayout) findViewById(R.id.linearLayout);

        // open database connection
        dao = new SegmentDAO(getApplicationContext());
        dao.open();

        // set map
        MapFragment mapFragment = (MapFragment) getFragmentManager()
                .findFragmentById(R.id.map);
        mMap = mapFragment.getMap();
        mMap.setMyLocationEnabled(true);
        mMap.getUiSettings().setMyLocationButtonEnabled(false);
        mMap.getUiSettings().setZoomControlsEnabled(false);
        myLocationChangeListener = new GoogleMap.OnMyLocationChangeListener() {
            @Override
            public void onMyLocationChange(Location location) {
                LatLng loc = new LatLng(location.getLatitude(), location.getLongitude());
                //Marker mMarker = mMap.addMarker(new MarkerOptions().position(loc));
                if (mMap != null) {
                    mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(loc, 16.0f));
                }
            }
        };
        mMap.setOnMyLocationChangeListener(myLocationChangeListener);

        // set step sensor
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        activityRunning = true;
        Sensor countSensor = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER);
        if (countSensor != null) {
            sensorManager.registerListener(this, countSensor, SensorManager.SENSOR_DELAY_UI);
        } else {
            Toast.makeText(this, "Count sensor not available!", Toast.LENGTH_LONG).show();
        }

        //set timer
        timer = new Timer();
        timer.schedule(new Task(this), new Date(new Date().getTime() + 1000 * 8), 8000);
    }

    // write database
    protected void writeDatabase() {
        Segment seg = new Segment(1, 201);
        dao.createSegment(seg);
    }

    @Override
    protected void onResume() {
        super.onResume();
        activityRunning = true;
        Sensor countSensor = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER);
        if (countSensor != null) {
            sensorManager.registerListener(this, countSensor, SensorManager.SENSOR_DELAY_UI);
        } else {
            Toast.makeText(this, "Count sensor not available!", Toast.LENGTH_LONG).show();
        }
    }


    @Override
    protected void onPause() {
        super.onPause();
        activityRunning = false;
        // if you unregister the last listener, the hardware will stop detecting step events
        sensorManager.unregisterListener(this);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (activityRunning) {
            if (prevStepCount == -1) {
                prevStepCount = event.values[0] - 1;
                totalStepCount = 1;
            }
            currentStepCount = event.values[0] - totalStepCount - prevStepCount;
            countView.setText(String.valueOf(currentStepCount));
        }
    }


    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
    }


    public void resetCount() {
        currentStepCount = 0;
        float totalStepCount = 0;
        float prevStepCount = -1;
    }

}
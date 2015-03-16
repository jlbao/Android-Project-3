package com.starboardland.pedometer;

import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Gravity;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.startboardland.common.Segment;
import com.startboardland.common.SegmentDAO;

/**
 * http://web.cs.wpi.edu/~emmanuel/courses/cs528/S15/projects/project3/project3.html
 */
public class CounterActivity extends FragmentActivity {

    GoogleMap mMap;

    GoogleMap.OnMyLocationChangeListener myLocationChangeListener;

    LinearLayout linearLayout;

    SegmentDAO dao;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_map);
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
                Marker mMarker = mMap.addMarker(new MarkerOptions().position(loc));
                if (mMap != null) {
                    mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(loc, 16.0f));
                }
            }
        };
        mMap.setOnMyLocationChangeListener(myLocationChangeListener);


        linearLayout = (LinearLayout) findViewById(R.id.linearLayout);
        int height = getResources().getDisplayMetrics().heightPixels;
        for (int i = 0; i < 8; i++) {
            TextView textView = new TextView(getApplicationContext());
            textView.setText("hello: " + i);
            linearLayout.addView(textView);
            LinearLayout.LayoutParams p = (LinearLayout.LayoutParams) textView.getLayoutParams();
            p.gravity = Gravity.CENTER_HORIZONTAL;
            textView.setLayoutParams(p);
        }
        Toast.makeText(this.getApplicationContext(), "hello world!!!", Toast.LENGTH_SHORT).show();


        // write database
        dao = new SegmentDAO(getApplicationContext());
        dao.open();
        Segment seg = new Segment(1, 201);
        dao.createSegment(seg);
    }
}
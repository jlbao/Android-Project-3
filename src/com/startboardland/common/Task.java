package com.startboardland.common;

import android.view.Gravity;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.starboardland.pedometer.CounterActivity;

import java.util.TimerTask;

/**
 * Created by Jialiang on 3/18/15.
 */
public class Task extends TimerTask {

    int segment_idx = 0;
    CounterActivity activity;

    public Task(CounterActivity activity) {
        this.activity = activity;
    }

    @Override
    public void run() {
        if (segment_idx++ == 8) {
            activity.timer.cancel();
            return;
        }
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                TextView textView = new TextView(activity.getApplicationContext());
                if (segment_idx <= 8) {
                    textView.setText(String.format("Segment %s steps: %s", segment_idx, activity.currentStepCount));
                    Toast.makeText(activity.getApplicationContext(), ((Float) activity.currentStepCount).toString(), Toast.LENGTH_SHORT).show();
                }
                Segment seg = new Segment(segment_idx, (int) activity.currentStepCount);
                activity.dao.createSegment(seg);
                activity.totalStepCount += activity.currentStepCount;
                activity.currentStepCount = 0;
                activity.linearLayout.addView(textView);
                LinearLayout.LayoutParams p = (LinearLayout.LayoutParams) textView.getLayoutParams();
                p.gravity = Gravity.CENTER_HORIZONTAL;
                textView.setLayoutParams(p);
                if (segment_idx == 8) {
                    TextView finalTextView = new TextView(activity.getApplicationContext());
                    finalTextView.setText(String.format("Total Steps: %s", activity.totalStepCount));
                    activity.linearLayout.addView(finalTextView);
                    p = (LinearLayout.LayoutParams) textView.getLayoutParams();
                    p.gravity = Gravity.CENTER_HORIZONTAL;
                    finalTextView.setLayoutParams(p);
                    activity.resetCount();
                    activity.sensorManager.unregisterListener(activity);
                }
            }
        });

    }
}

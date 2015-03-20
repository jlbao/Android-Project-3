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

    CounterActivity activity;

    public Task(CounterActivity activity) {
        this.activity = activity;
    }

    @Override
    public void run() {
        // store to database
        Segment seg = new Segment(activity.segment_idx, (int) activity.currentStepCount);
        activity.dao.createSegment(seg);
        if (activity.segment_idx == 8) {
            activity.resetCount();
            activity.sensorManager.unregisterListener(activity);
            activity.timer.cancel();
            activity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    //display toast
                    Toast.makeText(activity.getApplicationContext(), String.valueOf((int) activity.currentStepCount), Toast.LENGTH_SHORT).show();

                    // create final text view showing total step count
                    TextView finalTextView = new TextView(activity.getApplicationContext());
                    finalTextView.setText(String.format("Total Steps: %s", (int) activity.totalStepCount));
                    activity.linearLayout.addView(finalTextView);
                    LinearLayout.LayoutParams p = (LinearLayout.LayoutParams) activity.textView.getLayoutParams();
                    p.gravity = Gravity.CENTER_HORIZONTAL;
                    finalTextView.setLayoutParams(p);
                }
            });
            return;
        }
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                //display toast
                Toast.makeText(activity.getApplicationContext(), String.valueOf((int) activity.currentStepCount), Toast.LENGTH_SHORT).show();

                // calculate the steps
                activity.segment_idx++;
                activity.totalStepCount += activity.currentStepCount;
                activity.currentStepCount = 0;

                // create new Text view
                String text = "Segment " + activity.segment_idx + " steps: " + 0;
                activity.textView = new TextView(activity.getApplicationContext());
                activity.textView.setText(text);

                activity.linearLayout.addView(activity.textView);
                LinearLayout.LayoutParams p = (LinearLayout.LayoutParams) activity.textView.getLayoutParams();
                p.gravity = Gravity.CENTER_HORIZONTAL;
                activity.textView.setLayoutParams(p);
            }
        });

    }
}

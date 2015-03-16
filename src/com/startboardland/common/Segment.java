package com.startboardland.common;

/**
 * Created by Jialiang on 3/16/15.
 */
public class Segment {
    private int segment_id;
    private int stepCount;

    public Segment(int segment_id, int stepCount) {
        this.segment_id = segment_id;
        this.stepCount = stepCount;
    }

    public int getSegment_id() {
        return segment_id;
    }

    public int getStepCount() {
        return stepCount;
    }

    public void setStepCount(int stepCount) {
        this.stepCount = stepCount;
    }

    public void setSegment_id(int segment_id) {
        this.segment_id = segment_id;
    }
}

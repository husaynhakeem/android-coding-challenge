package com.stashinvest.stashchallenge.util;

import android.view.GestureDetector;
import android.view.MotionEvent;

public class LongPressGestureDetector extends GestureDetector.SimpleOnGestureListener {
    private final Listener listener;

    public LongPressGestureDetector(Listener listener) {
        this.listener = listener;
    }

    @Override
    public void onLongPress(MotionEvent e) {
        if (listener != null) {
            listener.onLongPress();
        }
    }

    public interface Listener {
        public void onLongPress();
    }
}

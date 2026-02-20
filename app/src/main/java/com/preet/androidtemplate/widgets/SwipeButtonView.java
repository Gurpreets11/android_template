package com.preet.androidtemplate.widgets;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.animation.OvershootInterpolator;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.preet.androidtemplate.R;


public class SwipeButtonView extends FrameLayout {

    private ImageView thumb, successIcon;
    private TextView label;
    private ProgressBar progressBar;
    private int maxSwipe;
    private boolean isCompleted = false;

    public interface OnSwipeCompleteListener {
        void onSwipeComplete();
    }

    private OnSwipeCompleteListener listener;

    public SwipeButtonView(Context context, AttributeSet attrs) {
        super(context, attrs);
        inflate(context, R.layout.view_swipe_button, this);
        init();
    }

    private void init() {
        thumb = findViewById(R.id.ivThumb);
        label = findViewById(R.id.txtLabel);
        progressBar = findViewById(R.id.progressBar);
        successIcon = findViewById(R.id.ivSuccess);

        post(() -> maxSwipe = getWidth() - thumb.getWidth());

        thumb.setOnTouchListener((v, event) -> {
            // Prevent ScrollView from stealing gesture
            getParent().requestDisallowInterceptTouchEvent(true);
            if (isCompleted) return false;

            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    return true;
                case MotionEvent.ACTION_MOVE:
                    float moveX = event.getRawX() - thumb.getWidth();
                    moveX = Math.max(0, Math.min(moveX, maxSwipe));
                    thumb.setTranslationX(moveX);
                    label.setAlpha(1 - (moveX / maxSwipe));
                    return true;

                case MotionEvent.ACTION_UP:
                    getParent().requestDisallowInterceptTouchEvent(false);
                    if (thumb.getTranslationX() >= maxSwipe * 0.85f) {
                        completeSwipe();
                    } else {
                        resetSwipe();
                    }
                    return true;
                case MotionEvent.ACTION_CANCEL:

                    getParent().requestDisallowInterceptTouchEvent(false);

                    if (thumb.getTranslationX() >= maxSwipe * 0.85f) {
                        completeSwipe();
                    } else {
                        resetSwipe();
                    }
                    return true;
            }
            return false;
        });
    }

    private void completeSwipe() {
        isCompleted = true;

        thumb.animate()
                .translationX(maxSwipe)
                .setDuration(200)
                .withEndAction(this::showLoading)
                .start();
    }

    private void showLoading() {
        thumb.setVisibility(GONE);
        label.setText("Logging in...");
        label.setAlpha(1f);
        progressBar.setVisibility(VISIBLE);

        if (listener != null) listener.onSwipeComplete();
    }

    /** Call this on API SUCCESS */
    public void showSuccess() {
        progressBar.setVisibility(GONE);
        label.setText("Success");
        successIcon.setVisibility(VISIBLE);

        successIcon.setScaleX(0f);
        successIcon.setScaleY(0f);

        successIcon.animate()
                .scaleX(1f)
                .scaleY(1f)
                .setDuration(300)
                .setInterpolator(new OvershootInterpolator())
                .start();
    }

    /** Call this on API FAILURE */
    public void resetSwipe() {
        isCompleted = false;

        progressBar.setVisibility(GONE);
        successIcon.setVisibility(GONE);
        thumb.setVisibility(VISIBLE);

        thumb.animate().translationX(0).setDuration(300).start();

        label.setText("Swipe to Login");
        label.setAlpha(1f);
    }
    public void setSwipeButtonLabel(String text) {
        label.setText(text);
        label.setAlpha(1f);
    }
    public void setOnSwipeCompleteListener(OnSwipeCompleteListener listener) {
        this.listener = listener;
    }
}



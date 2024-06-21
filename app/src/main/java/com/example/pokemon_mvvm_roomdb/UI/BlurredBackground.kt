package com.example.pokemon_mvvm_roomdb.UI;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.pokemon_mvvm_roomdb.R;

public class BlurredBackground {

    private ViewGroup progressView;
    private boolean isProgressShowing = false;
    private final Activity activity;

    public BlurredBackground(Activity activity) {
        this.activity = activity;
    }

    public void showProgressingView() {
        if (!isProgressShowing) {
            isProgressShowing = true;
            LayoutInflater inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            progressView = (ViewGroup) inflater.inflate(R.layout.progressbar_layout, null);

            // Make the progress view not touchable
            progressView.setClickable(true);
            progressView.setFocusable(true);

            View rootView = activity.findViewById(android.R.id.content).getRootView();
            ViewGroup viewGroup = (ViewGroup) rootView;
            viewGroup.addView(progressView);
        }
    }

    public void hideProgressingView() {
        if (isProgressShowing && progressView != null) {
            View rootView = activity.findViewById(android.R.id.content).getRootView();
            ViewGroup viewGroup = (ViewGroup) rootView;
            viewGroup.removeView(progressView);
            isProgressShowing = false;
        }
    }
}

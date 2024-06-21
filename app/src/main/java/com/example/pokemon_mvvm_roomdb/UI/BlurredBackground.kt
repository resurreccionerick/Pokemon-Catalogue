package com.example.pokemon_mvvm_roomdb.UI

import android.app.Activity
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.pokemon_mvvm_roomdb.R

class BlurredBackground(private val activity: Activity) {
    private var progressView: ViewGroup? = null
    private var isProgressShowing = false

    fun showProgressingView() {
        if (!isProgressShowing) {
            isProgressShowing = true
            val inflater = activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            progressView = inflater.inflate(R.layout.progressbar_layout, null) as ViewGroup

            // Make the progress view not touchable
            progressView?.isClickable = true
            progressView?.isFocusable = true

            val rootView = activity.findViewById<View>(android.R.id.content).rootView
            val viewGroup = rootView as ViewGroup
            viewGroup.addView(progressView)
        }
    }

    fun hideProgressingView() {
        if (isProgressShowing && progressView != null) {
            val rootView = activity.findViewById<View>(android.R.id.content).rootView
            val viewGroup = rootView as ViewGroup
            viewGroup.removeView(progressView)
            isProgressShowing = false
        }
    }
}

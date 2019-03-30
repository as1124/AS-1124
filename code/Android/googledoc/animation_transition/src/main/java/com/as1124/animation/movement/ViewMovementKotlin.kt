package com.as1124.animation.movement

import android.animation.ObjectAnimator
import android.app.Activity
import android.os.Bundle
import android.view.View
import com.as1124.animation.R

class ViewMovementKotlin : Activity() {

    private lateinit var mView: View

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_view_movement);
        mView = findViewById<View>(R.id.view_to_move);
        var animator: ObjectAnimator = ObjectAnimator.ofFloat(mView, View.TRANSLATION_X, 100f)
        animator.duration = 200
        animator.start()
    }
}
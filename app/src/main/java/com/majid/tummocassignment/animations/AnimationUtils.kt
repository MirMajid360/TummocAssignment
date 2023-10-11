package com.majid.tummocassignment.animations

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ObjectAnimator
import android.view.View
import android.view.animation.LinearInterpolator
import android.view.animation.TranslateAnimation
import android.widget.ImageView


class AnimationUtils {
    companion object {

        private var start = 0f
        private var end = 90f
        fun animateDropDownIcon(view: ImageView, setOpen: Boolean) {

            if (setOpen) {
                start = 0f
                end = 90f
            } else {
                start = 90f
                end = 0f
            }

            val rotateAnimation = ObjectAnimator.ofFloat(view, "rotation", start, end)
            rotateAnimation.duration = 100 // Set the duration of the animation in milliseconds
            rotateAnimation.interpolator =
                LinearInterpolator() // Set the animation to use a linear interpolator
            rotateAnimation.start()


        }

        fun slideDown(view: View) {
            val animate = TranslateAnimation(
                0f,
                0f,
                view.height.toFloat(),
                0f
            )
            animate.duration = 500
            animate.fillAfter = true
            view.startAnimation(animate)
        }
        fun slideUp(view: View) {
            val animate = TranslateAnimation(
                0f,
                0f,
                0f,
                view.height.toFloat()
            )
            animate.duration = 500
            animate.fillAfter = true
            view.startAnimation(animate)
        }


        fun viewGoneAnimator(view: View) {
            view.animate()
                .alpha(0f)
                .setDuration(500)
                .setListener(object : AnimatorListenerAdapter() {
                    override fun onAnimationEnd(animation: Animator) {
                        view.visibility = View.GONE
                    }
                })
        }


        fun viewVisibleAnimator(view: View) {
            view.animate()
                .setDuration(500)
                .setListener(object : AnimatorListenerAdapter() {
                    override fun onAnimationEnd(animation: Animator) {
                        view.visibility = View.VISIBLE
                    }
                })
        }


        fun animateViewVisibility(view: View, visibility: Int) {
            // cancel runnning animations and remove and listeners
            view.animate().cancel()
            view.animate().setListener(null)

            // animate making view visible
            if (visibility == View.VISIBLE) {
                view.animate().alpha(1f).start()
                view.visibility = View.VISIBLE
            } else {
                view.animate().setListener(object : AnimatorListenerAdapter() {
                    override fun onAnimationEnd(animation: Animator) {
                        view.visibility = visibility
                    }
                }).alpha(0f).start()
            }
        }



    }
}
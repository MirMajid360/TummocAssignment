package com.majid.tummocassignment.common


import android.view.View
import androidx.core.view.isVisible
import com.majid.tummocassignment.animations.AnimationUtils


/**
 *  functions to manage visibility of Views
 * **/

fun View.showVisibility() {
    if (this.visibility == View.GONE || this.visibility == View.INVISIBLE)
        this.visibility = View.VISIBLE
}


fun View.hideVisibility() {
    if (this.visibility == View.VISIBLE)
        this.visibility = View.GONE
}

fun View.toggleVisibility() {
    if (this.isVisible) {
        this.visibility = View.GONE
    } else {
        this.visibility = View.VISIBLE
    }
}




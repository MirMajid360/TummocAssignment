package com.majid.tummocassignment.utils

import android.annotation.SuppressLint
import android.app.Activity
import android.os.Build
import android.view.Window
import android.view.WindowManager
import androidx.core.content.ContextCompat
import androidx.core.view.WindowCompat
import com.majid.tummocassignment.R

enum class STATUS_BAR_THEME {
    COLOR_THEME, WHITE_THEME, COLOR_WHITE_THEME
}
object ViewUtils {

    @SuppressLint("ObsoleteSdkInt")
    fun setupStatusBar(activity: Activity, theme: STATUS_BAR_THEME) {

        if (theme == STATUS_BAR_THEME.COLOR_THEME) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                val window: Window = activity.window ?: return
                window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
                window.statusBarColor =
                    ContextCompat.getColor(activity, R.color.app_theme_color)
                window.navigationBarColor =
                    ContextCompat.getColor(activity, R.color.app_theme_color)
                WindowCompat.getInsetsController(window, window.decorView).apply {
                    isAppearanceLightStatusBars = false
                }
            }


        } else if (theme == STATUS_BAR_THEME.WHITE_THEME) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                val window: Window = activity.window ?: return
                window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
                window.statusBarColor = ContextCompat.getColor(activity, R.color.white)
                window.navigationBarColor = ContextCompat.getColor(activity, R.color.white)
                WindowCompat.getInsetsController(window, window.decorView).apply {
                    isAppearanceLightStatusBars = true
                }
            }


        } else if (theme == STATUS_BAR_THEME.COLOR_WHITE_THEME) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                val window: Window = activity.window ?: return
                window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
                window.statusBarColor =
                    ContextCompat.getColor(activity, R.color.app_theme_color)
                window.navigationBarColor = ContextCompat.getColor(activity, R.color.white)
                WindowCompat.getInsetsController(window, window.decorView).apply {
                    isAppearanceLightStatusBars = false
                }
            }


        }


    }
}
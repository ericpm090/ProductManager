package com.example.productmanager.domain

import android.content.Context
import android.util.DisplayMetrics
import android.view.WindowManager

class ScreenSizeHelper(private val context: Context) {

    fun getScreenSize(): Pair<Int, Int> {
        val displayMetrics = DisplayMetrics()
        val windowManager = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        windowManager.defaultDisplay.getMetrics(displayMetrics)

        val screenWidth = displayMetrics.widthPixels
        val screenHeight = displayMetrics.heightPixels

        return Pair(screenWidth, screenHeight)
    }
}
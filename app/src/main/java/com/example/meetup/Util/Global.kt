package com.example.meetup.Util

import android.content.res.Resources
import android.view.View

fun Float.fromDpToPx(): Int =
    (this * Resources.getSystem().displayMetrics.density).toInt()
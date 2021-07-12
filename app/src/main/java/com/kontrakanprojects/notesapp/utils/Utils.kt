package com.kontrakanprojects.notesapp.utils

import android.app.Activity
import androidx.core.content.res.ResourcesCompat
import com.kontrakanprojects.notesapp.R
import www.sanju.motiontoast.MotionToast

fun showMessage(
    activity: Activity,
    title: String,
    message: String = "Cek Koneksi Internet Dan Coba Lagi!",
    style: String
) {
    MotionToast.createColorToast(
        activity,
        title,
        message,
        style,
        MotionToast.GRAVITY_BOTTOM,
        MotionToast.LONG_DURATION,
        ResourcesCompat.getFont(activity, R.font.helvetica_regular)
    )
}

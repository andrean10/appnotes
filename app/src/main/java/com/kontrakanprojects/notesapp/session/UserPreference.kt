package com.kontrakanprojects.notesapp.session

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit

internal class UserPreference(context: Context) {
    private var preferences: SharedPreferences =
        context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

    companion object {
        private const val PREFS_NAME = "user_pref"
        private const val KEY_USER_FULLNAME = "user_fullname"
        private const val KEY_OPENAPP = "user_openapp"
    }

    fun setUser(values: String) {
        preferences.edit {
            putString(KEY_USER_FULLNAME, values)
        }
    }

    fun setStateApp(values: Boolean) {
        preferences.edit {
            putBoolean(KEY_OPENAPP, values)
        }
    }

    fun getUser(): String? = preferences.getString(KEY_USER_FULLNAME, "")

    fun getStateApp() = preferences.getBoolean(KEY_OPENAPP, false)
}
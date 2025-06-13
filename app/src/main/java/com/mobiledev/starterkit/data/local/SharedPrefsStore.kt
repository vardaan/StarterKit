package com.mobiledev.starterkit.data.local

import android.content.SharedPreferences
import androidx.core.content.edit

class SharedPrefsStore(private val prefs: SharedPreferences) : KeyValueStore {

    override fun putBool(key: String, value: Boolean) = prefs.edit { putBoolean(key, value) }

    override fun getBool(key: String, default: Boolean) = prefs.getBoolean(key, default)

    override fun putInt(key: String, value: Int) = prefs.edit { putInt(key, value) }

    override fun getInt(key: String, default: Int) = prefs.getInt(key, default)

    override fun putLong(key: String, value: Long) = prefs.edit { putLong(key, value) }

    override fun getLong(key: String, default: Long) = prefs.getLong(key, default)

    override fun putFloat(key: String, value: Float) = prefs.edit { putFloat(key, value) }

    override fun getFloat(key: String, default: Float) = prefs.getFloat(key, default)

    override fun putString(key: String, value: String) = prefs.edit { putString(key, value) }

    override fun getString(key: String, default: String) = prefs.getString(key, default) ?: default
}

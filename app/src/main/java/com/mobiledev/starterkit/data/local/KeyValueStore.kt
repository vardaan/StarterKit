package com.mobiledev.starterkit.data.local

interface KeyValueStore {
    fun putBool(key: String, value: Boolean)
    fun getBool(key: String, default: Boolean = false): Boolean

    fun putInt(key: String, value: Int)
    fun getInt(key: String, default: Int = 0): Int

    fun putLong(key: String, value: Long)
    fun getLong(key: String, default: Long = 0L): Long

    fun putFloat(key: String, value: Float)
    fun getFloat(key: String, default: Float = 0f): Float

    fun putString(key: String, value: String)
    fun getString(key: String, default: String = ""): String
}

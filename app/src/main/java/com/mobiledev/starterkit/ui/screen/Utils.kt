package com.mobiledev.starterkit.ui.screen

object Utils {
    fun formatElapsed(millis: Long): String {
        val minutes = millis / 60_000
        val seconds = (millis / 1_000) % 60
        val millisPart = millis % 1_000
        return "%02d:%02d.%03d".format(minutes, seconds, millisPart)
    }
}

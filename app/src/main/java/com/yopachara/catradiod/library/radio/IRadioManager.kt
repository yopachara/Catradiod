package com.yopachara.catradiod.library.radio


import android.graphics.Bitmap

/**
 * Created by mertsimsek on 03/07/15.
 * Modify to Kotlin by yopachara
 */
interface IRadioManager {

    fun startRadio(streamURL: String)

    fun stopRadio()

    val isPlaying: Boolean

    fun registerListener(mRadioListener: RadioListener)

    fun unregisterListener(mRadioListener: RadioListener)

    fun setLogging(logging: Boolean)

    fun connect()

    fun disconnect()

    fun updateNotification(singerName: String?, songName: String?, smallArt: Int, bigArt: Bitmap) {}

    fun updateNotification(song: String?, name: String?, default_art: Int, default_art1: Int) {}

}

package com.yopachara.catradiod.library.radio


import android.graphics.Bitmap

/**
 * Created by mertsimsek on 03/07/15.
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

    fun updateNotification(singerName: String, songName: String, smallArt: Int, bigArt: Int)

    fun updateNotification(singerName: String, songName: String, smallArt: Int, bigArt: Bitmap)

}

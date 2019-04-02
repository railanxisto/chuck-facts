package br.com.railanxisto.chuckfacts.presentation.utils

import android.content.Context
import android.net.ConnectivityManager
import androidx.core.content.getSystemService

fun Context.isConnected() =
    getSystemService<ConnectivityManager>()?.activeNetworkInfo?.isConnected ?: false
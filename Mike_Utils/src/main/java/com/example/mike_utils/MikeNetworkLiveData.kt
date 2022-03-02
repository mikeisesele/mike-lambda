package com.example.mike_utils

import android.annotation.SuppressLint
import android.app.Application
import android.net.ConnectivityManager
import android.content.Context
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import androidx.lifecycle.LiveData

/**
 *Observe  network state with network before network requests.
 * Initialize the live data in your application context. run MikeNetworkLiveData.init(this)
 * After which all methods can be accessed within any part of your app.
 * Observe directly on the MikeNetworkLiveData or use the isNetworkAvailable method to check for network.
 */
object MikeNetworkLiveData : LiveData<Boolean>() {

    override fun onActive() {
        super.onActive()
        getDetails()
    }

    lateinit var application: Application
    private lateinit var networkRequest: NetworkRequest

    fun init(application: Application) {
        this.application = application
        networkRequest = NetworkRequest.Builder()
            .addTransportType(NetworkCapabilities.TRANSPORT_CELLULAR)
            .addTransportType(NetworkCapabilities.TRANSPORT_WIFI)
            .build()
    }

    @SuppressLint("MissingPermission")
    private fun getDetails() {
        val cm = application.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        cm.registerNetworkCallback(networkRequest, object : ConnectivityManager.NetworkCallback() {

            override fun onAvailable(network: Network) {
                super.onAvailable(network)
                postValue(true)
            }

            override fun onUnavailable() {
                super.onUnavailable()
                postValue(false)
            }

            override fun onLost(network: Network) {
                super.onLost(network)
                postValue(false)
            }
        })
    }

    @SuppressLint("MissingPermission")
    fun isNetworkAvailable(): Boolean {
        val cm = application.getSystemService(
            Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork = cm.activeNetworkInfo
        return activeNetwork != null && activeNetwork.isConnected
    }

}
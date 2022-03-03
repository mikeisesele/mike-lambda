package com.example.mike_utils

import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.annotation.RequiresApi
import com.karumi.dexter.Dexter
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionDeniedResponse
import com.karumi.dexter.listener.PermissionGrantedResponse
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.single.DialogOnDeniedPermissionListener
import com.karumi.dexter.listener.single.PermissionListener

/**
 * Have access to log, toast, hide and show view, navigateToActivity, askForPermission.
 * ask for permission takes in the permission from the manifest, context and message if the permission was denied.
 */
object MikeUtils {

    fun <T> log(value : T){
        Log.i("INFORMATION", "log: $value")
    }

    fun toast(context: Context, message : String){
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

    fun View.hideView(){
        visibility = View.GONE
    }

    fun View.showView(){
        visibility = View.VISIBLE
    }

    inline fun <reified T> navigateToActivity(context: Context, destination: T, data: Bundle?){
        destination?.let {
            val intent = Intent(context, it::class.java)
            data?.let {
                intent.putExtra("data", it)
            }
            context.startActivity(intent)
        } ?: toast( context, "Choose an activity as your destination to navigate to")
    }

    fun askPermission(permission : String, context: Context, message : String) {

        Dexter.withContext(context)
            .withPermission(permission)
            .withListener(object : PermissionListener {
                @RequiresApi(Build.VERSION_CODES.R)
                override fun onPermissionGranted(response: PermissionGrantedResponse) { /* ... */

                }

                override fun onPermissionDenied(response: PermissionDeniedResponse) { /* ... */
                    DialogOnDeniedPermissionListener.Builder
                        .withContext(context)
                        .withTitle("Permission")
                        .withMessage(message)
                        .withButtonText(android.R.string.ok)
                        .build()

                    toast(context, "Permission is required to use this feature")
                }

                override fun onPermissionRationaleShouldBeShown(
                    permission: PermissionRequest,
                    token: PermissionToken
                ) { /* ... */
                    token.continuePermissionRequest()
                }
            }).check()

    }
}
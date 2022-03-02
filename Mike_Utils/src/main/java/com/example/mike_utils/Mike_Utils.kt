package com.example.mike_utils

import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.annotation.RequiresApi
import com.bumptech.glide.Glide
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.karumi.dexter.Dexter
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionDeniedResponse
import com.karumi.dexter.listener.PermissionGrantedResponse
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.single.DialogOnDeniedPermissionListener
import com.karumi.dexter.listener.single.PermissionListener


object MikeUtils {

    /**
     * Log any value of type T.
     */
    fun <T> log(value : T){
        Log.i("INFORMATION", "log: ${value.toString()}")
    }

    /**
     * Toast any value of type T
     */
    fun toast(context: Context, message : String){
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }
     /**
     * Navigate to any activity
     */
    inline fun <reified T> navigateToActivity(context: Context, destination: T){
        val intent = Intent(context, T::class.java)
        context.startActivity(intent)
    }

    /**
     * Asi for permission. Provide the permission name, context and message if permission was denied
     */
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

    /**
     * Duild a dialogue
     */
    fun showDialogue(
        context: Context,
        dialogueTitle: String?,
        dialogueMessage: String?,
        positiveButton: String?,
        negativeButton: String?,
        onPositiveClick: () -> Unit,
        onNegativeClick: () -> Unit
    ) {
        val dialog = MaterialAlertDialogBuilder(context)
            .setTitle(dialogueTitle)
            .setMessage(dialogueMessage)
            .setPositiveButton(positiveButton) { _, _ ->  onPositiveClick.invoke() }
            .setNegativeButton(negativeButton) { dialogInterface, _ ->
                dialogInterface.cancel()
                onNegativeClick.invoke()
            }
            .create()
        dialog.show()
    }

    /**
     * use glide library to set up images. simply provide image view and string
     */
    fun askPermission(permission : String, context: Context, permissionDeniedMessage : String, action: () -> Unit) {

        Dexter.withContext(context)
            .withPermission(permission)
            .withListener(object : PermissionListener {
                @RequiresApi(Build.VERSION_CODES.R)
                override fun onPermissionGranted(response: PermissionGrantedResponse) { /* ... */
                    action.invoke()
                }

                override fun onPermissionDenied(response: PermissionDeniedResponse) { /* ... */
                    DialogOnDeniedPermissionListener.Builder
                        .withContext(context)
                        .withTitle("Permission")
                        .withMessage(permissionDeniedMessage)
                        .withButtonText(android.R.string.ok)
                        .build()

                }

                override fun onPermissionRationaleShouldBeShown(
                    permission: PermissionRequest,
                    token: PermissionToken
                ) { /* ... */
                    token.continuePermissionRequest()
                }
            }).check()

    }

    /**
     * use glide library to set up images. simply provide image view and string
     */
    fun glideImage(view: ImageView, imageUrl: String?) {
        if (!imageUrl.isNullOrEmpty()) {
            Glide.with(view.context)
                .load(imageUrl)
                .into(view)
        }
    }

    /**
     * Build a sty;ed dialogue
     */
    fun showDialogueWithStyle(
        context: Context,
        style: Int,
        title: String?,
        message: String?,
        icon: Int,
        positiveButtonText: String?,
        negativeButtonText: String?,
        onPositiveClick: () -> Unit,
        onNegativeClick: () -> Unit
    ) {
        val dialog = MaterialAlertDialogBuilder(context, style)
            .setTitle(title)
            .setMessage(message)
            .setIcon(icon)
            .setPositiveButton(positiveButtonText) { _, _ ->
                onPositiveClick.invoke()
            }
            .setNegativeButton(negativeButtonText) { dialogInterface, _ ->
                dialogInterface.cancel()
                onNegativeClick.invoke()
            }
            .create()
        dialog.show()
    }


}
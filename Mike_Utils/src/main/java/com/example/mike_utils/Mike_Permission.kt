package com.example.mike_utils

import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import com.karumi.dexter.Dexter
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionDeniedResponse
import com.karumi.dexter.listener.PermissionGrantedResponse
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.single.DialogOnDeniedPermissionListener
import com.karumi.dexter.listener.single.PermissionListener

object MikePermission {

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

}
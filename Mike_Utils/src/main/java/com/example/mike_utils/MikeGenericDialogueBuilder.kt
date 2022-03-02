package com.example.mike_utils

import android.content.Context
import com.google.android.material.dialog.MaterialAlertDialogBuilder



object MikeGenericDialogueBuilder {

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

}
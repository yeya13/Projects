package com.example.reddit.dialogues

import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import com.example.reddit.R

class ErrorDialog(): DialogFragment(){
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            // Use the Builder class for convenient dialog construction
            val builder = AlertDialog.Builder(it)
            val message = getString(R.string.messageError)
            builder.setMessage(message)
                .setPositiveButton(
                    R.string.ok,
                    DialogInterface.OnClickListener { dialog, id ->
                        isCancelable = false
                    })
            // Create the AlertDialog object and return it
            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }
}
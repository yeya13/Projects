package com.example.manifesto.dialogues

import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import com.example.manifesto.R
import com.example.manifesto.database.models.FormEntity

class DeleteDialog(var name: String, var deleteListener: () -> Unit) : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            // Use the Builder class for convenient dialog construction
            val builder = AlertDialog.Builder(it)
            val message = getString(R.string.delete_person, name)
            builder.setMessage(message)
                .setPositiveButton(R.string.allow,
                    DialogInterface.OnClickListener { dialog, id ->
                        deleteListener.invoke()
                    })
                .setNegativeButton(R.string.deny,
                    DialogInterface.OnClickListener { dialog, id ->
                        dialog.cancel()
                    })
            // Create the AlertDialog object and return it
            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }
}
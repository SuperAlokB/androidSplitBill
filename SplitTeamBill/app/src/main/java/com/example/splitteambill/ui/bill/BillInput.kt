package com.example.splitteambill.ui.bill

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentActivity

class BillInput:DialogFragment() {


    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            // Use the Builder class for convenient dialog construction.
            val builder = AlertDialog.Builder(it)
            val inputEditText = EditText(requireContext())
            builder.setMessage("Enter Bill Item ")
                .setView(inputEditText)
                .setPositiveButton("Save") { dialog, id ->
                    // START THE GAME!
                }
                .setNegativeButton("Cancel") { dialog, id ->
                    // User cancelled the dialog.
                    val editTextValue = inputEditText.text.toString()
                    Toast.makeText(requireContext(), "$editTextValue Bill infor ", Toast.LENGTH_LONG).show()
                }

            // Create the AlertDialog object and return it.
            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }
}
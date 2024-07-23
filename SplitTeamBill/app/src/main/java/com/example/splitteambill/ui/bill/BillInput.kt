package com.example.splitteambill.ui.bill

import android.R
import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface.OnMultiChoiceClickListener
import android.os.Bundle
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.viewbinding.ViewBindings
import com.example.splitteambill.data.DBHelper


class BillInput:DialogFragment() {



    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {


            val foodNameEditText = EditText(requireContext())
            val foodQualityEditText = EditText(requireContext())
            val foodPriceEditText = EditText(requireContext())

            val layout = LinearLayout(context)
            layout.setOrientation(1)

            foodNameEditText.hint = "Food Item Name"
            foodQualityEditText.hint = "Quantity"
            foodPriceEditText.hint = "price"
            layout.addView(foodNameEditText)
            layout.addView(foodQualityEditText)
            layout.addView(foodPriceEditText)
            // Use the Builder class for convenient dialog construction.


            // initialise the list items for the alert dialog
            val db = DBHelper(requireContext(), null)

            val listItems = db.getMemebersList().toTypedArray()
            //val listItems = arrayOf("C", "C++", "JAVA", "PYTHON")
            val checkedItems = BooleanArray(listItems.size)

            // copy the items from the main list to the selected item list for the preview
            // if the item is checked then only the item should be displayed for the user
            val selectedItems = mutableListOf(*listItems)
            // initialise the alert dialog builder
            val builder = AlertDialog.Builder(requireContext())

            // set the title for the alert dialog
            builder.setTitle("Enter Bill Information ")

            // set the icon for the alert dialog
            //builder.setIcon(R.drawable.image_logo)

            // now this is the function which sets the alert dialog for multiple item selection ready
            builder.setMultiChoiceItems(listItems, checkedItems) { dialog, which, isChecked ->
                checkedItems[which] = isChecked
                val currentItem = selectedItems[which]
            }

            // alert dialog shouldn't be cancellable
            builder.setCancelable(false)

            // handle the positive button of the dialog
            builder.setPositiveButton("Save") { dialog, which ->
               // val editTextValue = foodNameEditText.text.toString()
                for (i in checkedItems.indices) {
                    if (checkedItems[i]) {
                        //tvSelectedItemsPreview.text = String.format("%s%s, ", tvSelectedItemsPreview.text, selectedItems[i])
                        //Toast.makeText(requireContext(), selectedItems[i] + "Item Info :" + editTextValue, Toast.LENGTH_LONG).show()
                        Toast.makeText(requireContext(), "$foodNameEditText Bill info ", Toast.LENGTH_LONG).show()
                    }
                }
            }

            // handle the negative button of the alert dialog
            builder.setNegativeButton("CANCEL") { dialog, which -> }

            // handle the neutral button of the dialog to clear the selected items boolean checkedItem
           // builder.setNeutralButton("CLEAR ALL") { dialog: DialogInterface?, which: Int ->
           //     Arrays.fill(checkedItems, false)
           // }
            builder.setView(layout)

            // Create the AlertDialog object and return it.
            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }
}
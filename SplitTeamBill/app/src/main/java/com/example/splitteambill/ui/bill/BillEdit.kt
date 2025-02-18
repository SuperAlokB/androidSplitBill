package com.example.splitteambill.ui.bill

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import com.example.splitteambill.data.DBHelper


class BillEdit(selectedItem: String?,selectedItemQty: String?,selectedItemPrice: String?) :DialogFragment() {

    var selectedItemText = selectedItem
    var selectedItemQtyText = selectedItemQty
    var selectedItemPriceText = selectedItemPrice


    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {




            // initialise the list items for the alert dialog
            val db = DBHelper(requireContext(), null)



            val editTextName = EditText(requireContext())
            val editTextQty = EditText(requireContext())
            val editTextPrice = EditText(requireContext())




            val layout = LinearLayout(context)
            layout.setOrientation(1)

            editTextName.hint = selectedItemText
            editTextQty.hint = selectedItemQtyText
            editTextPrice.hint = selectedItemPriceText

            layout.addView(editTextName)
            layout.addView(editTextQty)
            layout.addView(editTextPrice)
                 // initialise the list items for the alert dialog


            val builder = AlertDialog.Builder(requireContext())

            val listItems = db.getMembersList().toTypedArray()
            val checkedItems = BooleanArray(listItems.size)

            var teamMembersForFood = db.getTeamMembersFromBillForFood(selectedItemText.toString())
            val selectedMembers: List<String> = teamMembersForFood.split(",")
            if(selectedMembers.isNotEmpty()) {
                //Toast.makeText(requireContext(), "b4 Previous :$selectedMembers", Toast.LENGTH_LONG).show()
                for (teamMemberId in selectedMembers) {
                    if (teamMemberId.toString() != "null" ) {
                        checkedItems[teamMemberId.toInt() - 1] = true
                        //Toast.makeText(requireContext(), "1 Previous :" + (teamMemberId.toString()) , Toast.LENGTH_LONG).show()
                    }
                }
            }else{
                checkedItems[teamMembersForFood.toInt() - 1] = true
                //Toast.makeText(requireContext(), "2 Previous :" + (selectedMembers[0].toString()) , Toast.LENGTH_LONG).show()
            }



            // copy the items from the main list to the selected item list for the preview
            // if the item is checked then only the item should be displayed for the user
            val selectedItems = mutableListOf(*listItems)
            // now this is the function which sets the alert dialog for multiple item selection ready
            builder.setMultiChoiceItems(listItems, checkedItems) { dialog, which, isChecked ->
                checkedItems[which] = isChecked
                val currentItem = selectedItems[which]
            }
            // copy the items from the main list to the selected item list for the preview
            // if the item is checked then only the item should be displayed for the user
           // val selectedItems = mutableListOf(*listItems)
            // initialise the alert dialog builder


            // set the title for the alert dialog
            builder.setTitle("Modify Bill Information ")

            builder.setCancelable(true)

            // handle the positive button of the dialog
            builder.setPositiveButton("Save") { dialog, which ->

                var newValueName = editTextName.text.toString()
                if(newValueName.isEmpty()){
                    newValueName = selectedItemText.toString()
                }
                var newValueQty = editTextQty.text.toString()
                if(newValueQty.isEmpty()){
                    newValueQty = selectedItemQtyText.toString()
                }
                var newValePrice = editTextPrice.text.toString()
                if(newValePrice.isEmpty()){
                    newValePrice = selectedItemPriceText.toString()
                }
                //Update Team_Members Table

                var totalSelectedTeamMembers = 0
                for (i in checkedItems.indices) {
                    if (checkedItems[i]) {
                        totalSelectedTeamMembers ++
                    }
                }

               // var teamMemebrsForFood = db.getTeamMembersFromBillForFood(selectedItemText.toString())

                //var contriForEachMember = finalTotal / totalSelectedTeamMembers.toDouble()
                //Toast.makeText(requireContext(), contriForEachMember.toString(), Toast.LENGTH_LONG).show()
                for (i in checkedItems.indices) {
                    if (checkedItems[i]) {

                        //Toast.makeText(requireContext(), selectedItems[i] + "Item Info :" + editTextValue, Toast.LENGTH_LONG).show()

                     // Find if it is same members or new member added

                    }
                }

                //Get Team Members List for selected food





                //Update Team Member Table -

                //- Remove previous values
                //Update Bill Table
                db.updateBillInformation(selectedItemText.toString(),newValueName,newValueQty,newValePrice)
                //Update Team table with new values

            }

            // handle the negative button of the alert dialog
            builder.setNegativeButton("Delete") { dialog, which ->

               db.deleteBillRecord(selectedItemText.toString())
                //Delete Code
            }



            builder.setView(layout)

            // Create the AlertDialog object and return it.
            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }
}

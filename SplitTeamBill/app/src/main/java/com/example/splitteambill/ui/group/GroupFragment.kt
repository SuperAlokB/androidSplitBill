package com.example.splitteambill.ui.group

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.splitteambill.databinding.FragmentGroupBinding
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ListView
import android.widget.Toast
import com.example.splitteambill.data.DBHelper




class GroupFragment : Fragment() {

    // on below line we are creating a variable.
    lateinit var teamListView: ListView
    lateinit var addBtn: Button
    lateinit var delBtn: Button
    lateinit var teamMateName: EditText
    lateinit var membersList: ArrayList<String>


    private var _binding: FragmentGroupBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val groupViewModel =
            ViewModelProvider(this).get(GroupViewModel::class.java)

        _binding = FragmentGroupBinding.inflate(inflater, container, false)
        val root: View = binding.root

        // on below line we are initializing our variables.
        teamListView = binding.idListTeamMembers
        addBtn = binding.idBtnAdd
        delBtn = binding.idBtnRemove
        teamMateName = binding.idEdtMemberName

        membersList = ArrayList<String>()
        val db = DBHelper(requireContext(), null)
        membersList = db.getMembersList()

//
//        // on below line we are adding items to our list
//        //membersList.add("Alok")
//        val cursor = db.getName()
//        if(cursor !=null && cursor.count > 0) {
//            // moving the cursor to first position and
//            // appending value in the text view
//            cursor!!.moveToFirst()
//
//            membersList.add(cursor.getString(cursor.getColumnIndex(DBHelper.NAME_COl)))
//
//            while (cursor.moveToNext()) {
//                membersList.add(cursor.getString(cursor.getColumnIndex(DBHelper.NAME_COl)))
//            }
//        }


        val editCGSTTax: TextView = binding.idEditCGST
        val editSGSTTax: TextView = binding.idEditSGST
        val editVat: TextView = binding.idEditVAT

        groupViewModel.cgst.observe(viewLifecycleOwner) {
            editCGSTTax.text = it
        }
        groupViewModel.sggt.observe(viewLifecycleOwner) {
            editSGSTTax.text = it
        }
        groupViewModel.vatTax.observe(viewLifecycleOwner) {
            editVat.text = it
        }

        // on below line we are initializing adapter for our list view.
        val adapter: ArrayAdapter<String?> = ArrayAdapter<String?>(
            activity as Context,
            android.R.layout.simple_list_item_1,

            membersList as List<String?>
        )

        // on below line we are setting adapter for our list view.
        //languageLV.adapter = adapter
        teamListView.adapter = adapter


        //  languageLV.adapter = adapter
        addBtn.setOnClickListener {

            membersList.clear()
            // on below line we are getting text from edit text
            val memberName = teamMateName.text.toString()
            // on below line we are checking if item is not empty
            if (memberName.isNotEmpty()) {

               // membersList.clear()
                val age = 16
                // name to our database
                db.addName(memberName)
                 // Toast to message on the screen
                Toast.makeText(requireContext(), memberName + " added to database", Toast.LENGTH_LONG).show()

                val cursor = db.getName()
                // moving the cursor to first position and
                // appending value in the text view
                cursor!!.moveToFirst()

                membersList.add(cursor.getString(cursor.getColumnIndex(DBHelper.NAME_COl)))

                while(cursor.moveToNext()){
                    membersList.add(cursor.getString(cursor.getColumnIndex(DBHelper.NAME_COl)))
                }
             //  membersList = db.getMemebersList()
                // on below line we are notifying adapter
                // that data in list is updated to update our list view.
                adapter.notifyDataSetChanged()
            }
            teamMateName.text.clear()
        }
        delBtn.setOnClickListener {
            // on below line we are getting text from edit text
            val memberName = teamMateName.text.toString()
            // on below line we are checking if item is not empty
            if (memberName.isNotEmpty()) {
                membersList.clear()
                // on below line we are adding item to our list.
                //membersList.remove(memberName)
                db.delName(memberName)

                val cursor = db.getName()
                // moving the cursor to first position and
                // appending value in the text view
                cursor!!.moveToFirst()

                membersList.add(cursor.getString(cursor.getColumnIndex(DBHelper.NAME_COl)))

                while(cursor.moveToNext()){
                    membersList.add(cursor.getString(cursor.getColumnIndex(DBHelper.NAME_COl)))
                }

                // on below line we are notifying adapter
                // that data in list is updated to update our list view.
                adapter.notifyDataSetChanged()
            }
            teamMateName.text.clear()

        }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private suspend fun getData(){


    }

}






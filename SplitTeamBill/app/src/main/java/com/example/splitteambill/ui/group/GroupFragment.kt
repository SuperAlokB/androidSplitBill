package com.example.splitteambill.ui.group

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
import androidx.appcompat.app.AppCompatActivity

class GroupFragment : Fragment() {

    // on below line we are creating a variable.
    lateinit var languageLV: ListView
    lateinit var addBtn: Button
    lateinit var delBtn: Button
    lateinit var itemEdt: EditText
    lateinit var lngList: ArrayList<String>

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
        languageLV = binding.idLVLanguages
        addBtn = binding.idBtnAdd
        delBtn = binding.idBtnRemove
        itemEdt = binding.idEdtItemName
        lngList = ArrayList()

        // on below line we are adding items to our list
        lngList.add("Alok")


//        val textView: TextView = binding.textGroup
//        groupViewModel.text.observe(viewLifecycleOwner) {
//            textView.text = it
//        }

        // on below line we are initializing adapter for our list view.
        val adapter: ArrayAdapter<String?> = ArrayAdapter<String?>(
            activity as Context,
            android.R.layout.simple_list_item_1,
            lngList as List<String?>
        )

        // on below line we are setting adapter for our list view.
        languageLV.adapter = adapter
        addBtn.setOnClickListener {
            // on below line we are getting text from edit text
            val item = itemEdt.text.toString()

            // on below line we are checking if item is not empty
            if (item.isNotEmpty()) {
                // on below line we are adding item to our list.
                lngList.add(item)

                // on below line we are notifying adapter
                // that data in list is updated to update our list view.
                adapter.notifyDataSetChanged()
            }
        }
            delBtn.setOnClickListener {
                // on below line we are getting text from edit text
                val item = itemEdt.text.toString()

                // on below line we are checking if item is not empty
                if (item.isNotEmpty()) {
                    // on below line we are adding item to our list.
                    lngList.remove(item)

                    // on below line we are notifying adapter
                    // that data in list is updated to update our list view.
                    adapter.notifyDataSetChanged()
                }


            }



        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
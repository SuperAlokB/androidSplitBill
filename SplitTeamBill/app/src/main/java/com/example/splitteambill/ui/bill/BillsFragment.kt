package com.example.splitteambill.ui.bill

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.GridView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.splitteambill.data.DBHelper
import com.example.splitteambill.databinding.FragmentBillsBinding


private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class BillsFragment : Fragment() {

    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private var _binding: FragmentBillsBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    lateinit var addBtn: Button
    lateinit var itemGrid: List<String>
    lateinit var grandTotal: TextView
    lateinit var foodBill: TextView
    lateinit var liquorBill: TextView
    lateinit var resetBtn : Button

    // initialise the list items for the alert dialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val billsViewModel =
            ViewModelProvider(this).get(BillsViewModel::class.java)
        itemGrid = getData()
        _binding = FragmentBillsBinding.inflate(inflater, container, false)
        val root: View = binding.root
        val gridView = binding.gridView
        val adapter =
            ArrayAdapter<String>(requireContext(), android.R.layout.simple_list_item_1, itemGrid)
        gridView.numColumns = 4

        gridView.adapter = adapter
        gridView.onItemClickListener = AdapterView.OnItemClickListener { _, _, position, _ ->
            // Handle item click here
            val selectedItem = adapter.getItem(position)
            Toast.makeText(requireContext(), " edit " + selectedItem.toString(), Toast.LENGTH_LONG).show()
        }
        addBtn = binding.idBillBtnAdd
        grandTotal = binding.idTxTotalBill
        foodBill = binding.idTxtFoodBill
        liquorBill = binding.idTxLiquorBill
        grandTotal.text =  getTotalBill().toString()
        foodBill.text = getTotalFoodBill().toString()
        liquorBill.text = getTotalLiquorBill().toString()
        resetBtn = binding.idBillBtnRemove

        addBtn.setOnClickListener {

            BillInput().show((activity as AppCompatActivity).supportFragmentManager, "Bill Input")
            Toast.makeText(requireContext(), " Add bill information ", Toast.LENGTH_LONG).show()
            grandTotal.text =  getTotalBill().toString()
            foodBill.text = getTotalFoodBill().toString()
            liquorBill.text = getTotalLiquorBill().toString()

        }

        resetBtn.setOnClickListener {

            resetData()
            Toast.makeText(requireContext(), " Cleared All Bill Information", Toast.LENGTH_LONG).show()
        }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun resetData(){

        val db = DBHelper(requireContext(), null)
       db.resetData()

    }

    // Replace this with your data source
    private fun getData(): List<String> {
        val db = DBHelper(requireContext(), null)

        val membersList = ArrayList<String>()
        membersList as List<String?>
        val cursor = db.getFoodNames()

        if (cursor != null && cursor.count > 0) {
            // moving the cursor to first position and
            // appending value in the text view
            cursor!!.moveToFirst()
            membersList.add("Item")
            membersList.add("Qty")
            membersList.add("Price")
            membersList.add("Total")

                if(cursor.getString(cursor.getColumnIndex(DBHelper.LIQUOR_NAME)) != "") {
                    membersList.add(cursor.getString(cursor.getColumnIndex(DBHelper.LIQUOR_NAME)))
                }else{
                    membersList.add(cursor.getString(cursor.getColumnIndex(DBHelper.FOOD_NAME)))
                }
            membersList.add(cursor.getString(cursor.getColumnIndex(DBHelper.FOOD_QTY)))
            membersList.add(cursor.getString(cursor.getColumnIndex(DBHelper.FOOD_PRICE)))
            membersList.add(cursor.getString(cursor.getColumnIndex(DBHelper.Total_Price)))

            while (cursor.moveToNext()) {
                if(cursor.getString(cursor.getColumnIndex(DBHelper.LIQUOR_NAME)) != "") {
                    membersList.add(cursor.getString(cursor.getColumnIndex(DBHelper.LIQUOR_NAME)))
                }else{
                    membersList.add(cursor.getString(cursor.getColumnIndex(DBHelper.FOOD_NAME)))
                }

                membersList.add(cursor.getString(cursor.getColumnIndex(DBHelper.FOOD_QTY)))
                membersList.add(cursor.getString(cursor.getColumnIndex(DBHelper.FOOD_PRICE)))
                membersList.add(cursor.getString(cursor.getColumnIndex(DBHelper.Total_Price)))


            }
        }
        // membersList.add("Biryani")
        //  membersList.add("Biryani2")
        //  membersList.add("Biryani3")

        return membersList


        // return listOf("Item 1", "Item 2", "Item 3", "Item 4", "Item 5")
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment fragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            BillsFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    private fun getTotalBill(): Double {

        val db = DBHelper(requireContext(), null)
        var totalBill : Double  = 0.0


        val cursor = db.getFoodNames()

        if (cursor != null && cursor.count > 0) {
            cursor!!.moveToFirst()
            totalBill =  cursor.getString(cursor.getColumnIndex(DBHelper.Total_Price)).toDouble()
            while (cursor.moveToNext()) {

                totalBill += cursor.getString(cursor.getColumnIndex(DBHelper.Total_Price)).toDouble()


            }
        }
        return totalBill
    }
    private fun getTotalFoodBill(): Double {

        val db = DBHelper(requireContext(), null)
        var totalBill : Double  = 0.0


        val cursor = db.getFoodNames()

        if (cursor != null && cursor.count > 0) {
            cursor!!.moveToFirst()

            if(cursor.getString(cursor.getColumnIndex(DBHelper.FOOD_NAME)) != "") {
                totalBill =  cursor.getString(cursor.getColumnIndex(DBHelper.Total_Price)).toDouble()
            }

            while (cursor.moveToNext()) {

                if(cursor.getString(cursor.getColumnIndex(DBHelper.FOOD_NAME)) != "") {
                    totalBill +=  cursor.getString(cursor.getColumnIndex(DBHelper.Total_Price)).toDouble()
                }



            }
        }
        return totalBill
    }
    private fun getTotalLiquorBill(): Double {

        val db = DBHelper(requireContext(), null)
        var totalBill: Double = 0.0


        val cursor = db.getFoodNames()
        if (cursor != null && cursor.count > 0) {
            cursor!!.moveToFirst()


            if (cursor.getString(cursor.getColumnIndex(DBHelper.LIQUOR_NAME)) != "") {
                totalBill = cursor.getString(cursor.getColumnIndex(DBHelper.Total_Price)).toDouble()
            }

            while (cursor.moveToNext()) {

                if (cursor.getString(cursor.getColumnIndex(DBHelper.LIQUOR_NAME)) != "") {
                    totalBill +=
                        cursor.getString(cursor.getColumnIndex(DBHelper.Total_Price)).toDouble()
                }


            }
        }
        return totalBill
    }
}
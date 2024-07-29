package com.example.splitteambill.ui.dashboard


import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.TableLayout
import android.widget.TableRow
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.splitteambill.R
import com.example.splitteambill.data.DBHelper
import com.example.splitteambill.data.User
import com.example.splitteambill.databinding.FragmentDashboardBinding


class DashboardFragment : Fragment() {

    private var _binding: FragmentDashboardBinding? = null
    private var mTableLayout: TableLayout? = null
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private lateinit var tableRecyclerView : RecyclerView
    private lateinit var itemGrid: List<String>


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val dashboardViewModel =
            ViewModelProvider(this).get(DashboardViewModel::class.java)
        itemGrid = getData()
        _binding = FragmentDashboardBinding.inflate(inflater, container, false)
        val gridView = binding.TeamGridView
        val root: View = binding.root
        val adapter =
            ArrayAdapter<String>(requireContext(), android.R.layout.simple_list_item_1, itemGrid)
        gridView.numColumns = 4

        gridView.adapter = adapter
        gridView.onItemClickListener = AdapterView.OnItemClickListener { _, _, position, _ ->
            // Handle item click here
            val selectedItem = adapter.getItem(position)
            // Perform actions based on the selected item
        }


//        val textView: TextView = binding.textDashboard
//        dashboardViewModel.text.observe(viewLifecycleOwner) {
//            textView.text = it
//        }
        return root
    }

    override fun onResume() {
        //Log.e("DEBUG", "onResume of LoginFragment");
        itemGrid = getData()
        val gridView = binding.TeamGridView
        val adapter =
            ArrayAdapter<String>(requireContext(), android.R.layout.simple_list_item_1, itemGrid)
        gridView.numColumns = 4

        gridView.adapter = adapter
        adapter.notifyDataSetChanged()
        super.onResume()

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    // Replace this with your data source
    private fun getData(): List<String> {
        val db = DBHelper(requireContext(), null)

        val membersList = ArrayList<String>()
        membersList as List<String?>
        val cursor = db.getName()

        if (cursor != null && cursor.count >= 0) {
            // moving the cursor to first position and
            // appending value in the text view
            cursor!!.moveToFirst()
            membersList.add("Name")
            membersList.add("Food Bill")
            membersList.add("Drinks Bill")
            membersList.add("Total")


            membersList.add(cursor.getString(cursor.getColumnIndex(DBHelper.NAME_COl)))

            membersList.add(cursor.getString(cursor.getColumnIndex(DBHelper.FOOD_Bill)))
            membersList.add(cursor.getString(cursor.getColumnIndex(DBHelper.DRINKS_Bill)))
            membersList.add(cursor.getString(cursor.getColumnIndex(DBHelper.TotalBill)))

            while (cursor.moveToNext()) {
                membersList.add(cursor.getString(cursor.getColumnIndex(DBHelper.NAME_COl)))

                membersList.add(cursor.getString(cursor.getColumnIndex(DBHelper.FOOD_Bill)))
                membersList.add(cursor.getString(cursor.getColumnIndex(DBHelper.DRINKS_Bill)))
                membersList.add(cursor.getString(cursor.getColumnIndex(DBHelper.TotalBill)))



            }
        }
        // membersList.add("Biryani")
        //  membersList.add("Biryani2")
        //  membersList.add("Biryani3")

        return membersList


        // return listOf("Item 1", "Item 2", "Item 3", "Item 4", "Item 5")
    }
}
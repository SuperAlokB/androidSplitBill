package com.example.splitteambill.ui.dashboard


import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TableLayout
import android.widget.TableRow
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.splitteambill.R
import com.example.splitteambill.data.User
import com.example.splitteambill.databinding.FragmentDashboardBinding


class DashboardFragment : Fragment() {

    private var _binding: FragmentDashboardBinding? = null
    private var mTableLayout: TableLayout? = null
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private lateinit var tableRecyclerView : RecyclerView
    private var userList = ArrayList<User>()
   // private lateinit var tableRowAdapter: TableRowAdapter
    private lateinit var user : User


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val dashboardViewModel =
            ViewModelProvider(this).get(DashboardViewModel::class.java)

        _binding = FragmentDashboardBinding.inflate(inflater, container, false)
        val root: View = binding.root


    val ll = binding.table as TableLayout
    val tr = TableRow(activity)
    val tv1 = TextView(activity)

    tv1.setText("TEST NUMBER");
    tv1.setTextColor(Color.BLACK);
    //tv1.setTextSize(20.00);
    tv1.setPadding(5, 5, 5, 5);
    tr.addView(tv1);

        val tv11 = TextView(activity)
        tv11.setText("TEST NUMBER 1");
        tv11.setTextColor(Color.BLACK);
        tr.addView(tv11);
        val tv2 = TextView(activity)
        val tr1 = TableRow(activity)
        tv2.setText("TEST NUMBER 2")
        tv2.setTextColor(Color.BLACK)
        //tv1.setTextSize(20.00);
        tv2.setPadding(5, 5, 5, 5);
        tr1.addView(tv2)
        ll.addView(tr)
        ll.addView(tr1)


//        val textView: TextView = binding.textDashboard
//        dashboardViewModel.text.observe(viewLifecycleOwner) {
//            textView.text = it
//        }
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
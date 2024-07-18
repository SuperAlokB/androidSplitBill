package com.example.splitteambill.ui.bill

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.splitteambill.databinding.FragmentBillsBinding

class BillsFragment : Fragment() {

    private var _binding: FragmentBillsBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    lateinit var addBtn: Button

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val billsViewModel =
            ViewModelProvider(this).get(BillsViewModel::class.java)

        _binding = FragmentBillsBinding.inflate(inflater, container, false)
        val root: View = binding.root
        addBtn = binding.idBtnAdd
        addBtn.setOnClickListener {

            BillInput().show((activity as AppCompatActivity).supportFragmentManager , "Bill Input")
            Toast.makeText(requireContext(),  " Add bill information ", Toast.LENGTH_LONG).show()
        }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
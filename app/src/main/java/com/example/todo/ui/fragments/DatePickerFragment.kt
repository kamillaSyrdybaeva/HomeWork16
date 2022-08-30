package com.example.todo.ui.fragments

import android.app.DatePickerDialog
import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.DatePicker
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.setFragmentResult
import com.example.todo.databinding.FragmentDatePickerBinding
import java.text.SimpleDateFormat
import java.util.*

class DatePickerFragment : DialogFragment(){
    private lateinit var binding: FragmentDatePickerBinding

    override fun onCreateView(

        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentDatePickerBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupCalendar()
    }

    private fun setupCalendar() {
        binding.calendarViewRealizarReserva.setOnDateChangeListener{view, year, month, dayOfMonth->
            val msg =  "" + dayOfMonth + "." + (month + 1) + "." + year

            val bundle = Bundle()
            bundle.putString("key", msg.toString())

            setFragmentResult("myKey", bundle)
        }
    }


}


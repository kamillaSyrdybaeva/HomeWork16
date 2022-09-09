package com.example.todo.ui.fragments

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.PorterDuff
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.TextView
import androidx.navigation.fragment.findNavController
import com.example.todo.App
import com.example.todo.R
import com.example.todo.databinding.DialogRegularBinding
import com.example.todo.databinding.FragmentCreateTaskDataBinding
import com.example.todo.ui.fragments.models.CreateDataModel
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase


class CreateTaskData : BottomSheetDialogFragment() {

    private lateinit var binding: FragmentCreateTaskDataBinding
    private var taskModel: CreateDataModel? = null
    private val fireStore = Firebase.firestore

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCreateTaskDataBinding.inflate(layoutInflater)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val bottomSheet = view.parent as View
        bottomSheet.backgroundTintMode = PorterDuff.Mode.CLEAR
        bottomSheet.backgroundTintList = ColorStateList.valueOf(Color.TRANSPARENT)
        bottomSheet.setBackgroundColor(Color.TRANSPARENT)

        initClicker()

        if (arguments != null) {
            taskModel = arguments?.get("model") as CreateDataModel
            with(binding) {
                etTask.setText(taskModel?.task)
                btnDate.text = taskModel?.date
                btnRegular.text = taskModel?.regular
            }
        }
    }

    private fun initClicker() {
        binding.btnDate.setOnClickListener {
            val datePickerFragment = DatePickerFragment()
            requireActivity().supportFragmentManager.setFragmentResultListener(
                "myKey",
                viewLifecycleOwner
            ) { resultKey, bundle ->
                if (resultKey == "myKey") {
                    val date = bundle.getString("key")
                    binding.btnDate.text = date
                }
            }
            datePickerFragment.show(requireActivity().supportFragmentManager, "TAG")
        }
        binding.btnRegular.setOnClickListener {
            showRegularDialog()
        }
        binding.btnApply.setOnClickListener {
            val model = CreateDataModel(
                task = binding.etTask.text.toString(),
                date = binding.btnDate.text.toString(),
                regular = binding.btnRegular.text.toString()
            )
            if (binding.etTask.text.isNotEmpty()) {
                if (arguments != null){
                    val upDateModel = CreateDataModel(
                        id = taskModel?.id,
                        task = binding.etTask.text.toString(),
                        date = binding.btnDate.text.toString(),
                        regular = binding.btnRegular.text.toString()
                    )
                    App.appDataBase.taskDao().updateData(upDateModel)
                }else{
                    fireStore.collection("task").add(model).addOnSuccessListener {
                        Log.e("task", "success")
                    }.addOnFailureListener{
                        Log.e("task", "error ${it.message}")
                    }
                }
                findNavController().navigate(R.id.homeFragment)
                dismiss()

            } else {
                binding.etTask.error = "Задача не может быть пустым"
            }

        }
    }

    @SuppressLint("InflateParams")
    private fun showRegularDialog() {
        val dialog = Dialog(requireContext())
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(false)
        val binding = DialogRegularBinding.inflate(layoutInflater)
        dialog.setContentView(binding.root)

        with(binding) {
            btnEveryday.setOnClickListener {
                this@CreateTaskData.binding.btnRegular.text = btnEveryday.text
                dialog.dismiss()
            }
            btnEveryWeek.setOnClickListener {
                this@CreateTaskData.binding.btnRegular.text = btnEveryWeek.text
                dialog.dismiss()
            }
            btnMonth.setOnClickListener {
                this@CreateTaskData.binding.btnRegular.text = btnMonth.text
                dialog.dismiss()
            }
            btnEveryYear.setOnClickListener {
                this@CreateTaskData.binding.btnRegular.text = btnEveryYear.text
                dialog.dismiss()
            }
            btnCancel.setOnClickListener {
                dialog.dismiss()
            }
        }

        dialog.show()
    }
}
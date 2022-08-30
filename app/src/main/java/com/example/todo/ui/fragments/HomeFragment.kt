package com.example.todo.ui.fragments

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.todo.App
import com.example.todo.R
import com.example.todo.databinding.FragmentHomeBinding
import com.example.todo.ui.adapters.TaskAdapter
import com.example.todo.ui.fragments.models.CreateDataModel
import com.example.todo.ui.inter.OnItemClickHome
import com.example.todo.ui.inter.OnItemClicker

class HomeFragment : Fragment(), OnItemClickHome {

    private lateinit var binding: FragmentHomeBinding
    var adapter = TaskAdapter(arrayListOf(),this)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initClicker()

        App.appDataBase.taskDao().getAll().observe(viewLifecycleOwner) {data->
            adapter = TaskAdapter(data, this)
            binding.recyclerTask.adapter = adapter
        }
    }

    private fun initClicker() {
        binding.btnAddTask.setOnClickListener {
            val dialog = CreateTaskData()
            dialog.show(requireActivity().supportFragmentManager, "")
        }
    }



    override fun update(taskModel: CreateDataModel) {
        val dialog = CreateTaskData()
        val bundle = Bundle()
        bundle.putSerializable("model", taskModel)
        dialog.arguments = bundle
        dialog.show(requireActivity().supportFragmentManager, "")
    }

    override fun delete(taskModel: CreateDataModel) {
        val dialog = AlertDialog.Builder(requireActivity())
        dialog.setTitle("Вы точно хотите удалить задачу?")
        dialog.setPositiveButton("Да", DialogInterface.OnClickListener{
            dialogInterface, i ->
            App.appDataBase.taskDao().deleteData(taskModel)
            dialogInterface.dismiss()
        })
        dialog.setNegativeButton("Нет", DialogInterface.OnClickListener{
            dialogInterface, i ->
            dialogInterface.dismiss()
        })
        dialog.show()
    }
}
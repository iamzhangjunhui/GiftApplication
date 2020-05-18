package com.loyo.giftapplication.ui.salary_list

import android.graphics.Canvas
import android.graphics.Rect
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.loyo.giftapplication.MainApp
import com.loyo.giftapplication.R
import com.loyo.giftapplication.room_data.DataBaseUtil
import com.loyo.giftapplication.room_data.Salary
import com.loyo.giftapplication.utils.DividerDecoration
import kotlinx.android.synthetic.main.fragment_salarys.*

class SalarysFragment : Fragment() {

    private lateinit var notificationsViewModel: SalarysViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        notificationsViewModel = ViewModelProvider(
            this,
            ViewModelProvider.AndroidViewModelFactory.getInstance(MainApp.application)
        ).get(SalarysViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_salarys, container, false)
        notificationsViewModel.getSalarys().observe(viewLifecycleOwner,
            Observer<MutableList<Salary>> {
                list_salary.adapter = ListAdapter(it)
                list_salary.layoutManager = LinearLayoutManager(requireContext())
                list_salary
                list_salary.addItemDecoration(
                    DividerDecoration(
                        requireContext().getColor(R.color.color_gray),
                        1
                    )
                )
            })
        return root
    }
}

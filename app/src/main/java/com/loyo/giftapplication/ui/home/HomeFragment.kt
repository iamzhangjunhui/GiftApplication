package com.loyo.giftapplication.ui.home

import android.graphics.Color
import android.os.Bundle
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.SavedStateViewModelFactory
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.bigkoo.pickerview.builder.TimePickerBuilder
import com.bigkoo.pickerview.view.TimePickerView
import com.loyo.giftapplication.ImageAdapter
import com.loyo.giftapplication.MainApp
import com.loyo.giftapplication.R
import com.loyo.giftapplication.databinding.FragmentHomeBinding
import com.loyo.giftapplication.room_data.DataBaseUtil
import com.loyo.giftapplication.room_data.Salary
import com.youth.banner.indicator.CircleIndicator
import kotlinx.android.synthetic.main.fragment_home.*
import java.util.*
import kotlin.concurrent.thread

class HomeFragment : Fragment() {
    lateinit var timePickerView: TimePickerView
    var year: Int = 0
    var month: Int = 0
    private lateinit var homeViewModel: HomeViewModel
    lateinit var calendarSelect: Calendar

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        homeViewModel = ViewModelProvider(
            this,
            SavedStateViewModelFactory(MainApp.application, this, null)
        ).get(HomeViewModel::class.java)
        val dataBinding = DataBindingUtil.inflate<FragmentHomeBinding>(
            inflater,
            R.layout.fragment_home,
            container,
            false
        ).also {
            it.salary = homeViewModel.getSalary()
            it.lifecycleOwner = this
        }
        return dataBinding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        //banner
        val images = listOf<Int>(
            R.drawable.a,
            R.drawable.b,
            R.drawable.c,
            R.drawable.d
        )
        banner.adapter = ImageAdapter(images)
        banner.indicator = CircleIndicator(requireContext())
        banner.setIndicatorNormalColor(Color.GRAY)
        banner.setIndicatorSelectedColor(Color.RED)

        //默认当前月份
        val calendar = Calendar.getInstance()
        year = calendar.get(Calendar.YEAR)
        month = calendar.get(Calendar.MONTH) + 1
        text_select_month.text = getString(
            R.string.salary_month_after,
            year,
            month
        )
        //选择月份
        layout_month.setOnClickListener {
            if (!this::timePickerView.isInitialized) {
                val type = booleanArrayOf(true, true, false, false, false, false)
                val startCalendar: Calendar = Calendar.getInstance()
                startCalendar.set(2020, 0, 1)
                val endCalendar: Calendar = Calendar.getInstance()
                endCalendar.set(2100, 11, 31)
                timePickerView =
                    TimePickerBuilder(requireContext()).setTitleText(getString(R.string.select_month))
                        .setRangDate(startCalendar, endCalendar)
                        .setType(type).build()
            }
            val initDate =
                if (this::calendarSelect.isInitialized) calendarSelect else Calendar.getInstance()
            timePickerView.setDate(initDate).setBtnCancelVisible(View.GONE)
                .setBtnClearVisible(View.GONE)
                .setOnTimeSelectListener { date, _ ->
                    calendarSelect = Calendar.getInstance()
                    calendarSelect.time = date
                    year = calendarSelect.get(Calendar.YEAR)
                    month = calendarSelect.get(Calendar.MONTH) + 1
                    text_select_month.text = getString(
                        R.string.salary_month_after,
                        year,
                        month
                    )
                    timePickerView.dismiss()
                }.show()
        }
        image_clear.setOnClickListener {
            edit_salary.setText("")
        }
        //保存数据到Room
        btn_save.setOnClickListener {
            val nowSalary = homeViewModel.getSalary().value
            if (!TextUtils.isEmpty(nowSalary)) {
                val salaryDao = DataBaseUtil.getSalaryDao(requireContext())
                thread {
                   val long= salaryDao.insert(Salary(year * 100 + month, nowSalary!!.toInt()))
                    if (long>0){
                        btn_save.post {
                            Toast.makeText(requireContext(), getString(R.string.save_success), Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            } else {
                Toast.makeText(requireContext(), getString(R.string.input_salary), Toast.LENGTH_SHORT).show()
            }
        }


    }
}

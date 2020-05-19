package com.loyo.giftapplication.ui.home

import android.graphics.Color
import android.os.Bundle
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.text.format.Time
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CompoundButton
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
import kotlinx.android.synthetic.main.item_view.*
import java.util.*
import kotlin.concurrent.thread

class HomeFragment : Fragment() {
    lateinit var timePickerView: TimePickerView
    lateinit var timePickerViewYear: TimePickerView
    //要存到数据库的年月
    var year: Int = 0
    var month: Int = 0
    private lateinit var homeViewModel: HomeViewModel
    private lateinit var calendarSelect: Calendar
    lateinit var calendarSelectYear: Calendar
    //默认为当前时间
    lateinit var nowCalendar: Calendar

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
        nowCalendar = Calendar.getInstance()
        year = nowCalendar.get(Calendar.YEAR)
        month = nowCalendar.get(Calendar.MONTH) + 1
        text_select_month.text = getString(
            R.string.salary_month_after,
            year,
            month
        )
        //选择月份
        layout_month.setOnClickListener {
            if (!ck_bonus.isChecked) {
                if (!this::timePickerView.isInitialized) {
                    val type = booleanArrayOf(true, true, false, false, false, false)
                    timePickerView =
                        TimePickerBuilder(requireContext()).setTitleText(getString(R.string.select_month))
                            .setRangDate(
                                TimePickerBuilder.startCalendar,
                                nowCalendar
                            )
                            .setType(type).build()
                }
                val initDate =
                    if (this::calendarSelect.isInitialized) calendarSelect else nowCalendar
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
            } else {
                if (!this::timePickerViewYear.isInitialized) {
                    val type = booleanArrayOf(true, false, false, false, false, false)
                    timePickerViewYear =
                        TimePickerBuilder(requireContext()).setTitleText(getString(R.string.select_year))
                            .setRangDate(
                                TimePickerBuilder.startCalendar,
                                nowCalendar
                            )
                            .setType(type).build()
                }
                val initDate =
                    if (this::calendarSelectYear.isInitialized) calendarSelectYear else nowCalendar
                timePickerViewYear.setDate(initDate).setBtnCancelVisible(View.GONE)
                    .setBtnClearVisible(View.GONE)
                    .setOnTimeSelectListener { date, _ ->
                        calendarSelectYear = Calendar.getInstance()
                        calendarSelectYear.time = date
                        year = calendarSelectYear.get(Calendar.YEAR)
                        text_select_month.text = getString(
                            R.string.which_year_bonus,
                            year
                        )
                        timePickerViewYear.dismiss()
                    }.show()
            }
        }
        image_clear.setOnClickListener {
            edit_salary.setText("")
        }
        //选择是否是年终奖
        ck_bonus.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                var calendar = nowCalendar
                if (this::calendarSelectYear.isInitialized) {
                    calendar = calendarSelectYear
                }
                text_select_month.text = requireContext().getString(
                    R.string.which_year_bonus,
                    calendar.get(Calendar.YEAR)
                )
                text_month.text = "年份："
            } else {
                text_month.text = "月份："
                var canlendar = nowCalendar
                if (this::calendarSelect.isInitialized) {
                    canlendar = calendarSelect

                }
                val year = canlendar.get(Calendar.YEAR)
                val month = canlendar.get(Calendar.MONTH) + 1
                text_select_month.text = getString(
                    R.string.salary_month_after,
                    year,
                    month
                )
            }
        }
        //保存数据到Room
        btn_save.setOnClickListener {
            val nowSalary = homeViewModel.getSalary().value
            if (!TextUtils.isEmpty(nowSalary)) {
                val salaryDao = DataBaseUtil.getSalaryDao(requireContext())
                thread {
                    val yearMonth = if (ck_bonus.isChecked) year * 100 + 13 else year * 100 + month
                    val long = salaryDao.insert(Salary(yearMonth, nowSalary!!.toInt()))
                    if (long > 0) {
                        btn_save.post {
                            Toast.makeText(
                                requireContext(),
                                getString(R.string.save_success),
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                }
            } else {
                Toast.makeText(
                    requireContext(),
                    getString(R.string.input_salary),
                    Toast.LENGTH_SHORT
                ).show()
            }
        }


    }
}

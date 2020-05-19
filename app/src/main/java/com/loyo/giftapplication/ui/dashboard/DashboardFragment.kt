package com.loyo.giftapplication.ui.dashboard

import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bigkoo.pickerview.builder.TimePickerBuilder
import com.bigkoo.pickerview.builder.TimePickerBuilder.startCalendar
import com.bigkoo.pickerview.view.TimePickerView
import com.loyo.giftapplication.R
import com.loyo.giftapplication.databinding.FragmentDashboardBinding
import com.loyo.giftapplication.utils.FastClick
import kotlinx.android.synthetic.main.fragment_dashboard.*
import kotlinx.android.synthetic.main.fragment_home.*
import java.time.YearMonth
import java.util.*
import kotlin.properties.Delegates

class DashboardFragment : Fragment() {

    private lateinit var dashboardViewModel: DashboardViewModel
    private lateinit var timePickerView: TimePickerView
    private lateinit var calendarStart: Calendar
    private lateinit var calendarEnd: Calendar
    private lateinit var calendarSelectYear: Calendar
    private lateinit var timePickerViewSelectYear: TimePickerView
    private lateinit var dataBinding: FragmentDashboardBinding
    private var startYearMonth by Delegates.notNull<Int>()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        dashboardViewModel =
            ViewModelProvider(this).get(DashboardViewModel::class.java)
        dataBinding = DataBindingUtil.inflate<FragmentDashboardBinding>(
            inflater,
            R.layout.fragment_dashboard,
            container,
            false
        ).also {
            it.total = dashboardViewModel.getTotal()
            val calender = Calendar.getInstance()
            it.totalYear = dashboardViewModel.getYearTotal(calender.get(Calendar.YEAR))
            it.yearBonus = dashboardViewModel.getYearBonus(calender.get(Calendar.YEAR))
            it.totalBonus=dashboardViewModel.getTotalBonus()
            it.totalBonusMinMax=dashboardViewModel.getTotalBonusMaxMin()
            it.lifecycleOwner = this
        }

        return dataBinding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        scrollView.parent
        super.onActivityCreated(savedInstanceState)
        //选择年份
        txt_select_year.setOnClickListener {
            if (!this::timePickerViewSelectYear.isInitialized) {
                val type = booleanArrayOf(true, false, false, false, false, false)
                timePickerViewSelectYear =
                    TimePickerBuilder(requireContext()).setTitleText(getString(R.string.select_year))
                        .setRangDate(startCalendar, Calendar.getInstance())
                        .setType(type).build()
            }
            val initDate =
                if (this::calendarSelectYear.isInitialized) calendarSelectYear else Calendar.getInstance()
            timePickerViewSelectYear.setDate(initDate).setBtnCancelVisible(View.GONE)
                .setBtnClearVisible(View.GONE)
                .setOnTimeSelectListener { date, _ ->
                    calendarSelectYear = Calendar.getInstance()
                    calendarSelectYear.time = date
                    val year = calendarSelectYear.get(Calendar.YEAR)
                    txt_select_year.text = getString(
                        R.string.salary_year_after,
                        year
                    )
                    dataBinding.totalYear = dashboardViewModel.getYearTotal(year)
                    dataBinding.yearBonus=dashboardViewModel.getYearBonus(year)
                    timePickerViewSelectYear.dismiss()
                }.show()
        }
        //选择开始月份
        txt_start_month.setOnClickListener {

            if (!this::timePickerView.isInitialized) {
                val type = booleanArrayOf(true, true, false, false, false, false)
                timePickerView =
                    TimePickerBuilder(requireContext()).setTitleText(getString(R.string.select_month))
                        .setRangDate(startCalendar, Calendar.getInstance())
                        .setType(type).build()
            }
            val initDate =
                if (this::calendarStart.isInitialized) calendarStart else Calendar.getInstance()
            timePickerView.setDate(initDate).setBtnCancelVisible(View.GONE)
                .setBtnClearVisible(View.GONE)
                .setOnTimeSelectListener { date, _ ->
                    calendarStart = Calendar.getInstance()
                    calendarStart.time = date
                    val year = calendarStart.get(Calendar.YEAR)
                    val month = calendarStart.get(Calendar.MONTH) + 1
                    startYearMonth = year * 100 + month
                    txt_start_month.text = getString(
                        R.string.salary_month_after,
                        year,
                        month
                    )
                    timePickerView.dismiss()
                }.show()
        }
        //选择结束月份
        txt_end_month.setOnClickListener {
            if (FastClick.click(500)) {
                return@setOnClickListener
            }
            if (TextUtils.equals(txt_start_month.text, getString(R.string.select_start_month))) {
                Toast.makeText(
                    requireContext(),
                    getString(R.string.select_start_time_first),
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                val type = booleanArrayOf(true, true, false, false, false, false)
                val timePickerView =
                    TimePickerBuilder(requireContext()).setTitleText(getString(R.string.select_month))
                        .setRangDate(calendarStart, Calendar.getInstance())
                        .setType(type).build()
                val initDate =
                    if (this::calendarEnd.isInitialized) calendarEnd else Calendar.getInstance()
                timePickerView
                    .setDate(initDate).setBtnCancelVisible(View.GONE)
                    .setBtnClearVisible(View.GONE)
                    .setOnTimeSelectListener { date, _ ->
                        calendarEnd = Calendar.getInstance()
                        calendarEnd.time = date
                        val year = calendarEnd.get(Calendar.YEAR)
                        val month = calendarEnd.get(Calendar.MONTH) + 1
                        txt_end_month.text = getString(
                            R.string.salary_month_after,
                            year,
                            month
                        )
                        dataBinding.totalDefineYearMonth =
                            dashboardViewModel.getDefineMonthAll(startYearMonth, year * 100 + month)
                        timePickerView.dismiss()
                    }.show()
            }
        }

    }

}

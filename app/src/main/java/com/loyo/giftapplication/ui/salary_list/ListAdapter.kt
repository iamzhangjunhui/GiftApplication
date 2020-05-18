package com.loyo.giftapplication.ui.salary_list

import android.content.Context
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.loyo.giftapplication.R
import com.loyo.giftapplication.room_data.Salary
import kotlinx.android.synthetic.main.item_view.view.*

class ListAdapter(val salarys: MutableList<Salary>) :
    RecyclerView.Adapter<ListAdapter.ListViewHolder>() {
    lateinit var context: Context

    init {
        salarys.add(0, Salary(0, 0))
    }

    class ListViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        context = parent.context
        val view = LayoutInflater.from(context).inflate(R.layout.item_view, parent, false)
        return ListViewHolder(view)
    }

    override fun getItemCount(): Int {
        return salarys.size
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        if (position == 0) {
            holder.itemView.txt_month.text=context.getString(R.string.show_month)
            holder.itemView.txt_salary.text=context.getString(R.string.month_salary)
        } else {
            val yearMonth = salarys[position].yearAndMonth
            holder.itemView.txt_month.text =
                context.getString(R.string.year_and_month, yearMonth / 100, yearMonth % 100)
            holder.itemView.txt_salary.text =
                context.getString(R.string.salary_detail, salarys[position].monthSalary)
        }
    }
}
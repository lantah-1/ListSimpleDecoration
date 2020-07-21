package com.lamandys.recyclerviewitemdecorationdemo.source

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.lamandys.recyclerviewitemdecorationdemo.R

/**
 * Created by @Author(lamandys) on 2020/7/17 2:58 PM.
 */
class DateAdapter(private val dataList: MutableList<DateBean>) :
    RecyclerView.Adapter<DateAdapter.DateViewHolder>() {

    private var myDateList: MutableList<DateBean> = dataList

    fun refreshData(dataList: List<DateBean>) {
        myDateList.clear()
        myDateList.addAll(dataList)
        notifyDataSetChanged()
    }

    class DateViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val textView = view.findViewById<TextView>(R.id.itemName)

        fun bind(bean: DateBean) {
            textView?.text = bean.itemName
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DateViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.list_item_date, parent, false)
        return DateViewHolder(view)
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    override fun onBindViewHolder(holder: DateViewHolder, position: Int) {
        holder.bind(dataList[holder.adapterPosition])
    }
}
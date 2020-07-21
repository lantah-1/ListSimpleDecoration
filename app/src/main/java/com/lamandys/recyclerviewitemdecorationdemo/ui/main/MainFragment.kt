package com.lamandys.recyclerviewitemdecorationdemo.ui.main

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.lamandys.decoration.LineDecoration
import com.lamandys.decoration.StickyDecoration
import com.lamandys.decoration.StickyHoverBuilder
import com.lamandys.recyclerviewitemdecorationdemo.R
import com.lamandys.recyclerviewitemdecorationdemo.source.DateAdapter
import kotlinx.android.synthetic.main.main_fragment.*

class MainFragment : Fragment() {

    companion object {
        fun newInstance() = MainFragment()
    }

    private lateinit var viewModel: MainViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.main_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)

        viewModel.myListData.observe(viewLifecycleOwner, Observer {
            (mainRecyclerList?.adapter as? DateAdapter)?.refreshData(it)
        })

        viewModel.fetchListData()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        mainRecyclerList.layoutManager = LinearLayoutManager(context)
        mainRecyclerList.adapter = DateAdapter(arrayListOf())

        val stickyDecoration = StickyDecoration.Builder()
            .stickyTextColor(Color.parseColor("#456EFF"))
            .stickyTextSize(14)
            .stickyItemBackgroundColor(Color.parseColor("#5E6D82"))
            .stickyItemHeight(150)
            .stickTextMarginLeft(100f)
            .withStickyHoverBuilder(object :
                StickyHoverBuilder {
                override fun isFirstItemInGroup(position: Int): Boolean {
                    val date = viewModel.myListData.value?.get(position) ?: return false
                    return date.isFirstItemInGroup
                }

                override fun groupName(position: Int): String {
                    val date = viewModel.myListData.value?.get(position) ?: return "Unknow"
                    return date.groupName
                }

            })
            .build()
        mainRecyclerList.addItemDecoration(stickyDecoration)
        mainRecyclerList.addItemDecoration(LineDecoration.default())
    }
}
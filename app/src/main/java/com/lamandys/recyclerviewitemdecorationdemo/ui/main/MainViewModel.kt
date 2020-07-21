package com.lamandys.recyclerviewitemdecorationdemo.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.lamandys.recyclerviewitemdecorationdemo.source.DateBean

class MainViewModel : ViewModel() {

    private val _listData = MutableLiveData<List<DateBean>>()
    val myListData = _listData

    fun fetchListData() {
        val list = arrayListOf<DateBean>()
        for (i in 0..100) {
            val bean = DateBean("My ${(i / 18) + 1} Weeks", "第 $i 天")
            bean.isFirstItemInGroup = i % 18 == 0
            list.add(bean)
        }
        _listData.value = list
    }
}
package com.lamandys.decoration

/**
 * Created by @Author(lamandys) on 2020/7/17 3:42 PM.
 */
interface StickyHoverBuilder {

    fun isFirstItemInGroup(position: Int): Boolean

    fun groupName(position: Int): String

}
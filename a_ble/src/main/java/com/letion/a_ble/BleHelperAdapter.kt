/*
 *
 *  Copyright 2017 liu-feng
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  imitations under the License.
 *
 */

package com.letion.a_ble

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.weyee.posres.callback.Callback1

/**
 *
 * @author wuqi by 2019/2/21.
 */
class BleHelperAdapter(private var list: MutableList<String>?, private val callback1: Callback1<String>?) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    init {
        if (list == null) {
            list = mutableListOf()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(android.R.layout.simple_list_item_1, parent, false)
        return object : RecyclerView.ViewHolder(view) {
        }
    }

    override fun getItemCount(): Int = list?.size ?: 0

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder.itemView as TextView).text = list?.get(position)
        holder.itemView.setOnClickListener { callback1?.call(list?.get(position)) }
    }

    fun addData(vararg args: String) {
        list?.addAll(args)
        notifyDataSetChanged()
    }
}
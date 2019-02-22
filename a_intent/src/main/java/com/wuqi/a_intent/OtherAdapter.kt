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

package com.wuqi.a_intent

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.weyee.posres.callback.Callback1


/**
 *
 * @author wuqi by 2019/2/13.
 */
class OtherAdapter(list: List<OtherItem>,callback: Callback1<Intent>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    var list: List<OtherItem>? = list
    var callback: Callback1<Intent>? = callback


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return IntentViewHolder(
            LayoutInflater.from(parent.context).inflate(
                android.R.layout.simple_list_item_1,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val data = list?.get(position)
        val view = holder.itemView as TextView
        view.text = data?.title
        holder.itemView.setOnClickListener {
            callback?.call(data?.intent)
        }
    }

    override fun getItemCount(): Int {
        if (list == null) {
            return 0
        }
        return list!!.size
    }
}
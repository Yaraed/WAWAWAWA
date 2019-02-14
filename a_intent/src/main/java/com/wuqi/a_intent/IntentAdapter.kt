/*
 *
 *  Copyright 2017 liu-feng
 *
 *  Licensed under the Apache License, Version 2.0 (the "License")
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
import android.net.Uri
import android.provider.Settings
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.weyee.sdk.permission.IntentEnum
import com.weyee.sdk.permission.PermissionIntents


/**
 *
 * @author wuqi by 2019/2/13.
 */
class IntentAdapter(list: Array<IntentEnum>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    var list: Array<IntentEnum>? = list


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
        view.text = data?.label
        holder.itemView.setOnClickListener {

            if (data?.path == null){
                PermissionIntents.toPermissionSetting(view.context)
                return@setOnClickListener
            }else{
                val intent = Intent()
                intent.action = data.path
                if (data.path == Settings.ACTION_APPLICATION_DETAILS_SETTINGS){
                    intent.data = Uri.parse("package:" + view.context.packageName)
                }
                view.context.startActivity(intent)
            }
        }
    }

    override fun getItemCount(): Int {
        if (list == null) {
            return 0
        }
        return list!!.size
    }
}
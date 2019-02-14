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

import android.app.AlertDialog
import android.content.Context
import android.text.TextUtils
import com.yanzhenjie.permission.Permission
import com.yanzhenjie.permission.Rationale
import com.yanzhenjie.permission.RequestExecutor




/**
 *
 * @author wuqi by 2019/2/13.
 */
class RuntimeRationale : Rationale<List<String>> {
    override fun showRationale(context: Context?, permissions: List<String>?, executor: RequestExecutor?) {
        val permissionNames = Permission.transformText(context, permissions)
        val message = context?.getString(R.string.message_permission_rationale, TextUtils.join("\n", permissionNames))
        AlertDialog.Builder(context)
            .setCancelable(false)
            .setTitle(R.string.title_dialog)
            .setMessage(message)
            .setPositiveButton(R.string.resume) { dialog, which -> executor?.execute() }
            .setNegativeButton(R.string.cancel) { dialog, which -> executor?.cancel() }
            .show()
    }
}
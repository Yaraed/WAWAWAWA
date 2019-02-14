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
import com.yanzhenjie.permission.Rationale
import com.yanzhenjie.permission.RequestExecutor
import java.io.File

/**
 *
 * @author wuqi by 2019/2/13.
 */
class InstallRationale : Rationale<File> {

    override fun showRationale(context: Context?, data: File?, executor: RequestExecutor?) {
        AlertDialog.Builder(context)
            .setCancelable(false)
            .setTitle(R.string.title_dialog)
            .setMessage(R.string.message_install_failed)
            .setPositiveButton(R.string.setting) { _, _ -> executor?.execute() }
            .setNegativeButton(R.string.cancel) { _, _ -> executor?.cancel() }
            .show()
    }
}
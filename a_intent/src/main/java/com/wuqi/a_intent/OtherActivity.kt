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

import android.content.ActivityNotFoundException
import android.os.Bundle
import android.os.Environment
import androidx.recyclerview.widget.LinearLayoutManager
import com.alibaba.android.arouter.facade.annotation.Route
import com.weyee.poscore.base.BaseActivity
import com.weyee.poscore.di.component.AppComponent
import com.weyee.poscore.mvp.BaseModel
import com.weyee.poscore.mvp.BasePresenter
import com.weyee.poscore.mvp.IView
import com.weyee.posres.callback.Callback1
import com.weyee.sdk.multitype.HorizontalDividerItemDecoration
import com.weyee.sdk.permission.*
import com.weyee.sdk.router.IntentNavigation
import com.weyee.sdk.toast.ToastUtils
import kotlinx.android.synthetic.main.activity_other.*


@Route(path = IntentNavigation.MODULE_NAME + "Other")
class OtherActivity : BaseActivity<BasePresenter<BaseModel, IView>>() {

    override fun setupActivityComponent(appComponent: AppComponent?) {
    }

    override fun getResourceId(): Int = R.layout.activity_other

    override fun initView(savedInstanceState: Bundle?) {
        recyclerView.layoutManager = LinearLayoutManager(this@OtherActivity)

        val res = resources
        val intents = ArrayList<OtherItem>()

        // PhoneIntents
        intents.add(OtherItem(res.getString(R.string.dialer), PhoneIntents.newDialNumberIntent(null)))
        intents.add(OtherItem(res.getString(R.string.call_number), PhoneIntents.newCallNumberIntent("+123456789")))
        intents.add(OtherItem(res.getString(R.string.dial_number), PhoneIntents.newDialNumberIntent("+123456789")))
        intents.add(
            OtherItem(
                res.getString(R.string.send_sms_to),
                PhoneIntents.newSmsIntent(this, "this is a test SMS", "+123456789")
            )
        )
        intents.add(
            OtherItem(
                res.getString(R.string.send_sms),
                PhoneIntents.newSmsIntent(this, "this is a test SMS")
            )
        )
        intents.add(OtherItem(res.getString(R.string.pick_contact), PhoneIntents.newPickContactIntent()))
        intents.add(
            OtherItem(
                res.getString(R.string.pick_contact_with_phone),
                PhoneIntents.newPickContactWithPhoneIntent()
            )
        )

        // GeoIntents
        intents.add(
            OtherItem(
                res.getString(R.string.map_of),
                GeoIntents.newMapsIntent("1 rue du louvre 75000 Paris", "Le Louvre, Paris")
            )
        )
        intents.add(
            OtherItem(
                res.getString(R.string.map_at),
                GeoIntents.newMapsIntent(43.481055f, -1.561959f, "Biarritz, France")
            )
        )
        intents.add(
            OtherItem(
                res.getString(R.string.navigate_to_address),
                GeoIntents.newNavigationIntent("1 rue du louvre 75000 Paris")
            )
        )
        intents.add(
            OtherItem(
                res.getString(R.string.navigate_to_location),
                GeoIntents.newNavigationIntent(43.481055f, -1.561959f)
            )
        )
        intents.add(
            OtherItem(
                res.getString(R.string.streetview_at_location),
                GeoIntents.newStreetViewIntent(43.481055f, -1.561959f)
            )
        )

        // MediaIntents
        intents.add(
            OtherItem(
                res.getString(R.string.play_image),
                MediaIntents.newPlayImageIntent("http://upload.wikimedia.org/wikipedia/commons/thumb/a/a9/Biarritz-Plage.JPG/1920px-Biarritz-Plage.JPG")
            )
        )
        intents.add(
            OtherItem(
                res.getString(R.string.play_audio),
                MediaIntents.newPlayAudioIntent("http://www.stephaniequinn.com/Music/Allegro%20from%20Duet%20in%20C%20Major.mp3")
            )
        )
        intents.add(
            OtherItem(
                res.getString(R.string.play_video),
                MediaIntents.newPlayVideoIntent("http://mirror.bigbuckbunny.de/peach/bigbuckbunny_movies/big_buck_bunny_480p_h264.mov")
            )
        )
        intents.add(
            OtherItem(
                res.getString(R.string.play_video_youtube),
                MediaIntents.newPlayYouTubeVideoIntent("b_yiWIXBI7o")
            )
        )
        intents.add(
            OtherItem(
                res.getString(R.string.browse_web),
                MediaIntents.newOpenWebBrowserIntent("http://vincentprat.info")
            )
        )
        intents.add(
            OtherItem(
                res.getString(R.string.take_pic),
                MediaIntents.newTakePictureIntent(Environment.getExternalStorageDirectory().toString() + "/temp.jpg")
            )
        )
        intents.add(OtherItem(res.getString(R.string.select_pic), MediaIntents.newSelectPictureIntent()))

        // EmailIntents
        intents.add(
            OtherItem(
                res.getString(R.string.email_to),
                EmailIntents.newEmailIntent("test@example.com", "My subject", "My content")
            )
        )

        // ShareIntents
        intents.add(
            OtherItem(
                res.getString(R.string.share),
                ShareIntents.newShareTextIntent("My subject", "My message", getString(R.string.share_dialog_title))
            )
        )

        // SystemIntents
        intents.add(
            OtherItem(
                res.getString(R.string.app_store),
                SystemIntents.newMarketForAppIntent(this, "fr.marvinlabs.coverartwallpaper")
            )
        )
        recyclerView.addItemDecoration(HorizontalDividerItemDecoration.Builder(this).margin(10,10).build())
        recyclerView.adapter = OtherAdapter(intents, Callback1 {
            try {
                startActivity(it)
            }catch (e : ActivityNotFoundException){
                ToastUtils.show("无法找到该页面")
            }
        })
    }

    override fun initData(savedInstanceState: Bundle?) {
    }
}

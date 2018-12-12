package com.letion.core

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import me.leolin.shortcutbadger.ShortcutBadger
import org.json.JSONObject
import java.nio.charset.Charset

class MainActivity : AppCompatActivity(), MainView {

    private lateinit var presenter: MainPresenter
    private var dialog: Dialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //tvContent.text = printJson(parseJson(readJson()))
        Thread {
            while (true) {
                ShortcutBadger.applyCount(applicationContext, (Math.random() * 1000).toInt())
                Thread.sleep(2000)
            }
        }.start()

        presenter = MainPresenter(this)

        val array = arrayOfNulls<String>(10)
        for (i in 0 until 10) {
            array[i] = "这是第${i}个位置"
        }

        listView.adapter = ArrayAdapter<String>(baseContext, android.R.layout.simple_list_item_1, array)

        listView.onItemClickListener = AdapterView.OnItemClickListener { _, _, i, _ ->
            run {
                if (i == 1) {
                    presenter.getBook()
                } else if (i == 2) {
                    presenter.cancelBook()
                } else if (i == 3) {
                    presenter.getBook(true)
                } else if (i == 4) {
                    presenter.cancelBook(true)
                }
            }
        }
    }

    fun readJson(): String {
        val inputStream = assets.open("emoji.json")
        val length = inputStream.available()
        val byteArray = ByteArray(length)
        inputStream.read(byteArray)
        inputStream.close()
        return String(byteArray, Charset.defaultCharset())
    }

    fun parseJson(json: String): List<Emoji> {
        val list = ArrayList<Emoji>()
        val jsonArray = JSONObject(json).optJSONArray("list")
        if (jsonArray != null) {
            for (i in 0 until jsonArray.length()) {
                val jsonObject = jsonArray.optJSONObject(i)
                list.add(Emoji(jsonObject?.optString("name"), jsonObject.optInt("unicode")))
            }
        }
        return list
    }

    fun printJson(array: List<Emoji>): String {
        val builder = StringBuilder()
        array.forEach {
            builder.append(it.unicodeTemp)
        }
        return builder.toString()
    }

    data class Emoji(var name: String?, var unicode: Int) {
        val unicodeTemp: String?
            get() = String(Character.toChars(unicode))
    }

    override fun dialog(): Dialog? {
        if (dialog == null) {
            dialog = AlertDialog.Builder(this).setMessage("loading...").create()
        }
        return dialog
    }
}

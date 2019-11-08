package com.as1124.ui

import android.app.*
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.as1124.selflib.AppTaskUtil
import com.as1124.selflib.WindowUtils
import com.as1124.selflib.biology.BiologyVerifyCenter
import com.as1124.ui.layout.AboutLayoutActivity
import com.as1124.ui.layout.card.AboutCardviewActivity
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_main.*

/**
 * @author As-1124 (mailto:as1124huang@gmail.com)
 */
class MainActivity : Activity(), View.OnClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)
        Snackbar.make(
            window.decorView.findViewById<View>(android.R.id.content),
            "请进行操作",
            Snackbar.LENGTH_INDEFINITE
        ).setText("沙雕").setAction("关闭", this).show()


        btn_to_layout.setOnClickListener(this)
        btn_test.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        if (v is Snackbar) {
            v.dismiss()
            return
        }
        when (v.id) {
            R.id.btn_to_layout -> {
                startActivity(Intent(this, AboutLayoutActivity::class.java))
            }
            R.id.btn_test -> {
                testButtonClick()
            }
        }
    }

    fun testButtonClick() {
        AppTaskUtil.exitApp(this, taskId)
    }
}

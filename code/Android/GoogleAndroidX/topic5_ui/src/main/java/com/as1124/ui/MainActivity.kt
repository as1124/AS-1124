package com.as1124.ui

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.as1124.ui.layout.AboutLayoutActivity
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_main.*

/**
 * @author As-1124 (mailto:as1124huang@gmail.com)
 */
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)
        var mainContainer: View = window.decorView.findViewById<View>(android.R.id.content)
        Snackbar.make(mainContainer, "请进行操作", Snackbar.LENGTH_INDEFINITE)
            .setText("沙雕").setAction("关闭") { bar ->
                if (bar is Snackbar) {
                    bar.dismiss()
                } else {
                }
            }.show()


        btn_to_layout.setOnClickListener {
            startActivity(
                Intent(
                    this,
                    AboutLayoutActivity::class.java
                )
            )
        }

    }
}

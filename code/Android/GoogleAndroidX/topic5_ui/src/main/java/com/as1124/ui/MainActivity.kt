package com.as1124.ui

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.as1124.selflib.hardware.FlashLightUtil
import com.as1124.ui.layout.AboutLayoutActivity
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


        if (Build.VERSION.SDK_INT >= 23) {
            if (checkCallingOrSelfPermission(Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(arrayOf(Manifest.permission.READ_PHONE_STATE), 1122)
            }

            if (checkCallingOrSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(
                    arrayOf(
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE
                    ), 2233
                )
            }

            if (checkCallingOrSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(arrayOf(Manifest.permission.CAMERA), 3344)
            }
        }

        btn_to_layout.setOnClickListener(this)

        btn_test.setOnClickListener(this)
        btn_flash_on.setOnClickListener(this)
        btn_flash_off.setOnClickListener(this)

    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        Toast.makeText(this, permissions[0], Toast.LENGTH_SHORT).show()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (RESULT_OK == resultCode && requestCode == 1234) {

        }
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
            R.id.btn_flash_on -> {
                FlashLightUtil.enableFlashTorch(this, true)
            }
            R.id.btn_flash_off -> {
                FlashLightUtil.enableFlashTorch(this, false)
            }
        }
    }

    private fun testButtonClick() {
        var intent = Intent(Intent.ACTION_GET_CONTENT).apply {
            type = "image/*"
            addCategory(Intent.CATEGORY_OPENABLE)
        }
        startActivityForResult(intent, 1234)
    }

}

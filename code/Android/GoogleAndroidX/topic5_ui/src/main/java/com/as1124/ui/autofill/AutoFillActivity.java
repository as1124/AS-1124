package com.as1124.ui.autofill;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.as1124.ui.R;

public class AutoFillActivity extends Activity implements View.OnClickListener {

    private EditText mUserText;

    private EditText mPwdText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auto_fill);


        mUserText = findViewById(R.id.text_user);
        mUserText.setOnClickListener(this);


        mPwdText = findViewById(R.id.text_pwd);
        mPwdText.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.text_user:
                break;
            case R.id.text_pwd:
                break;
            default:
                break;
        }
    }
}

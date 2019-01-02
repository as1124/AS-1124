package com.as1124.ch2_material.textinput;

import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;

import com.as1124.ch2_material.R;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LoginActivity extends AppCompatActivity {

    private static final String EMAIL_PATTERN = "^[a-zA-Z0-9#_~!$&'()*+,;=:.\"(),:;<>@\\[\\]\\\\]+@[a-zA-Z0-9-]+(\\.[a-zA-Z0-9-]+)*$";

    private Pattern pattern;

    private TextInputLayout accountLayout;
    private TextInputLayout passwordLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        pattern = Pattern.compile(EMAIL_PATTERN);
        accountLayout = findViewById(R.id.material_layout_account);
        passwordLayout = findViewById(R.id.material_layout_password);
        findViewById(R.id.but_login_account).setOnClickListener(v -> login());

    }

    private void login() {
        String userName = accountLayout.getEditText().getText().toString();
        String password = passwordLayout.getEditText().getText().toString();
        if (TextUtils.isEmpty(userName) || TextUtils.isEmpty(password)) {
            return;
        }

        if (validateUserName(userName)) {
            accountLayout.setErrorEnabled(false);
        } else {
            accountLayout.setErrorEnabled(true);
            accountLayout.setError("请输入正确的邮箱地址");
        }

        if (validatePassword(password)) {
            passwordLayout.setErrorEnabled(false);
        } else {
            passwordLayout.setErrorEnabled(true);
            passwordLayout.setError("密码字数过少");
        }
    }

    private boolean validatePassword(String password) {
        return (!TextUtils.isEmpty(password) && password.length() > 6);
    }

    private boolean validateUserName(String username) {
        Matcher matcher = pattern.matcher(username);
        return matcher.matches();
    }
}

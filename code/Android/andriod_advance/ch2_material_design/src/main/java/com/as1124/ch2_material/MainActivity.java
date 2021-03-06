package com.as1124.ch2_material;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.as1124.ch2_material.list.RecyclerListActivity;
import com.as1124.ch2_material.tab.TabLayoutActivity;
import com.as1124.ch2_material.textinput.LoginActivity;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputLayout;

/**
 * Material Design Support控件苦的使用
 * <ol>
 * <li>{@link Snackbar}</li>
 * <li>{@link TextInputLayout}</li>
 * </ol>
 *
 * @author as-1124(mailto:as1124huang@gmail.com)
 */
public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.btn_snackbar).setOnClickListener(v -> snackBarUsage());
        findViewById(R.id.btn_to_login).setOnClickListener(v ->
                startActivity(new Intent(MainActivity.this, LoginActivity.class)));
        findViewById(R.id.btn_to_floating).setOnClickListener(v ->
                startActivity(new Intent(MainActivity.this, FloatingButtonActivity.class)));
        findViewById(R.id.btn_to_tablayout).setOnClickListener(v ->
                startActivity(new Intent(MainActivity.this, TabLayoutActivity.class)));
        findViewById(R.id.btn_to_list).setOnClickListener(v ->
                startActivity(new Intent(this, RecyclerListActivity.class)));

    }

    private void snackBarUsage() {
        View frameLayout = getWindow().getDecorView().findViewById(android.R.id.content);
        View userRoot = ((ViewGroup) frameLayout).getChildAt(0);
        Snackbar snackbar = Snackbar.make(findViewById(R.id.coorLayout), "来呀, 快活呀", Snackbar.LENGTH_INDEFINITE);
        // 只能显示一个Action
        snackbar.setAction("Fuck one", v -> Toast.makeText(MainActivity.this, "游刃有余", Toast.LENGTH_SHORT).show())
                .setAction("Three Happy?", v -> Toast.makeText(this, "神仙也嫉妒", Toast.LENGTH_SHORT).show())
                .setAction("As much as possible?", v -> Toast.makeText(this, "有预谋的谋杀", Toast.LENGTH_SHORT).show())
                .show();
    }
}

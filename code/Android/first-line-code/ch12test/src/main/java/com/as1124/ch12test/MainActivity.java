package com.as1124.ch12test;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.v7Toolbar);
        toolbar.setBackgroundColor(getResources().getColor(R.color.colorAccent));
        setSupportActionBar(toolbar);

        findViewById(R.id.but_to_drawer).setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, DrawerActivity.class);
            startActivity(intent);
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemID = item.getItemId();
        switch (itemID) {
            case R.id.backup:
                break;
            case R.id.delete:
                break;
            case R.id.settings:
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}

package com.as1124.ch13test;

import android.app.Activity;
import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;

import com.as1124.ch13test.model.Book;
import com.as1124.ch13test.model.Person;

/**
 * @author as-1124(mailto:as1124huang@gmail.com)
 */
public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.but_different_context).setOnClickListener(v -> {
            //
        });
        findViewById(R.id.but_intent_parcelable).setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, IntentExtraActivity.class);
            Person p = new Person("呵呵呵", 26);
            Book b = new Book();
            b.setName("30天精通所有");
            b.setPrice(55);
            intent.putExtra("person", p);
            intent.putExtra("book", b);
            startActivity(intent);
        });
        findViewById(R.id.but_timer_task).setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, TaskActivity.class);
            startActivity(intent);
        });
    }
}

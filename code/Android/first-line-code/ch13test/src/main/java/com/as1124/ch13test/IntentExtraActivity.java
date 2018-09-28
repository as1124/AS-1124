package com.as1124.ch13test;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.as1124.ch13test.model.Book;
import com.as1124.ch13test.model.Person;

public class IntentExtraActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intent_extra);

        TextView infoText = findViewById(R.id.text_show_extra);

        Intent intent = getIntent();
        if (intent != null) {
            Person person = (Person) intent.getSerializableExtra("person");
            Book book = intent.getParcelableExtra("book");
            infoText.setText("收到参数：\nPerson=" + person.getName() + "\nBook=" + book.getName());
        }
    }
}

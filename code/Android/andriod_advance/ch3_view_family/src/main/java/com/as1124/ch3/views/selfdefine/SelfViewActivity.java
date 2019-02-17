package com.as1124.ch3.views.selfdefine;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.as1124.ch3.views.R;

import java.util.List;

public class SelfViewActivity extends Activity {

    private ListView one;
    private ListView two;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_self_view);

        one = findViewById(R.id.listview_one);
        two = findViewById(R.id.listview_two);

        String[] str1 = {"1", "2", "3", "4", "5", "11", "12", "13", "14", "15", "21", "332", "213", "414", "1115"};
        ArrayAdapter<String> adapter1 = new ArrayAdapter<>(this, android.R.layout.simple_expandable_list_item_1, str1);
        one.setAdapter(adapter1);

        String[] str2 = {"1a", "2b", "3d", "4c", "5ee", "11f", "12g", "13h", "14kjd", "15aa", "19993h", "18884kjd", "17775aa"};
        ArrayAdapter<String> adapter2 = new ArrayAdapter<>(this, android.R.layout.simple_expandable_list_item_1, str2);
        two.setAdapter(adapter2);


    }
}

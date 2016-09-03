package com.as1124.android.ch4;

import java.util.ArrayList;

import com.as1124.android.ch2.R;

import android.app.Activity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

/**
 * TODO-LIST main activity.
 * 
 * @author huangjw(mailto:huangjw@primeton.com)
 *
 */
public class TodoListActivity extends Activity {

	/* (non-Javadoc)
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		this.setContentView(R.layout.todo_list);
		
		ListView listview = (ListView) findViewById(R.id.todoListview);
		final ArrayList<String> todoItems = new ArrayList<String>();
		final ArrayAdapter<String> listAdapter = new ArrayAdapter<String>(this, 
				android.R.layout.simple_list_item_1, todoItems);
		//将ArrayAdapter绑定到Listview
		listview.setAdapter(listAdapter);
		
		final EditText text = (EditText) findViewById(R.id.todoList_EditText);
		text.setOnKeyListener(new View.OnKeyListener() {
			
			public boolean onKey(View v, int keyCode, KeyEvent event) {
				if(event.getAction() == KeyEvent.ACTION_DOWN){
					if(keyCode==KeyEvent.KEYCODE_DPAD_CENTER || keyCode==KeyEvent.KEYCODE_ENTER){
						todoItems.add(0, text.getText().toString());
						listAdapter.notifyDataSetChanged();
						text.setText("");
						return true;
					}
				}
				return false;
			}
		});
		
	}
	
	
}

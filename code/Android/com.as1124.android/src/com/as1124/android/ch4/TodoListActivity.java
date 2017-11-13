package com.as1124.android.ch4;

import java.util.ArrayList;

import com.as1124.android.ch2.R;
import com.as1124.android.ch4.fragment.TodoListFragment;

import android.app.Activity;
import android.app.FragmentManager;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

/**
 * Todo-list activity.
 * 
 * @author huangjw(mailto:huangjw@primeton.com)
 *
 */
public class TodoListActivity extends Activity implements OnNewItemAddedListener {

	ArrayList<String> todoItems;
	
	ArrayAdapter<String> listAdapter;
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
//		uiFromNormal(savedInstanceState);
		
		uiFromFragment(savedInstanceState);
		
	}
	
	/**
	 * 以普通控件形式创建Todo-list的UI
	 * 
	 * @param savedInstanceState
	 */
	protected void uiFromNormal(Bundle savedInstanceState){
		this.setContentView(R.layout.todo_list);
		
		ListView listview = (ListView) findViewById(R.id.todoListview);
		todoItems = new ArrayList<String>();
		listAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, todoItems);
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
	
	/**
	 * 通过Fragment组件化的形式创建Todo-list Activity的UI
	 * 
	 * @param savedInstanceState
	 */
	protected void uiFromFragment(Bundle savedInstanceState){
		this.setContentView(R.layout.todo_list_by_fragment);
		
		//获取该Fragment的引用
		FragmentManager fm = this.getFragmentManager();
		TodoListFragment todoListFragment = (TodoListFragment) fm.findFragmentById(R.id.TodoListFragment);
		
		//创建to do项的ArrayList
		todoItems = new ArrayList<String>();
		
//		listAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, todoItems);
		
		//自定义listview-item
		listAdapter = new ArrayAdapter<String>(this, R.layout.todo_list_item, todoItems);
		
		//将ArrayAdapter绑定到listview上
		todoListFragment.setListAdapter(listAdapter);
	}

	public void onNewItemAdded(String newItem) {
		todoItems.add(newItem);
		listAdapter.notifyDataSetChanged();
	}
	
}

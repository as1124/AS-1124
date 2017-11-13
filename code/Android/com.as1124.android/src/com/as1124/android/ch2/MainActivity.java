package com.as1124.android.ch2;

import com.as1124.android.ch4.TodoListActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * 
 * 主Activity入口
 * 
 * @author as1124(as1124huang@gmail.com)
 *
 */
public class MainActivity extends Activity {

	protected void onCreate(Bundle savedInstanceState) {
		//在完整生命周期开始时调用, 只执行一次
		super.onCreate(savedInstanceState);
		
		//初始化一个Activity并填充UI
		setContentView(R.layout.activity_main);
		LinearLayout parentLayout = (LinearLayout) this.findViewById(R.id.main_top_parent);
		TextView myTextview = new TextView(this);
		myTextview.setText("Hello, Android");
		parentLayout.addView(myTextview);
		
		//要先setContentView(), 不然程序出错
//		setContentView(R.layout.activity_main);
		
		View todoActivityBut = this.findViewById(R.id.button_todo_list);
		todoActivityBut.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
				Intent intent = new Intent(getApplicationContext(), TodoListActivity.class);
				v.getContext().startActivity(intent);
			}
		});
		
		Log.i("11", "onCreate");
	}

	protected void onRestoreInstanceState(Bundle savedInstanceState) {
		//在onCreate之后调用, 用于恢复ui状态
		super.onRestoreInstanceState(savedInstanceState);
		
		//从savedInstanceState恢复UI状态, 这个Bundle也被传递给了onCreate，
		//自Activity上次可见之后，只有当系统终止了该Activity时，才会被调用。在随后的Activity进程的可见生命周期之前调用
		Log.i("11", "onRestoreInstanceState");
	}
	
	protected void onStart() {
		super.onStart();
		
		Log.i("11", "onStart");
	}
	
	protected void onResume() {
		super.onResume();
		
		Log.i("11", "onResume");
	}

	
	protected void onPause() {
		super.onPause();
		Log.i("11", "onPause");
	}
	
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		
		Log.i("11", "onSaveInstanceState");
	}
	
	protected void onStop() {
		super.onStop();
		
		Log.i("11", "onStop");
	}
	
	protected void onRestart() {
		super.onRestart();
		
		//onRestart()之后执行onStart()及其之后的过程
		Log.i("11", "onRestart");
	}
	
	protected void onDestroy() {
		super.onDestroy();
		
		Log.i("11", "onDestory");
	}
	
	public boolean onCreateOptionsMenu(Menu menu) {
		//ATTENTION 什么时候执行
		
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will automatically handle clicks on the Home/Up button, 
		//so long as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		
		return super.onOptionsItemSelected(item);
	}
}

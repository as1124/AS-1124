package com.as1124.android.ch2;

import com.as1124.android.ch4.TodoListActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
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

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		//在完整生命周期开始时调用, 只执行一次
		super.onCreate(savedInstanceState);

//		hideTitleBar();
//		hideStatusBar();
		
		//初始化一个Activity并填充UI
		setContentView(R.layout.activity_main);
		LinearLayout parentLayout = (LinearLayout) this.findViewById(R.id.main_top_parent);
		TextView myTextview = new TextView(this);
		myTextview.setText("Hello, Android");
		parentLayout.addView(myTextview);

		getStatusBarHeight(this);
		getNavigationHeight(this);
		
		View todoActivityBut = this.findViewById(R.id.button_todo_list);
		todoActivityBut.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {
				Intent intent = new Intent(getApplicationContext(), TodoListActivity.class);
				v.getContext().startActivity(intent);
			}
		});

		Log.i("11", "onCreate");
	}

	/**
	 * 隐藏标题栏
	 */
	private void hideTitleBar() {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		if (getActionBar() != null)
			getActionBar().hide();
	}

	/**
	 * 设置全屏，隐藏状态栏;
	 * 或者设计透明状态栏
	 */
	private void hideStatusBar() {
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

		//设置状态栏透明 
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
		
		//设置导航栏透明
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
	}

	@Override
	public void onWindowFocusChanged(boolean hasFocus) {
		super.onWindowFocusChanged(hasFocus);

		if (hasFocus) {
			View decorView = getWindow().getDecorView();
			decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE //保持布局状态  
					| View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION //布局显示在导航栏下层
					| View.SYSTEM_UI_FLAG_HIDE_NAVIGATION //隐藏导航栏  
					| View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN //布局全屏（占用状态栏高度），显示在状态栏下层
					| View.SYSTEM_UI_FLAG_FULLSCREEN //隐藏状态栏  
					| View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY); //显示状态栏后几秒后消失  
		}
	}

	public static int getStatusBarHeight(Activity context) {
		int result = 0;
		int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
		if (resourceId > 0) {
			result = context.getResources().getDimensionPixelSize(resourceId);
		}
		return result;
	}

	/** 
	 * 获取导航栏高度 
	 * @param context 
	 * @return 
	 */
	public static int getNavigationHeight(Activity context) {
		int result = 0;
		int resourceId = 0;
		int rid = context.getResources().getIdentifier("config_showNavigationBar", "bool", "android");
		if (rid != 0) {
			resourceId = context.getResources().getIdentifier("navigation_bar_height", "dimen", "android");
			result = context.getResources().getDimensionPixelSize(resourceId);
		}
		
		return result;
	}

	@Override
	protected void onRestoreInstanceState(Bundle savedInstanceState) {
		//在onCreate之后调用, 用于恢复ui状态
		super.onRestoreInstanceState(savedInstanceState);

		//从savedInstanceState恢复UI状态, 这个Bundle也被传递给了onCreate，
		//自Activity上次可见之后，只有当系统终止了该Activity时，才会被调用。在随后的Activity进程的可见生命周期之前调用
		Log.i("11", "onRestoreInstanceState");
	}

	@Override
	protected void onStart() {
		super.onStart();

		Log.i("11", "onStart");
	}

	@Override
	protected void onResume() {
		super.onResume();

		Log.i("11", "onResume");
	}

	@Override
	protected void onPause() {
		super.onPause();
		Log.i("11", "onPause");
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);

		Log.i("11", "onSaveInstanceState");
	}

	@Override
	protected void onStop() {
		super.onStop();

		Log.i("11", "onStop");
	}

	@Override
	protected void onRestart() {
		super.onRestart();

		//onRestart()之后执行onStart()及其之后的过程
		Log.i("11", "onRestart");
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();

		Log.i("11", "onDestory");
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		//ATTENTION 什么时候执行

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
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

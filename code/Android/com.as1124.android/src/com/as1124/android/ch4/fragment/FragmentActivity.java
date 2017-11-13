package com.as1124.android.ch4.fragment;

import com.as1124.android.ch2.R;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;

/**
 * @author as-1124(mailto:as1124huang@gmail.com)
 *
 */
public class FragmentActivity extends Activity {

	
	/* (non-Javadoc)
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 */
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		//填充包含Fragment容器的布局（Activity）
		this.setContentView(R.layout.test_frame_layout);
		FragmentManager fm = this.getFragmentManager();
		
		//ATTENTION 这样的类型转换没有错吗？FrameLayout--->Fragment(DetailFragment)
		//请检查该Fragment back栈是否被填充，如果没有过，创建并填充这个布局
		DetailsFragment detailsFragment = (DetailsFragment) fm.findFragmentById(R.id.details_container);
		
		if(detailsFragment == null){
			FragmentTransaction ft = fm.beginTransaction();
			ft.add(R.id.details_container, new DetailsFragment());
			ft.add(R.id.ui_container, new MyListFragment());
			ft.commit();
		}
	}
	
}

package com.as1124.android.ch4.fragment;

import android.app.Activity;
import android.app.ListFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Todo-list的Fragment形式改造
 * Todo事项的listview
 * 
 * @author as-1124(mailto:as1124huang@gmail.com)
 *
 */
public class TodoListFragment extends ListFragment {

	public void onAttach(Activity activity) {
		super.onAttach(activity);
	}
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}
	
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		
		return super.onCreateView(inflater, container, savedInstanceState);
	}
	
}

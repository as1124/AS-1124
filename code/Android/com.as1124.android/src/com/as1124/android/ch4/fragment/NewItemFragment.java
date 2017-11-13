package com.as1124.android.ch4.fragment;

import com.as1124.android.ch2.R;
import com.as1124.android.ch4.OnNewItemAddedListener;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

/**
 * 
 * To-list的Fragment形式改造
 * @author as-1124(mailto:as1124huang@gmail.com)
 *
 */
public class NewItemFragment extends Fragment {

	private OnNewItemAddedListener listener;

	public void onAttach(Activity activity) {
		super.onAttach(activity);
		try {
			listener = (OnNewItemAddedListener) activity;
		} catch (ClassCastException e) {
			throw e;
		}
	}
	
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.new_item_fragment, container, false);
		
		view.findViewById(R.id.myEditText).setOnKeyListener(new View.OnKeyListener() {
			
			@Override
			public boolean onKey(View v, int keyCode, KeyEvent event) {
				if(event.getAction() == KeyEvent.ACTION_DOWN){
					if(keyCode==KeyEvent.KEYCODE_DPAD_CENTER || keyCode==KeyEvent.KEYCODE_ENTER){
						String newItem = ((EditText)v).getText().toString();
						listener.onNewItemAdded(newItem);
						((EditText)v).setText("");
						return true;
					}
				}
				return false;
			}
		});
		
		return view;
	}
	
}

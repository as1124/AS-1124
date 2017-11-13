package com.as1124.android.ch4.fragment;

import com.as1124.android.ch2.R;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * 
 * Fragment.
 * Fragment 不需要在AndroidManifest.xml进行注册，因为Fragment只有嵌套
 * 到Activity中才能够存在，所以它的生命周期也依赖所嵌入的Activity.
 * <li>Fragment一定要和Activity绑定才能使用
 * <li>Fragment的生命周期镜像它所绑定的Activity
 * <li>Fragment可以没有UI
 * 
 * @author as-1124(mailto:as1124huang@gmail.com)
 *
 */
public class MyListFragment extends Fragment {

	public void onAttach(Activity context) {
		super.onAttach(context);
		//调用该方法时，Fragment会被链接到它的父Activity上
		
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//调用该方法进行Fragment的初始化创建；非UI初始化
		
	}
	
	/**
	 * 一旦Fragment被创建完成，要创建它自己的用户界面时调用该方法
	 * @param inflater
	 * @param container
	 * @param savedInstanceState
	 * @return
	 */
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//		return super.onCreateView(inflater, container, savedInstanceState);
		
		//创建或者填充Fragment的UI， 并返回它
		//如果这个Fragment没有UI，返回null
		return inflater.inflate(R.layout.fragment_container_layout, container, false);
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		//一旦父Activity和Fragment的UI已经被创建，则调用该方法
	}
	
	@Override
	public void onStart() {
		super.onStart();
		//在可见生命周期的开始时被调用
		//应用所有需要的UI变化，现在Fragment是可见的
	}
	
	/**
	 * 在活动生命周期的开始时被调用
	 * 恢复所有的Fragment需要的UI更行，线程或者进程，但在非活动状态它是暂停的
	 * @see android.app.Fragment#onResume()
	 */
	@Override
	public void onResume() {
		super.onResume();
		//
	}
	
	/**
	 * 在活动生命周期结束时被调用
	 * 当Activity不是活动的前台Activity时，需要暂停UI的更新，挂起线程或者暂停那些
	 * 不需要更新的CPU的集中处理。由于调用这个方法后，进程可能被终止，所以要保存所有的编辑和状态改变信息
	 */
	public void onPause() {
		super.onPause();
		
	}
	
	/**
	 * 在生命周期结束时，调用该方法保存UI的状态变化
	 * @see android.app.Fragment#onSaveInstanceState(android.os.Bundle)
	 */
	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		//将UI的状态改变信息保存到outState中
		//这个bundle会被传递到onCreate、onCreteView和onActivityCreate方法中
		
	}
	
	/**
	 * 在生命周期结束时调用该方法
	 * @see android.app.Fragment#onStop()
	 */
	@Override
	public void onStop() {
		super.onStop();
		//当Fragment不可见时，暂停其余的UI更新、挂起线程或者暂停不再需要的处理
	}
	
	/**
	 * 当Fragment和View被分离时，清理相关的View资源
	 * @see android.app.Fragment#onDestroyView()
	 */
	@Override
	public void onDestroyView() {
		super.onDestroyView();
	}
	
	/**
	 * 在整个生命周期结束时调用
	 * 清楚所有的资源，包括结束线程和关闭数据库连接
	 * @see android.app.Fragment#onDestroy()
	 */
	@Override
	public void onDestroy() {
		super.onDestroy();
		
	}
	
	/**
	 * 当Fragment从它的父Activity上分离时调用
	 * @see android.app.Fragment#onDetach()
	 */
	@Override
	public void onDetach() {
		super.onDetach();
		
	}
	
}


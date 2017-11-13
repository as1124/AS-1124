package com.as1124.android.ch4.widget;

import com.as1124.android.ch2.R;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.widget.TextView;

/**
 * 
 * 扩展TextView的UI, 并用到Todo-list的Item上
 * 
 * @author as-1124(mailto:as1124huang@gmail.com)
 *
 */
public class TodoListItemView extends TextView {

	private Paint marginPaint;
	
	private Paint linePaint;
	
	private int paperColor;
	
	private float margin;
	
	public TodoListItemView(Context context) {
		super(context);
		init();
	}
	
	public TodoListItemView(Context context, AttributeSet attrs){
		super(context, attrs);
		init();
	}

	public TodoListItemView(Context context, AttributeSet attrs, int defStyle){
		super(context, attrs, defStyle);
		init();
	}

	private void init(){
		//获得对资源表的引用
		Resources myResource = this.getResources();
		
		//创建将在onDraw方法中使用画刷
		marginPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
		marginPaint.setColor(myResource.getColor(R.color.notepad_margin));
		
		linePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
		linePaint.setColor(myResource.getColor(R.color.notepad_lines));
		
		//获取页面背景色和边缘宽度
		paperColor = myResource.getColor(R.color.notepad_paper);
		margin = myResource.getDimension(R.dimen.notepad_margin);
	}
	
	/**
	 * 覆盖TextView的UI绘制事件
	 * 
	 * @see android.widget.TextView#onDraw(android.graphics.Canvas)
	 */
	protected void onDraw(Canvas canvas) {
		//绘制整个控件的背景色
		canvas.drawColor(paperColor);
		
		canvas.drawLine(0, 0, 0, getMeasuredHeight(), linePaint);
		canvas.drawLine(0, getMeasuredHeight(), getMeasuredWidth(), getMeasuredHeight(), linePaint);
		
		//Draw margin
		canvas.drawLine(margin, 0, margin, getMeasuredHeight(), marginPaint);
		
		//移动文本，让它跨过边缘
		canvas.save();
		canvas.translate(margin, 0);
		
		//使用TextView渲染文本
		super.onDraw(canvas);
		canvas.restore();
	}
	
	/* (non-Javadoc)
	 * @see android.widget.TextView#onKeyDown(int, android.view.KeyEvent)
	 */
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		return super.onKeyDown(keyCode, event);
	}
	
}

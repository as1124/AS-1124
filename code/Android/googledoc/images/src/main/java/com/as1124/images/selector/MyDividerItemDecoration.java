package com.as1124.images.selector;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * 通过{@link android.support.v7.widget.RecyclerView.ItemDecoration}
 * 来为<code>RecyclerView</code>设置分割线
 *
 * @author as-1124(mailto:as1124huang@gmail.com)
 */
public class MyDividerItemDecoration extends RecyclerView.ItemDecoration {

    private static final int[] ATTRS = new int[]{android.R.attr.listDivider};

    private Drawable mDivider;
    private int mOrientation;

    public MyDividerItemDecoration(Context context, int orientation) {
        TypedArray array = context.obtainStyledAttributes(ATTRS);
        mDivider = array.getDrawable(0);
        if (LinearLayoutManager.HORIZONTAL != orientation && LinearLayoutManager.VERTICAL != orientation) {
            throw new IllegalArgumentException("invalid orientation!!");
        }
        this.mOrientation = orientation;
    }

    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        super.onDraw(c, parent, state);
        if (LinearLayoutManager.VERTICAL == mOrientation) {
            drawVerticalDivider(c, parent);
        } else if (LinearLayoutManager.HORIZONTAL == mOrientation) {
            drawHorizontalDiverder(c, parent);
        }
    }

    private void drawVerticalDivider(Canvas canvas, RecyclerView listview) {
        int left = listview.getPaddingLeft();
        int right = listview.getWidth() - listview.getPaddingRight();
        int itemCount = listview.getChildCount();
        for (int i = 0; i < itemCount; i++) {
            View child = listview.getChildAt(i);
            RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child.getLayoutParams();
            int top = child.getBottom() + params.bottomMargin;
            int bottom = top + mDivider.getIntrinsicHeight();
            mDivider.setBounds(left, top, right, bottom);
            mDivider.draw(canvas);
        }
    }

    private void drawHorizontalDiverder(Canvas canvas, RecyclerView listview) {
        int top = listview.getPaddingTop();
        int bottom = listview.getHeight() - listview.getPaddingBottom();
        int itemCount = listview.getChildCount();
        for (int i = 0; i < itemCount; i++) {
            View child = listview.getChildAt(i);
            RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child.getLayoutParams();
            int left = child.getRight() + params.rightMargin;
            int right = left + mDivider.getIntrinsicWidth();
            mDivider.setBounds(left, top, right, bottom);
            mDivider.draw(canvas);
        }
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        // ATTENTION 这个方法的数据处理不是太明白，难道两个坐标对应的不是左上角-右下角？？
        // 而且super的效果和自行处理的效果是一样的？
        if (mOrientation == LinearLayoutManager.VERTICAL) {
            outRect.set(0, 0, 0, mDivider.getIntrinsicHeight());
        } else if (mOrientation == LinearLayoutManager.HORIZONTAL) {
            outRect.set(0, 0, 0, mDivider.getIntrinsicWidth());
        }
    }
}

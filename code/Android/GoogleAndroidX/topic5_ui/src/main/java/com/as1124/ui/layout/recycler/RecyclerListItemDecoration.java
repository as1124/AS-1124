package com.as1124.ui.layout.recycler;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.as1124.ui.R;

/**
 * Item装饰器, 可以用来做成像通讯录右侧字母栏，通讯录姓名字母排序Section这些
 *
 * @author As-1124(mailto:as1124huang@gmail.com)
 */
public class RecyclerListItemDecoration extends RecyclerView.ItemDecoration {

    private Bitmap decorationBitmap;

    private Paint mPaint;

    public RecyclerListItemDecoration() {
        // default constructor
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setDither(true);
    }

    /**
     * 调用一次，在{@link RecyclerView#onDraw(Canvas)} 中触发；此处绘制的内容在holder.itemView的下层
     *
     * @param canvas 是整个RecyclerView对应的Canvas
     * @param parent
     * @param state
     */
    @Override
    public void onDraw(@NonNull Canvas canvas, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        for (int i = 0; i < parent.getChildCount(); i++) {
            View childView = parent.getChildAt(i);
            mPaint.setColor(Color.GREEN);
            // childView在OutRect内部, 这里注意4个顶点的坐标与矩形的对应关系
            canvas.drawRect(childView.getLeft() - 15, childView.getTop() - 10, childView.getRight() + 15, childView.getTop() - 5, mPaint);
        }
    }

    /**
     * 调用一次，在{@link RecyclerView#draw(Canvas)} 中触发，在{@link #onDraw(Canvas, RecyclerView, RecyclerView.State)}后执行.
     * 此处绘制的内容处于holder.itemView图层的上层
     *
     * @param canvas 是整个RecyclerView对应的Canvas
     * @param parent
     * @param state
     */
    @Override
    public void onDrawOver(@NonNull Canvas canvas, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        if (decorationBitmap == null) {
            decorationBitmap = BitmapFactory.decodeResource(parent.getContext().getResources(), R.drawable.drawable_list_pulldown);
        }

        int imgHeightHalf = decorationBitmap.getHeight() / 2;
        for (int i = 0; i < parent.getChildCount(); i++) {
            View childView = parent.getChildAt(i);
            canvas.drawBitmap(decorationBitmap, 0, childView.getTop() + childView.getHeight() / 2 - imgHeightHalf, mPaint);
        }
    }

    /**
     * 控制装饰器填充的相对位置，是否需要让原有的holder.itemView的内容进行偏移; 有点类似于margin
     *
     * @param outRect  用于进行绘制item + decoration的矩形框，如果都为0，则和原有item的content显示区域完全重叠
     * @param itemView 当前正在进行测绘的holder.itemView
     * @param parent
     * @param state
     */
    @Override
    public void getItemOffsets(@NonNull Rect outRect, @NonNull View itemView, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        // 方法是为每个Item准备，所以会多次调用
        outRect.set(0, 10, 0, 0);
    }
}

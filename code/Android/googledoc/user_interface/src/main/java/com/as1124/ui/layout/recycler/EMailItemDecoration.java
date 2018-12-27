package com.as1124.ui.layout.recycler;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.as1124.ui.R;

/**
 * Item装饰，用来绘制分割线
 *
 * @author as-1124(mailto:as1124huang@gmail.com)
 */
public class EMailItemDecoration extends RecyclerView.ItemDecoration {


    public EMailItemDecoration() {
        // default constructor
    }

    /**
     * @param outRect Rect to receive the output
     * @param view    The child view to decorate
     * @param parent  RecyclerView this ItemDecoration is decorating
     * @param state   The current state of RecyclerView
     */
    @Override
    public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        outRect.set(0, 0, 0, 0);
    }

    @Override
    public void onDraw(@NonNull Canvas c, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        Paint paint = new Paint();
        paint.setColor(parent.getResources().getColor(R.color.itemDivider));
        int childCount = parent.getChildCount();

        for (int i = 0; i < childCount; i++) {
            View childView = parent.getChildAt(i);

            // 这样绘制出来的是相对 RecyclerView屏幕固定的；导致的结果：
            // 滚动Item的时候分割线固定不动，Item从从分割线中穿越而过
//                int left = 0;
//                int top = childView.getHeight() * (i + 1);
//                int right = childView.getWidth();
//                int bottom = top + 2;


            // 采用相对Item的方式进行绘制
            int left = childView.getLeft();
            int top = childView.getBottom();
            int right = childView.getWidth();
            int bottom = top + 2;

            c.drawRect(left, top, right, bottom, paint);
        }
    }
}

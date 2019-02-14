package com.as1124.ch3.views.move;

import android.app.Activity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.as1124.ch3.views.R;

/**
 * View的滑动
 * <ol>
 * <li>调用{@link android.view.View#layout(int, int, int, int)}; 不触发onDraw</li>
 * <li>调用 offsetLeftAndRight, offsetTopAndBottom; 不触发onDraw</li>
 * <li>修改LayoutParams; 修改了margin, 触发onDraw </li>
 * <li>动画</li>
 * <li>scrollTo、scrollBy: 要注意的就是数值正负和方向的匹配</li>
 * <li>{@link android.widget.Scroller}方式, 只负责实时记录偏移量并不改变View坐标, 需要自编码处理scroll</li>
 * </ol>
 *
 * @author as-1124(mailto:as1124huang@gmail.com)
 */
public class ViewMovementActivity extends Activity {

    private static int scrollType = 1;

    private As1124View customerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_movement);

        customerView = findViewById(R.id.as1124view);
        Spinner mSpinner = findViewById(R.id.spinner_change_scroll);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item);
        adapter.add("layout");
        adapter.add("offsetLeftAndRight");
        adapter.add("LayoutParams");
        adapter.add("animation");
        adapter.add("property-animation");
        adapter.add("scrollTo");
        adapter.add("scrollBy");
        adapter.add("scroller");
        mSpinner.setAdapter(adapter);
        mSpinner.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                setScrollType(position);
                String typeName = parent.getAdapter().getItem(position).toString();
                if ("scroller".equalsIgnoreCase(typeName)) {
                    customerView.smoothScrollTo(-200);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Toast.makeText(ViewMovementActivity.this, "未指定操作方法，默认使用layout", Toast.LENGTH_SHORT).show();
                setScrollType(-1);
            }
        });
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return super.onTouchEvent(event);
    }

    public static int getScrollType() {
        return scrollType;
    }

    public static void setScrollType(int type) {
        scrollType = type;
    }
}

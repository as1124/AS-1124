package as1124.com.helloworld.ch3;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.Toast;

import as1124.com.helloworld.R;

public class TitleLayoutView extends LinearLayout {

    public TitleLayoutView(Context context, AttributeSet attrs) {
        super(context, attrs);

        LayoutInflater.from(context).inflate(R.layout.title, this);
        findViewById(R.id.titlebar_left).setOnClickListener(v -> {
            ((Activity) getContext()).finish();
        });
        findViewById(R.id.titlebar_right).setOnClickListener(v -> {
            Toast.makeText(getContext(), "显示菜单项", Toast.LENGTH_SHORT).show();
        });
    }
}

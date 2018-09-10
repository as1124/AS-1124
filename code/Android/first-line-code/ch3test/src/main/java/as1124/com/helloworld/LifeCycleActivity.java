package as1124.com.helloworld;

import android.app.Activity;
import android.os.Bundle;

/**
 * 显示android生命周期图
 *
 * @author as-1124(mailto:as1124huang@gmail.com)
 */
public class LifeCycleActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_life_cycle);
    }

    @Override
    public void onBackPressed() {
//        super.onBackPressed();

        // 按下系统返回按钮时触发
        android.util.Log.i(getClass().getSimpleName(), "System back-button pressed!!");
        this.finish();
    }
}

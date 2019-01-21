package com.as1124.activities.loaders;

import android.app.Activity;
import android.os.Bundle;

import com.as1124.activities.R;

/**
 * 利用Loader可以实现数据的同步监听, 不用手动去去读新数据判断变更项
 *
 * @author as-1124(mailto:as1124huang@gmail.com)
 */
public class LoaderMainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loader_main);
    }
}

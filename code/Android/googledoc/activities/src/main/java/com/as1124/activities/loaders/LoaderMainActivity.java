package com.as1124.activities.loaders;

import android.app.Activity;
import android.app.LoaderManager;
import android.os.Bundle;

import com.as1124.activities.R;

/**
 * 利用Loader可以实现数据的同步监听, 不用手动去去读新数据判断变更项<br/>
 * <ul>
 * <li>用于Activity和Fragment</li>
 * <li>异步加载数据</li>
 * <li>监听数据源内容变化并传递新结果</li>
 * </ul>
 *
 * @author as-1124(mailto:as1124huang@gmail.com)
 */
public class LoaderMainActivity extends Activity {

    private LoaderManager loaderManager;
    private int loaderID = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loader_main);

        loaderManager = getLoaderManager();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        loaderManager.destroyLoader(loaderID);
    }
}

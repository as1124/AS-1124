package com.as1124.architecture.binding;

import android.app.Activity;
import android.databinding.DataBindingUtil;
import android.os.Bundle;

import com.as1124.architecture.R;
import com.as1124.architecture.databinding.ActivityDataBindingBinding;
import com.as1124.architecture.model.User;

import java.util.HashMap;
import java.util.Map;

/**
 * Android LifeCycle 中ViewModel-DataBinding的使用
 * <ul>
 * <li>属性绑定：基本语法<code>@{variable.field}</code></li>
 * <li>控件事件绑定：[Method References]方式
 * <p>
 * 1. 基本语法 @{variable::method-name}<br/>
 * 2. 要求：Parameters of the method must match the parameter of the event listener
 * </p></li>
 * <li>控件事件绑定：[Listener Binding]方式
 * <p>
 * 1. 基本语法 @{(event-parameter-list) -> variable.method-name}; 这里其实就是直接执行了Lambda表达式<br/>
 * 2. 要求: Return value must match the expected return value of the listener
 * </p></li>
 * </ul>
 *
 * @author as-1124(mailto:as1124huang@gmail.com)
 */
public class DataBindingActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_data_binding);

        // 返回的Binding实例相当于ContentView
        // ActivityDataBindingBinding 是根据layout文件名自动生成的
        ActivityDataBindingBinding dataBinding = DataBindingUtil.setContentView(this, R.layout.activity_data_binding);
        User user = new User("Hey, 沙雕", "去你妈的！");
        user.addr.set("地球就是老子的住址");
        dataBinding.setUser(user);

        Map<String, String> testMap = new HashMap<>();
        testMap.put("sd1", "张三");
        testMap.put("sd2", "李四麻子");
        dataBinding.setMyMap(testMap);
        dataBinding.setMyKey("sd2");

        findViewById(R.id.but_collections_binding).setOnClickListener(v -> {
            User user2 = dataBinding.getUser();
            user2.setFirstName("You TMD!");
            dataBinding.setUser(user2);

            dataBinding.setMyKey("sd1");

            dataBinding.getUser().addr.set("哈哈哈....老子搬家了!");
        });
    }
}

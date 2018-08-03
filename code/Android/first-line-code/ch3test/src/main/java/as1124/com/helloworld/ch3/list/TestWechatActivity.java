package as1124.com.helloworld.ch3.list;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import as1124.com.helloworld.R;

/**
 * 模仿微信聊天界面
 *
 * @author as-1124 (as1124huang@gmail.com)
 */
public class TestWechatActivity extends Activity {

    private List<WechatMessage> msgs = new ArrayList<>();

    private TextView textView;

    private RecyclerView.Adapter dataAdapter;

    private RecyclerView wechatList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_ch3_wechat);

        init();
        wechatList = findViewById(R.id.ch3_test_wechatlist);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        wechatList.setLayoutManager(layoutManager);
        dataAdapter = new WechatListAdapter(msgs);
        wechatList.setAdapter(dataAdapter);

        // FIXME 输入框弹出时，ListView下面的内容被遮挡了没有显示
        textView = findViewById(R.id.wechat_send_text);
        findViewById(R.id.wechat_send_but).setOnClickListener(v -> {
            String sendContent = textView.getText().toString();
            if (!TextUtils.isEmpty(sendContent)) {
                textView.setText("");
                WechatMessage newMsg = new WechatMessage(sendContent, WechatMessage.TYPE_SENT);
                msgs.add(newMsg);
                // 通知Adapter有数据变化
                dataAdapter.notifyItemInserted(msgs.size() - 1);
                // 将ListView滚动到最后一行
                wechatList.scrollToPosition(msgs.size() - 1);
            }
        });
    }

    private void init() {
        msgs.add(new WechatMessage("去你妈的，哈哈哈", WechatMessage.TYPE_RECEIVED));
        msgs.add(new WechatMessage("你个禽兽，哈哈哈", WechatMessage.TYPE_SENT));
        msgs.add(new WechatMessage("傻狗", WechatMessage.TYPE_SENT));
        msgs.add(new WechatMessage("我也无言以对", WechatMessage.TYPE_RECEIVED));
    }
}

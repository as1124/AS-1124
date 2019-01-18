package coms.as1124.webbased;

import android.app.AlertDialog;
import android.content.Context;
import android.util.DisplayMetrics;
import android.view.ViewGroup;
import android.webkit.JavascriptInterface;
import android.widget.CalendarView;
import android.widget.Toast;

/**
 * 网页JavaScript调用原生端方法接口
 *
 * @author as-1124(mailto:as1124huang@gmail.com)
 */
public class WebviewScriptBridge {

    private Context mContext;

    public WebviewScriptBridge(Context context) {
        mContext = context;
    }

    @JavascriptInterface
    public String executeAndroid(String args) {
        Toast.makeText(mContext, "哈哈哈, 我是Android", Toast.LENGTH_SHORT).show();
        showDialog();
        return "client return success!";
    }

    public void showYourName(String args) {
        Toast.makeText(mContext, "你好, 沙雕：【" + args + "】", Toast.LENGTH_LONG).show();
    }


    private void showDialog() {
        CalendarView customerView = new CalendarView(mContext);
        DisplayMetrics displayMetrics = mContext.getResources().getDisplayMetrics();
        int height = (int) (400 * displayMetrics.density);
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, height);
        customerView.setLayoutParams(params);
        customerView.setDate(System.currentTimeMillis());
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setTitle("Web-based Content").setMessage("网页触发原生弹框").setCancelable(true)
                .setCustomTitle(customerView);
        builder.show();
    }
}

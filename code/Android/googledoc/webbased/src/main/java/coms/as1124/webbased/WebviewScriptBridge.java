package coms.as1124.webbased;

import android.app.AlertDialog;
import android.content.Context;
import android.util.DisplayMetrics;
import android.view.ViewGroup;
import android.webkit.JavascriptInterface;
import android.widget.CalendarView;
import android.widget.Toast;

/**
 * 网页JavaScript和原生方法交互接口
 *
 * @author as-1124(mailto:as1124huang@gmail.com)
 */
public class WebviewScriptBridge {

    private Context mContext;

    public WebviewScriptBridge(Context context) {
        mContext = context;
    }

    @JavascriptInterface
    public void executeAndroid(String args) {
        Toast.makeText(mContext, "哈哈哈, 我是Android", Toast.LENGTH_SHORT).show();
        showDialog();
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

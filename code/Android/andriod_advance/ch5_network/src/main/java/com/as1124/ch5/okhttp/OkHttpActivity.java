package com.as1124.ch5.okhttp;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.as1124.ch5.MainActivity;
import com.as1124.ch5.R;
import com.as1124.ch5.WechatConstants;
import com.squareup.okhttp.Call;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;
import com.squareup.okhttp.ResponseBody;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.text.MessageFormat;

public class OkHttpActivity extends Activity {


    private ImageView imgView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ok_http);

        imgView = findViewById(R.id.img_ok_http);

        findViewById(R.id.but_okhttp_get).setOnClickListener(v -> getRequest());
        findViewById(R.id.but_okhttp_post).setOnClickListener(v -> postRequest());
        findViewById(R.id.but_okhttp_download).setOnClickListener(v -> downloadImg());

    }

    private void getRequest() {
        String url = MessageFormat.format(WechatConstants.API_GET_TOKEN, WechatConstants.DEBUG_APP_KEY, WechatConstants.DEBUG_APP_SECRET);
        Request getRequest = new Request.Builder().url(url).get().build();

        OkHttpClient httpClient = new OkHttpClient();
        Call call = httpClient.newCall(getRequest);
//            try {
//                Response response = call.execute();
//                String msg = response.message();
//                Log.i(MainActivity.TAG, "OKHttp-GET: " + msg);
//            } catch (IOException e) {
//                e.printStackTrace();
//            }

        call.enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
                Toast.makeText(OkHttpActivity.this, "OKHttp-Get Failed! 网络异常", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResponse(Response response) throws IOException {
                ResponseBody responseBody = response.body();
                String result = responseBody.string();
                responseBody.close();
                try {
                    WechatConstants.WECHAT_TOKEN = new JSONObject(result).getString("access_token");
                    Log.i(MainActivity.TAG, "OKHttp-GET: " + WechatConstants.WECHAT_TOKEN);
                } catch (JSONException e) {
                }

            }
        });
    }

    private void postRequest() {
        String url = MessageFormat.format(WechatConstants.API_MARK_USER, WechatConstants.WECHAT_TOKEN);
        JSONObject param = new JSONObject();
        try {
            param.put("openid", "oB1fEuFzGh6e2EMg5Ac5c9xugaRQ");
            param.put("remark", "For fun");
        } catch (JSONException e) {
        }

        RequestBody requestForm = RequestBody.create(MediaType.parse("application/json"), param.toString());
        Request getRequest = new Request.Builder().url(url).post(requestForm).build();
        OkHttpClient httpClient = new OkHttpClient();
        Call call = httpClient.newCall(getRequest);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
                Toast.makeText(OkHttpActivity.this, "OKHttp-Get Failed! 网络异常", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResponse(Response response) throws IOException {
                ResponseBody responseBody = response.body();
                String result = responseBody.string();
                responseBody.close();
                Log.i(MainActivity.TAG, "OKHttp-GET: " + result);
            }
        });
    }

    private void downloadImg() {
        String url = "http://img.article.pchome.net/00/28/07/66/29232453E2LZ_m.jpg";
        Request getRequest = new Request.Builder().url(url).get().build();
        OkHttpClient httpClient = new OkHttpClient();
        Call call = httpClient.newCall(getRequest);

        call.enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
                Toast.makeText(OkHttpActivity.this, "OKHttp-Get Failed! 网络异常", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResponse(Response response) throws IOException {
                InputStream imgStream = response.body().byteStream();
                try {
                    Bitmap bitmap = BitmapFactory.decodeStream(imgStream);
                    if (bitmap != null) {
                        runOnUiThread(() -> {
                            imgView.setVisibility(View.VISIBLE);
                            imgView.setImageBitmap(bitmap);
                        });
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    if (imgStream != null) {
                        imgStream.close();
                    }
                }
            }
        });
    }
}

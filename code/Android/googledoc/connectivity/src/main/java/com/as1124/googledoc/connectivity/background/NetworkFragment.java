package com.as1124.googledoc.connectivity.background;

import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.as1124.googledoc.connectivity.R;

public class NetworkFragment extends Fragment implements IDownloadCallback {

    public static final String FRAGMENT_TAG = "background_network";

    private static String TAG = "[NetworkFragment]";

    private ImageView mImage;
    private DownloadTask downloadTask;

    public static NetworkFragment getInstance(FragmentManager manager) {
        NetworkFragment one;
        if (manager.findFragmentByTag(FRAGMENT_TAG) != null) {
            one = (NetworkFragment) manager.findFragmentByTag(FRAGMENT_TAG);
        } else {
            one = new NetworkFragment();
            manager.beginTransaction().add(R.id.frame_layout_https, one, FRAGMENT_TAG).commit();
        }
        return one;
    }

    public NetworkFragment() {
        // Required empty public constructor
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        android.util.Log.i(TAG, "Fragment OnAttach!!!");
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        android.util.Log.i(TAG, "Fragment onCreated!!!");

        // Retain this Fragment across configuration changes in the host Activity.
        // 这里阻止了Fragment#onDestory(), 但是和Activity相关的onDetach()、onAttach还是会调用
        this.setRetainInstance(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View topView = inflater.inflate(R.layout.fragment_blank, container, false);
        topView.findViewById(R.id.but_https_image).setOnClickListener(v -> startDownload(""));
        mImage = topView.findViewById(R.id.img_https_background);
        android.util.Log.i(TAG, "Fragment OnCreateView!!!");

        topView.findViewById(R.id.but_alpha_dialog).setOnClickListener(v -> {
        });

        return topView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        android.util.Log.i(TAG, "Fragment onActivityCreated!!!");
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        android.util.Log.i(TAG, "Fragment Configuration Changed!!");
    }

    @Override
    public void onDestroy() {
        // 放在 onDestroy() 而不是放在 onDetach() 方法中
        cancelDownload();

        super.onDestroy();
        android.util.Log.i(TAG, "Fragment onDestroy ");
    }

    @Override
    public void onDetach() {
        super.onDetach();
        android.util.Log.i(TAG, "Fragment OnDetach ");
    }

    @Override
    public void startDownload(String downloadURL) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        ProgressBar bar = new ProgressBar(getActivity());
        ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(300, 300);
        bar.setLayoutParams(layoutParams);
        bar.setIndeterminate(true);
        bar.setVisibility(View.VISIBLE);
        builder.setView(bar).create().show();

        downloadTask = new DownloadTask(this.getActivity(), this);
        downloadTask.execute("https://wx2.sinaimg.cn/mw1024/d8203382ly1fty09ydhlmj21481kwk05.jpg");
    }

    @Override
    public void onFinished(Object result) {
        if (result != null && result instanceof Drawable) {
            mImage.setImageDrawable((Drawable) result);
        }
        downloadTask = null;
    }

    @Override
    public void cancelDownload() {
        if (downloadTask != null && !downloadTask.isCancelled()) {
            downloadTask.cancel(true);
        }
        downloadTask = null;
    }
}

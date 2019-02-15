package com.as1124.ch3.views.scroller;

import android.app.Activity;
import android.hardware.Camera;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.TextView;
import android.widget.Toast;

import com.as1124.ch3.views.R;

import java.io.IOException;

/**
 * 了解Android种Scroller
 *
 * @author as-1124(mailto:as1124huang@gmail.com)
 */
public class ScrollerActivity extends Activity {

    private Camera mCamera;

    private SurfaceView mSurface;
    private SurfaceHolder mHolder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scroller);

        mSurface = findViewById(R.id.surfaceView);
        mHolder = mSurface.getHolder();
        mHolder.addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder holder) {
                mHolder = holder;
            }

            @Override
            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

            }

            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {

            }
        });
        mHolder.setKeepScreenOn(true);


        TextView textView = findViewById(R.id.text_scroller_impl);
//        textView.setText("Scroller的使用是为了替代View自身scrollTo、scrollBy瞬间完成滑动诞生的, "
//                + "它的作用就是实现平滑的滑动过度，类比translate动画;将位移distance分割成小段放到duration中，通过累积实现看似平滑的过程. "
//                + "注意Scroller自身并不处理滑动, 而是通过记录view滑动时相关的参数信息供"
//                + "开发者调用(getCurrX、getCurrY)然后仍然执行View自身的scroll以及draw实现View的平移");

        findViewById(R.id.but_fingerprint).setOnClickListener(v -> useFingerprint());
        findViewById(R.id.but_face_unlock).setOnClickListener(v -> faceUnlock());

    }

    private void useFingerprint() {
        BiologyVerifyCenter bvc = new BiologyVerifyCenter(this);
        if (bvc.supportFingerprint()) {
            if (bvc.hasEnrolledFingerprint()) {
                bvc.verifyFingerprint();
            } else {
                Toast.makeText(this, "设备没有录入指纹信息", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "设备不支持指纹解锁", Toast.LENGTH_LONG).show();
        }
    }

    private void faceUnlock() {
        int size = Camera.getNumberOfCameras();
        for (int i = 0; i < size; i++) {
            Camera.CameraInfo info = new Camera.CameraInfo();
            Camera.getCameraInfo(i, info);
            if (info.facing == Camera.CameraInfo.CAMERA_FACING_FRONT) {
                mCamera = Camera.open(i);
                break;
            }
        }
        mCamera.setFaceDetectionListener(new Camera.FaceDetectionListener() {
            @Override
            public void onFaceDetection(Camera.Face[] faces, Camera camera) {
                if (faces.length > 0) {
                    Log.i("FACE_UNLOCK", "sdfjk:  " + faces[0].score);
                }
            }
        });

        if (mCamera.getParameters().getMaxNumDetectedFaces() > 0) {
            try {
                mCamera.setDisplayOrientation(90);
                mCamera.startPreview();
                mCamera.setPreviewDisplay(mHolder);
                mCamera.startFaceDetection();
            } catch (IOException e) {
                e.printStackTrace();
            }

        } else {
            Toast.makeText(this, "设备不支持人脸识别", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mCamera != null) {
            mCamera.stopPreview();
            mCamera.stopFaceDetection();
            mCamera.release();
        }
    }
}

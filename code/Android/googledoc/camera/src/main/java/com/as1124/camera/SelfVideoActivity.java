package com.as1124.camera;

import android.app.Activity;
import android.hardware.Camera;
import android.media.CamcorderProfile;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.Button;

import java.io.File;
import java.io.IOException;

/**
 * 应用内捕获视频，不打开第三方应用
 *
 * @author as-1124(mailto:as1124huang@gmail.com)
 */
public class SelfVideoActivity extends Activity implements SurfaceHolder.Callback {

    private SurfaceHolder mHolder;
    private Camera mCamera;
    private int cameraID;
    private MediaRecorder mediaRecorder;
    private File outputFile;

    private boolean recording = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_self_video);

        SurfaceView preview = findViewById(R.id.surface_video);
        mHolder = preview.getHolder();
        mHolder.addCallback(this);

        outputFile = new File(Environment.getExternalStorageDirectory(), "huangjw/abcd.mp4");
        findViewById(R.id.but_capture_video).setOnClickListener(v -> {
            if (recording) {
                releaseMediaRecorder();
                releaseCamera();
                ((Button) v).setText("Start New Video");
                recording = false;
            } else {
                prepareCamera();
                prepareMediaRecorder();
                mediaRecorder.start();
                ((Button) v).setText("Stop Capturing");
                recording = true;
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        prepareCamera();
//        prepareMediaRecorder();
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (mCamera != null) {
            mCamera.startPreview();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mediaRecorder != null) {
            mediaRecorder.stop();
        }
        if (mCamera != null) {
            mCamera.stopPreview();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();

        releaseMediaRecorder();
        releaseCamera();
    }

    private void prepareCamera() {
        if (mCamera == null) {
            for (int i = 0; i < Camera.getNumberOfCameras(); i++) {
                Camera.CameraInfo info = new Camera.CameraInfo();
                Camera.getCameraInfo(i, info);
                if (Camera.CameraInfo.CAMERA_FACING_BACK == info.facing) {
                    cameraID = i;
                    mCamera = Camera.open(i);
                    break;
                }
            }
            try {
                mCamera.setPreviewDisplay(mHolder);
//                mCamera.startPreview();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void prepareMediaRecorder() {
        if (mediaRecorder == null) {
            mediaRecorder = new MediaRecorder();
            mCamera.unlock();
            mediaRecorder.setCamera(mCamera);
            mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
            mediaRecorder.setVideoSource(MediaRecorder.VideoSource.CAMERA);
//            mediaRecorder.setProfile(CamcorderProfile.get(cameraID, CamcorderProfile.QUALITY_720P));
            mediaRecorder.setOutputFile(outputFile.getAbsolutePath());
            mediaRecorder.setPreviewDisplay(mHolder.getSurface());
            try {
                mediaRecorder.prepare();
            } catch (IOException e) {
                Log.e("Self-Video", "IOException preparing MediaRecorder: " + e.getMessage(), e);
                mediaRecorder.stop();
                mediaRecorder.release();
            }
        }
    }

    private void releaseMediaRecorder() {
        if (mediaRecorder != null) {
            mediaRecorder.stop();
            mediaRecorder.reset();
            mediaRecorder.release();
            mediaRecorder = null;
        }
    }

    private void releaseCamera() {
        if (mCamera != null) {
            mCamera.lock();
            mCamera.stopPreview();
            mCamera.release();
            mCamera = null;
        }
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        try {
            mCamera.setPreviewDisplay(mHolder);
            mCamera.setDisplayOrientation(90);
            mCamera.startPreview();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        releaseMediaRecorder();
        releaseCamera();
    }
}

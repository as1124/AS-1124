package com.as1124.camera;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.hardware.Camera;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraCharacteristics;
import android.hardware.camera2.CameraDevice;
import android.hardware.camera2.CameraManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.io.IOException;
import java.util.List;

/**
 * 在应用内使用相机预览，不打开第三方相机。
 * setPreviewDisplay()必须在onSurfaceCreated之后调用才能生效，否则黑屏的，看不到preview图像。
 * 摄像头默认是横向采样的，所以要注意图像的方向
 *
 * @author as-1124(mailto:as1124huang@gmail.com)
 */
public class SelfCameraActivity extends Activity implements SurfaceHolder.Callback {

    private SurfaceView mSurfaceView;
    private SurfaceHolder mHolder;

    private CameraManager cameraManager;
    private Camera mCamera;
    private CameraDevice mDevice;
    private List<Camera.Size> localPreviewSize;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_self_camera);

        mSurfaceView = findViewById(R.id.surface_picture);
        mHolder = mSurfaceView.getHolder();
        // Install a SurfaceHolder.Callback so we got notified when underlying surface is created and destroyed
        mHolder.addCallback(this);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                    requestPermissions(new String[]{Manifest.permission.CAMERA}, 111);
                    return;
                }
            }
            openCameraAbove21();
        } else {
            openCameraBelow21();
        }
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (Build.VERSION.SDK_INT >= 21) {

        } else {
//        stopPreviewAndFreeCamera();
            try {
                // Important: Call startPreview() to start updating the preview surface.
                // Preview must be started before you can take a picture
                mCamera.startPreview();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mCamera != null) {
            mCamera.stopPreview();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mCamera != null) {
            stopPreviewAndFreeCamera();
        }
    }

    @TargetApi(21)
    private void openCameraAbove21() {
        cameraManager = (CameraManager) getSystemService(Context.CAMERA_SERVICE);
        try {
            String[] cameraIDs = cameraManager.getCameraIdList();
            String ss = "";
            String id2open = "";
            for (String id : cameraIDs) {
                ss = id + "," + ss;
                CameraCharacteristics characteristics = cameraManager.getCameraCharacteristics(id);
                Integer facing = characteristics.get(CameraCharacteristics.LENS_FACING);
                if (CameraCharacteristics.LENS_FACING_BACK == facing) {
                    // 后置摄像头
                    id2open = id;
                }
            }

            cameraManager.openCamera(id2open, new CameraDevice.StateCallback() {
                @Override
                public void onOpened(@NonNull CameraDevice camera) {
                    Log.i("Self-Camera", "CameraDevice-opened");
                    mDevice = camera;
                }

                @Override
                public void onDisconnected(@NonNull CameraDevice camera) {
                    Log.i("Self-Camera", "CameraDevice-disconnected");
                }

                @Override
                public void onError(@NonNull CameraDevice camera, int error) {
                    Log.i("Self-Camera", "CameraDevice-onError");
                }
            }, null);
            Log.i("Camera-ID>=21", "一共有" + cameraIDs.length + "个摄像头, 标识为==" + ss);
        } catch (CameraAccessException e) {
            e.printStackTrace();
        } catch (SecurityException e) {
            Log.e("Self-Camera", "请检查权限");
            Log.e("Self-Camera", e.getMessage(), e);
        }
    }

    private void openCameraBelow21() {
        Log.i("Camera-ID<21", "一共有" + Camera.getNumberOfCameras() + "个摄像头");
        Camera.CameraInfo info = new Camera.CameraInfo();
        for (int i = 0; i < Camera.getNumberOfCameras(); i++) {
            Camera.getCameraInfo(i, info);
            if (Camera.CameraInfo.CAMERA_FACING_BACK == info.facing) {
                // 后置摄像头
                mCamera = Camera.open(i);
                break;
            }
        }

        // 照相机默认打开是横向采样的
        int rotation = getWindowManager().getDefaultDisplay().getRotation();
        int degrees = 0;
        switch (rotation) {
            case Surface.ROTATION_0:
                degrees = 0;
                break;
            case Surface.ROTATION_90:
                degrees = 90;
                break;
            case Surface.ROTATION_180:
                degrees = 180;
                break;
            case Surface.ROTATION_270:
                degrees = 270;
                break;
        }
        if (info.facing == Camera.CameraInfo.CAMERA_FACING_FRONT) {
            degrees = (info.orientation + degrees) % 360;
            degrees = (360 - degrees) % 360;
        } else {
            degrees = (info.orientation - degrees + 360) % 360;
        }
        mCamera.setDisplayOrientation(degrees);
    }

    private void stopPreviewAndFreeCamera() {
        if (mCamera != null) {
            // Call stopPreview() to stop updating the preview surface
            mCamera.stopPreview();

            // Important: Call release() to release the camera for use by other applications.
            // Applications should release the camera immediately during onPause() amd re-open() it during onResume()
            mCamera.release();
            mCamera = null;
        }
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        Log.i("My_Camera", "Surface created");
        try {
            mCamera.setPreviewDisplay(mHolder);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        // Now that the size is known, set up the camera parameter and begin the preview
        Camera.Parameters parameters = mCamera.getParameters();

        Log.i("My_Camera", "SurfaceChanged");
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        // Surface will be destroyed when we return, so stop the preview
        if (mCamera != null) {
            mCamera.stopPreview();
        }
    }
}

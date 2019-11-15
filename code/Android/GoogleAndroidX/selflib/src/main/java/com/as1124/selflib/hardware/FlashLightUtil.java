package com.as1124.selflib.hardware;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraCharacteristics;
import android.hardware.camera2.CameraDevice;
import android.hardware.camera2.CameraManager;
import android.hardware.camera2.CaptureRequest;
import android.os.Build;
import android.widget.Toast;

/**
 * 闪光灯工具类
 *
 * @author As-1124(mailto:as1124huang@gmail.com)
 */
public class FlashLightUtil {

    public static void enableFlashTorch(Context context, boolean on) {
        if (context.checkCallingOrSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(context, "当前应用无权使用相机, 请前往授权", Toast.LENGTH_SHORT).show();
            return;
        }
        if (context.getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH)) {
            if (Build.VERSION.SDK_INT >= 23) {
                CameraManager cameraManager = (CameraManager) context.getSystemService(Context.CAMERA_SERVICE);
                try {
                    String[] cids = cameraManager.getCameraIdList();
                    for (String cameraID : cids) {
                        CameraCharacteristics characteristics = cameraManager.getCameraCharacteristics(cameraID);
                        boolean hasFlash = characteristics.get(CameraCharacteristics.FLASH_INFO_AVAILABLE);
                        int lensFacing = characteristics.get(CameraCharacteristics.LENS_FACING);
                        if (hasFlash && CameraCharacteristics.LENS_FACING_BACK == lensFacing) {
                            cameraManager.setTorchMode(cameraID, on);
                            break;
                        }
                    }
                } catch (CameraAccessException e) {
                    e.printStackTrace();
                }
            } else if (Build.VERSION.SDK_INT >= 21) {
                CameraManager cameraManager = (CameraManager) context.getSystemService(Context.CAMERA_SERVICE);
                try {
                    cameraManager.openCamera("0", new CameraDevice.StateCallback() {
                        @Override
                        public void onOpened(CameraDevice camera) {
                            try {
                                CaptureRequest.Builder builder = camera.createCaptureRequest(CameraDevice.TEMPLATE_MANUAL);
                                builder.set(CaptureRequest.FLASH_MODE, CaptureRequest.FLASH_MODE_TORCH);
                                CaptureRequest captureRequest = builder.build();
//                                SessionConfiguration sessionConfig =
//                                camera.createCaptureSession();
                            } catch (CameraAccessException e) {
                                e.printStackTrace();
                            }
                        }

                        @Override
                        public void onDisconnected(CameraDevice camera) {
                        }

                        @Override
                        public void onError(CameraDevice camera, int error) {
                        }
                    }, null);
                } catch (CameraAccessException e) {
                    e.printStackTrace();
                }
            } else {
                Camera camera = Camera.open();
                camera.startPreview();
                if (camera != null) {
                    Camera.Parameters parameters = camera.getParameters();
                    if (on) {
//                        parameters.setFlashMode(Camera.Parameters.FLASH_MODE_ON);
                        parameters.setFlashMode(Camera.Parameters.FLASH_MODE_TORCH);
                    } else {
                        parameters.setFlashMode(Camera.Parameters.FLASH_MODE_OFF);
                    }
                    camera.setParameters(parameters);
                }
            }
        } else {
            Toast.makeText(context, "设备无闪光灯功能", Toast.LENGTH_SHORT).show();
        }
    }

}

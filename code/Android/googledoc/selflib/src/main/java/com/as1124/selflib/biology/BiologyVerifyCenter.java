package com.as1124.selflib.biology;

import android.content.Context;
import android.hardware.fingerprint.FingerprintManager;
import android.os.Build;
import android.os.CancellationSignal;
import android.util.Log;

/**
 * Android设备统一生物识别认证中心. Android Pie中用BiometricPrompt 取代了{@link}
 *
 * @author as-1124(mailto:as1124huang@gmail.com)
 */
public class BiologyVerifyCenter {

    private FingerprintManager mFingerprintManager;
    private CancellationSignal mFingerprintCancel = null;

    public BiologyVerifyCenter(Context context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            mFingerprintManager = (FingerprintManager) context.getSystemService(Context.FINGERPRINT_SERVICE);
        }
    }

    /**
     * 手机硬件层是否支持指纹
     *
     * @return true means device supports fingerprint-verify
     */
    public boolean supportFingerprint() {
        if (Build.VERSION.SDK_INT >= 23 && mFingerprintManager != null) {
            return mFingerprintManager.isHardwareDetected();
        } else {
            return false;
        }
    }

    /**
     * 判断设备是否已经录有指纹
     *
     * @return true means at least one
     */
    public boolean hasEnrolledFingerprint() {
        if (Build.VERSION.SDK_INT >= 23 && mFingerprintManager != null) {
            return mFingerprintManager.hasEnrolledFingerprints();
        } else {
            return false;
        }
    }

    public boolean supportFaceDetector() {

        return false;
    }

    public void verifyFingerprint() {
        if (Build.VERSION.SDK_INT >= 23 && mFingerprintManager != null) {
            mFingerprintCancel = new CancellationSignal();
            mFingerprintManager.authenticate(null, mFingerprintCancel, 0, new FingerprintManager.AuthenticationCallback() {
                @Override
                public void onAuthenticationError(int errorCode, CharSequence errString) {
                    // 指纹验证出错, 例如: 失败次数尝试过多
                    Log.i("HUANG", "Fingerprint error: " + errorCode);
                }

                @Override
                public void onAuthenticationSucceeded(FingerprintManager.AuthenticationResult result) {
                    Log.i("HUANG", "Fingerprint is good: " + result.toString());
                }

                @Override
                public void onAuthenticationFailed() {
                    // 指纹验证失败
                    Log.i("HUANG", "Fingerprint failed: ");
                }
            }, null);
        }
    }

    /**
     * 取消指纹验证
     */
    public void cancelFingerprint() {
        if (mFingerprintCancel != null && !mFingerprintCancel.isCanceled()) {
            mFingerprintCancel.cancel();
            mFingerprintCancel = null;
        }
    }

    public void verifyFace() {

    }

}

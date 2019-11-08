package com.as1124.selflib.biology;

import android.content.Context;
import android.content.pm.PackageManager;
import android.hardware.biometrics.BiometricPrompt;
import android.hardware.fingerprint.FingerprintManager;
import android.os.Build;
import android.os.CancellationSignal;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * Android设备统一生物识别认证中心. Android Pie中用BiometricPrompt 取代了{@link}
 *
 * @author As-1124(mailto:as1124huang@gmail.com)
 */
public class BiologyVerifyCenter {

    private BiologyVerifyCenter() {
        // default constructor
    }

    /**
     * 手机硬件层是否支持指纹解锁
     *
     * @param context
     * @return true means device supports fingerprint-verify
     */
    public static boolean supportFingerprint(Context context) {
        if (Build.VERSION.SDK_INT >= 23) {
            Object fingerprintManager = context.getSystemService(Context.FINGERPRINT_SERVICE);
            if (fingerprintManager != null) {
                return ((FingerprintManager) fingerprintManager).isHardwareDetected();
            }
        }
        return false;
    }

    /**
     * 判断设备是否已经录有指纹
     *
     * @param context
     * @return true means at least one
     */
    public static boolean hasEnrolledFingerprint(Context context) {
        if (Build.VERSION.SDK_INT >= 23 && context.getSystemService(Context.FINGERPRINT_SERVICE) != null) {
            return ((FingerprintManager) context.getSystemService(Context.FINGERPRINT_SERVICE)).hasEnrolledFingerprints();
        } else {
            return false;
        }
    }

    /**
     * 判断设备是否支持脸部识别认证功能
     *
     * @param context
     * @return
     */
    public static boolean supporyFace(Context context) {
        PackageManager packageManager = context.getPackageManager();
        return packageManager.hasSystemFeature(PackageManager.FEATURE_FACE) || packageManager.hasSystemFeature(PackageManager.FEATURE_IRIS);
    }

    /**
     * 调用系统统一生物识别认证接口
     *
     * @param context
     * @param resultCallback
     * @return <code>CancellationSignal</code> - 不为空，接口调用成功；用户取消身份验证
     */
    public static CancellationSignal startBiometricVerify(Context context, VerifyCallback resultCallback) {
        CancellationSignal mCancelSignal = new CancellationSignal();
        CharSequence appName = context.getPackageManager().getApplicationLabel(context.getApplicationInfo());
        if (Build.VERSION.SDK_INT >= 28) {
            Executor executor = Executors.newSingleThreadExecutor();
            BiometricPrompt.AuthenticationCallback authenticationCallback = new BiometricPrompt.AuthenticationCallback() {
                @Override
                public void onAuthenticationError(int errorCode, CharSequence errString) {
                    resultCallback.onError(errorCode, errString.toString());
                }

                @Override
                public void onAuthenticationSucceeded(BiometricPrompt.AuthenticationResult result) {
                    resultCallback.onSuccessed();
                }

                @Override
                public void onAuthenticationFailed() {
                    resultCallback.onFailed();
                }
            };
            BiometricPrompt.Builder builder = new BiometricPrompt.Builder(context).setTitle("身份识别认证")
                    .setSubtitle("【" + appName + "】")
                    .setDescription("请求认证您的身份信息");
            if (Build.VERSION.SDK_INT >= 29) {
                builder = builder.setConfirmationRequired(true).setDeviceCredentialAllowed(true);
            }
            BiometricPrompt prompt = builder.build();
            prompt.authenticate(mCancelSignal, executor, authenticationCallback);
            return mCancelSignal;
        } else if (Build.VERSION.SDK_INT >= 23 && context.getSystemService(Context.FINGERPRINT_SERVICE) != null) {
            FingerprintManager fingerprintManager = (FingerprintManager) context.getSystemService(Context.FINGERPRINT_SERVICE);
            fingerprintManager.authenticate(null, mCancelSignal, 0, new FingerprintManager.AuthenticationCallback() {
                @Override
                public void onAuthenticationError(int errorCode, CharSequence errString) {
                    resultCallback.onError(errorCode, errString.toString());
                }

                @Override
                public void onAuthenticationSucceeded(FingerprintManager.AuthenticationResult result) {
                    resultCallback.onSuccessed();
                }

                @Override
                public void onAuthenticationFailed() {
                    resultCallback.onFailed();
                }
            }, null);
            return mCancelSignal;
        } else {
            return null;
        }
    }


    /**
     * 生物身份验证回调接口
     *
     * @author As-1124(mailto:as1124huang@gmail.com)
     */
    public static interface VerifyCallback {

        /**
         * 身份认证通过
         */
        public void onSuccessed();

        /**
         * 身份认证失败
         */
        public void onFailed();

        /**
         * 接口调用或验证异常
         *
         * @param errCode
         * @param errMsg
         */
        public void onError(int errCode, String errMsg);

    }

}

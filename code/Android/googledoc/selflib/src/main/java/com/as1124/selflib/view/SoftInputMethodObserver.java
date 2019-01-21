package com.as1124.selflib.view;

import android.app.Activity;
import android.content.Context;
import android.graphics.Rect;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;

import java.util.HashMap;
import java.util.Map;

/**
 * Surface 上软键盘状态监测器
 *
 * @author as-1124(mailto:as1124huang@gmail.com)
 */
public class SoftInputMethodObserver implements ViewTreeObserver.OnGlobalLayoutListener {

    private static Map<View, SoftInputMethodObserver> observerMap = new HashMap<>();
    private int keyboardHeight = -1;

    public interface SoftInputStateWatcher {
        void softInputStateChanged(boolean isOpened);
    }

    private InputMethodManager imm;
    private Map<TextView, SoftInputStateWatcher> watchers;
    private View mDecorView;
    // 上一个处于输入状态的 TextView
    private View previousInput = null;

    private SoftInputMethodObserver(View decorView) {
        watchers = new HashMap<>();
        mDecorView = decorView;
        imm = (InputMethodManager) decorView.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        decorView.getViewTreeObserver().addOnGlobalLayoutListener(this);
    }

    /**
     * 获取SoftInputMethod观测器实例, 实例最小观测单元为一屏 Surface
     *
     * @param context current surface
     * @return 软键盘状态观测器
     */
    public static SoftInputMethodObserver getObserver(Activity context) {
        View decorView = context.getWindow().getDecorView();
        SoftInputMethodObserver observer;
        if (observerMap.containsKey(decorView)) {
            observer = observerMap.get(decorView);
        } else {
            observer = new SoftInputMethodObserver(decorView);
            observerMap.put(decorView, observer);
        }
        return observer;
    }

    /**
     * 移除SoftInputMethod观测器
     *
     * @param context the whole surface to observer
     */
    public static void removeObserver(Activity context) {
        View decorView = context.getWindow().getDecorView();
        observerMap.remove(decorView);
    }

    public void addStateWatcher(TextView textView, SoftInputStateWatcher stateWatcher) {
        if (!watchers.containsKey(textView)) {
            watchers.put(textView, stateWatcher);
        }
    }

    public void removeStateWatcher(TextView textView) {
        watchers.remove(textView);
    }

    @Override
    public void onGlobalLayout() {
        if (watchers.isEmpty() || !mDecorView.hasWindowFocus()) {
            return;
        }

        // 初始化键盘高度值, 预留50px差数空间
        Rect rect = new Rect();
        mDecorView.getWindowVisibleDisplayFrame(rect);
        if (keyboardHeight < 0) {
            keyboardHeight = mDecorView.getHeight() - rect.bottom - 50;
        }

        View currentFocus = mDecorView.findFocus();
        boolean isKeyboardVisible = ((mDecorView.getHeight() - rect.bottom) > keyboardHeight)
                & (keyboardHeight > 0);
        if (previousInput != null && watchers.containsKey(previousInput)) {
            if (!isKeyboardVisible || !imm.isActive(previousInput) || previousInput != mDecorView.findFocus()) {
                // 通知上一个Control 输入框关闭
                watchers.get(previousInput).softInputStateChanged(false);
                previousInput = null;
            }
        }

        if (currentFocus != null && watchers.containsKey(currentFocus)
                && currentFocus != previousInput && imm.isActive(currentFocus) && isKeyboardVisible) {
            // 通知 currentFocus： SoftInputMethod is available
            watchers.get(currentFocus).softInputStateChanged(true);
            previousInput = currentFocus;
        }
    }

    public View getPreviousInput() {
        return previousInput;
    }

    public void setPreviousInput(View previousInput) {
        this.previousInput = previousInput;
    }
}

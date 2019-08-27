package com.as1124.touch_input.softinput.way2;

import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Canvas;
import android.inputmethodservice.InputMethodService;
import android.inputmethodservice.Keyboard;
import android.inputmethodservice.KeyboardView;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputConnection;
import android.view.inputmethod.InputMethodInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.as1124.touch_input.R;
import com.as1124.touch_input.softinput.As1124InputMethodService;
import com.as1124.touch_input.softinput.As1124Keyboard;
import com.as1124.touch_input.softinput.ExKey;

import java.util.List;

/**
 * 通过扩展{@link ViewGroup}来实现输入法的键盘界面布局管理
 *
 * @author as-1124(mailto:as1124huang@gmail.com)
 */
public class SoftInputKeyboardLayout extends ViewGroup implements KeyboardView.OnKeyboardActionListener {

    private static final String LOG_TAG = "SELF_KEYBOARD_VIEW";

    protected static final int MSG_REPEAT = 124;
    protected static final int MSG_PREVIEW = 125;

    private As1124Keyboard mKeyboard;

    private Keyboard.Key lastTouchKey;

    private KeyboardView.OnKeyboardActionListener mKeyboardListener;

    private InputMethodManager mInputManager;

    /**
     * 多个keycode时，当前是否处于弹窗选择状态
     */
    private boolean isPopupShow = false;

    private boolean previewEnabled = true;

    private int oldOrientation = -1;

    /**
     * 按键View是否填充完成
     */
    private boolean inflateDone = false;

    /**
     * 操作的手指数
     */
    private int mPointerCount = 1;

    private GestureDetector mTouchDetector;

    private OnTouchListener keyTouchListener = (v, me) -> analyseTouchEvent(v, me);

    private Handler mHandler;

    private PopupWindow previewWindow;

    private PopupWindow selectKeyWindow;


    public SoftInputKeyboardLayout(Context context) {
        super(context);
    }

    public SoftInputKeyboardLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SoftInputKeyboardLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();

        this.mInputManager = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        GestureDetector.OnGestureListener gestureListener = new GestureDetector.SimpleOnGestureListener() {
            @Override
            public void onLongPress(MotionEvent e) {
                Log.i("Touch---Event", "onLongPress, actionMask==" + e.getActionMasked());
                showPopupKeys();
            }

            @Override
            public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
                Log.i("Touch---Event", "onFling, actionMask==" + e2.getActionMasked());
                return true;
            }
        };
        this.mTouchDetector = new GestureDetector(getContext(), gestureListener);
        this.mHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case MSG_REPEAT:
                        Keyboard.Key aKey = (Keyboard.Key) msg.obj;
                        if (aKey.text != null) {
                            mKeyboardListener.onText(aKey.text);
                        } else if (aKey.modifier) {
                            mKeyboardListener.onKey(aKey.codes[0], aKey.codes);
                        }
                        repeatKey(aKey);
                        break;
                    case MSG_PREVIEW:
                        if (previewWindow.isShowing()) {
                            previewWindow.dismiss();
                        }
                }
            }
        };

        this.previewWindow = new PopupWindow(getContext());
        this.previewWindow.setBackgroundDrawable(null);
        this.previewWindow.setTouchable(true);

        this.selectKeyWindow = new PopupWindow(getContext());
        this.selectKeyWindow.setBackgroundDrawable(null);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        // Step1: 针对 Keyboard 进行测绘并且把 inflate 的View添加进来
        // Step2: 测绘每个键（自定义组合View）

        As1124Keyboard keyboard = getKeyboard();
        int suggestedWidth = getMeasuredWidth();
        if (suggestedWidth < 100) {
            suggestedWidth = getKeyboard().getMinWidth();
        }
        setMeasuredDimension(suggestedWidth, getPaddingTop() + getPaddingBottom() + keyboard.getHeight());

        if (keyboard.isNeedResize()) {
            if (keyboard.getKeyboardType().startsWith("26")) {
                As1124Keyboard.adjust26Keys(keyboard, getMeasuredWidth());
            } else {
                As1124Keyboard.adjust9Keys(keyboard, getMeasuredWidth());
            }
            keyboard.setMinWidth(getMeasuredWidth());
            keyboard.setNeedResize(false);
        }

        if (!inflateDone) {
            initKeys();
            inflateDone = true;
            measureChildren(widthMeasureSpec, heightMeasureSpec);
        }
    }

    private void initKeys() {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        List<Keyboard.Key> allKeys = getKeyboard().getKeys();
        float scaleBase = getResources().getDisplayMetrics().scaledDensity;
        for (int i = 0; i < allKeys.size(); i++) {
            ExKey oneKey = (ExKey) allKeys.get(i);
            SoftInputKeyLayout keyView = (SoftInputKeyLayout) inflater.inflate(R.layout.view_soft_key2, this, false);
            keyView.setKeyIndex(i);
            keyView.showText(oneKey, false);
            if (oneKey.backgroundResID != -1) {
                keyView.setBackground(getResources().getDrawable(oneKey.backgroundResID));
            }
            if (oneKey.icon != null) {
                keyView.showIcon(oneKey.icon);
            }
            if (oneKey.labelSize != -1) {
                // 获得的值是已经经过计算后的值，所以要还原
                float originalSize = getResources().getDimension(oneKey.labelSize);
                keyView.setMajorTextSize(originalSize / scaleBase);
            }
            keyView.setOnTouchListener(keyTouchListener);
            this.addView(keyView, oneKey.width, oneKey.height);
        }
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        // Step3: call layout on every child, which means i need to figure out the child's position
        List<Keyboard.Key> allKeys = getKeyboard().getKeys();
        for (int i = 0; i < getChildCount(); i++) {
            Keyboard.Key key = allKeys.get(i);
            getChildAt(i).layout(key.x, key.y, key.x + key.width, key.y + key.height);
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }

    boolean analyseTouchEvent(View v, MotionEvent me) {
        boolean result = false;
        Keyboard.Key key = getKeyboard().getKeys().get(((SoftInputKeyLayout) v).getKeyIndex());
        int pointerCount = me.getPointerCount();
        if (pointerCount == mPointerCount && pointerCount == 1) {
            if (mTouchDetector.onTouchEvent(me)) {
                // 在 GestureDetector 中处理了 LongPress 事件， 如果被消费掉了则不再继续处理
                return true;
            }
            if (key != lastTouchKey) {
                removeAllMessage();
            }
            switch (me.getActionMasked()) {
                case MotionEvent.ACTION_DOWN:
                    if (key.sticky) {
                        v.setPressed(!v.isPressed());
                    } else {
                        v.setPressed(true);
                    }
                    mKeyboardListener.onPress(key.codes[0]);
                    if (key.repeatable) {
                        repeatKey(key);
                    }
                    if (isPreviewEnabled()) {
                        showPreview(key);
                    }
                    lastTouchKey = key;
                    break;
                case MotionEvent.ACTION_MOVE:
                    break;
                case MotionEvent.ACTION_UP:
                case MotionEvent.ACTION_CANCEL:
                    if (!key.sticky) {
                        v.setPressed(false);
                    }
                    mHandler.sendMessageDelayed(mHandler.obtainMessage(MSG_PREVIEW), 200);
                    if (key.text != null) {
                        mKeyboardListener.onText(key.text);
                    } else if (key.modifier) {
                        mKeyboardListener.onKey(key.codes[0], key.codes);
                    }
                    // need to remove all message
                    removeAllMessage();
                    mKeyboardListener.onRelease(key.codes[0]);
                    break;
                case MotionEvent.ACTION_OUTSIDE:
                    ((InputMethodService) getContext()).requestHideSelf(0);
                    closing();
                    break;
            }
            result = true;
        } else {

        }
        mPointerCount = pointerCount;
        return result;
    }

    /**
     * 显示按键的预览弹窗
     */
    private void showPreview(Keyboard.Key aKey) {
        if (aKey == lastTouchKey && previewWindow.isShowing() || aKey.modifier) {
            return;
        }
        if (previewWindow.getContentView() == null) {
            TextView previewText = (TextView) LayoutInflater.from(getContext()).inflate(R.layout.view_key_preview, this, false);
            previewWindow.setWidth(aKey.width + 40);
            previewWindow.setHeight(aKey.height + 40);
            previewWindow.setContentView(previewText);
        }
        ((TextView) previewWindow.getContentView()).setText(aKey.label);
        int marginLeft = ((MarginLayoutParams) getLayoutParams()).getMarginStart();
        int popupX = aKey.x + marginLeft + getPaddingLeft();
        int popupY = aKey.y - 10;
        if (previewWindow.isShowing()) {
            previewWindow.update(popupX, popupY, previewWindow.getWidth(), previewWindow.getHeight());
        } else {
            previewWindow.showAtLocation(this, Gravity.NO_GRAVITY, popupX, popupY);
        }

    }

    /**
     * 长按时触发，显示多个KeyCode的选择框
     */
    private void showPopupKeys() {

    }

    private void repeatKey(Keyboard.Key key) {
        Message msg = mHandler.obtainMessage(MSG_REPEAT, key);
        mHandler.sendMessageDelayed(msg, 100);
    }

    public void removeAllMessage() {
        mHandler.removeMessages(MSG_REPEAT);
    }

    private void onShiftState(boolean isShifted) {
        List<Keyboard.Key> allKey = getKeyboard().getKeys();
        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            View child = getChildAt(i);
            if (child instanceof SoftInputKeyLayout) {
                SoftInputKeyLayout keyContainer = (SoftInputKeyLayout) child;
                keyContainer.showText(allKey.get(keyContainer.getKeyIndex()), isShifted);
            }
        }
    }

    public void closing() {

    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();

        closing();
    }

    @Override
    public void onPress(int primaryCode) {
        // 所有按键按下都会触发 onPress
        Log.i(LOG_TAG, "onPress: " + primaryCode);
    }

    @Override
    public void onKey(int primaryCode, int[] keyCodes) {
        // Modify 键按下会触发，普通键不触发
        As1124InputMethodService inputService = ((As1124InputMethodService) getContext());
        switch (primaryCode) {
            case Keyboard.KEYCODE_SHIFT:
                getKeyboard().setShifted(!getKeyboard().isShifted());
                onShiftState(getKeyboard().isShifted());
                break;
            case Keyboard.KEYCODE_DELETE:
                InputConnection connection = ((InputMethodService) getContext()).getCurrentInputConnection();
                if (connection != null) {
                    CharSequence selection = connection.getSelectedText(0);
                    if (selection != null) {
                        connection.deleteSurroundingText(selection.length(), 0);
                    } else {
                        connection.deleteSurroundingText(1, 0);
                    }
                }
                break;
            case Keyboard.KEYCODE_DONE:
                // 直接通过 InputMethodManager 来隐藏键盘不行, 因为windowToken
//            mInputManager.hideSoftInputFromWindow(getWindowToken(), 0);

                // Step1: 如果是 ACTION_DONE 则隐藏输入法
                // Step2: 分发对应的ACTION事件
                EditorInfo ei = ((InputMethodService) getContext()).getCurrentInputEditorInfo();
                InputConnection ic = ((InputMethodService) getContext()).getCurrentInputConnection();
                if (ei != null && ic != null) {
                    int actionMask = ei.imeOptions & EditorInfo.IME_MASK_ACTION;
                    if (actionMask == EditorInfo.IME_ACTION_NONE) {
                        ((InputMethodService) getContext()).requestHideSelf(0);
                    }
                    ic.performEditorAction(ei.actionId == 0 ? actionMask : ei.actionId);
                }
                break;
            case Keyboard.KEYCODE_MODE_CHANGE:
                // 切换键盘类型
                String type = getKeyboard().getKeyboardType();
                if ("26_en".equals(type)) {
                    setKeyboard(inputService.getKeyboard("9_num"));
                } else {
                    setKeyboard(inputService.getKeyboard("26_en"));
                }
                requestLayout();
                break;
            case 731:
                // 切换输入法
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                    inputService.switchToNextInputMethod(false);
                } else {
                    String nextInputId = "";
                    List<InputMethodInfo> softInputs = mInputManager.getInputMethodList();
                    for (InputMethodInfo info : softInputs) {
                        if (!info.getPackageName().equals(getContext().getPackageName())) {
                            nextInputId = info.getId();
                            break;
                        }
                    }
                    ((InputMethodService) getContext()).switchInputMethod(nextInputId);
                }
                break;
            default:
                if (isPopupShow) {
                    onText(String.valueOf((char) primaryCode));
                    isPopupShow = false;
                }
        }
    }

    @Override
    public void onRelease(int primaryCode) {
        Log.i(LOG_TAG, "onRelease: " + primaryCode);
    }

    @Override
    public void onText(CharSequence text) {
        // ATTENTION 处理提示语句
        InputConnection connection = ((InputMethodService) getContext()).getCurrentInputConnection();
        if (connection != null) {
            if (getKeyboard().isShifted()) {
                connection.commitText(text.toString().toUpperCase(), text.length() - 1);
            } else {
                connection.commitText(text, text.length() - 1);
            }
        }
    }

    @Override
    public void swipeLeft() {
        Log.i(LOG_TAG, "swipeLeft");
    }

    @Override
    public void swipeRight() {
        Log.i(LOG_TAG, "swipeRight");
    }

    @Override
    public void swipeDown() {
        Log.i(LOG_TAG, "swipeDown");
    }

    @Override
    public void swipeUp() {
        Log.i(LOG_TAG, "swipeUp");
    }

    @Override
    protected void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if (newConfig.orientation != oldOrientation && getKeyboard() != null) {
            getKeyboard().setNeedResize(true);
        }
        oldOrientation = newConfig.orientation;
    }


    public void setKeyboard(As1124Keyboard mKeyboard) {
        this.mKeyboard = mKeyboard;
    }

    public As1124Keyboard getKeyboard() {
        return mKeyboard;
    }

    public void setOnKeyboardActionListener(KeyboardView.OnKeyboardActionListener listener) {
        mKeyboardListener = listener;
    }

    public boolean isPreviewEnabled() {
        return previewEnabled;
    }

    public void setPreviewEnabled(boolean previewEnable) {
        this.previewEnabled = previewEnable;
    }
}

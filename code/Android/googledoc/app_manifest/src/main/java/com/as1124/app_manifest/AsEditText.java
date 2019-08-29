package com.as1124.app_manifest;

import android.content.Context;
import android.util.AttributeSet;
import android.view.ContextMenu;
import android.widget.EditText;

public class AsEditText extends EditText {

    public AsEditText(Context context) {
        super(context);
    }

    public AsEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public AsEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();

        this.setSelected(false);
    }

    @Override
    public boolean hasSelection() {
        setTextIsSelectable(false);
        return false;
    }

}

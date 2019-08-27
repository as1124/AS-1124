package com.as1124.touch_input.softinput;

import android.content.res.Resources;
import android.content.res.XmlResourceParser;
import android.inputmethodservice.Keyboard;

/**
 * @author as-1124(mailto:as1124huang@gmail.com)
 */
public class ExKey extends Keyboard.Key implements Cloneable {

    private static final String APP_NAMESPACE = "http://schemas.android.com/apk/res-auto";

    public int backgroundResID;

    public int labelSize;

    public ExKey(Resources res, Keyboard.Row parent, int x, int y, XmlResourceParser parser) {
        super(res, parent, x, y, parser);

        backgroundResID = parser.getAttributeResourceValue(APP_NAMESPACE, KeyboardConstants.ATTR_KEY_BACKGROUND, -1);
        labelSize = parser.getAttributeResourceValue(APP_NAMESPACE, KeyboardConstants.ATTR_KEY_TEXTSIZE, -1);

    }

    @Override
    public boolean isInside(int x, int y) {
        // 修改边界判断，简单化
        return (x >= this.x && x <= (this.x + this.width) && y >= this.y && (y <= this.y + this.height));
    }

    @Override
    public ExKey clone() throws CloneNotSupportedException {
        ExKey newKey = (ExKey) super.clone();

        return newKey;
    }
}

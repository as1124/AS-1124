package com.as1124.touch_input.softinput.way1;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.XmlResourceParser;
import android.inputmethodservice.Keyboard;

import java.util.ArrayList;
import java.util.List;

/**
 * 自定义键盘; 目的是为了重新计算Key的坐标值以及增加、适配自定义属性来更细粒度的控制每个键的大小
 *
 * @author as-1124(mailto:as1124huang@gmail.com)
 */
public class As1124Keyboard extends Keyboard {

    protected List<ExRow> keyRows;

    private ExRow currentRow;

    // 26_en, 9_en, 9_num
    private String keyboardType = "26_en";

    public As1124Keyboard(Context context, int xmlLayoutResId) {
        super(context, xmlLayoutResId);
        onAllKeysLoad();
    }

    public As1124Keyboard(Context context, int xmlLayoutResId, int modeId) {
        super(context, xmlLayoutResId, modeId);
        onAllKeysLoad();
    }


    @Override
    protected Row createRowFromXml(Resources res, XmlResourceParser parser) {
        // 强制约定所有尺寸单位都按照dp来计算
        ExRow row = new ExRow(res, this, parser);
        currentRow = row;
        if (keyRows == null) {
            keyRows = new ArrayList<>(8);
        }
        keyRows.add(row);

        return row;
    }

    @Override
    protected Key createKeyFromXml(Resources res, Row parent, int x, int y, XmlResourceParser parser) {
        ExKey key = new ExKey(res, parent, x, y, parser);
        currentRow.myKeys.add(key);
        return key;
    }

    private void onAllKeysLoad() {
    }

    public String getKeyboardType() {
        return keyboardType;
    }

    public void setKeyboardType(String keyboardType) {
        this.keyboardType = keyboardType;
    }
}

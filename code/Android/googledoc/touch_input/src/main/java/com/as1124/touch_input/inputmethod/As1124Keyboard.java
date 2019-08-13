package com.as1124.touch_input.inputmethod;

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

    private static List<Key> myKeys = new ArrayList<>();

    private static List<Row> myRows = new ArrayList<>();

    private Row currentRow;

    private int mRowIndex = 0;

    public As1124Keyboard(Context context, int xmlLayoutResId) {
        super(context, xmlLayoutResId);
        onAllKeysLoad();
    }

    public As1124Keyboard(Context context, int xmlLayoutResId, int modeId) {
        super(context, xmlLayoutResId, modeId);
        onAllKeysLoad();
    }

    private void adjustRowKeys() {
        List<Key> keys = super.getKeys();
        int totalWidth;
    }

    @Override
    protected Row createRowFromXml(Resources res, XmlResourceParser parser) {
        Row row = super.createRowFromXml(res, parser);
        this.myRows.add(row);
        if (this.myKeys != null) {
            // 每当有新的一行键模型产生时，重新测算位置
            adjustRowKeys();
            this.myKeys.clear();
        }
        currentRow = row;
        return row;
    }

    @Override
    protected Key createKeyFromXml(Resources res, Row parent, int x, int y, XmlResourceParser parser) {
        // 创建新的按键模型的时候调用此方法
        Key key = super.createKeyFromXml(res, parent, x, y, parser);
        this.myKeys.add(key);

        return key;
    }

    private void onAllKeysLoad() {

    }
}

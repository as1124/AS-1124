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

    private int minWidth;

    private boolean needResize = true;

    // 26_en, 9_en, 9_num
    private String keyboardType = "26_en";

    private boolean randomKeys = false;

    public As1124Keyboard(Context context, int xmlLayoutResId) {
        super(context, xmlLayoutResId);
    }

    public As1124Keyboard(Context context, int xmlLayoutResId, int modeId) {
        super(context, xmlLayoutResId, modeId);
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

    @Override
    public List<Key> getKeys() {
        List<Key> keys = super.getKeys();

        // ATTENTION 随机键盘的算法需要调整
//        Key[] newKeys = new Key[keys.size()];
//        if (isRandomKeys()) {
//            Map<Integer, Key> normalKeys = new HashMap<>();
//            Random random = new Random();
//            for (int i = 0; i < newKeys.length; i++) {
//                Key key = keys.get(i);
//                if (key.modifier) {
//                    newKeys[i] = keys.get(i);
//                } else {
//                    normalKeys.put(i, key);
//                }
//            }
//            Map.Entry<Integer, Key>[] entrys = new Map.Entry[normalKeys.size()];
//            normalKeys.entrySet().toArray(entrys);
//            for (int i = 0; i < newKeys.length; i++) {
//                Map.Entry<Integer, Key> exchangeEntry;
//                if (newKeys[i] != null) {
//                    // 普通键需要进行随机交换
//                    while (true) {
//                        int index = random.nextInt(normalKeys.size());
//                        if (entrys[index].getKey().intValue() != i) {
//                            exchangeEntry = entrys[index];
//                            break;
//                        }
//                    }
//
//                }
//            }
//        }
        return keys;
    }

    @Override
    public int getMinWidth() {
        return this.minWidth;
    }

    public void setMinWidth(int minWidth) {
        this.minWidth = minWidth;
    }

    public String getKeyboardType() {
        return keyboardType;
    }

    public void setKeyboardType(String keyboardType) {
        this.keyboardType = keyboardType;
    }

    public boolean isNeedResize() {
        return needResize;
    }

    public void setNeedResize(boolean needResize) {
        this.needResize = needResize;
    }

    public boolean isRandomKeys() {
        return randomKeys;
    }

    public void setRandomKeys(boolean randomKeys) {
        this.randomKeys = randomKeys;
    }
}

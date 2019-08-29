package com.as1124.touch_input.softinput;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.XmlResourceParser;
import android.inputmethodservice.Keyboard;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * 自定义键盘; 目的是为了重新计算Key的坐标值以及增加、适配自定义属性来更细粒度的控制每个键的大小
 * <pre>
 *
 *
 * 在处理随机键盘的时候的问题及解决思路：
 * </pre>
 * 因为在{@link android.inputmethodservice.KeyboardView#setKeyboard(Keyboard)}的时候
 * 先对所有的Key做了浅拷贝给mKeys字段，之后才进行了View的measure 和 layout ，所以存在的问题如下：
 * <ul>
 * <li>浅拷贝导致在随机键盘时出现Key对象上x、y的同步读写问题（因为不是volatile修饰）, 总是成对出现几个Key的坐标交换失败</li>
 * <li>键盘随机只能在onMeasure之后明确了具体的宽、高之后才能进行随机处理，所以坐标信息错乱</li>
 * </ul>
 * 相关解决思路如下：<br/>
 * <ol>
 * <li>修改浅拷贝为深拷贝，添加{@link Cloneable}接口支持</li>
 * <li>修改setKeyboard的生命周期：先requestLayout，然后再调用super处理mKeys的复制</li>
 * </ol>
 *
 * @author as-1124(mailto:as1124huang@gmail.com)
 */
public class As1124Keyboard extends Keyboard {

    private List<ExRow> keyRows;

    private ExRow currentRow;

    private int minWidth;

    private boolean needResize = true;

    // 26_en, 9_en, 9_num
    private String keyboardType = "26_en";

    /**
     * 是否随机排列按键
     */
    private boolean randomKeys = false;

    private List<Key> keyInRandom = new ArrayList<>();

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

    /**
     * 处理随机键盘
     */
    public synchronized void makeKeysRandom() {
        if (isRandomKeys()) {
            keyInRandom.clear();
            List<Key> originalKeys = super.getKeys();
            Key[] newKeys = new Key[originalKeys.size()];
            List<Map.Entry<Integer, Key>> normalKeys = new ArrayList<>();
            for (int i = 0; i < originalKeys.size(); i++) {
                Key key = originalKeys.get(i);
                if (!key.modifier && key.text != null && Character.isLetterOrDigit(key.text.charAt(0))) {
                    normalKeys.add(new AbstractMap.SimpleEntry<>(i, key));
                } else {
                    newKeys[i] = key;
                }
            }
            Random random = new Random();
            while (normalKeys.size() > 1) {
                Map.Entry<Integer, Key> one = normalKeys.remove(random.nextInt(normalKeys.size()));
                Map.Entry<Integer, Key> two = normalKeys.remove(random.nextInt(normalKeys.size()));
                if (one == two) {
                    continue;
                }

//                // 存在对象修改同步问题
//                int tempX = one.getValue().x;
//                int tempY = one.getValue().y;
//                one.getValue().x = two.getValue().x;
//                one.getValue().y = two.getValue().y;
//                two.getValue().x = tempX;
//                two.getValue().y = tempY;
//                keys.set(one.getKey(), two.getValue());
//                keys.set(two.getKey(), one.getValue());

                try {
                    // 采用深拷贝模式然后全部替换, 以此来冲抵掉同步带来的问题
                    ExKey oneCopy = ((ExKey) one.getValue()).clone();
                    oneCopy.x = two.getValue().x;
                    oneCopy.y = two.getValue().y;

                    ExKey twoCopy = ((ExKey) two.getValue()).clone();
                    twoCopy.x = one.getValue().x;
                    twoCopy.y = one.getValue().y;

                    newKeys[two.getKey()] = oneCopy;
                    newKeys[one.getKey()] = twoCopy;
                } catch (CloneNotSupportedException e) {
                    e.printStackTrace();
                }
            }
            keyInRandom.addAll(Arrays.asList(newKeys));
        }
    }

    @Override
    public List<Key> getKeys() {
        if (isRandomKeys()) {
            return this.keyInRandom;
        } else {
            return super.getKeys();
        }
    }

    public static void adjust26Keys(As1124Keyboard keyboard, int w) {
        List<ExRow> allRows = keyboard.keyRows;
        int rowNum = allRows.size();
        int normalKeyW = -1;
        for (int i = 0; i < rowNum; i++) {
            ExRow row = allRows.get(i);
            int keyNum = row.myKeys.size();
            int normalKeyNum = 0;

            int modifyKeyTotal = 0;
            int gapTotal = 0;

            // Step1：先统计各类型按键数量及对应的宽度和
            for (int j = 0; j < keyNum; j++) {
                ExKey key = row.myKeys.get(j);
                if (j > 0) {
                    gapTotal += key.gap;
                }
                if (key.modifier) {
                    modifyKeyTotal += key.width;
                } else {
                    normalKeyNum++;
                }
            }

            // Step2：现有View宽度不够, 保留修饰键的宽度, 确定普通键的宽度
            if (i == 0) {
                normalKeyW = (w - gapTotal - modifyKeyTotal) / normalKeyNum;
            }

            // Step3：确定修饰键需要调整的宽度
            int differ = (normalKeyNum * normalKeyW + modifyKeyTotal + gapTotal - w);
            int cutoutPixel = 0;
            if (keyNum != normalKeyNum) {
                cutoutPixel = differ / (keyNum - normalKeyNum);
            }

            // Step4：调整居中显示时的起始offset
            int leftOffset = 0;
            if ((keyNum == normalKeyNum) && (normalKeyNum * normalKeyW + gapTotal) < w) {
                leftOffset = (w - normalKeyNum * normalKeyW - gapTotal) / 2;
            }

            // Step5：确定每个键值的坐标
            int x = leftOffset;
            for (int k = 0; k < keyNum; k++) {
                ExKey key = row.myKeys.get(k);
                if (key.modifier) {
                    key.width = key.width - cutoutPixel;
                } else {
                    key.width = normalKeyW;
                }
                key.x = x;
                x = x + key.width + key.gap;
            }
        }
    }

    public static void adjust9Keys(As1124Keyboard keyboard, int w) {
        List<ExRow> allRows = keyboard.keyRows;
        int rowNum = allRows.size();
        for (int i = 0; i < rowNum; i++) {
            ExRow row = allRows.get(i);
            int keyNum = row.myKeys.size();
            int gapTotal = row.myKeys.get(0).gap * (keyNum + 1);

            int keyW = (w - gapTotal) / keyNum;
            int leftOffset = row.myKeys.get(0).gap;
            for (int j = 0; j < keyNum; j++) {
                ExKey key = row.myKeys.get(j);
                key.x = leftOffset;
                key.width = keyW;
                leftOffset = leftOffset + key.width + key.gap;
            }
        }
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

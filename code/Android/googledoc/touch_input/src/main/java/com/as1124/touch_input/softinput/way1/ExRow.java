package com.as1124.touch_input.softinput.way1;

import android.content.res.Resources;
import android.content.res.XmlResourceParser;
import android.inputmethodservice.Keyboard;

import java.util.ArrayList;

public class ExRow extends Keyboard.Row {

    public ArrayList<ExKey> myKeys = new ArrayList<>();

    public int backgroundDrawable;

    public ExRow(Keyboard parent) {
        super(parent);
    }

    public ExRow(Resources res, Keyboard parent, XmlResourceParser parser) {
        super(res, parent, parser);

        backgroundDrawable = parser.getAttributeResourceValue(null, KeyboardConstants.ATTR_KEY_BACKGROUND, -1);

    }


}

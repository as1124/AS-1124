package com.as1124.ui.layout.recycler;

import android.graphics.Color;
import android.widget.EdgeEffect;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Item Edge特效
 *
 * @author As-1124(mailto:as1124huang@gmail.com)
 */
public class RecyclerListEdgeEffectFactory extends RecyclerView.EdgeEffectFactory {

    protected RecyclerListEdgeEffectFactory() {
        // default constructor
    }

    @NonNull
    @Override
    protected EdgeEffect createEdgeEffect(@NonNull RecyclerView view, int direction) {
//        switch (direction) {
//            case DIRECTION_LEFT:
//                break;
//            case DIRECTION_RIGHT:
//                break;
//            case DIRECTION_TOP:
//                break;
//            case DIRECTION_BOTTOM:
//                break;
//            default:
//        }
        EdgeEffect edgeEffect = super.createEdgeEffect(view, direction);
        edgeEffect.setColor(Color.YELLOW);
        edgeEffect.setSize(800, 600);

        return edgeEffect;
    }
}

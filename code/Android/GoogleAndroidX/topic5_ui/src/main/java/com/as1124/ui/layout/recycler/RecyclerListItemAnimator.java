package com.as1124.ui.layout.recycler;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.animation.AnimatorListenerAdapter;

import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.RecyclerView;

import com.as1124.ui.R;

import java.util.ArrayList;
import java.util.List;

/**
 * RecyclerView中Item变化动画
 *
 * @author As-1124(mailto:as1124huang@gmail.com)
 */
public class RecyclerListItemAnimator extends DefaultItemAnimator {

    private List<RecyclerView.ViewHolder> mPendingAddAnimators = new ArrayList<>();
    private List<RecyclerView.ViewHolder> mAddAnimators = new ArrayList<>();

    private List<RecyclerView.ViewHolder> mPendingRemoveAnimators = new ArrayList<>();
    private List<RecyclerView.ViewHolder> mRemoveAnimators = new ArrayList<>();

    public RecyclerListItemAnimator() {
        setAddDuration(2000);
        setRemoveDuration(2000);
        setChangeDuration(2000);
        setMoveDuration(2000);
    }

    // ATTENTION 处理删除、添加动画效果时，item的移动存在bug：存在RecyclerView中间item不显示，出现空白区域的问题（item的相对位置并不错乱）


    /********************* 4个方法来自SimpleItemAnimator ******************/
    @Override
    public boolean animateRemove(RecyclerView.ViewHolder holder) {
        mPendingRemoveAnimators.add(holder);
        return true;
    }

    @Override
    public boolean animateAdd(RecyclerView.ViewHolder holder) {
        holder.itemView.setAlpha(0);
        mPendingAddAnimators.add(holder);
        return true;
    }

    @Override
    public boolean animateMove(RecyclerView.ViewHolder holder, int fromX, int fromY, int toX, int toY) {
        return super.animateMove(holder, fromX, fromY, toX, toY);
    }

    @Override
    public boolean animateChange(RecyclerView.ViewHolder oldHolder, RecyclerView.ViewHolder newHolder, int fromLeft, int fromTop, int toLeft, int toTop) {
        return super.animateChange(oldHolder, newHolder, fromLeft, fromTop, toLeft, toTop);
    }

    /******************* 4个方法来自RecyclerView.ItemAnimator ********************/
    @Override
    public void runPendingAnimations() {
        super.runPendingAnimations();
        boolean hasRemove = !mPendingRemoveAnimators.isEmpty();
        boolean hasAdd = !mPendingAddAnimators.isEmpty();
        if (hasRemove) {
            for (RecyclerView.ViewHolder holder : mPendingRemoveAnimators) {
                mRemoveAnimators.add(holder);
                Animator animator = AnimatorInflater.loadAnimator(holder.itemView.getContext(), R.animator.animator_listitem_remove);
                animator.setDuration(getRemoveDuration());
                animator.setTarget(holder.itemView);
                animator.addListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationCancel(Animator animation) {
                        animation.setTarget(null);
                        mRemoveAnimators.remove(holder);
                    }

                    @Override
                    public void onAnimationStart(Animator animation) {
                        dispatchRemoveStarting(holder);
                    }

                    @Override
                    public void onAnimationEnd(Animator animation) {
                        animation.setTarget(null);
                        mRemoveAnimators.remove(holder);
                        dispatchRemoveFinished(holder);
                    }
                });
                animator.start();
            }
            mPendingRemoveAnimators.clear();
        }
        if (hasAdd) {
            for (RecyclerView.ViewHolder holder : mPendingAddAnimators) {
                mAddAnimators.add(holder);
                Animator animator = AnimatorInflater.loadAnimator(holder.itemView.getContext(), R.animator.animator_listitem_add);
                animator.setDuration(getAddDuration());
                animator.setTarget(holder.itemView);
                animator.addListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationCancel(Animator animation) {
                        animation.setTarget(null);
                        mAddAnimators.remove(holder);
                    }

                    @Override
                    public void onAnimationStart(Animator animation) {
                        dispatchAddStarting(holder);
                    }

                    @Override
                    public void onAnimationEnd(Animator animation) {
                        animation.setTarget(null);
                        mAddAnimators.remove(holder);
                        dispatchAddFinished(holder);
                    }
                });
                animator.start();
            }
            mPendingAddAnimators.clear();
        }
    }


    @Override
    public boolean isRunning() {
        return (!mAddAnimators.isEmpty() || !mRemoveAnimators.isEmpty()) || super.isRunning();
    }
}

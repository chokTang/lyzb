package com.lyzb.jbx.util.itemdecoration;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.text.TextPaint;
import android.text.TextUtils;
import android.view.View;

import com.like.utilslib.screen.DensityUtil;

public class StickyDecoration extends RecyclerView.ItemDecoration {

    private IGroupListener listener;
    private int mGroupHeight = DensityUtil.dpTopx(40);
    private int mLeftMargin = DensityUtil.dpTopx(10);
    private TextPaint textPaint;
    private Paint mGroutPaint;

    public StickyDecoration(IGroupListener listener) {
        this.listener = listener;
        mGroutPaint = new Paint();
        mGroutPaint.setColor(Color.WHITE);

        textPaint = new TextPaint();
        textPaint.setAntiAlias(true);
        textPaint.setTextSize(DensityUtil.spTopx(16));
        textPaint.setColor(Color.parseColor("#333333"));
        textPaint.setTextAlign(Paint.Align.LEFT);
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        //处理高度
        int position = parent.getChildAdapterPosition(view);
        String groupName = getGroupName(position);
        if (groupName == null) return;
        if (position == 0 || isFristInGroup(position)) {
            outRect.top = mGroupHeight;
        }
    }

    @Override
    public void onDrawOver(Canvas c, RecyclerView parent, RecyclerView.State state) {
        super.onDrawOver(c, parent, state);
        final int itemCount = state.getItemCount();
        final int childCount = parent.getChildCount();
        final int left = parent.getLeft() + parent.getPaddingLeft();
        final int right = parent.getRight() - parent.getPaddingRight();
        String preGroupName;      //标记上一个item对应的Group
        String currentGroupName = null;       //当前item对应的Group
        for (int i = 0; i < childCount; i++) {
            View view = parent.getChildAt(i);
            int position = parent.getChildAdapterPosition(view);
            preGroupName = currentGroupName;
            currentGroupName = getGroupName(position);
            if (currentGroupName == null || TextUtils.equals(currentGroupName, preGroupName))
                continue;
            int viewBottom = view.getBottom();
            float top = Math.max(mGroupHeight, view.getTop());//top 决定当前顶部第一个悬浮Group的位置
            if (position + 1 < itemCount) {
                //获取下个GroupName
                String nextGroupName = getGroupName(position + 1);
                //下一组的第一个View接近头部
                if (!currentGroupName.equals(nextGroupName) && viewBottom < top) {
                    top = viewBottom;
                }
            }
            //根据top绘制group
            c.drawRect(left, top - mGroupHeight, right, top, mGroutPaint);
            Paint.FontMetrics fm = textPaint.getFontMetrics();
            //文字竖直居中显示
            float baseLine = top - (mGroupHeight - (fm.bottom - fm.top)) / 2 - fm.bottom;
            c.drawText(currentGroupName, left + mLeftMargin, baseLine, textPaint);
        }
    }

    private boolean isFristInGroup(int position) {
        if (position == 0) {
            return true;
        } else {
            String preGroupName = getGroupName(position - 1);
            String groupName = getGroupName(position);
            return !TextUtils.equals(preGroupName, groupName);
        }
    }

    private String getGroupName(int poistion) {
        if (listener != null) {
            return listener.getGroupName(poistion);
        }
        return null;
    }
}

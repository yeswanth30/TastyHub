package com.dailyrecipe;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import androidx.customview.widget.ViewDragHelper;

public class SlidingMenuLayout extends ViewGroup {

    private View menuView;
    private View mainView;
    private ViewDragHelper dragHelper;

    public SlidingMenuLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        dragHelper = ViewDragHelper.create(this, 1.0f, new ViewDragHelper.Callback() {
            @Override
            public boolean tryCaptureView(View child, int pointerId) {
                return child == mainView;
            }

            @Override
            public int clampViewPositionHorizontal(View child, int left, int dx) {
                return Math.max(0, Math.min(left, menuView.getWidth()));
            }

            @Override
            public void onViewReleased(View releasedChild, float xvel, float yvel) {
                if (xvel > 0 || (xvel == 0 && releasedChild.getLeft() > menuView.getWidth() / 2)) {
                    openMenu();
                } else {
                    closeMenu();
                }
            }
        });
    }

    void openMenu() {
        dragHelper.smoothSlideViewTo(mainView, menuView.getWidth(), 0);
        invalidate();
    }

    void closeMenu() {
        dragHelper.smoothSlideViewTo(mainView, 0, 0);
        invalidate();
    }

    @Override
    public void computeScroll() {
        if (dragHelper.continueSettling(true)) {
            invalidate();
        }
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        menuView = getChildAt(0);
        mainView = getChildAt(1);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        menuView.layout(0, 0, menuView.getMeasuredWidth(), b);
        mainView.layout(0, 0, r, b);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        measureChildren(widthMeasureSpec, heightMeasureSpec);

        int maxWidth = MeasureSpec.getSize(widthMeasureSpec);
        int maxHeight = MeasureSpec.getSize(heightMeasureSpec);

        setMeasuredDimension(
                resolveSizeAndState(maxWidth, widthMeasureSpec, 0),
                resolveSizeAndState(maxHeight, heightMeasureSpec, 0)
        );
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return dragHelper.shouldInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        dragHelper.processTouchEvent(event);
        return true;
    }
}


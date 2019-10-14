package com.feedreader.myapplication.tools;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.Scroller;

import com.feedreader.myapplication.R;

/**
 * Author: Zixin Ye
 * This class sets a self-defined SlideLayout style for ListView
 */
public class SlideLayout extends FrameLayout {

    private View contentView;
    private View menuView;

    private int viewHeight; //The height should be the same
    private int contentWidth;
    private int menuWidth;

    private float startX;
    private float startY;
    private float downX;
    private float downY;

    private Scroller scroller;

    public SlideLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        scroller = new Scroller(context);
    }

    /*
    The layout file is called when it is finished loading
    */
    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        contentView = findViewById(R.id.news);
        menuView = findViewById(R.id.slide_list_delete);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        viewHeight = getMeasuredHeight();

        contentWidth = contentView.getMeasuredWidth();
        menuWidth = menuView.getMeasuredWidth();
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        menuView.layout(contentWidth, 0, contentWidth + menuWidth, viewHeight);
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {

        super.onTouchEvent(event);
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                downX = startX = event.getX();
                downY = startY = event.getY();
                break;

            case MotionEvent.ACTION_MOVE:
                float endX = event.getX();
                float endY = event.getY();

                //Compute the offset
                float distanceX = endX - startX;
                int toScrollX = (int) (getScrollX() - distanceX);

                //Remove illegal value
                if (toScrollX < 0) {
                    toScrollX = 0;
                }
                if (toScrollX > menuWidth) {
                    toScrollX = menuWidth;
                }
                scrollTo(toScrollX, getScrollY());

                startX = event.getX();

                float dx = Math.abs(event.getX() - downX);
                float dy = Math.abs(event.getY() - downY);
                if (dx > dy && dx > 6) {
                    getParent().requestDisallowInterceptTouchEvent(true);
                }
                break;

            case MotionEvent.ACTION_UP:
                if (getScrollX() > menuWidth / 2) {
                    openMenu();
                } else {
                    closeMenu();
                }
                break;
        }

        return true;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        switch (event.getAction()) {

            case MotionEvent.ACTION_DOWN:
                downX = startX = event.getX();
                downY = startY = event.getY();
                if (onStateChangeListener != null) {
                    onStateChangeListener.onMove(this);
                }
                break;

            case MotionEvent.ACTION_MOVE:
                float dx = Math.abs(event.getX() - downX);
                float dy = Math.abs(event.getY() - downY);
                if (dx > dy && dx > 6) {
                    //Intercept events
                    return true;
                }
                break;

            case MotionEvent.ACTION_UP:
                break;
        }
        return super.onInterceptTouchEvent(event);
    }

    /*
    Method for opening Menu
    */
    public void openMenu() {
        int dx = menuWidth - getScrollX();
        scroller.startScroll(getScrollX(), getScrollY(), dx, getScrollY());
        invalidate();
        if (onStateChangeListener != null) {
            onStateChangeListener.onOpen(this);
        }
    }

    /*
    Method for closing Menu
    */
    public void closeMenu() {
        //0 represents the target distance that menu moves to
        int dx = 0 - getScrollX();
        scroller.startScroll(getScrollX(), getScrollY(), dx, getScrollY());
        invalidate();
        if (onStateChangeListener != null) {
            onStateChangeListener.onClose(this);
        }
    }

    @Override
    public void computeScroll() {
        super.computeScroll();
        if (scroller.computeScrollOffset()) {
            scrollTo(scroller.getCurrX(), scroller.getCurrY());
            invalidate();
        }
    }

    public interface OnStateChangeListener {
        void onOpen(SlideLayout slideLayout);

        void onMove(SlideLayout slideLayout);

        void onClose(SlideLayout slideLayout);
    }

    public OnStateChangeListener onStateChangeListener;

    public void setOnStateChangeListener(OnStateChangeListener onStateChangeListener) {
        this.onStateChangeListener = onStateChangeListener;
    }
}
package com.example.myslidemenu.view;

import android.content.Context;
import android.util.AttributeSet;
import android.util.LogPrinter;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.Scroller;
import android.util.Log;


public class SlideMenu extends FrameLayout {

    private Scroller scroller;
    private View menuView,mainView;
    private int menuWidth;


    public SlideMenu(Context context) {
        super(context);
        init();
    }

    public SlideMenu(Context context, AttributeSet attrs){
        super(context, attrs);
        init();
    }

    private void init(){
        scroller = new Scroller(getContext());
    }

    @Override
    protected void onFinishInflate(){
        super.onFinishInflate();
        menuView = getChildAt(0);
        mainView = getChildAt(1);
        menuWidth = menuView.getLayoutParams().width;
    }

    //menu的滑动功能

    public boolean onInterceptTouchEvent(MotionEvent event){

        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                downX = (int)event.getX();
                break;
            case MotionEvent.ACTION_MOVE:
                //int down_x = (int)event.getX();
                //??????java变量的产生湮灭时间顺序downX
                int deltaX = (int)(event.getX() - downX);
                if(Math.abs(downX) > 8){
                    return true;
                }
                break;
        }
        return super.onInterceptTouchEvent(event);
    }

    //屏幕滑动
    private int downX;
    @Override
    protected void onLayout(boolean changed,int l,int t,int r,int b){
        menuView.layout(-menuWidth,0,0,b);
        mainView.layout(0,0,r,b);
    }

    //处理屏幕滑动
    public boolean onTouchEvent(MotionEvent event){
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN://初次接触到屏幕触发
                downX = (int)event.getX();
                break;
            case MotionEvent.ACTION_MOVE://在屏幕上滑动触发
                int moveX = (int)event.getX();
                //Log.d("move","move");//3.28
                int deltaX = moveX - downX;
                int newScrollX = getScrollX() - deltaX;
                if(newScrollX < -menuWidth) newScrollX = -menuWidth;
                if(newScrollX > 0) newScrollX = 0;
                //System.out.println(newScrollX);
                scrollTo(newScrollX,0);
                //scrollTo调用了吗
                downX = moveX;
                //这一步之后downX就是0
                //System.out.println(downX);
                //????没看懂这一段
                break;
            case MotionEvent.ACTION_UP://离开屏幕时触发
                if(getScrollX() > -menuWidth/2){
                    closeMenu();
                }else{
                    openMenu();
                }
                break;
        }
        return true;
    }

    private void closeMenu(){
        scroller.startScroll(getScrollX(),0,0-getScrollX(),0,400);
        invalidate();
    }

    private void openMenu(){
        scroller.startScroll(getScrollX(),0,-menuWidth-getScrollX(),0,400);
        invalidate();
    }
    //invalide->draw->computeScroll
    public void computScroll(){
        super.computeScroll();
        if(scroller.computeScrollOffset()){
            //return true，表示动画还没结束
            scrollTo(scroller.getCurrX(),0);
            invalidate();
        }
    }

    public void switchMenu(){
        if(getScrollX() == 0){
            openMenu();
        }else{
            closeMenu();
        }
    }
}

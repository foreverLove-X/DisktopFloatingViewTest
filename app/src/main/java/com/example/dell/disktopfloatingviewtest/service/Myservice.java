package com.example.dell.disktopfloatingviewtest.service;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;

import com.example.dell.disktopfloatingviewtest.activity.Main3Activity;

public class Myservice extends Service {
    //屏幕宽度
    private int screenWidth;
    //屏幕高度
    private int screenHeight;
    //记录移动的最后的位置
    private int lastX;
    private int lastY;
    //设置变量，来解决OnTouch和OnClick事件的click事件的冲突
    private boolean isclick;

    private MyBind mMyBind = new MyBind ();

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return mMyBind;
    }

    public class MyBind extends Binder {
        public void start(View view, final Context context) {

            DisplayMetrics dm = getResources ().getDisplayMetrics ();//获取显示屏属性
            screenWidth = dm.widthPixels;
            screenHeight = dm.heightPixels;

            view.setOnTouchListener (new View.OnTouchListener () {
                @Override
                public boolean onTouch(View view, MotionEvent motionEvent) {
                    int ea = motionEvent.getAction ();
                    switch (ea) {
                        case MotionEvent.ACTION_DOWN:
                            isclick = false;//当按下的时候设置isclick为false。
                            lastX = (int) motionEvent.getRawX ();
                            lastY = (int) motionEvent.getRawY ();//按钮初始的横纵坐标
                            break;
                        case MotionEvent.ACTION_MOVE:
                            isclick = true;//当按钮被移动的时候设置isclick为true，避免与click事件冲突
                            int dx = (int) motionEvent.getRawX () - lastX;
                            int dy = (int) motionEvent.getRawY () - lastY;//按钮被移动的距离
                            int l = view.getLeft () + dx;
                            int b = view.getBottom () + dy;
                            int r = view.getRight () + dx;
                            int t = view.getTop () + dy;
                            if (l < 0) {//处理按钮被移动到上下左右四个边缘时的情况，决定着按钮不会被移动到屏幕外边去
                                l = 0;
                                r = l + view.getWidth ();
                            }
                            if (t < 0) {
                                t = 0;
                                b = t + view.getHeight ();
                            }
                            if (r > screenWidth) {
                                r = screenWidth;
                                l = r - view.getWidth ();
                            }
                            if (b > screenHeight) {
                                b = screenHeight;
                                t = b - view.getHeight ();
                            }
                            view.layout (l, t, r, b);
                            lastX = (int) motionEvent.getRawX ();
                            lastY = (int) motionEvent.getRawY ();
                            view.postInvalidate ();
                            break;
                        default:
                            break;
                    }
                    //如果按钮没有移动，则返回false，触发与按钮绑定的click事件
                    return isclick;
                }
            });

            view.setOnClickListener (new View.OnClickListener () {
                @Override
                public void onClick(View view) {
                    if (!isclick) {
                        Intent intent = new Intent (context, Main3Activity.class);
                        intent.addFlags (Intent.FLAG_ACTIVITY_NEW_TASK);
                        getApplication ().startActivity (intent);
                    }
                }
            });
        }
    }
}

package com.example.dell.disktopfloatingviewtest.service;


import android.annotation.SuppressLint;
import android.app.Service;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.Build;
import android.os.IBinder;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.example.dell.disktopfloatingviewtest.activity.Main3Activity;
import com.example.dell.disktopfloatingviewtest.R;

public class FloatViewService extends Service {

    private static final String TAG = "FloatViewService";
    //定义浮动窗口布局
    private LinearLayout mFloatLayout;
    private WindowManager.LayoutParams wmParams;
    //创建浮动窗口设置布局参数的对象
    private WindowManager mWindowManager;

    private ImageButton mFloatView;
    //屏幕宽度
    private int screenWidth;
    //屏幕高度
    private int screenHeight;
    //记录移动的最后的位置
    private int lastX;
    private int lastY;
    //设置变量，来解决OnTouch和OnClick事件的click事件的冲突
    private boolean isclick;

    @Override
    public void onCreate() {
        super.onCreate ();
        Log.i (TAG, "onCreate");
        DisplayMetrics dm = getResources ().getDisplayMetrics ();//获取显示屏属性
        screenWidth = dm.widthPixels;
        screenHeight = dm.heightPixels;
        createFloatView ();
    }

    private void createFloatView() {
        wmParams = new WindowManager.LayoutParams ();
        //通过getApplication获取的是WindowManagerImpl.CompatModeWrapper
        mWindowManager = (WindowManager) getApplication ().getSystemService (getApplication ().WINDOW_SERVICE);
        //设置window type 为了适配Android8.0以上的系统，需要进行判断
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            wmParams.type = WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY;
        } else {
            wmParams.type = WindowManager.LayoutParams.TYPE_SYSTEM_ALERT;
        }
        //设置图片格式，效果为背景透明
        wmParams.format = PixelFormat.RGBA_8888;
        //设置浮动窗口不可聚焦（实现操作除浮动窗口外的其他可见窗口的操作）
        wmParams.flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;
        //调整悬浮窗显示的停靠位置为左侧置顶
        wmParams.gravity = Gravity.BOTTOM | Gravity.RIGHT;

        //设置悬浮窗口长宽数据
        wmParams.width = WindowManager.LayoutParams.WRAP_CONTENT;
        wmParams.height = WindowManager.LayoutParams.WRAP_CONTENT;

        LayoutInflater inflater = LayoutInflater.from (getApplication ());
        //获取浮动窗口视图所在布局
        mFloatLayout = (LinearLayout) inflater.inflate (R.layout.floatingview, null);
        //添加mFloatLayout
        mWindowManager.addView (mFloatLayout, wmParams);
        //浮动窗口按钮
        mFloatView = (ImageButton) mFloatLayout.findViewById (R.id.floating_btn);
        Glide.with (this).load (R.drawable.btn_bg).asBitmap ().into (mFloatView);
        mFloatLayout.measure (View.MeasureSpec.makeMeasureSpec (0,
                View.MeasureSpec.UNSPECIFIED), View.MeasureSpec
                .makeMeasureSpec (0, View.MeasureSpec.UNSPECIFIED));
        //设置监听浮动窗口的触摸移动
        mFloatView.setOnTouchListener (new View.OnTouchListener () {

            @SuppressLint("ClickableViewAccessibility")
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                switch (motionEvent.getAction ()) {
                    case MotionEvent.ACTION_DOWN:
                        isclick = false;
                        lastX = (int) motionEvent.getRawX ();
                        lastY = (int) motionEvent.getRawY ();//按钮初始的横纵坐标
                        break;
                    case MotionEvent.ACTION_MOVE:
                        isclick = true;//当按钮被移动的时候设置isclick为true，避免与click事件冲突
                        int dx = -(int) motionEvent.getRawX () + lastX;
                        int dy = -(int) motionEvent.getRawY () + lastY;//按钮被移动的距离
                        wmParams.x = wmParams.x + dx;
                        wmParams.y = wmParams.y + dy;
                        if(wmParams.y > screenHeight-495) {
                            wmParams.y = screenHeight-495;
                        }
                        mWindowManager.updateViewLayout (mFloatLayout, wmParams);
//                        Log.d (TAG, "onTouch: " + wmParams.x + "    " + wmParams.y + "   " + screenHeight);
                        lastX = (int) motionEvent.getRawX ();
                        lastY = (int) motionEvent.getRawY ();
                        return true;
                    case MotionEvent.ACTION_UP:
                        return isclick;
                    default:
                        break;
                }
                return false;
            }
        });

        mFloatView.setOnClickListener (new View.OnClickListener () {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent (getApplicationContext (), Main3Activity.class);
                Log.i (TAG, String.valueOf (getApplicationContext ()));
                intent.addFlags (Intent.FLAG_ACTIVITY_NEW_TASK);
                getApplication ().startActivity (intent);
            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy ();
        if (mFloatLayout != null) {
            //移除悬浮窗口
            mWindowManager.removeView (mFloatLayout);
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}

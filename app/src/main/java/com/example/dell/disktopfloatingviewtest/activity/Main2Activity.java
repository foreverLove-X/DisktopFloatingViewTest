package com.example.dell.disktopfloatingviewtest.activity;

import android.content.Intent;
import android.content.ServiceConnection;
import android.graphics.Color;
import android.os.Build;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import com.example.dell.disktopfloatingviewtest.db.Data;
import com.example.dell.disktopfloatingviewtest.R;
import com.example.dell.disktopfloatingviewtest.adapter.NewsAdapter;
import com.example.dell.disktopfloatingviewtest.service.FloatViewService;
import com.example.dell.disktopfloatingviewtest.service.Myservice;

import java.util.ArrayList;
import java.util.List;

public class Main2Activity extends AppCompatActivity {
    private ListView mListView;
    private List<Data> mDataList;
    private NewsAdapter mNewsAdapter;
    private ImageButton mButton;
    private ImageButton back;
    private ServiceConnection conn;
    private Myservice mMyservice;
    private static final int OVERLAY_PERMISSION_REQ_CODE = 100;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_main2);
        //让背景与状态栏切合
        if (Build.VERSION.SDK_INT >= 21) {
            View decorView = getWindow ().getDecorView ();
            decorView.setSystemUiVisibility (
                    View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
            );
            getWindow ().setStatusBarColor (Color.TRANSPARENT);
        }
        //adnroid6.0以上系统需要通过开启系统设置提醒用户开启权限
        if (Build.VERSION.SDK_INT >= 23) {
            if (Settings.canDrawOverlays (this)) {
                //有悬浮窗权限开启服务绑定 绑定权限
                Intent intent = new Intent (this, FloatViewService.class);
                startService (intent);

            } else {
                //没有悬浮窗权限m,去开启悬浮窗权限
                try {
                    Intent intent = new Intent (Settings.ACTION_MANAGE_OVERLAY_PERMISSION);
                    //开启之后执行回调
                    startActivityForResult (intent, OVERLAY_PERMISSION_REQ_CODE);
                } catch (Exception e) {
                    e.printStackTrace ();
                }

            }
        } else {
            //默认有悬浮窗权限  但是 华为, 小米,oppo等手机会有自己的一套Android6.0以下  会有自己的一套悬浮窗权限管理 也需要做适配
            Intent intent = new Intent (this, FloatViewService.class);
            startService (intent);
        }

        initViews ();

        //        conn = new ServiceConnection () {
        //            @Override
        //            public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
        //                Myservice.MyBind myBind = (Myservice.MyBind) iBinder;
        //                myBind.start (mButton, Main2Activity.this);
        //            }
        //
        //            @Override
        //            public void onServiceDisconnected(ComponentName componentName) {
        //                mMyservice = null;
        //            }
        //        };
        //        Intent intent = new Intent (this, Myservice.class);
        //        startService (intent);
        //        bindService (intent, conn, BIND_AUTO_CREATE);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == OVERLAY_PERMISSION_REQ_CODE) {
            if (Build.VERSION.SDK_INT >= 23) {
                if (!Settings.canDrawOverlays (this)) {
                    Toast.makeText (this, "权限授予失败，无法开启悬浮窗", Toast.LENGTH_SHORT).show ();
                } else {
                    Toast.makeText (this, "权限授予成功！", Toast.LENGTH_SHORT).show ();
                    //有悬浮窗权限开启服务绑定 绑定权限
                    Intent intent = new Intent (this, FloatViewService.class);
                    startService (intent);
                }
            }
        }
    }

    /*
     * 初始化各个控件
     * */
    private void initViews() {
        mListView = findViewById (R.id.lists);
        initdatas ();
        mNewsAdapter = new NewsAdapter (this, mDataList);
        mListView.setAdapter (mNewsAdapter);
        mButton = findViewById (R.id.floating_btn);
        back = findViewById (R.id.backs);
        back.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick(View view) {
                finish ();
            }
        });
    }

    /*
     * 初始化ListView控件的数据
     * */
    private void initdatas() {
        mDataList = new ArrayList<> ();
        for (int i = 0; i < 4; i++) {
            if (i == 3) {
                Data data = new Data (R.drawable.img, R.string.news_tit2, R.string.news_txt2, "2018-06-0" + (9 - i));
                mDataList.add (data);
            } else {
                Data data = new Data (R.drawable.user_img, R.string.news_tit1, R.string.news_txt1, "2018-06-0" + (9 - i));
                mDataList.add (data);
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy ();
        //        Log.d ("LIU", "onDestroy: " + "alkwdjawlkawlk");
        //        unbindService (conn);
    }

    @Override
    protected void onStart() {
        Intent intent = new Intent (this, FloatViewService.class);
        startService (intent);
        super.onStart ();
    }

    @Override
    protected void onStop() {
        Intent intent = new Intent (this, FloatViewService.class);
        stopService (intent);
        super.onStop ();
    }
}

package com.example.dell.disktopfloatingviewtest.activity;

import android.graphics.Color;
import android.os.Build;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.TextView;

import com.example.dell.disktopfloatingviewtest.R;
import com.example.dell.disktopfloatingviewtest.adapter.FragmentAdapter;
import com.example.dell.disktopfloatingviewtest.adapter.NewsAdapter;
import com.example.dell.disktopfloatingviewtest.fragments.NegativeFragment;
import com.example.dell.disktopfloatingviewtest.fragments.PositiveFragment;

import java.util.ArrayList;
import java.util.List;

public class Main3Activity extends AppCompatActivity implements View.OnClickListener {
    private FragmentAdapter mFragmentAdapter;
    private TextView[] tabs;
    private ViewPager mViewPager;
    private TextView positive_item, negative_item, line, dels;
    private NegativeFragment mNegativeFragment;
    private PositiveFragment mPositiveFragment;
    private List<Fragment> fragmentList = new ArrayList<Fragment> ();

    //偏移量（手机屏幕宽度 / 选项卡总数 - 选项卡长度） / 2
    private int offset = 0;
    //下划线宽度
    private int lineWidth;
    //当前选项卡的位置
    private int current_index = 0;
    private int current_color;
    //选项卡总数
    private static final int TAB_COUNT = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_main3);

        //让背景与状态栏切合
        if (Build.VERSION.SDK_INT >= 21) {
            View decorView = getWindow ().getDecorView ();
            decorView.setSystemUiVisibility (
                    View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
            );
            getWindow ().setStatusBarColor (Color.TRANSPARENT);
        }
        initViews ();
        initline ();
        //为ViewPager加载adpter
        mFragmentAdapter = new FragmentAdapter (this.getSupportFragmentManager (), fragmentList);
        //ViewPager的缓存为2帧
        mViewPager.setOffscreenPageLimit (2);
        //初始设置ViewPager选中第一帧
        mViewPager.setCurrentItem (0);
        mViewPager.setAdapter (mFragmentAdapter);
        positive_item.setTextColor (Color.parseColor ("#2cf1f7"));

        //ViewPager的监听事件
        mViewPager.addOnPageChangeListener (new ViewPager.OnPageChangeListener () {

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                //导航栏移动，下划线也移动
                lineSlide (position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    /*
     * 初始化各个控件
     * */
    private void initViews() {
        positive_item = findViewById (R.id.positive);
        negative_item = findViewById (R.id.negative);
        tabs = new TextView[]{positive_item,negative_item};
        //为导航栏添加点击事件
        positive_item.setOnClickListener (this);
        negative_item.setOnClickListener (this);
        //初始化ViewPager
        mViewPager = findViewById (R.id.mainViewPager);
        //初始化fragment
        mPositiveFragment = new PositiveFragment ();
        mNegativeFragment = new NegativeFragment ();
        //为FragmentList添加数据
        fragmentList.add (mPositiveFragment);
        fragmentList.add (mNegativeFragment);

        dels = findViewById (R.id.dels);
        dels.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick(View view) {
                finish ();
            }
        });
    }

    /**
     * 初始化底部下划线
     */
    private void initline() {
        line = (TextView) findViewById (R.id.line);
        lineWidth = line.getWidth ();
        // Android提供的DisplayMetrics可以很方便的获取屏幕分辨率
        DisplayMetrics dm = new DisplayMetrics ();
        getWindowManager ().getDefaultDisplay ().getMetrics (dm);
        int screenW = dm.widthPixels; // 获取分辨率宽度
        offset = (screenW / TAB_COUNT - lineWidth) / 2;  // 计算偏移值
        line.setWidth (offset * 2);
    }

    /*
     * 导航栏切换，下划线滑动
     * */
    private void lineSlide(int position) {
        // 下划线开始移动前的位置
        int one = offset * 2 + lineWidth;
        float fromX = one * current_index;
        // 下划线移动完毕后的位置
        float toX = one * position;
        Animation animation = new TranslateAnimation (fromX, toX, 0, 0);
        //添加动画
        line.startAnimation (animation);
        animation.setFillAfter (true);
        animation.setDuration (500);
        //改变字体颜色

        current_color = tabs[position].getCurrentTextColor ();
        tabs[position].setTextColor (Color.parseColor ("#4cd3ef"));
        tabs[current_index].setTextColor (current_color);
        current_index = position;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId ()) {
            case R.id.positive:
                mViewPager.setCurrentItem (0, true);
                break;
            case R.id.negative:
                mViewPager.setCurrentItem (1, true);
                break;
        }
    }
}

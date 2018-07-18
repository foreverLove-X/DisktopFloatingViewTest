package com.example.dell.disktopfloatingviewtest.activity;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.dell.disktopfloatingviewtest.R;

public class MainActivity extends AppCompatActivity {
    private int time = 10;
    private TextView timeText, jumpBtn;
    private boolean t = true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_main);
        initview ();
        new Thread (new Runnable () {
            @Override
            public void run() {
                while (t) {
                    try {
                        Thread.sleep (1000);
                        Message message = new Message ();
                        message.what = 1;
                        mHandler.sendMessage (message);
                    } catch (InterruptedException e) {
                        e.printStackTrace ();
                    }
                }
            }
        }).start ();

        jumpBtn.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent (MainActivity.this,Main2Activity.class);
                startActivity (intent);
                t=false;
            }
        });

    }

    private void initview() {
        timeText = findViewById (R.id.time);
        jumpBtn = findViewById (R.id.jump);
    }

    Handler mHandler = new Handler () {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    time--;
                    timeText.setText (time + "秒");
                    if(time == 0) {
                        t = false;
                        Intent intent = new Intent (MainActivity.this,Main2Activity.class);
                        startActivity (intent);
                    }
            }
            super.handleMessage (msg);
        }
    };
}

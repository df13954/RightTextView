package com.base.uitextview;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    private RightTextView mHotView5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RightTextView hotView = findViewById(R.id.hv_view);
        hotView.setContent("代码set测试文字");
        hotView.setColor(Color.BLUE);
        //px 自己转dp
        hotView.setSize(90);
        hotView.setDefRes(R.drawable.ic_test);


        mHotView5 = findViewById(R.id.hv_view5);
        mHotView5.setContent("代码set代码set测试文字sdfasdfasdfadsfasdfadsfasdfasdfasdfsdfasdfasdfadsfasdfasdfasdfasdfdsf");
        mHotView5.setColor(Color.BLUE);
        //px 自己转dp
        mHotView5.setSize(90);
        mHotView5.setDefRes(R.drawable.ic_test);
    }

    public void remove(View view) {
        //点击按钮,移除右边的图片资源
        mHotView5.setDefRes(-1);
    }
}

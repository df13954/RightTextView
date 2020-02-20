# RightTextView
实现文字右边...+图片效果 

![image](https://github.com/ff-frida/RightTextView/blob/master/app/android-cap22.png)
![image](https://github.com/ff-frida/RightTextView/blob/master/android-cap.png)


```
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

```

动态移除图片
```
//点击按钮,移除右边的图片资源
mHotView5.setDefRes(-1);
//mHotView5.removeRes();
```

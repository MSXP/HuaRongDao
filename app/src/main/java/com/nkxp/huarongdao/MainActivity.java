package com.nkxp.huarongdao;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    public static final String EXTRA_MESSAGE = "com.nkxp.huarongdao.extra.MESSAGE";

    public Button Qz[] = new Button[10];
    public int BG[][] = new int[5][4];
    private Chronometer timer;
    public TextView showInfo;
    public TextView showStep;
    public String level_text;
    public float horizontalWidth, averageWidth;
    public float x1, x2, y1, y2, mx,my;
    public int Step = 0;
    public int type; // 1、兵  2、四种竖直英雄  3、关羽  4、曹操
    public int row, col;

    @Override
    @SuppressLint("ClickableViewAccessibility")
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Qz[0] = this.findViewById(R.id.Qz1);
        Qz[1] = this.findViewById(R.id.Qz2);
        Qz[2] = this.findViewById(R.id.Qz3);
        Qz[3] = this.findViewById(R.id.Qz4);
        Qz[4] = this.findViewById(R.id.Qz5);
        Qz[5] = this.findViewById(R.id.Qz6);
        Qz[6] = this.findViewById(R.id.Qz7);
        Qz[7] = this.findViewById(R.id.Qz8);
        Qz[8] = this.findViewById(R.id.Qz9);
        Qz[9] = this.findViewById(R.id.Qz10);
        showInfo = this.findViewById(R.id.ShowInfo);
        showStep = this.findViewById(R.id.showStep);

        for (int i = 0; i < 10; i++) {
            Qz[i].setOnTouchListener(new mTouch());
        }
        for (int i = 0; i < 5; i++){
            for (int j = 0; j < 4; j++){
                BG[i][j] = 1;
            }
        }
        Intent intent = getIntent();
        level_text = intent.getStringExtra(EXTRA_MESSAGE);

        showInfo.post(new Runnable() {
            @Override
            public void run() {
                showInfo.setText(String.format(getResources().getString(R.string.welcome), level_text));
                horizontalWidth = showInfo.getWidth();
                averageWidth = horizontalWidth/4;
                switch (level_text) {
                    case "横刀立马":
                        BG[4][1] = 0;
                        BG[4][2] = 0;
                        init();
                        break;
                    case "齐头并进":
                        BG[4][1] = 0;
                        BG[4][2] = 0;
                        init2();
                        break;
                    case "兵分三路":
                        BG[4][1] = 0;
                        BG[4][2] = 0;
                        init3();
                        break;
                    case "屯兵东路":
                        BG[4][2] = 0;
                        BG[4][3] = 0;
                        init4();
                        break;
                    case "壁垒森严":
                        BG[0][0] = 0;
                        BG[0][3] = 0;
                        init5();
                        break;
                    case "不惑之后":
                        BG[0][0] = 0;
                        BG[0][1] = 0;
                        init6();
                        break;
                }
            }
        });

        //获得计时器对象
        timer = (Chronometer)this.findViewById(R.id.chronometer);
        // 将计时器清零
        timer.setBase(SystemClock.elapsedRealtime());
        //开始计时
        timer.start();
    }

    //重置游戏界面
    public void onReset(View v) {
        Intent intent=new Intent(this, MainActivity.class);
        intent.putExtra(EXTRA_MESSAGE, level_text);
        startActivity(intent);
        finish();//关闭自己
        overridePendingTransition(0, 0);
    }

    //返回选择关卡页面
    public void backToSelect(){
        Intent intent=new Intent(this, SelectLevelActivity.class);
        startActivity(intent);
        finish();//关闭自己
    }

    //放弃本关游戏
    public void onQuit(View v) {
        //定义一个新的对话框对象
        AlertDialog.Builder alertdialogbuilder=new AlertDialog.Builder(this);
        //设置对话框提示内容
        alertdialogbuilder.setMessage("确定放弃吗？");
        //定义对话框2个按钮标题及接受事件的函数
        alertdialogbuilder.setPositiveButton("确定",click1);
        alertdialogbuilder.setNegativeButton("取消",click2);
        //创建并显示对话框
        AlertDialog alertdialog1 = alertdialogbuilder.create();
        alertdialog1.show();
    }
    private DialogInterface.OnClickListener click1=new DialogInterface.OnClickListener() {
        //使用该标记是为了增强程序在编译时候的检查，如果该方法并不是一个覆盖父类的方法，在编译时编译器就会报告错误。
        @Override
        public void onClick(DialogInterface arg0,int arg1) {
            //当按钮click1被按下时返回选择关卡页面
            backToSelect();
        }
    };
    private DialogInterface.OnClickListener click2=new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface arg0,int arg1) {
            //当按钮click2被按下时则取消操作
            arg0.cancel();
        }
    };

    //过关后，进入胜利页面
    public void whenWin(){
        Intent intent = new Intent(this, WinActivity.class);
        intent.putExtra("level_name",level_text);
        intent.putExtra("step",showStep.getText());
        intent.putExtra("time",timer.getText());
        startActivity(intent);
        finish();//关闭自己
    }

    //操作的时候的处理逻辑
    public class mTouch implements View.OnTouchListener {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            System.out.println("horizontalWidth"+horizontalWidth);
            System.out.println("getWidth"+v.getWidth());
            System.out.println("getHeight"+v.getHeight());
            if (v.getWidth() == v.getHeight()) {
                if (v.getHeight() > averageWidth)
                    type = 4;
                else
                    type = 1;
            } else {
                if (v.getHeight() > v.getWidth()){
                    type = 2;
                }
                else{
                    type = 3;
                }
            }
            System.out.println("getX:"+v.getX());
            System.out.println("getY:"+v.getY());
            row = (int) (v.getY() / averageWidth);
            col = (int) (v.getX() / averageWidth);
            System.out.println("row:"+row);
            System.out.println("col:"+col);
            //继承了Activity的onTouchEvent方法，直接监听点击事件
            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                //当手指按下的时候
                x1 = event.getRawX();
                y1 = event.getRawY();
                System.out.println("x1:"+x1);
                System.out.println("y1:"+y1);
                if(type == 4 && row==3 && col==1 && BG[4][1] == 1 && BG[4][2] == 1){
                    timer.stop(); //停止计时
                    whenWin();
                }
            }
            if (event.getAction() == MotionEvent.ACTION_MOVE) {
                //当手指滑动的时候
                mx = event.getRawX();
                my = event.getRawY();
            }
            if (event.getAction() == MotionEvent.ACTION_UP) {
                //当手指离开的时候
                x2 = mx;
                y2 = my;
                System.out.println("x2:"+x2);
                System.out.println("y2:"+y2);
                // 向上滑
                if ((int)((y2 - y1)/averageWidth) == -1) {
                    switch (type) {
                        case 1:
                            if (row > 0 && BG[row - 1][col] == 0) {
                                setPosition(v, row-1, col);
                                BG[row][col] = 0;
                                BG[row-1][col] = 1;
                                Step++;
                                showStep.setText(String.format(getResources().getString(R.string.step_cost), Step));
                            }
                            v.performClick();
                            break;
                        case 2:
                            if (row > 0 && BG[row - 1][col] == 0) {
                                setPosition(v, row-1, col);
                                BG[row - 1][col] = 1;
                                BG[row + 1][col] = 0;
                                Step++;
                                showStep.setText(String.format(getResources().getString(R.string.step_cost), Step));
                            }
                            v.performClick();
                            break;
                        case 3:
                            if (row > 0 && BG[row - 1][col] == 0 && BG[row - 1][col + 1] == 0) {
                                setPosition(v, row - 1, col);
                                BG[row - 1][col] = 1;
                                BG[row - 1][col + 1] = 1;
                                BG[row][col] = 0;
                                BG[row][col + 1] = 0;
                                Step++;
                                showStep.setText(String.format(getResources().getString(R.string.step_cost), Step));
                            }
                            v.performClick();
                            break;
                        case 4:
                            if (row > 0 && BG[row - 1][col] == 0 && BG[row - 1][col + 1] == 0) {
                                setPosition(v, row - 1, col);
                                BG[row - 1][col] = 1;
                                BG[row - 1][col + 1] = 1;
                                BG[row + 1][col] = 0;
                                BG[row + 1][col + 1] = 0;
                                Step++;
                                showStep.setText(String.format(getResources().getString(R.string.step_cost), Step));
                            }
                            v.performClick();
                            break;
                    }
                } else if ((int)((y2 - y1)/averageWidth) == 1) {  //向下滑
                    switch (type) {
                        case 1:
                            if (row < 4 && BG[row + 1][col] == 0) {
                                setPosition(v, row + 1, col);
                                BG[row + 1][col] = 1;
                                BG[row][col] = 0;
                                Step++;
                                showStep.setText(String.format(getResources().getString(R.string.step_cost), Step));
                            }
                            v.performClick();
                            break;
                        case 2:
                            if (row < 3 && BG[row + 2][col] == 0) {
                                setPosition(v, row + 1, col);
                                BG[row + 1][col] = 1;
                                BG[row + 2][col] = 1;
                                BG[row][col] = 0;
                                Step++;
                                showStep.setText(String.format(getResources().getString(R.string.step_cost), Step));
                            }
                            v.performClick();
                            break;
                        case 3:
                            if (row < 4 && BG[row + 1][col] == 0 && BG[row + 1][col + 1] == 0) {
                                setPosition(v, row + 1, col);
                                BG[row + 1][col] = 1;
                                BG[row + 1][col + 1] = 1;
                                BG[row][col] = 0;
                                BG[row][col + 1] = 0;
                                Step++;
                                showStep.setText(String.format(getResources().getString(R.string.step_cost), Step));
                            }
                            v.performClick();
                            break;
                        case 4:
                            if (row < 3 && BG[row + 2][col] == 0 && BG[row + 2][col + 1] == 0) {
                                setPosition(v, row + 1, col);
                                BG[row + 2][col] = 1;
                                BG[row + 2][col + 1] = 1;
                                BG[row + 1][col] = 1;
                                BG[row + 1][col + 1] = 1;
                                BG[row][col] = 0;
                                BG[row][col + 1] = 0;
                                Step++;
                                showStep.setText(String.format(getResources().getString(R.string.step_cost), Step));
                            }
                            v.performClick();
                            break;
                    }
                } else if ((int)((x2 - x1)/averageWidth) == -1) { //向左滑
                    switch (type) {
                        case 1:
                            if (col > 0 && BG[row][col - 1] == 0) {
                                setPosition(v, row, col - 1);
                                BG[row][col - 1] = 1;
                                BG[row][col] = 0;
                                Step++;
                                showStep.setText(String.format(getResources().getString(R.string.step_cost), Step));
                            }
                            v.performClick();
                            break;
                        case 2:
                            if (col > 0 && BG[row][col - 1] == 0 && BG[row + 1][col - 1] == 0) {
                                setPosition(v, row, col - 1);
                                BG[row][col - 1] = 1;
                                BG[row + 1][col - 1] = 1;
                                BG[row][col] = 0;
                                BG[row + 1][col] = 0;
                                Step++;
                                showStep.setText(String.format(getResources().getString(R.string.step_cost), Step));
                            }
                            v.performClick();
                            break;
                        case 3:
                            if (col > 0 & BG[row][col - 1] == 0) {
                                setPosition(v, row, col - 1);
                                BG[row][col - 1] = 1;
                                BG[row][col] = 1;
                                BG[row][col + 1] = 0;
                                Step++;
                                showStep.setText(String.format(getResources().getString(R.string.step_cost), Step));
                            }
                            v.performClick();
                            break;
                        case 4:
                            if (col > 0 && BG[row][col - 1] == 0 && BG[row + 1][col - 1] == 0) {
                                setPosition(v, row, col - 1);
                                BG[row][col - 1] = 1;
                                BG[row + 1][col - 1] = 1;
                                BG[row][col] = 1;
                                BG[row+1][col] = 1;
                                BG[row][col+1] = 0;
                                BG[row + 1][col + 1] = 0;
                                Step++;
                                showStep.setText(String.format(getResources().getString(R.string.step_cost), Step));
                            }
                            v.performClick();
                            break;
                    }
                } else if ((int)((x2 - x1)/averageWidth) == 1) {   //向右滑
                    switch (type) {
                        case 1:
                            if (col < 3 && BG[row][col + 1] == 0) {
                                setPosition(v, row, col + 1);
                                BG[row][col + 1] = 1;
                                BG[row][col] = 0;
                                Step++;
                                showStep.setText(String.format(getResources().getString(R.string.step_cost), Step));
                            }
                            v.performClick();
                            break;
                        case 2:
                            if (col < 3 & BG[row][col + 1] == 0 && BG[row + 1][col + 1] == 0) {
                                setPosition(v, row, col + 1);
                                BG[row][col + 1] = 1;
                                BG[row + 1][col + 1] = 1;
                                BG[row][col] = 0;
                                BG[row + 1][col] = 0;
                                Step++;
                                showStep.setText(String.format(getResources().getString(R.string.step_cost), Step));
                            }
                            v.performClick();
                            break;
                        case 3:
                            if (col < 2 && BG[row][col + 2] == 0) {
                                setPosition(v, row, col + 1);
                                BG[row][col + 2] = 1;
                                BG[row][col +1] = 1;
                                BG[row][col] = 0;
                                Step++;
                                showStep.setText(String.format(getResources().getString(R.string.step_cost), Step));
                            }
                            v.performClick();
                            break;
                        case 4:
                            if (row == 3 && col == 1) {
                                showInfo.setText(String.format(getResources().getString(R.string.step_sum), Step));
                            }
                            if (col < 2 && BG[row][col + 2] == 0 && BG[row + 1][col + 2] == 0) {
                                setPosition(v, row, col + 1);
                                BG[row][col + 2] = 1;
                                BG[row + 1][col + 2] = 1;
                                BG[row][col + 1] = 1;
                                BG[row+1][col + 1] = 1;
                                BG[row][col] = 0;
                                BG[row + 1][col] = 0;
                                Step++;
                                showStep.setText(String.format(getResources().getString(R.string.step_cost), Step));
                            }
                            v.performClick();
                            break;
                    }
                }
            }
            v.performClick();
            return true;
        }
    }

    //根据手机分辨率转成像素
    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    //设置英雄的尺寸
    void setSize(Button btn, int w, int h) {
        btn.setWidth(w * dip2px(getApplicationContext(), horizontalWidth / 4));
        btn.setHeight(h * dip2px(getApplicationContext(), horizontalWidth / 4));
    }

    //设置英雄的初始位置
    void setPosition(View v, int r, int c) {
        v.setX(c * horizontalWidth / 4f);
        v.setY(r * horizontalWidth / 4f);
    }

    //初始化：横刀立马
    void init() {
        setSize(Qz[0], 1, 1);
        setPosition(Qz[0], 4, 0);
        setSize(Qz[1], 1, 1);
        setPosition(Qz[1], 4, 3);
        setSize(Qz[2], 1, 1);
        setPosition(Qz[2], 3, 1);
        setSize(Qz[3], 1, 1);
        setPosition(Qz[3], 3, 2);
        setSize(Qz[4], 1, 2);
        setPosition(Qz[4], 0, 0);
        setSize(Qz[5], 1, 2);
        setPosition(Qz[5], 0, 3);
        setSize(Qz[6], 1, 2);
        setPosition(Qz[6], 2, 3);
        setSize(Qz[7], 1, 2);
        setPosition(Qz[7], 2, 0);
        setSize(Qz[8], 2, 1);
        setPosition(Qz[8], 2, 1);
        setSize(Qz[9], 2, 2);
        setPosition(Qz[9], 0, 1);
    }

    //初始化：齐头并进
    void init2() {
        setSize(Qz[0], 1, 1);
        setPosition(Qz[0], 2, 0);
        setSize(Qz[1], 1, 1);
        setPosition(Qz[1], 2, 1);
        setSize(Qz[2], 1, 1);
        setPosition(Qz[2], 2, 2);
        setSize(Qz[3], 1, 1);
        setPosition(Qz[3], 2, 3);
        setSize(Qz[4], 1, 2);
        setPosition(Qz[4], 0, 0);
        setSize(Qz[5], 1, 2);
        setPosition(Qz[5], 0, 3);
        setSize(Qz[6], 1, 2);
        setPosition(Qz[6], 3, 3);
        setSize(Qz[7], 1, 2);
        setPosition(Qz[7], 3, 0);
        setSize(Qz[8], 2, 1);
        setPosition(Qz[8], 3, 1);
        setSize(Qz[9], 2, 2);
        setPosition(Qz[9], 0, 1);
    }

    //初始化：兵分三路
    void init3() {
        setSize(Qz[0], 1, 1);
        setPosition(Qz[0], 3, 1);
        setSize(Qz[1], 1, 1);
        setPosition(Qz[1], 3, 2);
        setSize(Qz[2], 1, 1);
        setPosition(Qz[2], 0, 0);
        setSize(Qz[3], 1, 1);
        setPosition(Qz[3], 0, 3);
        setSize(Qz[4], 1, 2);
        setPosition(Qz[4], 1, 0);
        setSize(Qz[5], 1, 2);
        setPosition(Qz[5], 1, 3);
        setSize(Qz[6], 1, 2);
        setPosition(Qz[6], 3, 3);
        setSize(Qz[7], 1, 2);
        setPosition(Qz[7], 3, 0);
        setSize(Qz[8], 2, 1);
        setPosition(Qz[8], 2, 1);
        setSize(Qz[9], 2, 2);
        setPosition(Qz[9], 0, 1);
    }

    //初始化：屯兵东路
    void init4() {
        setSize(Qz[0], 1, 1);
        setPosition(Qz[0], 2, 2);
        setSize(Qz[1], 1, 1);
        setPosition(Qz[1], 2, 3);
        setSize(Qz[2], 1, 1);
        setPosition(Qz[2], 3, 2);
        setSize(Qz[3], 1, 1);
        setPosition(Qz[3], 3, 3);
        setSize(Qz[4], 1, 2);
        setPosition(Qz[4], 0, 2);
        setSize(Qz[5], 1, 2);
        setPosition(Qz[5], 0, 3);
        setSize(Qz[6], 1, 2);
        setPosition(Qz[6], 3, 1);
        setSize(Qz[7], 1, 2);
        setPosition(Qz[7], 3, 0);
        setSize(Qz[8], 2, 1);
        setPosition(Qz[8], 2, 0);
        setSize(Qz[9], 2, 2);
        setPosition(Qz[9], 0, 0);
    }

    //初始化：壁垒森严
    void init5() {
        setSize(Qz[0], 1, 1);
        setPosition(Qz[0], 1, 0);
        setSize(Qz[1], 1, 1);
        setPosition(Qz[1], 2, 0);
        setSize(Qz[2], 1, 1);
        setPosition(Qz[2], 1, 3);
        setSize(Qz[3], 1, 1);
        setPosition(Qz[3], 2, 3);
        setSize(Qz[4], 1, 2);
        setPosition(Qz[4], 3, 0);
        setSize(Qz[5], 1, 2);
        setPosition(Qz[5], 3, 1);
        setSize(Qz[6], 1, 2);
        setPosition(Qz[6], 3, 3);
        setSize(Qz[7], 1, 2);
        setPosition(Qz[7], 3, 2);
        setSize(Qz[8], 2, 1);
        setPosition(Qz[8], 0, 1);
        setSize(Qz[9], 2, 2);
        setPosition(Qz[9], 1, 1);
    }

    //初始化：不惑之后
    void init6() {
        setSize(Qz[0], 1, 1);
        setPosition(Qz[0], 4, 0);
        setSize(Qz[1], 1, 1);
        setPosition(Qz[1], 4, 1);
        setSize(Qz[2], 1, 1);
        setPosition(Qz[2], 4, 2);
        setSize(Qz[3], 1, 1);
        setPosition(Qz[3], 4, 3);
        setSize(Qz[4], 1, 2);
        setPosition(Qz[4], 0, 2);
        setSize(Qz[5], 1, 2);
        setPosition(Qz[5], 0, 3);
        setSize(Qz[6], 1, 2);
        setPosition(Qz[6], 2, 3);
        setSize(Qz[7], 1, 2);
        setPosition(Qz[7], 2, 2);
        setSize(Qz[8], 2, 1);
        setPosition(Qz[8], 1, 0);
        setSize(Qz[9], 2, 2);
        setPosition(Qz[9], 2, 0);
    }
}
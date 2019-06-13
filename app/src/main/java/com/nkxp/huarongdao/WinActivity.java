package com.nkxp.huarongdao;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class WinActivity extends AppCompatActivity {
    private TextView mlevelView;
    private TextView mStepView;
    private TextView mTimeView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_win);
        mlevelView = this.findViewById(R.id.level_name);
        mStepView = this.findViewById(R.id.step_text);
        mTimeView = this.findViewById(R.id.time_text);

        //获取数据
        Intent intent = getIntent();
        String levelName ="【" + intent.getStringExtra("level_name") + "】";
        String steps =intent.getStringExtra("step");
        String time ="用时：" + intent.getStringExtra("time");

        //显示
        mlevelView.setText(levelName);
        mStepView.setText(steps);
        mTimeView.setText(time);
    }

    public void onBack(View view) {
        Intent intent=new Intent(this, FirstActivity.class);
        startActivity(intent);
        finish();//关闭自己
    }

    public void onReselect(View view) {
        Intent intent=new Intent(this, SelectLevelActivity.class);
        startActivity(intent);
        finish();//关闭自己
    }
}

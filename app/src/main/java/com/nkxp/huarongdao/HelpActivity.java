package com.nkxp.huarongdao;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class HelpActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);
    }

    public void onBack(View view) {
        Intent intent=new Intent(this, FirstActivity.class);
        startActivity(intent);
        finish();//关闭自己
    }
}

package com.nkxp.huarongdao;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class SelectLevelActivity extends AppCompatActivity {
    public static final String EXTRA_MESSAGE = "com.nkxp.huarongdao.extra.MESSAGE";
    private Button clickedButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select);
    }

    public void onSelect(View view) {
        clickedButton = (Button)view;
        String level_text = (String)clickedButton.getText();
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra(EXTRA_MESSAGE, level_text);
        startActivity(intent);
        finish();
    }

    public void onBackToFirst(View view) {
        Intent intent=new Intent(this, FirstActivity.class);
        startActivity(intent);
        finish();//关闭自己
    }
}

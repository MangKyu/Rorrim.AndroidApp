package com.rorrim.mang.smartmirror;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ContentActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_main); //해당 아이디에 자신이 만든 레이아웃의 이름을 쓴다
        createAction();
    }

    @Override
    protected void onStart(){
        Intent intent = getIntent();
        setComponent(intent.getStringExtra("status"), intent.getStringExtra("result"));

        super.onStart();
    }

    private void setComponent(String status, String result){
        TextView tv = findViewById(R.id.tv_status);
        tv.setText(status);
        tv = findViewById(R.id.tv_result);
        tv.setText(result);
    }

    private void createAction(){
        Button button = (Button) findViewById(R.id.btn_calander); //해당 버튼을 지정합니다.
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { //버튼이 눌렸을 때
                Intent intent = new Intent(ContentActivity.this, CalanderActivity.class);
                startActivity(intent); //액티비티 이동
            }
        });
    }

}
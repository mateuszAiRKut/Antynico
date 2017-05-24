package com.example.mateusz.antynico;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.os.CountDownTimer;
import android.content.Intent;

public class StartActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        //image = (ImageView) findViewById(R.id.stop);
        new CountDownTimer(2500,1000){
            @Override
            public void onTick(long millisUntilFinished){}

            @Override
            public void onFinish(){
                //setContentView(R.layout.activity_main);
                Intent intent = new Intent(StartActivity.this, LoginActivity.class);
                startActivity(intent);
                StartActivity.this.finish();
            }
        }.start();
    }

    private ImageView image;
}

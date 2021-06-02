package com.example.TravelDay;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;



//스플래쉬 화면
public class SplashActivity extends AppCompatActivity {

    Animation anim_FadeIn;
    TextView textView1,textView2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        textView1 = findViewById(R.id.textView2);
        textView2 = findViewById(R.id.textView3);

        anim_FadeIn = AnimationUtils.loadAnimation(this,R.anim.anim_splash_fadein);
        textView1.startAnimation(anim_FadeIn);
        textView2.startAnimation(anim_FadeIn);

        anim_FadeIn.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                startActivity(new Intent(SplashActivity.this,LoginActivity.class));
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }
    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }
}

package com.example.TravelDay;


import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;


//메인화면 tabLayOut
public class MainActivity extends AppCompatActivity {
    private TabLayout tabLayout;
    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("Travel Day");
        setContentView(R.layout.activity_main);


        tabLayout = (TabLayout) findViewById(R.id.tabLayout);
        tabLayout.addTab(tabLayout.newTab().setText("일정 메모"));
        tabLayout.addTab(tabLayout.newTab().setText("경비 계산"));
        tabLayout.addTab(tabLayout.newTab().setText("구글 맵"));

        viewPager = (ViewPager) findViewById(R.id.viewpager);

        //탭페이저어댑터 생성
        TabPagerAdapter pagerAdapter = new TabPagerAdapter(getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(pagerAdapter);

        //페이지체인지 리스너 설정
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.log_out:
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                Toast.makeText(this,"로그아웃",Toast.LENGTH_SHORT).show();
                startActivity(intent);
                return true;
            case R.id.user_helper:
                Toast.makeText(this, "고객지원", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.user_peedback:
                String id = "ksmwy9029@naver.com";

                ClipboardManager clipboardManager =(ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
                ClipData clipData =ClipData.newPlainText("adress",id);
                clipboardManager.setPrimaryClip(clipData);

                Toast.makeText(this,"이메일 복사가 완료되었습니다.",Toast.LENGTH_SHORT).show();
                return true;
            default:
                return false;
        }
    }
}
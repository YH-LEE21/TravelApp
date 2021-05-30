package com.example.TravelDay;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class AddActivity extends AppCompatActivity {

    CalendarView calendarView;
    EditText editText;
    Button  btnDone1,btnNo1;
    TextView diaryDate;
    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_memo);

        calendarView = findViewById(R.id.calendarview1);
        diaryDate = findViewById(R.id.diaryDate);
        editText = findViewById(R.id.editMemo);
        btnDone1 = findViewById(R.id.btnDone1);
        btnNo1 = findViewById(R.id.btnNo1);

        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                diaryDate.setVisibility(View.VISIBLE);
                editText.setVisibility(View.VISIBLE);
                btnDone1.setVisibility(View.VISIBLE);
                btnNo1.setVisibility(View.VISIBLE);
                diaryDate.setText(String.format("%d/%d/%d",year,month+1,dayOfMonth));
            }
        });

        btnDone1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String str = editText.getText().toString();
                if(str.length()>0){

                    String substr = diaryDate.getText().toString();
                    Intent intent = new Intent();
                    intent.putExtra("main",str);
                    intent.putExtra("sub",substr);
                    setResult(0,intent);

                    finish();
                }
            }
        });
        btnNo1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}

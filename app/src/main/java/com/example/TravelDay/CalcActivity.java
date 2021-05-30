package com.example.TravelDay;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class CalcActivity extends AppCompatActivity {
    CalendarView calendarView2;
    EditText editHome,editetc,editShopping,editfood,editTraffic,editTravel1;
    Button btnDone2,btnNo2;
    TextView diaryDate2;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calc);

        calendarView2 = findViewById(R.id.calendarview2);
        diaryDate2 = findViewById(R.id.diaryDate2);
        editHome = findViewById(R.id.editHome);
        editetc = findViewById(R.id.editetc);
        editShopping = findViewById(R.id.editShopping);
        editTraffic = findViewById(R.id.editTraffic);
        editTravel1 = findViewById(R.id.editTravel1);
        editfood = findViewById(R.id.editfood);

        btnDone2 = findViewById(R.id.btnDone2);
        btnNo2 = findViewById(R.id.btnNo2);

        calendarView2.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                diaryDate2.setVisibility(View.VISIBLE);
                editHome.setVisibility(View.VISIBLE);
                editetc.setVisibility(View.VISIBLE);
                editfood.setVisibility(View.VISIBLE);
                editShopping.setVisibility(View.VISIBLE);
                editTraffic.setVisibility(View.VISIBLE);
                editTravel1.setVisibility(View.VISIBLE);

                btnDone2.setVisibility(View.VISIBLE);
                btnNo2.setVisibility(View.VISIBLE);
                diaryDate2.setText(String.format("%d/%d/%d",year,month+1,dayOfMonth));
            }
        });



        btnDone2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int[] a = {Integer.parseInt(editHome.getText().toString()),Integer.parseInt(editfood.getText().toString()),
                        Integer.parseInt(editTraffic.getText().toString()),Integer.parseInt(editetc.getText().toString()),
                        Integer.parseInt(editTravel1.getText().toString()),Integer.parseInt(editShopping.getText().toString())};
                int sum=0;
                for(int i=0;i<a.length;i++) {
                    sum += a[i];
                }
                String mainstr = Integer.toString(sum);
                String substr = diaryDate2.getText().toString();
                    Intent intent = new Intent();
                    intent.putExtra("Calc",mainstr);
                    intent.putExtra("sub",substr);
                    setResult(0,intent);

                    finish();
                }
        });
        btnNo2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}

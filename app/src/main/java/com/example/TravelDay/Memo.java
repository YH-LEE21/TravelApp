package com.example.TravelDay;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class Memo {
    String maintext;//메모
    String subText;//날짜
    int  isdone; //완료여부
    public Memo(String maintext,String subText,int isdone){
        this.maintext = maintext;
        this.subText = subText;
        this.isdone = isdone;
    }

    public String getMaintext(){
        return maintext;
    }
    public String getSubText() {
        return subText;
    }

    public int getIsdone() {
        return isdone;
    }
    public void setMaintext(String maintext){
        this.maintext=maintext;
    }

    public void setSubText(String subText) {
        this.subText = subText;
    }

    public void setIsdone(int isdone) {
        this.isdone = isdone;
    }
}
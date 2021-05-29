package com.example.TravelDay;

public class Memo {
    String maintext;//메모
    String subText;//날짜
    public Memo(String maintext, String subText){
        this.maintext = maintext;
        this.subText = subText;
    }

    public String getMaintext(){
        return maintext;
    }
    public String getSubText() {
        return subText;
    }

    public void setMaintext(String maintext){
        this.maintext=maintext;
    }

    public void setSubText(String subText) {
        this.subText = subText;
    }

}
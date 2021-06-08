package com.example.TravelDay;

public class Memo {
    int seq;
    String maintext;//메모
    String subText;//날짜

    public Memo(int seq,String maintext,String subText){
        this.seq = seq;
        this.maintext = maintext;
        this.subText = subText;
    }
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

    public int getSeq() {
        return seq;
    }

    public void setSeq(int seq) {
        this.seq = seq;
    }
}
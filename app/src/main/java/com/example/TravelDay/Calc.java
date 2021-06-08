package com.example.TravelDay;

public class Calc {

        int seq;
        String mainCalc;//
        String subText;//날짜

        public Calc(int seq,String mainCalc,String subText){
            this.seq = seq;
            this.mainCalc = mainCalc;
            this.subText = subText;
        }
        public Calc(String mainCalc, String subText){
            this.mainCalc = mainCalc;
            this.subText = subText;
        }

        public String getMainCalc(){
            return mainCalc;
        }
        public String getSubText() {
            return subText;
        }

        public void setMainCalc(String mainCalc){
            this.mainCalc=mainCalc;
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

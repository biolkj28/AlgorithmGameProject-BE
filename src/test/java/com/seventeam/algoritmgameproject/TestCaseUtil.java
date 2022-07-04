package com.seventeam.algoritmgameproject;

import java.util.Random;

public class TestCaseUtil {
    Random rnd = new Random();

    public String randomStr(int len) {
        int num = rnd.nextInt(len);
        StringBuffer buf = new StringBuffer();
        for (int i = 0; i < num; i++) {
            buf.append((char) ((int) (rnd.nextInt(26)) + 97));
        }
        return buf.toString();
    }

    public String randomNumAndStr(int len) {
        StringBuffer buf = new StringBuffer();
        for (int i = 0; i < len; i++) {
            if (rnd.nextBoolean()) {
                buf.append((char) ((int) (rnd.nextInt(26)) + 97));
            } else {
                buf.append((rnd.nextInt(10)));
            }
        }
        return buf.toString();
    }

    public String randomPhoneNumber() {

        StringBuffer common;
        int num = rnd.nextInt(10);
        if(num<5){
            common = new StringBuffer("010");
        }else{
            common = new StringBuffer("02");
        }
        for (int i = 0; i < 8; i++) {
            common.append(rnd.nextInt(10));
        }
        return common.toString();
    }

}



package com.adanac.module.accout.captcha;

import java.util.Random;

/**
 * Created by song on 2016/9/17.
 */
public class RandomGenerator {
    private static String range = "0123456789abcdefghijklmnopqrstuvwxyz";
    public static synchronized String getRandomString(){
        Random random = new Random();
        StringBuffer stringBuffer = new StringBuffer();
        for(int i =0;i<8;i++){
            stringBuffer.append(range.charAt(random.nextInt(range.length())));
        }
        return stringBuffer.toString();
    }
}

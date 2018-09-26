package com.oper.util;

/**
 * @Auther: jindb
 * @Date: 2018/8/18 11:14
 * @Description:
 */
public class MathUtil {

    public static int GetevenNum(double num1,double num2) {
        int s = (int) num1 + (int) (Math.random() * (num2 - num1));
        if (s % 2 == 0) {
            return s;
        } else
            return s +1;
    }
}


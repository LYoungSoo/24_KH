package pkg3.dto;

import java.util.Scanner;

public class LYSCalculator implements Calculator {
    @Override
    public int plus(int a, int b) {
        int result = a + b;
        if (result > MAX_NUM) result = MAX_NUM;
        if (result < MIN_NUM) result = MIN_NUM;
        return result;
    }
    
    @Override
    public int minus(int a, int b) {
        int result = a - b;
        if (result > MAX_NUM) result = MAX_NUM;
        if (result < MIN_NUM) result = MIN_NUM;
        return result;
    }
    
    @Override
    public int multi(int a, int b) {
        int result = a * b;
        if (result > MAX_NUM) result = MAX_NUM;
        if (result < MIN_NUM) result = MIN_NUM;
        return result;
    }
    
    @Override
    public int div(int a, int b) {
        int result = a / b;
        if (result > MAX_NUM) result = MAX_NUM;
        if (result < MIN_NUM) result = MIN_NUM;
        return result;
    }
    
    @Override
    public int mod(int a, int b) {
        int result = a % b;
        if (result > MAX_NUM) result = MAX_NUM;
        if (result < MIN_NUM) result = MIN_NUM;
        return result;
    }
    
    @Override
    public int pow(int a, int b) {
        int result = (int) Math.pow(a, b);
        // for (int i=0; i<b; i++) a = a*a;
        if (result > MAX_NUM) result = MAX_NUM;
        if (result < MIN_NUM) result = MIN_NUM;
        return result;
    }
    
    @Override
    public double areaOfCircle(double r) {
        double result = PI * Math.pow(r, 2);
//        double result = PI * r * r;
        if (result > MAX_NUM) result = MAX_NUM;
        if (result < MIN_NUM) result = MIN_NUM;
        result = Double.parseDouble(String.format("%.5f",result));
        return result;
    }
    
    @Override
    public String toBinary(int num) {
        if (num > MAX_NUM) num = MAX_NUM;
        if (num < MIN_NUM) num = MIN_NUM;
        String result = Integer.toBinaryString(num);
        return result;
    }
    
    @Override
    public String toHexadecimal(int num) {
        if (num > MAX_NUM) num = MAX_NUM;
        if (num < MIN_NUM) num = MIN_NUM;
        String result = Integer.toHexString(num);
        return result;
    }
    
}
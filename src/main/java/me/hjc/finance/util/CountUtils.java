package me.hjc.finance.util;

public class CountUtils {
    private CountUtils(){}

    private static volatile int COUNT = 0;
    private static volatile int TOTAL = 0;

    synchronized static public int getCount() {
        return COUNT;
    }

    synchronized static public void addCount() {
        COUNT++;
    }

    synchronized static public int getTotal() {
        return TOTAL;
    }

    synchronized static public void setTotal(int total) {
        TOTAL = total;
    }
}

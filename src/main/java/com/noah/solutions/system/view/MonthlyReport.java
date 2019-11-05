package com.noah.solutions.system.view;

public class MonthlyReport {
    String month;
    Integer count;

    public MonthlyReport() {
    }

    public MonthlyReport(String month, Integer count) {
        this.month = month;
        this.count = count;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }
}

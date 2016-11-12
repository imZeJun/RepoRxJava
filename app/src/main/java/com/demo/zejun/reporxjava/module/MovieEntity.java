package com.demo.zejun.reporxjava.module;

/**
 * @author lizejun
 * @version 1.0 2016/11/8
 */
public class MovieEntity {

    private int count;
    private int start;
    private int total;
    private Object subjects;
    private String title;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public Object getSubjects() {
        return subjects;
    }

    public void setSubjects(Object subjects) {
        this.subjects = subjects;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}

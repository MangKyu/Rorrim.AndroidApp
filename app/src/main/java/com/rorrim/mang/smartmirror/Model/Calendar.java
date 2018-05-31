package com.rorrim.mang.smartmirror.Model;

import java.io.Serializable;

public class Calendar implements Serializable {
    private String date;
    private String time;
    private String contents;

    public Calendar(){}
    public Calendar(String date, String time, String contents) {
        this.date = date;
        this.time = time;
        this.contents = contents;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getContents() {
        return contents;
    }
    public void setContents(String contents){this.contents = contents;}

    @Override
    public String toString() {
        return "MusicDto{" +
                "date='" + date + '\'' +
                ", time='" + time + '\'' +
                ", contents='" + contents + '\'' +
                '}';
    }
}

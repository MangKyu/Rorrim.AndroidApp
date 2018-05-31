package com.rorrim.mang.smartmirror.Model;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Calendar implements Serializable {
    private String date;
    private String time;
    private String contents;
    private String day;
    private String apm;
    private String location;

    public Calendar() {
    }

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

    public void setContents(String contents) {
        this.contents = contents;
    }

    public void setDay(String date) throws ParseException {
        java.util.Calendar cal = java.util.Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
        Date nDate = dateFormat.parse(date);
        cal.setTime(nDate);

        int dayNum = cal.get(java.util.Calendar.DAY_OF_WEEK);

        switch(dayNum){
            case 1:
                this.day = "SUN";
                break;
            case 2:
                this.day =  "MON";
                break;
            case 3:
                this.day =  "TUE";
                break;
            case 4:
                this.day =  "WED";
                break;
            case 5:
                this.day = "THU";
                break;
            case 6:
                this.day = "FRI";
                break;
            case 7:
                this.day = "SAT";
                break;

        }
   }

   public String getDay(){
        return this.day;
   }
    @Override
    public String toString() {
        return "CalendarDto{" +
                "date='" + date + '\'' +
                ", time='" + time + '\'' +
                ", contents='" + contents + '\'' +
                '}';
    }

    public String getApm() {
        return apm;
    }

    public void setApm(String time) {
        int temp = Integer.parseInt(time.substring(0,1));
        if(temp >= 12) this.apm = "오후";
        else this.apm = "오전";
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
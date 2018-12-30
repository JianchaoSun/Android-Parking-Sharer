package com.example.jianchaosun.parkingspotsharer;

import android.net.Uri;
import android.os.CountDownTimer;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class obj implements Comparable<obj> {
    private int hour;
    private int min;
    private int time;
    private String numberOfParking;
    private String Structure;
    private long expTime;
    private String exp;


    private long timeStamp;
    private String realTime;
    private long cutoff;
    private String uri=null;
    private String bitmap=null;
     Map<String,String>serT;


    public obj(int hour,int min, String num, String stru,String uri,String bitmap){
        //serT = map;
        this.hour = hour;
        this.min = min;
        this.uri=uri;
        this.bitmap = bitmap;
        time = 60*hour+min;
        numberOfParking = num;
        Structure = stru;
        Date date = new Date();
        timeStamp = date.getTime();

        cutoff = timeStamp+ TimeUnit.MILLISECONDS.convert(2, TimeUnit.MINUTES);
        Timestamp realTime1 = new Timestamp(timeStamp);
        realTime = realTime1.toString();

        /*Calendar cal = Calendar.getInstance();
        Date date=cal.getTime();
        DateFormat h = new SimpleDateFormat("H");
        String ch =h.format(date);
        int currH = Integer.parseInt(ch);
        DateFormat m = new SimpleDateFormat("m");
        String cm=m.format(date);
        int currM = Integer.parseInt(cm);
        long timeM = hour*3600000+min*60000;
        long curtimM = currH*3600000+currM*60000;
        if(timeM-curtimM>0) {
            if(min+5<60){
                exp =hour+":"+(min+5);
            }else{
                if(hour+1==24){
                    exp = 0+":"+ (min+5-60);
                }else{
                    exp = (hour+1)+":"+(min+5-60);
                }
            }
        }else{
            if(currM+5<60){
                exp =currH+":"+(currM+5);
            }else{
                if(currH+1==24){
                    exp = 0+":"+ (currM+5-60);
                }else{
                    exp = (currH+1)+":"+(currM+5-60);
                }
            }
        }*/



    }
    public obj(){

    }

    @Override
    public int compareTo(obj o) {
        return o.getTime()-getTime();
    }


    public String getNumberOfParking() {
        return numberOfParking;
    }

    public void setNumberOfParking(String numberOfParking) {
        this.numberOfParking = numberOfParking;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public int getMin() {
        return min;
    }

    public void setMin(int min) {
        this.min = min;
    }

    public int getHour() {
        return hour;
    }

    public void setHour(int hour) {
        this.hour = hour;
    }

    public long getExpTimeTime() {
        return expTime;
    }

    public void setExpTime(long remainTime) {
        this.expTime = remainTime;
    }

    public String getExp() {
        return exp;
    }

    public void setExp(String exp) {
        this.exp = exp;
    }

    public long getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(long timeStamp) {
        this.timeStamp = timeStamp;
    }

    public String getRealTime() {
        return realTime;
    }

    public void setRealTime(String realTime) {
        this.realTime = realTime;
    }

    public long getCutoff() {
        return cutoff;
    }

    public void setCutoff(long cutoff) {
        this.cutoff = cutoff;
    }


    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public String getBitmap() {
        return bitmap;
    }

    public void setBitmap(String bitmap) {
        this.bitmap = bitmap;
    }
}

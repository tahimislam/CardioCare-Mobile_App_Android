package com.example.cardiocare;

public class Record {

    private String passeduser,id;
    private String date;
    private String time;
    private String sys;
    private String dis;
    private String bpm;
    private String cmnt;

    public Record(String passeduser, String id, String date, String time, String sys, String dis, String bpm, String cmnt) {
        this.passeduser = passeduser;
        this.id = id;
        this.date = date;
        this.time = time;
        this.sys = sys;
        this.dis = dis;
        this.bpm = bpm;
        this.cmnt = cmnt;
    }

    public String getPasseduser() {
        return passeduser;
    }

    public void setPasseduser(String passeduser) {
        this.passeduser = passeduser;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getSys() {
        return sys;
    }

    public void setSys(String sys) {
        this.sys = sys;
    }

    public String getDis() {
        return dis;
    }

    public void setDis(String dis) {
        this.dis = dis;
    }

    public String getBpm() {
        return bpm;
    }

    public void setBpm(String bpm) {
        this.bpm = bpm;
    }

    public String getCmnt() {
        return cmnt;
    }

    public void setCmnt(String cmnt) {
        this.cmnt = cmnt;
    }
}

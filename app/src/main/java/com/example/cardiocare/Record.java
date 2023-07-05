package com.example.cardiocare;

public class Record {
    private int systolicPressure,diastolicPressure,heartRate;
    private String timeMeasured,dateMeasured,comment;

    public Record(int systolicPressure,
                  int diastolicPressure,
                  int heartRate,
                  String timeMeasured,
                  String dateMeasured,
                  String comment) {
        this.systolicPressure = systolicPressure;
        this.diastolicPressure = diastolicPressure;
        this.heartRate = heartRate;
        this.timeMeasured = timeMeasured;
        this.dateMeasured = dateMeasured;
        this.comment = comment;
    }
    public Record(Record record){
        systolicPressure = record.getSystolicPressure();
        diastolicPressure = record.getDiastolicPressure();
        heartRate = record.getHeartRate();
        timeMeasured = record.getTimeMeasured();
        dateMeasured = record.getDateMeasured();
        comment = record.getComment();
    }

    public int getSystolicPressure() {
        return systolicPressure;
    }

    public void setSystolicPressure(int systolicPressure) {
        this.systolicPressure = systolicPressure;
    }

    public int getDiastolicPressure() {
        return diastolicPressure;
    }

    public void setDiastolicPressure(int diastolicPressure) {
        this.diastolicPressure = diastolicPressure;
    }

    public int getHeartRate() {
        return heartRate;
    }

    public void setHeartRate(int heartRate) {
        this.heartRate = heartRate;
    }

    public String getTimeMeasured() {
        return timeMeasured;
    }

    public void setTimeMeasured(String timeMeasured) {
        this.timeMeasured = timeMeasured;
    }

    public String getDateMeasured() {
        return dateMeasured;
    }

    public void setDateMeasured(String dateMeasured) {
        this.dateMeasured = dateMeasured;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

}

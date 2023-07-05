package com.example.cardiocare;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class RecordDetailsActivity extends AppCompatActivity {
    TextView sysPressure,diaPressure,heartRate,timeMeasured,dateMeasured,comment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record_details);
        getSupportActionBar().hide();

        sysPressure = findViewById(R.id.activity_record_details_sys_pressure);
        diaPressure = findViewById(R.id.activity_record_details_dia_pressure);
        heartRate = findViewById(R.id.activity_record_details_heart_rate);
        timeMeasured = findViewById(R.id.activity_record_details_time_measured);
        dateMeasured = findViewById(R.id.activity_record_details_date_measured);
        comment= findViewById(R.id.activity_record_details_comment);

        Intent intent = getIntent();
        //getting the record details from recordList
        if(intent.hasExtra("index")) {
            int index = intent.getIntExtra("index", -1);
            if (index > -1 && index < RecordList.getInstance().getCount()) {
                Record record = RecordList.getInstance().getRecord(index);
                sysPressure.setText(String.valueOf(record.getSystolicPressure()) + " mmHg");
                diaPressure.setText(String.valueOf(record.getDiastolicPressure()) + " mmHg");
                heartRate.setText(String.valueOf(record.getHeartRate()) + " bits/min");
                timeMeasured.setText(record.getTimeMeasured());
                dateMeasured.setText(record.getDateMeasured());
                comment.setText(String.valueOf(record.getComment()));
            } else {
                Toast.makeText(this, "Invalid operation request", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
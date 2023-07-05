package com.example.cardiocare;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputLayout;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class InsertRecordActivity extends AppCompatActivity {

    EditText sysPressure,diaPressure,heartRate,timeMeasured,dateMeasured,comment;
    Button saveButton;
    TextInputLayout dateMeasuredLayout,timeMeasuredLayout;
    final Calendar calendar = Calendar.getInstance();
    int hr,min;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert_record);
        getSupportActionBar().hide();

        //connecting widgets
        saveButton = findViewById(R.id.coe_record_action_button);
        sysPressure = findViewById(R.id.coe_record_systolic_edit_text_input);
        diaPressure = findViewById(R.id.coe_record_diastolic_edit_text_input);
        heartRate = findViewById(R.id.coe_record_heart_rate_edit_text_input);
        timeMeasured= findViewById(R.id.coe_record_time_measured_edit_text_input);
        dateMeasured = findViewById(R.id.coe_record_date_edit_text_input);
        comment = findViewById(R.id.coe_record_comment_edit_text_input);
        dateMeasuredLayout  = findViewById(R.id.coe_record_date_edit_text_layout);
        timeMeasuredLayout  = findViewById(R.id.coe_record_time_measured_edit_text_layout);

        Intent intent = getIntent();
        //checking if the activity should in in update or create mode depending on t he availability of intent extra
        if(intent.hasExtra("index")){
            int index = intent.getIntExtra("index",-1);
//            Toast.makeText(this, String.valueOf(index), Toast.LENGTH_SHORT).show();
            RecordList recordList = RecordList.getInstance();
            if(index != -1 && index<recordList.getCount()){
                //load data
                Record record = recordList.getRecord(index);
                sysPressure.setText(String.valueOf(record.getSystolicPressure()));
                diaPressure.setText(String.valueOf(record.getDiastolicPressure()));
                heartRate.setText(String.valueOf(record.getHeartRate()));
                //parsing values of time
                String[] tempTime = record.getTimeMeasured().split(":",2);
                hr = Integer.parseInt(tempTime[0]);
                min = Integer.parseInt(tempTime[1]);
                timeMeasured.setText(record.getTimeMeasured());
                //parsing values of date
                String[] tempDate= record.getDateMeasured().split("-",3);
                calendar.set(Integer.parseInt(tempDate[2]),Integer.parseInt(tempDate[1]),Integer.parseInt(tempDate[0]));
                dateMeasured.setText(record.getDateMeasured());
                comment.setText(String.valueOf(record.getComment()));
            }else{
                Toast.makeText(this, "Invalid operation request", Toast.LENGTH_SHORT).show();
            }
        }

        //listening to change and checking the range of 90-140
        sysPressure.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(charSequence.length()!=0){
                    int temp =  Integer.parseInt(charSequence.toString());
                    if(temp<50 || temp>250){
                        sysPressure.setError("should be in range: 50-250");
                    }
                }

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        //listening to change and checking the range of 60-90
        diaPressure.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(charSequence.toString().trim().length()!=0){
                    int temp =  Integer.parseInt(charSequence.toString());
                    if(temp<50 || temp>150){
                        diaPressure.setError("Should be in range: 50-150");
                    }
                }

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        //initiating date picker
        DatePickerDialog.OnDateSetListener dateSetListener  = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                calendar.set(year,month,day);
                updateDateData();
            }
        };

        //initiating time picker
        TimePickerDialog.OnTimeSetListener timeSetListener = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int _hr, int _min) {
                hr = _hr;
                min = _min;
                updateTimeData();
            }
        };

        dateMeasuredLayout.setEndIconOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(InsertRecordActivity.this,
                        dateSetListener,
                        calendar.get(Calendar.YEAR),
                        calendar.get(Calendar.MONTH),
                        calendar.get(Calendar.DAY_OF_MONTH)
                ).show();
            }
        });
        //validating the custom input of date measured
        dateMeasured.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String str = charSequence.toString().trim();
                //checking the format validity of the input
                if(str.length()==10){
                    String[]temp = str.split("-",3);
                    if(temp.length==3){
                        if(Integer.parseInt(temp[0])>31 || Integer.parseInt(temp[0])<1){
                            dateMeasured.setError("Day should be in range: 1-31");
                        }else if(Integer.parseInt(temp[1])>12 || Integer.parseInt(temp[1])<1){
                            dateMeasured.setError("Month should be in range: 1-12");
                        }else if(Integer.parseInt(temp[2])==0){
                            dateMeasured.setError("Illegal entry");
                        }else{
                            calendar.set(Integer.parseInt(temp[2]),Integer.parseInt(temp[1])-1,Integer.parseInt(temp[0]));
                        }
                    }else{
                        dateMeasured.setError("Illegal format");
                    }
                }else{
                    dateMeasured.setError("Illegal format");
                }

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        timeMeasuredLayout.setEndIconOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new TimePickerDialog(InsertRecordActivity.this,timeSetListener,hr,min,true).show();
            }
        });
        //validating the custom input of time measured
        timeMeasured.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String str = charSequence.toString().trim();
                //checking the format validity of the input
                if(str.length()>=3 && str.length()<=5){
                    String[]temp = str.split(":");
                    if(temp.length==2){
                        if(Integer.parseInt(temp[0])>24 || Integer.parseInt(temp[0])<0){
                            timeMeasured.setError("Hour should be in range: 0-24");
                        }else if(Integer.parseInt(temp[1])>60 || Integer.parseInt(temp[1])<0){
                            timeMeasured.setError("Minutes should be in range: 0-60");
                        }else{
                            hr = Integer.parseInt(temp[0]);
                            min = Integer.parseInt(temp[1]);
                        }
                    }else{
                        timeMeasured.setError("Illegal format");
                    }
                }else{
                    timeMeasured.setError("Illegal format");
                }

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        saveButton.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onClick(View view) {
                if(sysPressure.getError()==null
                        && diaPressure.getError()==null
                        && timeMeasured.getError()==null
                        && dateMeasured.getError()==null
                ){
                    //save in database

                    String format = "dd-MM-yyyy";
                    SimpleDateFormat dateFormat = new SimpleDateFormat(format, Locale.getDefault());
                    if(intent.hasExtra("index")){
                        //update
                        int position = intent.getIntExtra("index",-1);
                        RecordList.getInstance().updateRecord(position,new Record(
                                Integer.parseInt(sysPressure.getText().toString().trim()),
                                Integer.parseInt(diaPressure.getText().toString().trim()),
                                Integer.parseInt(heartRate.getText().toString().trim()),
                                timeMeasured.getText().toString().trim(),
                                dateFormat.format(calendar.getTime()),
                                comment.getText().toString().trim()));
//                        RecordActivity.recordListAdaptor.notifyItemChanged(position);
                        RecordActivity.recordListAdaptor.notifyItemChanged(position, RecordList.getInstance().getRecord(position));
                        Toast.makeText(InsertRecordActivity.this,"Record updated successfully!", Toast.LENGTH_SHORT).show();
                    }else{
                        RecordList.getInstance().addRecord(new Record(
                                Integer.parseInt(sysPressure.getText().toString().trim()),
                                Integer.parseInt(diaPressure.getText().toString().trim()),
                                Integer.parseInt(heartRate.getText().toString().trim()),
                                timeMeasured.getText().toString().trim(),
                                dateFormat.format(calendar.getTime()),
                                comment.getText().toString().trim()
                        ));


                        RecordActivity.recordListAdaptor.notifyItemInserted( RecordList.getInstance().getCount()-1);
                        Toast.makeText(InsertRecordActivity.this,"Record created successfully!", Toast.LENGTH_SHORT).show();
                    }

                    //saving in database
                    Helpers.setRecordsToDB(InsertRecordActivity.this,RecordList.getInstance().getRecords());
                    finish();
                    RecordActivity.setTotal();

                }else{
                    //checking error in data
                    Toast.makeText(InsertRecordActivity.this,"Invalid data", Toast.LENGTH_SHORT).show();
                }
            }
        });


    }

    //set formatted text
    private void updateDateData() {
        String format = "dd-MM-yyyy";
        SimpleDateFormat dateFormat = new SimpleDateFormat(format, Locale.getDefault());
        dateMeasured.setText(dateFormat.format(calendar.getTime()));
    }

    //set formatted text
    private void updateTimeData() {
        String temp = String.valueOf(hr)+":"+String.valueOf(min);
        timeMeasured.setText(temp);
    }


}
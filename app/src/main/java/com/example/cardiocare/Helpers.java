package com.example.cardiocare;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class Helpers {

    static  public  String getStatus(int systolicPressure, int diastolicPressure){
        if(systolicPressure<=90 && diastolicPressure<=60) {
            return "Low";
        }else if(systolicPressure<=120 && diastolicPressure<=80){
            return "Normal";
        }else if(systolicPressure>120 && systolicPressure<130 && diastolicPressure<=80){
            return "High";
        }else if(systolicPressure>=130 && systolicPressure<140 && diastolicPressure>=80 && diastolicPressure<=89){
            return "High(stage 1)";
        }else if(systolicPressure>=140  && diastolicPressure>=90){
            return "High(stage 2)";
        }else{
            return "Unusual";
        }
    }
    static public String getStatusColor(int systolicPressure,int diastolicPressure){
        String status = getStatus(systolicPressure, diastolicPressure);
        switch (status){
            case "Low":
                return "#F6A758";
            case "Normal":
                return "#00C897";
            case "High":
                return "#F6A758";
            case "High(stage 1)":
                return "#FB5858";
            case "High(stage 2)":
                return "#FB5858";
            default:
                return "#F3683C";
        }
    }
    static public List<Record> getRecordsFromDB(Context context){
        SharedPreferences sharedPreferences =
                context.getSharedPreferences("shared",Context.MODE_PRIVATE);
        Gson gson = new Gson();
        String jsonString = sharedPreferences.getString("records","[]");
        Type type = new TypeToken<ArrayList<Record>>(){}.getType();
        return gson.fromJson(jsonString,type);
    }

    static public void setRecordsToDB(Context context,List<Record> records){
        SharedPreferences sharedPreferences =
                context.getSharedPreferences("shared",Context.MODE_PRIVATE);
        SharedPreferences.Editor editor =sharedPreferences.edit();
        Gson gson = new Gson();
        String jsonString = gson.toJson(records);
        editor.putString("records",jsonString);
        editor.apply();
    }

}



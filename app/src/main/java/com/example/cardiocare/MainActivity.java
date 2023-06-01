package com.example.cardiocare;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.heartify.R;
import com.example.heartify.adaptors.RecordListAdaptor;
import com.example.heartify.interfaces.RecordListListeners;
import com.example.heartify.models.Record;
import com.example.heartify.models.RecordList;
import com.example.heartify.utils.Helpers;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.dialog.MaterialDialogs;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements RecordListListeners {

    RecyclerView recyclerView;
    Button createButton;
    RecordList recordList = RecordList.getInstance();
    static public RecordListAdaptor recordListAdaptor;
    static TextView totalText;
    static ImageView imageView;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = findViewById(R.id.main_recycler_view);
        createButton = findViewById(R.id.main_create_button);
        totalText = findViewById(R.id.activity_main_total);
        imageView = findViewById(R.id.activity_main_empty_image);
        context = this;

        recordList.setRecords(Helpers.getRecordsFromDB(MainActivity.this));

        recordListAdaptor = new RecordListAdaptor(this, recordList.getRecords(), this);
        recyclerView.setAdapter(recordListAdaptor);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        createButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, COERecordActivity.class);
                startActivity(intent);
            }
        });


        setTotal();

    }

    @SuppressLint("SetTextI18n")
    static void setTotal() {
        if (RecordList.getInstance().getCount() == 0) {
            imageView.setVisibility(View.VISIBLE);
        } else {
            imageView.setVisibility(View.GONE);
        }
        totalText.setText("Total results found: " + String.valueOf(RecordList.getInstance().getCount()));
    }


    @Override
    public void onRecordDeleteListener(int position) {
        showDeleteDialog(position);
    }

    private void showDeleteDialog(int position) {

        final AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity.this);
        View view = getLayoutInflater().inflate(R.layout.record_delete_dialog_layout, null);
        Button confirmButton, cancelButton;
        confirmButton = view.findViewById(R.id.delete_record_dialog_confirm_button);
        cancelButton = view.findViewById(R.id.delete_record_dialog_cancel_button);
        alert.setView(view);
        final AlertDialog alertDialog = alert.create();
        alertDialog.setCanceledOnTouchOutside(true);
        alertDialog.show();
        //Delete the record
        confirmButton.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onClick(View view) {
                //deleting the item
                RecordList.getInstance().deleteRecord(position);
                //notifying the listener
                recordListAdaptor.notifyItemRemoved(position);
                recordListAdaptor.notifyItemRangeChanged(position, RecordList.getInstance().getCount());
                //showing a toast to the user
                Toast.makeText(MainActivity.this, "Record deleted", Toast.LENGTH_SHORT).show();
                //dismissing the alert
                alertDialog.dismiss();
                //setting total text
                setTotal();
                //saving data to database
                Helpers.setRecordsToDB(MainActivity.this, RecordList.getInstance().getRecords());
            }
        });
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
            }
        });
    }

}
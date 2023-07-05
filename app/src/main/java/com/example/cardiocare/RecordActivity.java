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

import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.dialog.MaterialDialogs;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.List;

public class RecordActivity extends AppCompatActivity implements RecordListListeners {

    RecyclerView recyclerView;
    Button createButton;
    RecordList recordList = RecordList.getInstance();
    static public RecordListAdaptor recordListAdaptor;
    static TextView totalText;
    static ImageView imageView, signout;
    Context context;
    private FirebaseAuth authProfile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record);
        getSupportActionBar().hide();


        recyclerView = findViewById(R.id.main_recycler_view);
        createButton = findViewById(R.id.main_create_button);
        totalText = findViewById(R.id.activity_main_total);
        imageView = findViewById(R.id.activity_main_empty_image);
        signout=findViewById(R.id.signout);
        authProfile=FirebaseAuth.getInstance();
        context = this;

        recordList.setRecords(Helpers.getRecordsFromDB(RecordActivity.this));

        recordListAdaptor = new RecordListAdaptor(this,recordList.getRecords(),this);
        recyclerView.setAdapter(recordListAdaptor);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        createButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RecordActivity.this,InsertRecordActivity.class);
                startActivity(intent);
            }
        });



        setTotal();

        signout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                authProfile.signOut();
                Toast.makeText(RecordActivity.this,"Signed Out",Toast.LENGTH_LONG).show();
                Intent intent=new Intent(RecordActivity.this,LoginActivity.class);

                //Clear all stacks
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK
                        | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish();               //Close ProfileActivity
            }
        });

    }
    @SuppressLint("SetTextI18n")
    static void setTotal(){
        if(RecordList.getInstance().getCount()==0){
            imageView.setVisibility(View.VISIBLE);
        }else{
            imageView.setVisibility(View.GONE);
        }
        totalText.setText("Total results found: "+String.valueOf(RecordList.getInstance().getCount()));
    }


    @Override
    public void onRecordDeleteListener(int position) {
        showDeleteDialog(position);
    }

    private void showDeleteDialog(int position){

        final AlertDialog.Builder alert = new AlertDialog.Builder(RecordActivity.this);
        View view = getLayoutInflater().inflate(R.layout.record_delete_dialog_layout,null);
        Button confirmButton,cancelButton;
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
                recordListAdaptor.notifyItemRangeChanged(position,RecordList.getInstance().getCount());
                //showing a toast to the user
                Toast.makeText(RecordActivity.this, "Record deleted successfully!", Toast.LENGTH_SHORT).show();
                //dismissing the alert
                alertDialog.dismiss();
                //setting total text
                setTotal();
                //saving data to database
                Helpers.setRecordsToDB(RecordActivity.this,RecordList.getInstance().getRecords());
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
package com.example.cardiocare;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class RecordListAdaptor extends RecyclerView.Adapter<RecordListAdaptor.RecordListViewHolder> {

    List<Record> records = new ArrayList<>();
    Context context;
    private RecordListListeners recordListListeners;

    /**
     * This is a constructor to for the adaptor
     * @param context, records, recordListListener
     */
    public RecordListAdaptor(Context context, List<Record> records, RecordListListeners recordListListeners) {
        this.records = records;
        this.context = context;
        this.recordListListeners = recordListListeners;
    }

    /**
     * This is a method to for the adaptor
     * @param parent, viewType
     *                This binds the layout to the recycler view
     */
    @NonNull
    @Override
    public RecordListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.record_recycler_item_view,parent,false);
        return new RecordListViewHolder(view);
    }

    /**
     * This is a method to for the adaptor
     * @param holder, position
     *                This allows to access the views and do certain operations
     */
    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull RecordListViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.dateMeasured.setText(records.get(position).getDateMeasured());
        holder.sysPressure.setText(records.get(position).getSystolicPressure()+" mmHg");
        holder.diaPressure.setText(records.get(position).getDiastolicPressure()+" mmHg");
        holder.heartRate.setText(records.get(position).getHeartRate()+" bits/min");
        holder.status.setText(Helpers.getStatus(
                records.get(position).getSystolicPressure(),
                records.get(position).getDiastolicPressure()));
        holder.statusBackground.setBackgroundColor(Color.parseColor(Helpers.getStatusColor(
                records.get(position).getSystolicPressure(),
                records.get(position).getDiastolicPressure())));

        //on details record click
        holder.viewButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, RecordDetailsActivity.class);
                intent.putExtra("index",position);

                context.startActivity(intent);
            }
        });

        //on edit record click
        holder.editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

//                Toast.makeText(context, "okay" + String.valueOf(position), Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(context, InsertRecordActivity.class);

                intent.putExtra("index",position);
                context.startActivity(intent);
            }
        });

        //on delete record click
        holder.deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                recordListListeners.onRecordDeleteListener(position);
            }
        });
    }

    /**
     * This is a method to get the size of the list in the adaptor
     *       This allows to access the views and do certain operations
     */

    @Override
    public int getItemCount() {
        return records.size();
    }

    /**
     * This is a class to connect the views with the local variables
     */
    public class RecordListViewHolder extends RecyclerView.ViewHolder{
        TextView sysPressure,diaPressure,heartRate,dateMeasured,status;
        Button editButton,deleteButton,viewButton;
        ConstraintLayout statusBackground;
        public RecordListViewHolder(@NonNull View itemView) {
            super(itemView);

            //connecting views by their ids
            sysPressure = itemView.findViewById(R.id.r_r_v_i_systolic_data);
            diaPressure = itemView.findViewById(R.id.r_r_v_i_diastolic_data);
            heartRate = itemView.findViewById(R.id.r_r_v_i_heart_rate_data);
            dateMeasured= itemView.findViewById(R.id.r_r_v_i_dateMeasured);
            statusBackground = itemView.findViewById(R.id.r_r_v_i_status_background);
            status = itemView.findViewById(R.id.r_r_v_i_status_data);
            viewButton = itemView.findViewById(R.id.r_r_v_i_view_button);
            editButton = itemView.findViewById(R.id.r_r_v_i_edit_button);
            deleteButton = itemView.findViewById(R.id.r_r_v_i_delete_button);

        }
    }

}


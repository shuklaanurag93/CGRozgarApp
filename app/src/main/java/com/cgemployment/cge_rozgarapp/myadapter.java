package com.cgemployment.cge_rozgarapp;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class myadapter extends RecyclerView.Adapter<myadapter.myviewholder> {


    List<AlertStatusModel> ResultModelClassList;
    btn_rojgar_alert_formListener btnRojgarAlertFormListener;
    Context context;

    public myadapter(List<AlertStatusModel> resultModelClassList, btn_rojgar_alert_formListener btnRojgarAlertFormListener) {
        ResultModelClassList = resultModelClassList;
        this.btnRojgarAlertFormListener = btnRojgarAlertFormListener;
        this.context = context;
    }

    /*  public myadapter(List<AlertStatusModel> resultModelClassList,Context context) {
        ResultModelClassList = resultModelClassList;
        this.context = context;
    } */




    private List<String> mData;
    private LayoutInflater mInflater;
    //private ItemClickListener mClickListener;
    // data is passed into the constructor
    myadapter(Context context, List<String> data) {
        this.mInflater = LayoutInflater.from(context);
        this.mData = data;
    }



    @NonNull
    @Override
    public myviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.singlerowxml, parent, false);
        return new myviewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull myviewholder holder, @SuppressLint("RecyclerView") int position) {

        AlertStatusModel alertStatusModel = ResultModelClassList.get(position);
        holder.t1.setText(alertStatusModel.getDepartmentName());
        holder.t2.setText(alertStatusModel.getJobDescription());
        holder.t3.setText(alertStatusModel.getWebsiteUrl());

        holder.t3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

               // Toast.makeText(context,alertStatusModel.getStateCode() , Toast.LENGTH_SHORT).show();
                String url=alertStatusModel.getWebsiteUrl();
                btnRojgarAlertFormListener.sentData(position, url);


            }
        });




    }
    @Override
    public int getItemCount() {
        return ResultModelClassList.size();
    }


    public AlertStatusModel getObject(int position) {
        return ResultModelClassList.get(position);
    }


    class myviewholder extends RecyclerView.ViewHolder
    {
        TextView t1,t2,t3;
        public myviewholder(@NonNull View itemView) {
            super(itemView);
            t1=itemView.findViewById(R.id.t1);
            t2=itemView.findViewById(R.id.t2);
            t3=itemView.findViewById(R.id.t3);


        }
    }
}
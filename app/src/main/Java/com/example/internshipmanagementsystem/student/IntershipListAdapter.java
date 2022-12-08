package com.example.internshipmanagementsystem.student;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.internshipmanagementsystem.R;
import com.example.internshipmanagementsystem.model.ApplyForInternshipModel;

import java.util.ArrayList;


public class IntershipListAdapter extends RecyclerView.Adapter<IntershipListAdapter.ViewHolder>{
    private ArrayList<ApplyForInternshipModel> listdata;
    InternshipListActivity internshipListActivity;

    // RecyclerView recyclerView;
    public IntershipListAdapter(InternshipListActivity activity,ArrayList<ApplyForInternshipModel> listdata) {
        this.listdata = listdata;
        this.internshipListActivity=activity;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem= layoutInflater.inflate(R.layout.list_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(listItem);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final ApplyForInternshipModel myListData = listdata.get(position);
        holder.update_companyName.setText(listdata.get(position).getCompanyName().toString());
        holder.update_year.setText(listdata.get(position).getYear().toString());
        holder.update_semester.setText(listdata.get(position).getSemesterTerm().toString());
        if (listdata.get(position).getStatus().equals("PENDING")){
            holder.update_status.setText("Under Review");
        }
        holder.update_enddate.setText(listdata.get(position).getEnddate().toString());
        holder.update_startdate.setText(listdata.get(position).getStartDate().toString());
        holder.relativeLayoutl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(internshipListActivity, UpdateDetails.class);
                intent.putExtra("STUDENTKEY", listdata.get(position).getKey());
                internshipListActivity.startActivity(intent);
            }
        });
    }


    @Override
    public int getItemCount() {
        return listdata.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView update_status,update_enddate,update_startdate,update_year,update_semester,update_companyName;
        public CardView relativeLayoutl;
        public ViewHolder(View itemView) {
            super(itemView);
            this.update_year = (TextView) itemView.findViewById(R.id.update_year);
            this.update_companyName = (TextView) itemView.findViewById(R.id.update_companyName);
            this.update_semester = (TextView) itemView.findViewById(R.id.update_semester);
            this.update_status = (TextView) itemView.findViewById(R.id.update_status);
            this.update_enddate = (TextView) itemView.findViewById(R.id.update_enddate);
            this.update_startdate = (TextView) itemView.findViewById(R.id.update_startdate);
            this.relativeLayoutl = (CardView) itemView.findViewById(R.id.relativeLayoutl);
        }
    }
}

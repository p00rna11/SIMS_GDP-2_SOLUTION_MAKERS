package com.example.internshipmanagementsystem.student;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.internshipmanagementsystem.R;
import com.example.internshipmanagementsystem.model.ApplyForInternshipModel;

import java.util.ArrayList;



public class StatusIntershipListAdapter extends RecyclerView.Adapter<StatusIntershipListAdapter.ViewHolder>{
    private ArrayList<ApplyForInternshipModel> listdata;
    Status internshipListActivity;

    // RecyclerView recyclerView;
    public StatusIntershipListAdapter(Status activity, ArrayList<ApplyForInternshipModel> listdata) {
        this.listdata = listdata;
        this.internshipListActivity=activity;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem= layoutInflater.inflate(R.layout.status_list_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(listItem);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final ApplyForInternshipModel myListData = listdata.get(position);
        holder.update_companyName.setText(listdata.get(position).getCompanyName().toString());
        holder.update_year.setText(listdata.get(position).getYear().toString());
        holder.update_semester.setText(listdata.get(position).getSemesterTerm().toString());
        holder.update_status.setText(listdata.get(position).getStatus().toString());
        holder.update_applicationid.setText(listdata.get(position).getRandom().toString());
        holder.update_enddate.setText(listdata.get(position).getEnddate().toString());
        holder.update_startdate.setText(listdata.get(position).getStartDate().toString());
        holder.update_comment.setText(listdata.get(position).getComment().toString());

    }


    @Override
    public int getItemCount() {
        return listdata.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView update_status,update_comment,update_enddate,update_startdate,update_year,update_semester,update_companyName,update_applicationid;
        public CardView relativeLayoutl;
        public ViewHolder(View itemView) {
            super(itemView);
            this.update_year = (TextView) itemView.findViewById(R.id.update_year);
            this.update_companyName = (TextView) itemView.findViewById(R.id.update_companyName);
            this.update_semester = (TextView) itemView.findViewById(R.id.update_semester);
            this.update_status = (TextView) itemView.findViewById(R.id.update_status);
            this.update_applicationid = (TextView) itemView.findViewById(R.id.update_applicationid);
            this.update_comment = (TextView) itemView.findViewById(R.id.update_comment);
            this.update_enddate = (TextView) itemView.findViewById(R.id.update_enddate);
            this.update_startdate = (TextView) itemView.findViewById(R.id.update_startdate);
            this.relativeLayoutl = (CardView) itemView.findViewById(R.id.relativeLayoutl);
        }
    }
}

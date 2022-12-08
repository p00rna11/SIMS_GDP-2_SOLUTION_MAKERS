package com.example.internshipmanagementsystem.faculty;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.internshipmanagementsystem.R;
import com.example.internshipmanagementsystem.model.ApplyForInternshipModel;
import com.example.internshipmanagementsystem.student.FindInternship;
import com.example.internshipmanagementsystem.student.UpdateDetails;

import java.util.ArrayList;


class TotalInternAdapter extends RecyclerView.Adapter<TotalInternAdapter.ViewHolder>{

    private ArrayList<ApplyForInternshipModel> listdata;
    CurrentSemeterInternActivity internshipListActivity;

    // RecyclerView recyclerView;
    public TotalInternAdapter(CurrentSemeterInternActivity activity,ArrayList<ApplyForInternshipModel> listdata) {
        this.listdata = listdata;
        this.internshipListActivity=activity;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem= layoutInflater.inflate(R.layout.totalintern_list_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(listItem);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final ApplyForInternshipModel myListData = listdata.get(position);
        if(listdata.get(position).getStatus().equalsIgnoreCase("APPROVE")){
            holder.studentStatus.setText("APPROVED");
        }else{
            holder.studentStatus.setText(listdata.get(position).getStatus());
        }
          holder.studentappid.setText(listdata.get(position).getRandom().toString());
        holder.studentName.setText(listdata.get(position).getFirstName().toString());
        holder.firstLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=  new Intent(internshipListActivity, InternStudentDetails.class);
                intent.putExtra("interndetails",myListData);
                internshipListActivity.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return listdata.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView studentName,studentappid,studentStatus;
        LinearLayout firstLayout;

        public ViewHolder(View itemView) {
            super(itemView);
            this.studentStatus = (TextView) itemView.findViewById(R.id.studentStatus);
            this.studentappid = (TextView) itemView.findViewById(R.id.studentappid);
            this.studentName = (TextView) itemView.findViewById(R.id.studentName);
            this.firstLayout = (LinearLayout) itemView.findViewById(R.id.firstLayout);

        }
    }
}

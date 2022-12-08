package com.example.internshipmanagementsystem.student;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.internshipmanagementsystem.R;
import com.example.internshipmanagementsystem.model.ApplyForInternshipModel;

import java.util.ArrayList;

 class FindListAdapter extends RecyclerView.Adapter<FindListAdapter.ViewHolder>{

    private ArrayList<ApplyForInternshipModel> listdata;
    FindInternship internshipListActivity;

    // RecyclerView recyclerView;
    public FindListAdapter(FindInternship activity,ArrayList<ApplyForInternshipModel> listdata) {
        this.listdata = listdata;
        this.internshipListActivity=activity;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem= layoutInflater.inflate(R.layout.list_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(listItem);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final ApplyForInternshipModel myListData = listdata.get(position);
/*        holder.textView.setText(listdata.get(position).getCompanyName().toString());
        holder.relativeLayoutl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(internshipListActivity, UpdateDetails.class);
                intent.putExtra("STUDENTKEY", listdata.get(position).getKey());
                internshipListActivity.startActivity(intent);
            }
        });*/

    }

    @Override
    public int getItemCount() {
        return listdata.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView textView;
        public CardView relativeLayoutl;
        public ViewHolder(View itemView) {
            super(itemView);
        //    this.textView = (TextView) itemView.findViewById(R.id.detailsText);
            this.relativeLayoutl = (CardView ) itemView.findViewById(R.id.constaint);
        }
    }
}

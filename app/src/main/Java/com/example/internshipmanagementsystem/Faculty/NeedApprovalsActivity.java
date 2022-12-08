package com.example.internshipmanagementsystem.faculty;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.example.internshipmanagementsystem.R;
import com.example.internshipmanagementsystem.model.ApplyForInternshipModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class NeedApprovalsActivity extends AppCompatActivity {


    RecyclerView listview_totalIntern;
    NeedApprovalInternAdapter needApprovalInternAdapter;
    ProgressDialog progressDialog;
    ArrayList<ApplyForInternshipModel> pendingList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_need_approvals);
        //Intent intent = getIntent();
        //ArrayList<ApplyForInternshipModel> arrayList = (ArrayList<ApplyForInternshipModel>) getIntent().getSerializableExtra("PENDINGINTERLIST");
     /*   if (arrayList.size() > 0) {
            listview_totalIntern = findViewById(R.id.listview_totalIntern);
            listview_totalIntern.setLayoutManager(new LinearLayoutManager(this));
            needApprovalInternAdapter = new NeedApprovalInternAdapter(NeedApprovalsActivity.this, arrayList);
            listview_totalIntern.setAdapter(needApprovalInternAdapter);
        }*/
        pendingList = new ArrayList<>();
        listview_totalIntern = findViewById(R.id.listview_totalIntern);
        listview_totalIntern.setLayoutManager(new LinearLayoutManager(this));
        needApprovalInternAdapter = new NeedApprovalInternAdapter(this, pendingList);
        listview_totalIntern.setAdapter(needApprovalInternAdapter);
        getPendingList();

    }

    private void getPendingList() {
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading....");
        progressDialog.show();
        pendingList.clear();
        String facultyMail = getIntent().getStringExtra("MAIL");
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("facultyDatabase").child("studentInternshipDetails").child(facultyMail);
        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                progressDialog.cancel();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    ApplyForInternshipModel applyForInternshipModel = dataSnapshot.getValue(ApplyForInternshipModel.class);

                    if (applyForInternshipModel.getStatus().equals("PENDING")) {
                        pendingList.add(applyForInternshipModel);
                    }

                }
                needApprovalInternAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                progressDialog.cancel();
            }
        });


    }

    @Override
    protected void onResume() {
        super.onResume();
        if (needApprovalInternAdapter != null) {
            needApprovalInternAdapter.notifyDataSetChanged();
        }
    }
}
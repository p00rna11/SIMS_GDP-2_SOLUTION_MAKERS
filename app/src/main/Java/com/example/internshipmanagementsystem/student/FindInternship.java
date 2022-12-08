package com.example.internshipmanagementsystem.student;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.Toast;

import com.example.internshipmanagementsystem.CommonUtils;
import com.example.internshipmanagementsystem.R;
import com.example.internshipmanagementsystem.model.ApplyForInternshipModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class FindInternship extends AppCompatActivity {

    RecyclerView intership_recycerview;
    ProgressDialog progressDialog;
    FindListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_internship);
        intership_recycerview=findViewById(R.id.searchList);
        intership_recycerview.setLayoutManager(new LinearLayoutManager(this));
        ArrayList<ApplyForInternshipModel> list=new ArrayList<>();
      //  adapter=new FindListAdapter(this,list);
        getData(list);
        if(list!=null){
        //    intership_recycerview.setAdapter(adapter);
        }
    }

    private void getData(ArrayList<ApplyForInternshipModel> list) {


        FirebaseDatabase database = FirebaseDatabase.getInstance();
        String studentID = FirebaseAuth.getInstance().getCurrentUser().getUid();
        DatabaseReference myRef = database.getReference("student").child("applyForInternship").child(studentID);

        progressDialog = new ProgressDialog(FindInternship.this);
        progressDialog.setMessage("Loading....");
        progressDialog.show();

        if (CommonUtils.isConnectedToInternet(FindInternship.this)) {


            myRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    list.clear();

                    if (snapshot != null) {
                        for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                            ApplyForInternshipModel applyForInternshipModel = postSnapshot.getValue(ApplyForInternshipModel.class);
                            list.add(applyForInternshipModel);
                        }
                        adapter.notifyDataSetChanged();
                    }
                    progressDialog.cancel();
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    progressDialog.cancel();
                    Toast.makeText(FindInternship.this, "Please try again after sometime....", Toast.LENGTH_SHORT).show();
                }
            });
        }

/*        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot != null) {
                    for (DataSnapshot postSnapshot: snapshot.getChildren()) {
                        ApplyForInternshipModel applyForInternshipModel = postSnapshot.getValue(ApplyForInternshipModel.class);
                        list.add(applyForInternshipModel);
                }
            }
                @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(InternshipListActivity.this, "Please try again after sometime....", Toast.LENGTH_SHORT).show();
            });
            }
        }*/

    }
}
package com.example.internshipmanagementsystem.student;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.example.internshipmanagementsystem.R;
import com.example.internshipmanagementsystem.model.ApplyForInternshipModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Status extends AppCompatActivity {

    TextView txtApplicationIDStatus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_status);
        txtApplicationIDStatus = findViewById(R.id.txtApplicationIDStatus);
        getapplicationID();

    }

    private void getapplicationID() {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        String studentID = FirebaseAuth.getInstance().getCurrentUser().getUid();
        DatabaseReference myRef = database.getReference("student").child("applyForInternship").child(studentID);

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot != null) {
                    ApplyForInternshipModel model = snapshot.getValue(ApplyForInternshipModel.class);
                    if (model != null) {
                        Log.d("ApplyForInternshipModel", model.toString());
                        if (TextUtils.isEmpty(model.getPhoneNumber())) {

                        } else {
                            setDatatoViews(model);
                        }
                    } else {
                        Toast.makeText(Status.this, "No Data", Toast.LENGTH_LONG).show();
                        finish();
                    }

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(Status.this, "Please try again after sometime....", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void setDatatoViews(ApplyForInternshipModel model) {
        String phone = model.getRandom();
        txtApplicationIDStatus.setText("Your application id is: " + phone);

    }
}
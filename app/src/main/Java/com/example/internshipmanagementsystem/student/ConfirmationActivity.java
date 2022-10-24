package com.example.internshipmanagementsystem.student;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ActionBar;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.internshipmanagementsystem.MainActivity;
import com.example.internshipmanagementsystem.R;
import com.example.internshipmanagementsystem.model.ApplyForInternshipModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ConfirmationActivity extends AppCompatActivity {
    Button homePage;
    TextView txtApplicationIdConfirmationPage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirmation);
        homePage = findViewById(R.id.homePage);
        txtApplicationIdConfirmationPage = findViewById(R.id.txtApplicationIdConfirmationPage);
        getapplicationID();
        homePage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ConfirmationActivity.this, StudentDashboard.class));
                finish();
            }
        });
    }

    private void getapplicationID() {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        String studentID = FirebaseAuth.getInstance().getCurrentUser().getUid();
        String key=getIntent().getStringExtra("STUDENTKEY");
        DatabaseReference myRef = database.getReference("student").child("applyForInternship").child(studentID).child(key);

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
                        Toast.makeText(ConfirmationActivity.this, "No Data", Toast.LENGTH_LONG).show();
                        finish();
                    }

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(ConfirmationActivity.this, "Please try again after sometime....", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void setDatatoViews(ApplyForInternshipModel model) {
        String phone = model.getRandom();
        txtApplicationIdConfirmationPage.setText("Your application id is: " + phone);
    }

    @Override
    public void onBackPressed() {

    }
}
package com.example.internshipmanagementsystem.admin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.internshipmanagementsystem.R;
import com.example.internshipmanagementsystem.student.ApplyForInternship;
import com.example.internshipmanagementsystem.student.StudentDashboard;
import com.example.internshipmanagementsystem.student.StudentLoginActivity;
import com.google.firebase.auth.FirebaseAuth;

public class AdminDashboard extends AppCompatActivity {

CardView crdViewStudents;
CardView crdViewSupervisors;
CardView crdStudentView;
ImageView id_logout;
TextView loggedInUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_admin_dashboard);
        crdViewStudents = findViewById(R.id.viewStudents);
        crdViewSupervisors = findViewById(R.id.viewSupervisors);
        crdStudentView = findViewById(R.id.studentView);
        loggedInUser=findViewById(R.id.loggedInUser);

        id_logout = findViewById(R.id.id_logout);


        id_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
     //           startActivity(new Intent(AdminDashboard.this, AdminLoginActivity.class));
                finish();
            }
        });


        crdViewStudents.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AdminDashboard.this, DisplayAllStudents.class));
            }
        });

        crdViewSupervisors.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AdminDashboard.this, DisplayAllSupervisors.class));
            }
        });

    }

    @Override
    public void onBackPressed() {

    }
}
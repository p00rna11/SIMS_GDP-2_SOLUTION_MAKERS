package com.example.internshipmanagementsystem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;

import com.example.internshipmanagementsystem.admin.AdminLoginActivity;
import com.example.internshipmanagementsystem.faculty.FacultyDashboard;
import com.example.internshipmanagementsystem.faculty.FacultyLoginActivity;
import com.example.internshipmanagementsystem.student.StudentLoginActivity;

public class MainActivity extends AppCompatActivity {
    CardView studentCard;
    CardView facultyCard;
    CardView adminCard;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        studentCard = findViewById(R.id.studetnCard);
        studentCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, StudentLoginActivity.class));
            }
        });

        facultyCard = findViewById(R.id.facultyCard);
        facultyCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, FacultyLoginActivity.class));
            }
        });

        adminCard = findViewById(R.id.adminCard);
        adminCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, AdminLoginActivity.class));
            }
        });

    }
}
package com.example.internshipmanagementsystem.admin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.example.internshipmanagementsystem.MainActivity;
import com.example.internshipmanagementsystem.R;
import com.example.internshipmanagementsystem.faculty.FacultyDashboard;
import com.example.internshipmanagementsystem.faculty.FacultyOverallList;

public class DisplayAllSupervisors extends AppCompatActivity {

    TextView txtSupervisor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_all_supervisors);
        txtSupervisor = findViewById(R.id.txtSupervisor);
        txtSupervisor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(DisplayAllSupervisors.this, FacultyOverallList.class));
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.student_dashboard2, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_logout:
                startActivity(new Intent(DisplayAllSupervisors.this, MainActivity.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
package com.example.internshipmanagementsystem.faculty;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.internshipmanagementsystem.MainActivity;
import com.example.internshipmanagementsystem.R;
import com.example.internshipmanagementsystem.student.ConfirmationActivity;
import com.example.internshipmanagementsystem.student.StudentDashboard;
import com.example.internshipmanagementsystem.student.StudentLoginActivity;
import com.google.firebase.auth.FirebaseAuth;

public class FacultyDashboard extends AppCompatActivity {

    TextView t10, t11, t12, t13, t14, t15, t16, t17, t18;
    ImageView id_logout;
ConstraintLayout id_currentSemeter,id_PreviousSemeter,id_instate,id_oustate,id_neeedtobeapproved,id_needapprovals,id_totalSemeter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_faculty_overall_list);
        t10 = findViewById(R.id.textView7);
        id_needapprovals = findViewById(R.id.id_needapprovals);
        id_neeedtobeapproved = findViewById(R.id.id_neeedtobeapproved);
        id_oustate = findViewById(R.id.id_oustate);
        id_instate = findViewById(R.id.id_instate);
        id_currentSemeter = findViewById(R.id.id_currentSemeter);
        id_PreviousSemeter = findViewById(R.id.id_PreviousSemeter);
        id_logout = findViewById(R.id.id_logout);
        id_totalSemeter = findViewById(R.id.id_totalSemeter);


        id_totalSemeter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(FacultyDashboard.this, CurrentSemeterInternActivity.class));
            }
        });

        id_currentSemeter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(FacultyDashboard.this, CurrentSemeterInternActivity.class));
            }
        });
        id_PreviousSemeter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(FacultyDashboard.this, CurrentSemeterInternActivity.class));
            }
        });


        id_instate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(FacultyDashboard.this, StateActivity.class));
            }
        });



        id_oustate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(FacultyDashboard.this, OutStateActivity.class));
            }
        });

        id_needapprovals.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(FacultyDashboard.this, NeedApprovalsActivity.class));

            }
        });



        id_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(FacultyDashboard.this, MainActivity.class));
                finish();
            }
        });
    }


}
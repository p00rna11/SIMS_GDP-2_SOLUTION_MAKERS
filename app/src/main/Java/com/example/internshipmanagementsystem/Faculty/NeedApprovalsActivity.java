package com.example.internshipmanagementsystem.faculty;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.example.internshipmanagementsystem.R;

public class NeedApprovalsActivity extends AppCompatActivity {

    LinearLayout fourLinearLayout, thirdLinearLayout, secondLinearLayout, firstLinearLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_need_approvals);

        firstLinearLayout = findViewById(R.id.firstLinearLayout);
        secondLinearLayout = findViewById(R.id.secondLinearLayout);
        thirdLinearLayout = findViewById(R.id.thirdLinearLayout);
        fourLinearLayout = findViewById(R.id.fourLinearLayout);


        fourLinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(NeedApprovalsActivity.this, InternStudentDetails.class));

            }
        });

        thirdLinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(NeedApprovalsActivity.this, InternStudentDetails.class));

            }
        });
        secondLinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(NeedApprovalsActivity.this, InternStudentDetails.class));

            }
        });
        firstLinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(NeedApprovalsActivity.this, InternStudentDetails.class));

            }
        });
    }
}
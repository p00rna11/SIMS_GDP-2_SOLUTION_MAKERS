package com.example.internshipmanagementsystem.student;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Toast;

import com.example.internshipmanagementsystem.CommonUtils;
import com.example.internshipmanagementsystem.R;
import com.example.internshipmanagementsystem.model.ApplyForInternshipModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Random;

public class StudentAgreement extends AppCompatActivity {
    Button btnSubmit;
    CheckBox agreecheck;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_student_agreement);
        btnSubmit = findViewById(R.id.btnSubmitAgreement);
        agreecheck = findViewById(R.id.agreecheck);

        String firstname = getIntent().getStringExtra("firstname");
        String lastname = getIntent().getStringExtra("lastname");
        String credit = getIntent().getStringExtra("credit");
        String semesterterm = getIntent().getStringExtra("semesterterm");
        String year = getIntent().getStringExtra("year");
        String credithours = getIntent().getStringExtra("credithours");
        String crn = getIntent().getStringExtra("crn");
        String middlename = getIntent().getStringExtra("middlename");
        String address = getIntent().getStringExtra("address");
        String userid = getIntent().getStringExtra("userid");
        String phone = getIntent().getStringExtra("phone");
        String start = getIntent().getStringExtra("start");
        String enddate = getIntent().getStringExtra("enddate");
        String companyaddress = getIntent().getStringExtra("companyaddress");
        String hoursofwork = getIntent().getStringExtra("hoursofwork");
        String companycontact = getIntent().getStringExtra("companycontact");
        String companyname = getIntent().getStringExtra("companyname");
        String companymail = getIntent().getStringExtra("companymail");
        String facultymail = getIntent().getStringExtra("facultymail");
        String facultyname = getIntent().getStringExtra("facultyname");
        String offerletter=getIntent().getStringExtra("OFFERLETTER");


        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (agreecheck.isChecked()) {


                    String random = "000000";
                    try {
                        random = generateRandom();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }


                    if (CommonUtils.isConnectedToInternet(StudentAgreement.this)) {
                        try {

                            FirebaseDatabase database = FirebaseDatabase.getInstance();
                            String studentID = FirebaseAuth.getInstance().getCurrentUser().getUid();
                            String key = database.getReference().push().getKey();
                            DatabaseReference myRef = database.getReference("student").child("applyForInternship").child(studentID).child(key);
                            String mail=facultymail.replace("@","_");
                            String fmail=mail.replace(".","_");
                            DatabaseReference facultyDatabase = database.getReference("facultyDatabase").child("studentInternshipDetails").child(fmail).child(key);

                            ApplyForInternshipModel applyForInternshipModel = new ApplyForInternshipModel(
                                    firstname, middlename, lastname, userid, address, phone,
                                    companyname, companycontact, companymail, companyaddress, semesterterm, year, crn, credithours, credit, start, enddate, hoursofwork, facultymail, facultyname, random, key,"PENDING",studentID,offerletter,"");

                            myRef.setValue(applyForInternshipModel);
                            facultyDatabase.setValue(applyForInternshipModel);
                            Intent intent = new Intent(StudentAgreement.this, ConfirmationActivity.class);
                            intent.putExtra("STUDENTKEY", key);
                            startActivity(intent);
                            finish();

                        } catch (Exception e) {
                            e.printStackTrace();
                            Toast.makeText(StudentAgreement.this, "Try again after sometime ", Toast.LENGTH_LONG).show();
                        }

                    } else {
                        Toast.makeText(StudentAgreement.this, "No Internet Connection", Toast.LENGTH_LONG).show();
                    }

                } else {
                    Toast.makeText(StudentAgreement.this, "Agree Condition", Toast.LENGTH_LONG).show();

                }
            }
        });

    }

    public static String generateRandom() {
        Random rnd = new Random();
        int number = rnd.nextInt(999999);

        // this will convert any number sequence into 6 character.
        return String.format("%06d", number);
    }

}
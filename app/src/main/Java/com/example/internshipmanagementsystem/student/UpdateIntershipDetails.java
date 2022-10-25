package com.example.internshipmanagementsystem.student;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.internshipmanagementsystem.CommonUtils;
import com.example.internshipmanagementsystem.R;
import com.example.internshipmanagementsystem.model.ApplyForInternshipModel;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class UpdateIntershipDetails extends AppCompatActivity {

    Button btnContinue;
    TextInputEditText enterfirstname, entermiddlename, enterlastname, enteruserid, enteraddress, enterPhone, enterCompanyname, entercompanucontact,
            entercompanymail, entercompanyaddress, entersemeterterm, enteryear, entercrn, entercredithours, entercredit, enterstart,
            enterenddate, enterhoursofwork;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_intership_details);

        enteryear = findViewById(R.id.enteryear);
        entercredit = findViewById(R.id.entercredit);
        enterstart = findViewById(R.id.enterstart);
        enterenddate = findViewById(R.id.enterenddate);
        enterhoursofwork = findViewById(R.id.enterhoursofwork);
        entercredithours = findViewById(R.id.entercredithours);
        entercrn = findViewById(R.id.entercrn);
        enterfirstname = findViewById(R.id.enterfirstname);
        entermiddlename = findViewById(R.id.entermiddlename);
        enterlastname = findViewById(R.id.enterlastname);
        enteruserid = findViewById(R.id.enteruserid);
        enteraddress = findViewById(R.id.enteraddress);
        enterPhone = findViewById(R.id.enterPhone);
        entercompanucontact = findViewById(R.id.entercompanucontact);
        enterCompanyname = findViewById(R.id.enterCompanyname);
        entercompanymail = findViewById(R.id.entercompanymail);
        entercompanyaddress = findViewById(R.id.entercompanyaddress);
        entersemeterterm = findViewById(R.id.entersemeterterm);
        btnContinue = findViewById(R.id.btnContinue);

        btnContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String semesterterm = entersemeterterm.getText().toString();
                String companyaddress = entercompanyaddress.getText().toString();
                String companymail = entercompanymail.getText().toString();
                String companyname = enterCompanyname.getText().toString();
                String companycontact = entercompanucontact.getText().toString();
                String phone = enterPhone.getText().toString();
                String address = enteraddress.getText().toString();
                String userid = enteruserid.getText().toString();
                String middlename = entermiddlename.getText().toString();
                String crn = entercrn.getText().toString();
                String credithours = entercredithours.getText().toString();
                String hoursofwork = enterhoursofwork.getText().toString();
                String enddate = enterenddate.getText().toString();
                String start = enterstart.getText().toString();
                String credit = entercredit.getText().toString();
                String firstname = enterfirstname.getText().toString();
                String lastname = enterlastname.getText().toString();
                String year = enteryear.getText().toString();


                if (TextUtils.isEmpty(firstname)) {
                    Toast.makeText(UpdateIntershipDetails.this, "Enter firstname ", Toast.LENGTH_LONG).show();
                } else if (TextUtils.isEmpty(lastname)) {
                    Toast.makeText(UpdateIntershipDetails.this, "Enter lastname ", Toast.LENGTH_LONG).show();
                } else if (TextUtils.isEmpty(semesterterm)) {
                    Toast.makeText(UpdateIntershipDetails.this, "Enter Semester Term", Toast.LENGTH_LONG).show();
                } else if (TextUtils.isEmpty(year)) {
                    Toast.makeText(UpdateIntershipDetails.this, "Enter year ", Toast.LENGTH_LONG).show();
                } else if (TextUtils.isEmpty(lastname)) {
                    Toast.makeText(UpdateIntershipDetails.this, "Enter lastname ", Toast.LENGTH_LONG).show();
                } else if (TextUtils.isEmpty(credit)) {
                    Toast.makeText(UpdateIntershipDetails.this, "Enter credit ", Toast.LENGTH_LONG).show();
                } else if (TextUtils.isEmpty(start)) {
                    Toast.makeText(UpdateIntershipDetails.this, "Enter start ", Toast.LENGTH_LONG).show();
                } else if (TextUtils.isEmpty(enddate)) {
                    Toast.makeText(UpdateIntershipDetails.this, "Enter enddate ", Toast.LENGTH_LONG).show();
                } else if (TextUtils.isEmpty(hoursofwork)) {
                    Toast.makeText(UpdateIntershipDetails.this, "Enter hoursofwork ", Toast.LENGTH_LONG).show();
                } else if (TextUtils.isEmpty(credithours)) {
                    Toast.makeText(UpdateIntershipDetails.this, "Enter credithours ", Toast.LENGTH_LONG).show();
                } else if (TextUtils.isEmpty(crn)) {
                    Toast.makeText(UpdateIntershipDetails.this, "Enter crn ", Toast.LENGTH_LONG).show();
                } else if (TextUtils.isEmpty(middlename)) {
                    Toast.makeText(UpdateIntershipDetails.this, "Enter middlename ", Toast.LENGTH_LONG).show();
                } else if (TextUtils.isEmpty(userid)) {
                    Toast.makeText(UpdateIntershipDetails.this, "Enter userid ", Toast.LENGTH_LONG).show();
                } else if (TextUtils.isEmpty(address)) {
                    Toast.makeText(UpdateIntershipDetails.this, "Enter address ", Toast.LENGTH_LONG).show();
                } else if (TextUtils.isEmpty(phone)) {
                    Toast.makeText(UpdateIntershipDetails.this, "Enter phone ", Toast.LENGTH_LONG).show();
                } else if (TextUtils.isEmpty(companycontact)) {
                    Toast.makeText(UpdateIntershipDetails.this, "Enter companycontact ", Toast.LENGTH_LONG).show();
                } else if (TextUtils.isEmpty(companyname)) {
                    Toast.makeText(UpdateIntershipDetails.this, "Enter companyname ", Toast.LENGTH_LONG).show();
                } else if (TextUtils.isEmpty(companymail)) {
                    Toast.makeText(UpdateIntershipDetails.this, "Enter companymail ", Toast.LENGTH_LONG).show();
                } else if (TextUtils.isEmpty(companyaddress)) {
                    Toast.makeText(UpdateIntershipDetails.this, "Enter companyaddress ", Toast.LENGTH_LONG).show();
                } else {

                    if (CommonUtils.isConnectedToInternet(UpdateIntershipDetails.this)){

                        try {

                            FirebaseDatabase database = FirebaseDatabase.getInstance();
                            String studentID = FirebaseAuth.getInstance().getCurrentUser().getUid();
                            String key = database.getReference().push().getKey();
                            DatabaseReference myRef = database.getReference("student").child("applyForInternship").child(studentID).child(key);


                            ApplyForInternshipModel applyForInternshipModel = new ApplyForInternshipModel(
                                    firstname, middlename, lastname, userid, address, phone,
                                    companyname, companycontact, companymail, companyaddress, semesterterm, year, crn, credithours, credit, start, enddate, hoursofwork);

                            myRef.setValue(applyForInternshipModel);
                            Toast.makeText(UpdateIntershipDetails.this, "Details Updated.... ", Toast.LENGTH_LONG).show();

                        } catch (Exception e) {
                            e.printStackTrace();
                            //            Toast.makeText(ApplyForInternship.this,"Try again after sometime ",Toast.LENGTH_LONG).show();
                        }
                        finish();
                    }else{
                        Toast.makeText(UpdateIntershipDetails.this, "No Internet Connection", Toast.LENGTH_LONG).show();
                    }
                }

            }
        });

        try {
            if (CommonUtils.isConnectedToInternet(UpdateIntershipDetails.this)){
                getInternshipDetails();
            }else{
                Toast.makeText(UpdateIntershipDetails.this, "No Internet Connection", Toast.LENGTH_LONG).show();
            }
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    private void getInternshipDetails() {

        String key=getIntent().getStringExtra("STUDENTKEY");

        progressDialog=new ProgressDialog(UpdateIntershipDetails.this);
        progressDialog.setMessage("Fetching Details....");
        progressDialog.show();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        String studentID = FirebaseAuth.getInstance().getCurrentUser().getUid();
        DatabaseReference myRef = database.getReference("student").child("applyForInternship").child(studentID).child(key);

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot!=null){
                    ApplyForInternshipModel model=snapshot.getValue(ApplyForInternshipModel.class);
                    if(model!=null){
                        Log.d("ApplyForInternshipModel",model.toString());
                        setDatatoViews(model);
                    }else
                    {
                        Toast.makeText(UpdateIntershipDetails.this, "No Data", Toast.LENGTH_LONG).show();
                        progressDialog.cancel();
                        finish();
                    }

                }
                progressDialog.cancel();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(UpdateIntershipDetails.this, "Please try again after sometime....", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void setDatatoViews(ApplyForInternshipModel model) {
        enteryear.setText(model.getYear());
        entercredit.setText(model.getCreditTitle());
        enterstart.setText(model.getStartDate());
        enterenddate.setText(model.getEnddate());
        enterhoursofwork.setText(model.getHoursofwork());
        entercredithours.setText(model.getCreditHours());
        entercrn.setText(model.getCrn());
        enterfirstname.setText(model.getFirstName());
        entermiddlename.setText(model.getMiddleName());
        enterlastname.setText(model.getLastName());
        enteruserid.setText(model.getUserId());
        enteraddress.setText(model.getAddress());
        enterPhone.setText(model.getPhoneNumber());
        entercompanucontact.setText(model.getCompanyContact());
        enterCompanyname.setText(model.getCompanyName());
        entercompanymail.setText(model.getCompanyMail());
        entercompanyaddress.setText(model.getCompanyAddress());
        entersemeterterm.setText(model.getSemesterTerm());
    }
}
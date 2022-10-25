package com.example.internshipmanagementsystem.student;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
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

import java.util.ArrayList;
import java.util.Arrays;

public class InternShipUpdateone extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    Button btnContinue;
    TextInputEditText enterfirstname, entermiddlename, enterlastname, enteruserid, enteraddress, enterPhone, enterCompanyname, entercompanucontact,
            entercompanymail, entercompanyaddress, entersemeterterm, enteryear, entercrn, entercredithours, entercredit, enterstart,
            enterenddate, enterhoursofwork;

    private Spinner spinnerFall, spinnerYear;
    String semYear, totalyear;
    private static final String[] paths = {"Fall", "Spring", "Summer"};

    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intern_ship_updateone);
        btnContinue = findViewById(R.id.btnContinue);
        spinnerYear = findViewById(R.id.spinnerYear);
        entercredit = findViewById(R.id.entercredit);
        entercredithours = findViewById(R.id.entercredithours);
        entercrn = findViewById(R.id.entercrn);
        enterfirstname = findViewById(R.id.enterfirstname);
        entermiddlename = findViewById(R.id.entermiddlename);
        enterlastname = findViewById(R.id.enterlastname);
        enteruserid = findViewById(R.id.enteruserid);
        enteraddress = findViewById(R.id.enteraddress);
        enterPhone = findViewById(R.id.enterPhone);
        spinnerFall = findViewById(R.id.spinnerFall);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(InternShipUpdateone.this,
                android.R.layout.simple_spinner_item, paths);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerFall.setAdapter(adapter);
        spinnerFall.setOnItemSelectedListener(this);
        semYear = "Fall";

        ArrayAdapter<String> yearadapter = new ArrayAdapter<String>(InternShipUpdateone.this,
                android.R.layout.simple_spinner_item, getYear());

        yearadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerYear.setAdapter(yearadapter);
        spinnerYear.setOnItemSelectedListener(this);
        totalyear = "1950";

        spinnerYear.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                totalyear = String.valueOf(adapterView.getItemAtPosition(i));

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        try {
            if (CommonUtils.isConnectedToInternet(InternShipUpdateone.this)) {
                getInternApplication();
            } else {
                Toast.makeText(InternShipUpdateone.this, "No Internet Connection", Toast.LENGTH_LONG).show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


        btnContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                String semesterterm = semYear;
                String phone = enterPhone.getText().toString();
                String address = enteraddress.getText().toString();
                String userid = enteruserid.getText().toString();
                String middlename = entermiddlename.getText().toString();
                String crn = entercrn.getText().toString();
                String credithours = entercredithours.getText().toString();
                String credit = entercredit.getText().toString();
                String firstname = enterfirstname.getText().toString();
                String lastname = enterlastname.getText().toString();
                String year = totalyear;

                if (TextUtils.isEmpty(firstname)) {
                    Toast.makeText(InternShipUpdateone.this, "Enter firstname ", Toast.LENGTH_LONG).show();
                } else if (TextUtils.isEmpty(lastname)) {
                    Toast.makeText(InternShipUpdateone.this, "Enter lastname ", Toast.LENGTH_LONG).show();
                } else if (TextUtils.isEmpty(semesterterm)) {
                    Toast.makeText(InternShipUpdateone.this, "Enter Semester Term", Toast.LENGTH_LONG).show();
                } else if (TextUtils.isEmpty(year)) {
                    Toast.makeText(InternShipUpdateone.this, "Enter year ", Toast.LENGTH_LONG).show();
                } else if (TextUtils.isEmpty(lastname)) {
                    Toast.makeText(InternShipUpdateone.this, "Enter lastname ", Toast.LENGTH_LONG).show();
                } else if (TextUtils.isEmpty(credit)) {
                    Toast.makeText(InternShipUpdateone.this, "Enter credit ", Toast.LENGTH_LONG).show();
                } else if (TextUtils.isEmpty(credithours)) {
                    Toast.makeText(InternShipUpdateone.this, "Enter credit hours ", Toast.LENGTH_LONG).show();
                } else if (!checkHours(credithours)) {
                    Toast.makeText(InternShipUpdateone.this, "Enter credit hours between 1-5", Toast.LENGTH_LONG).show();
                } else if (TextUtils.isEmpty(crn)) {
                    Toast.makeText(InternShipUpdateone.this, "Enter crn ", Toast.LENGTH_LONG).show();
                } else if (TextUtils.isEmpty(userid)) {
                    Toast.makeText(InternShipUpdateone.this, "Enter userid ", Toast.LENGTH_LONG).show();
                } else if (TextUtils.isEmpty(address)) {
                    Toast.makeText(InternShipUpdateone.this, "Enter address ", Toast.LENGTH_LONG).show();
                } else if (TextUtils.isEmpty(phone)) {
                    Toast.makeText(InternShipUpdateone.this, "Enter phone ", Toast.LENGTH_LONG).show();
                } else if (!(phone.length() == 10)) {
                    Toast.makeText(InternShipUpdateone.this, "Enter correct phone number  ", Toast.LENGTH_LONG).show();
                } else {
                    String key = getIntent().getStringExtra("STUDENTKEY");

                    Intent intent = new Intent(InternShipUpdateone.this, InternShipUpdatetwo.class);
                    intent.putExtra("firstname", firstname);
                    intent.putExtra("lastname", lastname);
                    intent.putExtra("credit", credit);
                    intent.putExtra("semesterterm", semesterterm);
                    intent.putExtra("year", year);
                    intent.putExtra("credithours", credithours);
                    intent.putExtra("crn", crn);
                    intent.putExtra("middlename", middlename);
                    intent.putExtra("address", address);
                    intent.putExtra("userid", userid);
                    intent.putExtra("phone", phone);
                    intent.putExtra("STUDENTKEY", key);
                    startActivity(intent);

                }


            }
        });

    }

    private void getInternApplication() {

        String key = getIntent().getStringExtra("STUDENTKEY");

        progressDialog = new ProgressDialog(InternShipUpdateone.this);
        progressDialog.setMessage("Fetching Details....");
        progressDialog.show();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        String studentID = FirebaseAuth.getInstance().getCurrentUser().getUid();
        DatabaseReference myRef = database.getReference("student").child("applyForInternship").child(studentID).child(key);


        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot != null) {
                    ApplyForInternshipModel model = snapshot.getValue(ApplyForInternshipModel.class);
                    if (model != null) {
                        Log.d("ApplyForInternshipModel", model.toString());
                        setDatatoViews(model);
                    } else {
                        Toast.makeText(InternShipUpdateone.this, "No Data", Toast.LENGTH_LONG).show();
                        progressDialog.cancel();
                        finish();
                    }

                }
                progressDialog.cancel();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(InternShipUpdateone.this, "Please try again after sometime....", Toast.LENGTH_SHORT).show();
            }
        });


    }

    private void setDatatoViews(ApplyForInternshipModel model) {
        enterfirstname.setText(model.getFirstName());
        entermiddlename.setText(model.getMiddleName());
        enterlastname.setText(model.getLastName());
        enteruserid.setText(model.getUserId());
        enteraddress.setText(model.getAddress());
        enterPhone.setText(model.getPhoneNumber());
        entercrn.setText(model.getCrn());
        entercredithours.setText(model.getCreditHours());
        entercredit.setText(model.getCreditTitle());
        spinnerFall.setSelection(Arrays.asList(paths).indexOf(model.getSemesterTerm()));
        spinnerYear.setSelection(Arrays.asList(paths).indexOf(model.getYear()));


    }

    private boolean checkHours(String credithours) {
        int hours = Integer.parseInt(credithours);
        if (hours >= 1 && hours <= 5) {
            return true;
        } else {
            return false;
        }

    }

    private ArrayList<String> getYear() {
        ArrayList<String> list = new ArrayList<>();
        for (int i = 1950; i <= 2100; i++) {
            list.add("" + i);
        }
        return list;
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        switch (i) {
            case 0:
                semYear = "Fall";
                break;
            case 1:
                semYear = "Spring";
                break;
            case 2:
                semYear = "Summer";
                break;

        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
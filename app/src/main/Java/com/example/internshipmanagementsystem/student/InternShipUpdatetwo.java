package com.example.internshipmanagementsystem.student;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
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

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class InternShipUpdatetwo extends AppCompatActivity {


    Button btnContinue;
    TextInputEditText enterCompanyname, entercompanucontact,
            entercompanymail, entercompanyaddress,enterstart,
            enterenddate, enterhoursofwork,enterFacultyname,enterFacultymail;
    TextView startDate,endDate;
    String random="";
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intern_ship_updatetwo);


        enterhoursofwork = findViewById(R.id.enterhoursofwork);
        entercompanucontact = findViewById(R.id.entercompanucontact);
        enterCompanyname = findViewById(R.id.enterCompanyname);
        entercompanymail = findViewById(R.id.entercompanymail);
        entercompanyaddress = findViewById(R.id.entercompanyaddress);
        enterFacultymail = findViewById(R.id.enterFacultymail);
        enterFacultyname = findViewById(R.id.enterFacultyname);
        btnContinue = findViewById(R.id.btnContinue);
        startDate = findViewById(R.id.startDate);
        endDate = findViewById(R.id.endDate);


        try {
            if (CommonUtils.isConnectedToInternet(InternShipUpdatetwo.this)) {
                getInternApplication();
            } else {
                Toast.makeText(InternShipUpdatetwo.this, "No Internet Connection", Toast.LENGTH_LONG).show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }



        endDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar c = Calendar.getInstance();
                int mYear = c.get(Calendar.YEAR);
                int mMonth = c.get(Calendar.MONTH);
                int mDay = c.get(Calendar.DAY_OF_MONTH);


                DatePickerDialog datePickerDialog = new DatePickerDialog(InternShipUpdatetwo.this,
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
                                endDate.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);

                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.show();

            }

        });
        startDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar c = Calendar.getInstance();
                int mYear = c.get(Calendar.YEAR);
                int mMonth = c.get(Calendar.MONTH);
                int mDay = c.get(Calendar.DAY_OF_MONTH);


                DatePickerDialog datePickerDialog = new DatePickerDialog(InternShipUpdatetwo.this,
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
                                startDate.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);

                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.show();

            }

            private void updateData(Calendar myCalendar) {
                String myFormat="MM/dd/yy";
                SimpleDateFormat dateFormat=new SimpleDateFormat(myFormat, Locale.US);
                startDate.setText(dateFormat.format(myCalendar.getTime()));
            }
        });

        btnContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String companyaddress = entercompanyaddress.getText().toString();
                String companymail = entercompanymail.getText().toString();
                String companyname = enterCompanyname.getText().toString();
                String companycontact = entercompanucontact.getText().toString();
                String hoursofwork = enterhoursofwork.getText().toString();
                String enddate = endDate.getText().toString();
                String start = startDate.getText().toString();
                String facultymail = enterFacultymail.getText().toString();
                String facultyname = enterFacultyname.getText().toString();

                if (TextUtils.isEmpty(start)) {
                    Toast.makeText(InternShipUpdatetwo.this, "Enter start ", Toast.LENGTH_LONG).show();
                } else if (TextUtils.isEmpty(enddate)) {
                    Toast.makeText(InternShipUpdatetwo.this, "Enter enddate ", Toast.LENGTH_LONG).show();
                } else if (TextUtils.isEmpty(hoursofwork)) {
                    Toast.makeText(InternShipUpdatetwo.this, "Enter hoursofwork ", Toast.LENGTH_LONG).show();
                }
                else if(!checkHoursofWork(hoursofwork)){
                    Toast.makeText(InternShipUpdatetwo.this, "Enter hoursofwork between 1-40 ", Toast.LENGTH_LONG).show();
                }
                else if (TextUtils.isEmpty(companycontact)) {
                    Toast.makeText(InternShipUpdatetwo.this, "Enter companycontact ", Toast.LENGTH_LONG).show();
                }
                else if(!(companycontact.length()==10)){
                    Toast.makeText(InternShipUpdatetwo.this, "Enter company contact ", Toast.LENGTH_LONG).show();
                }
                else if (TextUtils.isEmpty(companyname)) {
                    Toast.makeText(InternShipUpdatetwo.this, "Enter companyname ", Toast.LENGTH_LONG).show();
                } else if (TextUtils.isEmpty(companymail)) {
                    Toast.makeText(InternShipUpdatetwo.this, "Enter companymail ", Toast.LENGTH_LONG).show();
                }
                else if(!checkMail(companymail)){
                    Toast.makeText(InternShipUpdatetwo.this, "Enter company mail ", Toast.LENGTH_LONG).show();
                }
                else if (TextUtils.isEmpty(companyaddress)) {
                    Toast.makeText(InternShipUpdatetwo.this, "Enter companyaddress ", Toast.LENGTH_LONG).show();
                } else if (TextUtils.isEmpty(facultyname)) {
                    Toast.makeText(InternShipUpdatetwo.this, "Enter facultyname ", Toast.LENGTH_LONG).show();
                } else if (TextUtils.isEmpty(facultymail)) {
                    Toast.makeText(InternShipUpdatetwo.this, "Enter facultymail ", Toast.LENGTH_LONG).show();
                }
                else if(!checkMail(facultymail)){
                    Toast.makeText(InternShipUpdatetwo.this, "Enter faculty mail ", Toast.LENGTH_LONG).show();
                } else if(!(isDateAfter(start,enddate))){
                    Toast.makeText(InternShipUpdatetwo.this, "Please check Date", Toast.LENGTH_LONG).show();
                }
                else{

                    String firstname=getIntent().getStringExtra("firstname");
                    String lastname=getIntent().getStringExtra("lastname");
                    String credit=getIntent().getStringExtra("credit");
                    String semesterterm=getIntent().getStringExtra("semesterterm");
                    String year=getIntent().getStringExtra("year");
                    String credithours=getIntent().getStringExtra("credithours");
                    String crn=getIntent().getStringExtra("crn");
                    String middlename=getIntent().getStringExtra("middlename");
                    String address=getIntent().getStringExtra("address");
                    String userid=getIntent().getStringExtra("userid");
                    String phone=getIntent().getStringExtra("phone");

                    Intent intent=new Intent(InternShipUpdatetwo.this,StudentAgreement.class);
                    intent.putExtra("start",start);
                    intent.putExtra("year",year);
                    intent.putExtra("credithours",credithours);
                    intent.putExtra("userid",userid);
                    intent.putExtra("phone",phone);
                    intent.putExtra("address",address);
                    intent.putExtra("middlename",middlename);
                    intent.putExtra("crn",crn);
                    intent.putExtra("enddate",enddate);
                    intent.putExtra("companyaddress",companyaddress);
                    intent.putExtra("hoursofwork",hoursofwork);
                    intent.putExtra("companycontact",companycontact);
                    intent.putExtra("companyname",companyname);
                    intent.putExtra("companymail",companymail);
                    intent.putExtra("facultyname",facultyname);
                    intent.putExtra("facultymail",facultymail);
                    intent.putExtra("firstname",firstname);
                    intent.putExtra("lastname",lastname);
                    intent.putExtra("credit",credit);
                    intent.putExtra("semesterterm",semesterterm);
                    startActivity(intent);


                    if (CommonUtils.isConnectedToInternet(InternShipUpdatetwo.this)) {
                        try {
                            String key = getIntent().getStringExtra("STUDENTKEY");

                            FirebaseDatabase database = FirebaseDatabase.getInstance();
                            String studentID = FirebaseAuth.getInstance().getCurrentUser().getUid();
                            DatabaseReference myRef = database.getReference("student").child("applyForInternship").child(studentID).child(key);

                            ApplyForInternshipModel applyForInternshipModel = new ApplyForInternshipModel(
                                    firstname, middlename, lastname, userid, address, phone,
                                    companyname, companycontact, companymail, companyaddress, semesterterm, year, crn, credithours, credit, start, enddate, hoursofwork, facultymail, facultyname, random, key);

                            myRef.setValue(applyForInternshipModel);
                            Intent intents = new Intent(InternShipUpdatetwo.this, StudentDashboard.class);
                            startActivity(intents);
                            finish();

                        } catch (Exception e) {
                            e.printStackTrace();
                            Toast.makeText(InternShipUpdatetwo.this, "Try again after sometime ", Toast.LENGTH_LONG).show();
                        }

                    } else {
                        Toast.makeText(InternShipUpdatetwo.this, "No Internet Connection", Toast.LENGTH_LONG).show();
                    }






                }

            }
        });

    }

    private void getInternApplication() {

        String key = getIntent().getStringExtra("STUDENTKEY");

        progressDialog = new ProgressDialog(InternShipUpdatetwo.this);
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
                        Toast.makeText(InternShipUpdatetwo.this, "No Data", Toast.LENGTH_LONG).show();
                        progressDialog.cancel();
                        finish();
                    }

                }
                progressDialog.cancel();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(InternShipUpdatetwo.this, "Please try again after sometime....", Toast.LENGTH_SHORT).show();
            }
        });



    }

    private void setDatatoViews(ApplyForInternshipModel model) {
        enterFacultyname.setText(model.getFacultyname());
        enterFacultymail.setText(model.getFacultymail());
        enterCompanyname.setText(model.getCompanyName());
        entercompanucontact.setText(model.getCompanyContact());
        entercompanymail.setText(model.getCompanyMail());
        enterhoursofwork.setText(model.getHoursofwork());
        entercompanyaddress.setText(model.getCompanyAddress());
        entercompanyaddress.setText(model.getCompanyAddress());
        endDate.setText(model.getEnddate());
        startDate.setText(model.getStartDate());
        startDate.setText(model.getStartDate());
        random=model.getRandom();
    }


    public static boolean isDateAfter(String startDate,String endDate)
    {
        try
        {
            String myFormatString = "yyyy-M-dd"; // for example
            SimpleDateFormat df = new SimpleDateFormat(myFormatString);
            Date date1 = df.parse(endDate);
            Date startingDate = df.parse(startDate);

            if (date1.after(startingDate))
                return true;
            else
                return false;
        }
        catch (Exception e)
        {

            return false;
        }
    }

    private boolean checkMail(String companymail) {

        if(Patterns.EMAIL_ADDRESS.matcher(companymail.toString()).matches()){
            return true;
        }else{
            return false;
        }
    }

    private boolean checkHoursofWork(String hoursofwork) {
        int hours=Integer.parseInt(hoursofwork);
        if(hours>=1 && hours<=40){
            return true;
        } else {
            return false;
        }

    }
}
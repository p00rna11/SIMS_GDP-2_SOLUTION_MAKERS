package com.example.internshipmanagementsystem.student;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import com.example.internshipmanagementsystem.R;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class EmployerInfoActivity extends AppCompatActivity {

    Button btnContinue;
    TextInputEditText  enterCompanyname, entercompanucontact,
            entercompanymail, entercompanyaddress,enterstart,
            enterenddate, enterhoursofwork,enterFacultyname,enterFacultymail;
    TextView startDate,endDate;

    TextInputLayout enterStartDate,enterEndDate;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employer_info);
      //  enterstart = findViewById(R.id.enterstart);
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




        endDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar c = Calendar.getInstance();
                int mYear = c.get(Calendar.YEAR);
                int mMonth = c.get(Calendar.MONTH);
                int mDay = c.get(Calendar.DAY_OF_MONTH);


                DatePickerDialog datePickerDialog = new DatePickerDialog(EmployerInfoActivity.this,
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


                DatePickerDialog datePickerDialog = new DatePickerDialog(EmployerInfoActivity.this,
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
/*
        enterEndDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });*/

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
                    Toast.makeText(EmployerInfoActivity.this, "Enter start ", Toast.LENGTH_LONG).show();
                } else if (TextUtils.isEmpty(enddate)) {
                    Toast.makeText(EmployerInfoActivity.this, "Enter enddate ", Toast.LENGTH_LONG).show();
                } else if (TextUtils.isEmpty(hoursofwork)) {
                    Toast.makeText(EmployerInfoActivity.this, "Enter hoursofwork ", Toast.LENGTH_LONG).show();
                }
                else if(!checkHoursofWork(hoursofwork)){
                    Toast.makeText(EmployerInfoActivity.this, "Enter hoursofwork between 1-40 ", Toast.LENGTH_LONG).show();
                }
                else if (TextUtils.isEmpty(companycontact)) {
                    Toast.makeText(EmployerInfoActivity.this, "Enter companycontact ", Toast.LENGTH_LONG).show();
                }
                else if(!(companycontact.length()==10)){
                    Toast.makeText(EmployerInfoActivity.this, "Enter company contact ", Toast.LENGTH_LONG).show();
                }
                else if (TextUtils.isEmpty(companyname)) {
                    Toast.makeText(EmployerInfoActivity.this, "Enter companyname ", Toast.LENGTH_LONG).show();
                } else if (TextUtils.isEmpty(companymail)) {
                    Toast.makeText(EmployerInfoActivity.this, "Enter companymail ", Toast.LENGTH_LONG).show();
                }
                else if(!checkMail(companymail)){
                    Toast.makeText(EmployerInfoActivity.this, "Enter company mail ", Toast.LENGTH_LONG).show();
                }
                else if (TextUtils.isEmpty(companyaddress)) {
                    Toast.makeText(EmployerInfoActivity.this, "Enter companyaddress ", Toast.LENGTH_LONG).show();
                } else if (TextUtils.isEmpty(facultyname)) {
                    Toast.makeText(EmployerInfoActivity.this, "Enter facultyname ", Toast.LENGTH_LONG).show();
                } else if (TextUtils.isEmpty(facultymail)) {
                    Toast.makeText(EmployerInfoActivity.this, "Enter facultymail ", Toast.LENGTH_LONG).show();
                }
                else if(!checkMail(facultymail)){
                    Toast.makeText(EmployerInfoActivity.this, "Enter faculty mail ", Toast.LENGTH_LONG).show();
                } else if(!(isDateAfter(start,enddate))){
                    Toast.makeText(EmployerInfoActivity.this, "Please check Date", Toast.LENGTH_LONG).show();
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

                    Intent intent=new Intent(EmployerInfoActivity.this,StudentAgreement.class);
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
                }

            }
        });


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
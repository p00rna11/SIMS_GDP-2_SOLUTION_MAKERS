package com.example.internshipmanagementsystem.student;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.internshipmanagementsystem.CommonUtils;
import com.example.internshipmanagementsystem.MainActivity;
import com.example.internshipmanagementsystem.R;
import com.example.internshipmanagementsystem.model.ApplyForInternshipModel;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Random;

public class ApplyForInternship extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    Button btnContinue;
    TextInputEditText enterfirstname, entermiddlename, enterlastname, enteruserid, enteraddress, enterPhone, enterCompanyname, entercompanucontact,
            entercompanymail, entercompanyaddress, entersemeterterm, enteryear, entercrn, entercredithours, entercredit, enterstart,
            enterenddate, enterhoursofwork;

    private Spinner spinnerFall,spinnerYear;
    String semYear,totalyear;
    private static final String[] paths = {"Fall", "Spring", "Summer"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_apply_for_internship);
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
        btnContinue = findViewById(R.id.btnContinue);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(ApplyForInternship.this,
                android.R.layout.simple_spinner_item,paths);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerFall.setAdapter(adapter);
        spinnerFall.setOnItemSelectedListener(this);
        semYear="Fall";

        ArrayAdapter<String> yearadapter = new ArrayAdapter<String>(ApplyForInternship.this,
                android.R.layout.simple_spinner_item,getYear());

        yearadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerYear.setAdapter(yearadapter);
        spinnerYear.setOnItemSelectedListener(this);
        totalyear="1950";

        spinnerYear.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                        totalyear = String.valueOf(adapterView.getItemAtPosition(i));

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


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
                    Toast.makeText(ApplyForInternship.this, "Enter firstname ", Toast.LENGTH_LONG).show();
                } else if (TextUtils.isEmpty(lastname)) {
                    Toast.makeText(ApplyForInternship.this, "Enter lastname ", Toast.LENGTH_LONG).show();
                } else if (TextUtils.isEmpty(semesterterm)) {
                    Toast.makeText(ApplyForInternship.this, "Enter Semester Term", Toast.LENGTH_LONG).show();
                } else if (TextUtils.isEmpty(year)) {
                    Toast.makeText(ApplyForInternship.this, "Enter year ", Toast.LENGTH_LONG).show();
                } else if (TextUtils.isEmpty(lastname)) {
                    Toast.makeText(ApplyForInternship.this, "Enter lastname ", Toast.LENGTH_LONG).show();
                } else if (TextUtils.isEmpty(credit)) {
                    Toast.makeText(ApplyForInternship.this, "Enter credit ", Toast.LENGTH_LONG).show();
                } else if (TextUtils.isEmpty(credithours)) {
                    Toast.makeText(ApplyForInternship.this, "Enter credit hours ", Toast.LENGTH_LONG).show();
                }
                 else if(!checkHours(credithours)){
                    Toast.makeText(ApplyForInternship.this, "Enter credit hours between 1-5", Toast.LENGTH_LONG).show();
                }

                else if (TextUtils.isEmpty(crn)) {
                    Toast.makeText(ApplyForInternship.this, "Enter crn ", Toast.LENGTH_LONG).show();
                } else if (TextUtils.isEmpty(userid)) {
                    Toast.makeText(ApplyForInternship.this, "Enter userid ", Toast.LENGTH_LONG).show();
                } else if (TextUtils.isEmpty(address)) {
                    Toast.makeText(ApplyForInternship.this, "Enter address ", Toast.LENGTH_LONG).show();
                } else if (TextUtils.isEmpty(phone)) {
                    Toast.makeText(ApplyForInternship.this, "Enter phone ", Toast.LENGTH_LONG).show();
                } else if(!(phone.length()==10)){
                    Toast.makeText(ApplyForInternship.this, "Enter correct phone number  ", Toast.LENGTH_LONG).show();
                }else  {

                    Intent intent=new Intent(ApplyForInternship.this,EmployerInfoActivity.class);
                    intent.putExtra("firstname",firstname);
                    intent.putExtra("lastname",lastname);
                    intent.putExtra("credit",credit);
                    intent.putExtra("semesterterm",semesterterm);
                    intent.putExtra("year",year);
                    intent.putExtra("credithours",credithours);
                    intent.putExtra("crn",crn);
                    intent.putExtra("middlename",middlename);
                    intent.putExtra("address",address);
                    intent.putExtra("userid",userid);
                    intent.putExtra("phone",phone);
                    startActivity(intent);
                }

            }
        });


    }

    private boolean checkHours(String credithours) {
       int hours=Integer.parseInt(credithours);
       if(hours>=1 && hours<=5){
           return true;
       } else {
           return false;
       }

    }

    private ArrayList<String> getYear() {
        ArrayList<String> list=new ArrayList<>();
        for(int i=1950;i<=2100;i++)
        {
            list.add(""+i);
        }
        return list;
    }


    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        switch (i) {
            case 0:
                semYear="Fall";
                break;
            case 1:
                semYear="Spring";
                break;
            case 2:
                semYear="Summer";
                break;

        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
        // TODO Auto-generated method stub
    }
}
package com.example.internshipmanagementsystem.faculty;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.internshipmanagementsystem.CommonUtils;
import com.example.internshipmanagementsystem.MainActivity;
import com.example.internshipmanagementsystem.R;
import com.example.internshipmanagementsystem.model.ApplyForInternshipModel;
import com.example.internshipmanagementsystem.student.ConfirmationActivity;
import com.example.internshipmanagementsystem.student.InternShipUpdatetwo;
import com.example.internshipmanagementsystem.student.StudentAgreement;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class InternStudentDetails extends AppCompatActivity {

    TextView details_hrsofwork, details_enddate, details_startdate, details_companyaddress, details_companyemail, details_companycontact,
            details_companyname, details_title, details_credithrs, details_crn, details_year, details_semester, details_phonenumber, details_userid, rejected,
            details_lastname, details_middlename, details_firstname;
    Button intern_approve, showOfferletter;
    EditText id_comment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intern_student_details);

        details_firstname = findViewById(R.id.details_firstname);
        details_lastname = findViewById(R.id.details_lastname);
        id_comment = findViewById(R.id.id_comment);
        showOfferletter = findViewById(R.id.showOfferletter);
        details_middlename = findViewById(R.id.details_middlename);
        rejected = findViewById(R.id.rejected);
        details_firstname = findViewById(R.id.details_firstname);
        details_hrsofwork = findViewById(R.id.details_hrsofwork);
        details_enddate = findViewById(R.id.details_enddate);
        intern_approve = findViewById(R.id.intern_approve);
        details_companyaddress = findViewById(R.id.details_companyaddress);
        details_startdate = findViewById(R.id.details_startdate);
        details_companyemail = findViewById(R.id.details_companyemail);
        details_companyname = findViewById(R.id.details_companyname);
        details_companycontact = findViewById(R.id.details_companycontact);
        details_title = findViewById(R.id.details_title);
        details_crn = findViewById(R.id.details_crn);
        details_credithrs = findViewById(R.id.details_credithrs);
        details_year = findViewById(R.id.details_year);
        details_semester = findViewById(R.id.details_semester);
        details_phonenumber = findViewById(R.id.details_phonenumber);
        details_userid = findViewById(R.id.details_userid);

        ApplyForInternshipModel applyForInternshipModel = (ApplyForInternshipModel) getIntent().getSerializableExtra("interndetails");
        details_userid.setText(applyForInternshipModel.getUserId());
        details_phonenumber.setText(applyForInternshipModel.getPhoneNumber());
        details_semester.setText(applyForInternshipModel.getSemesterTerm());
        details_crn.setText(applyForInternshipModel.getCrn());
        details_year.setText(applyForInternshipModel.getYear());
        details_credithrs.setText(applyForInternshipModel.getCreditHours());
        details_companyname.setText(applyForInternshipModel.getCompanyName());
        details_title.setText(applyForInternshipModel.getCreditTitle());
        details_companycontact.setText(applyForInternshipModel.getCompanyContact());
        details_companyaddress.setText(applyForInternshipModel.getCompanyAddress());
        details_companyemail.setText(applyForInternshipModel.getCompanyMail());
        details_lastname.setText(applyForInternshipModel.getLastName());
        details_firstname.setText(applyForInternshipModel.getFirstName());
        details_hrsofwork.setText(applyForInternshipModel.getHoursofwork());
        details_enddate.setText(applyForInternshipModel.getEnddate());
        details_middlename.setText(applyForInternshipModel.getMiddleName());
        details_startdate.setText(applyForInternshipModel.getStartDate());


        intern_approve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String comment=id_comment.getText().toString();
                approveIntern(applyForInternshipModel, comment);

            }
        });

        showOfferletter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(InternStudentDetails.this, OfferletterPreviewActivity.class);
                intent.putExtra("IMAGE", applyForInternshipModel.getOfferletter());
                startActivity(intent);
            }
        });

        rejected.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String comment=id_comment.getText().toString();

                rejectIntern(applyForInternshipModel, comment);
            }
        });

        try {
            int bool=getIntent().getIntExtra("TRUEEE",22);
            if(bool==11){
                id_comment.setVisibility(View.VISIBLE);
                rejected.setVisibility(View.VISIBLE);
                intern_approve.setVisibility(View.VISIBLE);
            }else{
                id_comment.setVisibility(View.GONE);
                rejected.setVisibility(View.GONE);
                intern_approve.setVisibility(View.GONE);
            }
        }catch (Exception e){

        }

    }

    private void rejectIntern(ApplyForInternshipModel model, String te) {

        if (CommonUtils.isConnectedToInternet(InternStudentDetails.this)) {
            try {

                FirebaseDatabase database = FirebaseDatabase.getInstance();
                String studentID =model.getStudentID();
                String key = model.getKey();
                DatabaseReference myRef = database.getReference("student").child("applyForInternship").child(studentID).child(key);
                String mail=model.getFacultymail().replace("@","_");
                String fmail=mail.replace(".","_");
                DatabaseReference facultyDatabase = database.getReference("facultyDatabase").child("studentInternshipDetails").child(fmail).child(key);

                ApplyForInternshipModel applyForInternshipModels = new ApplyForInternshipModel(
                        model.getFirstName(), model.getMiddleName(), model.getLastName(), model.getUserId(), model.getAddress(), model.getPhoneNumber(),
                        model.getCompanyName(), model.getCompanyContact(), model.getCompanyMail(), model.getCompanyAddress(), model.getSemesterTerm(), model.getYear(), model.getCrn(), model.getCreditHours(), model.getCreditTitle(), model.getStartDate(), model.getEnddate(), model.getHoursofwork(), model.getFacultymail(), model.getFacultyname(), model.getRandom(), key,"REJECTED",studentID,model.getOfferletter(),te);

                myRef.setValue(applyForInternshipModels);
                facultyDatabase.setValue(applyForInternshipModels);
                Intent intent=new Intent(InternStudentDetails.this, FacultyDashboard.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|
                        Intent.FLAG_ACTIVITY_CLEAR_TASK |
                        Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("FACULTYEMAIL",mail);
                startActivity(intent);
                finish();


            }catch (Exception e){
                e.printStackTrace();
            }
        }else{
            Toast.makeText(InternStudentDetails.this, "No Internet Connection", Toast.LENGTH_SHORT).show();
        }
    }

    private void approveIntern(ApplyForInternshipModel model, String te) {

        if (CommonUtils.isConnectedToInternet(InternStudentDetails.this)) {
            try {

                FirebaseDatabase database = FirebaseDatabase.getInstance();
                String studentID = model.getStudentID();
                String key = model.getKey();
                DatabaseReference myRef = database.getReference("student").child("applyForInternship").child(studentID).child(key);
                String mail = model.getFacultymail().replace("@", "_");
                String fmail = mail.replace(".", "_");
                DatabaseReference facultyDatabase = database.getReference("facultyDatabase").child("studentInternshipDetails").child(fmail).child(key);

                ApplyForInternshipModel applyForInternshipModels = new ApplyForInternshipModel(
                        model.getFirstName(), model.getMiddleName(), model.getLastName(), model.getUserId(), model.getAddress(), model.getPhoneNumber(),
                        model.getCompanyName(), model.getCompanyContact(), model.getCompanyMail(), model.getCompanyAddress(), model.getSemesterTerm(), model.getYear(), model.getCrn(), model.getCreditHours(), model.getCreditTitle(), model.getStartDate(), model.getEnddate(), model.getHoursofwork(), model.getFacultymail(), model.getFacultyname(), model.getRandom(), key, "APPROVE", studentID, model.getOfferletter(), te);

                myRef.setValue(applyForInternshipModels);
                facultyDatabase.setValue(applyForInternshipModels);
                Intent intent = new Intent(InternStudentDetails.this, FacultyDashboard.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |
                        Intent.FLAG_ACTIVITY_CLEAR_TASK |
                        Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("FACULTYEMAIL", mail);
                startActivity(intent);
                finish();


            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            Toast.makeText(InternStudentDetails.this, "No Internet Connection", Toast.LENGTH_SHORT).show();
        }
    }
}
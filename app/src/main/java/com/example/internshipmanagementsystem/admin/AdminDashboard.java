package com.example.internshipmanagementsystem.admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.internshipmanagementsystem.CommonUtils;
import com.example.internshipmanagementsystem.MainActivity;
import com.example.internshipmanagementsystem.R;
import com.example.internshipmanagementsystem.faculty.CurrentSemeterInternActivity;
import com.example.internshipmanagementsystem.faculty.FacultyDashboard;
import com.example.internshipmanagementsystem.model.ApplyForInternshipModel;
import com.example.internshipmanagementsystem.student.ApplyForInternship;
import com.example.internshipmanagementsystem.student.StudentDashboard;
import com.example.internshipmanagementsystem.student.StudentLoginActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class AdminDashboard extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    CardView crdViewStudents;
    CardView crdViewSupervisors;
    CardView crdStudentView;
    ImageView id_logout;
    TextView loggedInUser, facdep;
    Spinner spinnerFaculty, spinnerDepartment;
    ArrayList<ApplyForInternshipModel> totalInternList;
    ArrayList<ApplyForInternshipModel> approvedInternList;
    ArrayList<ApplyForInternshipModel> rejectedInternList;

    CardView id_totalApplications, id_approvedApplications, id_rejectedApplications;

    private static final String[] facultyList = {"Dr. Aziz Fellah", "Dr. Ajay Bandi", "Dr. Charless Hoot", "Dr. Ratan Lal"};
    private static final String[] departmentList = {"CSIS", "Chemistry", "Mathematics", "Psychology"};


    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_admin_dashboard);
        id_logout = findViewById(R.id.id_logout);
        facdep = findViewById(R.id.facdep);
        spinnerFaculty = findViewById(R.id.spinnerFaculty);
        spinnerDepartment = findViewById(R.id.spinnerDepartment);
        id_rejectedApplications = findViewById(R.id.id_rejectedApplications);
        id_approvedApplications = findViewById(R.id.id_approvedApplications);
        id_totalApplications = findViewById(R.id.id_totalApplications);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(AdminDashboard.this,
                android.R.layout.simple_spinner_item, facultyList);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerFaculty.setAdapter(adapter);
        spinnerFaculty.setOnItemSelectedListener(this);


        id_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AdminDashboard.this, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |
                        Intent.FLAG_ACTIVITY_CLEAR_TASK |
                        Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish();
            }
        });


        id_totalApplications.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (totalInternList.size() > 0) {
                    Intent intent = new Intent(AdminDashboard.this, CurrentSemeterInternActivity.class);
                    intent.putExtra("TOTALINTERLIST", totalInternList);
                    startActivity(intent);
                } else {
                    Toast.makeText(AdminDashboard.this, "No Data", Toast.LENGTH_LONG).show();
                }
            }
        });

        id_rejectedApplications.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (rejectedInternList.size() > 0) {
                    Intent intent = new Intent(AdminDashboard.this, CurrentSemeterInternActivity.class);
                    intent.putExtra("TOTALINTERLIST", rejectedInternList);
                    startActivity(intent);
                } else {
                    Toast.makeText(AdminDashboard.this, "No Data", Toast.LENGTH_LONG).show();
                }
            }
        });

        id_approvedApplications.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (approvedInternList.size() > 0) {
                    Intent intent = new Intent(AdminDashboard.this, CurrentSemeterInternActivity.class);
                    intent.putExtra("TOTALINTERLIST", approvedInternList);
                    startActivity(intent);
                } else {
                    Toast.makeText(AdminDashboard.this, "No Data", Toast.LENGTH_LONG).show();
                }
            }
        });

        ArrayAdapter<String> depadapter = new ArrayAdapter<String>(AdminDashboard.this,
                android.R.layout.simple_spinner_item, departmentList);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerDepartment.setAdapter(depadapter);






/*

        id_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
     //           startActivity(new Intent(AdminDashboard.this, AdminLoginActivity.class));
                finish();
            }
        });


        crdViewStudents.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AdminDashboard.this, DisplayAllStudents.class));
            }
        });

        crdViewSupervisors.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AdminDashboard.this, DisplayAllSupervisors.class));
            }
        });
*/

    }

    @Override
    public void onBackPressed() {

    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        switch (i) {
            case 0:
                ((TextView) adapterView.getChildAt(0)).setTextColor(Color.BLACK); /* if you want your item to be white */
                getFacultyData("Azizfellah@nwmissouri.edu");
                facdep.setText("CSIS");
                break;
            case 1:
                ((TextView) adapterView.getChildAt(0)).setTextColor(Color.BLACK); /* if you want your item to be white */
                getFacultyData("ajaybandi@nwmissouri.edu");
                facdep.setText("Chemistry");

                break;
            case 2:
                ((TextView) adapterView.getChildAt(0)).setTextColor(Color.BLACK); /* if you want your item to be white */
                getFacultyData("Hoot@nwmissouri.edu");
                facdep.setText("Mathematics");

                break;
            case 3:
                ((TextView) adapterView.getChildAt(0)).setTextColor(Color.BLACK); /* if you want your item to be white */
                getFacultyData("Lalratan@nwmissouri.edu");
                facdep.setText("Psychology");

                break;
        }
    }

    private void getFacultyData(String facultyMail) {
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading Data");
        progressDialog.show();
        if (totalInternList == null) {
            totalInternList = new ArrayList<>();
        } else {
            totalInternList.clear();
        }

        if (rejectedInternList == null) {
            rejectedInternList = new ArrayList<>();
        } else {
            rejectedInternList.clear();
        }

        if (approvedInternList == null) {
            approvedInternList = new ArrayList<>();
        } else {
            approvedInternList.clear();
        }

        if (CommonUtils.isConnectedToInternet(AdminDashboard.this)) {
            String maiilfd=facultyMail.toLowerCase();
            String removeUnwantedfrommail = maiilfd.replace("@", "_");
            String mail = removeUnwantedfrommail.replace(".", "_");
            FirebaseDatabase facultyDatabase = FirebaseDatabase.getInstance();
            DatabaseReference facultydatabaserefference = facultyDatabase.getReference("facultyDatabase").child("studentInternshipDetails");
            facultydatabaserefference.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {

                    if (progressDialog.isShowing()) {
                        progressDialog.cancel();
                    }
                    if (snapshot.hasChild(mail)) {
                        facultydatabaserefference.child(mail).addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshots) {
                                if (snapshot.hasChildren()) {
                                    for (DataSnapshot dataSnapshot : snapshots.getChildren()) {
                                        ApplyForInternshipModel applyForInternshipModel = dataSnapshot.getValue(ApplyForInternshipModel.class);
                                        Log.d("ADMINDASHBOARDMODEL", applyForInternshipModel.toString());
                                        totalInternList.add(applyForInternshipModel);

                                        if (applyForInternshipModel.getStatus().equals("REJECTED")) {
                                            rejectedInternList.add(applyForInternshipModel);
                                        }

                                        if (applyForInternshipModel.getStatus().equals("APPROVE")) {
                                            approvedInternList.add(applyForInternshipModel);
                                        }


                                    }
                                } else {
                                    Toast.makeText(AdminDashboard.this, "No Record Found....", Toast.LENGTH_SHORT).show();
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                                if (progressDialog.isShowing()) {
                                    progressDialog.cancel();
                                }
                            }
                        });
                    } else {

                        if (progressDialog.isShowing()) {
                            progressDialog.cancel();
                        }
                        Toast.makeText(AdminDashboard.this, "No Record Found....", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    Toast.makeText(AdminDashboard.this, "No Record Found....", Toast.LENGTH_SHORT).show();

                    if (progressDialog.isShowing()) {
                        progressDialog.cancel();
                    }
                }
            });
        } else {
            if (progressDialog.isShowing()) {
                progressDialog.cancel();
            }
            Toast.makeText(AdminDashboard.this, "Check Internet Connection....", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
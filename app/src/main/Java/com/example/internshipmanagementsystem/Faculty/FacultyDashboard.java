package com.example.internshipmanagementsystem.faculty;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.internshipmanagementsystem.MainActivity;
import com.example.internshipmanagementsystem.R;
import com.example.internshipmanagementsystem.model.ApplyForInternshipModel;
import com.example.internshipmanagementsystem.student.ConfirmationActivity;
import com.example.internshipmanagementsystem.student.InternshipListActivity;
import com.example.internshipmanagementsystem.student.StudentDashboard;
import com.example.internshipmanagementsystem.student.StudentLoginActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class FacultyDashboard extends AppCompatActivity {

    TextView t10, totalInterns, studentState, studentStateout, needApprovals, semIntern, PreviousSemcount, approvedApprovals;
    ImageView id_logout;
    TextView textView13, textView12, approvedtext, tetxt13, isntate, textout, pendingtext, rejectedtext, rejectedApplicationsCount;
    ProgressDialog progressDialog;
    ConstraintLayout id_currentSemeter, id_approvedapprovals, id_rejectedappli, id_PreviousSemeter, id_instate, id_oustate, id_neeedtobeapproved, id_needapprovals, id_totalSemeter;

    ArrayList<ApplyForInternshipModel> totalInternList;
    ArrayList<ApplyForInternshipModel> inStudentList;
    ArrayList<ApplyForInternshipModel> outStudentList;
    ArrayList<ApplyForInternshipModel> pendingList;
    ArrayList<ApplyForInternshipModel> RejectedList;
    ArrayList<ApplyForInternshipModel> ApprovedList;
    ArrayList<ApplyForInternshipModel> currentSem;
    ArrayList<ApplyForInternshipModel> previousSem;

    private void getStudentInternshipDetails() {
        progressDialog = new ProgressDialog(FacultyDashboard.this);
        progressDialog.setMessage("Loading....");
        progressDialog.show();
        totalInternList = new ArrayList<>();
        inStudentList = new ArrayList<>();
        outStudentList = new ArrayList<>();
        pendingList = new ArrayList<>();
        currentSem = new ArrayList<>();
        previousSem = new ArrayList<>();
        RejectedList = new ArrayList<>();
        ApprovedList = new ArrayList<>();
        String facultyMail = getIntent().getStringExtra("FACULTYEMAIL");
        String removeUnwantedfrommail = facultyMail.replace("@", "_");
        String mail = removeUnwantedfrommail.replace(".", "_");
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        String studentID = FirebaseAuth.getInstance().getCurrentUser().getUid();
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);

        HashMap<String, String> newMap = new HashMap<String, String>();
        newMap.put("0", "spring");
        newMap.put("1", "spring");
        newMap.put("2", "spring");
        newMap.put("3", "spring");
        newMap.put("4", "summer");
        newMap.put("5", "summer");
        newMap.put("6", "summer");
        newMap.put("7", "fall");
        newMap.put("8", "fall");
        newMap.put("9", "fall");
        newMap.put("10", "fall");
        newMap.put("11", "fall");

        String value = newMap.get(String.valueOf(month));

        DatabaseReference myRef = database.getReference("facultyDatabase").child("studentInternshipDetails").child(mail);

        progressDialog.show();
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Log.d("FacultyDashboard", snapshot.toString());
                totalInterns.setText(Long.toString(snapshot.getChildrenCount()));
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    ApplyForInternshipModel applyForInternshipModel = dataSnapshot.getValue(ApplyForInternshipModel.class);
                    totalInternList.add(applyForInternshipModel);

                    if (applyForInternshipModel.getAddress().equals("nwmissouri")) {
                        inStudentList.add(applyForInternshipModel);
                    } else {
                        outStudentList.add(applyForInternshipModel);
                    }

                    if (applyForInternshipModel.getStatus().equals("PENDING")) {
                        pendingList.add(applyForInternshipModel);
                    }

                    if (applyForInternshipModel.getStatus().equals("REJECTED")) {
                        RejectedList.add(applyForInternshipModel);
                    }

                    if (applyForInternshipModel.getStatus().equals("APPROVE")) {
                        ApprovedList.add(applyForInternshipModel);
                    }

                    // set current semester


                    try {
                        Calendar calendar = Calendar.getInstance();
                        int year = calendar.get(Calendar.YEAR);
                        int month = calendar.get(Calendar.MONTH);
                        if (applyForInternshipModel.getYear().equals(String.valueOf(year))) {
                            if (applyForInternshipModel.getSemesterTerm().toLowerCase().contains(value)) {
                                currentSem.add(applyForInternshipModel);
                            } else {
                                String prev = "";
                                String sem = applyForInternshipModel.getSemesterTerm();
                                if (value.contains("summer")) {
                                    prev = "Spring";
                                } else if (value.contains("Spring")) {
                                    prev = "Fall";
                                } else if (value.contains("fall")) {
                                    prev = "Summer";
                                }

                                if (prev.contains(sem)) {
                                    previousSem.add(applyForInternshipModel);
                                }

                            }


                        }


                    } catch (Exception e) {
                        e.printStackTrace();
                    }


                    progressDialog.cancel();
                }

                if (inStudentList.size() > 0) {
                    studentState.setText(String.valueOf(inStudentList.size()));
                } else {
                    studentState.setText("0");
                }


                if (outStudentList.size() > 0) {
                    studentStateout.setText(String.valueOf(outStudentList.size()));
                } else {
                    studentStateout.setText("0");
                }

                if (previousSem.size() > 0) {
                    PreviousSemcount.setText(String.valueOf(previousSem.size()));
                } else {
                    PreviousSemcount.setText("0");
                }

                if (currentSem.size() > 0) {
                    semIntern.setText(String.valueOf(currentSem.size()));
                } else {
                    semIntern.setText("0");
                }


                if (pendingList.size() > 0) {
                    needApprovals.setText(String.valueOf(pendingList.size()));
                } else {
                    needApprovals.setText("0");
                }

                if (ApprovedList.size() > 0) {
                    approvedApprovals.setText(String.valueOf(ApprovedList.size()));
                } else {
                    approvedApprovals.setText("0");
                }

                if (RejectedList.size() > 0) {
                    rejectedApplicationsCount.setText(String.valueOf(RejectedList.size()));
                } else {
                    rejectedApplicationsCount.setText("0");
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                progressDialog.cancel();

            }
        });


    }

    private String getSem(String semesterTerm, int month) {
        String sem = "";
        return sem;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_faculty_overall_list);
        t10 = findViewById(R.id.textView7);
        studentState = findViewById(R.id.studentState);
        pendingtext = findViewById(R.id.pendingtext);
        id_rejectedappli = findViewById(R.id.id_rejectedappli);
        id_approvedapprovals = findViewById(R.id.id_approvedapprovals);
        approvedtext = findViewById(R.id.approvedtext);
        textView12 = findViewById(R.id.textView12);
        approvedApprovals = findViewById(R.id.approvedApprovals);
        textView13 = findViewById(R.id.textView13);
        isntate = findViewById(R.id.isntate);
        rejectedtext = findViewById(R.id.rejectedtext);
        rejectedApplicationsCount = findViewById(R.id.rejectedApplicationsCount);
        PreviousSemcount = findViewById(R.id.PreviousSemcount);
        tetxt13 = findViewById(R.id.tetxt13);
        id_needapprovals = findViewById(R.id.id_needapprovals);
        id_neeedtobeapproved = findViewById(R.id.id_neeedtobeapproved);
        id_oustate = findViewById(R.id.id_oustate);
        textout = findViewById(R.id.textout);
        totalInterns = findViewById(R.id.totalInterns);
        id_instate = findViewById(R.id.id_instate);
        semIntern = findViewById(R.id.semIntern);
        needApprovals = findViewById(R.id.needApprovals);
        studentStateout = findViewById(R.id.studentStateout);
        id_currentSemeter = findViewById(R.id.id_currentSemeter);
        id_PreviousSemeter = findViewById(R.id.id_PreviousSemeter);
        id_logout = findViewById(R.id.id_logout);
        id_totalSemeter = findViewById(R.id.id_totalSemeter);


        isntate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (inStudentList.size() > 0) {
                    Intent intent = new Intent(FacultyDashboard.this, StateActivity.class);
                    intent.putExtra("INSTATETOTALINTERLIST", inStudentList);
                    startActivity(intent);
                } else {
                    Toast.makeText(FacultyDashboard.this, "No Data", Toast.LENGTH_SHORT).show();
                }
            }
        });

        rejectedtext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (RejectedList.size() > 0) {
                    Intent intent = new Intent(FacultyDashboard.this, CurrentSemeterInternActivity.class);
                    intent.putExtra("TOTALINTERLIST", RejectedList);
                    startActivity(intent);

                } else {
                    Toast.makeText(FacultyDashboard.this, "No Data", Toast.LENGTH_SHORT).show();
                }
            }
        });

        id_approvedapprovals.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ApprovedList.size() > 0) {
                    Intent intent = new Intent(FacultyDashboard.this, CurrentSemeterInternActivity.class);
                    intent.putExtra("TOTALINTERLIST", ApprovedList);
                    startActivity(intent);

                } else {
                    Toast.makeText(FacultyDashboard.this, "No Data", Toast.LENGTH_SHORT).show();
                }
            }
        });

        approvedtext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ApprovedList.size() > 0) {
                    Intent intent = new Intent(FacultyDashboard.this, CurrentSemeterInternActivity.class);
                    intent.putExtra("TOTALINTERLIST", ApprovedList);
                    startActivity(intent);

                } else {
                    Toast.makeText(FacultyDashboard.this, "No Data", Toast.LENGTH_SHORT).show();
                }
            }
        });

        id_rejectedappli.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (RejectedList.size() > 0) {
                    Intent intent = new Intent(FacultyDashboard.this, CurrentSemeterInternActivity.class);
                    intent.putExtra("TOTALINTERLIST", RejectedList);
                    startActivity(intent);
                } else {
                    Toast.makeText(FacultyDashboard.this, "No Data", Toast.LENGTH_SHORT).show();
                }
            }
        });

        textView13.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (totalInternList.size() > 0) {
                    Intent intent = new Intent(FacultyDashboard.this, CurrentSemeterInternActivity.class);
                    intent.putExtra("TOTALINTERLIST", totalInternList);
                    startActivity(intent);
                } else {
                    Toast.makeText(FacultyDashboard.this, "No Data", Toast.LENGTH_SHORT).show();
                }

            }
        });
        textout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (outStudentList.size() > 0) {
                    Intent intent = new Intent(FacultyDashboard.this, OutStateInternActivity.class);
                    intent.putExtra("OUTSTATETOTALINTERLIST", outStudentList);
                    startActivity(intent);
                } else {
                    Toast.makeText(FacultyDashboard.this, "No Data", Toast.LENGTH_LONG).show();
                }
            }
        });
        pendingtext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String facultyMail = getIntent().getStringExtra("FACULTYEMAIL");
                String removeUnwantedfrommail = facultyMail.replace("@", "_");
                String mail = removeUnwantedfrommail.replace(".", "_");

                if (pendingList.size() > 0) {

                    Intent intent = new Intent(FacultyDashboard.this, NeedApprovalsActivity.class);
                    intent.putExtra("PENDINGINTERLIST", pendingList);
                    intent.putExtra("MAIL", mail);
                    startActivity(intent);

                } else {
                    Toast.makeText(FacultyDashboard.this, "No Data", Toast.LENGTH_LONG).show();
                }
            }
        });
        id_totalSemeter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (totalInternList.size() > 0) {

                    Intent intent = new Intent(FacultyDashboard.this, CurrentSemeterInternActivity.class);
                    intent.putExtra("TOTALINTERLIST", totalInternList);
                    startActivity(intent);
                } else {
                    Toast.makeText(FacultyDashboard.this, "No Data", Toast.LENGTH_LONG).show();
                }
            }
        });

        id_oustate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (outStudentList.size() > 0) {
                    Intent intent = new Intent(FacultyDashboard.this, OutStateInternActivity.class);
                    intent.putExtra("OUTSTATETOTALINTERLIST", outStudentList);
                    startActivity(intent);
                } else {
                    Toast.makeText(FacultyDashboard.this, "No Data", Toast.LENGTH_LONG).show();
                }
            }
        });
        id_PreviousSemeter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (previousSem.size() > 0) {

                    Intent intent = new Intent(FacultyDashboard.this, PrevoiusSemeterInternActivity.class);
                    intent.putExtra("PREVOUS", previousSem);
                    startActivity(intent);

                } else {
                    Toast.makeText(FacultyDashboard.this, "No Data", Toast.LENGTH_LONG).show();

                }
            }
        });

        tetxt13.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (previousSem.size() > 0) {
                    Intent intent = new Intent(FacultyDashboard.this, PrevoiusSemeterInternActivity.class);
                    intent.putExtra("PREVOUS", previousSem);
                    startActivity(intent);
                } else {
                    Toast.makeText(FacultyDashboard.this, "No Data", Toast.LENGTH_LONG).show();

                }


            }
        });


        id_instate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (inStudentList.size() > 0) {
                    Intent intent = new Intent(FacultyDashboard.this, StateActivity.class);
                    intent.putExtra("INSTATETOTALINTERLIST", inStudentList);
                    startActivity(intent);
                } else {
                    Toast.makeText(FacultyDashboard.this, "No Data", Toast.LENGTH_LONG).show();
                }
            }
        });


        textView12.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (currentSem.size() > 0) {
                    Intent intent = new Intent(FacultyDashboard.this, CurrentSemesterActivity.class);
                    intent.putExtra("CURRENTSEM", currentSem);
                    startActivity(intent);
                } else {
                    Toast.makeText(FacultyDashboard.this, "No Data", Toast.LENGTH_LONG).show();
                }

            }
        });

        id_currentSemeter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (currentSem.size() > 0) {
                    Intent intent = new Intent(FacultyDashboard.this, CurrentSemesterActivity.class);
                    intent.putExtra("CURRENTSEM", currentSem);
                    startActivity(intent);
                } else {
                    Toast.makeText(FacultyDashboard.this, "No Data", Toast.LENGTH_LONG).show();

                }

            }

        });

        id_needapprovals.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (pendingList.size() > 0) {
                    String facultyMail = getIntent().getStringExtra("FACULTYEMAIL");
                    String removeUnwantedfrommail = facultyMail.replace("@", "_");
                    String mail = removeUnwantedfrommail.replace(".", "_");

                    Intent intent = new Intent(FacultyDashboard.this, NeedApprovalsActivity.class);
                    intent.putExtra("PENDINGINTERLIST", pendingList);
                    intent.putExtra("MAIL", mail);
                    startActivity(intent);
                } else {
                    Toast.makeText(FacultyDashboard.this, "No Data", Toast.LENGTH_LONG).show();
                }

            }
        });


        id_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(FacultyDashboard.this, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |
                        Intent.FLAG_ACTIVITY_CLEAR_TASK |
                        Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        getStudentInternshipDetails();

    }

    @Override
    public void onBackPressed() {

    }
}
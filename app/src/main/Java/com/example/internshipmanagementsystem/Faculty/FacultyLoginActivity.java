package com.example.internshipmanagementsystem.faculty;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.internshipmanagementsystem.CommonUtils;
import com.example.internshipmanagementsystem.R;
import com.example.internshipmanagementsystem.admin.AdminDashboard;
import com.example.internshipmanagementsystem.admin.AdminLoginActivity;
import com.example.internshipmanagementsystem.student.StudentDashboard;
import com.example.internshipmanagementsystem.student.StudentLoginActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class FacultyLoginActivity extends AppCompatActivity {

    EditText email;
    EditText password;
    Button btnSignIn;
    TextView txtFacultyForgotPassword;
    ProgressDialog progressDialog;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_faculty_login);
        email = (EditText) findViewById(R.id.username);
        password = (EditText) findViewById(R.id.password);
        btnSignIn = findViewById(R.id.login);
        mAuth = FirebaseAuth.getInstance();


        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
/*

                email.setText("facid@nwmissouri.edu");
                password.setText("northwest");
*//*

                email.setText("Azizfellah@nwmissouri.edu");
                password.setText("northwest");
*/

                String emailUsername = email.getText().toString();
                String passwordText = password.getText().toString();




                if (CommonUtils.isConnectedToInternet(FacultyLoginActivity.this)) {

                    if (!TextUtils.isEmpty(emailUsername)) {

                        boolean isDIGIT = true;

                        char[] chars = emailUsername.toCharArray();
                        for (char c : chars) {
                            if (Character.isDigit(c)) {
                                isDIGIT = false;
                            }
                        }

                        if (TextUtils.isEmpty(passwordText)) {
                            Toast.makeText(FacultyLoginActivity.this, "Enter password", Toast.LENGTH_LONG).show();
                        } else {
                            if (isDIGIT) {

                                if (!TextUtils.isDigitsOnly(emailUsername) && (!emailUsername.toLowerCase().contains("admin"))) {
                                    progressDialog = new ProgressDialog(FacultyLoginActivity.this);
                                    progressDialog.setMessage("Loading....");
                                    progressDialog.show();


                                    String email=emailUsername;
                                    mAuth.signInWithEmailAndPassword(emailUsername, passwordText).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                        @Override
                                        public void onComplete(@NonNull Task<AuthResult> task) {
                                            progressDialog.cancel();
                                            if (task.isSuccessful()) {
                                                Intent intent = new Intent(FacultyLoginActivity.this, FacultyDashboard.class);
                                                intent.putExtra("FACULTYEMAIL",email);
                                                startActivity(intent);
                                            } else {
                                                Toast.makeText(FacultyLoginActivity.this, "Inavalid Credentials", Toast.LENGTH_LONG).show();
                                            }

                                        }
                                    }).addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            progressDialog.cancel();

                                            Toast.makeText(FacultyLoginActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
                                        }
                                    });
                                } else {
                                    Toast.makeText(FacultyLoginActivity.this, "Please enter faculty id", Toast.LENGTH_LONG).show();
                                }
                            } else {
                                Toast.makeText(FacultyLoginActivity.this, "Please enter faculty id", Toast.LENGTH_LONG).show();

                            }
                        }
                    } else {
                        Toast.makeText(FacultyLoginActivity.this, "Enter Mail", Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(FacultyLoginActivity.this, "No Internet Connection", Toast.LENGTH_LONG).show();

                }


            }
        });

        txtFacultyForgotPassword = findViewById(R.id.txtFacultyPassword);
        txtFacultyForgotPassword.setMovementMethod(LinkMovementMethod.getInstance());
    }


}
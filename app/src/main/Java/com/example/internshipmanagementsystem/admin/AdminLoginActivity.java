package com.example.internshipmanagementsystem.admin;

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
import android.widget.TextView;
import android.widget.Toast;

import com.example.internshipmanagementsystem.CommonUtils;
import com.example.internshipmanagementsystem.R;
import com.example.internshipmanagementsystem.student.StudentDashboard;
import com.example.internshipmanagementsystem.student.StudentLoginActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class AdminLoginActivity extends AppCompatActivity {

    EditText email;
    EditText password;
    Button btnSignIn;
    TextView txtAdminForgotPassword;
    private FirebaseAuth mAuth;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_login);
        email = (EditText) findViewById(R.id.username);
        password = (EditText) findViewById(R.id.password);
        btnSignIn = findViewById(R.id.login);
        mAuth = FirebaseAuth.getInstance();

        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String emailUsername = email.getText().toString();
                String passwordText = password.getText().toString();
// Default Username And Password
/*
                 emailUsername="admin01@nwmissouri.edu";
                 passwordText="northwest";
*/


                if(CommonUtils.isConnectedToInternet(AdminLoginActivity.this)){

                    if (!TextUtils.isEmpty(emailUsername)) {
                        if (TextUtils.isEmpty(passwordText)) {
                            Toast.makeText(AdminLoginActivity.this, "Enter password", Toast.LENGTH_LONG).show();
                        } else if (emailUsername.contains("admin") || emailUsername.contains("Admin")) {
                            progressDialog=new ProgressDialog(AdminLoginActivity.this);
                            progressDialog.setMessage("Loading....");
                            progressDialog.show();


                            mAuth.signInWithEmailAndPassword(emailUsername, passwordText).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    progressDialog.cancel();

                                    if (task.isSuccessful()) {
                                        Intent intent = new Intent(AdminLoginActivity.this, AdminDashboard.class);
                                        startActivity(intent);
                                    } else {
                                        Toast.makeText(AdminLoginActivity.this, "Inavalid Credentials", Toast.LENGTH_LONG).show();
                                    }
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    progressDialog.cancel();

                                    Toast.makeText(AdminLoginActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
                                }
                            });
                        }else{
                            Toast.makeText(AdminLoginActivity.this, "Please enter admin id", Toast.LENGTH_LONG).show();
                        }
                    } else {
                        Toast.makeText(AdminLoginActivity.this, "Enter Mail", Toast.LENGTH_LONG).show();
                    }
                }else{
                    Toast.makeText(AdminLoginActivity.this, "No Internet Connection", Toast.LENGTH_LONG).show();

                }


            }
        });

        txtAdminForgotPassword = findViewById(R.id.txtFacultyPassword);
        txtAdminForgotPassword.setMovementMethod(LinkMovementMethod.getInstance());

    }
}
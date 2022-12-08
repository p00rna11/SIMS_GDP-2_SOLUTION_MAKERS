package com.example.internshipmanagementsystem.student;

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
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class StudentLoginActivity extends AppCompatActivity {

    EditText email;
    EditText password;
    Button btnSignIn;
    TextView forgotPassword;
    private FirebaseAuth mAuth;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_login);
        email = (EditText) findViewById(R.id.username);
        password = (EditText) findViewById(R.id.password);
        btnSignIn = findViewById(R.id.login);
        mAuth = FirebaseAuth.getInstance();


        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

               /* email.setText("s54321@nwmissouri.edu");
                password.setText("northwest");
*/

                String emailUsername = email.getText().toString();
                String passwordText = password.getText().toString();




                if (CommonUtils.isConnectedToInternet(StudentLoginActivity.this)) {

                    if (!TextUtils.isEmpty(emailUsername)) {

                        char[] strArray = emailUsername.toCharArray();

                        for (int i = 0; i < strArray.length; i++) {
                            System.out.println(strArray[i]);
                        }


                        if (TextUtils.isEmpty(emailUsername)) {
                            Toast.makeText(StudentLoginActivity.this, "Enter Mail", Toast.LENGTH_LONG).show();
                        } else if (TextUtils.isEmpty(passwordText)) {
                            Toast.makeText(StudentLoginActivity.this, "Enter password", Toast.LENGTH_LONG).show();
                        } else {

                            if (strArray[0] == 'S' || strArray[0] == 's') {
                                if (TextUtils.isDigitsOnly(Character.toString(strArray[1]))) {
                                    progressDialog = new ProgressDialog(StudentLoginActivity.this);
                                    progressDialog.setMessage("Loading....");
                                    progressDialog.show();

                                    mAuth.signInWithEmailAndPassword(emailUsername, passwordText).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                        @Override
                                        public void onComplete(@NonNull Task<AuthResult> task) {
                                            progressDialog.cancel();

                                            if (task.isSuccessful()) {
                                                Intent intent = new Intent(StudentLoginActivity.this, StudentDashboard.class);
                                                startActivity(intent);
                                            } else {
                                                Toast.makeText(StudentLoginActivity.this, "Inavalid Credentials", Toast.LENGTH_LONG).show();
                                            }
                                        }
                                    }).addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            progressDialog.cancel();

                                            Toast.makeText(StudentLoginActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
                                        }
                                    });
                                } else {
                                    Toast.makeText(StudentLoginActivity.this, "Please enter student ID", Toast.LENGTH_LONG).show();
                                }
                            } else {
                                Toast.makeText(StudentLoginActivity.this, "Please enter student ID", Toast.LENGTH_LONG).show();
                            }

                        }
                    } else {
                        Toast.makeText(StudentLoginActivity.this, "Enter Mail", Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(StudentLoginActivity.this, "No Internet Connection", Toast.LENGTH_LONG).show();
                }

            }
        });

        forgotPassword = findViewById(R.id.txtFacultyPassword);
        forgotPassword.setMovementMethod(LinkMovementMethod.getInstance());

    }
}
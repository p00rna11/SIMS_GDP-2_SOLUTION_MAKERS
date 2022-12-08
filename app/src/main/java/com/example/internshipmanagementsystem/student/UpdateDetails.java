package com.example.internshipmanagementsystem.student;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.internshipmanagementsystem.CommonUtils;
import com.example.internshipmanagementsystem.MainActivity;
import com.example.internshipmanagementsystem.R;
import com.example.internshipmanagementsystem.model.ApplyForInternshipModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class UpdateDetails extends AppCompatActivity {

    Button btnUpdate;
    Button btnDrop;
    TextView txtApplicationIDUpdatePage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_details);
        btnUpdate = findViewById(R.id.btnUpdate);
        btnDrop = findViewById(R.id.btnDrop);
        txtApplicationIDUpdatePage = findViewById(R.id.txtApplicationIDUpdatePage);

        try {
            String key=getIntent().getStringExtra("STUDENTKEY");
            getStatus(key);
        } catch (Exception e) {

        }

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String key=getIntent().getStringExtra("STUDENTKEY");
         //       Intent intent = new Intent(UpdateDetails.this, UpdateIntershipDetails.class);
                Intent intent = new Intent(UpdateDetails.this, InternShipUpdateone.class);
                intent.putExtra("STUDENTKEY",key);
                startActivity(intent);
            }
        });

        btnDrop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getAlert();
            }
        });

    }

    private void getStatus(String key) {
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
                        if (TextUtils.isEmpty(model.getPhoneNumber())) {

                        } else {
                            setDatatoViews(model);
                        }
                    } else {
                        Toast.makeText(UpdateDetails.this, "No Data", Toast.LENGTH_LONG).show();
                        finish();
                    }

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(UpdateDetails.this, "Please try again after sometime....", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void setDatatoViews(ApplyForInternshipModel model) {
        String phone = model.getRandom();
        txtApplicationIDUpdatePage.setText("Your application id is: " + phone);

    }

    private void getAlert() {


        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setMessage("Are you sure you want to delete....");
        dialog.setTitle("Drop Application");
        dialog.setPositiveButton("YES",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,
                                        int which) {
                        deleteInternship();
                    }
                });
        dialog.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        AlertDialog alertDialog = dialog.create();
        alertDialog.show();
    }

    private void deleteInternship() {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        String studentID = FirebaseAuth.getInstance().getCurrentUser().getUid();

        if (CommonUtils.isConnectedToInternet(UpdateDetails.this)) {

            try {
                String key=getIntent().getStringExtra("STUDENTKEY");
                DatabaseReference myRef = database.getReference("student").child("applyForInternship").child(studentID).child(key);

                if (myRef.getDatabase() != null) {
                    myRef.removeValue();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            Toast.makeText(UpdateDetails.this, "No Internet Connection", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.student_dashboard2, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_logout:
                startActivity(new Intent(UpdateDetails.this, MainActivity.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
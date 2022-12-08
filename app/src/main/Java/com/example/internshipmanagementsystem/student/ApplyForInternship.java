package com.example.internshipmanagementsystem.student;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
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
import android.widget.TextView;
import android.widget.Toast;

import com.example.internshipmanagementsystem.CommonUtils;
import com.example.internshipmanagementsystem.MainActivity;
import com.example.internshipmanagementsystem.R;
import com.example.internshipmanagementsystem.model.ApplyForInternshipModel;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Random;

public class ApplyForInternship extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    Button btnContinue,id_uploadOfferLetter;
    TextInputEditText enterfirstname, entermiddlename, enterlastname, enteruserid, enteraddress, enterPhone, enterCompanyname, entercompanucontact,
            entercompanymail, entercompanyaddress, entersemeterterm, enteryear, entercrn, entercredithours, entercredit, enterstart,
            enterenddate, enterhoursofwork;

    private Spinner spinnerFall, spinnerYear;
    String semYear, totalyear;
    String offerletter="";
    private static final String[] paths = {"Fall", "Spring", "Summer"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_apply_for_internship);
        spinnerYear = findViewById(R.id.spinnerYear);
        entercredit = findViewById(R.id.entercredit);
        entercredithours = findViewById(R.id.entercredithours);
        id_uploadOfferLetter = findViewById(R.id.id_uploadOfferLetter);
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
                android.R.layout.simple_spinner_item, paths);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerFall.setAdapter(adapter);
        spinnerFall.setOnItemSelectedListener(this);
        semYear = "Fall";

        ArrayAdapter<String> yearadapter = new ArrayAdapter<String>(ApplyForInternship.this,
                android.R.layout.simple_spinner_item, getYear());

        yearadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerYear.setAdapter(yearadapter);
        spinnerYear.setOnItemSelectedListener(this);
        totalyear = "1950";

        spinnerYear.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                totalyear = String.valueOf(adapterView.getItemAtPosition(i));
                ((TextView) adapterView.getChildAt(0)).setTextColor(Color.BLACK); /* if you want your item to be white */

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        id_uploadOfferLetter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getPermission();
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

                Calendar calendar = Calendar.getInstance();
                int currentyear = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);

                String currentSem = "";
                String previousSem = "";

                if (Integer.parseInt(year) == currentyear) {
                    currentSem = semesterterm;
                    if (semesterterm.contains("summer")) {
                        previousSem = "Spring";
                    } else if (semesterterm.contains("Spring")) {
                        previousSem = "Fall";
                    } else if (semesterterm.contains("fall")) {
                        previousSem = "Summer";
                    }
                }


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
                } else if (!checkHours(credithours)) {
                    Toast.makeText(ApplyForInternship.this, "Enter credit hours between 1-5", Toast.LENGTH_LONG).show();
                } else if (TextUtils.isEmpty(crn)) {
                    Toast.makeText(ApplyForInternship.this, "Enter crn ", Toast.LENGTH_LONG).show();
                } else if (TextUtils.isEmpty(userid)) {
                    Toast.makeText(ApplyForInternship.this, "Enter userid ", Toast.LENGTH_LONG).show();
                } else if (TextUtils.isEmpty(address)) {
                    Toast.makeText(ApplyForInternship.this, "Enter address ", Toast.LENGTH_LONG).show();
                } else if (TextUtils.isEmpty(phone)) {
                    Toast.makeText(ApplyForInternship.this, "Enter phone ", Toast.LENGTH_LONG).show();
                } else if (!(phone.length() == 10)) {
                    Toast.makeText(ApplyForInternship.this, "Enter correct phone number  ", Toast.LENGTH_LONG).show();
                } else {

                    Intent intent = new Intent(ApplyForInternship.this, EmployerInfoActivity.class);
                    intent.putExtra("firstname", firstname);
                    intent.putExtra("lastname", lastname);
                    intent.putExtra("credit", credit);
                    intent.putExtra("semesterterm", semesterterm);
                    intent.putExtra("year", year);
                    intent.putExtra("credithours", credithours);
                    intent.putExtra("crn", crn);
                    intent.putExtra("middlename", middlename);
                    intent.putExtra("address", address);
                    intent.putExtra("userid", userid);
                    intent.putExtra("phone", phone);
                    intent.putExtra("currentSEM", currentSem);
                    intent.putExtra("preSem", previousSem);
                    intent.putExtra("OFFERLETTER",offerletter);
                    startActivity(intent);
                }

            }
        });


    }

    private void getPermission() {
        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.READ_EXTERNAL_STORAGE
        ) == PackageManager.PERMISSION_GRANTED
        ) {
            openGallery();
        } else {
            ActivityCompat.requestPermissions(ApplyForInternship.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 401);
        }
    }

    private void openGallery() {
        Intent gallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        startActivityForResult(gallery, 11);
    }

    private boolean checkHours(String credithours) {
        int hours = Integer.parseInt(credithours);
        if (hours >= 1 && hours <= 5) {
            return true;
        } else {
            return false;
        }

    }

    private ArrayList<String> getYear() {
        ArrayList<String> list = new ArrayList<>();
        for (int i = 1950; i <= 2100; i++) {
            list.add("" + i);
        }
        return list;
    }


    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        switch (i) {
            case 0:
                ((TextView) adapterView.getChildAt(0)).setTextColor(Color.BLACK); /* if you want your item to be white */

                semYear = "Fall";
                break;
            case 1:
                semYear = "Spring";
                ((TextView) adapterView.getChildAt(0)).setTextColor(Color.BLACK); /* if you want your item to be white */
                break;
            case 2:
                semYear = "Summer";
                ((TextView) adapterView.getChildAt(0)).setTextColor(Color.BLACK); /* if you want your item to be white */

                break;

        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
        // TODO Auto-generated method stub
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 401) {
            if (grantResults.length == 0 || grantResults == null) {

            } else if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                openGallery();
            } else if (grantResults[0] == PackageManager.PERMISSION_DENIED) {
            }
        } else if (requestCode == 402) {
            if (grantResults.length == 0 || grantResults == null) {

            } else if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {

            } else if (grantResults[0] == PackageManager.PERMISSION_DENIED) {
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == 11) {
            Uri imageUri = data.getData();
         //   profile_image.setImageURI(imageUri);

            try {

                // Setting image on image view using Bitmap
                Bitmap bitmap = MediaStore
                        .Images
                        .Media
                        .getBitmap(
                                getContentResolver(),
                                imageUri);

                uploadImage(imageUri);

            } catch (IOException e) {
                // Log the exception
                e.printStackTrace();
            }


        }
    }

    private void uploadImage(Uri filePath) {

        if (filePath != null) {
            final ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setTitle("Uploading...");
            progressDialog.show();

            FirebaseStorage storage = FirebaseStorage.getInstance();
            StorageReference storageReference = storage.getReference();
            String studentID = FirebaseAuth.getInstance().getCurrentUser().getUid();
            StorageReference ref = storageReference.child("images/" + studentID);

            Task<Uri> urlTask = ref.putFile(filePath).continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                @Override
                public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                    if (!task.isSuccessful()) {
                        throw task.getException();
                    }
                    // Continue with the task to get the download URL
                    offerletter=ref.getDownloadUrl().toString();
                    return ref.getDownloadUrl();
                }
            }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                @Override
                public void onComplete(@NonNull Task<Uri> task) {
                    if (task.isSuccessful()) {
                        progressDialog.dismiss();
                      //  Toast.makeText(ApplyForInternship.this, "Offer letter Uploaded", Toast.LENGTH_SHORT).show();
                    //    Toast.makeText(ApplyForInternship.this, "Offer letter Uploaded" + offerletter, Toast.LENGTH_SHORT).show();
                        getImage();
                    } else {
                        progressDialog.dismiss();
                        Toast.makeText(ApplyForInternship.this, "Failed ", Toast.LENGTH_SHORT).show();
                    }
                }
            });


        }
    }

    private void getImage() {


            FirebaseStorage storage = FirebaseStorage.getInstance();
            StorageReference storageReference = storage.getReference();
            String studentID = FirebaseAuth.getInstance().getCurrentUser().getUid();
            StorageReference storageRef = storageReference.child("images/" + studentID);


            storageRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                @Override
                public void onSuccess(Uri uri) {
                    //Toast.makeText(StudentDashboard.this, "Success"+uri.toString(), Toast.LENGTH_SHORT).show();
                    offerletter=uri.toString();
                    Toast.makeText(ApplyForInternship.this, ""+offerletter, Toast.LENGTH_SHORT).show();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception exception) {
                    //    Toast.makeText(StudentDashboard.this, "failure", Toast.LENGTH_SHORT).show();
                }
            });


    }

}
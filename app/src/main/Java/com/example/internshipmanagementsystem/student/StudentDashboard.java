package com.example.internshipmanagementsystem.student;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.internshipmanagementsystem.MainActivity;
import com.example.internshipmanagementsystem.R;
import com.example.internshipmanagementsystem.faculty.FacultyDashboard;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.Calendar;
import java.util.TimeZone;
import java.util.UUID;

import de.hdodenhof.circleimageview.CircleImageView;

public class StudentDashboard extends AppCompatActivity {

    CardView applyCard;
    CardView statusCard;
    CardView updateCard;
    ImageView id_logout;
    CardView findInternship;
    TextView loggedInUser;
    CircleImageView profile_image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_dashboard);

        applyCard = findViewById(R.id.applyForInternship);
        profile_image = findViewById(R.id.profile_image);
        statusCard = findViewById(R.id.statusCard);
        updateCard = findViewById(R.id.updateCard);
        loggedInUser = findViewById(R.id.loggedInUser);
      //  findInternship = findViewById(R.id.findingInternship);
        id_logout = findViewById(R.id.id_logout);


        try {

            getimage();
        } catch (Exception e) {
            e.printStackTrace();
        }

        profile_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getPermission();
            }
        });

        id_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                FirebaseAuth.getInstance().signOut();
                Intent intent=new Intent(StudentDashboard.this, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|
                        Intent.FLAG_ACTIVITY_CLEAR_TASK |
                        Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish();
            }
        });

        applyCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(StudentDashboard.this, ApplyForInternship.class));
            }
        });

        statusCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(StudentDashboard.this, Status.class));
            }
        });

        updateCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(StudentDashboard.this, InternshipListActivity.class));
            }
        });

/*
        findInternship.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            //    startActivity(new Intent(StudentDashboard.this, FindInternship.class));
            }
        });
*/

    }

    private void getimage() {

        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageReference = storage.getReference();
        String studentID = FirebaseAuth.getInstance().getCurrentUser().getUid();
        StorageReference storageRef = storageReference.child("images/" + studentID);


        storageRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                //Toast.makeText(StudentDashboard.this, "Success"+uri.toString(), Toast.LENGTH_SHORT).show();
                Picasso.get().load(uri).into(profile_image);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                //    Toast.makeText(StudentDashboard.this, "failure", Toast.LENGTH_SHORT).show();
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
            ActivityCompat.requestPermissions(StudentDashboard.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 401);
        }
    }


    @Override
    public void onBackPressed() {

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

    private void openGallery() {
        Intent gallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        startActivityForResult(gallery, 11);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == 11) {
            Uri imageUri = data.getData();
            profile_image.setImageURI(imageUri);

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
                    return ref.getDownloadUrl();
                }
            }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                @Override
                public void onComplete(@NonNull Task<Uri> task) {
                    if (task.isSuccessful()) {
                        progressDialog.dismiss();
                        Toast.makeText(StudentDashboard.this, "Uploaded", Toast.LENGTH_SHORT).show();

                    } else {
                        progressDialog.dismiss();
                        Toast.makeText(StudentDashboard.this, "Failed ", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }
}
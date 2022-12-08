package com.example.internshipmanagementsystem.faculty;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;

import com.example.internshipmanagementsystem.R;
import com.squareup.picasso.Picasso;

public class OfferletterPreviewActivity extends AppCompatActivity {

    ImageView imagepreview;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_offerletter_preview);
        imagepreview=findViewById(R.id.imagepreview);

        try {
            String imge=getIntent().getStringExtra("IMAGE");
            Picasso.get().load(imge).into(imagepreview);

        }catch (Exception e ){
            e.printStackTrace();
        }
    }
}
package com.example.internshipmanagementsystem.faculty;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import com.example.internshipmanagementsystem.R;
import com.example.internshipmanagementsystem.model.ApplyForInternshipModel;

import java.util.ArrayList;

public class CurrentSemesterActivity extends AppCompatActivity {
    RecyclerView listview_totalIntern;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_current_semester);
        Intent intent = getIntent();
        ArrayList<ApplyForInternshipModel> arrayList = (ArrayList<ApplyForInternshipModel>) getIntent().getSerializableExtra("CURRENTSEM");;
        if(arrayList.size()>0){
            listview_totalIntern=findViewById(R.id.listview_totalIntern);
            listview_totalIntern.setLayoutManager(new LinearLayoutManager(this));
            listview_totalIntern.setAdapter(new TotalCurrentSemAdapter(CurrentSemesterActivity.this,arrayList));
        }
    }
}
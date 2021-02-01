package com.example.bunnyhub;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.bunnyhub.faculty.UpdateFacultyActivity;
import com.example.bunnyhub.notice.DeleteNoticeActivity;
import com.example.bunnyhub.notice.UploadNoticeActivity;

public class MainActivity extends AppCompatActivity {
    CardView notice;
    CardView image;
    CardView notes;
    CardView faculty;
    CardView deleteNotice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        notice=findViewById(R.id.notice);
        image=findViewById(R.id.addImage);
        notes=findViewById(R.id.Notes);
        faculty=findViewById(R.id.updateFaculty);
        deleteNotice=findViewById(R.id.deleteMainNotice);

        notice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent one=new Intent(MainActivity.this, UploadNoticeActivity.class);
                startActivity(one);
            }
        });

        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent two=new Intent(MainActivity.this,UploadImageActivity.class);
                startActivity(two);
            }
        });

        notes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent three=new Intent(MainActivity.this,UploadNotesActivity.class);
                startActivity(three);
            }
        });

        faculty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent four=new Intent(MainActivity.this, UpdateFacultyActivity.class);
                startActivity(four);
            }
        });

        deleteNotice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent five=new Intent(MainActivity.this, DeleteNoticeActivity.class);
                startActivity(five);
            }
        });
    }
}
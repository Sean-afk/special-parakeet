package com.example.bunnyhub.faculty;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.bunnyhub.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class UpdateFacultyActivity extends AppCompatActivity {
    FloatingActionButton fab;
    private RecyclerView csDepartment,itDepartment,extcDepartment,scienceDepartment,nonDepartment;
    private LinearLayout csNoData,itNoData,extcNoData,humNoData,nonNoData;
    private List<TeacherData> list1,list2,list3,list4,list5;
    private TeacherAdapter adapter;

    private DatabaseReference reference,databaseReference;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_faculty);

        fab = findViewById(R.id.fab);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(UpdateFacultyActivity.this, AddTeacherActivity.class));
            }
        });

        csDepartment = findViewById(R.id.csDepartment);
        itDepartment = findViewById(R.id.itDepartment);
        extcDepartment = findViewById(R.id.extcDepartment);
        scienceDepartment = findViewById(R.id.scienceDepartment);
        nonDepartment = findViewById(R.id.nonDepartment);

        csNoData = findViewById(R.id.csNoData);
        itNoData = findViewById(R.id.itNoData);
        extcNoData = findViewById(R.id.extcNoData);
        humNoData = findViewById(R.id.humNoData);
        nonNoData = findViewById(R.id.nonNoData);


        reference = FirebaseDatabase.getInstance().getReference().child("Faculty");

        csDepartment();
        itDepartment();
        extcDepartment();
        humDepartment();
        nonDepartment();


    }

    private void csDepartment() {
        databaseReference=reference.child("Computer Engineering");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list1= new ArrayList<>();
                if(!snapshot.exists()){
                    csNoData.setVisibility(View.VISIBLE);
                    csDepartment.setVisibility(View.GONE);
                }else {
                    csNoData.setVisibility(View.GONE);
                    csDepartment.setVisibility(View.VISIBLE);
                    for(DataSnapshot snapshot1: snapshot.getChildren()){
                        TeacherData data=snapshot1.getValue(TeacherData.class);
                        list1.add(data);
                    }

                    csDepartment.setHasFixedSize(true);
                    csDepartment.setLayoutManager(new LinearLayoutManager(UpdateFacultyActivity.this));

                    adapter = new TeacherAdapter(list1,UpdateFacultyActivity.this,"Computer Engineering");
                    csDepartment.setAdapter(adapter);

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(UpdateFacultyActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });
    }
    private void itDepartment() {
        databaseReference=reference.child("Information Technology");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list2= new ArrayList<>();
                if(!snapshot.exists()){
                    itNoData.setVisibility(View.VISIBLE);
                    itDepartment.setVisibility(View.GONE);
                }else {
                    itNoData.setVisibility(View.GONE);
                    itDepartment.setVisibility(View.VISIBLE);
                    for(DataSnapshot snapshot5: snapshot.getChildren()){
                        TeacherData data=snapshot5.getValue(TeacherData.class);
                        list2.add(data);
                    }
                    itDepartment.setHasFixedSize(true);
                    itDepartment.setLayoutManager(new LinearLayoutManager(UpdateFacultyActivity.this));
                    adapter = new TeacherAdapter(list2,UpdateFacultyActivity.this,"Information Technology");
                    itDepartment.setAdapter(adapter);

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(UpdateFacultyActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });
    }
    private void extcDepartment() {
        databaseReference=reference.child("Electronics & Telecommunication");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list3= new ArrayList<>();
                if(!snapshot.exists()){
                    extcNoData.setVisibility(View.VISIBLE);
                    extcDepartment.setVisibility(View.GONE);
                }else {
                    extcNoData.setVisibility(View.GONE);
                    extcDepartment.setVisibility(View.VISIBLE);
                    for(DataSnapshot snapshot4: snapshot.getChildren()){
                        TeacherData data=snapshot4.getValue(TeacherData.class);
                        list3.add(data);
                    }
                    extcDepartment.setHasFixedSize(true);
                    extcDepartment.setLayoutManager(new LinearLayoutManager(UpdateFacultyActivity.this));
                    adapter = new TeacherAdapter(list3,UpdateFacultyActivity.this,"Electronics & Telecommunication");
                    extcDepartment.setAdapter(adapter);

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(UpdateFacultyActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });
    }
    private void humDepartment() {
        databaseReference=reference.child("Applied Sciences & Humanities");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list4= new ArrayList<>();
                if(!snapshot.exists()){
                    humNoData.setVisibility(View.VISIBLE);
                    scienceDepartment.setVisibility(View.GONE);
                }else {
                    humNoData.setVisibility(View.GONE);
                    scienceDepartment.setVisibility(View.VISIBLE);
                    for(DataSnapshot snapshot3: snapshot.getChildren()){
                        TeacherData data=snapshot3.getValue(TeacherData.class);
                        list4.add(data);
                    }
                    scienceDepartment.setHasFixedSize(true);
                    scienceDepartment.setLayoutManager(new LinearLayoutManager(UpdateFacultyActivity.this));
                    adapter = new TeacherAdapter(list4,UpdateFacultyActivity.this,"Applied Sciences & Humanities");
                    scienceDepartment.setAdapter(adapter);

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(UpdateFacultyActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });
    }
    private void nonDepartment() {
        databaseReference=reference.child("Non Teaching");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list5= new ArrayList<>();
                if(!snapshot.exists()){
                    nonNoData.setVisibility(View.VISIBLE);
                    nonDepartment.setVisibility(View.GONE);
                }else {
                    nonNoData.setVisibility(View.GONE);
                    nonDepartment.setVisibility(View.VISIBLE);
                    for(DataSnapshot snapshot2: snapshot.getChildren()){
                        TeacherData data=snapshot2.getValue(TeacherData.class);
                        list5.add(data);
                    }
                    nonDepartment.setHasFixedSize(true);
                    nonDepartment.setLayoutManager(new LinearLayoutManager(UpdateFacultyActivity.this));
                    adapter = new TeacherAdapter(list5,UpdateFacultyActivity.this,"Non Teaching");
                    nonDepartment.setAdapter(adapter);

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(UpdateFacultyActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });
    }
}

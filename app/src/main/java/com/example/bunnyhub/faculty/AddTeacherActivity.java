package com.example.bunnyhub.faculty;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.bunnyhub.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class AddTeacherActivity extends AppCompatActivity {

    private ImageView addTeacherImage;
    private EditText teacherName,teacherMail,teacherPost;
    private Spinner teacherCategory;
    private Button addTeacherButton;
    private final int REQ=1;
    private Bitmap bitmap=null;
    private String category;
    private String name,email,post,downloadUrl="";
    private ProgressDialog pd;

    private StorageReference storageReference;
    private DatabaseReference reference,databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_teacher);

        addTeacherImage=findViewById(R.id.addTeacherImage);
        teacherName=findViewById(R.id.Name);
        teacherMail=findViewById(R.id.Email);
        teacherPost=findViewById(R.id.Post);
        teacherCategory=findViewById(R.id.Category);
        addTeacherButton=findViewById(R.id.addTeacherButton);

        pd=new ProgressDialog(this);

        reference= FirebaseDatabase.getInstance().getReference().child("Faculty");
        storageReference= FirebaseStorage.getInstance().getReference();

        String[] items=new String[]{"Select Category","Computer Engineering","Information Technology","Electronics & Telecommunication","Non Teaching","Applied Sciences & Humanities",};
        teacherCategory.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item,items));

        teacherCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                category=teacherCategory.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        addTeacherImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openGallery();
            }
        });

        addTeacherButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkValidation();
            }
        });
    }

    private void checkValidation() {
        name = teacherName.getText().toString();
        email = teacherMail.getText().toString();
        post = teacherPost.getText().toString();

        if (name.isEmpty()) {
            teacherName.setError("Empty");
            teacherName.requestFocus();
        } else if (email.isEmpty()) {
            teacherMail.setError("Empty");
            teacherName.requestFocus();
        } else if (post.isEmpty()) {
            teacherPost.setError("Empty");
            teacherPost.requestFocus();
        } else if (category.equals("Select Category")) {
            Toast.makeText(this, "Please select a Category", Toast.LENGTH_SHORT).show();

        }else if(bitmap==null){
            insertData();

        }else {
            pd.setMessage("Uploading...");
            pd.show();
            uploadImage();
        }
    }

    private void uploadImage() {

        ByteArrayOutputStream baos=new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,50,baos);
        byte[] finalImg=baos.toByteArray();
        final StorageReference filePath;
        filePath= storageReference.child("Teachers").child(finalImg+"jpg");
        final UploadTask uploadTask = filePath.putBytes(finalImg);
        uploadTask.addOnCompleteListener(AddTeacherActivity.this, new OnCompleteListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                if (task.isSuccessful()) {
                    uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            filePath.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    downloadUrl = String.valueOf(uri);
                                    insertData();
                                }
                            });

                        }
                    });
                } else {
                    pd.dismiss();
                    Toast.makeText(AddTeacherActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    private void insertData(){
        databaseReference= reference.child(category);
        final String uniqueKey=databaseReference.push().getKey();


        TeacherData teacherData=new TeacherData(name,email,post,downloadUrl,uniqueKey);

        databaseReference.child(uniqueKey).setValue(teacherData).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                pd.dismiss();
                Toast.makeText(AddTeacherActivity.this, "Details Uploaded", Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                pd.dismiss();
                Toast.makeText(AddTeacherActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
            }
        });
    }


    private void openGallery() {
        Intent picImage=new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(picImage,REQ);
    }

    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode==REQ&&resultCode==RESULT_OK){
            Uri uri=data.getData();
            try {
                bitmap=MediaStore.Images.Media.getBitmap(getContentResolver(),uri);
            } catch (IOException e) {
                e.printStackTrace();
            }
            addTeacherImage.setImageBitmap(bitmap);
        }
    }
}

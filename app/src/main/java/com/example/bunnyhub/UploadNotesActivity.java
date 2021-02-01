package com.example.bunnyhub;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.OpenableColumns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.util.HashMap;

public class UploadNotesActivity extends AppCompatActivity {
    private CardView addNote;
    private final int REQ=1;
    private Uri fileData;
    private EditText noteTitle;
    private Button noteButton;
    private TextView fileView;
    private String fileName,title;

    private DatabaseReference databaseReference;
    private StorageReference storageReference;
    String downloadUrl="";
    private ProgressDialog pd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_notes);

        pd=new ProgressDialog(this);

        addNote=findViewById(R.id.selectFile);
        noteTitle=findViewById(R.id.noteTitle);
        noteButton=findViewById(R.id.notesButton);
        fileView=findViewById(R.id.fileTextView);

        databaseReference= FirebaseDatabase.getInstance().getReference();
        storageReference= FirebaseStorage.getInstance().getReference();


        addNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openGallery();
            }
        });

        noteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                title=noteTitle.getText().toString();
                if (title.isEmpty()){
                    noteTitle.setError("Empty");
                    noteTitle.requestFocus();
                }else if(fileData==null){
                    Toast.makeText(UploadNotesActivity.this, "Please Upload a File", Toast.LENGTH_SHORT).show();
                }else {
                    uploadFile();
                }

            }

        });
    }

    private void uploadFile() {
        pd.setTitle("Please Wait..");
        pd.setMessage("Uploading file");
        pd.show();
        StorageReference reference= storageReference.child(("pdf/"+fileName+"-"+System.currentTimeMillis()));
        reference.putFile(fileData).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Task<Uri> uriTask=taskSnapshot.getStorage().getDownloadUrl();
                while ((uriTask.isComplete()));
                Uri uri=uriTask.getResult();
                uploadData(String.valueOf(uri));
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                pd.dismiss();
                Toast.makeText(UploadNotesActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();

            }
        });

    }

    private void uploadData(String valueOf) {
        String uniqueKey=databaseReference.child("pdf").push().getKey();
        HashMap data=new HashMap();
        data.put("noteTitle",title);
        data.put("fileUrl",downloadUrl);

        databaseReference.child("file").child(uniqueKey).setValue(data).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                pd.dismiss();
                Toast.makeText(UploadNotesActivity.this, "File Uploaded Successfully", Toast.LENGTH_SHORT).show();
                noteTitle.setText("");
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                pd.dismiss();
                Toast.makeText(UploadNotesActivity.this, "Failed To Upload File", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void openGallery() {
       Intent intent=new Intent();
       intent.setType("pdf/docs/ppt");
       intent.setAction(Intent.ACTION_GET_CONTENT);
       startActivityForResult(Intent.createChooser(intent,"Select File"),REQ);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode==REQ&&resultCode==RESULT_OK){
           fileData= data.getData();

           if(fileData.toString().startsWith("content://")){
               Cursor cursor=null;

               try {
                   cursor=UploadNotesActivity.this.getContentResolver().query(fileData,null,null,null,null);
                   if(cursor !=null && cursor.moveToFirst()){
                       fileName=cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));

                   }
               } catch (Exception e) {
                   e.printStackTrace();
               }
           }else if(fileData.toString().startsWith("file://")){
               fileName=new File(fileData.toString()).getName();
           }
           fileView.setText(fileName);

        }
    }
}
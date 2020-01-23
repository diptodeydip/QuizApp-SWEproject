package com.example.dip.quizapp;


import android.content.Intent;
//import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;

public class Classlist extends MenuBar implements ClassAdapter.OnItemClickListener {

    RecyclerView rView;
    DatabaseReference db, image_db;
    List<Classes> uploads;
    ProgressBar pbar;
    ClassAdapter iAdapter;
    FirebaseAuth mAuth;
    String userId;
    FirebaseStorage fs;
    ValueEventListener dBListener;
    Button signOut,create_class;
    GoogleSignInClient mGoogleSignInClient;
    String user_email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_classlist);

        mAuth = FirebaseAuth.getInstance();
        Toast.makeText(this, "" + mAuth.getCurrentUser().getEmail(), Toast.LENGTH_LONG).show();
        user_email = mAuth.getCurrentUser().getEmail().toString();
        userId = mAuth.getCurrentUser().getUid();

        // Create Class
        create_class = findViewById(R.id.createclass);
        create_class.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Classlist.this,createClass.class);
                //intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });

        //Sign Out
        signOut = (Button) findViewById(R.id.sign_out);
        signOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mAuth.signOut(); //get signed out
                // Configure Google Sign In
                GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                        .requestIdToken("466020745046-1hpmtvoqfg3md4vkp452regadbei1sqf.apps.googleusercontent.com")
                        .requestEmail()
                        .build();

                // Build a GoogleSignInClient with the options specified by gso.
                mGoogleSignInClient = GoogleSignIn.getClient(Classlist.this, gso);
                mGoogleSignInClient.signOut()
                        .addOnCompleteListener(Classlist.this, new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                Toast.makeText(Classlist.this, "Signed Out", Toast.LENGTH_LONG).show();
                               // revokeAccess();
                                finish();
                                Intent intent = new Intent(Classlist.this,MainActivity.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(intent);
                            }
                        });


            }
        });

  /*      Classes c = new Classes();
        c.setCode("aa");
        image_db = FirebaseDatabase.getInstance().getReference("Classes");
        image_db.child("" + System.currentTimeMillis()).setValue(c);*/


        rView = findViewById(R.id.recyclerView);
        rView.setHasFixedSize(true);
        rView.setLayoutManager(new LinearLayoutManager(this));
        pbar = findViewById(R.id.pbar4);

        uploads = new ArrayList<>();
        // fs = FirebaseStorage.getInstance();
        db = FirebaseDatabase.getInstance().getReference("users").child(userId);
        iAdapter = new ClassAdapter(this, uploads);
        iAdapter.setOnItemClickListener(this);
        rView.setAdapter(iAdapter);


        dBListener = db.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                uploads.clear();

                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    Classes upload = postSnapshot.getValue(Classes.class);
                /*    if(upload.getId().toString().equals(userId)) {
                        upload.setKey(postSnapshot.getKey());
                        */
                    uploads.add(upload);

                }
                iAdapter.notifyDataSetChanged();
                pbar.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(Classlist.this, databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                pbar.setVisibility(View.INVISIBLE);
            }
        });
    }

    //Oncreate ended



    private void revokeAccess() {
        mGoogleSignInClient.revokeAccess()
                .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                       // Toast.makeText(Classlist.this, "Google access revoked.", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    @Override
    public void onItemClick(int position) {
/*        // Toast.makeText(this, "Long click to see option", Toast.LENGTH_SHORT).show();
        Upload selectedItem = uploads.get(position);
        if(!selectedItem.getImageUrl().equals("none")){
            MainActivity.url=selectedItem.getImageUrl();
            startActivity(new Intent(this,Problemrelatedimage.class));
        }*/
        Toast.makeText(Classlist.this, "" + position, Toast.LENGTH_SHORT).show();
        Classes selectedItem = uploads.get(position);
        MainActivity.classCode = selectedItem.getCode();
        startActivity(new Intent(Classlist.this,questionlist.class));
    }


    @Override
    public void onDeleteClick(int position) {
        Classes selectedItem = uploads.get(position);
        FirebaseDatabase.getInstance().getReference("Classes").child(selectedItem.getCode())
                .removeValue();
        FirebaseDatabase.getInstance().getReference("users").child(userId).child(selectedItem.getCode())
                .removeValue();
        Toast.makeText(Classlist.this, "Class deleted", Toast.LENGTH_SHORT).show();
    }

    @Override
    public  void onStopClick(int position){
        Classes selectedItem = uploads.get(position);
            FirebaseDatabase.getInstance().getReference("Classes").child(selectedItem.getCode())
                    .child("flag").child("value").setValue("0");
            Toast.makeText(Classlist.this, "Exam Stopped", Toast.LENGTH_SHORT).show();
    }

    @Override
    public  void onStartClick(int position){
        Classes selectedItem = uploads.get(position);

            FirebaseDatabase.getInstance().getReference("Classes").child(selectedItem.getCode())
                    .child("flag").child("value").setValue("1");
        FirebaseDatabase.getInstance().getReference("Classes").child(selectedItem.getCode())
                .child("ifStarted").child("value1").setValue("1");
            Toast.makeText(Classlist.this, "Exam Started", Toast.LENGTH_SHORT).show();
    }



    protected void onDestroy() {
        super.onDestroy();
        db.removeEventListener(dBListener);
    }
}


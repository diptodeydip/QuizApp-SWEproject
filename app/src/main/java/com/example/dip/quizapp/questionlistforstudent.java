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
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class questionlistforstudent extends AppCompatActivity implements QuesAdapter.OnItemClickListener {

    RecyclerView rView;
    DatabaseReference db, image_db;
    List<Questions> uploads;
    ProgressBar pbar;
    QuesAdapter iAdapter;
    FirebaseAuth mAuth;
    String userId;
    FirebaseStorage fs;
    ValueEventListener dBListener;
    Button result,submit;
    GoogleSignInClient mGoogleSignInClient;
    String user_email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_questionlistforstudent);

        rView = findViewById(R.id.recyclerView2);

        result = findViewById(R.id.seeresult1);
        // submit
        submit = findViewById(R.id.Submit);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rView.setVisibility(View.GONE);
                submit.setVisibility(View.GONE);
                result.setVisibility(View.VISIBLE);


                //
                Query userNameQuery = FirebaseDatabase.getInstance().getReference().child("Classes")
                        .child(MainActivity.classCode)
                        .child("answer")
                        .child(MainActivity.reg)
                        .orderByChild("verdict").equalTo("Correct");

                userNameQuery.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        resultformat upload = new resultformat();

                        upload.setMarks(Integer.toString((int)dataSnapshot.getChildrenCount()));
                        upload.setReg(MainActivity.reg);

                        FirebaseDatabase.getInstance().getReference("Classes").child(MainActivity.classCode)
                                .child("result").child(""+System.currentTimeMillis()).setValue(upload);
                    }
                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                    }
                });
                //
                Map map = new HashMap();
                map.put(MainActivity.reg,"1");
                FirebaseDatabase.getInstance().getReference("Classes").child(MainActivity.classCode)
                        .child("participants").child(""+System.currentTimeMillis()).setValue(map);

            }
        });
        /////
        result.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(questionlistforstudent.this,Resultpage.class));
            }
        });




        rView.setHasFixedSize(true);
        rView.setLayoutManager(new LinearLayoutManager(this));
        pbar = findViewById(R.id.pbar6);

        if(MainActivity.participantFlag.equals("0")){
            rView.setVisibility(View.VISIBLE);
            submit.setVisibility(View.VISIBLE);
            result.setVisibility(View.GONE);
        }

        uploads = new ArrayList<>();
        // fs = FirebaseStorage.getInstance();
        db = FirebaseDatabase.getInstance().getReference("Classes").child(MainActivity.classCode).child("questions");
        iAdapter = new QuesAdapter(this, uploads);
        iAdapter.setOnItemClickListener(this);
        rView.setAdapter(iAdapter);


        dBListener = db.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                uploads.clear();

                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    Questions upload = postSnapshot.getValue(Questions.class);
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
                Toast.makeText(questionlistforstudent.this, databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                pbar.setVisibility(View.INVISIBLE);
            }
        });
    }

    //Oncreate ended


    @Override
    public void onItemClick(int position) {
        Toast.makeText(questionlistforstudent.this, "Long tap to see options", Toast.LENGTH_SHORT).show();
    }


       @Override
    public void onDeleteClick(int position) {
     }

    @Override
    public void onAnswerClick(int position) {
        Questions selectedItem = uploads.get(position);
        Answerformat upload = new Answerformat();
        upload.setAnswer(MainActivity.answer);
        upload.setQuestion(selectedItem.getQuestion());
        if(selectedItem.getCa().equals(MainActivity.answer)){
            upload.setVerdict("Correct");
        }
        else{
            upload.setVerdict("Wrong");
        }
        FirebaseDatabase.getInstance().getReference("Classes").child(MainActivity.classCode)
                .child("answer").child(MainActivity.reg).child(""+Integer.toString(position)).setValue(upload);
        Toast.makeText(questionlistforstudent.this,"Your answer for Q-"+(position+1)+" is "+MainActivity.answer,Toast.LENGTH_LONG).show();
    }


    protected void onDestroy() {
        super.onDestroy();
        db.removeEventListener(dBListener);
    }
}


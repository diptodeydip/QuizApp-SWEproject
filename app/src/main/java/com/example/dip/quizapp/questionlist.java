package com.example.dip.quizapp;


import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;

import java.util.ArrayList;
import java.util.List;

public class questionlist extends MenuBar implements QuesAdapter.OnItemClickListener {

    RecyclerView rView;
    DatabaseReference db, image_db;
    List<QuestionFormat> uploads;
    ProgressBar pbar;
    QuesAdapter iAdapter;
    FirebaseAuth mAuth;
    String userId;
    FirebaseStorage fs;
    ValueEventListener dBListener;
    Button result,addques;
    GoogleSignInClient mGoogleSignInClient;
    String user_email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_questionlist);

        // Create Ques
        addques = findViewById(R.id.addques);
        ////EXTRAAAA/////

        Query q = FirebaseDatabase.getInstance().getReference().child("Classes").child(MainActivity.classCode)
                .orderByChild("value1").equalTo("1");
        q.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.getChildrenCount()>0){
                    addques.setVisibility(View.GONE);
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });

        //////////////////
        addques.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(questionlist.this,Addques.class);
                //intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });
        result = findViewById(R.id.seeresult);
        result.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(questionlist.this,Resultpage.class));
            }
        });



        rView = findViewById(R.id.recyclerView1);
        rView.setHasFixedSize(true);
        rView.setLayoutManager(new LinearLayoutManager(this));
        pbar = findViewById(R.id.pbar5);

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
                    QuestionFormat upload = postSnapshot.getValue(QuestionFormat.class);
                    upload.setKey(postSnapshot.getKey());
                    uploads.add(upload);
                }
                MainActivity.totalMarks = uploads.size();
                iAdapter.notifyDataSetChanged();
                pbar.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(questionlist.this, databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                pbar.setVisibility(View.INVISIBLE);
            }
        });
    }

    //Oncreate ended


    @Override
    public void onItemClick(int position) {
        Toast.makeText(questionlist.this, "Long tap to see options", Toast.LENGTH_SHORT).show();
    }


      @Override
    public void onDeleteClick(int position) {
        QuestionFormat selectedItem = uploads.get(position);
        final String selectedKey = selectedItem.getKey();
        DatabaseReference db = FirebaseDatabase.getInstance().getReference("Classes").child(MainActivity.classCode)
                .child("questions");
        db.child(selectedKey).removeValue();
          Toast.makeText(questionlist.this, "Deleted", Toast.LENGTH_SHORT).show();
     }

    @Override
    public void onAnswerClick(int position) {
    }


    protected void onDestroy() {
        super.onDestroy();
        db.removeEventListener(dBListener);
    }
}


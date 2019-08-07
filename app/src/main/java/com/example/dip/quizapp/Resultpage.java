package com.example.dip.quizapp;


import android.content.Intent;
import android.support.annotation.NonNull;
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
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Resultpage extends AppCompatActivity  {

    RecyclerView rView;
    DatabaseReference db;
    List<resultformat> uploads;
    ProgressBar pbar;
    ResultAdapter iAdapter;
    FirebaseAuth mAuth;
    String userId;
    FirebaseStorage fs;
    ValueEventListener dBListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resultpage);


        rView = findViewById(R.id.recyclerView3);
        rView.setHasFixedSize(true);
        rView.setLayoutManager(new LinearLayoutManager(this));
        pbar = findViewById(R.id.pbar7);

        uploads = new ArrayList<>();
        db = FirebaseDatabase.getInstance().getReference("Classes").child(MainActivity.classCode).child("result");
        iAdapter = new ResultAdapter(Resultpage.this, uploads);
        rView.setAdapter(iAdapter);


        dBListener = db.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                uploads.clear();

                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    resultformat upload = postSnapshot.getValue(resultformat.class);

                    uploads.add(upload);

                }
                //
                Comparator c = Collections.reverseOrder(new Sortbyreg());
                Collections.sort(uploads, c);
                //
                iAdapter.notifyDataSetChanged();
                pbar.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(Resultpage.this, databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                pbar.setVisibility(View.INVISIBLE);
            }
        });
    }

    //Oncreate ended





    protected void onDestroy() {
        super.onDestroy();
        db.removeEventListener(dBListener);
    }

    class Sortbyreg implements Comparator<resultformat>
    {
        // Used for sorting in ascending order of
        // roll number
        public int compare(resultformat a, resultformat b)
        {
            return Integer.parseInt(a.marks) - Integer.parseInt(b.marks);
        }
    }
}


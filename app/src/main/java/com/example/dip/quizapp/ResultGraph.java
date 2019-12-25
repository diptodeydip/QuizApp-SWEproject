package com.example.dip.quizapp;


import android.content.Intent;
import android.graphics.Color;
import android.os.Environment;
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
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class ResultGraph extends AppCompatActivity  {
    DatabaseReference db;
    ValueEventListener dBListener;
    LineGraphSeries series;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result_graph);


        GraphView graph = (GraphView) findViewById(R.id.graph);
        graph.getViewport().setMinY(0.0);
        graph.getViewport().setMaxY(100.0);
        graph.getViewport().setMinX(0.0);
        graph.getViewport().setMaxX(120.0);
        graph.getViewport().setYAxisBoundsManual(true);
        graph.getViewport().setXAxisBoundsManual(true);


        series = new LineGraphSeries();
        graph.addSeries(series);
        series.setColor(Color.GREEN);
        series.setDrawDataPoints(true);

        db = FirebaseDatabase.getInstance().getReference("Classes").child(MainActivity.classCode).child("result");



        dBListener = db.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                DataPoint dp[] = new DataPoint[(int) dataSnapshot.getChildrenCount()];
                int index = 0;
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    resultformat upload = postSnapshot.getValue(resultformat.class);

                     dp[index] = new DataPoint(Integer.parseInt(upload.getReg()),Integer.parseInt(upload.getMarks()));
                    index++;
                }
                series.resetData(dp);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }

    //Oncreate ended





    protected void onDestroy() {
        super.onDestroy();
        db.removeEventListener(dBListener);
    }



}


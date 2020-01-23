package com.example.dip.quizapp;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.util.HashMap;
import java.util.Map;

public class barGraph extends MenuBar  {
    DatabaseReference db;
    ValueEventListener dBListener;
    LineGraphSeries series;
    Map<String,Integer> info = new HashMap<String,Integer>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bar_graph);


        GraphView graph = (GraphView) findViewById(R.id.graph1);

        graph.getViewport().setMinY(0.0);
        graph.getViewport().setMaxY(100.0);
        graph.getViewport().setMinX(0.0);
        graph.getViewport().setMaxX(MainActivity.totalMarks);
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
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    resultformat upload = postSnapshot.getValue(resultformat.class);
                    try {
                        info.put(upload.getMarks(), info.get(upload.getMarks()) + 1);
                    }
                    catch (Exception e){
                        info.put(upload.getMarks(), 1);
                    }
                }

                DataPoint dp[] = new DataPoint[(int) info.size()];
                int index = 0;

                for (Map.Entry<String,Integer> entry : info.entrySet()){
                    dp[index] = new DataPoint(Integer.parseInt(entry.getKey()),entry.getValue());
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


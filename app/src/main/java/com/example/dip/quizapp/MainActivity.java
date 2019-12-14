package com.example.dip.quizapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    Intent intent;
    DatabaseReference db;
    //public static String user_email;
    public static String classCode,participantFlag,userFlag,answer,reg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

/*        db = FirebaseDatabase.getInstance().getReference();
        Classes up = new Classes();
        up.setClassName("afdsf");
        up.setCode("555");
        String id = "xKhjCAs0V2PubvpSDbku4AbCHzI2";
        Map mp = new HashMap();
        mp.put("className","asdjfk");
        mp.put("code","asf");
        db.child("users").child(id).child("asf").setValue(mp);*/


        intent = new Intent(this,signIn.class);
        Button teacher = (Button)findViewById(R.id.teacher);
        Button student = findViewById(R.id.student);

        teacher.setOnClickListener(
                new Button.OnClickListener(){
                    public void onClick(View v){
                        questionlistforstudent.flag.clear(); //
                        startActivity(intent);
                    }
                }
        );
        student.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,StudentLogIn.class));
            }
        });

    }
}

package com.example.dip.quizapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    Intent intent;
    //public static String user_email;
    public static String classCode,participantFlag,userFlag,answer,reg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        intent = new Intent(this,signIn.class);
        Button teacher = (Button)findViewById(R.id.teacher);
        Button student = findViewById(R.id.student);

        teacher.setOnClickListener(
                new Button.OnClickListener(){
                    public void onClick(View v){
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

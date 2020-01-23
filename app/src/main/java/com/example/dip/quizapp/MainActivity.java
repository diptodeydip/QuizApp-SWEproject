package com.example.dip.quizapp;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends MenuBar {
    private static final int EXTERNAL_STORAGE_CODE = 1, READ_EXTERNAL_STORAGE=2;
    Intent intent;
    public static double totalMarks=1.0;
    DatabaseReference db;
    //public static String user_email;
    public static String classCode,participantFlag,userFlag,answer,reg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //permission
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

            if (checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED
                    || checkSelfPermission(android.Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED) {
                String[] permissions = {android.Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE};
                requestPermissions(permissions, EXTERNAL_STORAGE_CODE);
            }
        }

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
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case EXTERNAL_STORAGE_CODE: {
                if (grantResults.length > 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED) {

                } else {
                    Toast.makeText(this, "Storage permission is required to store data", Toast.LENGTH_SHORT).show();
                    finish();
                }
                break;
            }
        }
    }
}

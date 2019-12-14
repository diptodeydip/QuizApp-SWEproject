package com.example.dip.quizapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import org.apache.commons.lang3.RandomStringUtils;
import java.util.UUID;

public class createClass extends AppCompatActivity {
    Button code_generate,save_class;
    TextView codeview;
    EditText exam_name;
    Classes upload;
    FirebaseAuth mAuth;
    String user_email,code,userId,ques;
    DatabaseReference db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_class);
        save_class = findViewById(R.id.saveclass);
        code_generate = findViewById(R.id.create_code);
        exam_name = findViewById(R.id.Exam_name);
        codeview = findViewById(R.id.exam_code);
        db = FirebaseDatabase.getInstance().getReference();

        mAuth = FirebaseAuth.getInstance();
        Toast.makeText(this, "" + mAuth.getCurrentUser().getEmail(), Toast.LENGTH_LONG).show();
        user_email = mAuth.getCurrentUser().getEmail().toString();
        userId = mAuth.getCurrentUser().getUid();

        upload = new Classes();

        code_generate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //code = UUID.randomUUID().toString();
                code = RandomStringUtils.randomAlphanumeric(6);
                codeview.setText(code);
                save_class.setVisibility(View.VISIBLE);

            }
        });

        save_class.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ques = exam_name.getText().toString();
                check();

            }
        });

    }
    void check(){
        if (ques.isEmpty()) {
            exam_name.setError("Class Name Required");
            exam_name.requestFocus();
            return ;
        }
        uploadtodb();
    }
    void uploadtodb(){
        upload.setCode(code);
        upload.setClassName(ques);
        //Toast.makeText(createClass.this, userId, Toast.LENGTH_LONG).show();
        //Toast.makeText(createClass.this, code, Toast.LENGTH_LONG).show();
        FirebaseDatabase.getInstance().getReference("users").child(userId.toString()).child(code.toString()).setValue(upload);
        Toast.makeText(createClass.this, "Created", Toast.LENGTH_LONG).show();
        finish();
    }
}

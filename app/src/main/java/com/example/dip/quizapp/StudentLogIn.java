package com.example.dip.quizapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

public class StudentLogIn extends AppCompatActivity {
    Button login;
    EditText reg,code;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_log_in);

        code = findViewById(R.id.entrycode);
        reg = findViewById(R.id.regNo);
        login = findViewById(R.id.login);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               check();
            }
        });
    }
    void  check(){
        if(code.getText().toString().isEmpty()){
            code.setError("Valid Code Required");
            code.requestFocus();
            return ;
        }
        if(reg.getText().toString().isEmpty()){
            reg.setError("Reg No Required");
            reg.requestFocus();
            return;
        }

        DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference("Classes");
        rootRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                if (snapshot.hasChild(code.getText().toString())) {
                    Toast.makeText(getApplicationContext(),"Entering",Toast.LENGTH_SHORT).show();
                    questionlistforstudent.flag.clear(); //

                    // check if the user already sat for exam
                    Query userNameQuery = FirebaseDatabase.getInstance().getReference().child("Classes").child(code.getText().toString())
                            .child("participants")
                            .orderByChild(reg.getText().toString()).equalTo("1");
                    userNameQuery.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            if(dataSnapshot.getChildrenCount()>0){
                                MainActivity.participantFlag = "1";
                                Toast.makeText(getApplicationContext(),"You can only see result",Toast.LENGTH_SHORT).show();

                            }
                            else{
                                Toast.makeText(getApplicationContext(),"Only once You can sit for this exam",Toast.LENGTH_LONG).show();
                                MainActivity.participantFlag = "0";
                            }
                        }
                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                        }
                    });
                    //
                    //To check if the classroom is open or not
                    Query q = FirebaseDatabase.getInstance().getReference().child("Classes").child(code.getText().toString())
                            .orderByChild("value").equalTo("1");
                    q.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            if(dataSnapshot.getChildrenCount()==0){
                                MainActivity.participantFlag = "1";
                                Toast.makeText(getApplicationContext(),"You can only see result",Toast.LENGTH_SHORT).show();
                            }
                            MainActivity.classCode = code.getText().toString();
                            MainActivity.userFlag = "Student";
                            MainActivity.reg = reg.getText().toString();
                            finish();
                            startActivity(new Intent(StudentLogIn.this,questionlistforstudent.class));
                        }
                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                        }
                    });
                    ///
                }
                else{
                    code.setError("Valid Code Required");
                    code.requestFocus();
                    return ;
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }
}

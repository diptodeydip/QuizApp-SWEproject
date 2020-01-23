package com.example.dip.quizapp;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.FirebaseDatabase;

public class Addques extends MenuBar {

    Spinner spinner;
    String ca,a,b,c,d,description;
    Button save;
    EditText A,B,C,D,ques;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addques);


        //Spinner

        spinner = (Spinner) findViewById(R.id.spinnner);
        ArrayAdapter<String> myAdapter = new ArrayAdapter<String>(Addques.this,
                android.R.layout.simple_list_item_1,getResources().getStringArray(R.array.items)){
            @Override
            public boolean isEnabled(int position) {
                if (position == 0) {
                    // Disable the first item from Spinner
                    // First item will be use for hint
                    return false;
                } else {
                    return true;
                }
            }

            @Override
            public View getDropDownView(int position, View convertView,
                                        ViewGroup parent) {
                View view = super.getDropDownView(position, convertView, parent);
                TextView tv = (TextView) view;
                if (position == 0) {
                    // Set the hint text color gray
                    tv.setTextColor(Color.GRAY);
                } else {
                    tv.setTextColor(Color.BLACK);
                }
                return view;
            }
        };
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                ca = (String) parent.getItemAtPosition(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        myAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(myAdapter);
        /////

        A = findViewById(R.id.op1);
        B = findViewById(R.id.op2);
        C = findViewById(R.id.op3);
        D = findViewById(R.id.op4);
        ques = findViewById(R.id.question);

        save = findViewById(R.id.saveques);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                a = A.getText().toString();
                b = B.getText().toString();
                c = C.getText().toString();
                d = D.getText().toString();
                description = ques.getText().toString();
                check();
            }
        });
    }
    private void check() {

        if (a.isEmpty()) {
            A.setError("Option required");
            A.requestFocus();
            return ;
        }
        else if (b.isEmpty()) {
            B.setError("Option required");
            B.requestFocus();
            return ;
        }
        else if (description.isEmpty()) {
            ques.setError("Question description required");
            ques.requestFocus();
            return ;
        }
        else if (c.isEmpty()) {
            C.setError("Option required");
            C.requestFocus();
            return ;
        }
        else if (d.isEmpty()) {
            D.setError("Option required");
            D.requestFocus();
            return ;
        }
        else if(ca.equals("Select Answer")){

            Toast.makeText(getApplicationContext(), "Select Answer", Toast.LENGTH_SHORT).show();
            return;
        }
        else {
            uploadtodb();
        }
    }

    void uploadtodb() {
        QuestionFormat q = new QuestionFormat();
        q.setCa(ca);
        q.setOpa(a);
        q.setQuestion(description);
        q.setOpb(b);
        q.setOpc(c);
        q.setOpd(d);
        FirebaseDatabase.getInstance().getReference("Classes").child(MainActivity.classCode).child("questions").child(""+System.currentTimeMillis()).setValue(q);
        Toast.makeText(Addques.this, "Created", Toast.LENGTH_LONG).show();
        finish();
    }
}

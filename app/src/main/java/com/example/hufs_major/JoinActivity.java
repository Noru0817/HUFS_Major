package com.example.hufs_major;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Base64;

public class JoinActivity extends AppCompatActivity {
    private DatabaseReference mRootRef;
    private Button delete, btn;
    private EditText join_name, join_email,join_password;

    private FirebaseDatabase test_count_database;
    private DatabaseReference test_count_ref;

    // ArrayList<Integer> treeList = new ArrayList<>();

    private int test_count = 0;

    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView(R.layout.activity_join);

        join_name = (EditText) findViewById(R.id.join_name);
        join_email = (EditText) findViewById(R.id.join_email);
        join_password = (EditText) findViewById(R.id.join_password) ;
        btn = (Button)findViewById(R.id.join_button);
        delete = (Button) findViewById(R.id.delete);

        mRootRef = FirebaseDatabase.getInstance().getReference("DB");

        test_count_database = FirebaseDatabase.getInstance();
        test_count_ref = test_count_database.getReference("DB");

        test_count_ref.child("Data_count").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists() && dataSnapshot.getValue() != null) {
                    String dataCountStr = dataSnapshot.getValue().toString();
                    if (!dataCountStr.isEmpty()) {
                        long dataCount = Long.parseLong(dataCountStr);
                        test_count = (int) dataCount;
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // 에러 처리
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        // 취소버튼
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent goLoginIntent = new Intent(getApplicationContext(),MainActivity.class);
                startActivity(goLoginIntent);
            }
        });

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

//                treeList.clear();
//                test_count++;
//
//                Info info = new Info(join_name.getText().toString(),join_email.getText().toString(),
//                        join_password.getText().toString());
//
//                treeList.add(info);
//
//                ref.setValue(treeList);
//
//                if (test_count != 1) {
//                    test_count = treeList.get(0);
//                }

                test_count++;

                String name = join_name.getText().toString();
                String email = join_email.getText().toString();
                String password = join_password.getText().toString();

                DatabaseReference ref = mRootRef.child("Student").child("student" + String.valueOf(test_count));

                ref.child("name").setValue(name);
                ref.child("email").setValue(email);
                ref.child("password").setValue(password);

                DatabaseReference data_count_ref = test_count_ref.child("Data_count");
                data_count_ref.setValue(test_count);
            }
        });
    }
}
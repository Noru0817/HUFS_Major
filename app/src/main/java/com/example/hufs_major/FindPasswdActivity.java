package com.example.hufs_major;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class FindPasswdActivity extends AppCompatActivity {
    private DatabaseReference mRootRef;
    private Button find_delete, change_btn;
    private EditText find_id, find_email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_passwd);

        find_id = findViewById(R.id.find_id);
        find_email = findViewById(R.id.find_email);

        change_btn = findViewById(R.id.find_btn);
        find_delete = findViewById(R.id.find_delete);

        mRootRef = FirebaseDatabase.getInstance().getReference("DB");
    }

    @Override
    protected void onStart() {
        super.onStart();

        // 취소버튼
        find_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goMainActivity = new Intent(FindPasswdActivity.this, MainActivity.class);
                startActivity(goMainActivity);
            }
        });

        change_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String name = find_id.getText().toString();
                final String email = find_email.getText().toString();

                mRootRef.child("Student").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        boolean found = false;

                        for (DataSnapshot userSnapshot : dataSnapshot.getChildren()) {
                            Info user = userSnapshot.getValue(Info.class);
                            if (user != null && user.getName().equals(name) && user.getEmail().equals(email)) {
                                // 데이터베이스의 정보가 일치하면, 비밀번호 변경 화면으로 이동
                                Intent goChangePasswd = new Intent(FindPasswdActivity.this, ChangePassword.class);
                                startActivity(goChangePasswd);
                                finish();
                                found = true;
                                break;
                            }
                        }

                        if (!found) {
                            // If no match is found, display an error message
                            Toast.makeText(FindPasswdActivity.this, "일치하는 정보를 찾을 수 없습니다.", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        // Handle database error if any
                        Toast.makeText(FindPasswdActivity.this, "데이터베이스 오류", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }
}

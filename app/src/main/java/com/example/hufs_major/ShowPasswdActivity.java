//package com.example.mylab;
//
//import android.content.Intent;
//import android.os.Bundle;
//import android.provider.ContactsContract;
//import android.widget.TextView;
//
//import androidx.annotation.NonNull;
//import androidx.appcompat.app.AppCompatActivity;
//
//import com.google.firebase.database.DataSnapshot;
//import com.google.firebase.database.DatabaseError;
//import com.google.firebase.database.DatabaseReference;
//import com.google.firebase.database.FirebaseDatabase;
//import com.google.firebase.database.ValueEventListener;
//
//import java.util.ArrayList;
//
//public class ShowPasswdActivity extends AppCompatActivity {
//
//
//    private FirebaseDatabase database;
//    private DatabaseReference databaseReference;
//
//    private ArrayList<Info> arrayList;
//
//    private TextView test;
//
//    String passwd = "";
//
//
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_show_passwd);
//
//
//
//        test = findViewById(R.id.test);
//
//        Intent intent = getIntent();
//        String[] check_info = intent.getStringArrayExtra("check_info");
//
//        //이전 비밀번호 찾기에서 입력한 아이디와 이메일 데이터 제대로 가지고 왔는지 확인
////        test.setText(check_info[0] + check_info[1]);
//
//        database = FirebaseDatabase.getInstance();
//
//        databaseReference = database.getReference("DB");
//
//        arrayList = new ArrayList<>();
//
//        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
//
//            @Override
//            public void onDataChange(@NonNull DataSnapshot datasnapshot) {
//
//
//
//
//
//                //왜 이 for문이 실행이 안되지
//                for(DataSnapshot snapshot : datasnapshot.getChildren()) {
//
//                    Info info = snapshot.getValue(Info.class);
//
//                    arrayList.add(info);
//                }
//
//
////                test.setText(String.valueOf(arrayList.size()));
//
//
//                for (Info info : arrayList) {
//                    if (info.getName().equals(check_info[0]) && info.getEmail().equals(check_info[1])) {
//                        test.setText(info.getPassword());
//                        break;
//                    }
//                }
//
//
//
//
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });
//    }
//}

//-----------------------------------------------------------------------------------------------
package com.example.hufs_major;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ShowPasswdActivity extends AppCompatActivity {

    private FirebaseDatabase database;
    private DatabaseReference databaseReference;
    private ArrayList<Info> arrayList;
    private TextView test;

    private Button btn_check;

    String passwd = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_passwd);

        test = findViewById(R.id.shown_passwd);
        Intent intent = getIntent();
        String[] check_info = intent.getStringArrayExtra("check_info");

        // 이전 비밀번호 찾기에서 입력한 아이디와 이메일 데이터 제대로 가지고 왔는지 확인
//        test.setText(check_info[0] + check_info[1]);

        btn_check = findViewById(R.id.back_btn);

        database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference("DB");

        arrayList = new ArrayList<>();

        //Toast.makeText(this, check_info[0] + check_info[1], Toast.LENGTH_SHORT).show();



//        Query myQry = databaseReference.orderByChild("email").equalTo("hello2");
//        myQry.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//
//                //Toast.makeText(ShowPasswdActivity.this, "asdasd", Toast.LENGTH_SHORT).show();
//                for (DataSnapshot dsnapshot : snapshot.getChildren()) {
//
//                    Info info = dsnapshot.getValue(Info.class);
//                    arrayList.add(info);
//                    test.setText(info.getPassword());
//                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });

        databaseReference.child("Student").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot mysnapshot: snapshot.getChildren()) {
                    Info myinfo = mysnapshot.getValue(Info.class);
                    if (myinfo.getName().equals(check_info[0]) && myinfo.getEmail().equals(check_info[1])) {
                        test.setText(myinfo.getPassword());
                        break;
                    }
                }
                // Toast.makeText(ShowPasswdActivity.this, myinfo.password, Toast.LENGTH_LONG).show();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        btn_check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goMain = new Intent(ShowPasswdActivity.this, MainActivity.class);
                startActivity(goMain);
            }
        });
    }
}


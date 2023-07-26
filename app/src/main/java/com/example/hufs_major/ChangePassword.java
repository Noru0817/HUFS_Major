package com.example.hufs_major;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class ChangePassword extends AppCompatActivity {
    private EditText currentPasswordEditText;
    private EditText newPasswordEditText;
    private EditText confirmNewPasswordEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_passwd);

        Intent intent = getIntent();
        String[] check_info = intent.getStringArrayExtra("check_info");

        currentPasswordEditText = findViewById(R.id.current_password_edit_text);
        newPasswordEditText = findViewById(R.id.new_password_edit_text);
        confirmNewPasswordEditText = findViewById(R.id.confirm_new_password_edit_text);

        Button changePasswordButton = findViewById(R.id.change_password_button);
        Button change_delete = (Button) findViewById(R.id.change_delete);

        change_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goMainActivity = new Intent(ChangePassword.this, FindPasswdActivity.class);
                startActivity(goMainActivity);
            }
        });

        changePasswordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String currentPassword = currentPasswordEditText.getText().toString();
                String newPassword = newPasswordEditText.getText().toString();
                String confirmNewPassword = confirmNewPasswordEditText.getText().toString();

                if (TextUtils.isEmpty(currentPassword)) {
                    currentPasswordEditText.setError("현재 비밀번호를 입력해주세요.");
                    currentPasswordEditText.requestFocus();
                    return;
                }

                if (TextUtils.isEmpty(newPassword)) {
                    newPasswordEditText.setError("새로운 비밀번호를 입력해주세요.");
                    newPasswordEditText.requestFocus();
                    return;
                }

                if (TextUtils.isEmpty(confirmNewPassword)) {
                    confirmNewPasswordEditText.setError("새로운 비밀번호를 입력해주세요.");
                    confirmNewPasswordEditText.requestFocus();
                    return;
                }

                if (!newPassword.equals(confirmNewPassword)) {
                    confirmNewPasswordEditText.setError("비밀번호가 일치하지 않습니다.");
                    confirmNewPasswordEditText.requestFocus();
                    return;
                }

                // TODO: Perform password change logic here
                // 비밀번호를 파이어베이스에서 가져옴
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser(); // 수정 요망
                if (user != null) {
                    newPassword = confirmNewPassword;

                    user.updatePassword(newPassword)
                            .addOnCompleteListener(task -> {
                                if (task.isSuccessful()) {
                                    Toast.makeText(ChangePassword.this, "비밀번호가 변경 되었습니다.", Toast.LENGTH_SHORT).show();
                                    finish();
                                } else {
                                    Toast.makeText(ChangePassword.this, "비밀번호 변경에 실패했습니다.", Toast.LENGTH_SHORT).show();
                                }
                            });
                } else {
                    Toast.makeText(ChangePassword.this, "비밀번호 변경에 실패하였습니다. 사용자를 찾을 수 없습니다.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
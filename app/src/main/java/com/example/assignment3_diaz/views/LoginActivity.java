package com.example.assignment3_diaz.views;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.example.assignment3_diaz.databinding.ActivityLoginBinding;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;

    private ActivityLoginBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        mAuth = FirebaseAuth.getInstance();

        binding.loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = binding.editEmailAddress.getText().toString();
                String password = binding.editPassword.getText().toString();

                // if empty
                if (password.isBlank() || email.isBlank()) {
                    Toast.makeText(LoginActivity.this, "Password or Email is empty", Toast.LENGTH_SHORT).show();
                    return;
                }

                Login(email, password);
            }
        });

        binding.registerViewButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentObj = new Intent(getApplicationContext(), RegisterActivity.class);
                startActivity(intentObj);
            }
        });
    }

    private void Login(String email, String password){
        mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    Log.d("tag","signInWithEmailAndPassword:good stuff");
                    FirebaseUser user = mAuth.getCurrentUser();
                    Toast.makeText(LoginActivity.this,"Logged In uID: " + user.getUid(),Toast.LENGTH_SHORT).show();
                    Intent intentObj = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intentObj);
                    finish();
                }
                else{
                    Log.d("tag","signInWithEmailAndPassword:failure",task.getException());
                    Toast.makeText(LoginActivity.this,"Auth failed",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
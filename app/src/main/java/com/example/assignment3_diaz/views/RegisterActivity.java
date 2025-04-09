package com.example.assignment3_diaz.views;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import com.example.assignment3_diaz.databinding.ActivityRegisterBinding;
import com.example.assignment3_diaz.model.Movie;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class RegisterActivity extends AppCompatActivity {
    private ActivityRegisterBinding binding;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        binding = ActivityRegisterBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        mAuth = FirebaseAuth.getInstance();

        binding.registerButton.setOnClickListener(v -> {
            String email = Objects.requireNonNull(binding.editEmailAddress.getText()).toString();
            String password = Objects.requireNonNull(binding.editPassword.getText()).toString();
            String confirmPassword = Objects.requireNonNull(binding.confirmPassword.getText()).toString();

            // if empty
            if (password.isBlank() || email.isBlank()) {
                Toast.makeText(RegisterActivity.this, "Password or Email is empty", Toast.LENGTH_SHORT).show();
                return;
            }


            // if they do not match
            if (!password.equals(confirmPassword)) {
                Toast.makeText(RegisterActivity.this, "Passwords do not match", Toast.LENGTH_SHORT).show();
                return;
            }

            RegisterUser(email, password);
        });
    }

    private void RegisterUser(String email, String password) {
        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                Log.d("tag", "createUserWithEmail: success");
                FirebaseUser user = mAuth.getCurrentUser();
                assert user != null;
                String uid = user.getUid();

                Toast.makeText(RegisterActivity.this, "Registered uID: " + uid, Toast.LENGTH_SHORT).show();

                FirebaseFirestore db = FirebaseFirestore.getInstance();
                Map<String, Object> userData = new HashMap<>();
                userData.put("id", uid);
                userData.put("email", email);
                userData.put("favourites", new ArrayList<Movie>());

                db.collection("Users").document(uid)
                        .set(userData)
                        .addOnSuccessListener(aVoid -> Log.d("Firestore", "User document created with UID"))
                        .addOnFailureListener(e -> Log.w("Firestore", "Error adding user document", e));

                Intent intentObj = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intentObj);
                finish();
            } else {
                Log.d("tag", "createUserWithEmail: failure", task.getException());
                Toast.makeText(RegisterActivity.this, "Auth failed", Toast.LENGTH_SHORT).show();
            }
        });
    }
}

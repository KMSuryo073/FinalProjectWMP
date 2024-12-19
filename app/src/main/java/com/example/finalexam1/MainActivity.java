package com.example.finalexam1;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {
    Button registerButton, loginButton;
    EditText usernameEditText, passwordEditText;
    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Enable edge-to-edge layout for better UI
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        // Adjust UI to handle system bars (e.g., status bar, navigation bar)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Initialize views
        usernameEditText = findViewById(R.id.username);
        passwordEditText = findViewById(R.id.password);
        registerButton = findViewById(R.id.register);
        loginButton = findViewById(R.id.login);

        // Initialize Firebase
        FirebaseApp.initializeApp(this);
        auth = FirebaseAuth.getInstance();

        // Register button click listener
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = usernameEditText.getText().toString();
                String password = passwordEditText.getText().toString();

                // Log button click and inputs
                Log.d("MainActivity", "Register button clicked");
                Log.d("MainActivity", "Username: " + username);
                Log.d("MainActivity", "Password: " + password);

                if (TextUtils.isEmpty(username) || TextUtils.isEmpty(password)) {
                    Log.d("MainActivity", "Empty username or password");
                    Toast.makeText(MainActivity.this, "Please input username and password", Toast.LENGTH_LONG).show();
                } else {
                    registerUser(username, password);
                }
            }
        });

        // Login button click listener to navigate to login screen
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Log button click and navigate to login page
                Log.d("MainActivity", "Login button clicked");
                Intent intent = new Intent(MainActivity.this, LoginActivity.class); // Assuming LoginActivity exists
                startActivity(intent);
            }
        });
    }

    private void registerUser(String username, String password) {
        // Register user with Firebase Authentication
        auth.createUserWithEmailAndPassword(username, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    Log.d("MainActivity", "Registration successful");
                    Toast.makeText(MainActivity.this, "Registration Successful", Toast.LENGTH_LONG).show();

                    // Navigate to login page after successful registration
                    Intent intent = new Intent(MainActivity.this, LoginActivity.class); // Navigate to Login page
                    startActivity(intent);
                    finish(); // Finish the current activity to prevent going back to register page
                } else {
                    Log.e("MainActivity", "Registration failed", task.getException());
                    Toast.makeText(MainActivity.this, "Registration Failed: " + task.getException().getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}

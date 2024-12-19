package com.example.finalexam1;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class SelectSubjectActivity extends AppCompatActivity {

    private LinearLayout subjectContainer;
    private Button enrollButton;
    private FirebaseFirestore db;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_subject);

        subjectContainer = findViewById(R.id.subjectContainer);
        enrollButton = findViewById(R.id.enrollButton);

        // Initialize Firestore and FirebaseAuth
        db = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();

        enrollButton.setOnClickListener(v -> {
            // Collect selected subjects
            StringBuilder enrolledSubjects = new StringBuilder();
            int totalCredits = 0;
            int selectedCount = 0; // To count selected subjects

            for (int i = 0; i < subjectContainer.getChildCount(); i++) {
                View child = subjectContainer.getChildAt(i);
                if (child instanceof LinearLayout) {
                    // Find the CheckBox within the LinearLayout
                    CheckBox checkBox = child.findViewById(R.id.subject1 + i); // Adjust this line
                    if (checkBox != null && checkBox.isChecked()) {
                        enrolledSubjects.append(checkBox.getText()).append("\n");
                        totalCredits += 4; // Each subject is worth 4 credits
                        selectedCount++; // Increment the count of selected subjects
                    }
                }
            }

            // Check if the selected count exceeds the limit
            if (selectedCount > 6) {
                Toast.makeText(SelectSubjectActivity.this, "You can select a maximum of 24 Credits.", Toast.LENGTH_SHORT).show();
                return; // Exit the method if the limit is exceeded
            }

            if (enrolledSubjects.length() > 0) {
                // Save the subjects and credits to Firestore
                FirebaseUser user = auth.getCurrentUser();
                if (user != null) {
                    String userId = user.getUid();
                    // Create a map to hold the data
                    Map<String, Object> enrollmentData = new HashMap<>();
                    enrollmentData.put("selectedSubjects", enrolledSubjects.toString());
                    enrollmentData.put("totalCredits", totalCredits);

                    // Save to Firestore
                    db.collection("enrollments").document(userId)
                            .set(enrollmentData)
                            .addOnSuccessListener(aVoid -> {
                                // Navigate to EnrollmentSummaryActivity
                                Intent intent = new Intent(SelectSubjectActivity.this, EnrollmentSummaryActivity.class);
                                startActivity(intent);
                            })
                            .addOnFailureListener(e -> {
                                Toast.makeText(SelectSubjectActivity.this, "Error saving data", Toast.LENGTH_SHORT).show();
                            });
                } else {
                    Toast.makeText(SelectSubjectActivity.this, "User not logged in", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(SelectSubjectActivity.this, "No subjects selected", Toast.LENGTH_SHORT).show();
            }
        });
    }
}

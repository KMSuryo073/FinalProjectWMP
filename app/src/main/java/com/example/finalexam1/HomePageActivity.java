package com.example.finalexam1;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class HomePageActivity extends AppCompatActivity {

    private Button selectSubjectButton;
    private Button enrollmentSummaryButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page); // Link to your layout

        selectSubjectButton = findViewById(R.id.selectSubjectButton);
        enrollmentSummaryButton = findViewById(R.id.enrollmentSummaryButton);

        // OnClickListener for Select Subject button
        selectSubjectButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate to SelectSubjectActivity
                Intent intent = new Intent(HomePageActivity.this, SelectSubjectActivity.class);
                startActivity(intent);
            }
        });

        // OnClickListener for Enrollment Summary button
        enrollmentSummaryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate to EnrollmentSummaryActivity
                Intent intent = new Intent(HomePageActivity.this, EnrollmentSummaryActivity.class);
                startActivity(intent);
            }
        });
    }
}

package com.uitests;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.uitests.model.Student;
import com.uitests.model.Subject;

import java.util.List;

public class hallticket extends AppCompatActivity {

    private int rollNumber;
    private String answerScriptId;
    private String subjectCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hallticket);

        // Retrieve the Student object from the intent
        Intent intent = getIntent();
        if (intent != null && intent.hasExtra("student")) {
            Student student = (Student) intent.getSerializableExtra("student");

            // Display student details in TextViews
            TextView rollNumberTextView = findViewById(R.id.rollNumberTextView);
            rollNumberTextView.setText("Roll Number: " + student.getRollNumber());
            rollNumber = student.getRollNumber();
            subjectCode = student.getSubjectCode();
           // String subjectCode = student.getSubjectCode();

            TextView nameTextView = findViewById(R.id.nameTextView);
            nameTextView.setText("Name: " + student.getName());

            TextView degreeTextView = findViewById(R.id.degreeTextView);
            degreeTextView.setText("Degree: " + student.getDegree());

            TextView departmentTextView = findViewById(R.id.departmentTextView);
            departmentTextView.setText("Department: " + student.getDepartment());

            List<Subject> subjects = student.getCurrentCoursesEnrolled();
            TextView subjectsTextView = findViewById(R.id.subjectsTextView);
            StringBuilder subjectsText = new StringBuilder("Subjects Enrolled:\n");

            for (Subject subject : subjects) {
                subjectsText.append(subject.getSubCode()).append(" - ").append(subject.getSubName()).append("\n");
            }
            subjectsTextView.setText(subjectsText.toString());
        } else {
            // Handle the case where the intent doesn't contain the expected data
            Toast.makeText(this, "No student data found", Toast.LENGTH_SHORT).show();
        }

        // Get the roll number from the student
       // rollNumber = student.getRollNumber();
       // answerScriptId = "";
        // Find the button and set OnClickListener to start scanner2 activity
        Button btnScanner2 = findViewById(R.id.btnScanner2);
        btnScanner2.setOnClickListener(view -> {
            // Create an Intent to start the scanner2 activity
            Intent scanner2Intent = new Intent(hallticket.this, scanner2.class);
          scanner2Intent.putExtra("rollNumber", rollNumber);
          scanner2Intent.putExtra("subjectCode", subjectCode);
            Log.d("Scanner-halltickt", "Subject Codeh: " + subjectCode);
            startActivity(scanner2Intent);
        });
    }
}

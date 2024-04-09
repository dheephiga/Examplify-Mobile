package com.uitests;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;
import com.uitests.model.Student;
import com.uitests.retrofit.RetrofitService;
import com.uitests.retrofit.studentApi;

import java.util.ArrayList;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class homeactivity extends AppCompatActivity {
    private Button btnGetData;
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        btnGetData = findViewById(R.id.btnGetData);
        listView = findViewById(R.id.listviewData);

        btnGetData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RetrofitService retrofitService = new RetrofitService(); // Create an instance of RetrofitService
                studentApi studentApi = retrofitService.getRetrofit().create(studentApi.class);
                Call<List<Student>> call = studentApi.getAllData();

                call.enqueue(new Callback<List<Student>>() {
                    @Override
                    public void onResponse(Call<List<Student>> call, Response<List<Student>> response) {
                        List<Student> students = response.body();
                        if (students != null && !students.isEmpty()) {
                            // Populate your ListView with student information
                            List<String> studentInfoList = new ArrayList<>();
                            for (Student student : students) {
                                String studentInfo = "Roll Number: " + student.getRollNumber() +
                                        "\nName: " + student.getName() +
                                        "\nDegree: " + student.getDegree() +
                                        "\nDepartment: " + student.getDepartment();
                                studentInfoList.add(studentInfo);
                            }

                            // Use an ArrayAdapter to display the student information in the ListView
                            ArrayAdapter<String> adapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_list_item_1, studentInfoList);
                            listView.setAdapter(adapter);
                        } else {
                            // Handle empty or null response
                            Toast.makeText(homeactivity.this, "No data available", Toast.LENGTH_LONG).show();
                        }
                    }



                    @Override
                    public void onFailure(Call<List<Student>> call, Throwable t) {
                        t.printStackTrace();  // Print the stack trace to get more details about the error

                        String errorMessage = "An error has occurred. Check logcat for details.";
                        if (t.getMessage() != null) {
                            errorMessage = "An error has occurred: " + t.getMessage();
                        }

                        Toast.makeText(homeactivity.this, errorMessage, Toast.LENGTH_LONG).show();
                    }

                });
            }
        });

// Add OnClickListener for the Scanner button
        Button btnScanner = findViewById(R.id.btnScanner);
        btnScanner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Create an Intent to start the Scanner activity
                Intent intent = new Intent(homeactivity.this, scanner1.class);
                startActivity(intent);
            }
        });

    }
}

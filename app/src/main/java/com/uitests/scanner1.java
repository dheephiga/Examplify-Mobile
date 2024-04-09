package com.uitests;
import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.uitests.model.Student;
import com.uitests.retrofit.RetrofitService;
import com.uitests.retrofit.studentApi;
import com.uitests.retrofit.studentId;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class scanner1 extends AppCompatActivity {

    private static final int PERMISSION_REQUEST_CAMERA = 1;
    private RetrofitService retrofitService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scanner1);

        retrofitService = new RetrofitService();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, PERMISSION_REQUEST_CAMERA);
            } else {
                initQRCodeScanner();
            }
        } else {
            initQRCodeScanner();
        }
    }
//hallticket hl = new hallticket();
    private void initQRCodeScanner() {
        IntentIntegrator integrator = new IntentIntegrator(this);
        integrator.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE);
        integrator.setOrientationLocked(false);
        integrator.setPrompt("Scan Student ID");
        integrator.initiateScan();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result != null) {
            if (result.getContents() == null) {
                Toast.makeText(this, "Scan cancelled", Toast.LENGTH_LONG).show();
            } else {
                sendScannedDataToServer(result.getContents());
            }
        }
    }

    private void sendScannedDataToServer(String scannedData) {
        try {
            // Attempt to parse the scanned data as an integer
            int studentId = Integer.parseInt(scannedData);

            // Create a Call object for the API method (e.g., getStudentById)
            studentId studentId1 = retrofitService.getRetrofit().create(studentId.class);
            studentApi student = retrofitService.getRetrofit().create(studentApi.class);
            Call<Student> call = studentId1.getStudentById(studentId);
            Call<String> call1 = student.getSubjectCode(studentId);

            // Asynchronously enqueue the call
            call.enqueue(new Callback<Student>() {
                @Override
                public void onResponse(Call<Student> call, Response<Student> response) {
                    if (response.isSuccessful()) {
                        // Handle the server response here
                        Student student = response.body();
                        int rollNumber = student.getRollNumber();
                        String subjectCode = student.getSubjectCode();
                        Log.d("Scanner1", "Subject Code1: " + subjectCode);
                    Intent intent = new Intent(scanner1.this, hallticket.class);
                    Intent scanner2Intent = new Intent(scanner1.this, scanner2.class);
                    scanner2Intent.putExtra("rollNumber", rollNumber);
                    scanner2Intent.putExtra("subjectCode", subjectCode);
                        Log.d("Scanner1", "Subject Code1: " + subjectCode);

                        // Pass student data to the next activity if needed
                     intent.putExtra("student", student);
                     startActivity(intent);

                    } else {
                        // Handle error
                        Toast.makeText(scanner1.this, "Error: " + response.message(), Toast.LENGTH_LONG).show();
                    }
                }

                @Override
                public void onFailure(Call<Student> call, Throwable t) {
                    // Handle failure
                    Toast.makeText(scanner1.this, "Error: " + t.getMessage(), Toast.LENGTH_LONG).show();
                }
            });
        } catch (NumberFormatException e) {
            // Handle the case where the scanned data is not a valid integer
            Toast.makeText(scanner1.this, "Invalid QR code content", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSION_REQUEST_CAMERA) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                initQRCodeScanner();
            } else {
                Toast.makeText(this, "Camera permission is required", Toast.LENGTH_LONG).show();
                finish();
            }
        }
    }
}

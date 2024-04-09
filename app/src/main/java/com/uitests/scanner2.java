package com.uitests;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.widget.Toast;
import android.widget.TextView;
import android.app.AlertDialog;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.uitests.retrofit.RetrofitService;
import com.uitests.retrofit.TrackingService;
import com.uitests.model.ExamTracking;

import retrofit2.*;
import android.util.Log;
import java.io.IOException;

public class scanner2 extends AppCompatActivity {

    private static final int PERMISSION_REQUEST_CAMERA = 1;
    private int rollNumber;
    private String subjectCode;
    private RetrofitService retrofitService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scanner2);
        retrofitService = new RetrofitService();

        // Retrieve roll number and subject code from the intent
        Intent intent = getIntent();
        if (intent != null) {
            rollNumber = intent.getIntExtra("rollNumber",0);
            subjectCode = intent.getStringExtra("subjectCode");

            // Check camera permission and initialize QR code scanner
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
    }

    private void initQRCodeScanner() {
        IntentIntegrator integrator = new IntentIntegrator(this);
        integrator.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE);
        integrator.setOrientationLocked(false);
        integrator.setPrompt("Scan answer script QR code");
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
                // Display scanned data in a TextView
                TextView scannedDataTextView = findViewById(R.id.scannedDataTextView);
                String answerScript = result.getContents();
                Log.d("Scanner", "Scanned data: " + result.getContents());
                // scannedDataTextView.setText(answerScript);

                // Log the rollNumber and subjectCode
                Log.d("Scanner", "Roll Number: " + rollNumber);
                Log.d("Scanner", "Subject Code: " + subjectCode);

                // Log the rollNumber obtained from the intent directly
                Intent intent = getIntent();
                if (intent != null) {
                    int rollNumberFromIntent = intent.getIntExtra("rollNumber", -1);
                    Log.d("Scanner", "Roll Number from Intent: " + rollNumberFromIntent);
                }

                // Check if the rollNumber is correctly retrieved from the intent
                // Ensure that the rollNumber obtained from the intent is passed to the API call
                TrackingService trackingService = retrofitService.getRetrofit().create(TrackingService.class);
                Call<String> call = trackingService.createTrackingTicket(rollNumber, subjectCode, answerScript);

                call.enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
                        if (response.isSuccessful()) {
                            // Handle successful response
                            String responseBody = response.body();
                            // Display the response body in a popup dialog
                            displayPopupDialog("Response", responseBody);
                        } else {
                            // Handle error
                            String errorMessage = "Unknown error occurred";
                            displayPopupDialog("Error", errorMessage);
                        }
                    }

                    private void displayPopupDialog(String title, String message) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(scanner2.this);
                        builder.setTitle(title)
                                .setMessage(message)
                                .setPositiveButton("OK", null); // Adding OK button, null listener will dismiss the dialog when clicked
                        AlertDialog dialog = builder.create();
                        dialog.show();
                    }


                    @Override
                    public void onFailure(Call<String> call, Throwable t) {
                        // Handle failure
                        String errorMessage = t.getMessage();
                        if (errorMessage == null || errorMessage.isEmpty()) {
                            errorMessage = "Unknown failed";
                        }
                        Toast.makeText(scanner2.this, "Error: " + errorMessage, Toast.LENGTH_LONG).show();
                        Log.e("Failure", "Error: " + errorMessage, t);
                    }



                });


            }
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
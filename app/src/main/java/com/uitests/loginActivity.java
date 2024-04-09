package com.uitests;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class loginActivity extends AppCompatActivity {

    EditText username, password;
    Button login;

    dbHelper da;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        username = (EditText) findViewById(R.id.username1);
        password = (EditText) findViewById(R.id.password1);
        login = (Button) findViewById(R.id.btnsignin1);
        da = new dbHelper(this);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String user = username.getText().toString();
                String pass = password.getText().toString();

                if(user.equals("")||pass.equals(""))
                {
                    Toast.makeText(loginActivity.this,"Please enter all the fields", Toast.LENGTH_SHORT).show();
                }
                else {
                    Boolean checkuserpass = da.checkUsernamePassword(user, pass);
                    if(checkuserpass){
                        Toast.makeText(loginActivity.this, "Sign in successful", Toast.LENGTH_SHORT).show();
                        //Intent intent = new Intent(getApplicationContext(),scanner1.class);
                        Intent intent = new Intent(getApplicationContext(),homeactivity.class);
                        startActivity(intent);
                    }
                    else{
                        Toast.makeText(loginActivity.this, "Invalid credentials", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }
}
package com.example.a9thili.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.a9thili.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class RegisterActivity extends AppCompatActivity {

    EditText name,email,pass;
    private FirebaseAuth auth;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        //getSupportActionBar().hide();
        auth =FirebaseAuth.getInstance();
        name=findViewById(R.id.name);
        email=findViewById(R.id.email);
        pass=findViewById(R.id.pass);
        /*sharedPreferences=getSharedPreferences("OnBoardingScreen",MODE_PRIVATE);

        boolean firsttime=sharedPreferences.getBoolean("firsttime ",true);
        if (firsttime){
            SharedPreferences.Editor editor=sharedPreferences.edit();
            editor.putBoolean("firsttime",true);
            editor.apply();

            Intent intent=new Intent(RegisterActivity.this,OnBoardingActivity.class);
            startActivity(intent);
           // finish();
        }*/


    }
    public void signup(View view) {

        String username= name.getText().toString();
        String usermail= email.getText().toString();
        String userpass= pass.getText().toString();

        if(TextUtils.isEmpty(username)){
            Toast.makeText(this,"Enter Name ",Toast.LENGTH_SHORT).show();
            return;
        }
        if(TextUtils.isEmpty(usermail)){
            Toast.makeText(this,"Enter eMail ",Toast.LENGTH_SHORT).show();
            return;
        }
        if(TextUtils.isEmpty(userpass)){
            Toast.makeText(this,"Enter Password ",Toast.LENGTH_SHORT).show();
            return;
        }
        if(userpass.length()<6){
            Toast.makeText(this,"Password short ,enter 6 characters  ",Toast.LENGTH_SHORT).show();
            return;
        }

        auth.createUserWithEmailAndPassword(usermail,userpass)
                .addOnCompleteListener(RegisterActivity.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    Toast.makeText(RegisterActivity.this,"Sucessfully Registered",Toast.LENGTH_SHORT);
                    startActivity(new Intent(RegisterActivity.this,MainActivity.class));


                }else {
                    Toast.makeText(RegisterActivity.this,"Registration failed "+task.getException(),Toast.LENGTH_SHORT);

                }
            }
        });

        startActivity(new Intent(RegisterActivity.this,MainActivity.class));
    }

    public void signin(View view) {
        startActivity(new Intent(RegisterActivity.this,LoginActivity.class));

    }


}
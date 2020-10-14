package com.example.assesmentapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Objects;

public class FormActivity extends AppCompatActivity {

    private TextView name,email,address,phone;
    private CheckBox cooking,reading,sport;
    private FirebaseAuth mAuth;
    private FirebaseUser CurrentUser;
    private FirebaseDatabase mFirebaseDatabase;
    private ProgressBar progressBar;
    private DatabaseReference databaseReference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form);
        name = findViewById(R.id.name);
        email = findViewById(R.id.email);
        address = findViewById(R.id.address);
        phone = findViewById(R.id.phone);
        cooking = findViewById(R.id.cooking);
        reading = findViewById(R.id.reading);
        sport = findViewById(R.id.sport);
        progressBar = findViewById(R.id.progressBar);
        FirebaseApp.initializeApp(this);
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mAuth = FirebaseAuth.getInstance();
        CurrentUser =mAuth.getCurrentUser() ;
        databaseReference = mFirebaseDatabase.getReference();
        findViewById(R.id.submit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                writeNewProfile();
            }
        });
    }
    private void writeNewProfile() {
        String emailValue = email.getText().toString().trim();
        if (emailValue.isEmpty()) {
            email.setError("Email is required");
            email.requestFocus();
            return;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(emailValue).matches()) {
            email.setError("Please enter a valid email");
            email.requestFocus();
            return;
        }
        String nameValue = name.getText().toString().trim();
        if (nameValue.isEmpty()) {
            name.setError("Name is required");
            name.requestFocus();
            return;
        }
        String addressValue = address.getText().toString().trim();
        if (addressValue.isEmpty()) {
            address.setError("Address is required");
            address.requestFocus();
            return;
        }
        String phoneValue = phone.getText().toString().trim();
        if (phoneValue.isEmpty()||phoneValue.length()<10) {
            phone.setError("Phone is required");
            phone.requestFocus();
            return;
        }
        if (!Patterns.PHONE.matcher(phoneValue).matches()) {
            phone.setError("Please enter a valid email");
            phone.requestFocus();
            return;
        }

        String hobiesValue ="Not Mentioned";

        if(sport.isChecked()||cooking.isChecked()||reading.isChecked() ){
            hobiesValue="";
        }

        if (sport.isChecked() ){
            hobiesValue=hobiesValue +" "+ sport.getText().toString();
        }

        if (cooking.isChecked() ){
            hobiesValue=hobiesValue +" "+cooking.getText().toString();
        }

        if (reading.isChecked()){
            hobiesValue=hobiesValue +" "+ reading.getText().toString();
        }
        if(progressBar!=null) {
            progressBar.setVisibility(View.VISIBLE);
        }

        ProfileModel profile = new ProfileModel(nameValue, emailValue, addressValue, phoneValue, hobiesValue);
        databaseReference.child("users").child(CurrentUser.getUid()).child(nameValue).setValue(profile).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(progressBar!=null) {
                    progressBar.setVisibility(View.GONE);
                }
                if (task.isSuccessful()) {
                    Toast.makeText(getApplicationContext(), "Profile Saved", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getApplicationContext(), Objects.requireNonNull(task.getException()).getMessage(), Toast.LENGTH_LONG).show();
                    System.out.println(Objects.requireNonNull(task.getException()).getMessage());
                }
            }
        });
    }
    @Override
    protected void onStart() {
        super.onStart();
        if (mAuth.getCurrentUser() == null) {
            finish();
            startActivity(new Intent(this, LoginActivity.class));
        }
    }
}
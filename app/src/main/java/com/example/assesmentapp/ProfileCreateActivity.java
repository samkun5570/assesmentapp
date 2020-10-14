package com.example.assesmentapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Objects;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileCreateActivity extends AppCompatActivity implements View.OnClickListener {

    private FirebaseAuth mAuth;
    private FirebaseUser CurrentUser;
    private DatabaseReference mLocationDatabaseReference;
    private TextView email,userID,name,phone;
    private EditText emailUpdate,passwordUpdate,nameUpdate;
    private CircleImageView circleImageView;
    private ProgressBar progressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState)  {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        FirebaseApp.initializeApp(this);
        FirebaseDatabase mFirebaseDatabase = FirebaseDatabase.getInstance();
        mAuth = FirebaseAuth.getInstance();
        CurrentUser = mAuth.getCurrentUser();
        email = findViewById(R.id.email);
        userID = findViewById(R.id.userID);
        name = findViewById(R.id.name);
//        phone = findViewById(R.id.phone);
        circleImageView = findViewById(R.id.profileImage);
        nameUpdate = findViewById(R.id.nameUpdate);
        progressBar = findViewById(R.id.progressbar);
        passwordUpdate = findViewById(R.id.passwordUpdate);
        emailUpdate = findViewById(R.id.emailUpdate);
        findViewById(R.id.buttonUpdateName).setOnClickListener(this);
        findViewById(R.id.buttonUpdateEmail).setOnClickListener(this);
        findViewById(R.id.buttonUpdateProfile).setOnClickListener(this);
        findViewById(R.id.buttonUpdatePassword).setOnClickListener(this);
        if(CurrentUser!=null){
            userID.setText(CurrentUser.getUid());
            email.setText(CurrentUser.getEmail());
            name.setText(CurrentUser.getDisplayName());
//            phone.setText(CurrentUser.getPhoneNumber());
            Glide.with(ProfileCreateActivity.this).load(CurrentUser.getPhotoUrl()).into(circleImageView);
        }
    }

    private void updateDisplayName() {
        String name = nameUpdate.getText().toString().trim();
        if (name.isEmpty()) {
            nameUpdate.setError("Name is required");
            nameUpdate.requestFocus();
            return;
        }
        progressBar.setVisibility(View.VISIBLE);
        UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                .setDisplayName(name)
                .build();

        CurrentUser.updateProfile(profileUpdates)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        progressBar.setVisibility(View.GONE);
                        if (task.isSuccessful()) {
                            Toast.makeText(getApplicationContext(), "Updated", Toast.LENGTH_SHORT).show();
                            nameReupdate();
                        }
                        else{
                            Toast.makeText(getApplicationContext(), Objects.requireNonNull(task.getException()).getMessage() , Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
    private void nameReupdate(){
        name.setText(CurrentUser.getDisplayName());
    }
    private void photoReupdate(){
        Glide.with(ProfileCreateActivity.this).load(CurrentUser.getPhotoUrl()).into(circleImageView);
    }
    private void updateEmail() {
        String email = emailUpdate.getText().toString().trim();
        if (email.isEmpty()) {
            emailUpdate.setError("Email is required");
            emailUpdate.requestFocus();
            return;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            emailUpdate.setError("Please enter a valid email");
            emailUpdate.requestFocus();
            return;
        }


        progressBar.setVisibility(View.VISIBLE);
        CurrentUser.updateEmail(email)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        progressBar.setVisibility(View.GONE);
                        if (task.isSuccessful()) {
                            Toast.makeText(getApplicationContext(), "Updated", Toast.LENGTH_SHORT).show();
                            nameReupdate();
                        }
                        else{
                            Toast.makeText(getApplicationContext(), Objects.requireNonNull(task.getException()).getMessage() , Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
    private void updatePass() {

        String password = passwordUpdate.getText().toString().trim();
        if (password.isEmpty()) {
            passwordUpdate.setError("Password is required");
            passwordUpdate.requestFocus();
            return;
        }
        progressBar.setVisibility(View.VISIBLE);
        CurrentUser.updatePassword(password)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        progressBar.setVisibility(View.GONE);
                        if (task.isSuccessful()) {
                            Toast.makeText(getApplicationContext(), "Updated", Toast.LENGTH_SHORT).show();
                            nameReupdate();
                        }
                        else{
                            Toast.makeText(getApplicationContext(), Objects.requireNonNull(task.getException()).getMessage() , Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
    private void updatePic(){
        Intent opengalleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(opengalleryIntent,420);
    }
    @Override
    protected void onActivityResult(int requestCode,int resultCode,@androidx.annotation.Nullable  Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 420){
            if(resultCode == Activity.RESULT_OK){
                Uri imageuri = data.getData();
//                circleImageView.setImageURI(imageuri);
                progressBar.setVisibility(View.VISIBLE);
                UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                        .setPhotoUri(imageuri)
                        .build();

                CurrentUser.updateProfile(profileUpdates)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                progressBar.setVisibility(View.GONE);
                                if (task.isSuccessful()) {
                                    Toast.makeText(getApplicationContext(), "Updated", Toast.LENGTH_SHORT).show();
                                    photoReupdate();
                                }
                                else{
                                    Toast.makeText(getApplicationContext(), Objects.requireNonNull(task.getException()).getMessage() , Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.buttonUpdateProfile:
                updatePic();
                break;

            case R.id.buttonUpdateName:
                updateDisplayName();
                break;

            case R.id.buttonUpdateEmail:
                updateEmail();
                break;

            case R.id.buttonUpdatePassword:
                updatePass();
                break;
        }
    }
}
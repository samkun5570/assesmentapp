package com.example.assesmentapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextClock;

import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    FirebaseAuth mAuth;
    FirebaseUser CurrentUser;
    TextClock tClock;
    final List<String> areas = new ArrayList<>();
    private DatabaseReference mLocationDatabaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar actionBar = findViewById(R.id.toolbar_top);
        if (actionBar != null) {
            setSupportActionBar(actionBar);
            Drawable drawable = ContextCompat.getDrawable(getApplicationContext(),R.drawable.ic_baseline_menu_24);
            actionBar.setOverflowIcon(drawable);
            actionBar.getOverflowIcon().setTint(ContextCompat.getColor(this, R.color.colorPrimaryDark));
            Objects.requireNonNull(getSupportActionBar()).setTitle("");
        }
        FirebaseApp.initializeApp(this);
        FirebaseDatabase mFirebaseDatabase = FirebaseDatabase.getInstance();
        mAuth = FirebaseAuth.getInstance();
        CurrentUser = mAuth.getCurrentUser();
        mLocationDatabaseReference = mFirebaseDatabase.getReference().child(CurrentUser.getUid());

        findViewById(R.id.profile).setOnClickListener(this);
        tClock = findViewById(R.id.textClock1);
        findViewById(R.id.form).setOnClickListener(this);
        findViewById(R.id.logout).setOnClickListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.menu_logout:
                mAuth.signOut();
                finish();
                return true;
            case R.id.menu_login:
//                startActivity( new Intent(MainActivity.this, LoginActivity.class));
                logout();
                return true;
            case R.id.menu_profile:
                startActivity( new Intent(MainActivity.this, ProfileCreateActivity.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void logout() {
//        moveTaskToBack(true);
//        android.os.Process.killProcess(android.os.Process.myPid());
//        System.exit(1);
        mAuth.signOut();
        startActivity(new Intent(getApplicationContext(), LoginActivity.class));
        finish();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.profile:
                startActivity( new Intent(MainActivity.this, ProfileCreateActivity.class));
                break;
            case R.id.form:
                startActivity(new Intent(MainActivity.this, ProfileViewActivity.class));
                break;
            case R.id.logout:
                logout();
                break;
        }
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
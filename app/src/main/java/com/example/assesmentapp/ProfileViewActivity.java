package com.example.assesmentapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.firebase.ui.database.SnapshotParser;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

public class ProfileViewActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private FirebaseAuth mAuth;
    private FirebaseUser CurrentUser;
    private ProfileModel profileModel;
    private ProgressBar progressBar;
    private profileAdapter adapter;
    private FloatingActionButton floatingActionButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_view);
        mAuth = FirebaseAuth.getInstance();
        CurrentUser =mAuth.getCurrentUser() ;
        recyclerView = findViewById(R.id.recylerView);
        floatingActionButton = findViewById(R.id.floatingActionButton);
        progressBar=findViewById(R.id.progressBar2);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                finish();
                startActivity(new Intent(ProfileViewActivity.this, FormActivity.class));
            }
        });
        progressBar.setVisibility(View.VISIBLE);
        fetch();
    }

    private void fetch() {
        Query query = FirebaseDatabase.getInstance()
                .getReference()
                .child("users").child(CurrentUser.getUid());

        FirebaseRecyclerOptions<ProfileModel> options =
                new FirebaseRecyclerOptions.Builder<ProfileModel>()
                        .setQuery(query,ProfileModel.class)
                        .build();

       adapter = new profileAdapter(options,ProfileViewActivity.this);
       recyclerView.setAdapter(adapter);
    }
    public void disable(){
        progressBar.setVisibility(View.GONE);
    }
    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();
        if (mAuth.getCurrentUser() == null) {
            finish();
            startActivity(new Intent(this, LoginActivity.class));
        }
    }
    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();
    }
    @Override
    protected void onResume(){
        super.onResume();
        progressBar.setVisibility(View.VISIBLE);

    }
}
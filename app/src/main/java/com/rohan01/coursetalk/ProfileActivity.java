package com.rohan01.coursetalk;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.rohan01.coursetalk.databinding.ActivityProfileBinding;

import static android.content.ContentValues.TAG;

public class ProfileActivity extends AppCompatActivity {

    // view binding
    private ActivityProfileBinding binding;

    private FirebaseAuth firebaseAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityProfileBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        //init firebase auth
        firebaseAuth = FirebaseAuth.getInstance();
        checkUser();

        // handle click, logout
        binding.logoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                firebaseAuth.signOut();
                checkUser();
            }
        });
    }

    private void checkUser() {
        // get current user
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
        if(firebaseUser == null){
            // user not logged in
            startActivity(new Intent(this, MainActivity.class));
            finish();
        }
        else{
            //user logged in
            // get user info
            String email = firebaseUser.getEmail();
            // set email
            binding.emailTv.setText(email);

        }
    }
}
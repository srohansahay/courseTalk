package com.rohan01.coursetalk;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.rohan01.coursetalk.databinding.ActivityMainBinding;

import static android.util.Log.d;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;

    private static final int RC_SIGN_IN = 100;
    private GoogleSignInClient googleSignInClient;

    private FirebaseAuth firebaseAuth;

    private static final String TAG ="GOOGLE_SIGN_IN_TAG";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        //configure Google Signin
        GoogleSignInOptions googleSignInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        googleSignInClient = GoogleSignIn.getClient(this,googleSignInOptions);

        firebaseAuth = FirebaseAuth.getInstance();
        checkUser();

        binding.googleSignInBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                d(TAG, "onClick: Google SignIn begins");
                Intent intent = googleSignInClient.getSignInIntent();
                startActivityForResult(intent, RC_SIGN_IN);
            }
        });
    }

    private void checkUser() {
        // if user is already signed in then go to profile activity
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
        if(firebaseUser != null){
            Log.d(TAG, "onActivityResult: Already logged in");
            startActivity(new Intent(this, ProfileActivity.class));
            finish();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == RC_SIGN_IN){
            Log.d(TAG, "onActivtyResult: Google SignIn intent result");
            Task<GoogleSignInAccount> accountTask = GoogleSignIn.getSignedInAccountFromIntent(data);
            try{
                // google sign in action success, now firebase authentication
                GoogleSignInAccount account = accountTask.getResult(ApiException.class);
                firebaseAuthWithGoogleAccount(account);
            }
            catch (Exception e){
                // google sign in action failed
                Log.d(TAG, "onActityResult: "+e.getMessage());
            }
        }
    }

    private void firebaseAuthWithGoogleAccount(GoogleSignInAccount account) {
        Log.d(TAG, "firebaseAuthWithGoogleAccount: begin firebase auth with gmail account");
        AuthCredential credential = GoogleAuthProvider.getCredential(account.getIdToken(), null);
        firebaseAuth.signInWithCredential(credential)
                .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        // when login is success
                        d(TAG, "onSuccess: Logged In");

                        //get logged in user
                        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();

                        String allowedEmailDomain = "goa.bits-pilani.ac.in";

                        //get user details
                        String uid = firebaseUser.getUid();
                        String email01 = firebaseUser.getEmail();
                        String email;

                        if(email01.split("@")[1].equals(allowedEmailDomain))
                        {
                            email = email01;

                            Log.d(TAG, "onSuccess: Email: "+email);
                            Log.d(TAG,"onSuccess: UID:"+uid);

                            if(authResult.getAdditionalUserInfo().isNewUser())
                            {
                                Log.d(TAG, "onSucess: Account Created...\n" + email);
                                Toast.makeText(MainActivity.this, "Account Created...\n" + email, Toast.LENGTH_SHORT).show();
                            }
                            else {
                                // existing user = Logged In
                                Log.d(TAG, "onSuccess: Existing user...\n"+email);
                                Toast.makeText(MainActivity.this, "Existing User...\n" + email, Toast.LENGTH_SHORT).show();
                            }

                            // start profile activity
                            startActivity(new Intent(MainActivity.this, ProfileActivity.class));
                            finish();

                        }
                        else {
                          Toast.makeText(MainActivity.this, "Enter your BITS Gmail id", Toast.LENGTH_LONG).show();
                        }





                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        // when login is failed
                        Log.d(TAG, "onFailure: Login Failed"+e.getMessage());
                    }
                });
    }
}
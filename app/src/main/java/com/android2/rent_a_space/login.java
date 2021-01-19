package com.android2.rent_a_space;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class login extends AppCompatActivity {

    private Button ToReg;
    private Button LogIn;
    private TextInputLayout username, userpassword;
    private FirebaseAuth.AuthStateListener authStateListener;
    private FirebaseAuth fauth;
    private FirebaseFirestore fstore;
    private ProgressBar progressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getSupportActionBar().hide();


        fauth = FirebaseAuth.getInstance();
        fstore = FirebaseFirestore.getInstance();


        if (fauth.getCurrentUser() != null) {

            DocumentReference df = FirebaseFirestore.getInstance().collection("Users").document(FirebaseAuth.getInstance().getCurrentUser().getUid());
            df.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                @Override
                public void onSuccess(DocumentSnapshot documentSnapshot) {

                    if (documentSnapshot.getString("isAdmin") != null) {

                        startActivity((new Intent(getApplicationContext(), UserSpaceRentPage.class)));
                        finish();
                    } else if(documentSnapshot.getString("isUser") != null) {

                        startActivity((new Intent(getApplicationContext(), UserSpaceRentPage.class)));
                        finish();

                    }

                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    FirebaseAuth.getInstance().signOut();
                    startActivity(new Intent(getApplicationContext(), com.android2.rent_a_space.login.class));
                    finish();
                }
            });


        }

        progressBar = findViewById(R.id.progress_circular);
        username = findViewById(R.id.User_Name);
        userpassword = findViewById(R.id.User_Password);

        LogIn = findViewById(R.id.LogInButton);

        LogIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (validUserName() && validPassWord()) {
                    progressBar.setVisibility(View.VISIBLE);
                    fauth.signInWithEmailAndPassword(username.getEditText().getText().toString(), userpassword.getEditText().getText().toString()).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                        @Override
                        public void onSuccess(AuthResult authResult) {


                            Toast.makeText(com.android2.rent_a_space.login.this, "Log In Successful", Toast.LENGTH_LONG).show();
                            checkUserAccessLevel(authResult.getUser().getUid());
                            progressBar.setVisibility(View.GONE);
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {

                            Toast.makeText(com.android2.rent_a_space.login.this, "Log In Failed", Toast.LENGTH_LONG).show();
                            progressBar.setVisibility(View.GONE);
                        }
                    });


                }
            }
        });

        //Button to Registration Page
        ToReg = findViewById(R.id.register);
        ToReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(com.android2.rent_a_space.login.this, Registration.class));

            }
        });
    }

    //intent where log in or not
    private void checkUserAccessLevel(String uid) {
        DocumentReference df = fstore.collection("Users").document(uid);
        //extract data from the document
        df.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {

                Log.d("TAG", "onSuccess" + documentSnapshot.getData());


                if (documentSnapshot.getString("isUser") != null) {

                    startActivity(new Intent(getApplicationContext(), UserSpaceRentPage.class));
                    finish();
                } else if (documentSnapshot.getString("isAdmin") != null) {

                    startActivity(new Intent(getApplicationContext(), UserSpaceRentPage.class));
                    finish();

                }

            }
        });

    }

    public boolean validUserName() {
        String UName = username.getEditText().getText().toString();
        if (UName.isEmpty()) {
            username.setError("Field cannot be empty");
            return false;
        } else {
            username.setError(null);
            username.setErrorEnabled(false);
            return true;
        }

    }

    public boolean validPassWord() {

        String UserPWord = userpassword.getEditText().getText().toString();
        if (UserPWord.isEmpty()) {
            userpassword.setError("Field cannot be empty");
            return false;

        } else {

            userpassword.setError(null);
            userpassword.setErrorEnabled(false);
            return true;
        }

    }


}

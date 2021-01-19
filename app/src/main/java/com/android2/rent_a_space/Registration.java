package com.android2.rent_a_space;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class Registration extends AppCompatActivity {

    ProgressBar progressBar2;
    TextInputLayout fullName, email, password, phone;
    Button RegisterBtn;
    FirebaseAuth fauth;
    FirebaseFirestore fstore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        getSupportActionBar().hide();
        progressBar2 = findViewById(R.id.progress_circular2);


        fauth = FirebaseAuth.getInstance();
        fstore = FirebaseFirestore.getInstance();


        fullName = findViewById(R.id.Full_Name);
        email = findViewById(R.id.User_Email);
        password = findViewById(R.id.User_Reg_Password);
        phone = findViewById(R.id.User_Phone);


        RegisterBtn = findViewById(R.id.Reg_Button);


        RegisterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (validName() && validEmail() && validPassword() && validPhone()) {
                    progressBar2.setVisibility(View.VISIBLE);

                    //Start Registration Process
                    fauth.createUserWithEmailAndPassword(email.getEditText().getText().toString(), password.getEditText().getText().toString())
                            .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                        @Override
                        public void onSuccess(AuthResult authResult) {

                            FirebaseUser user = fauth.getCurrentUser();
                            DocumentReference df = fstore.collection("Users").document(user.getUid());


                            String FullName = fullName.getEditText().getText().toString();
                            String UserEmail = email.getEditText().getText().toString();
                            String UserPhone = phone.getEditText().getText().toString();

                            //Custom object na member tinawag dito para magamit
                            usersInfo usersInfo = new usersInfo();


                            usersInfo.setFullName(FullName);
                            usersInfo.setUserEmail(UserEmail);
                            usersInfo.setUserPhone(UserPhone);
                            usersInfo.setUserId(user.getUid());
                            usersInfo.setIsUser("1");
                            usersInfo.setUserImageUri(null);


                            df.set(usersInfo);
                            Toast.makeText(Registration.this, "Account Created", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(), UserSpaceRentPage.class));
                            finish();


                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {

                            Toast.makeText(Registration.this, "Failed to create account", Toast.LENGTH_SHORT).show();

                        }
                    });

                }

            }
        });


    }

    // button to login page

    public void clickToLoginPage(View view) {

        startActivity(new Intent(getApplicationContext(), login.class));

    }

    public boolean validName() {
        String FName = fullName.getEditText().getText().toString();
        if (FName.isEmpty()) {
            fullName.setError("Field cannot be empty");
            return false;
        } else {
            fullName.setError(null);
            fullName.setErrorEnabled(false);
            return true;
        }

    }

    public boolean validEmail() {
        String EMail = email.getEditText().getText().toString();
        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        if (EMail.isEmpty()) {
            email.setError("Field cannot be empty");
            return false;
        } else if (!EMail.matches(emailPattern)) {
            email.setError("Invalid email address!");
            return false;

        } else {
            email.setError(null);
            email.setErrorEnabled(false);
            return true;
        }


    }

    public boolean validPassword() {
        String PWord = password.getEditText().getText().toString();

        if (PWord.isEmpty()) {
            password.setError("Field cannot be empty");
            return false;
        } else if (PWord.length() < 6) {

            password.setError("Password too short ");
            return false;

        } else {
            password.setError(null);
            password.setErrorEnabled(false);
            return true;
        }

    }

    public boolean validPhone() {
        String Phone = phone.getEditText().getText().toString();
        if (Phone.isEmpty()) {
            phone.setError("Field cannot be empty");
            return false;
        } else {
            phone.setError(null);
            phone.setErrorEnabled(false);
            return true;
        }

    }
}

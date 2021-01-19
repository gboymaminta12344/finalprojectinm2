package com.android2.rent_a_space;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;

public class ProfileMenu extends Fragment {

    public View view;

    //Listener
    private static final String TAG = null;

    String CurrentLogin;

    String FName;

    //firebase
    FirebaseAuth fAuth;
    FirebaseUser currentUser;

    //fire Store
    FirebaseFirestore fStore = FirebaseFirestore.getInstance();
    DocumentReference usersRef;

    EditText update_user_FName;



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_profile_menu, container, false);


        //ini
        fAuth = FirebaseAuth.getInstance();
        currentUser = fAuth.getCurrentUser();
        //get
        CurrentLogin = currentUser.getEmail();


        //get intent

        usersRef = fStore.collection("Users").document(CurrentLogin);

        //ini

        update_user_FName = view.findViewById(R.id.Edit_Profile_Page_Name2_profile);

        Toast.makeText(getContext(), CurrentLogin, Toast.LENGTH_LONG).show();






        return view;
    }


}

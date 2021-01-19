package com.android2.rent_a_space;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.shapes.OvalShape;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.UUID;

import static android.app.Activity.RESULT_OK;

public class PostPropertyMenu extends Fragment {


    CardView BTN_Submit_Property_single;
    CardView BTN_Submit_Property_family;

    EditText searView_address;
    public View view;
    PropertyAdapter adapter;
    RecyclerView my_property_recycler_list_owner;

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    CollectionReference propertyRef = db.collection("Property");

    String CurrentLogin;

    //firebase
    FirebaseAuth fAuth;
    FirebaseUser currentUser;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_post_property_menu, container, false);


        //ini
        fAuth = FirebaseAuth.getInstance();
        currentUser = fAuth.getCurrentUser();
        //get
        CurrentLogin = currentUser.getUid();


        BTN_Submit_Property_single = view.findViewById(R.id.card_add_single);
        BTN_Submit_Property_single.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(getActivity(), PostYourPropertyScreening.class));


            }
        });


        BTN_Submit_Property_family = view.findViewById(R.id.card_add_family);
        BTN_Submit_Property_family.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(getContext(), "this module is under construction", Toast.LENGTH_LONG).show();


            }
        });

        property_List_Recyclerview_Owner();


        return view;
    }

    public void property_List_Recyclerview_Owner() {

        Query query = propertyRef.whereEqualTo("user_id",CurrentLogin);
        FirestoreRecyclerOptions<Property> options = new FirestoreRecyclerOptions.Builder<Property>()
                .setQuery(query, Property.class)
                .build();


        adapter = new PropertyAdapter(options);
        my_property_recycler_list_owner = view.findViewById(R.id.my_property_listahan);
        my_property_recycler_list_owner.setHasFixedSize(true);
        my_property_recycler_list_owner.setLayoutManager(new LinearLayoutManager(getContext()));
        my_property_recycler_list_owner.setAdapter(adapter);


    }

    @Override
    public void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        adapter.stopListening();
    }
}



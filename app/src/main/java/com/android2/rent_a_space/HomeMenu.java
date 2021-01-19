package com.android2.rent_a_space;

import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

public class HomeMenu<view> extends Fragment {
    private static final String TAG = "Data";

    EditText searView;
    public View view;
    PropertyAdapter adapter;
    RecyclerView my_property_recycler_list;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    CollectionReference propertyRef = db.collection("Property");


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_home_menu, container, false);


        //Recycler view in fragment;
        property_List_Recyclerview();


        //search view
        searchProperty();



        return view;
    }


    @Override
    public void onStart() {
        super.onStart();
        adapter.startListening();


    }

    @Override
    public void onStop() {
        super.onStop();
        if (adapter != null) {
            adapter.stopListening();
        }

    }

    public void property_List_Recyclerview() {


        Query query = propertyRef.whereEqualTo("property_status", "available");
        FirestoreRecyclerOptions<Property> options = new FirestoreRecyclerOptions.Builder<Property>()
                .setQuery(query, Property.class)
                .build();


        adapter = new PropertyAdapter(options);
        my_property_recycler_list = view.findViewById(R.id.property_recycler_list);
        my_property_recycler_list.setHasFixedSize(true);
        my_property_recycler_list.setLayoutManager(new LinearLayoutManager(getContext()));
        my_property_recycler_list.setAdapter(adapter);


    }

    public void searchProperty(){
        //Search
        searView = view.findViewById(R.id.search_bar);
        searView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                Query query;

                Log.d(TAG, "Search box has changed to: " + s.toString());
                if (s.toString().isEmpty()) {

                    query = propertyRef.orderBy("timestamp", Query.Direction.DESCENDING);
                    FirestoreRecyclerOptions<Property> options = new FirestoreRecyclerOptions.Builder<Property>()
                            .setQuery(query, Property.class)
                            .build();
                    adapter.updateOptions(options);


                } else {

                    query = propertyRef.whereEqualTo("propertyADDRESSSCITY",s.toString()).orderBy("timestamp", Query.Direction.DESCENDING);
                    FirestoreRecyclerOptions<Property> options = new FirestoreRecyclerOptions.Builder<Property>()
                            .setQuery(query, Property.class)
                            .build();

                    adapter.updateOptions(options);

                }


            }
        });



    }
}







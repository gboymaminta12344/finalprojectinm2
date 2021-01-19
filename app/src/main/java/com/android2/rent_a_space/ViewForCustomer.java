package com.android2.rent_a_space;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.squareup.picasso.Picasso;

public class ViewForCustomer extends AppCompatActivity {

    //firebase
    FirebaseAuth fAuth;
    FirebaseUser currentUser;

    //Listener
    private static final String TAG = "Property Details";

    //variables to fetch the current data
    String CurrentLogin;
    String CompareIDtoCurrentLogin;
    private String newId;
    private String imageUri;

    private String propertyADDRESSHOUSENUMBER;
    private String propertyADDRESSSTREET;
    private String propertyADDRESSSBRGY;
    private String propertyADDRESSSCITY;
    private String propertyADDRESSSAREA;
    private String propertyLANDMARK;
    private String propertyOWNER;
    private String propertyOWNERMOBILE;
    private String propertyOWNERFB;
    private String propertyGENDERACCEPT;
    private String propertyPRICE;
    private String propertyADVANCE;
    private String propertyDEPOSIT;
    private String propertyADVANCEDEPOSIT;
    private String per;


    FirebaseFirestore fStore = FirebaseFirestore.getInstance();
    DocumentReference propertyRef;
    CollectionReference listOfProperty;

    //fire storage
    FirebaseStorage storage;
    StorageReference storageReference;


    //ini
    ImageView fetch_property_image2;
    TextView fetch_Location_Address2, fetch_Location_LandMark2, fetch_location_owner_name;
    TextView fetch_location_owner_contact, fetch_location_owner_fb, fetch_location_availability;
    TextView fetch_property_payment, fetch_property_advance_deposit;
    Button BTN_to_Update;


    //Calling Property Class
    Property property = new Property();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_for_customer);


        //get intent
        newId = (getIntent().getStringExtra("id") + "");
        propertyRef = fStore.collection("Property").document(newId);
        listOfProperty = fStore.collection("Property");


        //ini
        fAuth = FirebaseAuth.getInstance();
        currentUser = fAuth.getCurrentUser();


        //get
        CurrentLogin = currentUser.getUid();


        //ini

        fetch_property_image2 = findViewById(R.id.house_image_view);
        fetch_Location_Address2 = findViewById(R.id.textView_property_Address);
        fetch_Location_LandMark2 = findViewById(R.id.textView_property_LandMark);
        fetch_location_owner_name = findViewById(R.id.textView_property_owner_name);
        fetch_location_owner_contact = findViewById(R.id.textView_property_owner_contact);
        fetch_location_owner_fb = findViewById(R.id.textView_property_owner_fb);
        fetch_location_availability = findViewById(R.id.textV_property_availability);
        fetch_property_payment = findViewById(R.id.textView_property_payment);
        fetch_property_advance_deposit = findViewById(R.id.textView_property_advance_deposit);

        //check current log in
        //check if the user is the owner of the property if true enable the button to update


        //button to update
        BTN_to_Update = findViewById(R.id.BTN_to_update);
        BTN_to_Update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String id = newId;
                Intent intent = new Intent(v.getContext(), ViewPropertyDetails.class);
                intent.putExtra("id", id);
                v.getContext().startActivity(intent);

            }
        });

        //firebase storage open this open this comment
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference("Property/" + currentUser.getEmail());


    }

    //i put on start  to

    @Override
    protected void onStart() {
        super.onStart();

        //image and display it on on create view
        displayImageOnCreate();
        //call data display on onCreate
        displayDataOnStart();

    }

    //display image

    private void displayImageOnCreate() {

        propertyRef.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {

                if (error != null) {
                    Toast.makeText(ViewForCustomer.this, "Error while loading!", Toast.LENGTH_LONG).show();
                    Log.d(TAG, error.toString());
                    return;
                }
                if (value.exists()) {

                    imageUri = value.getString("property_ImageUri");
                    Picasso.get().load(imageUri).into(fetch_property_image2);


                }


            }
        });


    }
    //display data

    private void displayDataOnStart() {

        // method to load property info//activity:this automatic end the listening if close
        propertyRef.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {

                if (error != null) {
                    Toast.makeText(ViewForCustomer.this, "Error while loading!", Toast.LENGTH_LONG).show();
                    Log.d(TAG, error.toString());
                    return;
                }
                if (value.exists()) {


                    propertyADDRESSHOUSENUMBER = value.getString("propertyADDRESSHOUSENUMBER");
                    propertyADDRESSSTREET = value.getString("propertyADDRESSSTREET");
                    propertyADDRESSSBRGY = value.getString("propertyADDRESSSBRGY");
                    propertyADDRESSSCITY = value.getString("propertyADDRESSSCITY");
                    propertyADDRESSSAREA = value.getString("propertyADDRESSSAREA");
                    propertyADDRESSSAREA = value.getString("propertyADDRESSSAREA");
                    propertyLANDMARK = value.getString("propertyLANDMARK");
                    CompareIDtoCurrentLogin = value.getString("user_id");

                    propertyOWNER = value.getString("propertyOWNER");
                    propertyOWNERMOBILE = value.getString("propertyOWNERMOBILE");
                    propertyOWNERFB = value.getString("propertyOWNERFB");
                    propertyGENDERACCEPT = value.getString("propertyGENDERACCEPT");
                    propertyPRICE = value.getString("propertyPRICE");
                    per = value.getString("per");

                    propertyADVANCE = value.getString("propertyADVANCE");
                    propertyDEPOSIT = value.getString("propertyDEPOSIT");
                    propertyADVANCEDEPOSIT = propertyADVANCE + " Advance " + propertyDEPOSIT + " Deposit ";


                    //Display location address
                    fetch_Location_Address2.setText(propertyADDRESSHOUSENUMBER + " " + propertyADDRESSSTREET + " " + propertyADDRESSSBRGY
                            + " " + propertyADDRESSSCITY + " " + propertyADDRESSSAREA);

                    //Display LandMark
                    fetch_Location_LandMark2.setText(propertyLANDMARK);
                    //display owner name
                    fetch_location_owner_name.setText(propertyOWNER);
                    //display owner contact
                    fetch_location_owner_contact.setText(propertyOWNERMOBILE);
                    fetch_location_owner_fb.setText(propertyOWNERFB);

                    //availability
                    fetch_location_availability.setText("This space is available for " + propertyGENDERACCEPT);
                    //payment
                    fetch_property_payment.setText(propertyPRICE + " / " + per);
                    fetch_property_advance_deposit.setText(propertyADVANCEDEPOSIT);


                    //check if the user is the owner of the property if true enable the button to update
                    if (!CurrentLogin.equals(CompareIDtoCurrentLogin)) {
                        BTN_to_Update.setVisibility(View.GONE);


                    }


                }
            }
        });


    }

}
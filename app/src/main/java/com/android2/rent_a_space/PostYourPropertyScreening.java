package com.android2.rent_a_space;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.net.URI;
import java.util.UUID;

public class PostYourPropertyScreening extends AppCompatActivity implements AdapterView.OnItemSelectedListener {


    //firebase
    FirebaseAuth fAuth;
    FirebaseUser currentUser;
    FirebaseFirestore fStore = FirebaseFirestore.getInstance();

    private TextInputLayout input_propertyAddressHouseNUmber;
    private TextInputLayout input_propertyAddressHouseStreet;
    private TextInputLayout input_propertyAddressHouseBRGY;
    private TextInputLayout input_propertyAddressHouseCity;
    private TextInputLayout input_propertyAddressHouseArea;
    private TextInputLayout input_propertyLandMark;
    //payment input
    private TextInputLayout input_property_price_per;
    private TextInputLayout input_advance_pay, input_deposit_pay;

    //global
    String allowed;
    String per;

    //radio button option
    RadioGroup radioGroup1;
    RadioButton set_default;

    //Spinner
    Spinner price_spinner;

    //owner details entry
    private TextInputLayout input_Name_owner_property, input_Contact_owner_property,
            input_Fb_owner_property;

    //post property
    private Button BTN_post_property;
    //upload image of property


    //firebase key
    DocumentReference propertyRef;
    CollectionReference listOfProperty;

    // CollectionReference houseInfo;
    // DocumentReference listofHouse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_your_property_screening);
        getSupportActionBar().hide();


        //fire Store initialization
        fAuth = FirebaseAuth.getInstance();
        currentUser = fAuth.getCurrentUser();

        listOfProperty = fStore.collection("Property");
        propertyRef = fStore.collection("Property").document();

        //Image gallery Collection
        //houseInfo = fStore.collection("Image Gallery Of Property");
        //listofHouse = fStore.collection("Image Gallery Of Property").document();

        //default value
        allowed = "Both Gender";

        //house number
        input_propertyAddressHouseNUmber = findViewById(R.id.input_property_location1);
        input_propertyAddressHouseStreet = findViewById(R.id.input_property_location2);
        input_propertyAddressHouseBRGY = findViewById(R.id.input_property_location3);
        input_propertyAddressHouseCity = findViewById(R.id.input_property_location4);
        input_propertyAddressHouseArea = findViewById(R.id.input_property_location5);
        input_propertyLandMark = findViewById(R.id.input_property_location_landmark);

        //payment input
        input_property_price_per = findViewById(R.id.input_property_price);
        input_advance_pay = findViewById(R.id.input_advance);
        input_deposit_pay = findViewById(R.id.input_deposit);

        //owner details
        input_Name_owner_property = findViewById(R.id.input_property_owner_name);
        input_Contact_owner_property = findViewById(R.id.input_property_owner_mobile);
        input_Fb_owner_property = findViewById(R.id.input_property_owner_facebook);

        //Spinner

        price_spinner = findViewById(R.id.price_property_spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.price, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        price_spinner.setAdapter(adapter);
        price_spinner.setOnItemSelectedListener(this);

        //radio group choose gender
        radioGroup1 = findViewById(R.id.property_allowed);
        radioGroup1.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {



            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                switch (checkedId) {


                    case R.id.girl_only:
                        allowed = "Girl Only";
                        Toast.makeText(PostYourPropertyScreening.this, allowed, Toast.LENGTH_LONG).show();
                        break;


                    case R.id.boy_only:
                        allowed = "Boy Only";
                        Toast.makeText(PostYourPropertyScreening.this, allowed, Toast.LENGTH_LONG).show();
                        break;

                    case R.id.both_gender:

                        allowed = "Both Gender";
                        Toast.makeText(PostYourPropertyScreening.this, allowed, Toast.LENGTH_LONG).show();
                        break;


                }

            }
        });


        //Button to upload the actual details for property entry
        BTN_post_property = findViewById(R.id.Upload_Property);
        BTN_post_property.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InfoOfProperties();

            }


        });


    }


    private void InfoOfProperties() {
        //insert to FireStore
        String propertyADDRESSHOUSENUMBER = input_propertyAddressHouseNUmber.getEditText().getText().toString();
        String propertyADDRESSSTREET = input_propertyAddressHouseStreet.getEditText().getText().toString();
        String propertyADDRESSSBRGY = input_propertyAddressHouseBRGY.getEditText().getText().toString();
        String propertyADDRESSSCITY = input_propertyAddressHouseCity.getEditText().getText().toString();
        String propertyLANDMARK = input_propertyLandMark.getEditText().getText().toString();
        String propertyADDRESSSAREA = input_propertyAddressHouseArea.getEditText().getText().toString();
        //owner Details
        String propertyOWNERNAME = input_Name_owner_property.getEditText().getText().toString();
        String propertyCONTACMOBILE = input_Contact_owner_property.getEditText().getText().toString();
        String propertyOWNERFB = input_Fb_owner_property.getEditText().getText().toString();

        String propertyPRICE = input_property_price_per.getEditText().getText().toString();
        String advance_pay = input_advance_pay.getEditText().getText().toString();
        String deposit_pay = input_deposit_pay.getEditText().getText().toString();



        Property property = new Property();

        //set property location details
        property.setPropertyADDRESSHOUSENUMBER(propertyADDRESSHOUSENUMBER);
        property.setPropertyADDRESSSTREET(propertyADDRESSSTREET);
        property.setPropertyADDRESSSBRGY(propertyADDRESSSBRGY);
        property.setPropertyADDRESSSCITY(propertyADDRESSSCITY);
        property.setPropertyADDRESSSAREA(propertyADDRESSSAREA);
        property.setPropertyLANDMARK(propertyLANDMARK);
        property.setPropertyGENDERACCEPT(allowed);
        property.setPropertyPRICE(propertyPRICE);
        property.setPer(per);
        property.setPropertyADVANCE(advance_pay);
        property.setPropertyDEPOSIT(deposit_pay);


        //set property owner name and contact info
        property.setPropertyOWNER(propertyOWNERNAME);
        property.setPropertyOWNERMOBILE(propertyCONTACMOBILE);
        property.setPropertyOWNERFB(propertyOWNERFB);
        //property id
        property.setProperty_id(propertyRef.getId());
        property.setUser_id(currentUser.getUid());
        //property image uri
        //no image for the mean time

        //set property status
        property.setProperty_status("available");


        //progress bar
        final ProgressDialog pd2 = new ProgressDialog(this);
        pd2.setTitle("Uploading Data");
        pd2.show();

        //add to database custom object
        listOfProperty.add(property).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
            @Override
            public void onComplete(@NonNull Task<DocumentReference> task) {


                Snackbar.make(findViewById(android.R.id.content), "Uploaded",Snackbar.LENGTH_LONG).show();
                BTN_post_property.setEnabled(true);
                pd2.dismiss();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

                Snackbar.make(findViewById(android.R.id.content), "Failed to upload" , Snackbar.LENGTH_LONG).show();
                BTN_post_property.setEnabled(true);
                pd2.dismiss();

            }
        });


    }

    // spinner choose from dropdown
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        per = parent.getItemAtPosition(position).toString();

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}



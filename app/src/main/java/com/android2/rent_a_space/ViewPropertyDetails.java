package com.android2.rent_a_space;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.Toast;

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
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.ListenerRegistration;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.SetOptions;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.security.Key;
import java.util.UUID;

public class ViewPropertyDetails extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    //firebase
    FirebaseAuth fAuth;
    FirebaseUser currentUser;

    //Listener
    private static final String TAG = "Property Details";

    //variables to fetch the current data
    String newId;

    //global variables
    String propertyADDRESSHOUSENUMBER_update;
    String propertyADDRESSSTREET_update;
    String propertyADDRESSSBRGY_update;
    String propertyADDRESSSCITY_update;
    String propertyLANDMARK_update;
    String propertyADDRESSSAREA_update;
    String propertyOWNER_update;
    String propertyOWNERMOBILE_update;
    String propertyOWNERFB_update;

    //payment
    String propertyPRICE_update;
    String advance_pay_update;
    String deposit_pay_update;


    //accept variables
    String propertyGENDERACCEPT_update;

    //global for image update
    String downloadUrl;
    String imageUri;

    FirebaseFirestore fStore = FirebaseFirestore.getInstance();
    DocumentReference propertyRef;
    CollectionReference listOfProperty;

    //fire storage
    FirebaseStorage storage;
    StorageReference storageReference;
    StorageTask uploadImageTask;

    //ini image
    ImageView fetch_property_image;
    Uri updated_ImageUri;

    //ini inputs for location details update
    private TextInputLayout input_propertyAddressHouseNUmber_update;
    private TextInputLayout input_propertyAddressHouseStreet_update;
    private TextInputLayout input_propertyAddressHouseBRGY_update;
    private TextInputLayout input_propertyAddressHouseCity_update;
    private TextInputLayout input_propertyAddressHouseArea_update;
    private TextInputLayout input_propertyLandMark_update;

    //payment input
    private TextInputLayout input_property_price_per_update;
    private TextInputLayout input_advance_pay_update, input_deposit_pay_update;

    //owner details entry
    private TextInputLayout input_Name_owner_property_update, input_Contact_owner_property_update,
            input_Fb_owner_property_update;


    //button
    Button BTN_update_Property;
    Button BTN_update_image;

    //global radio button list
    String allowed_update;
    String property_availability;


    //Spinner
    Spinner price_spinner_update;
    String per_update;

    //radio button option
    RadioGroup radioGroup1_update;

    //radio property status
    RadioGroup radioGroupStatus_update;


    //Calling Property Class
    Property property = new Property();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_property_details);
        getSupportActionBar().hide();


        //get intent
        newId = (getIntent().getStringExtra("id") + "");
        propertyRef = fStore.collection("Property").document(newId);
        listOfProperty = fStore.collection("Property");

        //ini
        fAuth = FirebaseAuth.getInstance();
        currentUser = fAuth.getCurrentUser();

        //ini
        fetch_property_image = findViewById(R.id.house_image_holder);
        input_propertyAddressHouseNUmber_update = findViewById(R.id.input_property_location1_update);
        input_propertyAddressHouseStreet_update = findViewById(R.id.input_property_location2_update);
        input_propertyAddressHouseBRGY_update = findViewById(R.id.input_property_location3_update);
        input_propertyAddressHouseCity_update = findViewById(R.id.input_property_location4_update);
        input_propertyAddressHouseArea_update = findViewById(R.id.input_property_location5_update);
        input_propertyLandMark_update = findViewById(R.id.input_property_location_landmark_update);
        //ini owner details
        input_Name_owner_property_update = findViewById(R.id.input_property_owner_name_update);
        input_Contact_owner_property_update = findViewById(R.id.input_property_owner_mobile_update);
        input_Fb_owner_property_update = findViewById(R.id.input_property_owner_facebook_update);
        //input property payment
        input_advance_pay_update = findViewById(R.id.input_advance_update);
        input_deposit_pay_update = findViewById(R.id.input_deposit_update);
        input_property_price_per_update = findViewById(R.id.input_property_price_update);


        //firebase storage open this open this comment
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference("Property/" + currentUser.getEmail());



        //update image and display it on on create view
        displayImageOnCreate();


        //Spinner
        price_spinner_update = findViewById(R.id.price_property_spinner_update);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.price, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        price_spinner_update.setAdapter(adapter);
        price_spinner_update.setOnItemSelectedListener(this);


        //update gender accept
        radioGroup1_update = findViewById(R.id.property_allowed_update);
        radioGroup1_update.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                switch (checkedId) {

                    case R.id.girl_only_update:
                        allowed_update = "Girl Only";
                        break;


                    case R.id.boy_only_update:
                        allowed_update = "Boy Only";
                        break;

                    case R.id.both_gender_update:
                        allowed_update = "Both Gender";
                        break;


                }
            }
        });

        //property status
        radioGroupStatus_update = findViewById(R.id.my_property_status);
        radioGroupStatus_update.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                switch (checkedId) {

                    case R.id.available:
                        property_availability = "available";
                        break;


                    case R.id.not_available:
                        property_availability = "not available";
                        break;

                }
            }
        });


        //update the image

        BTN_update_image = findViewById(R.id.BTN_select_PhotoUpdate);
        BTN_update_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (uploadImageTask != null && uploadImageTask.isInProgress()) {

                    Snackbar.make(findViewById(android.R.id.content), "In Progress", Snackbar.LENGTH_LONG).show();

                } else {
                    //update image
                    updateImage();

                }


            }
        });


        //update the property
        BTN_update_Property = findViewById(R.id.BTN_update);
        BTN_update_Property.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //call the update data method

                // no progress bar
                upDateData();


            }
        });


        //click image
        fetch_property_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                choosePicture();

            }
        });


    }


    //i put on start  to
    @Override
    protected void onStart() {
        super.onStart();

        // method to load property info//activity:this automatic end the listening if close
        propertyRef.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {

                if (error != null) {
                    Toast.makeText(ViewPropertyDetails.this, "Error while loading!", Toast.LENGTH_LONG).show();
                    Log.d(TAG, error.toString());
                    return;
                }
                if (value.exists()) {

                    imageUri = value.getString("property_ImageUri");
                    propertyADDRESSHOUSENUMBER_update = value.getString("propertyADDRESSHOUSENUMBER");
                    propertyADDRESSSTREET_update = value.getString("propertyADDRESSSTREET");
                    propertyADDRESSSBRGY_update = value.getString("propertyADDRESSSBRGY");
                    propertyADDRESSSCITY_update = value.getString("propertyADDRESSSCITY");
                    propertyADDRESSSAREA_update = value.getString("propertyADDRESSSAREA");
                    propertyLANDMARK_update = value.getString("propertyLANDMARK");
                    propertyOWNER_update = value.getString("propertyOWNER");
                    propertyOWNERMOBILE_update = value.getString("propertyOWNERMOBILE");
                    propertyOWNERFB_update = value.getString("propertyOWNERFB");
                    propertyGENDERACCEPT_update = value.getString("propertyGENDERACCEPT");
                    propertyPRICE_update = value.getString("propertyPRICE");
                    property_availability = value.getString("property_status");



                    //payment fetch

                    advance_pay_update = value.getString("propertyADVANCE");
                    deposit_pay_update = value.getString("propertyDEPOSIT");


                    //input if the user want to update
                    input_propertyAddressHouseNUmber_update.getEditText().setText(propertyADDRESSHOUSENUMBER_update);
                    input_propertyAddressHouseStreet_update.getEditText().setText(propertyADDRESSSTREET_update);
                    input_propertyAddressHouseBRGY_update.getEditText().setText(propertyADDRESSSBRGY_update);
                    input_propertyAddressHouseCity_update.getEditText().setText(propertyADDRESSSCITY_update);
                    input_propertyAddressHouseArea_update.getEditText().setText(propertyADDRESSSAREA_update);
                    input_propertyLandMark_update.getEditText().setText(propertyLANDMARK_update);

                    input_Name_owner_property_update.getEditText().setText(propertyOWNER_update);
                    input_Contact_owner_property_update.getEditText().setText(propertyOWNERMOBILE_update);
                    input_Fb_owner_property_update.getEditText().setText(propertyOWNERFB_update);

                    input_advance_pay_update.getEditText().setText(advance_pay_update);
                    input_deposit_pay_update.getEditText().setText(deposit_pay_update);
                    input_property_price_per_update.getEditText().setText(propertyPRICE_update);


                    if (propertyGENDERACCEPT_update.equals("Girl Only")) {

                        RadioButton rb1 = findViewById(R.id.girl_only_update);
                        rb1.setChecked(true);

                    } else if (propertyGENDERACCEPT_update.equals("Boy Only")) {

                        RadioButton rb2 = findViewById(R.id.boy_only_update);
                        rb2.setChecked(true);

                    } else {

                        RadioButton rb3 = findViewById(R.id.both_gender_update);
                        rb3.setChecked(true);


                    }

                    //property status

                    if(property_availability.equals("available")){

                        RadioButton stat_available = findViewById(R.id.available);
                        stat_available.setChecked(true);


                    } else if (property_availability.equals("not available")){

                        RadioButton stat_not_available = findViewById(R.id.not_available);
                        stat_not_available.setChecked(true);

                    }

                    //lalabas ito pag pinindot ung update sasabihin niya status ng member
                    Toast.makeText(ViewPropertyDetails.this, "This Property is:   " + property_availability, Toast.LENGTH_LONG).show();
                    // end ng if else condition
                }
            }
        });


    }

    //image insert to data storage
    private String getFileExtension(Uri uri) {
        ContentResolver cR = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cR.getType(uri));
    }


    //update data
    private void upDateData() {


        String propertyADDRESSHOUSENUMBER = input_propertyAddressHouseNUmber_update.getEditText().getText().toString();
        String propertyADDRESSSTREET = input_propertyAddressHouseStreet_update.getEditText().getText().toString();
        String propertyADDRESSSBRGY = input_propertyAddressHouseBRGY_update.getEditText().getText().toString();
        String propertyADDRESSSCITY = input_propertyAddressHouseCity_update.getEditText().getText().toString();
        String propertyADDRESSSAREA = input_propertyAddressHouseArea_update.getEditText().getText().toString();
        String propertyLANDMARK = input_propertyLandMark_update.getEditText().getText().toString();
        String propertyOWNER = input_Name_owner_property_update.getEditText().getText().toString();
        String propertyOWNERMOBILE = input_Contact_owner_property_update.getEditText().getText().toString();
        String propertyOWNERFB = input_Fb_owner_property_update.getEditText().getText().toString();
        String propertyADVANCE = input_advance_pay_update.getEditText().getText().toString();
        String propertyDEPOSIT = input_deposit_pay_update.getEditText().getText().toString();
        String propertyPRICE = input_property_price_per_update.getEditText().getText().toString();


        Property property = new Property();
        property.setPropertyADDRESSHOUSENUMBER(propertyADDRESSHOUSENUMBER);
        property.setPropertyADDRESSSTREET(propertyADDRESSSTREET);
        property.setPropertyADDRESSSBRGY(propertyADDRESSSBRGY);
        property.setPropertyADDRESSSCITY(propertyADDRESSSCITY);
        property.setPropertyADDRESSSAREA(propertyADDRESSSAREA);
        property.setPropertyLANDMARK(propertyLANDMARK);

        //payment update
        property.setPropertyADVANCE(propertyADVANCE);
        property.setPropertyDEPOSIT(propertyDEPOSIT);
        property.setPropertyPRICE(propertyPRICE);
        property.setPer(per_update);


        //update owner details
        property.setPropertyOWNER(propertyOWNER);
        property.setPropertyOWNERMOBILE(propertyOWNERMOBILE);
        property.setPropertyOWNERFB(propertyOWNERFB);

        //gender accept
        property.setPropertyGENDERACCEPT(allowed_update);

        //property status update
        property.setProperty_status(property_availability);

        //update query
        propertyRef.set(property, SetOptions.mergeFields("propertyADDRESSHOUSENUMBER", "propertyADDRESSSTREET", "propertyADDRESSSBRGY"
                , "propertyADDRESSSCITY", "propertyADDRESSSAREA", "propertyLANDMARK", "propertyOWNER"
                , "propertyADVANCE", "propertyDEPOSIT", "propertyPRICE", "per"
                , "propertyOWNERMOBILE", "propertyOWNERFB", "propertyGENDERACCEPT","property_status"));
        Snackbar.make(findViewById(android.R.id.content), "UpDated", Snackbar.LENGTH_LONG).show();


    }

    //still not finish
    private void choosePicture() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, 1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1 && resultCode == RESULT_OK && data != null && data.getData() != null) {
            updated_ImageUri = data.getData();
            Picasso.get().load(updated_ImageUri).into(fetch_property_image);

            //enable the button if intent is trigger
            BTN_update_image.setEnabled(true);
        }


    }

    //update the image

    private void updateImage() {
        //progress bar
        final ProgressDialog pd = new ProgressDialog(this);
        pd.setTitle("Updating Image");
        pd.show();

        //start
        if (updated_ImageUri != null) {

            //Data to Storage
            StorageReference fileReference = storageReference.child("Property Images/" + UUID.randomUUID().toString() + "." + getFileExtension(updated_ImageUri));
            uploadImageTask = fileReference.putFile(updated_ImageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {


                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                    fileReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {

                            //dismiss progress dialog if updated
                            pd.dismiss();

                            downloadUrl = uri.toString();
                            Property property = new Property();
                            property.setProperty_ImageUri(downloadUrl);
                            propertyRef.set(property, SetOptions.mergeFields("property_ImageUri"));

                            Snackbar.make(findViewById(android.R.id.content), "Uploaded", Snackbar.LENGTH_LONG).show();
                            BTN_update_image.setEnabled(false);
                            BTN_update_Property.setEnabled(true);
                        }
                    });

                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {

                    pd.dismiss();
                    Toast.makeText(getApplicationContext(), "Unable to Update", Toast.LENGTH_LONG).show();
                    BTN_update_Property.setEnabled(true);

                }
            }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {


                    double progressPercent = (100.00 * snapshot.getBytesTransferred() / snapshot.getTotalByteCount());
                    pd.setMessage("Percentage: " + (int) progressPercent + "%");
                    BTN_update_image.setEnabled(false);
                    BTN_update_Property.setEnabled(false);

                }
            });

        }
    }


    private void displayImageOnCreate() {

        propertyRef.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {

                if (error != null) {
                    Toast.makeText(ViewPropertyDetails.this, "Error while loading!", Toast.LENGTH_LONG).show();
                    Log.d(TAG, error.toString());
                    return;
                }
                if (value.exists()) {

                    imageUri = value.getString("property_ImageUri");
                    Picasso.get().load(imageUri).into(fetch_property_image);


                }


            }
        });


    }

    // spinner choose from dropdown

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        per_update = parent.getItemAtPosition(position).toString();

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}

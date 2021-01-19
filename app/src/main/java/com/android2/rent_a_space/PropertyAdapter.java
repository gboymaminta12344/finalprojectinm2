package com.android2.rent_a_space;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import java.util.UUID;


public class PropertyAdapter extends FirestoreRecyclerAdapter<Property, PropertyAdapter.PropertyHolder> {
    private Context context;

    //firebase Ini
    DocumentReference df;
    FirebaseStorage storage = FirebaseStorage.getInstance();


    public PropertyAdapter(@NonNull FirestoreRecyclerOptions<Property> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull PropertyHolder holder, int position, @NonNull Property model) {


        holder.editPaddress.setText(model.getPropertyADDRESSHOUSENUMBER() + ", " +
                model.getPropertyADDRESSSTREET() + " " +
                model.getPropertyADDRESSSBRGY() + " " +
                model.getPropertyADDRESSSCITY() + " " + " " + model.getPropertyADDRESSSAREA());
        holder.editPlandmark.setText(model.getPropertyLANDMARK());
        Picasso.get().load(model.getProperty_ImageUri()).into(holder.MyfetchImage);


        // this will find the user if admin or just a user

        df = FirebaseFirestore.getInstance().collection("Users").document(FirebaseAuth.getInstance().getCurrentUser().getUid());
        df.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {


                if (documentSnapshot.getString("isUser") != null) {

                    holder.holder_BTN_Delete.setVisibility(View.GONE);

                }

            }
        });

        // this will find the user if admin or just a user if user only do not display delete button

        holder.holder_BTN_Delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // call the delete
                delItem(position);
                //Delete the picture in Fire Storage
                StorageReference storageReference = storage.getReferenceFromUrl(model.getProperty_ImageUri());
                storageReference.delete();
                notifyDataSetChanged();
            }
        });

        //to full details

        holder.holder_BTN_details.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //giving the id of the property and view in much detailed
                String id = getSnapshots().getSnapshot(position).getId();
                Intent intent = new Intent(v.getContext(), ViewForCustomer.class);
                intent.putExtra("id", id);
                v.getContext().startActivity(intent);


            }
        });
    }

    @NonNull
    @Override
    public PropertyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {


        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.property_item, parent, false);
        context = parent.getContext();
        return new PropertyHolder(view);
    }

    //Delete Data in Database
    public void delItem(int position) {

        getSnapshots().getSnapshot(position).getReference().delete();


    }

    class PropertyHolder extends RecyclerView.ViewHolder {


        TextView editPaddress;
        TextView editPlandmark;
        ImageView MyfetchImage;
        Button holder_BTN_Delete;
        Button holder_BTN_details;


        public PropertyHolder(@NonNull View itemView) {
            super(itemView);

            editPaddress = itemView.findViewById(R.id.card_display_PADD);
            editPlandmark = itemView.findViewById(R.id.card_display_PLand);
            MyfetchImage = itemView.findViewById(R.id.fetch_property_image);
            holder_BTN_Delete = itemView.findViewById(R.id.BTN_del_property);
            holder_BTN_details = itemView.findViewById(R.id.BTN_full_details);


        }
    }
}

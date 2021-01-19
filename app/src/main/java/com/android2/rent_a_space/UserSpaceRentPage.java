package com.android2.rent_a_space;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.util.Log;
import android.view.Display;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.internal.LegacyInternalGmsClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.type.Date;

import java.util.HashMap;
import java.util.Map;

public class UserSpaceRentPage extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    //firebase
    FirebaseAuth fAuth;
    FirebaseUser currentUser;


    DrawerLayout drawerLayout;
    ActionBarDrawerToggle actionBarDrawerToggle;
    Toolbar toolbar;
    NavigationView navigationView;
    Window window;


    //fragments
    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_space_rent_page);
        //home menu item


        //status bar
        window = this.getWindow();
        window.setStatusBarColor(this.getResources().getColor(R.color.pink));

        //navigation bar
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        //ini
        fAuth = FirebaseAuth.getInstance();
        currentUser = fAuth.getCurrentUser();


        //instance of layout
        drawerLayout = findViewById(R.id.drawer);
        navigationView = findViewById(R.id.navigationView);
        navigationView.setNavigationItemSelectedListener(this);

        //display email of current log in to nav header
        upDateNavHeaderEmail();

        //Handle the toggle of our navigation bar
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open, R.string.close);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.setDrawerIndicatorEnabled(true);
        actionBarDrawerToggle.syncState();

        //load default fragment
        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.container_fragment, new HomeMenu());
        fragmentTransaction.commit();


    }

    //Navigation to Different Fragments //Menu of the navigation bar
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {


        if (item.getItemId() == R.id.nav_to_home) {


            fragmentManager = getSupportFragmentManager();
            fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.container_fragment, new HomeMenu());
            fragmentTransaction.commit();

        } else if (item.getItemId() == R.id.nav_my_account) {


            fragmentManager = getSupportFragmentManager();
            fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.container_fragment, new ProfileMenu());
            fragmentTransaction.commit();


        } else if (item.getItemId() == R.id.nav_my_property) {


            fragmentManager = getSupportFragmentManager();
            fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.container_fragment, new PostPropertyMenu());
            fragmentTransaction.commit();


        } else if (item.getItemId() == R.id.nav_logout) {

            FirebaseAuth.getInstance().signOut();
            startActivity(new Intent(getApplicationContext(), login.class));
            finish();
        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    //update the nav header username
    public void upDateNavHeaderEmail() {

        View headerView = navigationView.getHeaderView(0);
        TextView Nav_Email = headerView.findViewById(R.id.txt_email_displayer);
        Nav_Email.setText(currentUser.getEmail());


    }


}



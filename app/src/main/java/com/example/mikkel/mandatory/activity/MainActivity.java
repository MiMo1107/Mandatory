package com.example.mikkel.mandatory.activity;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mikkel.mandatory.R;
import com.example.mikkel.mandatory.fragment.ContactsFragment;
import com.example.mikkel.mandatory.fragment.CustomerFragment;
import com.example.mikkel.mandatory.fragment.MenuFragment;
import com.example.mikkel.mandatory.fragment.RateFragment;
import com.example.mikkel.mandatory.fragment.ReceiptFragment;
import com.example.mikkel.mandatory.fragment.TakeawayFragment;
import com.example.mikkel.mandatory.fragment.UserFragment;
import com.example.mikkel.mandatory.model.Customer;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private Customer loggedIn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        Menu nav_Menu = navigationView.getMenu();
        if (savedInstanceState == null) {
            Fragment fragment = new MenuFragment();
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction().add(R.id.main_content, fragment).commit();
            setTitle("Menu");
        }

        if(getIntent().getBooleanExtra("admin", false)){
            TextView name = (TextView)  navigationView.getHeaderView(0).findViewById(R.id.nav_header_name);
            name.setText("Administrator");
            TextView email = (TextView)  navigationView.getHeaderView(0).findViewById(R.id.nav_header_email);
            email.setText("");
            nav_Menu.findItem(R.id.nav_myProfile).setVisible(false);
            return;
        }
        String json = getIntent().getStringExtra("Customer");
        if(json != null){

            Gson gson = new Gson();
            loggedIn = gson.fromJson(json, Customer.class);

            if(!loggedIn.getPictureUrl().isEmpty()){
                ImageView image = (ImageView) navigationView.getHeaderView(0).findViewById(R.id.nav_header_image);
                Picasso.with(this).load(loggedIn.getPictureUrl()).into(image);
            }
            TextView name = (TextView)  navigationView.getHeaderView(0).findViewById(R.id.nav_header_name);
            name.setText(loggedIn.getFirstname() + " " + loggedIn.getLastname());
            TextView email = (TextView)  navigationView.getHeaderView(0).findViewById(R.id.nav_header_email);
            email.setText(loggedIn.getEmail());
            Toast.makeText(this, "Welcome " + loggedIn.getFirstname() + " " + loggedIn.getLastname(),Toast.LENGTH_LONG).show();

            nav_Menu.findItem(R.id.nav_customer).setVisible(false);
            nav_Menu.findItem(R.id.nav_takeaway).setVisible(false);
            return;
        } else {
            nav_Menu.findItem(R.id.nav_customer).setVisible(false);
            nav_Menu.findItem(R.id.nav_takeaway).setVisible(false);
            nav_Menu.findItem(R.id.nav_myProfile).setVisible(false);
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        Fragment fragment = null;
        Class fragmentClass = null;

        int id = item.getItemId();
        if (id == R.id.nav_contact) {
            fragmentClass = ContactsFragment.class;
        } else if (id == R.id.nav_menu) {
            fragmentClass = MenuFragment.class;
        } else if (id == R.id.nav_myProfile) {
            fragmentClass = UserFragment.class;
        } else if (id == R.id.nav_ratings) {
            fragmentClass = RateFragment.class;
        } else if (id == R.id.nav_takeaway) {
            fragmentClass = TakeawayFragment.class;
        } else if (id == R.id.nav_receipt) {
            fragmentClass = ReceiptFragment.class;
        }else if (id == R.id.nav_customer) {
            fragmentClass = CustomerFragment.class;
        }

        try {
            fragment = (Fragment) fragmentClass.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }

        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.main_content, fragment).commit();

        // Highlight the selected item has been done by NavigationView
        item.setChecked(true);
        // Set action bar title
        setTitle(item.getTitle());

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void mapsAction(View v) {
        String address = (String)((TextView) v).getText();
        String map = "http://maps.google.co.in/maps?q=" + address;
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(map));
        alert("Do you want to open maps?",intent);
    }

    public void phoneAction(View v) {
        String number = "tel:" + ((TextView) v).getText();
        Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse(number));
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CALL_PHONE},1);
                return;
        }
        alert("Do you want to call the number?",intent);
    }
    public void emailAction(View v){
        String email = "mailto:" + ((TextView) v).getText();
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse(email));
        alert("Do you want to send an email?",intent);
    }

    public void alert(String title, final Intent intent){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setPositiveButton(android.R.string.yes,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        startActivity(intent);
                    }
                });
        builder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    public Customer getLoggedIn() {
        return loggedIn;
    }

    public void clearSharedPreference(View view) {
        SharedPreferences settings;
        SharedPreferences.Editor editor;

        settings = view.getContext().getSharedPreferences("login", view.getContext().MODE_PRIVATE);
        editor = settings.edit();

        editor.clear();
        editor.commit();
        Toast.makeText(this, "Preferences deleted",Toast.LENGTH_SHORT).show();
    }
}

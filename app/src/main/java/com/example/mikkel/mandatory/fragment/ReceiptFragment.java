package com.example.mikkel.mandatory.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.mikkel.mandatory.R;
import com.example.mikkel.mandatory.model.Admin;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class ReceiptFragment extends Fragment {


    public ReceiptFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("users");

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                List<Admin> admins = new ArrayList<Admin>();
                HashMap<String, HashMap<String, String>> values = (HashMap)dataSnapshot.getValue();
                Iterator myVeryOwnIterator = values.keySet().iterator();
                while(myVeryOwnIterator.hasNext()) {
                    String key=(String)myVeryOwnIterator.next();
                    HashMap<String, String> value= values.get(key);
                    Iterator myVeryOwnIterator2 = value.keySet().iterator();
                    Admin admin = new Admin();
                    while(myVeryOwnIterator2.hasNext()) {
                        String key2=(String)myVeryOwnIterator2.next();
                        String value2=(String) value.get(key2);
                        if(key2.equals("username")){
                            admin.setUsername(value2);
                        } else if (key2.equals("password")){
                            admin.setPassword(value2);
                        }
                        admins.add(admin);
                    }

                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w("Firebase", "Failed to read value.", error.toException());
            }
        });

        return inflater.inflate(R.layout.fragment_receipt, container, false);
    }

}

package com.example.mikkel.mandatory.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.mikkel.mandatory.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class ContactsFragment extends Fragment {


    public ContactsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        Log.e("OMG", "Inside of fragment");
        return inflater.inflate(R.layout.fragment_contacts, container, false);
    }

}

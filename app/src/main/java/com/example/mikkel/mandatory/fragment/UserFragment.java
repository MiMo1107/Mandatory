package com.example.mikkel.mandatory.fragment;


import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mikkel.mandatory.R;
import com.example.mikkel.mandatory.activity.MainActivity;
import com.example.mikkel.mandatory.model.Customer;
import com.squareup.picasso.Picasso;

/**
 * A simple {@link Fragment} subclass.
 */
public class UserFragment extends Fragment {

    private Customer customer;

    public UserFragment() {
        customer = null;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_user, container, false);
        customer = ((MainActivity)getActivity()).getLoggedIn();
        if(customer != null) {
            ImageView imageView = (ImageView) rootView.findViewById(R.id.user_image);
            Picasso.with(this.getContext()).load(customer.getPictureUrl()).into(imageView);

            getActivity().setTitle(R.string.user_title);

            TextView firstName = (TextView) rootView.findViewById(R.id.user_firstName);
            firstName.setText(customer.getFirstname());

            TextView lastName = (TextView) rootView.findViewById(R.id.user_lastName);
            lastName.setText(customer.getLastname());

            TextView email = (TextView) rootView.findViewById(R.id.user_email);
            email.setText(customer.getEmail());

            TextView photoUrl= (TextView) rootView.findViewById(R.id.user_photoUrl);
            photoUrl.setText(customer.getPictureUrl());
        }
        return rootView;
    }

}

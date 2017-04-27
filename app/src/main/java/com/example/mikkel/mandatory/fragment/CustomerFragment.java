package com.example.mikkel.mandatory.fragment;


import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.mikkel.mandatory.R;
import com.example.mikkel.mandatory.adapter.CustomerAdapter;
import com.example.mikkel.mandatory.adapter.ItemTouchListener;
import com.example.mikkel.mandatory.model.Customer;
import com.example.mikkel.mandatory.rest.ApiClient;
import com.example.mikkel.mandatory.rest.ApiInterface;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class CustomerFragment extends Fragment {

    private List<Customer> customers;
    private CustomerAdapter adapter;
    private ProgressDialog progress;
    @Override

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        customers = new ArrayList<>();
        adapter = new CustomerAdapter(customers,R.layout.list_item_customer, getActivity().getApplicationContext());


        progress = new ProgressDialog(getActivity());
        progress.setTitle("Loading");
        progress.setMessage("Wait while loading...");
        progress.setCancelable(false); // disable dismiss by tapping outside of the dialog

        // Initialize dataset, this data would usually come from a local content provider or
        // remote server.
        initDataset();
        progress.show();
    }
    public CustomerFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_customer, container, false);

        final RecyclerView recyclerView = (RecyclerView) rootView.findViewById(R.id.customers_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext()));
        recyclerView.setAdapter(adapter);
        getActivity().setTitle("Customers list");
        // Inflate the layout for this fragment
        return rootView;
    }

    private void initDataset() {
        //Retrofit
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);

        Call<List<Customer>> call_list_customer = apiService.getAllCustomer();
        call_list_customer.enqueue(new Callback<List<Customer>>() {
            @Override
            public void onResponse(Call<List<Customer>>call_list_customer, Response<List<Customer>> response) {
                customers.clear();
                customers.addAll(response.body());
                adapter.notifyDataSetChanged();
                progress.dismiss();
            }

            @Override
            public void onFailure(Call<List<Customer>>call, Throwable t) {
                // Log error here since request failed
                Log.e("Fail", t.toString());
            }
        });
    }
}

package com.example.mikkel.mandatory.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.mikkel.mandatory.R;
import com.example.mikkel.mandatory.adapter.DishAdapter;
import com.example.mikkel.mandatory.adapter.DishClickListener;
import com.example.mikkel.mandatory.adapter.ItemTouchListener;
import com.example.mikkel.mandatory.model.Customer;
import com.example.mikkel.mandatory.model.Dish;
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
public class MenuFragment extends Fragment {

    private List<Dish> dishes;
    private DishAdapter adapter;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dishes = new ArrayList<>();
        adapter = new DishAdapter(dishes,R.layout.list_item_dish, getActivity().getApplicationContext());

        // Initialize dataset, this data would usually come from a local content provider or
        // remote server.
        initDataset();
    }

    public MenuFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_menu, container, false);

        final RecyclerView recyclerView = (RecyclerView) rootView.findViewById(R.id.dishes_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext()));
        recyclerView.setAdapter(adapter);
        recyclerView.addOnItemTouchListener(new ItemTouchListener(this.getContext(), recyclerView, new DishClickListener() {
            @Override
            public void onClick(View view, int position) {
                Fragment fragment = new DishFragment(dishes.get(position));
                FragmentManager fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.main_content, fragment).addToBackStack(null).commit();
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));
        getActivity().setTitle("Menu");
        // Inflate the layout for this fragment
        return rootView;
    }

    private void initDataset() {
        //Retrofit
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);

        Call<List<Dish>> call_list_dish = apiService.getAllDishes();
        call_list_dish.enqueue(new Callback<List<Dish>>() {
            @Override
            public void onResponse(Call<List<Dish>>call_list_dish, Response<List<Dish>> response) {

                dishes.clear();
                dishes.addAll(response.body());
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<List<Dish>>call, Throwable t) {
                // Log error here since request failed
                Log.e("Fail", t.toString());
            }
        });
    }

}

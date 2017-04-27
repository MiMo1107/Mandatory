package com.example.mikkel.mandatory.adapter;

/**
 * Created by Mikkel on 07-04-2017.
 */

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.URLUtil;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.mikkel.mandatory.R;
import com.example.mikkel.mandatory.model.Customer;
import com.squareup.picasso.Picasso;

import java.util.List;

public class CustomerAdapter extends RecyclerView.Adapter<CustomerAdapter.CustomerViewHolder> {

    private List<Customer> customers;
    private int rowLayout;
    private Context context;


    public static class CustomerViewHolder extends RecyclerView.ViewHolder {
        LinearLayout dishesLayout;
        ImageView image;
        TextView name;
        TextView email;


        public CustomerViewHolder(View v) {
            super(v);
            dishesLayout = (LinearLayout) v.findViewById(R.id.dish_layout);
            image = (ImageView) v.findViewById(R.id.item_customer_image);
            name = (TextView) v.findViewById(R.id.item_customer_name);
            email = (TextView) v.findViewById(R.id.item_customer_email);
        }
    }

    public CustomerAdapter(List<Customer> customers, int rowLayout, Context context) {
        this.customers = customers;
        this.rowLayout = rowLayout;
        this.context = context;
    }

    @Override
    public CustomerViewHolder onCreateViewHolder(ViewGroup parent,
                                                 int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(rowLayout, parent, false);
        return new CustomerViewHolder(view);
    }


    @Override
    public void onBindViewHolder(CustomerViewHolder holder, final int position) {
        if(customers.get(position).getPictureUrl() != null){
            if(URLUtil.isValidUrl(customers.get(position).getPictureUrl())){
                Picasso.with(this.context)
                        .load(customers.get(position).getPictureUrl())
                        .into(holder.image);
            } else {
                holder.image.setImageResource(R.drawable.ic_menu_user);
            }
        } else {
            holder.image.setImageResource(R.drawable.ic_menu_user);
        }
        holder.name.setText(customers.get(position).getFirstname() + " " + customers.get(position).getLastname());
        holder.email.setText(customers.get(position).getEmail());
    }

    @Override
    public int getItemCount() {
        return customers.size();
    }
}

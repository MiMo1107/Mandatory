package com.example.mikkel.mandatory.adapter;

/**
 * Created by Mikkel on 07-04-2017.
 */

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.mikkel.mandatory.R;
import com.example.mikkel.mandatory.model.TakeAway;

import java.util.List;

public class TakeawayAdapter extends RecyclerView.Adapter<TakeawayAdapter.TakeawayViewHolder> {

    private List<TakeAway> takeAways;
    private int rowLayout;
    private Context context;


    public static class TakeawayViewHolder extends RecyclerView.ViewHolder {
        LinearLayout dishesLayout;
        TextView customer_name;
        TextView email;
        TextView amount;
        TextView dish_name;
        TextView done;
        TextView ordered;


        public TakeawayViewHolder(View v) {
            super(v);
            dishesLayout = (LinearLayout) v.findViewById(R.id.dish_layout);
            email = (TextView) v.findViewById(R.id.item_takeaway_customer_email);
            customer_name = (TextView) v.findViewById(R.id.item_takeaway_customer_name);
            amount = (TextView) v.findViewById(R.id.item_takeaway_dish_amount);
            dish_name = (TextView) v.findViewById(R.id.item_takeaway_dish_name);
            done = (TextView) v.findViewById(R.id.item_takeaway_time_done);
            ordered = (TextView) v.findViewById(R.id.item_takeaway_time_ordered);
        }
    }

    public TakeawayAdapter(List<TakeAway> takeAways, int rowLayout, Context context) {
        this.takeAways = takeAways;
        this.rowLayout = rowLayout;
        this.context = context;
    }

    @Override
    public TakeawayViewHolder onCreateViewHolder(ViewGroup parent,
                                                 int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(rowLayout, parent, false);
        return new TakeawayViewHolder(view);
    }


    @Override
    public void onBindViewHolder(TakeawayViewHolder holder, final int position) {
        holder.amount.setText("" + takeAways.get(position).getHowmany());
        holder.ordered.setText(takeAways.get(position).getOrderDate());
        holder.done.setText(takeAways.get(position).getPickupDateTime());
        holder.customer_name.setText("" + takeAways.get(position).getCustomerId());
        holder.dish_name.setText("" + takeAways.get(position).getDishId());
    }

    @Override
    public int getItemCount() {
        return takeAways.size();
    }
}

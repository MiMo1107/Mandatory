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
import com.example.mikkel.mandatory.model.Dish;
import com.squareup.picasso.Picasso;

import java.util.List;

public class DishAdapter extends RecyclerView.Adapter<DishAdapter.DishViewHolder> {

    private List<Dish> dishes;
    private int rowLayout;
    private Context context;


    public static class DishViewHolder extends RecyclerView.ViewHolder {
        LinearLayout dishesLayout;
        ImageView image;
        TextView name;
        TextView energy;
        TextView weight;
        TextView price;


        public DishViewHolder(View v) {
            super(v);
            dishesLayout = (LinearLayout) v.findViewById(R.id.dish_layout);
            image = (ImageView) v.findViewById(R.id.item_dish_image);
            name = (TextView) v.findViewById(R.id.item_dish_name);
            energy = (TextView) v.findViewById(R.id.item_dish_energy);
            weight = (TextView) v.findViewById(R.id.item_dish_weight);
            price = (TextView) v.findViewById(R.id.item_dish_price);
        }
    }

    public DishAdapter(List<Dish> dishes, int rowLayout, Context context) {
        this.dishes = dishes;
        this.rowLayout = rowLayout;
        this.context = context;
    }

    @Override
    public DishAdapter.DishViewHolder onCreateViewHolder(ViewGroup parent,
                                                                 int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(rowLayout, parent, false);
        return new DishViewHolder(view);
    }


    @Override
    public void onBindViewHolder(DishViewHolder holder, final int position) {
        Picasso.with(this.context)
                .load(dishes.get(position).getPictureUrl())
                .into(holder.image);
        holder.name.setText(dishes.get(position).getTitle());
        holder.energy.setText("" + dishes.get(position).getEnergy() + " kcal");
        holder.weight.setText("" + dishes.get(position).getWeight() + " g");
        holder.price.setText(dishes.get(position).getPrice() + " kr.");
    }

    @Override
    public int getItemCount() {
        return dishes.size();
    }
}

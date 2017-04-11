package com.example.mikkel.mandatory.adapter;

import android.view.View;

/**
 * Created by Mikkel on 11-04-2017.
 */

public interface DishClickListener {
    public void onClick(View view, int position);

    public void onLongClick(View view, int position);
}

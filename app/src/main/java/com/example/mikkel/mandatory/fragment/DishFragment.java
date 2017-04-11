package com.example.mikkel.mandatory.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mikkel.mandatory.R;
import com.example.mikkel.mandatory.model.Dish;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class DishFragment extends Fragment {

    private Dish dish;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
    public DishFragment() {
        dish = null;
    }

    public DishFragment(Dish dish) {
        this.dish = dish;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_dish, container, false);
        if(dish != null) {
            ImageView imageView = (ImageView) rootView.findViewById(R.id.dish_fragment_image);
            Picasso.with(this.getContext()).load(dish.getPictureUrl()).into(imageView);

            getActivity().setTitle(dish.getTitle());
            TextView energy = (TextView) rootView.findViewById(R.id.dish_fragment_energy);
            energy.setText("Energy: " + dish.getEnergy() + "cal");
            TextView fat = (TextView) rootView.findViewById(R.id.dish_fragment_fat);
            fat.setText("Fat: " + dish.getFat() + "g");
            TextView alcohol = (TextView) rootView.findViewById(R.id.dish_fragment_alcohol);
            alcohol.setText("Alcohol: " + dish.getAlcohol() + "g");
            TextView carbohydrates= (TextView) rootView.findViewById(R.id.dish_fragment_carbohydrates);
            carbohydrates.setText("Carbohydrates: " + dish.getCarbohydrates() + "g");
            TextView protein = (TextView) rootView.findViewById(R.id.dish_fragment_protein);
            protein.setText("Protein: " + dish.getProtein() + "g");
            TextView weight = (TextView) rootView.findViewById(R.id.dish_fragment_weight);
            weight.setText("Weight: " + dish.getWeight() + "g");
            TextView price = (TextView) rootView.findViewById(R.id.dish_fragment_price);
            price.setText("Price: " + dish.getPrice() + " kr.");

            PieChart pieChart = (PieChart) rootView.findViewById(R.id.dish_fragment_piechart);
            pieChart.setUsePercentValues(true);

            List<PieEntry> yvalues = new ArrayList<>();
            if(dish.getFat() > 0){
                yvalues.add(new PieEntry(dish.getFat(), "Fat"));
            }
            if(dish.getAlcohol() > 0){
                yvalues.add(new PieEntry(dish.getAlcohol(), "Alcohol"));
            }
            if(dish.getCarbohydrates() > 0){
                yvalues.add(new PieEntry(dish.getCarbohydrates(), "Carbohydrates"));
            }
            if(dish.getProtein() > 0){
                yvalues.add(new PieEntry(dish.getProtein(), "Protein"));
            }

            PieDataSet dataSet = new PieDataSet(yvalues,"");
            dataSet.setColors(ColorTemplate.COLORFUL_COLORS);
            dataSet.setValueFormatter(new PercentFormatter());
            dataSet.setValueTextSize(20f);
            pieChart.setData(new PieData(dataSet));
            Description description = new Description();
            description.setText("");
            pieChart.setDescription(description);
        }
        return rootView;
    }
}

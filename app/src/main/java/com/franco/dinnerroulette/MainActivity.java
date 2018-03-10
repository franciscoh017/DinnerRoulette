package com.franco.dinnerroulette;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Parcelable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;

import com.franco.dinnerroulette.dinnerAdapter.DinnerAdapter;
import com.franco.dinnerroulette.model.Restaurant;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {

    public static final String RESTAURANTS = "Restaurants";
    private List<String> restNames;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        Button btnGenerate = findViewById(R.id.btnGenerate);
        Button btnAdd = findViewById(R.id.btnAdd);

        ListView restaurantsListView = findViewById(R.id.restaurantsListView);
        List<Restaurant> restaurants = new ArrayList<>();
        DinnerAdapter dinnerAdapter = new DinnerAdapter(this, restaurants);

        setupDefaultRestaurants( restaurants );

        btnAdd.setOnClickListener( (View view) -> {
            final EditText input = new EditText(this);
            input.setInputType(InputType.TYPE_CLASS_TEXT);
            AlertDialog.Builder alert = new AlertDialog.Builder(this)
                    .setTitle("Add Restaurant")
                    .setMessage("Enter the restaurant's name")
                    .setCancelable(false);
            alert.setView(input);

            alert.setPositiveButton("OK", (DialogInterface dialog, int which) -> {
                restaurants.add(new Restaurant(input.getText().toString()));
                dinnerAdapter.notifyDataSetChanged();
            } );
            alert.setNegativeButton("Cancel", (dialog, which) -> dialog.cancel() );

            alert.show();

        });



        restaurantsListView.setAdapter( dinnerAdapter );

        btnGenerate.setOnClickListener( (View v) ->{

            if(restaurants.size() <= 1){
                AlertDialog.Builder alert = new AlertDialog.Builder(this)
                        .setTitle("Not enough restaurants")
                        .setMessage("You must add at least 2 restaurant.")
                        .setCancelable(false);

                alert.setPositiveButton("OK", (dialog, which) -> {
                } );
                alert.show();
            }else{
                restNames = new ArrayList<>();
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    restaurants.forEach( (Restaurant restaurant) -> restNames.add( restaurant.name ) );
                }

                Intent intent = new Intent( this, myRoulleteActivity.class );
                intent.putStringArrayListExtra( RESTAURANTS, (ArrayList<String>) restNames );
                startActivity(intent);
            }



        });
    }

    private void setupDefaultRestaurants(List<Restaurant> restaurants) {
        if(restaurants.size()<1){
            restaurants.add( new Restaurant( "Papa Jhon's" ) );
            restaurants.add( new Restaurant( "Taco Bell" ) );
            restaurants.add( new Restaurant( "KFC" ) );
            restaurants.add( new Restaurant( "Yokomo" ) );
            restaurants.add( new Restaurant( "Green Bowl" ) );
            restaurants.add( new Restaurant( "McDonnalds" ) );
            restaurants.add( new Restaurant( "Wendy's" ) );
            restaurants.add( new Restaurant( "Sbarro" ) );
        }

    }
}

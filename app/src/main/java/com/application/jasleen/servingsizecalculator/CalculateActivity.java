package com.application.jasleen.servingsizecalculator;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class CalculateActivity extends AppCompatActivity {
    private static final String TAG = "Calculate Activity";
    private static final String EXTRA_POT_NAME = "com.application.jasleen.servingsizecalculator - CalculateActivity - Pot Name";
    private static final String EXTRA_POT_WEIGHT = "com.application.jasleen.servingsizecalculator - CalculateActivity - Pot Weight";

    private Pot myPot;
//    private String potName; //Alternative
//    private int potWeight;
    private int withFoodWeight;
    private int foodWeight;
    private int servingNumber;
    private int servingWeight;

    //For EditTexts and TextViews
    private EditText numberServings;
    private TextView calculateServingWeight;
    private TextView calculateFoodWeight;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculate);

        extractDataFromIntent();
        setupCalculateValues();

        //EditTexts and TextViews
        numberServings = findViewById(R.id.calculateNumberServings);
        EditText userWeightWithFood = findViewById(R.id.calculateWeightWithFood);
        calculateServingWeight = findViewById(R.id.calculateServingWeight);
        calculateFoodWeight = findViewById(R.id.calculateWeightOfFood);


        numberServings.addTextChangedListener(new TextWatcher(){
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
            @Override
            public void afterTextChanged(Editable s) {
                    try {
                        servingNumber = Integer.parseInt(s.toString());
                        if (servingNumber == 0) {
                            servingWeight = 0;
                            calculateServingWeight.setText(String.valueOf(servingWeight));
                        }else{
                            servingWeight = foodWeight / servingNumber;
                            calculateServingWeight.setText(String.valueOf(servingWeight));
                        }
                        Log.i(TAG, "Serving Number : " + servingNumber + "  Serving Weight: " + servingWeight);

                    } catch (NumberFormatException e) {
                        //When nothing is entered in number of servings
                        calculateServingWeight.setText("");
                        Log.i(TAG, "Serving Number (Catch) : " + servingNumber + "  Serving Weight: " + servingWeight);

                    }
            }
        });

        userWeightWithFood.addTextChangedListener(new TextWatcher(){
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
            @Override
            public void afterTextChanged(Editable s) {
                try {
                    withFoodWeight = Integer.parseInt(s.toString());

                    //Calculation of Weight of food (g)
                    if(withFoodWeight>=myPot.getWeightInG()) {
                        foodWeight = withFoodWeight - myPot.getWeightInG();
                        calculateFoodWeight.setText(String.valueOf(foodWeight));
                        Log.i("Calculate Activity", "Weight with food (g) : " + withFoodWeight + "  Weight of food (g) : " + foodWeight);
                    }
                    else{
                        Toast.makeText(CalculateActivity.this, "Please input Weight with Food (g) greater than Weight Empty (g)", Toast.LENGTH_LONG)
                                .show();
                        calculateServingWeight.setText("");
                        calculateFoodWeight.setText("");
                    }
                    //Checking if Number of Servings has been inputted by user
                    if (numberServings.length() > 0 && withFoodWeight>=myPot.getWeightInG()) {

                        if (servingNumber != 0) {
                            servingWeight = foodWeight / servingNumber;
                            calculateServingWeight.setText(String.valueOf(servingWeight));
                        }else{
                            servingWeight=0;
                            calculateServingWeight.setText(String.valueOf(servingWeight));
                            Log.i(TAG, "Serving Weight: " + servingWeight + "  Weight of food (g) : " + foodWeight);
                        }
                    }
                }
                catch(NumberFormatException e){
                    //When nothing is entered in Weight with food (g)
                    calculateFoodWeight.setText("");
                    calculateServingWeight.setText("");
                    Log.i(TAG, "Serving Weight (Catch): " + servingWeight + "  Weight of food (g) : " + foodWeight);

                }
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_calculate, menu);
        return true;
    }
    // Back button to go to Pot List Activity (Main Activity) using Action Bar Buttons
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.actBarBack:
                Log.i(TAG, "Clicked 'BACK'");
                Toast.makeText(CalculateActivity.this, "Clicked 'BACK'", Toast.LENGTH_SHORT)
                        .show();
                finish(); //goes back to the Pot list Activity
                return true;

        }
        return super.onOptionsItemSelected(item);
    }

    private void setupCalculateValues() {
        //Setting pot name
        TextView calculatePotName = findViewById(R.id.calculatePotName);
        calculatePotName.setText(myPot.getName());

        //Setting empty pot weight
        TextView calculatePotWeight = findViewById(R.id.calculateWeightEmpty);
        calculatePotWeight.setText(String.valueOf(myPot.getWeightInG()));

    }

    private void extractDataFromIntent() {
        Intent intent = getIntent();
        //pulling the data out here, can get rid of String and int and mypot for alternative
        String potName = intent.getStringExtra(EXTRA_POT_NAME);
        int potWeight = intent.getIntExtra(EXTRA_POT_WEIGHT, 0);
        myPot = new Pot(potName, potWeight); // put it into new pot
    }


    // encapsulating in it's second activity the ability to create itself
    public static Intent makeLaunchIntent(Context context, Pot pot) {
        Intent intent = new Intent(context, CalculateActivity.class);
        //pushed the data in here
        intent.putExtra(EXTRA_POT_NAME, pot.getName());
        intent.putExtra(EXTRA_POT_WEIGHT, pot.getWeightInG());
        return intent;
    }


}

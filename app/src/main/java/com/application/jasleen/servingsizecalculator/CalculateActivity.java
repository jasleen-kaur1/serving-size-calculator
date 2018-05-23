package com.application.jasleen.servingsizecalculator;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculate);
        
        extractDataFromIntent();
        setupBackButton();
        setupCalculateValues();
        setupWeightOfFood();
        setupServingWeight();

    }

    private void setupServingWeight() {
        final EditText numberServings = findViewById(R.id.calculateNumberServings);
        numberServings.addTextChangedListener(new TextWatcher(){

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
            @Override
            public void afterTextChanged(Editable s) {
                String valNumberServings = numberServings.getText().toString();
                servingNumber = Integer.parseInt(valNumberServings);

                TextView calculateServingWeight = findViewById(R.id.calculateServingWeight);

                if (servingNumber ==0){

                    servingWeight = 0;
                    calculateServingWeight.setText("" + servingWeight);
                }else {

                    servingWeight = foodWeight / servingNumber;
                    calculateServingWeight.setText("" + servingWeight);
                }
                Log.i(TAG, "Serving Number : "+ servingNumber + "Serving Weight: " + servingWeight);

            }
        });
    }

    private void setupWeightOfFood() {
        final EditText userWeightWithFood = findViewById(R.id.calculateWeightWithFood);
        userWeightWithFood.addTextChangedListener(new TextWatcher(){
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                //Converts EditText of Weight with food (g) to int
                String valWithFoodWeight = userWeightWithFood.getText().toString();
                withFoodWeight = Integer.parseInt(valWithFoodWeight);

                //Calculation of Weight of food (g)
                foodWeight = withFoodWeight - myPot.getWeightInG();

                Log.i("Calculate Activity", "Weight with food (g) : " + withFoodWeight+ "  Weight of food (g) : " + foodWeight);

                //Setting text in Weight of food (g)
                TextView calculateFoodWeight = findViewById(R.id.calculateWeightOfFood);
                calculateFoodWeight.setText("" + foodWeight);

                //Checking if Number of Servings has been inputted by user
                EditText numberOfServings = findViewById(R.id.calculateNumberServings);
                String validServingNumber = numberOfServings.getText().toString();
                if(!validServingNumber.isEmpty() ){

                    if(servingNumber!=0) {
                        servingWeight = foodWeight / servingNumber;

                        TextView calculateServingWeight = findViewById(R.id.calculateServingWeight);
                        calculateServingWeight.setText("" + servingWeight);
                        Log.i("Calculate Activity", "Serving Weight: " + servingWeight + "  Weight of food (g) : " + foodWeight);
                    }

                }

            }
        });
    }

    private void setupCalculateValues() {
        //Setting pot name
        TextView calculatePotName = findViewById(R.id.calculatePotName);
        calculatePotName.setText(myPot.getName());

        //Setting empty pot weight
        TextView calculatePotWeight = findViewById(R.id.calculateWeightEmpty);
        calculatePotWeight.setText("" + myPot.getWeightInG());

    }

    private void extractDataFromIntent() {
        Intent intent = getIntent();
        //pulling the data out here //can get rid of String and int and mypot for alternative
        String potName = intent.getStringExtra(EXTRA_POT_NAME);
        int potWeight = intent.getIntExtra(EXTRA_POT_WEIGHT, 0);
        myPot = new Pot(potName, potWeight); // put it into new pot
    }

    private void setupBackButton() {
        //Wire up the button to do stuff
        Button btnBack = findViewById(R.id.btnBack);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(TAG, "Clicked 'CANCEL'");
                Toast.makeText(CalculateActivity.this, "Clicked 'BACK'", Toast.LENGTH_SHORT)
                        .show();
                finish(); //goes back to the Pot list Activity

            }
        });
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

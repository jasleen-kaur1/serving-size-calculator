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
    private int newFoodWeight;
    private int servingWeight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculate);
        
        extractDataFromIntent();
        setupBackButton();
        setupCalculateValues();
       // setupWeightOfFood();
    }
/*
    private void setupWeightOfFood() {
        foodWeight = withFoodWeight - myPot.getWeightInG();

        TextView calculateFoodWeight = findViewById(R.id.calculateWeightOfFood);
        calculateFoodWeight.setText("" + foodWeight);
    }
*/
    private void setupCalculateValues() {
        //Setting pot name
        TextView calculatePotName = findViewById(R.id.calculatePotName);
        calculatePotName.setText(myPot.getName());

        //Setting pot weight
        TextView calculatePotWeight = findViewById(R.id.calculateWeightEmpty);
        calculatePotWeight.setText("" + myPot.getWeightInG());
/*
        //Weight of food
        EditText userFoodWeight = findViewById(R.id.calculateWeightWithFood);
        String valWithFoodWeight = userFoodWeight.getText().toString();
        withFoodWeight = Integer.parseInt(valWithFoodWeight);
        //setupWeightOfFood();


*/

        final EditText userFoodWeight = findViewById(R.id.calculateWeightWithFood);
        //final TextView calculateFoodWeight = findViewById(R.id.calculateWeightOfFood);
        userFoodWeight.addTextChangedListener(new TextWatcher(){
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String valWithFoodWeight = userFoodWeight.getText().toString();
                withFoodWeight = Integer.parseInt(valWithFoodWeight);

                foodWeight = withFoodWeight - myPot.getWeightInG();

                TextView calculateFoodWeight = findViewById(R.id.calculateWeightOfFood);
                calculateFoodWeight.setText("" + foodWeight);

            }
        });

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

                TextView WeightOfFood = findViewById(R.id.calculateWeightOfFood);
                String valWeightOfFood = WeightOfFood.getText().toString();
                newFoodWeight= Integer.parseInt(valWeightOfFood);

                servingWeight = newFoodWeight/ servingNumber;

                TextView calculateServingWeight = findViewById(R.id.calculateServingWeight);
                calculateServingWeight.setText("" + servingWeight);

            }
        });
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

    public static Intent makeLaunchIntent(Context context, Pot pot) {// encapsulating in it's second activity the ability to create itself
        Intent intent = new Intent(context, CalculateActivity.class);
        //pushed the data in here
        intent.putExtra(EXTRA_POT_NAME, pot.getName());
        intent.putExtra(EXTRA_POT_WEIGHT, pot.getWeightInG());
        return intent;
    }
}

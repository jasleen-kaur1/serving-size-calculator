package com.application.jasleen.servingsizecalculator;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AddPotActivity extends AppCompatActivity {
    private static final String TAG = "Add Pot Activity";
    public static final String RESULT_KEY_POT_NAME = "com.application.jasleen.servingsizecalculator.AddPotActivity - Return Pot Name";
    public static final String RESULT_KEY_POT_WEIGHT = "com.application.jasleen.servingsizecalculator.AddPotActivity - Return Pot Weight";
    public static final int RESULT_CODE_DELETE_POT = 1054;
    private String newPotName;
    private int newPotWeight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_pot);

        setupOkButton();
        setupCancelButton();
        setupDeleteButton();
    }

    private void setupDeleteButton() {
        Button btnDeletePot = findViewById(R.id.btnDeletePot);
        btnDeletePot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setResult(RESULT_CODE_DELETE_POT);
                Log.i(TAG, "Clicked 'DELETE POT'");
                Toast.makeText(AddPotActivity.this, "Clicked 'DELETE POT'", Toast.LENGTH_SHORT)
                        .show();
                finish();
            }
        });
    }


    private void setupOkButton() {
        Button btnOk = findViewById(R.id.btnOk);
        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Extract data from the UI:
                EditText editPotName = findViewById(R.id.txtPotName);
                newPotName = editPotName.getText().toString(); //What the user has typed in

                EditText editPotWeight = findViewById(R.id.txtPotWeight);
                String valWeight = editPotWeight.getText().toString();

                //Pass data back
                Intent intent = new Intent();

                //Added for Error Checking Input (4.3)
                //Validate if user has correctly inputted pot name
                if (newPotName.length() <= 0) {
                    Toast.makeText(AddPotActivity.this, "Please input a Pot Name", Toast.LENGTH_LONG)
                            .show();
                } else {
                    intent.putExtra(RESULT_KEY_POT_NAME, newPotName);
                }
                //Validate that the user has written something for Pot Weight
                if (valWeight.isEmpty()) {
                    Toast.makeText(AddPotActivity.this, "Please input a Pot Weight (g)", Toast.LENGTH_LONG)
                            .show();
                }else {
                    //Convert pot weight string to an integer
                    newPotWeight = Integer.parseInt(editPotWeight.getText().toString());

                    //Validating user's pot weight input
                    if(newPotWeight<0){
                        Toast.makeText(AddPotActivity.this, "Pot Weight (g) must be greater than or equal to zero", Toast.LENGTH_LONG)
                                .show();
                    }else {
                        intent.putExtra(RESULT_KEY_POT_WEIGHT, newPotWeight);
                    }
                    //If user has correctly inputted both pot weight and pot name then only pass data back and finish
                    if(newPotName.length()>0  && newPotWeight>=0) {
                        setResult(Activity.RESULT_OK, intent);
                        Log.i(TAG, "Clicked 'OK'");
                        Toast.makeText(AddPotActivity.this, "Clicked 'OK'", Toast.LENGTH_SHORT)
                                .show();
                        finish(); //always want to call finish to not keep adding on to stack
                    }
                }

            }
        });
    }
    private void setupCancelButton() { //SETS UP CANCEL BUTTON
        //Wire up the button to do stuff
        Button btnCancel = findViewById(R.id.btnCancel);
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(TAG, "Clicked 'CANCEL'");
                Toast.makeText(AddPotActivity.this, "Clicked 'CANCEL'", Toast.LENGTH_SHORT)
                        .show();
                // Intent intent = new Intent();
                finish(); //always want to call finish to not keep adding on to stack
            }
        });

    }


    public static Intent makeIntent(Context context){ // can make itself
        return new Intent(context, AddPotActivity.class);
    }

    public static Pot getPotFromIntent(Intent data){
        String potName= data.getStringExtra(RESULT_KEY_POT_NAME);
        int potWeight= data.getIntExtra(RESULT_KEY_POT_WEIGHT,0);
        return new Pot(potName, potWeight) ;
    }
}

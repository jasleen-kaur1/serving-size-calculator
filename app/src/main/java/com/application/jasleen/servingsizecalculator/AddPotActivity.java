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
    private String newPotName;
    private int newPotWeight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_pot);

        setupCancelButton();
        setupOkButton();
    }

    private void setupOkButton() {
        Button btnOk = findViewById(R.id.btnOk);
        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(TAG, "Clicked 'OK'");
                Toast.makeText(AddPotActivity.this, "Clicked 'OK'", Toast.LENGTH_SHORT)
                        .show();
                //Extract data from the UI:
                EditText editPotName = findViewById(R.id.txtPotName);
                newPotName = editPotName.getText().toString(); //What the user has typed in

                EditText editPotWeight = findViewById(R.id.txtPotWeight);
                String valWeight = editPotWeight.getText().toString();
                newPotWeight = Integer.parseInt(valWeight);

                //Pass data back
                Intent intent = new Intent();
                intent.putExtra(RESULT_KEY_POT_NAME, newPotName);
                intent.putExtra(RESULT_KEY_POT_WEIGHT, newPotWeight);
                setResult(Activity.RESULT_OK, intent);

                finish(); //always want to call finish to not keep adding on to stack
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
               // setResult(Activity.RESULT_CANCELED, intent);
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

package com.application.jasleen.servingsizecalculator;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AddPotActivity extends AppCompatActivity {
    private static final String TAG = "Add Pot Activity";
    public static final String RESULT_KEY_POT_NAME = "com.application.jasleen.servingsizecalculator.AddPotActivity - Return Pot Name";
    public static final String RESULT_KEY_POT_WEIGHT = "com.application.jasleen.servingsizecalculator.AddPotActivity - Return Pot Weight";
    public static final int RESULT_CODE_DELETE_POT = 1054;
    private static final String EXTRA_EDIT_POT_NAME = "com.application.jasleen.servingsizecalculator - edit Pot Name";
    private static final String EXTRA_EDIT_POT_WEIGHT = "com.application.jasleen.servingsizecalculator - edit Pot Weight";

    private EditText editPotName;
    private EditText editPotWeight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_pot);

        editPotName = findViewById(R.id.txtPotName);
        editPotWeight = findViewById(R.id.txtPotWeight);

        extractEditData();
        setupDeleteButton();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_add_pot, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.actBarOk:

                //Extract data from the UI:
                String newPotName = editPotName.getText().toString();
                String valWeight = editPotWeight.getText().toString();

                //Pass data back
                Intent intent = new Intent();

                //Added for Error Checking Input (4.3)
                //Validate if user has correctly inputted pot name
                if (newPotName.length() <= 0) {
                    Toast.makeText(AddPotActivity.this, "Please input a Pot Name", Toast.LENGTH_SHORT)
                            .show();
                } else {
                    intent.putExtra(RESULT_KEY_POT_NAME, newPotName);
                }
                //Validate that the user has written something for Pot Weight
                if (valWeight.isEmpty()) {
                    Toast.makeText(AddPotActivity.this, "Please input a Pot Weight (g)", Toast.LENGTH_SHORT)
                            .show();
                }else {
                    //Convert pot weight string to an integer
                    int newPotWeight = Integer.parseInt(editPotWeight.getText().toString());
                    intent.putExtra(RESULT_KEY_POT_WEIGHT, newPotWeight);

                    //If user has correctly inputted both pot weight and pot name then only pass data back and finish
                    if(newPotName.length()>0) {
                        setResult(Activity.RESULT_OK, intent);
                        Log.i(TAG, "Clicked 'OK'");
                        Toast.makeText(AddPotActivity.this, "Clicked 'OK'", Toast.LENGTH_SHORT)
                                .show();
                        finish(); //always want to call finish to not keep adding on to stack
                    }
                }
                return true;
            case R.id.actBarCancel:
                Log.i(TAG, "Clicked 'CANCEL'");
                Toast.makeText(AddPotActivity.this, "Clicked 'CANCEL'", Toast.LENGTH_SHORT)
                        .show();
                finish(); //always want to call finish to not keep adding on to stack

                return true;
        }
        return super.onOptionsItemSelected(item);
    }
    //Button to Delete Pot (4.2)
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

    public static Intent makeIntent(Context context){ // can make itself
        return new Intent(context, AddPotActivity.class);
    }

    public static Pot getPotFromIntent(Intent data){
        String potName= data.getStringExtra(RESULT_KEY_POT_NAME);
        int potWeight= data.getIntExtra(RESULT_KEY_POT_WEIGHT,0);
        return new Pot(potName, potWeight) ;
    }

    private void extractEditData(){
        Intent receiveIntent = getIntent();
        if(receiveIntent!=null && receiveIntent.getExtras()!=null){
            String name = receiveIntent.getStringExtra(EXTRA_EDIT_POT_NAME);
            editPotName.setText(name);

            int weight = receiveIntent.getIntExtra(EXTRA_EDIT_POT_WEIGHT, 0);
            editPotWeight.setText(String.valueOf(weight));
        }
    }

    public static Intent makeEditIntent(Context context, Pot pot) {
        Intent intent = new Intent(context, AddPotActivity.class);
        intent.putExtra(EXTRA_EDIT_POT_NAME, pot.getName() );
        intent.putExtra(EXTRA_EDIT_POT_WEIGHT, pot.getWeightInG());
        return intent;
    }
}

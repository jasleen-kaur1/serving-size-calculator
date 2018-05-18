package com.application.jasleen.servingsizecalculator;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class AddPotActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_pot);

        setupCancelButton();
    }


    private void setupCancelButton() { //SETS UP CANCEL BUTTON
        //Wire up the button to do stuff
        Button btnCancel = findViewById(R.id.btnCancel);
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish(); //always want to call finish to not keep adding on to stack
            }
        });

    }

    public static Intent makeIntent(Context context){ // can make itself
        return new Intent(context, AddPotActivity.class);
    }
}

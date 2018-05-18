package com.application.jasleen.servingsizecalculator;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "PotListActivity";
    private PotCollection startPotCollection; // instantiate

    //Array of options --> ArrayAdapter --> ListView (Array Adapter populates ListView)

    //List view: {views: potlist_items.xml}

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        startPotCollection = new PotCollection(); //create a new object, instantiate
        setupAddPotButton();
        populateListView();

    }

    private void populateListView() {
        //Create a list of pots

        //IS THIS THE CORRECT WAY?!?!?!
        Pot fry_pan= new Pot("Big Fry Pan", 206);
        Pot hugePot = new Pot("Huge Pot", 1002);
        startPotCollection.addPot(fry_pan);
        startPotCollection.addPot(hugePot);

        //Build Adapter
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                this,                             //Context for activity
                R.layout.potlist_items,                   // Layout to use (create)
                startPotCollection.getPotDescriptions()); //Items to be displayed

        //Configure the list view
        ListView list = (ListView) findViewById(R.id.listViewPotList);
        list.setAdapter(adapter);
    }

    private void setupAddPotButton() {
        // Wire up the button to do stuff
        //...get the button
        Button addPot = findViewById(R.id.btnAddPot);
        addPot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i (TAG, "Clicked 'Add Pot'");
                Toast.makeText(MainActivity.this, "Clicked 'Add Pot'", Toast.LENGTH_SHORT)
                        .show();

                //Launch Add Pot activity
                //Intent intent = new Intent(MainActivity.this, AddPotActivity.class); Alternative way
                Intent intent = AddPotActivity.makeIntent(MainActivity.this);
                startActivity(intent); //Use that intent to start the activity

                //Kill the main activity
                //finish();
            }
        });
    }
}
package com.application.jasleen.servingsizecalculator;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "Pot List Activity";

    public static final int REQUEST_CODE_GETLIST = 1004;
    public static final int REQUEST_CODE_EDIT_POT = 1024;
    private PotCollection startPotCollection; // instantiate
    private int indexOfPot;

    //Array of options --> ArrayAdapter --> ListView (Array Adapter populates ListView)

    //List view: {views: potlist_items.xml}

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        startPotCollection = new PotCollection(); //create a new object, instantiate
        setupAddPotButton();
        populateListView();

        registerClickCallBack();

    }

    private void populateListView() {
        //Create a list of pots, For initial testing    IS THIS THE CORRECT WAY?!?!?!
        /*
        Pot fryPan = new Pot("Big Fry Pan", 206);
        Pot hugePot = new Pot("Huge Pot", 1002);
        startPotCollection.addPot(fryPan);
        startPotCollection.addPot(hugePot);
        */
        String[] myPotList = startPotCollection.getPotDescriptions();
        //Build Adapter
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                this,                             //Context for activity
                R.layout.potlist_items,                   // Layout to use (create)
                myPotList);                               //Items to be displayed

        //Configure the list view
        ListView list = findViewById(R.id.listViewPotList);
        list.setAdapter(adapter);
    }

    //private class ArrayAdapter extends ArrayAdapter<String>{

    //}
    private void registerClickCallBack() {
        ListView list = findViewById(R.id.listViewPotList);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            //When you click on a specific item
            public void onItemClick(AdapterView<?> parent, View viewClicked, int position, long id) {
                TextView textView = (TextView) viewClicked;
                String message = "You clicked # " + position
                        + ", which is string: " + textView.getText().toString();
                Log.i(TAG, message);
                Toast.makeText(MainActivity.this, message, Toast.LENGTH_LONG)
                        .show();

                //Launch Calculate Activity
                //Intent intent = new Intent(MainActivity.this, CalculateActivity.class);
                Intent newIntent = CalculateActivity.makeLaunchIntent(MainActivity.this, startPotCollection.getPot(position) );
                startActivity(newIntent); //Use that intent to start the activity

                //Kill the main activity
                //finish();
            }
        });
        //Long pressing on the pot to Edit Pot (4.1)
        list.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                indexOfPot = position;
                TextView textView = (TextView) view;
                String message = "You clicked # " + position
                        + ", which is string: " + textView.getText().toString() + "to edit";
                Log.i(TAG, message);
                Toast.makeText(MainActivity.this, message, Toast.LENGTH_LONG)
                        .show();
                //Launch Add Pot Activity to edit
                Intent editIntent = AddPotActivity.makeIntent(MainActivity.this);

                //Start activity with the intention of getting result back
                startActivityForResult(editIntent, REQUEST_CODE_EDIT_POT); //Use that intent to start the activity

                return false;
            }
        });
    }

    private void setupAddPotButton() {
        // Wire up the button to do stuff
        //...get the button
        Button addPot = findViewById(R.id.btnAddPot);
        addPot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(TAG, "Clicked 'Add Pot'");
                Toast.makeText(MainActivity.this, "Clicked 'Add Pot'", Toast.LENGTH_SHORT)
                        .show();

                //Launch Add Pot activity
                //Intent intent = new Intent(MainActivity.this, AddPotActivity.class); Alternative way
                Intent intent = AddPotActivity.makeIntent(MainActivity.this);
                //startActivity(intent); //Use that intent to start the activity

                //Start activity with intention of getting result back
                startActivityForResult(intent, REQUEST_CODE_GETLIST); // to launch Add Pot Activity

                //Kill the main activity
                //finish();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) { //come back for cancelling
        //super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case REQUEST_CODE_GETLIST:
                if (resultCode == Activity.RESULT_OK) {

                    //Get the new Pot
                     Pot newPot = AddPotActivity.getPotFromIntent(data);

                    //Add pot to collection and in list
                    startPotCollection.addPot(newPot);
                    populateListView();

                    Log.i(TAG, "New Pot is: " + newPot.getName()+ " - " + newPot.getWeightInG() + "g");
                    break;
                } else {

                    Log.i(TAG, "Activity is cancelled");

                }
                //For Edit Pot (4.1)
            case REQUEST_CODE_EDIT_POT:
                if (resultCode == Activity.RESULT_OK){
                    //Get the new Pot
                    Pot newPot = AddPotActivity.getPotFromIntent(data);
                    startPotCollection.changePot(newPot, indexOfPot);
                    populateListView();
                    Log.i(TAG, "Edited Pot at position #: " + indexOfPot+ " Now: "+ newPot.getName()+ " - " + newPot.getWeightInG() + "g");
                    break;
                }
        }

    }
}
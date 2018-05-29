package com.application.jasleen.servingsizecalculator;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {

    private static final String TAG = "Pot List Activity";

    public static final int REQUEST_CODE_GETLIST = 1004;
    public static final int REQUEST_CODE_EDIT_POT = 1024;
    private PotCollection startPotCollection; // instantiate
    private int indexOfPot;
    String[] myPotList;

    //Array of options --> ArrayAdapter --> ListView (Array Adapter populates ListView)
    //List view: {views: potlist_items.xml}

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        startPotCollection = new PotCollection(); //create a new object, instantiate
        populateListView();
        registerClickCallBack();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }
    //Action Bar button for Add Pot, this is the same as Add Pot Button (4.5)
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.actBarAddPot:
                Log.i(TAG, "Clicked 'Add Pot'");
                Toast.makeText(MainActivity.this, "Clicked 'Add Pot'", Toast.LENGTH_SHORT)
                        .show();

                //Launch Add Pot activity
                Intent intent = AddPotActivity.makeIntent(MainActivity.this);

                //Start activity with intention of getting result back
                startActivityForResult(intent, REQUEST_CODE_GETLIST); // to launch Add Pot Activity

                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    //Items to be displayed in List View
    private void populateListView() {
        //List of pots get stored in myPotList
        myPotList = startPotCollection.getPotDescriptions();
        //Build Adapter
        ArrayAdapter<String>adapter= new ArrayAdapter<>(
                this,                             //Context for activity
                R.layout.potlist_items,                   // Layout to use (create)
                myPotList);                               //Configure the list view
        ListView list = findViewById(R.id.listViewPotList);
        list.setAdapter(adapter);
    }

    private void registerClickCallBack() {
        //For Calculate Activity
        ListView list = findViewById(R.id.listViewPotList);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            //When you click on a specific item
            public void onItemClick(AdapterView<?> parent, View viewClicked, int position, long id) {
                TextView textView = (TextView) viewClicked;
                String message = "You clicked # " + position
                        + ", which is Pot: " + textView.getText().toString();
                Log.i(TAG, message);
                Toast.makeText(MainActivity.this, message, Toast.LENGTH_LONG)
                        .show();

                //Launch Calculate Activity
                Intent newIntent = CalculateActivity.makeLaunchIntent(MainActivity.this, startPotCollection.getPot(position) );

                //Start activity
                startActivity(newIntent); //Use that intent to start the activity

                //To kill the main activity
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
                        + ", which is pot: " + textView.getText().toString() + " to edit";
                Log.i(TAG, message);
                Toast.makeText(MainActivity.this, message, Toast.LENGTH_LONG)
                        .show();
                //Launch Add Pot Activity to edit
                Intent editIntent = AddPotActivity.makeEditIntent(MainActivity.this, startPotCollection.getPot(indexOfPot));

                //Start activity with the intention of getting result back
                startActivityForResult(editIntent, REQUEST_CODE_EDIT_POT); //Use that intent to start the activity
                return true;
            }
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case REQUEST_CODE_GETLIST:
                if (resultCode == Activity.RESULT_OK) {
                    //Get the new Pot
                     Pot newPot = AddPotActivity.getPotFromIntent(data);

                    //Add pot to collection and in list
                    startPotCollection.addPot(newPot);
                    populateListView();

                    Log.i(TAG, "Result new Pot is: " + newPot.getName()+ " - " + newPot.getWeightInG() + "g");
                    break;
                } else {

                    Log.i(TAG, "Activity is cancelled");
                    break;
                }
                //For Edit Pot (4.1)
            case REQUEST_CODE_EDIT_POT:
                if (resultCode == Activity.RESULT_OK) {
                    //Get the new edited Pot
                    Pot newPot = AddPotActivity.getPotFromIntent(data);

                    startPotCollection.changePot(newPot, indexOfPot);
                    populateListView();

                    Log.i(TAG, " Result edited Pot at position #: " + indexOfPot + " Now: " + newPot.getName() + " - " + newPot.getWeightInG() + "g");
                    break;
                    //For Deleting Pot (4.2)
               }if(resultCode == 1054) {

                    startPotCollection.deletePot(indexOfPot);
                    populateListView();
                    break;
               } else {
                    Log.i(TAG, "Activity is cancelled");
                    break;
                }
        }
    }
}


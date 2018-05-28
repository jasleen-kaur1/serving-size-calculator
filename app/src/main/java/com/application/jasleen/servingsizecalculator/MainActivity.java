package com.application.jasleen.servingsizecalculator;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
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
    String[] myPotList;
    //List<String> stringPotList;
    //Array of options --> ArrayAdapter --> ListView (Array Adapter populates ListView)
    //List view: {views: potlist_items.xml}

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //loadListOfPots(getApplicationContext());//FOR saving

        //create a new object, instantiate
        startPotCollection = new PotCollection();
        populateListView();
        //setupAddPotButton();
        registerClickCallBack();
/*
        //for saving 4.4
        Button btnSavePots = findViewById(R.id.btnSaveData);
        btnSavePots.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getListInSharedPreferences();
            }
        });
        */
    }
    /*
//FOR SAVING
    @Override
    protected void onResume() {
        super.onResume();
        loadListOfPots();
        populateListView();
    }
*/
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }

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

    //Items to be displayed
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
/*
    private void getListInSharedPreferences(){
        /*
        SharedPreferences sharedPrefs = getSharedPreferences("Pot Values", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPrefs.edit(); // to edit the file we just created
        Gson gson = new Gson();
        String json = gson.toJson(myPotList);
        editor.putString("pot list", json);
        editor.apply(); //writes all the key values to the sharedPreferences

        //Type type = new TypeToken<PotCollection>{}.getType();
        SharedPreferences sharedPrefs = getSharedPreferences("Pot_Data_Values", Activity.MODE_PRIVATE); //only my application can access it
        //SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(this); //creates XML file stored inside your phone
        SharedPreferences.Editor editor = sharedPrefs.edit(); // to edit the file we just created
        editor.putInt("Pot_Collection_Size", startPotCollection.countPots());
        //key value pairs go here
        for (int i= 0; i<startPotCollection.countPots(); i++) {
            //editor.remove("collection" +i);
            editor.putString("collection" +i, myPotList[i]);
            Log.i(TAG, "HIIII"+myPotList[i]);
        }
        editor.apply(); //writes all the key values to the sharedPreferences

    }

    private void loadListOfPots(){
        //read from shared preferences to get the data
        //and populate the list adapter

        SharedPreferences sharedPrefs = getSharedPreferences("Pot_Data_Values", MODE_PRIVATE); //only my application can access it
        //SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(context); //creates XML file stored inside your phone
        int sizeNew = sharedPrefs.getInt("Pot_Collection_Size", 0);
        myPotList = new String[sizeNew];
        for (int i = 0 ; i < sizeNew ; i++){
            myPotList[i]= sharedPrefs.getString("collection"+i, "");
        }

    }
    */
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

                //Start activity with the intention of getting result back
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
                Intent editIntent = AddPotActivity.makeIntent(MainActivity.this);

                //Start activity with the intention of getting result back
                startActivityForResult(editIntent, REQUEST_CODE_EDIT_POT); //Use that intent to start the activity

                return true;
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
                    //saveListInSharedPreferences(); //added FOR SHARED PREFERENCES

                    Log.i(TAG, "Result new Pot is: " + newPot.getName()+ " - " + newPot.getWeightInG() + "g");
                    break;
                } else {

                    Log.i(TAG, "Activity is cancelled");
                    break;
                }
                //For Edit Pot (4.1)
            case REQUEST_CODE_EDIT_POT:
                if (resultCode == Activity.RESULT_OK) {
                    //Get the new Pot
                    Pot newPot = AddPotActivity.getPotFromIntent(data);
                    startPotCollection.changePot(newPot, indexOfPot);
                    populateListView();
                    Log.i(TAG, " Result edited Pot at position #: " + indexOfPot + " Now: " + newPot.getName() + " - " + newPot.getWeightInG() + "g");
                    break;
                    //For Deleting Pot
               }if(resultCode == 1054) {

                    startPotCollection.deletePot(indexOfPot);
                    populateListView();
                    break;
               } else {
                    Log.i(TAG, "Activity is cancelled");
                    break;
                }
        }
        //getListInSharedPreferences();

    }
}

/*
    SharedPreferences sharedPrefs2 = PreferenceManager.getDefaultSharedPreferences(context);
    startPotCollection.removeAll();
    int sizeNew = sharedPrefs2.getInt("Pot Collection Size", 0);

    for (int j = 0; j<sizeNew ; j++){
        stringPotList.add(sharedPrefs2.getString("calculations" + j, null));
    }

    if(myPotList == null){
        startPotCollection = new PotCollection();
    }

        SharedPreferences sharedPrefs = getSharedPreferences("Pot Values", MODE_PRIVATE);
        Gson gson = new Gson();
        String gson = sharedPrefs.getString("Pot List",null);
        Type type = new TypeToken<PotCollection>{}.getType();
*/


//    private void setupAddPotButton() {
//
//
//        Button actBarAddPot= findViewById(R.id.actBarAddPot);
//        actBarAddPot.setOnClickListener(new v);

//        FOR BUTTON!!!
//        // Wire up the button to do stuff
//        //...get the button
//        Button addPot = findViewById(R.id.btnAddPot);
//        addPot.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Log.i(TAG, "Clicked 'Add Pot'");
//                Toast.makeText(MainActivity.this, "Clicked 'Add Pot'", Toast.LENGTH_SHORT)
//                        .show();
//
//                //Launch Add Pot activity
//                //Intent intent = new Intent(MainActivity.this, AddPotActivity.class); Alternative way
//                Intent intent = AddPotActivity.makeIntent(MainActivity.this);
//                //startActivity(intent); //Use that intent to start the activity
//
//                //Start activity with intention of getting result back
//                startActivityForResult(intent, REQUEST_CODE_GETLIST); // to launch Add Pot Activity
//
//                //Kill the main activity
//                //finish();
//            }
//        });
//    }

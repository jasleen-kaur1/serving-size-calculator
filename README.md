# CMPT 276 Assignment 2: Serving Size Calculator

### App Overview:
<p> 1. User inputs one or more pots' name and weight into app. </p>
<p> 2. In real life: user then uses a pot to make a meal and weighs the pot with the food in it. </p>
<p> 3. In app: user selects the pot they used and enters the combined weight of the pot plus the food. Tool subtracts pot's weight (when empty) from the total weight to compute weight of just the food. </p>
<p> 4. Finally, user enters how many servings are in the food and tool divides the weight of the food by number of servings, displaying weight of each serving. </p>

### App Features:
<p> * List of pots with their weights are displayed on the main activity page </p>
<p> * User can add pot data on a separate activity page </p>
<p> * User can calculate servings on a separate activity page </p>
<p> * User can edit a pot stored in the list of pots by long-pressing on the pot name </p>
<p> * User can delete pot from list of pots </p>
<p> * Error checking is done on all user inputs. Pot name must be at least one character long. Pot weight must be greater than or equal to zero. When an error is detected, a corresponding error toast message is displayed </p>
<p> * Action bar buttons were implemented </p>
<p> * JUnit tests were created to test the Pot class </p>

package com.example.justjava;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;

import java.text.NumberFormat;

/**
 * This app displays an order form to order coffee.
 */
public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.appBarColor)));
    }

    int numberOfCoffees = 1;
    boolean hasWhippedCream = false;
    boolean hasChocolate = false;

    //This method calculates the total cost of an order based on the number of coffees ordered.

    private int calculatePrice(int quantity, boolean hasWhippedCream, boolean hasChocolate)
    {

        int coffeePrice = 50;
        if(hasWhippedCream) {
            coffeePrice += 10;
        }
        if(hasChocolate) {
            coffeePrice += 20;
        }
        int totalPrice = quantity * coffeePrice;
        return totalPrice;
    }

    //This method creates an order summary based on the number of coffees ordered.

    private String createOrderSummary(){
        String message;
        int toppings = 0;
        String name = getName();
        int order = calculatePrice(numberOfCoffees, hasWhippedCream, hasChocolate);
        message = "Name: " + name + "\n";
        if (hasWhippedCream) {
            toppings += 1;
        }
        if (hasChocolate) {
            toppings += 2;
        }
        if (toppings == 0){
            message += "No Toppings have been added";
        }
        else if (toppings == 1){
            message += "Added Whipped Cream";
        }
        else if (toppings == 2){
            message += "Added Chocolate";
        }
        else{
            message += "Both toppings have been added";
        }
        message += "\nQuantity: " + numberOfCoffees;
        message += "\nTotal: " + NumberFormat.getCurrencyInstance().format(order) + "\nThank you!";
        return message;
    }

    /**
     * This method is called when the order button is clicked.
     */


    public void submitOrder(View view) {
        String message = createOrderSummary();
//        displayMessage(message);
        sendOrderSummary(message);

    }

    // These are the methods to change the quantity of coffees.

    public void increment(View view) {

        if(numberOfCoffees < 100) {
            numberOfCoffees++;
            displayQuantity(numberOfCoffees);
        }
        else{
            LinearLayout rootView = findViewById(R.id.root_view);
            String snackbarMessage = "Can not order more than a hundred coffees!";
            int duration =Snackbar.LENGTH_SHORT;
            Snackbar quantitySnackBar = Snackbar.make(rootView, snackbarMessage , duration);
            quantitySnackBar.setBackgroundTint(Color.MAGENTA);
            quantitySnackBar.setTextColor(Color.WHITE);
            quantitySnackBar.show();
        }
    }
    public void decrement(View view) {
        if(numberOfCoffees > 1) {
            numberOfCoffees--;
            displayQuantity(numberOfCoffees);
        }
        else{
            LinearLayout rootView = findViewById(R.id.root_view);
            String snackbarMessage = "Can not order less than one coffee!";
            int duration =Snackbar.LENGTH_SHORT;
            Snackbar quantitySnackBar = Snackbar.make(rootView, snackbarMessage , duration);
            quantitySnackBar.setBackgroundTint(Color.MAGENTA);
            quantitySnackBar.setTextColor(Color.WHITE);
            quantitySnackBar.show();
        }
    }

    /**
     * This method displays the given quantity value on the screen.
     */
    private void displayQuantity(int number) {
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
        quantityTextView.setText("" + number);
    }

//    private void displayMessage(String message) {
//        TextView priceTextView = (TextView) findViewById(R.id.order_summary_text_view);
//        priceTextView.setText(message);
//    }

    private void sendOrderSummary(String message){
        Intent emailIntent = new Intent(Intent.ACTION_SENDTO);
        emailIntent.setData(Uri.parse("mailto:")); // only email apps should handle this
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Just Java Order");
        emailIntent.putExtra(Intent.EXTRA_TEXT, message);
        if (emailIntent.resolveActivity(getPackageManager()) != null)
        {
            startActivity(Intent.createChooser(emailIntent, "Send Mail Using :"));
        }
    }

//    These methods are called when the checkboxes for toppings are checked or unchecked.

    public void checkWhippedCream(View view){
        hasWhippedCream = !hasWhippedCream;
    }

    public void checkChocolate(View view){
        hasChocolate = !hasChocolate;
    }

//    This method is called to return the Name entered in the name field of the order form.

    private String getName() {
        EditText nameField = (EditText) findViewById(R.id.name_field);
        return nameField.getText().toString();
    }
}
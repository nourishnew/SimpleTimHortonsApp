package com.example.android.justjava;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.NumberFormat;

/**
 * This app displays an order form to order coffee.
 */
public class MainActivity extends AppCompatActivity {
    int quantity = 99;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    private String createOrderSummary(int price, boolean addWhippedCream, boolean addChocolate, String name) {
        String orderSummary = "Name : " + name;
        orderSummary += "\nAdd Whipped Cream: " + addWhippedCream;
        orderSummary += "\nAdd Chocolate: " + addChocolate;
        orderSummary = orderSummary + "\nQuantity : " + quantity;
        orderSummary = orderSummary + "\nTotal : $ " + price;
        orderSummary = orderSummary + "\nThankyou";
        return orderSummary;


    }

    /**
     * Calculates the price of the order.
     *
     * @param quantity is the number of cups of coffee ordered
     */
    private int calculatePrice(boolean addWhippedCream, boolean addChocolate) {
        int baseprice = 5;

        if (addWhippedCream) {
            baseprice += 1;
        }
        if (addChocolate) {
            baseprice += 2;
        }

        baseprice = baseprice * quantity;
        return baseprice;
    }

    /**
     * This method is called when the order button is clicked.
     */
    public void submitOrder(View view) {
        EditText nameField = (EditText) findViewById(R.id.edit_Text);
        String name = nameField.getText().toString();
        CheckBox whippedCreamCheckBox = (CheckBox) findViewById(R.id.whipped_cream_checkbox);
        boolean hasWhippedCream = whippedCreamCheckBox.isChecked();
        CheckBox chocolateCheckBox = (CheckBox) findViewById(R.id.chocolate_Check_Box);
        boolean hasChocolate = chocolateCheckBox.isChecked();

        Log.v("MainActivity", "Has Whipped Cream: " + hasWhippedCream);

        int price = calculatePrice(hasWhippedCream, hasChocolate);
        String printMessage = createOrderSummary(price, hasWhippedCream, hasChocolate, name);
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:")); // only email apps should handle this
        intent.putExtra(Intent.EXTRA_SUBJECT, "Just java cofee order for "+name);
        intent.putExtra(Intent.EXTRA_TEXT,printMessage);

        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
        displayMessage(printMessage);


    }

    /**
     * This method displays the given quantity value on the screen.
     */
    private void displayQuantity(int number) {
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
        quantityTextView.setText("" + number);
    }


    private void displayMessage(String message) {
        TextView orderTextView = (TextView) findViewById(R.id.order_summary_text_view);
        orderTextView.setText(message);
    }

    public void increment(View view) {
        if (quantity == 100) {
            Toast.makeText(this, "You cannot have more than 100 cofee u idiot", Toast.LENGTH_SHORT).show();


            return;
        }

        quantity = quantity + 1;
        displayQuantity(quantity);
    }

    public void decrement(View view) {
        if (quantity == 1) {
            Toast.makeText(this, "You cannot have less than 1 cofee u idiot", Toast.LENGTH_SHORT).show();
            return;

        }

        quantity = quantity - 1;
        displayQuantity(quantity);

    }
}



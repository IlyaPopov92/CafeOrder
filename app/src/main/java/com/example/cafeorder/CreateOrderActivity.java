package com.example.cafeorder;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;

public class CreateOrderActivity extends AppCompatActivity {

    private TextView textViewHello;
    private TextView textViewAdditions;
    private CheckBox checkBoxMilk;
    private CheckBox checkBoxSugar;
    private CheckBox checkBoxLemon;
    private Spinner spinnerCoffee;
    private Spinner spinnerTea;

    private String name;
    private String password;

    private String drink;
    private StringBuilder buildAdd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_order);
        Intent intent = getIntent();
        if (intent.hasExtra("name") && intent.hasExtra("password")) {
            name = intent.getStringExtra("name");
            password = intent.getStringExtra("password");
        } else {
            name = getString(R.string.default_name);
            password = getString(R.string.default_password);
        }
        textViewHello = findViewById(R.id.textViewHello);
        String hello = String.format(getString(R.string.hello), name);
        textViewHello.setText(hello);

        textViewAdditions = findViewById(R.id.textViewAdditions);
        drink = getString(R.string.tea);
        String order = String.format(getString(R.string.additions), drink);
        textViewAdditions.setText(order);


        checkBoxMilk = findViewById(R.id.checkBoxMilk);
        checkBoxSugar = findViewById(R.id.checkBoxSugar);
        checkBoxLemon = findViewById(R.id.checkBoxLemon);

        spinnerCoffee = findViewById(R.id.spinnerCoffee);
        spinnerTea = findViewById(R.id.spinnerTea);

        buildAdd = new StringBuilder();

    }

    public void onClickChangeDrink(View view) {
        RadioButton radioButton = (RadioButton) view;
        int id = radioButton.getId();
        if (id == R.id.radioButtonTea) {
            drink = getString(R.string.tea);
            spinnerTea.setVisibility(View.VISIBLE);
            spinnerCoffee.setVisibility(View.INVISIBLE);
            checkBoxLemon.setVisibility(View.VISIBLE);
        } else {
            drink = getString(R.string.coffe);
            spinnerTea.setVisibility(View.INVISIBLE);
            spinnerCoffee.setVisibility(View.VISIBLE);
            checkBoxLemon.setVisibility(View.INVISIBLE);
        }
        String additions = String.format(getString(R.string.additions), drink);
        textViewAdditions.setText(additions);
    }

    public void onClickSendOrder(View view) {
        buildAdd.setLength(0);
        if (checkBoxMilk.isChecked()) {
            buildAdd.append(getString(R.string.milk)).append(" ");
        }
        if (checkBoxSugar.isChecked()) {
            buildAdd.append(getString(R.string.sugar)).append(" ");
        }
        if (checkBoxLemon.isChecked() && drink.equals(getString(R.string.tea))) {
            buildAdd.append(getString(R.string.lemon)).append(" ");
        }
        String optionOfDrink;
        if (drink.equals(getString(R.string.tea))) {
            optionOfDrink = spinnerTea.getSelectedItem().toString();
        } else {
            optionOfDrink = spinnerCoffee.getSelectedItem().toString();
        }
        String order = String.format(getString(R.string.all_order), name, password, drink, optionOfDrink);

        String additions;
        if (buildAdd.length() > 0) {
            additions = getString(R.string.addition) + buildAdd;
        } else {
            additions = " ";
        }

        String fullOrder = order + additions;
        Intent intent = new Intent(this, OrderDetailActivity.class);
        intent.putExtra("order", fullOrder);
        startActivity(intent);

    }
}
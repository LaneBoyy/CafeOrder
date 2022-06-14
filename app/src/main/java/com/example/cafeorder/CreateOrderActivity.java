package com.example.cafeorder;

import androidx.appcompat.app.AppCompatActivity;
import androidx.transition.Visibility;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;

public class CreateOrderActivity extends AppCompatActivity {

    private TextView textViewAdditions;
    private CheckBox checkBoxLemon;
    private CheckBox checkBoxMilk;
    private CheckBox checkBoxSugar;
    private Spinner spinnerTea;
    private Spinner spinnerCoffee;
    private String name;
    private StringBuilder builderAdditions;
    private String password;
    private String drink;

    @SuppressLint("ResourceAsColor")
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


        TextView textViewHello = findViewById(R.id.textViewWelcome);
        textViewAdditions = findViewById(R.id.textViewAdditions);
        checkBoxMilk = findViewById(R.id.checkboxMilk);
        checkBoxSugar = findViewById(R.id.checkboxSugar);
        checkBoxLemon = findViewById(R.id.checkboxLemon);
        spinnerTea = findViewById(R.id.spinnerTea);
        spinnerCoffee = findViewById(R.id.spinnerCoffee);

        String hello = String.format(getString(R.string.hello_user), name);
        textViewHello.setText(hello);

        builderAdditions = new StringBuilder();
        onClickButtonTea();
        onClickButtonCoffee();
        onClickSendOrder();
    }

    private void onClickButtonTea() {
        RadioButton buttonTea = findViewById(R.id.radioButtonTea);
        buttonTea.setOnClickListener(v -> {
            drink = getString(R.string.tea);
            textViewAdditions.setText(R.string.choose_tea);
            checkBoxLemon.setChecked(false);
            spinnerTea.setVisibility(View.VISIBLE);
            spinnerCoffee.setVisibility(View.INVISIBLE);
            checkBoxLemon.setVisibility(View.VISIBLE);
        });
    }

    private void onClickButtonCoffee() {
        RadioButton buttonCoffee = findViewById(R.id.radioButtonCoffee);
        buttonCoffee.setOnClickListener(v -> {
            drink = getString(R.string.coffee);
            textViewAdditions.setText(R.string.choose_coffee);
            spinnerTea.setVisibility(View.INVISIBLE);
            spinnerCoffee.setVisibility(View.VISIBLE);
            checkBoxLemon.setVisibility(View.GONE);
        });
    }

    private void onClickSendOrder() {
        ImageView buttonCreateOrder = findViewById(R.id.buttonCreateOrder);
        buttonCreateOrder.setOnClickListener(v -> {
            builderAdditions.setLength(0);

            if (checkBoxMilk.isChecked()) {
                builderAdditions.append(getString(R.string.milk)).append(" ");
            }
            if (checkBoxSugar.isChecked()) {
                builderAdditions.append(getString(R.string.sugar)).append(" ");
            }
            if (checkBoxLemon.isChecked() && drink.equals(getString(R.string.tea))) {
                builderAdditions.append(getString(R.string.lemon)).append(" ");
            }

            String optionOfDrink = "";
            if (drink.equals(getString(R.string.tea))) {
                optionOfDrink = spinnerTea.getSelectedItem().toString();
            } else {
                optionOfDrink = spinnerCoffee.getSelectedItem().toString();
            }

            String order = String.format(getString(R.string.order), name, password, drink, optionOfDrink);
            String additions;
            if (builderAdditions.length() > 0) {
                additions = "\n" + getString(R.string.need_additions) + builderAdditions.toString();
            } else {
                additions = "";
            }

            String fullOrder = order + additions;
            Intent intent = new Intent(this, OrderDetailActivity.class);
            intent.putExtra("order", fullOrder);
            startActivity(intent);
        });
    }
}
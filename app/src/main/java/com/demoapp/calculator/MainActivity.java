package com.demoapp.calculator;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    // Declare Buttons and TextView for display
    Button btnZero, btn1, btn2, btn3, btn4, btn5, btn6, btn7, btn8, btn9;
    Button btnAllClear, btnClear, btnPercent, btnDivide, btnMultiply, btnMinus, btnPlus, btnEquals, btnDot;
    TextView txtDisplay;

    // Variables for calculations
    double firstOperand = 0;
    double secondOperand = 0;
    String operator = "";
    boolean isOperatorSelected = false;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Number Buttons
        btnZero = findViewById(R.id.btn_zero);
        btn1 = findViewById(R.id.btn_1);
        btn2 = findViewById(R.id.btn_2);
        btn3 = findViewById(R.id.btn_3);
        btn4 = findViewById(R.id.btn_4);
        btn5 = findViewById(R.id.btn_5);
        btn6 = findViewById(R.id.btn_6);
        btn7 = findViewById(R.id.btn_7);
        btn8 = findViewById(R.id.btn_8);
        btn9 = findViewById(R.id.btn_9);

        // Operator buttons
        btnAllClear = findViewById(R.id.btn_all_clear);
        btnClear = findViewById(R.id.btn_clear);
        btnPercent = findViewById(R.id.btn_percent);
        btnDivide = findViewById(R.id.btn_divide);
        btnMultiply = findViewById(R.id.btn_multiply);
        btnMinus = findViewById(R.id.btn_minus);
        btnPlus = findViewById(R.id.btn_plus);
        btnEquals = findViewById(R.id.btn_equals);
        btnDot = findViewById(R.id.btn_dot);
        txtDisplay = findViewById(R.id.txt_display);

        // Set onClickListeners for all buttons
        setListeners();
    }

    public void setListeners() {
        Button[] numberButtons = {btnZero, btn1, btn2, btn3, btn4, btn5, btn6, btn7, btn8, btn9};
        for (Button button : numberButtons) {
            button.setOnClickListener(this);
        }

        Button[] operatorButtons = {btnAllClear, btnClear, btnPercent, btnDivide, btnMultiply, btnMinus, btnPlus, btnEquals, btnDot};
        for (Button button : operatorButtons) {
            button.setOnClickListener(this);
        }
    }

    @SuppressLint({"NonConstantResourceId", "SetTextI18n"})
    public void onClick(View view) {
        String result = txtDisplay.getText().toString();

        switch (view.getId()) {
            // Number Buttons
            case R.id.btn_zero:
            case R.id.btn_1:
            case R.id.btn_2:
            case R.id.btn_3:
            case R.id.btn_4:
            case R.id.btn_5:
            case R.id.btn_6:
            case R.id.btn_7:
            case R.id.btn_8:
            case R.id.btn_9:
                appendNumber(((Button) view).getText().toString());
                break;

            // Operator Buttons
            case R.id.btn_plus:
                handleOperator("+");
                break;
            case R.id.btn_minus:
                handleOperator("-");
                break;
            case R.id.btn_multiply:
                handleOperator("*");
                break;
            case R.id.btn_divide:
                handleOperator("/");
                break;

            case R.id.btn_equals:
                calculateResult();
                break;

            case R.id.btn_clear:
                clear();
                break;

            case R.id.btn_all_clear:
                allClear();
                break;

            case R.id.btn_percent:
                percentage();
                break;

            case R.id.btn_dot:
                appendDot();
                break;

            default:
                break;
        }
    }

    public void appendNumber(String number) {
        if (isOperatorSelected) {
            txtDisplay.setText(number);
            isOperatorSelected = false;
        } else {
            String currentDisplay = txtDisplay.getText().toString();
            if (currentDisplay.equals("0")) {
                txtDisplay.setText(number);
            } else {
                txtDisplay.append(number);
            }
        }
    }

//    public void setOperator(String selectedOperator) {
//        firstOperand = Double.parseDouble(txtDisplay.getText().toString());
//        operator = selectedOperator;
//        isOperatorSelected = true;
//    }

// Updated method for handling operators for chaining the result
public void handleOperator(String selectedOperator) {
    if (!operator.isEmpty()) {
        calculateResult(); // Perform intermediate calculation
    }
    firstOperand = Double.parseDouble(txtDisplay.getText().toString());
    operator = selectedOperator;
    isOperatorSelected = true;
}

    public void calculateResult() {
        secondOperand = Double.parseDouble(txtDisplay.getText().toString());
        double result = 0;

        switch (operator) {
            case "+":
                result = firstOperand + secondOperand;
                break;
            case "-":
                result = firstOperand - secondOperand;
                break;
            case "*":
                result = firstOperand * secondOperand;
                break;
            case "/":
                if (secondOperand != 0) {
                    result = firstOperand / secondOperand;
                } else {
                    txtDisplay.setText("Can't divide by zero");
                    return;
                }
                break;
        }

        txtDisplay.setText(String.valueOf(result));
        firstOperand = result; // allow chaining the result
        isOperatorSelected = true;
    }

    public void clear() {
        String currentText = txtDisplay.getText().toString();
        if (!currentText.isEmpty()) {
            txtDisplay.setText(currentText.substring(0, currentText.length() - 1));
        }
    }

    public void allClear() {
        txtDisplay.setText("0");
        firstOperand = 0;
        secondOperand = 0;
        operator = "";
        isOperatorSelected = false;
    }

    public void percentage() {
        double value = Double.parseDouble(txtDisplay.getText().toString());
        txtDisplay.setText(String.valueOf(value / 100));
    }

    public void appendDot() {
        if (!txtDisplay.getText().toString().contains(".")) {
            txtDisplay.append(".");
        }
    }
}

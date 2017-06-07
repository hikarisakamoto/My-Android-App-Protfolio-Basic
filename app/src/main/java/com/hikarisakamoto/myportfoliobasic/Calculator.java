package com.hikarisakamoto.myportfoliobasic;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Calculator extends AppCompatActivity {

    private EditText result, newNumber;
    private TextView displayOperation;
    private static final String STATE_PENDINDG_OPERATION = "PendingOperation";
    private static final String STATE_OPERAND1 = "Operand1";
    private static final String STATE_MEMORY = "Memory";

    // Variables to hold operands and types os calculations
    private Double operand1 = null;
    private String pendingOperation = "=";
    private Double memory = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculator);
        // Setting up the UP button
//        Toolbar toolbar = (Toolbar) findViewById(R.id.ac)

        setTitle(getResources().getString(R.string.app1));
        //EditText and TextView
        result = (EditText) findViewById(R.id.result);
        newNumber = (EditText) findViewById(R.id.newNumber);
        displayOperation = (TextView) findViewById(R.id.operation);

        //Buttons
        // 0-9
        Button button0 = (Button) findViewById(R.id.button0);
        Button button1 = (Button) findViewById(R.id.button1);
        Button button2 = (Button) findViewById(R.id.button2);
        Button button3 = (Button) findViewById(R.id.button3);
        Button button4 = (Button) findViewById(R.id.button4);
        Button button5 = (Button) findViewById(R.id.button5);
        Button button6 = (Button) findViewById(R.id.button6);
        Button button7 = (Button) findViewById(R.id.button7);
        Button button8 = (Button) findViewById(R.id.button8);
        Button button9 = (Button) findViewById(R.id.button9);
        // Dot
        Button buttonDot = (Button) findViewById(R.id.buttonDot);
        // Operations
        Button buttonEquals = (Button) findViewById(R.id.buttonEquals);
        Button buttonDivide = (Button) findViewById(R.id.buttonDivide);
        Button buttonMultiply = (Button) findViewById(R.id.buttonMultiply);
        Button buttonMinus = (Button) findViewById(R.id.buttonMinus);
        Button buttonPlus = (Button) findViewById(R.id.buttonPlus);
        // CLEAR BUTTON
        Button buttonClear = (Button) findViewById(R.id.buttonClear);
        // BUTTON NEG
        Button buttonNeg = (Button) findViewById(R.id.buttonNeg);

        // New Operation buttons
        Button buttonOneByX = (Button) findViewById(R.id.buttonOneByX);
        Button buttonPercent = (Button) findViewById(R.id.buttonPercentage);
        Button buttonSquareRoot = (Button) findViewById(R.id.buttonSquareRoot);

        // New functions
        Button buttonMemoryStore = (Button) findViewById(R.id.buttonMemoryStore);
        Button buttonMemoryRecall = (Button) findViewById(R.id.buttonMemoryRecall);


        View.OnClickListener numbersListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Assigning the number in the button clicked to the EditText newNumber using toString
                Button btn = (Button) v;
                newNumber.append(btn.getText().toString());

            }
        };
        // Setting the OnClickListener for each button 0-9 and .
        button0.setOnClickListener(numbersListener);
        button1.setOnClickListener(numbersListener);
        button2.setOnClickListener(numbersListener);
        button3.setOnClickListener(numbersListener);
        button4.setOnClickListener(numbersListener);
        button5.setOnClickListener(numbersListener);
        button6.setOnClickListener(numbersListener);
        button7.setOnClickListener(numbersListener);
        button8.setOnClickListener(numbersListener);
        button9.setOnClickListener(numbersListener);
        buttonDot.setOnClickListener(numbersListener);


        //ClickListner for the Operations
        View.OnClickListener operationsListner = new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Button btn = (Button) v;
                String operation = btn.getText().toString();
                String value = newNumber.getText().toString();

//                if (value.length() != 0) {
//                    performOperation(value, operation);
//                }

                try {

                    Double doubleValue = Double.valueOf(value);
                    performOperation(doubleValue, operation);

                } catch (NumberFormatException e) {

                    newNumber.setText("");

                }
                pendingOperation = operation;
                displayOperation.setText(pendingOperation);

            }
        };
        // Setting the OnClickListener for the operation Buttons
        buttonEquals.setOnClickListener(operationsListner);
        buttonDivide.setOnClickListener(operationsListner);
        buttonMultiply.setOnClickListener(operationsListner);
        buttonMinus.setOnClickListener(operationsListner);
        buttonPlus.setOnClickListener(operationsListner);


        // BUTTON NEG CLICK LISTENER
        buttonNeg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String value = newNumber.getText().toString();
                if (value.length() == 0) {
                    newNumber.setText("-");
                } else {
                    try {
                        Double doubleValue = Double.valueOf(value);
                        doubleValue *= -1;
                        newNumber.setText(doubleValue.toString());
                    } catch (NumberFormatException e) {
                        // newNumber is "-" or ".", clear it
                        newNumber.setText("");
                    }
                }
            }
        });


        // BUTTON CLEAR CLICKLISTENER
        buttonClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                newNumber.setText("");
                pendingOperation = "=";
                operand1 = null;
                displayOperation.setText("");
                result.setText("");
            }
        });


        // BUTTON MEMORY STORE
        buttonMemoryStore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (newNumber.getText().toString().equals("")) {
                    Toast.makeText(getBaseContext(), getResources().getString(R.string.nothingtostore), Toast.LENGTH_SHORT).show();
                } else {
                    memory = Double.valueOf(newNumber.getText().toString());
                    Toast.makeText(getBaseContext(), "Number " + newNumber.getText().toString() + " stored!", Toast.LENGTH_LONG).show();


                }
            }
        });


        // BUTTON MEMORY RECALL
        buttonMemoryRecall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (memory == null) {
                    Toast.makeText(getBaseContext(), getResources().getString(R.string.memoryempty), Toast.LENGTH_SHORT).show();
                } else {
                    newNumber.setText(memory.toString());
                }
            }
        });

        // BUTTON SQUARE ROOT
        buttonSquareRoot.setOnClickListener(new View.OnClickListener()

        {
            @Override
            public void onClick(View v) {
                displayOperation.setText(getResources().getString(R.string.sqr));
                if (newNumber.getText().toString().equals("") || newNumber.getText().toString().equals("-")) {
                    result.setText("0");
                } else {
                    Double value = Math.sqrt(Double.valueOf(newNumber.getText().toString()));
                    result.setText(value.toString());
                    newNumber.setText("");
                }
            }
        });

        // BUTTON ONE BY X
        buttonOneByX.setOnClickListener(new View.OnClickListener()

        {
            @Override
            public void onClick(View v) {
                if (newNumber.getText().toString().equals("") || newNumber.getText().toString().equals("-")) {
                    Toast.makeText(getBaseContext(), getResources().getString(R.string.cantperform), Toast.LENGTH_SHORT);
                } else {
                    displayOperation.setText("1/" + newNumber.getText());
                    Double value = Double.valueOf(newNumber.getText().toString());
                    value = 1 / value;
                    result.setText(value.toString());
                    newNumber.setText("");
                }
            }
        });

        // BUTTON PERCENT
        buttonPercent.setOnClickListener(new View.OnClickListener()

        {
            @Override
            public void onClick(View v) {
                Toast toast = Toast.makeText(getBaseContext(), getResources().getString(R.string.percentinfo), Toast.LENGTH_SHORT);
                if (result.getText().toString().equals("")) {
                    toast.show();
                    result.setText(newNumber.getText());
                    newNumber.setText("");
                } else if (newNumber.getText().toString().equals("") || newNumber.getText().toString().equals("-")) {
                    toast.show();
                } else {
                    Double value = ((Double.valueOf(result.getText().toString()) / 100) * Double.valueOf(newNumber.getText().toString()));
                    displayOperation.setText(result.getText() + "% x " + newNumber.getText());
                    result.setText(value.toString());
                    newNumber.setText("");

                }

            }
        });


    }

    private void performOperation(Double value, String operation) {

        if (null == operand1) { //Verify if operand1 is null if is goes to direct to result.setText
            operand1 = value;
        } else { // If operand1 isnt null rest of the code will run

            if (pendingOperation.equals("=")) {
                pendingOperation = operation;
            }

            switch (pendingOperation.toUpperCase()) {

                case "=":
                    operand1 = value;
                    break;

                case "/":
                    if (value == 0) {
                        operand1 = 0.0;
                    } else {
                        operand1 /= value;
                    }
                    break;

                case "*":
                    operand1 *= value;
                    break;

                case "-":
                    operand1 -= value;
                    break;

                case "+":
                    operand1 += value;
                    break;
            }
        }

        result.setText(operand1.toString());
        newNumber.setText("");

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putString(STATE_PENDINDG_OPERATION, pendingOperation);
        if (operand1 != null) {
            outState.putDouble(STATE_OPERAND1, operand1);

        }

        if (memory != null) {
            outState.putDouble(STATE_MEMORY, memory);
        } else {
            memory = null;
        }
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        pendingOperation = savedInstanceState.getString(STATE_PENDINDG_OPERATION);
        memory = savedInstanceState.getDouble(STATE_MEMORY);
        operand1 = savedInstanceState.getDouble(STATE_OPERAND1);
        try {
            displayOperation.setText(pendingOperation);
        } catch (Exception e) {
            displayOperation.setText("=");
        }
    }
}
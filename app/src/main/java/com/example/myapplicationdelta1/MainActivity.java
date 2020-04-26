package com.example.learnfactorise;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashSet;
import java.util.Random;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, RadioGroup.OnCheckedChangeListener {

    EditText number;
    TextView question;
    RadioGroup options;
    RadioButton option1, option2, option3;
    Button test;

    int answer;
    HashSet<Integer> optionValues = null;

    private void initializeActivity(){
        number = findViewById(R.id.edt_getNumber);
        question = findViewById(R.id.txtVW_question);
        options = findViewById(R.id.optionGroup);
        option1 = findViewById(R.id.option1);
        option2 = findViewById(R.id.option2);
        option3 = findViewById(R.id.option3);
        test = findViewById(R.id.btnTest);
        Toast.makeText(getApplicationContext(),"Enter any number other than 0", Toast.LENGTH_LONG).show();
        options.setVisibility(View.INVISIBLE);
        question.setVisibility(View.INVISIBLE);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initializeActivity();
        test.setOnClickListener(this);
        options.setOnCheckedChangeListener(this);
    }

    private int generateCorrectAnswer(int number){
        Random random = new Random();
        do {
            int randomNumber = random.nextInt(number);
            if (randomNumber != 0 && (number % randomNumber) == 0) {
                return randomNumber;
            }
        }while(true);
    }

    private int generateWrongAnswer(int number){
        Random random = new Random();
        do {
            int randomNumber = random.nextInt(number+10) ;
            if (randomNumber != 0
                    && (number % randomNumber) != 0
                    && !(optionValues.contains(randomNumber))) {
                return randomNumber;
            }
        }while(true);
    }

    private void setQuestionAndAnswer() {
        int num= Integer.parseInt(number.getText().toString());

        if(num == 0) {
            Toast.makeText(this, "Number should not be zero. Please try again", Toast.LENGTH_LONG).show();
            return;
        } else if (num == 1) {
            answer = 1;
        } else {
            answer = generateCorrectAnswer(num);
        }

        question.setText("Which one is the factor of " + number.getText().toString() + "?");
        optionValues = new HashSet<>();
        optionValues.add(answer);
        optionValues.add(generateWrongAnswer(num));
        optionValues.add(generateWrongAnswer(num));

        int i = 1;
        for (int optionValue : optionValues) {
            switch (i) {
                case 1:
                    option1.setText(String.valueOf(optionValue));
                case 2:
                    option2.setText(String.valueOf(optionValue));
                case 3:
                    option3.setText(String.valueOf(optionValue));
            }
            i++;
        }
        options.setVisibility(View.VISIBLE);
        question.setVisibility(View.VISIBLE);
    }

    private void validateAnswer(RadioGroup group){
        RadioButton checkedOptionRdBtn = findViewById(group.getCheckedRadioButtonId());
        int checkedOptionValue = Integer.parseInt(checkedOptionRdBtn.getText().toString());
        if (checkedOptionValue == answer){
            Toast.makeText(getApplicationContext(),"Correct Answer", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(getApplicationContext(),"Selected option is wrong answer. \n The Correct answer is " + answer, Toast.LENGTH_LONG).show();
        }
        resetScreen();
    }

    private void resetScreen(){
        options.setVisibility(View.INVISIBLE);
        question.setVisibility(View.INVISIBLE);

        option1.setChecked(false);
        option3.setChecked(false);
        option2.setChecked(false);
    }

    @Override
    public void onClick(View v) {

        setQuestionAndAnswer();
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        validateAnswer(group);
    }
}

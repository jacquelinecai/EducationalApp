package com.example.schoolgame;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Random;

public class Essay extends AppCompatActivity {

    private String[] problems;
    private String[] answers;
    TextView problem;
    TextView answer;
    Button submit;
    int randomIndex;
    int points = 10;

    public static final String POINTS = "0";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.essay);

        problems = getResources().getStringArray(R.array.Kenglish);
        randomIndex = new Random().nextInt(problems.length);
        String randomProblem = problems[randomIndex];
        problem = findViewById(R.id.problemEssay);
        problem.setText(randomProblem);

        submit = findViewById(R.id.submitEssay);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                answers = getResources().getStringArray(R.array.KenglishAns);
                String ans = answers[randomIndex];
                answer = findViewById(R.id.answerEssay);
                String input = answer.getText().toString();

                if (input.equalsIgnoreCase(ans)) {
                    Toast.makeText(Essay.this, "Correct!", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(getApplicationContext(), MainScreen.class);
                    intent.putExtra(POINTS, points);
                    setResult(2000, intent);
                    finish();
                } else {
                    Toast.makeText(Essay.this, "Incorrect answer", Toast.LENGTH_LONG).show();
                    points -= 2;
                    if (points < 0) {
                        points = 0;
                    }
                }
            }
        });
    }
}

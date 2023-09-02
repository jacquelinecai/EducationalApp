package com.example.schoolgame;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Random;

public class Pset extends AppCompatActivity {

    private String[] problems;
    private String[] answers;
    TextView problem;
    TextView answer;
    Button submit;
    int randomIndex;
    int points = 5;

    public static final String POINTS = "0";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pset);

        problems = getResources().getStringArray(R.array.Kmath);
        randomIndex = new Random().nextInt(problems.length);
        String randomProblem = problems[randomIndex];
        problem = findViewById(R.id.problemPset);
        problem.setText(randomProblem);

        submit = findViewById(R.id.submitPset);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                answers = getResources().getStringArray(R.array.KmathAns);
                String ans = answers[randomIndex];
                answer = findViewById(R.id.answerPset);
                String input = answer.getText().toString();

                if (input.equalsIgnoreCase(ans)) {
                    Toast.makeText(Pset.this, "Correct!", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(getApplicationContext(), MainScreen.class);
                    intent.putExtra(POINTS, points);
                    setResult(1000, intent);
                    finish();
                } else {
                    Toast.makeText(Pset.this, "Incorrect answer", Toast.LENGTH_LONG).show();
                    points--;
                    if (points < 0) {
                        points = 0;
                    }
                }
            }
        });
    }
}

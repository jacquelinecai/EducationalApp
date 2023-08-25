package com.example.schoolgame;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Random;

public class Video extends AppCompatActivity {

    private String[] problems;
    private String[] answers;
    TextView problem;
    TextView answer;
    Button submit;
    int randomIndex;
    int points = 20;

    public static final String POINTS = "0";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.video);

        problems = getResources().getStringArray(R.array.Khistory);
        randomIndex = new Random().nextInt(problems.length);
        String randomProblem = problems[randomIndex];
        problem = findViewById(R.id.problemVideo);
        problem.setText(randomProblem);

        submit = findViewById(R.id.submitVideo);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                answers = getResources().getStringArray(R.array.KhistoryAns);
                String ans = answers[randomIndex];
                answer = findViewById(R.id.answerVideo);
                String input = answer.getText().toString();

                if (input.equalsIgnoreCase(ans)) {
                    Intent intent = new Intent(getApplicationContext(), MainScreen.class);
                    intent.putExtra(POINTS, points);
                    setResult(3000, intent);
                    finish();
                } else {
                    Toast.makeText(Video.this, "Incorrect answer", Toast.LENGTH_LONG).show();
                    points -= 3;
                    if (points < 0) {
                        points = 0;
                    }
                }
            }
        });
    }
}

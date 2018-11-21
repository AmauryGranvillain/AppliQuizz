package fr.diginamic.formation.monquizz.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import fr.diginamic.formation.monquizz.R;
import fr.diginamic.formation.monquizz.ui.fragments.ScoreFragment;

public class AnswerActivity extends AppCompatActivity {

    private Boolean result;

    private TextView textResult;
    private static int scoreUser = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_answer);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        this.textResult = findViewById(R.id.show_results);

        if( getIntent().getBooleanExtra("result_answer", true)){
            textResult.setText(getString(R.string.correct_answer));
            scoreUser++;
        } else {
            textResult.setText(getString(R.string.wrong_answer));
        }
    }

}

package fr.diginamic.formation.monquizz.ui.activities;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import fr.diginamic.formation.monquizz.R;

public class AnswerActivity extends AppCompatActivity {

    private Boolean result;

    private TextView textResult;

    /*
    * Verify answer and show the result
    */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_answer);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        this.textResult = findViewById(R.id.show_results);

        if( getIntent().getBooleanExtra("result_answer", true)){
            textResult.setText("Bonne réponse !");
        } else {
            textResult.setText("Mauvaise réponse !");
        }

    }

}

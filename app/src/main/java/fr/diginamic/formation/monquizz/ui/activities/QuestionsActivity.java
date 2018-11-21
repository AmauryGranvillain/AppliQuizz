package fr.diginamic.formation.monquizz.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

import fr.diginamic.formation.monquizz.model.Question;
import fr.diginamic.formation.monquizz.R;

public class QuestionsActivity extends AppCompatActivity {

    private TextView intitule;

    private Button buttonFirstAnswer, buttonSecondAnswer, buttonThirdAnswer, buttonFourthAnswer;

    private String answer;
    private Boolean testResult;

    private ArrayList<String> propositions;

    /*
    * Test result after click on any answer
    */

    Question question;

    private View.OnClickListener validAnswer = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Button tappedAnswer = (Button) v;
            answer = tappedAnswer.getText().toString();
            Intent intent = new Intent(QuestionsActivity.this, AnswerActivity.class);
            if(question.verifierReponse(answer)) {
                intent.putExtra("result_answer", true);
                startActivity(intent);
            } else {
                intent.putExtra("result_answer", false);
                startActivity(intent);
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_questions);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        /**
         * Setup question
         */

        question = getIntent().getParcelableExtra("item");

        this.intitule = findViewById(R.id.intitule_question);
        this.buttonFirstAnswer = findViewById(R.id.button_firstanswer);
        this.buttonSecondAnswer = findViewById(R.id.button_secondanswer);
        this.buttonThirdAnswer = findViewById(R.id.button_thirdanswer);
        this.buttonFourthAnswer = findViewById(R.id.button_fourthanswer);




        intitule.setText(question.getIntitule());
        buttonFirstAnswer.setText(question.getPropositions().get(0));
        buttonSecondAnswer.setText(question.getPropositions().get(1));
        buttonThirdAnswer.setText(question.getPropositions().get(2));
        buttonFourthAnswer.setText(question.getPropositions().get(3));

        buttonFirstAnswer.setOnClickListener(validAnswer);
        buttonSecondAnswer.setOnClickListener(validAnswer);
        buttonThirdAnswer.setOnClickListener(validAnswer);
        buttonFourthAnswer.setOnClickListener(validAnswer);

    }

}

package fr.diginamic.formation.monquizz.ui.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RadioButton;

import fr.diginamic.formation.monquizz.R;
import fr.diginamic.formation.monquizz.model.Question;
import fr.diginamic.formation.monquizz.ui.activities.MainActivity;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link CreateQuestion.OnCreateListener} interface
 * to handle interaction events.
 * Use the {@link CreateQuestion#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CreateQuestion extends Fragment {

    private RadioButton radioButton2, radioButton3, radioButton4, radioButton1;

    private EditText editTextQuestion, editTextAnswer1, editTextAnswer2, editTextAnswer3, editTextAnswer4, editTextAnswerSelected;

    public OnCreateListener listener;

    public CreateQuestion() {
        // Required empty public constructor
    }

    private View.OnClickListener checkRadioButton = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            RadioButton check = (RadioButton) v;
            checkedRadioButton(check);
        }
    };

    public static CreateQuestion newInstance(String param1, String param2) {
        CreateQuestion fragment = new CreateQuestion();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_create_question, container, false);

        radioButton1 = rootView.findViewById(R.id.select_first_answer);
        radioButton2 = rootView.findViewById(R.id.select_second_answer);
        radioButton3 = rootView.findViewById(R.id.select_third_answer);
        radioButton4 = rootView.findViewById(R.id.select_fourth_answer);
        editTextQuestion = rootView.findViewById(R.id.creation_title_question);
        editTextAnswer1 = rootView.findViewById(R.id.create_first_answer);
        editTextAnswer2 = rootView.findViewById(R.id.create_second_answer);
        editTextAnswer3 = rootView.findViewById(R.id.create_third_answer);
        editTextAnswer4 = rootView.findViewById(R.id.create_fourth_answer);


        radioButton1.setOnClickListener(checkRadioButton);
        radioButton2.setOnClickListener(checkRadioButton);
        radioButton3.setOnClickListener(checkRadioButton);
        radioButton4.setOnClickListener(checkRadioButton);

        rootView.findViewById(R.id.valid_create_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               String stringCorrectAnswer = editTextAnswerSelected.getText().toString();

                Question question = new Question(editTextQuestion.getText().toString(), 4);

                question.addProposition(editTextAnswer1.getText().toString());
                question.addProposition(editTextAnswer2.getText().toString());
                question.addProposition(editTextAnswer3.getText().toString());
                question.addProposition(editTextAnswer4.getText().toString());
                question.setBonneReponse(stringCorrectAnswer);

                listener.createQuestion(question);
            }
        });

        return rootView;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        listener = null;
    }

    public void checkedRadioButton(RadioButton check){
        if (check.isChecked()) {
            radioButton1.setChecked(false);
            radioButton2.setChecked(false);
            radioButton3.setChecked(false);
            radioButton4.setChecked(false);

            check.setChecked(true);

            switch (check.getId()){
                case R.id.select_first_answer :
                    editTextAnswerSelected = editTextAnswer1;
                    break;
                case R.id.select_second_answer :
                    editTextAnswerSelected = editTextAnswer2;
                    break;
                case R.id.select_third_answer :
                    editTextAnswerSelected = editTextAnswer3;
                    break;
                case R.id.select_fourth_answer :
                    editTextAnswerSelected = editTextAnswer4;
                    break;
            }

        }
    }

    public interface OnCreateListener {
        void createQuestion(Question q);
    }
}

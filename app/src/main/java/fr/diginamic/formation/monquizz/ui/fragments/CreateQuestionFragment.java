package fr.diginamic.formation.monquizz.ui.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import fr.diginamic.formation.monquizz.R;
import fr.diginamic.formation.monquizz.model.Question;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link CreateQuestionFragment.OnCreateListener} interface
 * to handle interaction events.
 * Use the {@link CreateQuestionFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CreateQuestionFragment extends Fragment {

    private RadioButton radioButton2, radioButton3, radioButton4, radioButton1;

    private EditText editTextQuestion, editTextAnswer1, editTextAnswer2, editTextAnswer3, editTextAnswer4, editTextAnswerSelected;

    private Question questionEdit;

    public OnCreateListener listener;

    public CreateQuestionFragment() {
        // Required empty public constructor
    }

    private View.OnClickListener checkRadioButton = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            RadioButton check = (RadioButton) v;
            checkedRadioButton(check);
        }
    };

    public static CreateQuestionFragment newInstance(String param1, String param2) {
        CreateQuestionFragment fragment = new CreateQuestionFragment();
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

                if(editTextAnswerSelected == null){

                    Toast toast = Toast.makeText(getContext(), "Select the good answer please !", Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.TOP|Gravity.CENTER_VERTICAL, 0, 0);
                    toast.show();

                } else {

                    String stringCorrectAnswer = editTextAnswerSelected.getText().toString();

                    Question question = new Question(editTextQuestion.getText().toString(), 4);

                    question.addProposition(editTextAnswer1.getText().toString());
                    question.addProposition(editTextAnswer2.getText().toString());
                    question.addProposition(editTextAnswer3.getText().toString());
                    question.addProposition(editTextAnswer4.getText().toString());
                    question.setBonneReponse(stringCorrectAnswer);

                    listener.createQuestion(question);

                }
            }
        });

        if( questionEdit != null ){
            setupQuestion(questionEdit);
        }

        return rootView;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        listener = null;
    }

    /*
    Possibilty to select only one RadioButton and collect the good answer
     */

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

    public void setQuestion (Question q){
        questionEdit = q;
    }

    public void setupQuestion (Question q){
        editTextQuestion.setText(q.getIntitule());
        editTextAnswer1.setText(q.getPropositions().get(0));
        editTextAnswer2.setText(q.getPropositions().get(1));
        editTextAnswer3.setText(q.getPropositions().get(2));
        editTextAnswer4.setText(q.getPropositions().get(3));
        if (q.getPropositions().get(0).equals(q.getBonneReponse())) {
            radioButton1.setChecked(true);
        } if (q.getPropositions().get(1).equals(q.getBonneReponse())){
            radioButton2.setChecked(true);
        } if (q.getPropositions().get(2).equals(q.getBonneReponse())){
            radioButton3.setChecked(true);
        } if (q.getPropositions().get(3).equals(q.getBonneReponse())) {
            radioButton4.setChecked(true);
        }
    }

    public interface OnCreateListener {
        void createQuestion(Question q);
    }
}

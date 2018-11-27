package fr.diginamic.formation.monquizz.ui.fragments;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.utils.MPPointF;

import java.util.ArrayList;
import java.util.List;

import fr.diginamic.formation.monquizz.R;
import fr.diginamic.formation.monquizz.database.QuestionsDatabaseHelper;
import fr.diginamic.formation.monquizz.model.Question;
import fr.diginamic.formation.monquizz.ui.activities.AnswerActivity;
import fr.diginamic.formation.monquizz.ui.activities.MainActivity;
import fr.diginamic.formation.monquizz.ui.activities.QuestionsActivity;


public class ScoreFragment extends Fragment {

    private TextView showScoreUser;

    private PieChart chart;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_score, container, false);

        chart = rootView.findViewById(R.id.chart1);
        chart.setUsePercentValues(true);
        chart.getDescription().setEnabled(false);
        chart.setExtraOffsets(5, 10, 5, 5);

        chart.setDragDecelerationFrictionCoef(0.95f);

        chart.setDrawHoleEnabled(true);
        chart.setHoleColor(Color.WHITE);

        chart.setTransparentCircleColor(Color.WHITE);
        chart.setTransparentCircleAlpha(110);

        chart.setHoleRadius(10f);
        chart.setTransparentCircleRadius(61f);

        chart.setRotationAngle(0);
        chart.setRotationEnabled(true);

        chart.animateY(1400, Easing.EaseInOutQuad);

        Legend l = chart.getLegend();
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);
        l.setOrientation(Legend.LegendOrientation.VERTICAL);
        l.setDrawInside(false);
        l.setXEntrySpace(7f);
        l.setYEntrySpace(0f);
        l.setYOffset(0f);

        // entry label styling
        chart.setEntryLabelColor(Color.BLACK);
        chart.setEntryLabelTextSize(12f);

        updateChart();

        return rootView;
    }

    private void updateChart() {
        ArrayList<PieEntry> entries = new ArrayList<>();

        //TODO : Change your value here (Maybe a  call to the database)
        int[] listScore = getScore();
        int correctAnswersCount = listScore[0];
        int wrongAnswersCount = listScore[1];
        int unansweredQuestionCount = listScore[2];

        int total = correctAnswersCount + wrongAnswersCount + unansweredQuestionCount;

        ArrayList<PieEntry> questionEntries = new ArrayList<>();

        //TODO : set this values in string.xml for internationalization
        questionEntries.add(new PieEntry((float)correctAnswersCount/(float)(total),"Bonnes réponses"));
        questionEntries.add(new PieEntry((float)wrongAnswersCount/(float)(total),"Mauvaises réponses"));
        questionEntries.add(new PieEntry((float)unansweredQuestionCount/(float)(total),"A faire"));


        PieDataSet dataSet = new PieDataSet(questionEntries, "");

        dataSet.setSliceSpace(3f);
        dataSet.setIconsOffset(new MPPointF(0, 40));
        dataSet.setSelectionShift(5f);

        //TODO : Change your color to match your theme
        ArrayList<Integer> colors = new ArrayList<>();

        colors.add(Color.GREEN);
        colors.add(Color.RED);
        colors.add(Color.LTGRAY);

        dataSet.setColors(colors);

        PieData data = new PieData(dataSet);
        data.setValueFormatter(new PercentFormatter());
        data.setValueTextSize(11f);
        data.setValueTextColor(Color.BLACK);
        chart.setData(data);

        // undo all highlights
        chart.highlightValues(null);

        chart.invalidate();
    }

    private int[] getScore(){

        int goodAnswer = 0;
        int badAnswer = 0;
        int noReponse = 0;

        List<Question> questionList = QuestionsDatabaseHelper.getInstance(getActivity()).getAllQuestions();

        for (Question q : questionList){
            if(q.userAnswer == null) {
               noReponse++;
            } else {
                if (q.userAnswer.equals(q.bonneReponse)) {
                    goodAnswer++;
                } else {
                    badAnswer++;
                }
            }
        }

        return new int[]{goodAnswer, badAnswer, noReponse};
    }
}

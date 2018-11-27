package fr.diginamic.formation.monquizz.ui.thread;

import android.content.Context;
import android.os.AsyncTask;
import android.os.SystemClock;
import android.widget.ProgressBar;

public class TimeQuestionTask extends AsyncTask<Void, Integer, String> {

    int count = 0;

    public TimeListener listener;

    @Override
    protected void onPreExecute() {
        listener.starProgress();
    }

    @Override
    protected String doInBackground(Void... params) {
        while (count < 100){
            SystemClock.sleep(300);
            count++;
            publishProgress(count*1);
        }
        return "Time elapsed !";
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        if(count < 100){
            listener.progress(values);
        } else {
            listener.timeOut();
        }
    }

    public TimeQuestionTask(TimeListener listener) {
        super();
        this.listener = listener;
    }

    public interface TimeListener{
        void starProgress();
        void progress (Integer... count);
        void timeOut ();
    }
}

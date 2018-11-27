package fr.diginamic.formation.monquizz.api;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


import fr.diginamic.formation.monquizz.model.Question;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class APIClient {

        private final OkHttpClient client = new OkHttpClient();

        private static APIClient sInstance = new APIClient();

        public static APIClient getInstance(){
            return sInstance;
        }

        private static final String KEY_URL_DATA = "http://192.168.10.166:3000";
        private static final String KEY_QUESTION_ID = "id";
        private static final String KEY_QUESTION_TITLE = "title";
        private static final String KEY_ANSWER_1 = "answer_1";
        private static final String KEY_ANSWER_2 = "answer_2";
        private static final String KEY_ANSWER_3 = "answer_3";
        private static final String KEY_ANSWER_4 = "answer_4";
        private static final String KEY_CORRECT_ANSWER = "correct_answer";
        private static final String KEY_URL_IMG = "author_img_url";
        private static final String KEY_AUTHOR = "author";

        public void getQuestions(final APIResult<List<Question>> result) {

            Request request = null;
            //TODO : préparer la request
            request = new Request.Builder()
                    .url(KEY_URL_DATA + "/questions")
                    .build();

            client.newCall(request).enqueue(new Callback() {

                @Override
                public void onFailure(Call call, IOException e) {
                    result.onFailure(e);
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    List<Question> questions = new ArrayList<>();

                    // TODO : Lire les questions depuis la reponse et les ajouter à la liste
                    String getData = response.body().string();
                    try {
                        JSONArray jsonArr = new JSONArray(getData);
                        for(int i = 0; i < jsonArr.length(); i++){
                            JSONObject jsonObject = jsonArr.getJSONObject(i);
                            Question question = new Question(jsonObject.getString(KEY_QUESTION_TITLE),4);
                            question.addProposition(jsonObject.getString(KEY_ANSWER_1));
                            question.addProposition(jsonObject.getString(KEY_ANSWER_2));
                            question.addProposition(jsonObject.getString(KEY_ANSWER_3));
                            question.addProposition(jsonObject.getString(KEY_ANSWER_4));
                            question.bonneReponse = question.propositions.get(jsonObject.getInt(KEY_CORRECT_ANSWER)-1);
                            question.url_img = jsonObject.getString(KEY_URL_IMG);
                            question.author = jsonObject.getString(KEY_AUTHOR);
                            question.id = jsonObject.getInt(KEY_QUESTION_ID);

                            questions.add(question);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    result.OnSuccess(questions);
                }
            });
        }

        //TODO : Faire un update

        //TODO : Faire un delete

        //TODO : Faire un Create

    public void createQuestion(final APIResult<Question> result, Question q) {

        MediaType JSON_TYPE = MediaType.parse("application/json; charset=utf-8");
        final JSONObject jsonObject = new JSONObject();

        try {
            jsonObject.put(KEY_QUESTION_TITLE,q.getIntitule());
            jsonObject.put(KEY_ANSWER_1,q.getPropositions().get(0));
            jsonObject.put(KEY_ANSWER_2,q.getPropositions().get(1));
            jsonObject.put(KEY_ANSWER_3,q.getPropositions().get(2));
            jsonObject.put(KEY_ANSWER_4,q.getPropositions().get(3));
            jsonObject.put(KEY_CORRECT_ANSWER,q.getBonneReponse());
            jsonObject.put(KEY_URL_IMG,q.getUrl_img());
            jsonObject.put(KEY_AUTHOR,q.getAuthor());
            jsonObject.put(KEY_QUESTION_TITLE,q.id);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        Request request = null;
        //TODO : préparer la request
        request = new Request.Builder()
                .url(KEY_URL_DATA + "/questions").method("POST",RequestBody.create(JSON_TYPE,jsonObject.toString()))
                .build();

        client.newCall(request).enqueue(new Callback() {

            @Override
            public void onFailure(Call call, IOException e) {
                result.onFailure(e);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                try {
                        String getData = response.body().string();
                        JSONObject json = new JSONObject(getData);
                        Question newQuestion = new Question(json.getString(KEY_QUESTION_TITLE),4);
                        newQuestion.addProposition(json.getString(KEY_ANSWER_1));
                        newQuestion.addProposition(json.getString(KEY_ANSWER_2));
                        newQuestion.addProposition(json.getString(KEY_ANSWER_3));
                        newQuestion.addProposition(json.getString(KEY_ANSWER_4));
                        newQuestion.bonneReponse = newQuestion.propositions.get(jsonObject.getInt(KEY_CORRECT_ANSWER)-1);
                        newQuestion.url_img = json.getString(KEY_URL_IMG);
                        newQuestion.author = json.getString(KEY_AUTHOR);
                        newQuestion.id = json.getInt(KEY_QUESTION_ID);
                    result.OnSuccess(newQuestion);

                }
                 catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

         public interface APIResult<T> {
            void onFailure(IOException e);
            void OnSuccess(T object) throws IOException;
        }
}


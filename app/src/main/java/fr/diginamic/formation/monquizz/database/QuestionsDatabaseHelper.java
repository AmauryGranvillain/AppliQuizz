package fr.diginamic.formation.monquizz.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Debug;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import fr.diginamic.formation.monquizz.model.Question;

public class QuestionsDatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "questionsDatabase";
    private static final int DATABASE_VERSION = 9;

    private static final String TABLE_QUESTIONS = "questions";

    private static final String KEY_QUESTION_ID = "question_id";
    private static final String KEY_QUESTION_ENTITLED = "question_entitled";
    private static final String KEY_ANSWER_1 = "answer_1";
    private static final String KEY_ANSWER_2 = "answer_2";
    private static final String KEY_ANSWER_3 = "answer_3";
    private static final String KEY_ANSWER_4 = "answer_4";
    private static final String KEY_GOOD_ANSWER = "good_answer";
    private static final String KEY_USER_ANSWER = "user_answer";
    private static final String KEY_URL_IMG = "url_img";
    private static final String KEY_AUTHOR = "author";

    private static QuestionsDatabaseHelper instance;

    public static synchronized QuestionsDatabaseHelper getInstance(Context context){
        if( instance == null){
            instance = new QuestionsDatabaseHelper(context.getApplicationContext());
        }
        return instance;
    }

    private QuestionsDatabaseHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION );
    }

    @Override
    public void onCreate(SQLiteDatabase db){
        String CREATE_QUESTIONS_TABLE = "CREATE TABLE " + TABLE_QUESTIONS +
                "(" +
                    KEY_QUESTION_ID + " INTEGER PRIMARY KEY," +
                    KEY_QUESTION_ENTITLED + " VARCHAR(255)," +
                    KEY_ANSWER_1 + " VARCHAR(255)," +
                    KEY_ANSWER_2 + " VARCHAR(255)," +
                    KEY_ANSWER_3 + " VARCHAR(255)," +
                    KEY_ANSWER_4 + " VARCHAR(255)," +
                    KEY_GOOD_ANSWER + " VARCHAR(255)," +
                    KEY_USER_ANSWER + " VARCHAR(255)," +
                    KEY_URL_IMG + " VARCHAR(255)," +
                    KEY_AUTHOR + " VARCHAR(255)" +
                ")";
        db.execSQL(CREATE_QUESTIONS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if(oldVersion != newVersion){
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_QUESTIONS);
            onCreate(db);
        }
    }

    public List<Question> getAllQuestions(){
        List<Question> questions = new ArrayList<>();

        String QUESTIONS_SELECT_QUERY =
                String.format("SELECT * FROM " + TABLE_QUESTIONS);
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery(QUESTIONS_SELECT_QUERY, null);
        try{
            if(cursor.moveToFirst()){
                do {
                    Question newQuestion = new Question(cursor.getString(cursor.getColumnIndex(KEY_QUESTION_ENTITLED)), 4);
                    newQuestion.addProposition(cursor.getString(cursor.getColumnIndex(KEY_ANSWER_1)));
                    newQuestion.addProposition(cursor.getString(cursor.getColumnIndex(KEY_ANSWER_2)));
                    newQuestion.addProposition(cursor.getString(cursor.getColumnIndex(KEY_ANSWER_3)));
                    newQuestion.addProposition(cursor.getString(cursor.getColumnIndex(KEY_ANSWER_4)));
                    newQuestion.setBonneReponse(cursor.getString(cursor.getColumnIndex(KEY_GOOD_ANSWER)));
                    newQuestion.setUrl_img(cursor.getString(cursor.getColumnIndex(KEY_URL_IMG)));
                    newQuestion.setAuthor(cursor.getString(cursor.getColumnIndex(KEY_AUTHOR)));
                    newQuestion.id = cursor.getInt(cursor.getColumnIndex(KEY_QUESTION_ID));
                    newQuestion.userAnswer = cursor.getString(cursor.getColumnIndex(KEY_USER_ANSWER));
                    questions.add(newQuestion);
                } while(cursor.moveToNext());
            }
        } catch (Exception e){
            Log.d("DEBUG_DATABASE", "Error while trying to display list of questions");
        } finally {
            if(cursor != null && !cursor.isClosed()){
                cursor.close();
            }
        }
        return questions;
    }

    public void addQuestion(Question q){
       SQLiteDatabase db = getWritableDatabase();
       db.beginTransaction();
       try{
           ContentValues values = new ContentValues();
           values.put(KEY_QUESTION_ID,q.id);
           values.put(KEY_QUESTION_ENTITLED,q.getIntitule());
           values.put(KEY_ANSWER_1,q.getPropositions().get(0));
           values.put(KEY_ANSWER_2,q.getPropositions().get(1));
           values.put(KEY_ANSWER_3,q.getPropositions().get(2));
           values.put(KEY_ANSWER_4,q.getPropositions().get(3));
           values.put(KEY_GOOD_ANSWER,q.getBonneReponse());
           values.put(KEY_URL_IMG,q.getUrl_img());
           values.put(KEY_AUTHOR,q.getAuthor());
           values.put(KEY_QUESTION_ID,q.id);
           values.put(KEY_USER_ANSWER,q.userAnswer);
           db.insertOrThrow(TABLE_QUESTIONS, null, values);
           db.setTransactionSuccessful();
       } catch (Exception e){
           Log.d("DEBUG_DATABASE", "Error while trying to add question");
       } finally {
           db.endTransaction();
       }
     }

    public void updateQuestion(Question q){
        SQLiteDatabase db = getWritableDatabase();
        db.beginTransaction();
        try{
            ContentValues values = new ContentValues();
            values.put(KEY_QUESTION_ID,q.id);
            values.put(KEY_QUESTION_ENTITLED,q.getIntitule());
            values.put(KEY_ANSWER_1,q.getPropositions().get(0));
            values.put(KEY_ANSWER_2,q.getPropositions().get(1));
            values.put(KEY_ANSWER_3,q.getPropositions().get(2));
            values.put(KEY_ANSWER_4,q.getPropositions().get(3));
            values.put(KEY_GOOD_ANSWER,q.getBonneReponse());
            values.put(KEY_URL_IMG,q.getUrl_img());
            values.put(KEY_AUTHOR,q.getAuthor());
            db.update(TABLE_QUESTIONS, values, KEY_QUESTION_ID + "= ?", new String[]{String.valueOf(q.id)});
        } catch (Exception e){
            Log.d("DEBUG_DATABASE","Error while trying to add question");
        } finally {
            db.endTransaction();
        }
    }

    public void deleteQuestion(Question q){
        SQLiteDatabase db = getWritableDatabase();
        db.beginTransaction();
        try{
            db.delete(TABLE_QUESTIONS, KEY_QUESTION_ID + "= ?", new String[]{String.valueOf(q.id)});
        } catch (Exception e){
            Log.d("DEBUG_DATABASE", "Error while trying to delete all questions");
        } finally {
            db.endTransaction();
        }
    }

    public void synchroniseDatabaseQuestions(List<Question> serverQuestions) {

        List<Question> databaseQuestions = getAllQuestions();


        // Here we will choose if we need to add or to update the question return by the server
        for (Question serverQuestion: serverQuestions) {
            boolean found = false;
            for (Question dataBaseQuestion: databaseQuestions) {
                if (serverQuestion.id == dataBaseQuestion.id){
                    found =true;
                    break;
                }
            }

            if (found) {
                updateQuestion(serverQuestion);
            } else {
                addQuestion(serverQuestion);
            }
        }

        // Now we want to delete the question if thy are not on the server anymore
        for (Question dataBaseQuestion: databaseQuestions) {
            boolean found = false;
            for (Question serverQuestion: serverQuestions) {
                if (serverQuestion.id == dataBaseQuestion.id){
                    found =true;
                    break;
                }
            }

            if (!found) {
                deleteQuestion(dataBaseQuestion);
            }
        }
    }

    public void userAnswerUpgrade (Question q){
        SQLiteDatabase db = getWritableDatabase();
        db.beginTransaction();
        try{
            ContentValues values = new ContentValues();
            values.put(KEY_USER_ANSWER,q.userAnswer);
            db.update(TABLE_QUESTIONS, values, KEY_QUESTION_ID + "= ?", new String[]{String.valueOf(q.id)});
        } catch (Exception e){
            Log.d("DEBUG_DATABASE", "Error while trying to upload user answer");
        } finally {
            db.endTransaction();
        }
    }
}

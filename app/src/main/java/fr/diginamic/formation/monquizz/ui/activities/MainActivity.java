package fr.diginamic.formation.monquizz.ui.activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.List;

import fr.diginamic.formation.monquizz.R;
import fr.diginamic.formation.monquizz.model.Question;
import fr.diginamic.formation.monquizz.ui.adapters.QuestionListFragment;
import fr.diginamic.formation.monquizz.ui.fragments.CreateQuestion;
import fr.diginamic.formation.monquizz.ui.fragments.ScoreFragment;
import fr.diginamic.formation.monquizz.ui.fragments.SettingsFragment;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, QuestionListFragment.OnListQuestionListener, CreateQuestion.OnCreateListener {

    public static List<Question> list = new ArrayList<Question>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        if(savedInstanceState == null){
            QuestionListFragment fragment = new QuestionListFragment();
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container_layout, fragment)
                    .commit();
        }

        list.clear();

        Question question1 = new Question("Quelle est la capitale de la France ?", 4);

        question1.addProposition("Paris");
        question1.addProposition("Londres");
        question1.addProposition("Rome");
        question1.addProposition("Madrid");
        question1.setBonneReponse("Paris");

        list.add(question1);

        Question question2 = new Question("Combien de paupières ont les poissons ?", 4);

        question2.addProposition("1");
        question2.addProposition("3");
        question2.addProposition("0");
        question2.addProposition("2");
        question2.setBonneReponse("0");

        list.add(question2);


    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            SettingsFragment fragment = new SettingsFragment();
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container_layout, fragment)
                    .commit();
        }
        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_game) {
            QuestionListFragment fragment = new QuestionListFragment();
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container_layout, fragment)
                    .commit();
        } else if (id == R.id.nav_score) {
            ScoreFragment fragment = new ScoreFragment();
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container_layout, fragment)
                    .commit();
        } else if (id == R.id.nav_create_question) {
            CreateQuestion fragment = new CreateQuestion();
            fragment.listener = this;
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container_layout, fragment)
                    .commit();
        } else if (id == R.id.nav_setting) {
            SettingsFragment fragment = new SettingsFragment();
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container_layout, fragment)
                    .commit();
        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onListFragmentInteraction(Question item) {
        Intent intent = new Intent(MainActivity.this, QuestionsActivity.class);
        intent.putExtra("item",item);
        startActivity(intent);
    }

    @Override
    public void createQuestion(Question q) {
        getSupportFragmentManager().beginTransaction().replace(R.id.container_layout, new QuestionListFragment()).commit();
        list.add(q);
    }
}

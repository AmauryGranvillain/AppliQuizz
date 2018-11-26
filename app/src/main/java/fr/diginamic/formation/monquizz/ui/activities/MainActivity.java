package fr.diginamic.formation.monquizz.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.io.IOException;
import java.util.List;

import fr.diginamic.formation.monquizz.R;
import fr.diginamic.formation.monquizz.api.APIClient;
import fr.diginamic.formation.monquizz.database.QuestionsDatabaseHelper;
import fr.diginamic.formation.monquizz.model.Question;
import fr.diginamic.formation.monquizz.ui.adapters.QuestionListFragment;
import fr.diginamic.formation.monquizz.ui.fragments.CreateQuestionFragment;
import fr.diginamic.formation.monquizz.ui.fragments.ScoreFragment;
import fr.diginamic.formation.monquizz.ui.fragments.SettingsFragment;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, QuestionListFragment.OnListQuestionListener, CreateQuestionFragment.OnCreateListener, APIClient.APIResult {

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

        APIClient.getInstance().getQuestions(new APIClient.APIResult<List<Question>>() {
            @Override
            public void onFailure(IOException e) {

            }

            @Override
            public void OnSuccess(List<Question> object) throws IOException {
                for(int i =0; i < object.size(); i++){
                    QuestionsDatabaseHelper.getInstance(MainActivity.this).addQuestion(object.get(i));
                }
            }
        });
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
            CreateQuestionFragment fragment = new CreateQuestionFragment();
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
    public void onLongClickInteraction(Question item) {
        CreateQuestionFragment fragment = new CreateQuestionFragment();
        fragment.listener = this;
        fragment.setQuestion(item);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container_layout, fragment)
                .commit();
    }

    @Override
    public void createQuestion(Question q) {
        getSupportFragmentManager().beginTransaction().replace(R.id.container_layout, new QuestionListFragment()).commit();
        QuestionsDatabaseHelper databaseHelper = QuestionsDatabaseHelper.getInstance(this);
        databaseHelper.addQuestion(q);
    }

    @Override
    public void onFailure(IOException e) {

    }

    @Override
    public void OnSuccess(Object object) throws IOException {

    }
}

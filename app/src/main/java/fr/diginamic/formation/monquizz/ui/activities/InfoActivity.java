package fr.diginamic.formation.monquizz.ui.activities;

import android.content.SharedPreferences;
import android.icu.text.IDNA;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import fr.diginamic.formation.monquizz.R;

public class InfoActivity extends AppCompatActivity {

    boolean check;
    CheckBox checkboxSaved;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        checkboxSaved = findViewById(R.id.check_save_answer);

        final SharedPreferences mSettings = PreferenceManager.getDefaultSharedPreferences(this);

        checkboxSaved.setChecked(mSettings.getBoolean("check",true));

        checkboxSaved.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                SharedPreferences.Editor editor = mSettings.edit();
                editor.putBoolean("check",isChecked);
                editor.apply();
            }
        });
    }
}

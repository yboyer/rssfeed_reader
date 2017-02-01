package fr.unicaen.info.dnr.rssapp;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.CompoundButton;
import android.widget.Switch;

public class PreferencesActivity extends AppCompatActivity {
    final static String PREFERENCE_KEY = "rss_settings";
    final static String AUTOUPDATE_KEY = "autoupdate";
    final static boolean AUTOUPDATE_DEFAULT = true;
    final static String WIFIUPDATE_KEY = "wifiupdate";
    final static boolean WIFIUPDATE_DEFAULT = true;
    final static String DATAUPDATE_KEY = "dataupdate";
    final static boolean DATAUPDATE_DEFAULT = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preferences);

        SharedPreferences data = getSharedPreferences(PREFERENCE_KEY, 0);

        final SharedPreferences.Editor editeur = data.edit();

        Switch autoupdateSwitch = (Switch) findViewById(R.id.autoupdate_switch);
        autoupdateSwitch.setChecked(data.getBoolean(AUTOUPDATE_KEY, AUTOUPDATE_DEFAULT));
        autoupdateSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                editeur.putBoolean(AUTOUPDATE_KEY, isChecked).apply();
            }
        });

        Switch wifiSwitch = (Switch) findViewById(R.id.wifi_switch);
        wifiSwitch.setChecked(data.getBoolean(WIFIUPDATE_KEY, WIFIUPDATE_DEFAULT));
        wifiSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                editeur.putBoolean(WIFIUPDATE_KEY, isChecked).apply();
            }
        });

        Switch dataSwitch = (Switch) findViewById(R.id.data_switch);
        dataSwitch.setChecked(data.getBoolean(DATAUPDATE_KEY, DATAUPDATE_DEFAULT));
        dataSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                editeur.putBoolean(DATAUPDATE_KEY, isChecked).apply();
            }
        });
    }
}

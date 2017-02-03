package fr.unicaen.info.dnr.rssapp;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.CompoundButton;
import android.widget.Switch;

public class PreferencesActivity extends AppCompatActivity {
    public final static String PREFERENCE_KEY = "rss_settings";
    public final static String AUTOUPDATE_KEY = "autoupdate";
    public final static boolean AUTOUPDATE_DEFAULT = true;
    public final static String WIFIUPDATE_KEY = "wifiupdate";
    public final static boolean WIFIUPDATE_DEFAULT = true;
    public final static String DATAUPDATE_KEY = "dataupdate";
    public final static boolean DATAUPDATE_DEFAULT = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preferences);

        SharedPreferences data = getSharedPreferences(PREFERENCE_KEY, 0);

        final SharedPreferences.Editor editeur = data.edit();

        final Switch wifiSwitch = (Switch) findViewById(R.id.wifi_switch);
        wifiSwitch.setChecked(data.getBoolean(WIFIUPDATE_KEY, WIFIUPDATE_DEFAULT));
        wifiSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                editeur.putBoolean(WIFIUPDATE_KEY, isChecked).apply();
            }
        });

        final Switch dataSwitch = (Switch) findViewById(R.id.data_switch);
        dataSwitch.setChecked(data.getBoolean(DATAUPDATE_KEY, DATAUPDATE_DEFAULT));
        dataSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                editeur.putBoolean(DATAUPDATE_KEY, isChecked).apply();
            }
        });

        Switch autoupdateSwitch = (Switch) findViewById(R.id.autoupdate_switch);
        boolean autoupdate = data.getBoolean(AUTOUPDATE_KEY, AUTOUPDATE_DEFAULT);
        autoupdateSwitch.setChecked(autoupdate);
        wifiSwitch.setEnabled(autoupdate);
        dataSwitch.setEnabled(autoupdate);
        autoupdateSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                wifiSwitch.setEnabled(isChecked);
                dataSwitch.setEnabled(isChecked);
                editeur.putBoolean(AUTOUPDATE_KEY, isChecked).apply();
            }
        });
    }
}

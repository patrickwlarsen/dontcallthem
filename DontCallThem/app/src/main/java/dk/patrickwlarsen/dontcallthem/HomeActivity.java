package dk.patrickwlarsen.dontcallthem;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.logging.Logger;

import dk.patrickwlarsen.dontcallthem.adapter.SimpleContactAdapter;
import dk.patrickwlarsen.dontcallthem.contacts.ContactsFactory;
import dk.patrickwlarsen.dontcallthem.contacts.SimpleContact;
import dk.patrickwlarsen.dontcallthem.settings.SharedPrefsSettings;


public class HomeActivity extends ActionBarActivity implements View.OnClickListener {

    private SharedPrefsSettings settings;
    private ContactsFactory contactsFactory;

    private TextView tvStatus;
    private Button btnEnable, btnDisable;
    private ListView lvContacts;

    private SimpleContactAdapter contactListAdapter;

    private ArrayList<SimpleContact> contacts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            if(extras.getBoolean("EXIT"))
                finish();
        }
        setContentView(R.layout.activity_splash);
        initialize();
        initializeVars();
    }

    private void initialize() {
        settings = new SharedPrefsSettings(this);
        contactsFactory = new ContactsFactory(this);
        contacts = contactsFactory.getContacts();
    }

    private void initializeVars() {
        tvStatus = (TextView) findViewById(R.id.tv_status);
        btnEnable = (Button) findViewById(R.id.btn_enable);
        btnDisable = (Button) findViewById(R.id.btn_disable);
        lvContacts = (ListView) findViewById(R.id.lv_contacts);

        btnEnable.setOnClickListener(this);
        btnDisable.setOnClickListener(this);

        lvContacts.setAdapter(new SimpleContactAdapter(getApplicationContext(), contacts));

        tvStatus.setText(getString(settings.isCallConfirmationEnabled() ? R.string.txt_confirmation_enabled : R.string.txt_confirmation_disabled));
    }

    private void setConfirmationEnabled(boolean enabled) {
        settings.changeConfirmationEnabled(enabled);
        tvStatus.setText(getString(settings.isCallConfirmationEnabled() ? R.string.txt_confirmation_enabled : R.string.txt_confirmation_disabled));
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.btn_enable:
                setConfirmationEnabled(true);
                break;
            case R.id.btn_disable:
                setConfirmationEnabled(false);
                break;
            default:
                break;
        }
    }
}

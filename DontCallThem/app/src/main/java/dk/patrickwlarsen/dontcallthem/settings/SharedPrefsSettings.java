package dk.patrickwlarsen.dontcallthem.settings;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.ArrayList;

import dk.patrickwlarsen.dontcallthem.contacts.SimpleContact;

/**
 * Created by Patrick W Larsen on 09-11-2015.
 */
public class SharedPrefsSettings {

    private final String KEY_CONFIRMATION_ENABLED = "dk.patrickwlarsen.dontcallthem.confirmationenabled";
    private final String KEY_CONTACT_PREFIX = "dk.patrickwlarsen.dontcallthem.contact.";
    private final String KEY_CONTACT_PREFIX_CONFIRMED_ONCE = "dk.patrickwlarsen.dontcallthem.contact.confirmedonce.";

    private Context context;
    private SharedPreferences prefs;
    private boolean confirmationEnabled;

    public SharedPrefsSettings(Context context) {
        this.context = context;
        this.prefs = this.context.getSharedPreferences("dk.patrickwlarsen.dontcallthem", Context.MODE_PRIVATE);
        this.confirmationEnabled = prefs.getBoolean(KEY_CONFIRMATION_ENABLED, false);
    }

    public void changeConfirmationEnabled(boolean confirmationEnabled) {
        this.confirmationEnabled = confirmationEnabled;
        prefs.edit().putBoolean(KEY_CONFIRMATION_ENABLED, confirmationEnabled).commit();
    }

    public boolean isCallConfirmationEnabled() {
        return this.confirmationEnabled;
    }

    public void getCallConfirmationEnabledForContacts(ArrayList<SimpleContact> contacts) {
        for(SimpleContact sc : contacts) {
            sc.setCallConfirmationEnabled(prefs.getBoolean(KEY_CONTACT_PREFIX + sc.getName() + sc.getNumber(), false));
            sc.setConfirmedOnce(prefs.getBoolean(KEY_CONTACT_PREFIX_CONFIRMED_ONCE + sc.getName() + sc.getNumber(), false));
        }
    }

    public void updateCallConfirmationEnabledForContacts(ArrayList<SimpleContact> contacts) {
        for(SimpleContact sc : contacts) {
            prefs.edit().putBoolean(KEY_CONTACT_PREFIX + sc.getName() + sc.getNumber(), sc.isCallConfirmationEnabled()).commit();
            prefs.edit().putBoolean(KEY_CONTACT_PREFIX_CONFIRMED_ONCE + sc.getName() + sc.getNumber(), sc.isConfirmedOnce()).commit();
        }
    }
}

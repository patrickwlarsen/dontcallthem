package dk.patrickwlarsen.dontcallthem.interceptor;

import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.widget.Toast;

import java.util.ArrayList;

import dk.patrickwlarsen.dontcallthem.ShowOutgoingCallInterceptedActivity;
import dk.patrickwlarsen.dontcallthem.contacts.ContactsFactory;
import dk.patrickwlarsen.dontcallthem.contacts.SimpleContact;
import dk.patrickwlarsen.dontcallthem.settings.SharedPrefsSettings;

/**
 * Created by Patrick W Larsen on 09-11-2015.
 */
public class OutgoingCallInterceptor extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        SharedPrefsSettings settings = new SharedPrefsSettings(context);
        if(settings.isCallConfirmationEnabled()) {
            final String number = intent.getStringExtra(Intent.EXTRA_PHONE_NUMBER);
            ArrayList<SimpleContact> contacts = new ContactsFactory(context).getContacts();
            for(SimpleContact sc : contacts) {
                if(sc.getNumber().equals(number)) {
                    if (sc.isCallConfirmationEnabled()) {
                        if(!sc.isConfirmedOnce()) {
                            Intent newActivityIntent = new Intent(context, ShowOutgoingCallInterceptedActivity.class);
                            newActivityIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            newActivityIntent.putExtra("scName", sc.getName());
                            newActivityIntent.putExtra("scNumber", sc.getNumber());
                            context.startActivity(newActivityIntent);
                            setResultData(null);
                        } else {
                            sc.setConfirmedOnce(false);
                            settings.updateCallConfirmationEnabledForContacts(contacts);
                        }
                    }
                    break;
                }
            }
        }
    }
}
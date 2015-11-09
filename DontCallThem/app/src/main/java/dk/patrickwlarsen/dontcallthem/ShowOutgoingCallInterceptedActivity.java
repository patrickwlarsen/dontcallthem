package dk.patrickwlarsen.dontcallthem;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;

import java.util.ArrayList;

import dk.patrickwlarsen.dontcallthem.contacts.ContactsFactory;
import dk.patrickwlarsen.dontcallthem.contacts.SimpleContact;
import dk.patrickwlarsen.dontcallthem.interceptor.OutgoingCallInterceptor;
import dk.patrickwlarsen.dontcallthem.settings.SharedPrefsSettings;

/**
 * Created by Patrick W Larsen on 09-11-2015.
 */
public class ShowOutgoingCallInterceptedActivity extends Activity {

    private ArrayList<SimpleContact> contacts;
    private SharedPrefsSettings settings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            SimpleContact sc = new SimpleContact(extras.getString("scName"), extras.getString("scNumber"));
            showConfirmCallDialog(this, sc);
            contacts = new ContactsFactory(this).getContacts();
            settings = new SharedPrefsSettings(this);
        } else {
            finish();
        }
    }

    private void callConfirmed(SimpleContact sc) {
        Intent makeCall = new Intent(Intent.ACTION_CALL);
        makeCall.setData(Uri.parse("tel:" + sc.getNumber()));
        for(SimpleContact sc2 : contacts) {
            if(sc.getName().equals(sc2.getName()) &&
                    sc.getNumber().equals(sc2.getNumber())) {
                sc2.setConfirmedOnce(true);
                settings.updateCallConfirmationEnabledForContacts(contacts);
            }
        }
        startActivity(makeCall);
        finish();
    }


    private void callNotConfirmed() {
        Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra("EXIT", true);
        startActivity(intent);
        finish();
    }

    private void showConfirmCallDialog(Context context, final SimpleContact sc) {
        DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which){
                    case DialogInterface.BUTTON_POSITIVE:
                        callConfirmed(sc);
                        break;

                    case DialogInterface.BUTTON_NEGATIVE:
                        callNotConfirmed();
                        break;
                }
            }
        };

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage("Are you sure you want to call " + sc.getName() + " (" + sc.getNumber() + ")?").setPositiveButton("Yes", dialogClickListener)
                .setNegativeButton("No", dialogClickListener).show();
    }
}
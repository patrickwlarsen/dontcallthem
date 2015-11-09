package dk.patrickwlarsen.dontcallthem.contacts;

import android.content.Context;
import android.database.Cursor;
import android.provider.ContactsContract;

import java.util.ArrayList;

import dk.patrickwlarsen.dontcallthem.settings.SharedPrefsSettings;

/**
 * Created by Patrick W Larsen on 09-11-2015.
 */
public class ContactsFactory {

    private SharedPrefsSettings settings;
    private Context context;
    private Cursor cursor;

    public ContactsFactory(Context context) {
        this.context = context;
        this.settings = new SharedPrefsSettings(context);
    }

    public ArrayList<SimpleContact> getContacts() {
        ArrayList<SimpleContact> contacts = new ArrayList<SimpleContact>();
        cursor = context.getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                new String[] {
                        ContactsContract.CommonDataKinds.Phone._ID,
                        ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME,
                        ContactsContract.CommonDataKinds.Phone.NUMBER},
                null,
                null,
                null);
        while(cursor.moveToNext()) {
            contacts.add(new SimpleContact(cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME)),
                    cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER))));
        }
        if(!contacts.isEmpty()) {
            settings.getCallConfirmationEnabledForContacts(contacts);
        }

        return contacts;
    }
}

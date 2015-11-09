package dk.patrickwlarsen.dontcallthem.adapter;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RadioButton;
import android.widget.TextView;

import java.util.ArrayList;

import dk.patrickwlarsen.dontcallthem.R;
import dk.patrickwlarsen.dontcallthem.contacts.SimpleContact;
import dk.patrickwlarsen.dontcallthem.settings.SharedPrefsSettings;

/**
 * Created by Patrick W Larsen on 09-11-2015.
 */
public class SimpleContactAdapter extends BaseAdapter {

    private ArrayList<SimpleContact> contacts;
    private Context context;
    private SharedPrefsSettings settings;

    public SimpleContactAdapter(Context context, ArrayList<SimpleContact> contacts) {
        this.contacts = contacts;
        this.context = context;
        this.settings = new SharedPrefsSettings(this.context);
    }

    @Override
    public int getCount() {
        return contacts.size();
    }

    @Override
    public Object getItem(int position) {
        return contacts.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = inflater.inflate(R.layout.list_item_contact, null);
        TextView tvName = (TextView) convertView.findViewById(R.id.li_tv_name);
        TextView tvNumber = (TextView) convertView.findViewById(R.id.li_tv_number);
        RadioButton rbEnabled = (RadioButton) convertView.findViewById(R.id.li_rb_enabled);

        tvName.setText(contacts.get(position).getName());
        tvNumber.setText(contacts.get(position).getNumber());
        rbEnabled.setChecked(contacts.get(position).isCallConfirmationEnabled());
        rbEnabled.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                contacts.get(position).setCallConfirmationEnabled(!contacts.get(position).isCallConfirmationEnabled());
                settings.updateCallConfirmationEnabledForContacts(contacts);
                ((RadioButton)v).setChecked(contacts.get(position).isCallConfirmationEnabled());
            }
        });

        return convertView;
    }
}

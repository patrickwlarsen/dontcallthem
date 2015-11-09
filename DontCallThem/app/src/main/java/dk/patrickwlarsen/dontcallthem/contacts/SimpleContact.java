package dk.patrickwlarsen.dontcallthem.contacts;

/**
 * Created by Patrick W Larsen on 09-11-2015.
 */
public class SimpleContact {

    private String name;
    private String number;
    private boolean callConfirmationEnabled;
    private boolean confirmedOnce;

    public SimpleContact(String name, String number) {
        this.name = name;
        this.number = number;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getNumber() {
        return this.number;
    }

    public void setCallConfirmationEnabled(boolean callConfirmationEnabled) {
        this.callConfirmationEnabled = callConfirmationEnabled;
    }

    public boolean isCallConfirmationEnabled() {
        return this.callConfirmationEnabled;
    }

    public void setConfirmedOnce(boolean confirmedOnce) {
        this.confirmedOnce = confirmedOnce;
    }

    public boolean isConfirmedOnce() {
        return this.confirmedOnce;
    }
}

package fi.linna.erajorma.model;

import java.io.Serializable;

public class Information implements Serializable {

    public String[][] information;

    public Information(String[][] information) {
        this.information = information;
    }

    @SuppressWarnings("ReassignedVariable")
    public void addInformation(String[] information) {
        int oldLength = this.information.length;
        int newLength = oldLength + 1;

        String[][] oldInformation = this.information;
        String[][] newInformation = new String[newLength][];

        newInformation[oldLength] = information;
        for (int i = 0; i < oldLength; i++) {
            newInformation[i] = oldInformation[i];
        }

        this.information = newInformation;
    }
}

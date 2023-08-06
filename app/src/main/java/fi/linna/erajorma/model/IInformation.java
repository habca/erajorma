package fi.linna.erajorma.model;

import java.io.Serializable;

public interface IInformation extends Serializable {
    /**
     * Käytetään kun halutaan listata olion tiedot käyttöliittymään.
     * @return olion tietueet merkkijonoiksi muutettuna
     */
    Information getInformation();
}

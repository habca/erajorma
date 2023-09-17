package fi.linna.erajorma.model;

import java.io.Serializable;

public interface IKarttamerkki extends Serializable, Comparable<IKarttamerkki> {

    String getName();

    /**
     * ~WGS84 -koordinaatti pohjoista leveyttä asteina.
     * @return [asteet, minuutit, [sekunnit]]
     */
    double[] getLatitude();

    /**
     * ~WGS84 -koordinaatti itäistä pituutta asteina.
     * @return [asteet, minuutit, [sekunnit]]
     */
    double[] getLongitude();

    /**
     * ETRS-TM35FIN -koordinaatti pohjoista leveyttä metreinä.
     * @return etäisyys päiväntasaajaan metreinä.
     */
    double getNorth();

    /**
     * ETRS-TM35FIN -koordinaatti itäistä pituutta metreinä.
     * @return etäisyys keskimeridiaanista 27 pituuspiiri on 500 000 m.
     */
    double getEast();

    /**
     * UTM -koordinaatin projektiokaistan tunniste.
     * @return Numero-osa 6 pituuspiirin välein, kirjainosa leveyspiirin mukaan.
     */
    String getZone();

    /**
     * UTM -koordinaatti pohjoista leveyttä metreinä.
     * @return etäisyys projektiokaistan (UTM zone) leveyspiiristä.
     */
    double getNorthing();

    /**
     * UTM -koordinaatti itäistä pituutta metreinä.
     * @return etäisyys projektiokaistan (UTM zone) pituuspiiristä.
     */
    double getEasting();
}

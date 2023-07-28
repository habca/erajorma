package fi.linna.erajorma;

public interface IKarttamerkki {

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
     * ETRS-TM35FIN east coordinate as meters.
     * @return etäisyys keskimeridiaanista 27 pituuspiiri on 500 000 m.
     */
    double getEast();
}

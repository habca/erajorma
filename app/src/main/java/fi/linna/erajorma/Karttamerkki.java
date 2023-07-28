package fi.linna.erajorma;

public class Karttamerkki implements IKarttamerkki {
    private double[] lat;
    private double[] lon;
    private double N;
    private double E;

    public Karttamerkki(double[] lat, double[] lon) {
        double latitude = Koordinaatit.DmsToDegrees(lat);
        double longitude = Koordinaatit.DmsToDegrees(lon);
        double[] meters = Projektiokaavat.degreesToMeters(latitude, longitude);

        this.lat = lat;
        this.lon = lon;
        this.N = meters[0];
        this.E = meters[1];
    }

    public Karttamerkki(double[] lat, double[] lon, double N, double E) {
        this.lat = lat;
        this.lon = lon;
        this.N = N;
        this.E = E;
    }

    @Override
    public double[] getLatitude() {
        return lat;
    }

    @Override
    public double[] getLongitude() {
        return lon;
    }

    @Override
    public double getNorth() {
        return N;
    }

    @Override
    public double getEast() {
        return E;
    }
}

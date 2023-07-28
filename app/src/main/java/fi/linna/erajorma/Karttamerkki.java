package fi.linna.erajorma;

import java.util.Comparator;

public class Karttamerkki implements IKarttamerkki, Comparable<IKarttamerkki> {
    private String name;
    private double[] lat;
    private double[] lon;
    private double N;
    private double E;

    public Karttamerkki(String name, double[] lat, double[] lon) {
        double latitude = Koordinaatit.DmsToDegrees(lat);
        double longitude = Koordinaatit.DmsToDegrees(lon);
        double[] meters = Projektiokaavat.degreesToMeters(latitude, longitude);

        this.name = name;
        this.lat = lat;
        this.lon = lon;
        this.N = meters[0];
        this.E = meters[1];
    }

    public Karttamerkki(String name, double[] lat, double[] lon, double N, double E) {
        this.name = name;
        this.lat = lat;
        this.lon = lon;
        this.N = N;
        this.E = E;
    }

    public String getName() {
        return name;
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

    @Override
    public String toString() {
        String latitude = String.format("lat: %.0f\u00B0 %.4f\u2032", lat[0], lat[1]);
        String longitude = String.format("lon: %.0f\u00B0 %.4f\u2032", lon[0], lon[1]);
        return String.format("%s %s %s", name, latitude, longitude);
    }

    @Override
    public int compareTo(IKarttamerkki other) {
        return comparator().compare(this.getName(), other.getName());
    }

    public static Comparator<String> comparator() {
        return (str1, str2) -> {
            byte[] xs = str1.getBytes();
            byte[] ys = str2.getBytes();

            for (int i = 0; i < xs.length && i < ys.length; i++) {
                if (xs[i] != ys[i]) return xs[i] - ys[i];
            }
            return ys.length - xs.length;
        };
    }
}

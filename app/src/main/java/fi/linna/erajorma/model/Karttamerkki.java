package fi.linna.erajorma.model;

import java.io.Serializable;
import java.util.Comparator;

public class Karttamerkki implements IKarttamerkki, Serializable {
    public String name;
    public double[] lat;
    public double[] lon;
    public double N;
    public double E;
    public String UTM_zone;
    public double UTM_N;
    public double UTM_E;

    public Karttamerkki(String name, double[] lat, double[] lon) {
        double latitude = Koordinaatit.dmsToDegrees(lat);
        double longitude = Koordinaatit.dmsToDegrees(lon);
        double[] meters = Projektiokaavat.degreesToMeters(latitude, longitude);
        double[] utm = Projektiokaavat.degreesToUtm(latitude, longitude);
        String UTM_zone = Projektiokaavat.getUtmZone(latitude, longitude);

        this.name = name;
        this.lat = lat;
        this.lon = lon;
        this.N = meters[0];
        this.E = meters[1];
        this.UTM_N = utm[0];
        this.UTM_E = utm[1];
        this.UTM_zone = UTM_zone;
    }

    public Karttamerkki(String name, double[] lat, double[] lon, double N, double E) {
        this(name, lat, lon);
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
    public String getZone() {
        return UTM_zone;
    }

    @Override
    public double getNorthing() {
        return UTM_N;
    }

    @Override
    public double getEasting() {
        return UTM_E;
    }

    @Override
    public double getDistance(IKarttamerkki marker) {
        double lat1 = Koordinaatit.dmsToDegrees(getLatitude());
        double lon1 = Koordinaatit.dmsToDegrees(getLongitude());

        double lat2 = Koordinaatit.dmsToDegrees(marker.getLatitude());
        double lon2 = Koordinaatit.dmsToDegrees(marker.getLongitude());

        double distance = Koordinaatit.degreesToDistance(lat1, lon1, lat2, lon2);
        return distance;
    }

    @Override
    public double getAzimuth(IKarttamerkki marker) {
        double lat1 = Koordinaatit.dmsToDegrees(getLatitude());
        double lon1 = Koordinaatit.dmsToDegrees(getLongitude());

        double lat2 = Koordinaatit.dmsToDegrees(marker.getLatitude());
        double lon2 = Koordinaatit.dmsToDegrees(marker.getLongitude());

        double azimuth = Koordinaatit.degreesToDirection(lat1, lon1, lat2, lon2);
        return azimuth;
    }

    @Override
    public double getDistance25000(IKarttamerkki marker) {
        double distance_km = getDistance(marker);
        double map_scale = 1.0 / 25000.0;
        return Koordinaatit.distanceOnMap(distance_km, map_scale);
    }

    @Override
    public double getDistance50000(IKarttamerkki marker) {
        double distance_km = getDistance(marker);
        double map_scale = 1.0 / 50000.0;
        return Koordinaatit.distanceOnMap(distance_km, map_scale);
    }

    @Override
    public double getDistance100000(IKarttamerkki marker) {
        double distance_km = getDistance(marker);
        double map_scale = 1.0 / 100000.0;
        return Koordinaatit.distanceOnMap(distance_km, map_scale);
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

    @SuppressWarnings("ReassignedVariable")
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

package fi.linna.erajorma.model;

@SuppressWarnings("SpellCheckingInspection")
public class Koordinaatit {

    /**
     * Convert degrees decimal part to minutes and seconds.
     * @return [degrees, minutes, seconds]
     */
    public static double[] degreesToDms(double degrees) {
        double mmm = minutesToSeconds(degrees);
        double sss = minutesToSeconds(mmm);
        return new double[] { degrees, mmm, sss };
    }

    /**
     * Convert degrees decimal part to minutes.
     * @param degrees degrees
     * @return minutes
     */
    private static double minutesToSeconds(double degrees) {
        return 60 * (degrees - (int) degrees);
    }

    /**
     * Convert minutes and seconds to degrees decimal part.
     * @param dms [degrees, minutes, seconds]
     * @return degrees
     */
    public static double dmsToDegrees(double... dms) {
        if (dms.length == 3) {
            final double mmm = dms[1] + dms[2] / 60;
            return dms[0] + mmm / 60;
        } else if (dms.length == 2){
            return dms[0] + dms[1] / 60;
        } else if (dms.length == 1) {
            return dms[0];
        } else {
            throw new RuntimeException("Number of parameters wrong.");
        }
    }

    /**
     * Haversine of a spherical triangle.
     * @param lat1 1st latitude in degrees
     * @param lon1 1st longitude in degrees
     * @param lat2 2nd latitude in degrees
     * @param lon2 2nd longitude in degrees
     * @return distance between coordinates in kilometers
     */
    @SuppressWarnings("ReassignedVariable")
    public static double degreesToDistance(double lat1, double lon1, double lat2, double lon2) {
        lat1 = Math.toRadians(lat1);
        lon1 = Math.toRadians(lon1);
        lat2 = Math.toRadians(lat2);
        lon2 = Math.toRadians(lon2);

        double a = Math.pow(Math.sin((lat2 - lat1) * 0.5), 2);
        double b = Math.cos(lat1) * Math.cos(lat2) * Math.pow(Math.sin((lon2 - lon1) * 0.5), 2);
        double c = 2 * Math.asin(Math.sqrt(a + b));
        double r = 6371;
        return c * r;
    }

    /**
     * Hypothenuse of a rectangular triangle.
     * @param N1 1st latitude in meters
     * @param E1 1st longitude in meters
     * @param N2 2nd latitude in meters
     * @param E2 2nd longitude in meters
     * @return distance between coordinates in kilometers
     */
    @SuppressWarnings("ReassignedVariable")
    public static double metersToDistance(double N1, double E1, double N2, double E2) {
        double y = N2 - N1;
        double x = E2 - E1;
        double h = Math.sqrt(Math.pow(x, 2.0) + Math.pow(y, 2.0));
        return h / 1000.0;
    }

    /**
     * Bearing between two WGS84 coordinates in degrees.
     * @param lat1 1st latitude in degrees
     * @param lon1 1st longitude in degrees
     * @param lat2 2nd latitude in degrees
     * @param lon2 2nd longitude in degrees
     * @return angle between coordinates in degrees [-180, 180]
     */
    @SuppressWarnings("ReassignedVariable")
    public static double degreesToBearing(double lat1, double lon1, double lat2, double lon2) {
        lat1 = Math.toRadians(lat1);
        lon1 = Math.toRadians(lon1);
        lat2 = Math.toRadians(lat2);
        lon2 = Math.toRadians(lon2);

        double a = Math.cos(lat2) * Math.sin(lon2 - lon1);
        double b = Math.cos(lat1) * Math.sin(lat2) - Math.sin(lat1) * Math.cos(lat2) * Math.cos(lon2 - lon1);
        double c = Math.atan2(a, b);
        return Math.toDegrees(c);
    }

    /*
     * Bearing between two ETRS-TM35FIN coordinates in degrees.
     * @param N1 1st latitude in meters
     * @param E1 1st longitude in meters
     * @param N2 2nd latitude in meters
     * @param E2 2nd longitude in meters
     * @return angle between coordinates in degrees [-180, 180]
     */
    public static double metersToBearing(double N1, double E1, double N2, double E2) {
        double y = N2 - N1;
        double x = E2 - E1;
        double angle = Math.atan2(x, y);
        return Math.toDegrees(angle);
    }

    /**
     * Azimuth between two WGS84 coordinates in degrees.
     * @param lat1 1st latitude in degrees
     * @param lon1 1st longitude in degrees
     * @param lat2 2nd latitude in degrees
     * @param lon2 2nd longitude in degrees
     * @return angle between WGS84 coordinates in degrees [0, 360]
     */
    public static double degreesToDirection(double lat1, double lon1, double lat2, double lon2) {
        double bearing = degreesToBearing(lat1, lon1, lat2, lon2);
        double azimuth = (bearing + 360) % 360;
        return azimuth;
    }

    public static double metersToDirection(double N1, double E1, double N2, double E2) {
        double bearing = metersToBearing(N1, E1, N2, E2);
        double azimuth = (bearing + 360) % 360;
        return azimuth;
    }

    public static double distanceOnMap(double distance_km, double map_scale)
    {
        double distance_cm = distance_km * map_scale * 100000.0;
        return distance_cm;
    }

    public static double distanceOnNature(double distance_cm, double map_scale) {
        double distance_km = distance_cm / map_scale / 100000.0;
        return distance_km;
    }
}

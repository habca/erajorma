package fi.linna.erajorma.model;

public class Koordinaatit {

    /**
     * Convert degrees decimal part to minutes and seconds.
     * @return [degrees, minutes, seconds]
     */
    public static double[] degreesToDms(double degrees) {
        final double mmm = minutesToSeconds(degrees);
        final double sss = minutesToSeconds(mmm);
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
    public static double degreesToDistance(double lat1, double lon1, double lat2, double lon2) {
        lat1 = Math.toRadians(lat1);
        lon1 = Math.toRadians(lon1);
        lat2 = Math.toRadians(lat2);
        lon2 = Math.toRadians(lon2);

        final double a = Math.pow(Math.sin((lat2 - lat1) * 0.5), 2);
        final double b = Math.cos(lat1) * Math.cos(lat2) * Math.pow(Math.sin((lon2 - lon1) * 0.5), 2);
        final double c = 2 * Math.asin(Math.sqrt(a + b));
        final double r = 6371;
        return c * r;
    }

    /**
     * Azimuth between two coordinates in degrees.
     * @param lat1 1st latitude in degrees
     * @param lon1 1st longitude in degrees
     * @param lat2 2nd latitude in degrees
     * @param lon2 2nd longitude in degrees
     * @return angle between coordinates in degrees
     */
    public static double degreesToDirection(double lat1, double lon1, double lat2, double lon2) {
        lat1 = Math.toRadians(lat1);
        lon1 = Math.toRadians(lon1);
        lat2 = Math.toRadians(lat2);
        lon2 = Math.toRadians(lon2);

        final double a = Math.sin(lon2 - lon1) * Math.cos(lat2);
        final double b = Math.cos(lat1) * Math.sin(lat2) - Math.sin(lat1) * Math.cos(lat2) * Math.cos(lon2 - lon1);
        final double c = Math.atan2(a, b);
        return (Math.toDegrees(c) + 360) % 360;
    }
}

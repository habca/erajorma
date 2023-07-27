package fi.linna.erajorma;

public class Koordinaatit {

    /**
     * Convert degrees decimal part to minutes and seconds.
     * @return [degrees, minutes, seconds]
     */
    public static double[] DegreesToDms(double degrees) {
        double mmm = MinutesToSeconds(degrees);
        double sss = MinutesToSeconds(mmm);
        return new double[] { Math.floor(degrees), Math.floor(mmm), Math.round(sss) };
    }

    /**
     * Convert degrees decimal part to minutes.
     * @param degrees
     * @return minutes
     */
    private static double MinutesToSeconds(double degrees) {
        return 60 * (degrees - (int) degrees);
    }

    /**
     * Convert minutes and seconds to degrees decimal part.
     * @param dms [degrees, minutes, seconds]
     * @return degrees
     */
    public static double DmsToDegrees(double[] dms) {
        if (dms.length == 3) {
            double mmm = dms[1] + dms[2] / 60;
            double ddd = dms[0] + mmm / 60;
            return ddd;
        } else {
            double ddd = dms[0] + dms[1] / 60;
            return ddd;
        }
    }
}

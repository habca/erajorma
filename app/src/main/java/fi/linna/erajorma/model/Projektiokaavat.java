package fi.linna.erajorma.model;

public class Projektiokaavat {

    /**
     * From WGS84 to ETRS-TM35FIN conversion according to the JHS 197 EUREF-FIN.
     */
    public static double[] degreesToMeters(double latitude, double longitude) {

        double f = 1 / 298.257222101;
        double e_toiseen = (2.0 * f) - Math.pow(f, 2);

        double lambda_nolla = 0.471238898;
        double k_nolla = 0.9996;
        double E_nolla = 500000;

        double a = 6378137;
        double n = f / (2.0 - f);
        double A1 = (a / (1.0 + n)) * (1.0 + (Math.pow(n, 2) / 4.0) + (Math.pow(n, 4) / 64.0));

        double h1_pilkku = 1.0 / 2.0 * Math.pow(n, 1) - 2.0 / 3.0 * Math.pow(n, 2) + 5.0 / 16.0 * Math.pow(n, 3) + 41.0 / 180.0 * Math.pow(n, 4);
        double h2_pilkku = 13.0 / 48.0 * Math.pow(n, 2) - 3.0 / 5.0 * Math.pow(n, 3) + 557.0 / 1440.0 * Math.pow(n, 4);
        double h3_pilkku = 61.0 / 240.0 * Math.pow(n, 3) - 103.0 / 140.0 * Math.pow(n, 4);
        double h4_pilkku = 49561.0 / 161280.0 * Math.pow(n, 4);

        double fii = Math.toRadians(latitude) ;
        double lambda = Math.toRadians(longitude);

        double Q_pilkku = Projektiokaavat.arcsinh(Math.tan(fii));
        double Q2_pilkku = Projektiokaavat.arctanh(Math.sqrt(e_toiseen) * Math.sin(fii));
        double Q = Q_pilkku - Math.sqrt(e_toiseen) * Q2_pilkku;

        double l = lambda - lambda_nolla;
        double beeta = Math.atan(Math.sinh(Q));

        double eeta_pilkku = Projektiokaavat.arctanh(Math.cos(beeta) * Math.sin(l));
        double zeeta_pilkku = Math.asin(Math.sin(beeta) / Projektiokaavat.sech(eeta_pilkku));

        double zeeta1 = h1_pilkku * Math.sin(2.0 * zeeta_pilkku) * Math.cosh(2.0 * eeta_pilkku);
        double zeeta2 = h2_pilkku * Math.sin(4.0 * zeeta_pilkku) * Math.cosh(4.0 * eeta_pilkku);
        double zeeta3 = h3_pilkku * Math.sin(6.0 * zeeta_pilkku) * Math.cosh(6.0 * eeta_pilkku);
        double zeeta4 = h4_pilkku * Math.sin(8.0 * zeeta_pilkku) * Math.cosh(8.0 * eeta_pilkku);

        double eeta1 = h1_pilkku * Math.cos(2.0 * zeeta_pilkku) * Math.sinh(2.0 * eeta_pilkku);
        double eeta2 = h2_pilkku * Math.cos(4.0 * zeeta_pilkku) * Math.sinh(4.0 * eeta_pilkku);
        double eeta3 = h3_pilkku * Math.cos(6.0 * zeeta_pilkku) * Math.sinh(6.0 * eeta_pilkku);
        double eeta4 = h4_pilkku * Math.cos(8.0 * zeeta_pilkku) * Math.sinh(8.0 * eeta_pilkku);

        double zeeta = zeeta_pilkku + zeeta1 + zeeta2 + zeeta3 + zeeta4;
        double eeta = eeta_pilkku + eeta1 + eeta2 + eeta3 + eeta4;

        double N = A1 * zeeta * k_nolla;
        double E = A1 * eeta * k_nolla + E_nolla;

        return new double[] { N, E };
    }

    public static double arcsinh(double x) {
        return Math.log(x + Math.sqrt(x * x + 1.0));
    }

    public static double arctanh(double x) {
        return 0.5 * Math.log((1.0 + x) / (1.0 - x));
    }

    public static double sech(double x) {
        return 1.0 / Math.cosh(x);
    }
}

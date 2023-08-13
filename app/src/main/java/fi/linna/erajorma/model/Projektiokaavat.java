package fi.linna.erajorma.model;

public class Projektiokaavat {

    /**
     * From WGS84 to ETRS-TM35FIN conversion according to the JHS 197 EUREF-FIN.
     */
    public static double[] degreesToMeters(double latitude, double longitude) {

        // GRS80-vertausellipsoidin parametrit:

        double a = 6378137;
        double f = 1 / 298.257222101;

        // Karttaprojektion parametrit:

        double k_nolla = 0.9996;
        double lambda_nolla = Math.toRadians(27); // 27 E
        double E_nolla = 500000; // m

        // Apusuureet:

        double n = f / (2.0 - f);
        double A1 = (a / (1.0 + n)) * (1.0 + (Math.pow(n, 2) / 4.0) + (Math.pow(n, 4) / 64.0));
        double e_toiseen = (2.0 * f) - Math.pow(f, 2);

        double h1_pilkku = 1.0 / 2.0 * Math.pow(n, 1) - 2.0 / 3.0 * Math.pow(n, 2) + 5.0 / 16.0 * Math.pow(n, 3) + 41.0 / 180.0 * Math.pow(n, 4);
        double h2_pilkku = 13.0 / 48.0 * Math.pow(n, 2) - 3.0 / 5.0 * Math.pow(n, 3) + 557.0 / 1440.0 * Math.pow(n, 4);
        double h3_pilkku = 61.0 / 240.0 * Math.pow(n, 3) - 103.0 / 140.0 * Math.pow(n, 4);
        double h4_pilkku = 49561.0 / 161280.0 * Math.pow(n, 4);

        // Tasokoordinaateista geodeettisiksi koordinaateiksi

        double fii = Math.toRadians(latitude);
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

    /**
     * From ETRS-TM35FIN to WGS84 conversion according to the JHS 197 EUREF-FIN.
     */
    public static double[] metersToDegrees(double north, double east) {

        // GRS80-vertausellipsoidin parametrit:

        double a = 6378137; // m
        double f = 1 / 298.257222101;

        // Karttaprojektion parametrit:

        double k_nolla = 0.9996;
        double lambda_nolla = Math.toRadians(27); // 27 E
        double E_nolla = 500000; // m

        // Apusuureet:

        double n = f / (2.0 - f);
        double A1 = (a / (1.0 + n)) * (1.0 + (Math.pow(n, 2) / 4.0) + (Math.pow(n, 4) / 64.0));
        double e_toiseen = (2.0 * f) - Math.pow(f, 2);

        double h1 = 1.0 / 2.0 * n - 2.0 / 3.0 * Math.pow(n, 2.0) + 37.0 / 96.0 * Math.pow(n, 3.0) - 1.0 / 360.0 * Math.pow(n, 4.0);
        double h2 = 1.0 / 48.0 * Math.pow(n, 2.0) + 1.0 / 15.0 * Math.pow(n, 3.0) - 437.0 / 1440.0 * Math.pow(n, 4.0);
        double h3 = 17.0 / 480 * Math.pow(n, 3.0) - 37.0 / 840.0 * Math.pow(n, 4.0);
        double h4 = 4397.0 / 161280.0 * Math.pow(n, 4.0);

        // Tasokoordinaateista geodeettisiksi koordinaateiksi

        double N = north;
        double E = east;

        double zeeta = N / (A1 * k_nolla);
        double eeta = (E - E_nolla) / (A1 * k_nolla);

        double zeeta1_pilkku = h1 * Math.sin(2.0 * zeeta) * Math.cosh(2.0 * eeta);
        double zeeta2_pilkku = h2 * Math.sin(4.0 * zeeta) * Math.cosh(4.0 * eeta);
        double zeeta3_pilkku = h3 * Math.sin(6.0 * zeeta) * Math.cosh(6.0 * eeta);
        double zeeta4_pilkku = h4 * Math.sin(8.0 * zeeta) * Math.cosh(8.0 * eeta);

        double eeta1_pilkku = h1 * Math.cos(2.0 * zeeta) * Math.sinh(2.0 * eeta);
        double eeta2_pilkku = h2 * Math.cos(4.0 * zeeta) * Math.sinh(4.0 * eeta);
        double eeta3_pilkku = h3 * Math.cos(6.0 * zeeta) * Math.sinh(6.0 * eeta);
        double eeta4_pilkku = h4 * Math.cos(8.0 * zeeta) * Math.sinh(8.0 * eeta);

        double zeeta_pilkku = zeeta - zeeta1_pilkku - zeeta2_pilkku - zeeta3_pilkku - zeeta4_pilkku;
        double eeta_pilkku = eeta - eeta1_pilkku - eeta2_pilkku - eeta3_pilkku - eeta4_pilkku;

        double beeta = Math.asin(Projektiokaavat.sech(eeta_pilkku) * Math.sin(zeeta_pilkku));
        double l = Math.asin(Math.tanh(eeta_pilkku) / Math.cos(beeta));

        double Q = Projektiokaavat.arcsinh(Math.tan(beeta));
        double Q_pilkku = Q + Math.sqrt(e_toiseen) * Projektiokaavat.arctanh(Math.sqrt(e_toiseen) * Math.tanh(Q));

        // Käytännössä riittää kolme iteraatiokierrosta.

        for (int i = 0; i < 4; i++) {
            Q_pilkku = Q + Math.sqrt(e_toiseen) * Projektiokaavat.arctanh(Math.sqrt(e_toiseen) * Math.tanh(Q_pilkku));
        }

        double fii = Math.atan((Math.sinh(Q_pilkku))); // rad
        double lambda = lambda_nolla + l; // rad

        double latitude = Math.toDegrees(fii);
        double longitude = Math.toDegrees(lambda);

        return new double[] { latitude, longitude };
    }

    public static double arcsinh(double x) {
        return Math.log(x + Math.sqrt(Math.pow(x, 2.0) + 1.0));
    }

    public static double arctanh(double x) {
        return 0.5 * Math.log((1.0 + x) / (1.0 - x));
    }

    public static double sech(double x) {
        return 1.0 / Math.cosh(x);
    }

    /**
     * From WGS84 to UTM conversion.
     */
    public static double CalculateUTMNorthing(double latitude, double longitude) {

        int utmZone = (int) Math.floor((longitude + 180.0) / 6.0) + 1;

        int hemisphere = latitude < 0 ? -1 : 1;

        double CentralMeridian = (utmZone - 1.0) * 6.0 - 180.0 + 3.0;

        double ScaleFactor = 0.9996;

        double EquatorialRadius = 6378137;

        double EccentricitySquared = 0.00669438;

        double N = EquatorialRadius / Math.sqrt(1.0 - EccentricitySquared * Math.pow(Math.sin(latitude * 3.14159265358979 / 180.0), 2.0));

        double T = Math.pow(Math.tan(latitude * 3.14159265358979 / 180.0), 2.0);

        double C = EccentricitySquared * Math.pow(Math.cos(latitude * 3.14159265358979 / 180.0), 2.0);

        double A = Math.cos(latitude * 3.14159265358979 / 180.0) * (longitude - CentralMeridian) * 3.14159265358979 / 180.0;

        double M = EquatorialRadius * ((1.0 - EccentricitySquared / 4.0 - 3.0 * Math.pow(EccentricitySquared, 2.0) / 64.0 - 5.0 * Math.pow(EccentricitySquared, 3.0) / 256.0) * latitude * 3.14159265358979 / 180.0 - (3.0 * EccentricitySquared / 8.0 + 3.0 * Math.pow(EccentricitySquared, 2.0) / 32.0 + 45.0 * Math.pow(EccentricitySquared, 3.0) / 1024.0) * Math.sin(2.0 * latitude * 3.14159265358979 / 180.0) + (15.0 * Math.pow(EccentricitySquared, 2.0) / 256.0 + 45.0 * Math.pow(EccentricitySquared, 3.0) / 1024.0) * Math.sin(4.0 * latitude * 3.14159265358979 / 180.0) - (35.0 * Math.pow(EccentricitySquared, 3.0) / 3072.0) * Math.sin(6.0 * latitude * 3.14159265358979 / 180.0));

        double UTMScaleFactor = 0.9996;

        double UTMFalseNorthing = hemisphere == 1 ? 0.0 : 10000000.0;

        double UTMNorthing = UTMScaleFactor * (M + N * Math.tan(latitude * 3.14159265358979 / 180.0) * (Math.pow(A, 2.0) / 2.0 + (5.0 - T + 9.0 * C + 4.0 * Math.pow(C, 2.0)) * Math.pow(A, 4.0) / 24.0 + (61.0 - 58.0 * T + Math.pow(T, 2.0) + 600.0 * C - 330.0 * EccentricitySquared) * Math.pow(A, 6.0) / 720.0)) + UTMFalseNorthing;

        return UTMNorthing;
    }

    /**
     * From WGS84 to UTM conversion.
     */
    public static double CalculateUTMEasting(double latitude, double longitude) {

        int utmZone = (int) Math.floor((longitude + 180.0) / 6.0) + 1;

        double CentralMeridian = (utmZone - 1.0) * 6.0 - 180.0 + 3.0;

        double ScaleFactor = 0.9996;

        double EquatorialRadius = 6378137;

        double EccentricitySquared = 0.00669438;

        double N = EquatorialRadius / Math.sqrt(1.0 - EccentricitySquared * Math.pow(Math.sin(latitude * 3.14159265358979 / 180.0), 2.0));

        double T = Math.pow(Math.tan(latitude * 3.14159265358979 / 180.0), 2.0);

        double C = EccentricitySquared * Math.pow(Math.cos(latitude * 3.14159265358979 / 180.0), 2.0);

        double A = Math.cos(latitude * 3.14159265358979 / 180.0) * (longitude - CentralMeridian) * 3.14159265358979 / 180.0;

        double M = EquatorialRadius * ((1.0 - EccentricitySquared / 4.0 - 3.0 * Math.pow(EccentricitySquared, 2.0) / 64.0 - 5.0 * Math.pow(EccentricitySquared, 3.0) / 256.0) * latitude * 3.14159265358979 / 180.0 - (3.0 * EccentricitySquared / 8.0 + 3.0 * Math.pow(EccentricitySquared, 2.0) / 32.0 + 45.0 * Math.pow(EccentricitySquared, 3.0) / 1024.0) * Math.sin(2.0 * latitude * 3.14159265358979 / 180.0) + (15.0 * Math.pow(EccentricitySquared, 2.0) / 256.0 + 45.0 * Math.pow(EccentricitySquared, 3.0) / 1024.0) * Math.sin(4.0 * latitude * 3.14159265358979 / 180.0) - (35.0 * Math.pow(EccentricitySquared, 3.0) / 3072.0) * Math.sin(6.0 * latitude * 3.14159265358979 / 180.0));

        double UTMEasting = ScaleFactor * N * (A + (1.0 - T + C) * Math.pow(A, 3.0) / 6.0 + (5.0 - 18.0 * T + Math.pow(T, 2.0) + 72.0 * C - 58.0 * EccentricitySquared) * Math.pow(A, 5.0) / 120.0) + 500000.0;

        return UTMEasting;
    }

    /**
     * From WGS84 to UTM conversion.
     */
    public static String CalculateUTMZone(double latitude, double longitude) {

        int utmZone = (int) Math.floor((longitude + 180.0) / 6.0) + 1;

        String LatitudeBand;

        if (latitude >= 0) {
            if (latitude >= 0 && latitude < 8) {
                LatitudeBand = "N";
            } else if (latitude >= 8 && latitude < 16) {
                LatitudeBand = "P";
            } else if (latitude >= 16 && latitude < 24) {
                LatitudeBand = "Q";
            } else if (latitude >= 24 && latitude < 32) {
                LatitudeBand = "R";
            } else if (latitude >= 32 && latitude < 40) {
                LatitudeBand = "S";
            } else if (latitude >= 40 && latitude < 48) {
                LatitudeBand = "T";
            } else if (latitude >= 48 && latitude < 56) {
                LatitudeBand = "U";
            } else if (latitude >= 56 && latitude < 64) {
                LatitudeBand = "V";
            } else if (latitude >= 64 && latitude < 72) {
                LatitudeBand = "W";
            } else {
                LatitudeBand = "X";
            }
        } else {
            if (latitude < 0 && latitude >= -8) {
                LatitudeBand = "M";
            } else if (latitude < -8 && latitude >= -16) {
                LatitudeBand = "L";
            } else if (latitude < -16 && latitude >= -24) {
                LatitudeBand = "K";
            } else if (latitude < -24 && latitude >= -32) {
                LatitudeBand = "J";
            } else if (latitude < -32 && latitude >= -40) {
                LatitudeBand = "H";
            } else if (latitude < -40 && latitude >= -48) {
                LatitudeBand = "G";
            } else if (latitude < -48 && latitude >= -56) {
                LatitudeBand = "F";
            } else if (latitude < -56 && latitude >= -64) {
                LatitudeBand = "E";
            } else if (latitude < -64 && latitude >= -72) {
                LatitudeBand = "D";
            } else {
                LatitudeBand = "C";
            }
        }

        return utmZone + LatitudeBand;
    }
}

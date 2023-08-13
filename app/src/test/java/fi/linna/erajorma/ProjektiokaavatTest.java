package fi.linna.erajorma;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import static org.junit.Assert.assertEquals;

import androidx.annotation.NonNull;

import fi.linna.erajorma.model.Koordinaatit;
import fi.linna.erajorma.model.Projektiokaavat;

public class ProjektiokaavatTest {

    @Test
    public void degreesToMetersTest() {

        // GRS80-vertausellipsoidin parametrit:

        double a = 6378137;
        double f = 1 / 298.257222101;

        // Karttaprojektion parametrit:

        double k_nolla = 0.9996;
        double lambda_nolla = Math.toRadians(27); // 27 E
        double E_nolla = 500000; // m

        // Apusuureet:

        double n = f / (2.0 - f);
        assertEquals(0.001679220395, n, 0.000000000001);

        double A1 = (a / (1.0 + n)) * (1.0 + (Math.pow(n, 2) / 4.0) + (Math.pow(n, 4) / 64.0));
        assertEquals(6367449.145771, A1, 0.000001);

        double e_toiseen = (2.0 * f) - Math.pow(f, 2);
        assertEquals(0.006694380023, e_toiseen, 0.000000000001);

        double h1_pilkku = 1.0 / 2.0 * Math.pow(n, 1) - 2.0 / 3.0 * Math.pow(n, 2) + 5.0 / 16.0 * Math.pow(n, 3) + 41.0 / 180.0 * Math.pow(n, 4);
        assertEquals(0.000837731824734, h1_pilkku, 0.000000000000001);

        double h2_pilkku = 13.0 / 48.0 * Math.pow(n, 2) - 3.0 / 5.0 * Math.pow(n, 3) + 557.0 / 1440.0 * Math.pow(n, 4);
        assertEquals(0.000000760852779, h2_pilkku, 0.000000000000001);

        double h3_pilkku = 61.0 / 240.0 * Math.pow(n, 3) - 103.0 / 140.0 * Math.pow(n, 4);
        assertEquals(0.000000001197638, h3_pilkku, 0.000000000000001);

        double h4_pilkku = 49561.0 / 161280.0 * Math.pow(n, 4);
        assertEquals(0.000000000002443, h4_pilkku, 0.000000000000001);

        // Tasokoordinaateista geodeettisiksi koordinaateiksi

        double fii = 1.053918934088933; // rad
        double lambda = 0.346415337012956; // rad

        double Q_pilkku = Projektiokaavat.arcsinh(Math.tan(fii));
        assertEquals(1.330479626914950, Q_pilkku, 0.000000000000002);

        double Q2_pilkku = Projektiokaavat.arctanh(Math.sqrt(e_toiseen) * Math.sin(fii));
        assertEquals(0.071251194462820, Q2_pilkku, 0.000000000000001);

        double Q = Q_pilkku - Math.sqrt(e_toiseen) * Q2_pilkku;
        assertEquals(1.324649911823168, Q, 0.000000000000001);

        double l = lambda - lambda_nolla;
        assertEquals(-0.124823561025513, l, 0.000000000000001);

        double beeta = Math.atan(Math.sinh(Q));
        assertEquals(1.051030767588720, beeta, 0.000000000000001);

        double eeta_pilkku = Projektiokaavat.arctanh(Math.cos(beeta) * Math.sin(l));
        assertEquals(-0.061915076853879, eeta_pilkku, 0.000000000000001);

        double zeeta_pilkku = Math.asin(Math.sin(beeta) / Projektiokaavat.sech(eeta_pilkku));
        assertEquals(1.054391184619376, zeeta_pilkku, 0.000000000000001);

        double zeeta1 = h1_pilkku * Math.sin(2.0 * zeeta_pilkku) * Math.cosh(2.0 * eeta_pilkku);
        assertEquals(0.000724918454654, zeeta1, 0.000000000000001);

        double zeeta2 = h2_pilkku * Math.sin(4.0 * zeeta_pilkku) * Math.cosh(4.0 * eeta_pilkku);
        assertEquals(-0.000000690230193, zeeta2, 0.000000000000001);

        double zeeta3 = h3_pilkku * Math.sin(6.0 * zeeta_pilkku) * Math.cosh(6.0 * eeta_pilkku);
        assertEquals(0.000000000055283, zeeta3, 0.000000000000001);

        double zeeta4 = h4_pilkku * Math.sin(8.0 * zeeta_pilkku) * Math.cosh(8.0 * eeta_pilkku);
        assertEquals(0.000000000002298, zeeta4, 0.000000000000001);

        double eeta1 = h1_pilkku * Math.cos(2.0 * zeeta_pilkku) * Math.sinh(2.0 * eeta_pilkku);
        assertEquals(0.000053291297517, eeta1, 0.000000000000001);

        double eeta2 = h2_pilkku * Math.cos(4.0 * zeeta_pilkku) * Math.sinh(4.0 * eeta_pilkku);
        assertEquals(0.000000090400064, eeta2, 0.000000000000001);

        double eeta3 = h3_pilkku * Math.cos(6.0 * zeeta_pilkku) * Math.sinh(6.0 * eeta_pilkku);
        assertEquals(-0.000000000454791, eeta3, 0.000000000000001);

        double eeta4 = h4_pilkku * Math.cos(8.0 * zeeta_pilkku) * Math.sinh(8.0 * eeta_pilkku);
        assertEquals(0.000000000000692, eeta4, 0.000000000000001);

        double zeeta = zeeta_pilkku + zeeta1 + zeeta2 + zeeta3 + zeeta4;
        assertEquals(1.055115412901418, zeeta, 0.000000000000001);

        double eeta = eeta_pilkku + eeta1 + eeta2 + eeta3 + eeta4;
        assertEquals(-0.061861695610397, eeta, 0.000000000000001);

        double N = A1 * zeeta * k_nolla;
        assertEquals(6715706.37708, N, 0.00001);

        double E = A1 * eeta * k_nolla + E_nolla;
        assertEquals(106256.35961, E, 0.00001);

        // Lähtötiedot: Pisteen G4 (Geta) EUREF-FIN-koordinaatit:

        double[] latitude = Koordinaatit.degreesToDms(Math.toDegrees(fii));
        assertEquals(60, Math.floor(latitude[0]), 0.00001);
        assertEquals(23, Math.floor(latitude[1]), 0.00001);
        assertEquals(6.38474, latitude[2], 0.00001);
        assertEquals(60.3851068722, Koordinaatit.dmsToDegrees(new double[] {
                Math.floor(latitude[0]), Math.floor(latitude[1]), latitude[2],
        }), 0.0000000001);

        double[] longitude = Koordinaatit.degreesToDms(Math.toDegrees(lambda));
        assertEquals(19, Math.floor(longitude[0]), 0.00001);
        assertEquals(50, Math.floor(longitude[1]), 0.00001);
        assertEquals(53.29237, longitude[2], 0.00001);
        assertEquals(19.84813676944, Koordinaatit.dmsToDegrees(new double[] {
                Math.floor(longitude[0]), Math.floor(longitude[1]), longitude[2],
        }), 0.00000000001);
    }

    @Test
    public void metersToDegreesTest() {

        // GRS80-vertausellipsoidin parametrit:

        double a = 6378137; // m
        double f = 1 / 298.257222101;

        // Karttaprojektion parametrit:

        double k_nolla = 0.9996;
        double lambda_nolla = Math.toRadians(27); // 27 E
        double E_nolla = 500000; // m

        // Apusuureet:

        double n = f / (2.0 - f);
        assertEquals(0.001679220395, n, 0.000000000001);

        double A1 = (a / (1.0 + n)) * (1.0 + (Math.pow(n, 2) / 4.0) + (Math.pow(n, 4) / 64.0));
        assertEquals(6367449.145771, A1, 0.000001);

        double e_toiseen = (2.0 * f) - Math.pow(f, 2);
        assertEquals(0.006694380023, e_toiseen, 0.000000000001);

        double h1 = 1.0 / 2.0 * n - 2.0 / 3.0 * Math.pow(n, 2.0) + 37.0 / 96.0 * Math.pow(n, 3.0) - 1.0 / 360.0 * Math.pow(n, 4.0);
        assertEquals(0.000837732168164, h1, 0.000000000000001);

        double h2 = 1.0 / 48.0 * Math.pow(n, 2.0) + 1.0 / 15.0 * Math.pow(n, 3.0) - 437.0 / 1440.0 * Math.pow(n, 4.0);
        assertEquals(0.000000059058696, h2, 0.000000000000001);

        double h3 = 17.0 / 480 * Math.pow(n, 3.0) - 37.0 / 840.0 * Math.pow(n, 4.0);
        assertEquals(0.000000000167349, h3, 0.000000000000001);

        double h4 = 4397.0 / 161280.0 * Math.pow(n, 4.0);
        assertEquals(0.000000000000217, h4, 0.000000000000001);

        // Tasokoordinaateista geodeettisiksi koordinaateiksi

        double N = 6715706.37705;
        double E = 106256.35958;

        double zeeta = N / (A1 * k_nolla);
        assertEquals(1.055115412897463, zeeta, 0.000000000000001);

        double eeta = (E - E_nolla) / (A1 * k_nolla);
        assertEquals(-0.061861695615107, eeta, 0.000000000000001);

        double zeeta1_pilkku = h1 * Math.sin(2.0 * zeeta) * Math.cosh(2.0 * eeta);
        assertEquals(0.000724281930916, zeeta1_pilkku, 0.000000000000001);

        double zeeta2_pilkku = h2 * Math.sin(4.0 * zeeta) * Math.cosh(4.0 * eeta);
        assertEquals(-0.000000053657596, zeeta2_pilkku, 0.000000000000001);

        double zeeta3_pilkku = h3 * Math.sin(6.0 * zeeta) * Math.cosh(6.0 * eeta);
        assertEquals(0.000000000008501, zeeta3_pilkku, 0.000000000000001);

        double zeeta4_pilkku = h4 * Math.sin(8.0 * zeeta) * Math.cosh(8.0 * eeta);
        assertEquals(0.000000000000203, zeeta4_pilkku, 0.000000000000001);

        double eeta1_pilkku = h1 * Math.cos(2.0 * zeeta) * Math.sinh(2.0 * eeta);
        assertEquals(0.000053374333729, eeta1_pilkku, 0.000000000000001);

        double eeta2_pilkku = h2 * Math.cos(4.0 * zeeta) * Math.sinh(4.0 * eeta);
        assertEquals(0.000000006973167, eeta2_pilkku, 0.000000000000001);

        double eeta3_pilkku = h3 * Math.cos(6.0 * zeeta) * Math.sinh(6.0 * eeta);
        assertEquals(-0.000000000063479, eeta3_pilkku, 0.000000000000001);

        double eeta4_pilkku = h4 * Math.cos(8.0 * zeeta) * Math.sinh(8.0 * eeta);
        assertEquals(0.000000000000062, eeta4_pilkku, 0.000000000000001);

        double zeeta_pilkku = zeeta - zeeta1_pilkku - zeeta2_pilkku - zeeta3_pilkku - zeeta4_pilkku;
        assertEquals(1.054391184615438, zeeta_pilkku, 0.000000000000001);

        double eeta_pilkku = eeta - eeta1_pilkku - eeta2_pilkku - eeta3_pilkku - eeta4_pilkku;
        assertEquals(-0.061915076858586, eeta_pilkku, 0.000000000000001);

        double beeta = Math.asin(Projektiokaavat.sech(eeta_pilkku) * Math.sin(zeeta_pilkku));
        assertEquals(1.051030767584305, beeta, 0.000000000000001);

        double l = Math.asin(Math.tanh(eeta_pilkku) / Math.cos(beeta));
        assertEquals(-0.124823561034060, l, 0.000000000000001);

        double Q = Projektiokaavat.arcsinh(Math.tan(beeta));
        assertEquals(1.324649911814278, Q, 0.000000000000001);

        double Q_pilkku = Q + Math.sqrt(e_toiseen) * Projektiokaavat.arctanh(Math.sqrt(e_toiseen) * Math.tanh(Q));
        assertEquals(1.330469999534920, Q_pilkku, 0.000000000000001);

        // Käytännössä riittää kolme iteraatiokierrosta.

        double[] it = new double[] {
                1.330479611087242,
                1.330479626880053,
                1.330479626906003,
                1.330479626906045,
        };

        for (int i = 0; i < 4; i++) {
            Q_pilkku = Q + Math.sqrt(e_toiseen) * Projektiokaavat.arctanh(Math.sqrt(e_toiseen) * Math.tanh(Q_pilkku));
            assertEquals(it[i], Q_pilkku, 0.000000000000001);
        }

        double fii = Math.atan((Math.sinh(Q_pilkku))); // rad
        assertEquals(1.053918934084532, fii, 0.000000000000001);

        double lambda = lambda_nolla + l; // rad
        assertEquals(0.346415337004409, lambda, 0.000000000000001);

        // Lähtötiedot: Pisteen G4 (Geta) EUREF-FIN-koordinaatit:

        double[] latitude = Koordinaatit.degreesToDms(Math.toDegrees(fii));
        assertEquals(60, Math.floor(latitude[0]), 0.000001);
        assertEquals(23, Math.floor(latitude[1]), 0.000001);
        assertEquals(6.384739, latitude[2], 0.000001);

        double[] longitude = Koordinaatit.degreesToDms(Math.toDegrees(lambda));
        assertEquals(19, Math.floor(longitude[0]), 0.000001);
        assertEquals(50, Math.floor(longitude[1]), 0.000001);
        assertEquals(53.292368, longitude[2], 0.000001);
    }

    @RunWith(Parameterized.class)
    public static class DegreesToMetersTest {

        private static double EPSILON = 0.1;

        private double[] input;

        @Parameterized.Parameters
        public static double[][] data() {
            return new double[][] {
                    { 60.3851068722, 19.84813676944, 6715706.37708, 106256.35961 },
                    { 68.411994, 27.472200, 7588873.45, 519388.56 },
                    { 61.000563, 25.766045, 6763478.58, 433264.43 },
            };
        }

        public DegreesToMetersTest(@NonNull double[] input) {
            this.input = input;
        }

        @Test
        public void degreesToMetersTest() {
            double[] meters = Projektiokaavat.degreesToMeters(input[0], input[1]);

            assertEquals(input[2], meters[0], EPSILON);
            assertEquals(input[3], meters[1], EPSILON);
        }

        @Test
        public void metersToDegreesTest() {
            double[] degrees = Projektiokaavat.metersToDegrees(input[2], input[3]);

            assertEquals(input[0], degrees[0], EPSILON);
            assertEquals(input[1], degrees[1], EPSILON);
        }
    }
}

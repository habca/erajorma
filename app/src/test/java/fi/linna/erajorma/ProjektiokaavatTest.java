package fi.linna.erajorma;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ProjektiokaavatTest {

    @Test
    public void projectionFormulaTest() {
        double delta = 0.001;

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

        double fii = Math.toRadians(60.3851068722) ;
        assertEquals(1.053918934088933, fii, delta);

        double lambda = Math.toRadians(19.84813676944);
        assertEquals(0.346415337012956, lambda, delta);

        double Q_pilkku = Projektiokaavat.arcsinh(Math.tan(fii));
        assertEquals(1.330479626914950, Q_pilkku, delta);

        double Q2_pilkku = Projektiokaavat.arctanh(Math.sqrt(e_toiseen) * Math.sin(fii));
        assertEquals(0.071251194462820, Q2_pilkku, delta);

        double Q = Q_pilkku - Math.sqrt(e_toiseen) * Q2_pilkku;
        assertEquals(1.324649911823168, Q, delta);

        double l = lambda - lambda_nolla;
        assertEquals(-0.124823561025513, l, delta);

        double beeta = Math.atan(Math.sinh(Q));
        assertEquals(1.051030767588720, beeta, delta);

        double eeta_pilkku = Projektiokaavat.arctanh(Math.cos(beeta) * Math.sin(l));
        assertEquals(-0.061915076853879, eeta_pilkku, delta);

        double zeeta_pilkku = Math.asin(Math.sin(beeta) / Projektiokaavat.sech(eeta_pilkku));
        assertEquals(1.054391184619376, zeeta_pilkku, delta);

        double zeeta1 = h1_pilkku * Math.sin(2.0 * zeeta_pilkku) * Math.cosh(2.0 * eeta_pilkku);
        assertEquals(0.000724918454654, zeeta1, delta);

        double zeeta2 = h2_pilkku * Math.sin(4.0 * zeeta_pilkku) * Math.cosh(4.0 * eeta_pilkku);
        assertEquals(-0.000000690230193, zeeta2, delta);

        double zeeta3 = h3_pilkku * Math.sin(6.0 * zeeta_pilkku) * Math.cosh(6.0 * eeta_pilkku);
        assertEquals(0.000000000055283, zeeta3, delta);

        double zeeta4 = h4_pilkku * Math.sin(8.0 * zeeta_pilkku) * Math.cosh(8.0 * eeta_pilkku);
        assertEquals(0.000000000002298, zeeta4, delta);

        double eeta1 = h1_pilkku * Math.cos(2.0 * zeeta_pilkku) * Math.sinh(2.0 * eeta_pilkku);
        assertEquals(0.000053291297517, eeta1, delta);

        double eeta2 = h2_pilkku * Math.cos(4.0 * zeeta_pilkku) * Math.sinh(4.0 * eeta_pilkku);
        assertEquals(0.000000090400064, eeta2, delta);

        double eeta3 = h3_pilkku * Math.cos(6.0 * zeeta_pilkku) * Math.sinh(6.0 * eeta_pilkku);
        assertEquals(-0.000000000454791, eeta3, delta);

        double eeta4 = h4_pilkku * Math.cos(8.0 * zeeta_pilkku) * Math.sinh(8.0 * eeta_pilkku);
        assertEquals(0.000000000000692, eeta4, delta);

        double zeeta = zeeta_pilkku + zeeta1 + zeeta2 + zeeta3 + zeeta4;
        assertEquals(1.055115412901418, zeeta, delta);

        double eeta = eeta_pilkku + eeta1 + eeta2 + eeta3 + eeta4;
        assertEquals(-0.061861695610397, eeta, delta);

        double N = A1 * zeeta * k_nolla;
        assertEquals(6715706.37708, N, delta);

        double E = A1 * eeta * k_nolla + E_nolla;
        assertEquals(106256.35961, E, delta);
    }

    @Test
    public void ProjectionTest() {
        double latitude = 60.3851068722;
        double longitude = 19.84813676944;

        double[] result = Projektiokaavat.degreesToMeters(latitude, longitude);

        double N = result[0];
        double E = result[1];

        assertEquals(6715706.37708, N, 0.001);
        assertEquals(106256.35961, E, 0.001);
    }

    @Test
    public void LocationKiilopaaTest() {
        double latitude = 68.411994;
        double longitude = 27.472200;

        double[] result = Projektiokaavat.degreesToMeters(latitude, longitude);

        double N = result[0];
        double E = result[1];

        assertEquals(7588873.45, N, 0.1);
        assertEquals(519388.56, E, 0.1);
    }

    @Test
    public void LocationRattarinkatuTest() {
        double latitude = 61.000563;
        double longitude = 25.766045;

        double[] result = Projektiokaavat.degreesToMeters(latitude, longitude);

        double N = result[0];
        double E = result[1];

        assertEquals(6763478.58, N, 0.1);
        assertEquals(433264.43, E, 0.1);
    }
}

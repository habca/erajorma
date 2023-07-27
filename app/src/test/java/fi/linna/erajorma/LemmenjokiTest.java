package fi.linna.erajorma;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.Collection;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

/**
 * Lemmenkosken tupien Euref-Fin (~ WGS84)- ja ETRS-TM35FIN –koordinaatit.
 */
@RunWith(Parameterized.class)
public class LemmenjokiTest {

    @Parameters
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][]{
                { new double[] { 68, 42.0465 }, new double[] { 25, 59.5699 }, 7621332, 459173 }, // Härkäkoski
                //{ new double[] { 68, 60.588 }, new double[] { 26, 25.042 }, 7610581, 469505 }, // Juntinoja
                { new double[] { 68, 39.1804 }, new double[] { 25, 51.7932 }, 7616098, 453821 }, // Kultahamina
                { new double[] { 68, 55.3684 }, new double[] { 26, 24.8419 }, 7645867, 476482 }, // Kruununtupa, Heikkilä
                { new double[] { 68, 26.4436 }, new double[] { 26, 33.0690 }, 7592069, 481593 }, // Kyläjoki
                { new double[] { 68, 53.2158 }, new double[] { 26, 21.2792 }, 7641890, 474057 }, // Matti Musta
                { new double[] { 68, 40.1856 }, new double[] { 25, 46.7416 }, 7618031, 450439 }, // Morgamojan Kultala, Pellisen kämppä
                { new double[] { 68, 38.0346 }, new double[] { 26, 05.2823 }, 7613817, 462922 }, // Oahujoki
                { new double[] { 68, 17.3317 }, new double[] { 25, 33.4663 }, 7575766, 440464 }, // Paaraskalla
                { new double[] { 68, 37.3196 }, new double[] { 25, 25.3882 }, 7613035, 435859 }, // Postijoki
                { new double[] { 68, 12.6198 }, new double[] { 25, 06.8164 }, 7567509, 421866 }, // Pyhävasa
                { new double[] { 68, 11.4429 }, new double[] { 25, 23.1406 }, 7565002, 433075 }, // Raijankiselkä
                { new double[] { 68, 41.2522 }, new double[] { 25, 58.2821 }, 7619870, 458279 }, // Ravadasjärvi
                { new double[] { 68, 29.4243 }, new double[] { 25, 53.3528 }, 7597951, 454550 }, // Sallivaara, Mikko Takalon kämppä
                { new double[] { 68, 42.1138 }, new double[] { 26, 32.5374 }, 7621192, 481446 }, // Suivakkojärvi
                { new double[] { 68, 13.1156 }, new double[] { 25, 34.5995 }, 7567915, 441063 }, // Taatsijärvi
                { new double[] { 68, 33.0587 }, new double[] { 26, 21.5175 }, 7604431, 473826 }, // Taimenjärvi
                { new double[] { 68, 16.0135 }, new double[] { 25, 40.6527 }, 7573206, 445356 }, // Uurrekarkea
                { new double[] { 68, 47.5445 }, new double[] { 25, 36.6367 }, 7631849, 443912 }, // Vaskojoki
                { new double[] { 68, 35.7976 }, new double[] { 25, 10.3348 }, 7610490, 425572 }, // Vaskolompolo
        });
    }

    private double[] lat;
    private double[] lon;
    private double N;
    private double E;

    public LemmenjokiTest(double[] lat, double[] lon, double N, double E) {
        // Data driven test input.
        this.lat = lat;
        this.lon = lon;

        // Expected test result.
        this.N = N;
        this.E = E;
    }

    @Test
    public void RunTest() {
        double degrees_lat = Koordinaatit.DmsToDegrees(lat);
        double degrees_lon = Koordinaatit.DmsToDegrees(lon);

        double[] meters = Projektiokaavat.degreesToMeters(degrees_lat, degrees_lon);

        assertEquals(N, meters[0], 1);
        assertEquals(E, meters[1], 1);
    }
}

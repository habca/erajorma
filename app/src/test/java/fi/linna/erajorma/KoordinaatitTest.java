package fi.linna.erajorma;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import androidx.annotation.NonNull;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;

import fi.linna.erajorma.model.Koordinaatit;
import fi.linna.erajorma.model.Projektiokaavat;

public class KoordinaatitTest {

    @Test
    public void degreesToBearingTest() {

        // Kansas City
        double lat1 = 39.099912, lon1 = -94.581213;
        // St Louis
        double lat2 = 38.627089, lon2 = -90.200203;

        lat1 = Math.toRadians(lat1);
        lon1 = Math.toRadians(lon1);
        lat2 = Math.toRadians(lat2);
        lon2 = Math.toRadians(lon2);

        final double a = Math.cos(lat2) * Math.sin(lon2 - lon1);
        assertEquals(0.05967668696, a, 0.0000000001);

        final double b = Math.cos(lat1) * Math.sin(lat2) - Math.sin(lat1) * Math.cos(lat2) * Math.cos(lon2 - lon1);
        assertEquals(-0.00681261948, b, 0.00000000001);

        final double c = Math.atan2(a, b);
        assertEquals(1.684463062558, c, 0.0000000001);

        final double bearing = Math.toDegrees(c);
        assertEquals(96.51, bearing, 0.01);
    }

    private static double EPSILON = 0.0001;

    @RunWith(Parameterized.class)
    public static class DegreesToDmsTest
    {
        private double input;
        private double[] expected;

        @Parameterized.Parameters
         public static Collection<Object[]> data() {
            return Arrays.asList(new Object[][] {
                    { 49.947, new double[] { 49, 56, 49 } },
                    { 51.47788, new double[] { 51, 28, 40 } },
                    { 0.00147, new double[] { 0, 0, 5 } },
            });
        }

        public DegreesToDmsTest(double degrees, @NonNull double[] expected) {
            this.input = degrees;
            this.expected = expected;
        }

        @Test
        public void degreesToDmsTest() {
            double[] dms = Koordinaatit.degreesToDms(input);

            // First DMS decimal equals degrees.
            assertEquals(input, dms[0], EPSILON);

            assertEquals(expected[0], Math.floor(dms[0]), EPSILON);
            assertEquals(expected[1], Math.floor(dms[1]), EPSILON);
            assertEquals(expected[2], Math.round(dms[2]), EPSILON);
        }
    }

    @RunWith(Parameterized.class)
    public static class DmsToDegreesTest {

        private double[] input;
        private double expected;

        @Parameterized.Parameters
        public static Collection<Object[]> data() {
            return Arrays.asList(new Object[][] {
                    { new double[] { 41, 43, 57 }, 41.7325 },
                    { new double[] { 51, 28, 40.4 }, 51.47788 },
                    { new double[] { 0, 0, 5.3 }, 0.00147 }
            });
        }

        public DmsToDegreesTest(@NonNull double[] input, double expected) {
            this.input = input;
            this.expected = expected;
        }

        @Test
        public void dmsToDegreesTest() {
            double degrees = Koordinaatit.dmsToDegrees(input);
            assertEquals(expected, degrees, EPSILON);
        }
    }

    @Test
    public void degreesToDistanceTest_1() {
        double lat1 = Koordinaatit.dmsToDegrees(50, 3, 59);
        double lon1 = Koordinaatit.dmsToDegrees(-5, -42, -53);
        double lat2 = Koordinaatit.dmsToDegrees(58, 38, 38);
        double lon2 = Koordinaatit.dmsToDegrees(-3, -4, -12);

        double distanceFromDegrees = Koordinaatit.degreesToDistance(lat1, lon1, lat2, lon2);

        // https://www.movable-type.co.uk/scripts/latlong.html
        assertEquals(968.8535, distanceFromDegrees, EPSILON);

        double[] NE1 = Projektiokaavat.degreesToMeters(lat1, lon1);
        double[] NE2 = Projektiokaavat.degreesToMeters(lat2, lon2);

        double N1 = NE1[0], E1 = NE1[1];
        double N2 = NE2[0], E2 = NE2[1];

        double distanceFromMeters = Koordinaatit.metersToDistance(N1, E1, N2, E2);
        double k = Projektiokaavat.scaleCorrectionFromDegrees(lat1, lon1, lat2, lon2);

        assertNotEquals(968.8535, distanceFromMeters, 0.1);
        assertEquals(968.8535, distanceFromMeters / k, 0.1);
    }

    @Test
    public void degreesToDistanceTest_2() {
        double lat1 = 40.76;
        double lon1 = -73.984;

        double lat2 = 38.89;
        double lon2 = -77.032;

        double distance = Koordinaatit.degreesToDistance(lat1, lon1, lat2, lon2);

        // https://www.nhc.noaa.gov/gccalc.shtml
        assertEquals(333, distance, 1);
    }

    @Test
    public void degreesToDirectionTest() {
        double lat1 = Koordinaatit.dmsToDegrees(50, 3, 59);
        double lon1 = Koordinaatit.dmsToDegrees(-5, -42, -53);
        double lat2 = Koordinaatit.dmsToDegrees(58, 38, 38);
        double lon2 = Koordinaatit.dmsToDegrees(-3, -4, -12);

        double direction = Koordinaatit.degreesToDirection(lat1, lon1, lat2, lon2);
        double[] dms = Koordinaatit.degreesToDms(direction);

        assertEquals(9, Math.floor(direction), EPSILON);
        assertEquals(9, Math.floor(dms[0]), EPSILON);
        assertEquals(7, Math.floor(dms[1]), EPSILON);
        assertEquals(11, Math.round(dms[2]), EPSILON);
    }

    @Test
    public void degreesToDirectionNorthTest() {
        double lat1 = Koordinaatit.dmsToDegrees(50, 3, 59);
        double lat2 = Koordinaatit.dmsToDegrees(58, 38, 38);
        double lon = Koordinaatit.dmsToDegrees(-5, -42, -53);

        double direction = Koordinaatit.degreesToDirection(lat1, lon, lat2, lon);
        double[] dms = Koordinaatit.degreesToDms(direction);

        assertEquals(0, Math.floor(direction), EPSILON);
        assertEquals(0, Math.floor(dms[0]), EPSILON);
        assertEquals(0, Math.floor(dms[1]), EPSILON);
        assertEquals(0, Math.round(dms[2]), EPSILON);
    }

    @Test
    public void degreesToBearingTest_1() {
        double lat1 = 40.76;
        double lon1 = -73.984;

        double lat2 = 38.89;
        double lon2 = -77.032;

        double bearing = Koordinaatit.degreesToBearing(lat1, lon1, lat2, lon2);

        // https://www.igismap.com/map-tool/bearing-angle
        assertEquals(-127.635, bearing, 0.001);
    }

    @Test
    public void degreesToDirectionTest_1() {
        double lat1 = 40.76;
        double lon1 = -73.984;

        double lat2 = 38.89;
        double lon2 = -77.032;

        double bearing = Koordinaatit.degreesToBearing(lat1, lon1, lat2, lon2);
        double azimuth = Koordinaatit.degreesToDirection(lat1, lon1, lat2, lon2);

        assertEquals(azimuth, (bearing + 360) % 360, 0.0001);
    }

    @Test
    public void distanceOnMapTest() {
        // Mittakaava 1:25 000 (4 cm = 1 km)
        assertEquals(4, Koordinaatit.distanceOnMap(1, 1.0 / 25000.0), EPSILON);

        // Mittakaava 1:50 000 (2 cm = 1 km)
        assertEquals(2, Koordinaatit.distanceOnMap(1, 1.0 / 50000.0), EPSILON);

        // Mittakaava 1:100 000 (1 cm = 1 km)
        assertEquals(1, Koordinaatit.distanceOnMap(1, 1.0 / 100000.0), EPSILON);
    }

    @Test
    public void distanceOnNatureTest() {
        // Mittakaava 1:25 000 (1 cm = 250 m)
        assertEquals(0.250, Koordinaatit.distanceOnNature(1, 1.0 / 25000.0), EPSILON);

        // Mittakaava 1:50 000 (1 cm = 500 m)
        assertEquals(0.500, Koordinaatit.distanceOnNature(1, 1.0 / 50000.0), EPSILON);

        // Mittakaava 1:100 000 (1 cm = 1 km = 1 000 m)
        assertEquals(1.000, Koordinaatit.distanceOnNature(1, 1.0 / 100000.0), EPSILON);
    }
}

package fi.linna.erajorma;

import static org.junit.Assert.assertEquals;

import androidx.annotation.NonNull;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;

import fi.linna.erajorma.model.Koordinaatit;

public class KoordinaatitTest {

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
    public void degreesToDistanceTest() {
        double lat1 = Koordinaatit.dmsToDegrees(50, 3, 59);
        double lon1 = Koordinaatit.dmsToDegrees(-5, -42, -53);
        double lat2 = Koordinaatit.dmsToDegrees(58, 38, 38);
        double lon2 = Koordinaatit.dmsToDegrees(-3, -4, -12);
        double distance = Koordinaatit.degreesToDistance(lat1, lon1, lat2, lon2);

        assertEquals(968.8535, distance, EPSILON);
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
}

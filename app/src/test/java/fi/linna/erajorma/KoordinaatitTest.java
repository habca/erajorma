package fi.linna.erajorma;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class KoordinaatitTest {

    private static double EPSILON = 0.0001;

    @Test
    public void DegreesToDmsTest() {
        double degrees = 49.947;
        double[] dms = Koordinaatit.DegreesToDms(degrees);

        assertEquals(49, dms[0], EPSILON);
        assertEquals(56, dms[1], EPSILON);
        assertEquals(49, dms[2], EPSILON);
    }

    @Test
    public void DmsToDegreesTest() {
        double[] dms = new double[] { 41, 43, 57 };
        double degrees = Koordinaatit.DmsToDegrees(dms);

        assertEquals(41.7325, degrees, EPSILON);
    }
}

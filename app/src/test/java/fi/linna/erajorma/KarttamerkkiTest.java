package fi.linna.erajorma;

import static org.junit.Assert.assertEquals;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import fi.linna.erajorma.data.Karhunkierros;
import fi.linna.erajorma.data.Lemmenjoki;
import fi.linna.erajorma.data.PallasHettaOlos;
import fi.linna.erajorma.data.PyhaLuosto;
import fi.linna.erajorma.model.IKarttamerkki;
import fi.linna.erajorma.model.Karttamerkki;
import fi.linna.erajorma.model.Koordinaatit;
import fi.linna.erajorma.model.Projektiokaavat;

/**
 * Tupien Euref-Fin (~ WGS84)- ja ETRS-TM35FIN –koordinaatit.
 */
public class KarttamerkkiTest {

    @RunWith(Parameterized.class)
    public static class DegreesToMetersTest {

        @NonNull
        private IKarttamerkki input;

        @Parameterized.Parameters
        public static Collection<IKarttamerkki> data() {
            Collection<IKarttamerkki> data = new ArrayList<>();

            data.addAll(new Karhunkierros());
            data.addAll(new Lemmenjoki());
            data.addAll(new PallasHettaOlos());
            data.addAll(new PyhaLuosto());

            return data;
        }

        /**
         * Karttamerkki encapsulates location information as coordinates.
         * @param input data driven test input and expected result.
         */
        public DegreesToMetersTest(@NonNull IKarttamerkki input) {
            this.input = input;
        }

        @Test
        public void degreesToMetersTest() {
            double degrees_lat = Koordinaatit.dmsToDegrees(input.getLatitude());
            double degrees_lon = Koordinaatit.dmsToDegrees(input.getLongitude());

            double[] meters = Projektiokaavat.degreesToMeters(degrees_lat, degrees_lon);

            assertEquals(input.getNorth(), meters[0], 1);
            assertEquals(input.getEast(), meters[1], 1);
        }
    }

    @Test
    public void comparatorTest() {
        List<String> mjonot;

        mjonot = Arrays.asList(new String[] {"aaa", "aaa"});
        mjonot.sort(Karttamerkki.comparator());

        assertEquals(0, "aaa".compareTo("aaa"));
        assertEquals("aaa", mjonot.get(0));

        mjonot = Arrays.asList(new String[] {"aa", "aaa"});
        mjonot.sort(Karttamerkki.comparator());

        assertEquals(-1, "aa".compareTo("aaa"));
        assertEquals("aaa", mjonot.get(0));

        mjonot = Arrays.asList(new String[] {"aab", "aaa"});
        mjonot.sort(Karttamerkki.comparator());

        assertEquals(1, "aab".compareTo("aaa"));
        assertEquals("aaa", mjonot.get(0));
    }
}

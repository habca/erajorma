package fi.linna.erajorma;

import static org.junit.Assert.assertEquals;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import maastokartat.Karhunkierros;
import maastokartat.Lemmenjoki;
import maastokartat.PyhaLuosto;

/**
 * Tupien Euref-Fin (~ WGS84)- ja ETRS-TM35FIN –koordinaatit.
 */
@RunWith(Parameterized.class)
public class KarttamerkkiTest {

    @Parameters
    public static Collection<IKarttamerkki> data() {
        Collection<IKarttamerkki> data = new ArrayList<IKarttamerkki>();
        data.addAll(new Lemmenjoki());
        data.addAll(new Karhunkierros());
        data.addAll(new PyhaLuosto());
        return data;
    }

    @NonNull
    private IKarttamerkki karttamerkki;

    /**
     * Karttamerkki encapsulates location information as coordinates.
     * @param karttamerkki data driven test input and expected result.
     */
    public KarttamerkkiTest(@NonNull IKarttamerkki karttamerkki) {
        this.karttamerkki = karttamerkki;
    }

    @Test
    public void RunTest() {
        double degrees_lat = Koordinaatit.DmsToDegrees(karttamerkki.getLatitude());
        double degrees_lon = Koordinaatit.DmsToDegrees(karttamerkki.getLongitude());

        double[] meters = Projektiokaavat.degreesToMeters(degrees_lat, degrees_lon);

        assertEquals(karttamerkki.getNorth(), meters[0], 1);
        assertEquals(karttamerkki.getEast(), meters[1], 1);
    }

    @Test
    public void compareToTest() {
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

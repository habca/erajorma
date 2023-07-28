package fi.linna.erajorma;

import static org.junit.Assert.assertEquals;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.Collection;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import maastokartat.Karhunkierros;
import maastokartat.Lemmenjoki;

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
}

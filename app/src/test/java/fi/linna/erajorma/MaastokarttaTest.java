package fi.linna.erajorma;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import fi.linna.erajorma.data.*;
import fi.linna.erajorma.model.IKarttamerkki;
import fi.linna.erajorma.model.Koordinaatit;
import fi.linna.erajorma.model.Projektiokaavat;

public class MaastokarttaTest {

    @Test
    public void getKokTest() {
        AMaastokartta pallasHettaOlos = new PallasHettaOlos();
        assertEquals(13.11, pallasHettaOlos.getKok(pallasHettaOlos.nekYear), 0.01);
        assertEquals(13.36, pallasHettaOlos.getKok(pallasHettaOlos.nekYear + 1), 0.01);

        AMaastokartta pyhaLuosto = new PyhaLuosto();
        assertEquals(11.69, pyhaLuosto.getKok(pyhaLuosto.nekYear), 0.01);
        assertEquals(11.93, pyhaLuosto.getKok(pyhaLuosto.nekYear + 1), 0.01);
    }

    @Test
    public void getNakTest() {
        getNakTest_MeridianConvergence(new PallasHettaOlos());
        getNakTest_MeridianConvergence(new PyhaLuosto());

        getNakTest_DirectionalAngle(new PallasHettaOlos());
        getNakTest_DirectionalAngle(new PyhaLuosto());
    }

    /**
     * Kartan napaluvun korjaus vakio (NAK) on riittävän tarkka eri karttaruuduissa.
     */
    private void getNakTest_MeridianConvergence(AMaastokartta map) {
        for (IKarttamerkki marker : map) {
            double latitude = Koordinaatit.dmsToDegrees(marker.getLatitude());
            double longitude = Koordinaatit.dmsToDegrees(marker.getLongitude());

            double meridianConverge = Projektiokaavat.meridianConvergence(latitude, longitude);
            double nak_marker = marker.getNak();
            double nak_map = map.getNak();

            assertEquals(meridianConverge, -nak_marker, 0.01);
            assertEquals(meridianConverge, -nak_map, 0.4);
            assertEquals(nak_map, nak_marker, 0.4);
        }
    }

    /**
     * Kompassin suuntakulma on sama maastossa ja kartalla.
     */
    private void getNakTest_DirectionalAngle(AMaastokartta map) {
        for (int i = 1; i < map.size(); i ++) {
            IKarttamerkki marker0 = map.get(i - 1);
            IKarttamerkki marker1 = map.get(i);

            double lat0 = Koordinaatit.dmsToDegrees(marker0.getLatitude());
            double lon0 = Koordinaatit.dmsToDegrees(marker0.getLongitude());
            double lat1 = Koordinaatit.dmsToDegrees(marker1.getLatitude());
            double lon1 = Koordinaatit.dmsToDegrees(marker1.getLongitude());

            double azimuthFromDegrees = Koordinaatit.degreesToDirection(lat0, lon0, lat1, lon1);

            double N0 = marker0.getNorth();
            double E0 = marker0.getEast();
            double N1 = marker1.getNorth();
            double E1 = marker1.getEast();

            double azimuthFromMeters = Projektiokaavat.directionalAngle(N0, E0, N1, E1);

            double nak_marker = marker0.getNak();
            double nak_map = map.getNak();

            assertEquals(azimuthFromDegrees, azimuthFromMeters - nak_marker, 0.01);
            assertEquals(azimuthFromMeters, azimuthFromDegrees + nak_marker, 0.01);

            assertEquals(azimuthFromDegrees, azimuthFromMeters - nak_map, 0.4);
            assertEquals(azimuthFromMeters, azimuthFromDegrees + nak_map, 0.4);

            double angle = Koordinaatit.metersToDirection(N0, E0, N1, E1);
            double delta = Projektiokaavat.arcToChordCorrection(N0, E0, N1, E1);

            // delta = azimuthFromDegrees (+ nak) - azimuthFromMeters
            assertEquals(azimuthFromDegrees + nak_marker, angle - delta, 0.1);
        }
    }
}

package fi.linna.erajorma;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import fi.linna.erajorma.data.*;

public class MaastokarttaTest {

    @Test
    public void getKokTest() {

        AMaastokartta karhunkierros = new Karhunkierros();
        assertEquals(12.5, karhunkierros.getKok(karhunkierros.nekYear), 0.01);
        assertEquals(12.7, karhunkierros.getKok(karhunkierros.nekYear + 1), 0.01);

        AMaastokartta lemmenjoki = new Lemmenjoki();
        assertEquals(10.5, lemmenjoki.getKok(lemmenjoki.nekYear), 0.01);
        assertEquals(10.7, lemmenjoki.getKok(lemmenjoki.nekYear + 1), 0.01);

        AMaastokartta pallasHettaOlos = new PallasHettaOlos();
        assertEquals(13.11, pallasHettaOlos.getKok(pallasHettaOlos.nekYear), 0.01);
        assertEquals(13.36, pallasHettaOlos.getKok(pallasHettaOlos.nekYear + 1), 0.01);

        AMaastokartta pyhaLuosto = new PyhaLuosto();
        assertEquals(11.69, pyhaLuosto.getKok(pyhaLuosto.nekYear), 0.01);
        assertEquals(11.93, pyhaLuosto.getKok(pyhaLuosto.nekYear + 1), 0.01);
    }
}

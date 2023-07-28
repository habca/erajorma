package maastokartat;

import java.util.ArrayList;
import java.util.Arrays;

import fi.linna.erajorma.IKarttamerkki;
import fi.linna.erajorma.Karttamerkki;

public class PyhaLuosto extends ArrayList<IKarttamerkki> {
    /**
     * Pyhä Luoston tupien koordinaatit.
     */
    public PyhaLuosto() {
        this.addAll(Arrays.asList(new Karttamerkki[] {
                new Karttamerkki("Haarainselkä", new double[] { 66, 59.520 }, new double[] { 27, 10.008 }),
                new Karttamerkki("Kapusta", new double[] { 67, 03.689 }, new double[] { 27, 00.092 }),
                new Karttamerkki("Karhunjuomalampi", new double[] { 67, 01.212 }, new double[] { 27, 10.555 }),
                new Karttamerkki("Kiimaselkä", new double[] { 67, 02.515 }, new double[] { 27, 07.514 }),
                new Karttamerkki("Pyhälampi", new double[] { 67, 05.767 }, new double[] { 26, 57.128 }),
                new Karttamerkki("Salmiaapa", new double[] { 66, 59.724 }, new double[] { 27, 03.542 }),
                new Karttamerkki("Huttuloma", new double[] { 67, 01.720 }, new double[] { 27, 03.996 }),
                new Karttamerkki("Myllykämppä (Keino-oja)", new double[] { 67, 10.504 }, new double[] { 27, 06.917 }),
                new Karttamerkki("Yli-Luosto", new double[] { 67, 13.479 }, new double[] { 26, 45.094 }),
                new Karttamerkki("Huttujärvi", new double[] { 67, 01.821 }, new double[] { 27, 02.349 }),
                new Karttamerkki("Keino-ojan Myllytupa", new double[] { 67, 10.518 }, new double[] { 27, 06.918 }),
                new Karttamerkki("Kuukkeli", new double[] { 67, 05.601 }, new double[] { 26, 59.758 }),
                new Karttamerkki("Yrjölä", new double[] { 67, 07.458 }, new double[] { 26, 51.153 }),
        }));
    }
}

package maastokartat;

import java.util.ArrayList;
import java.util.Arrays;

import fi.linna.erajorma.Karttamerkki;

public class Karhunkierros extends ArrayList<Karttamerkki> {

    /**
     * Karhunkierroksen tupien koordinaatit.
     */
    public Karhunkierros() {
        this.addAll(Arrays.asList(new Karttamerkki[] {
                new Karttamerkki(new double[] { 66, 19.7389 }, new double[] { 29, 31.8963 }, 7358875, 613393), // Aitaniitty
                new Karttamerkki(new double[] { 66, 20.9317 }, new double[] { 29, 26.5978 }, 7360932, 609353), // Ansakämppä
                new Karttamerkki(new double[] { 66, 18.2051 }, new double[] { 29, 31.1304 }, 7356004, 612937), // Jussinkämppä
                new Karttamerkki(new double[] { 66, 24.5803 }, new double[] { 29, 09.2094 }, 7367230, 596153), // Puikkokämppä
                new Karttamerkki(new double[] { 66, 24.0413 }, new double[] { 29, 07.2209 }, 7366179, 594708), // Ristikallio
                new Karttamerkki(new double[] { 66, 25.9128 }, new double[] { 29, 09.3901 }, 7369709, 596202), // Savilampi
                new Karttamerkki(new double[] { 66, 15.2416 }, new double[] { 29, 26.2119 }, 7350356, 609477), // Siilastupa
                new Karttamerkki(new double[] { 66, 24.5227 }, new double[] { 29, 11.4147 }, 7367180, 597798), // Taivalköngäs
                new Karttamerkki(new double[] { 66, 11.2080 }, new double[] { 29, 11.5117 }, 7342459, 598736), // Valtavaara
        }));
    }
}

package maastokartat;

import java.util.ArrayList;
import java.util.Arrays;

import fi.linna.erajorma.Karttamerkki;

public class Lemmenjoki extends ArrayList<Karttamerkki> {

    /**
     * Lemmenjoen tupien koordinaatit.
     */
    public Lemmenjoki() {
        this.addAll(Arrays.asList(new Karttamerkki[] {
                new Karttamerkki(new double[] { 68, 42.0465 }, new double[] { 25, 59.5699 }, 7621332, 459173), // Härkäkoski

                // Maastokartan ETRS-TM35FIN -koordinaatit eivät täsmää Euref-Fin (~WGS84) -koordinaatteihin.
                // new Autiotupa(new double[] { 68, 60.588 }, new double[] { 26, 25.042 }, 7610581, 469505), // Juntinoja
                new Karttamerkki(new double[] { 68, 60.588 }, new double[] { 26, 25.042 }), // Juntinoja

                new Karttamerkki(new double[] { 68, 39.1804 }, new double[] { 25, 51.7932 }, 7616098, 453821), // Kultahamina
                new Karttamerkki(new double[] { 68, 55.3684 }, new double[] { 26, 24.8419 }, 7645867, 476482), // Kruununtupa, Heikkilä
                new Karttamerkki(new double[] { 68, 26.4436 }, new double[] { 26, 33.0690 }, 7592069, 481593), // Kyläjoki
                new Karttamerkki(new double[] { 68, 53.2158 }, new double[] { 26, 21.2792 }, 7641890, 474057), // Matti Musta
                new Karttamerkki(new double[] { 68, 40.1856 }, new double[] { 25, 46.7416 }, 7618031, 450439), // Morgamojan Kultala, Pellisen kämppä
                new Karttamerkki(new double[] { 68, 38.0346 }, new double[] { 26, 05.2823 }, 7613817, 462922), // Oahujoki
                new Karttamerkki(new double[] { 68, 17.3317 }, new double[] { 25, 33.4663 }, 7575766, 440464), // Paaraskalla
                new Karttamerkki(new double[] { 68, 37.3196 }, new double[] { 25, 25.3882 }, 7613035, 435859), // Postijoki
                new Karttamerkki(new double[] { 68, 12.6198 }, new double[] { 25, 06.8164 }, 7567509, 421866), // Pyhävasa
                new Karttamerkki(new double[] { 68, 11.4429 }, new double[] { 25, 23.1406 }, 7565002, 433075), // Raijankiselkä
                new Karttamerkki(new double[] { 68, 41.2522 }, new double[] { 25, 58.2821 }, 7619870, 458279), // Ravadasjärvi
                new Karttamerkki(new double[] { 68, 29.4243 }, new double[] { 25, 53.3528 }, 7597951, 454550), // Sallivaara, Mikko Takalon kämppä
                new Karttamerkki(new double[] { 68, 42.1138 }, new double[] { 26, 32.5374 }, 7621192, 481446), // Suivakkojärvi
                new Karttamerkki(new double[] { 68, 13.1156 }, new double[] { 25, 34.5995 }, 7567915, 441063), // Taatsijärvi
                new Karttamerkki(new double[] { 68, 33.0587 }, new double[] { 26, 21.5175 }, 7604431, 473826), // Taimenjärvi
                new Karttamerkki(new double[] { 68, 16.0135 }, new double[] { 25, 40.6527 }, 7573206, 445356), // Uurrekarkea
                new Karttamerkki(new double[] { 68, 47.5445 }, new double[] { 25, 36.6367 }, 7631849, 443912), // Vaskojoki
                new Karttamerkki(new double[] { 68, 35.7976 }, new double[] { 25, 10.3348 }, 7610490, 425572), // Vaskolompolo
        }));
    }
}

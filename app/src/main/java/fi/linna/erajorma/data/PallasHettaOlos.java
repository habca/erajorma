package fi.linna.erajorma.data;

import java.util.Arrays;

import fi.linna.erajorma.model.Karttamerkki;

public class PallasHettaOlos extends AMaastokartta {

    /**
     * Pallaksen, Hetan ja Oloksen tupien koordinaatit.
     */
    public PallasHettaOlos() {
        super(1.0 / 50000.0, 2019, 10.33, 0.25, 2.78);

        this.addAll(Arrays.asList(new Karttamerkki[] {
                new Karttamerkki("Aittalahti", new double[] { 68, 0.744 }, new double[] { 24, 14.928 }),
                new Karttamerkki("Hannukuru", new double[] { 68, 13.080 }, new double[] { 23, 56.934 }),
                new Karttamerkki("Hietalahti", new double[] { 68, 2.016 }, new double[] { 24, 16.464 }),
                new Karttamerkki("Juuvanrova", new double[] { 67, 52.80 }, new double[] { 23, 52.134 }),
                new Karttamerkki("Keimiöjärvi", new double[] { 67, 57.066 }, new double[] { 24, 9.792 }),
                new Karttamerkki("Kuusikonmaa", new double[] { 67, 51.330 }, new double[] { 23, 54.45 }),
                new Karttamerkki("Montellin maja", new double[] { 68, 8.316 }, new double[] { 24, 2.436 }),
                new Karttamerkki("Mustakero", new double[] { 67, 58.110 }, new double[] { 24, 11.418 }),
                new Karttamerkki("Mustavaara", new double[] { 67, 59.304 }, new double[] { 24, 7.590 }),
                new Karttamerkki("Mäntyrova", new double[] { 68, 1.626 }, new double[] { 24, 0.300 }),
                new Karttamerkki("Nammalakuru", new double[] { 68, 7.782 }, new double[] { 24, 3.366 }),
                new Karttamerkki("Pahakuru", new double[] { 68, 13.518 }, new double[] { 23, 55.140 }),
                new Karttamerkki("Punainenhiekka", new double[] { 68, 0.492 }, new double[] { 24, 13.932 }),
                new Karttamerkki("Puolitaival", new double[] { 68, 12.390 }, new double[] { 23, 38.454 }),
                new Karttamerkki("Pyhäkero", new double[] { 68, 20.562 }, new double[] { 23, 43.206 }),
                new Karttamerkki("Rautuoja", new double[] { 68, 14.484 }, new double[] { 23, 43.728 }),
                new Karttamerkki("Sioskuru", new double[] { 68, 17.880 }, new double[] { 23, 49.020 }),
                new Karttamerkki("Tammikämppä", new double[] { 67, 54.030 }, new double[] { 23, 51.828 }),
                new Karttamerkki("Tappuri", new double[] { 68, 16.920 }, new double[] { 23, 52.122 }),
                new Karttamerkki("Vuontisjärvi", new double[] { 68, 2.286 }, new double[] { 23, 51.270 }),
        }));
    }
}

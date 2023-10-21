# Eräjorma – GPS-paikannin

Sovellus kertoo GPS-sijainnin noin 50 m tarkkuudella.

- Maantieteelliset koordinaatit WGS84-koordinaatistossa.
- Suomen maastokarttojen karttaprojektio ETRS-TM35FIN.
- Norjan maastokarttojen karttaprojektio UTM.

## Käyttöoikeudet

Tarkka sijainti vaaditaan, muuten sovellus ei käynnisty.

![alt Käyttöoikeudet](images/Screenshot%20from%202023-08-06%2013-55-41.png)

## Käyttöohjeet

Sovellus kertoo kompassin suunnan ja etäisyyden kohteeseen.

1. Luetaan numeerinen sijainti kartan ruudukolta.
2. Otetaan suunta kompassilla haluttuun kohteeseen.
3. Kompassin metriasteikko kertoo etäisyyden kartalla.

![alt Käyttöohjeet](images/Screenshot%20from%202023-09-23%2015-19-53.png)

## Suomen maastokartat

Suomen maastokartoissa on ETRS-TM35FIN -karttaprojektio.

- Pyhä Luosto, 1:25 000. Karttakeskus 2020. ISBN 978-952-266-512-6.
- Pallas Hetta Olos, 1:50 000. Karttakeskus 2019. ISBN 978-952-266-627-7.
- Saariselkä Sokosti, 1:50 000. Karttakeskus 2019. ISBN 978-952-266-583-6.
- Karhunkierros, 1:25 000. Calazo 2019. ISBN 978-91-86773-48-9.
- Lemmenjoki, 1:100 000/1:25 000. Calazo 2017. ISBN 978-91-88335-71-5.

![alt Suomen maastokartat](images/Screenshot%20from%202023-09-23%2015-19-40.png)

## Norjan maastokartat

Norjan maastokartoissa on UTM-karttaprojektio.

- Senja, 1:50 000. Calazo 2023. ISBN 978-91-89541-45-0.
- Vest-Lofoten, 1:50 000. Nordeca 2016. ISBN 704-6-6600-2745-5.
- Vågan, 1:50 000. Nordeca 2016. ISBN 704-6-6600-2671-7.

## Kompassit

NH-kompassit on tasapainotettu pohjoiselle ja SH-kompassit eteläiselle pallonpuoliskolle.

- Suunto A-30, NH Metric Compass, SS012095013.
- Suunto M-3, NH Compass, SS021369000.

![alt Kompassit](images/Screenshot%20from%202023-09-23%2015-31-00.png)

# Development

This section explains how to set up a development environment for Arch Linux.

## Android Studio

A package manager Snap provides automatic updates which is convenient, but classic confinement requires the `/snap` directory. To allow the installation of classic snaps, create a symbolic link between `/var/lib/snapd/snap` and `/snap`. [1]

![alt Snap](images/Screenshot%20from%202023-10-07%2011-48-57.png)

```
git clone https://aur.archlinux.org/snapd.git
cd snapd
makepkg -sr
pacman -U *.pkg.tar.zst

systemctl enable --now snapd.socket
ln -s /var/lib/snapd/snap /snap
snap install android-studio --classic
```

## Terminal

Run automated tests from linux terminal or build pipeline.

```
./gradlew build
./gradlew test
```

## References

- [1] <https://wiki.archlinux.org/title/Snap#Classic_snaps>

# Eräjorma – GPS-paikannin

Eräjorma-sovellus kertoo GPS-sijainnin noin 50 m tarkkuudella suomalaisten maastokarttojen karttaprojektiossa ETRS-TM35FIN ja geografisessa tasokoordinaatistossa ETRS89 (~WGS84), EUREF89 (~WGS84) tai EUREF-FIN (~WGS84).

## Käyttöoikeudet

Tarkka sijainti vaaditaan, muuten sovellus ei käynnisty.

![alt Tarkka sijainti](images/Screenshot%20from%202023-08-06%2013-55-41.png)

## Käyttöohjeet

1. Luetaan numeerinen sijainti kartan ruudukolta.
2. Otetaan suunta kompassilla haluttuun kohteeseen.
3. Kompassin metriasteikko kertoo lyhimmän etäisyyden.

![alt Pääikkuna](images/Screenshot%20from%202023-08-06%2016-49-25.png)

## Maastokartat

- Pyhä Luosto, 1:25 000. Karttakeskus 2020. ISBN 978-952-266-512-6.
- Saariselkä Sokosti, 1:50 000. Karttakeskus 2019. ISBN 978-952-266-583-6.
- Karhunkierros, 1:25 000. Calazo 2019. ISBN 978-91-86773-48-9.
- Lemmenjoki, 1:100 000/1:25 000. Calazo 2017. ISBN 978-91-88335-71-5.

## Kompassit

- Suunto A-30

## Development

This section explains how to set up a development environment for Arch Linux.

### Android Studio

A package manager Snap provides automatic updates which is convenient, but classic confinement requires the `/snap` directory. To allow the installation of classic snaps, create a symbolic link between `/var/lib/snapd/snap` and `/snap`. [1]

```
git clone https://aur.archlinux.org/snapd.git
cd snapd
makepkg -sr
pacman -U *.pkg.tar.zst

systemctl enable --now snapd.socket
ln -s /var/lib/snapd/snap /snap
snap install android-studio --classic
```

## References

- [1] <https://wiki.archlinux.org/title/Snap#Classic_snaps>

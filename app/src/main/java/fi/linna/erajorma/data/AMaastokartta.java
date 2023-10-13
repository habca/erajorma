package fi.linna.erajorma.data;

import java.util.ArrayList;

import fi.linna.erajorma.model.IKarttamerkki;

public abstract class AMaastokartta extends ArrayList<IKarttamerkki> {

    public double mapScale;
    public int nekYear;
    private double nek;
    private double nekYearly;
    private double nak;

    public AMaastokartta(double mapScale, int nekYear, double nek, double nekYearly, double nak) {
        this.mapScale = mapScale;
        this.nekYear = nekYear;
        this.nek = nek;
        this.nekYearly = nekYearly;
        this.nak = nak;
    }

    public AMaastokartta(double mapScale, int nekYear, double nek, double nekYearly) {
        this(mapScale, nekYear, nek, nekYearly, 0.0);
    }

    public double getNak() {
        return nak;
    }

    public double getNek(int year) {
        return nek + nekYearly * (year - this.nekYear);
    }

    public double getKok(int year) {
        return getNak() + getNek(year);
    }
}

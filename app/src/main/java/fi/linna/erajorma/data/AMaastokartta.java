package fi.linna.erajorma.data;

import java.util.ArrayList;

import fi.linna.erajorma.model.IKarttamerkki;

public abstract class AMaastokartta extends ArrayList<IKarttamerkki> {

    public double mapScale;
    public int nekYear;
    public double nek;
    public double nekYearly;
    public double nak;

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

    public double getKok(int year) {
        return nak + nek + nekYearly * (year - this.nekYear);
    }
}

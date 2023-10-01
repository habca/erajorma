package fi.linna.erajorma.view;

import android.content.Context;
import android.widget.EditText;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;

import fi.linna.erajorma.R;
import fi.linna.erajorma.model.Erajorma;
import fi.linna.erajorma.model.IKarttamerkki;
import fi.linna.erajorma.model.Koordinaatit;

public class MarkerView extends FrameLayout {

    public MarkerView(@NonNull Context context, IKarttamerkki marker, Erajorma erajorma) {
        super(context);
        init(marker, erajorma);
    }

    private void init(IKarttamerkki marker, Erajorma erajorma) {
        inflate(getContext(), R.layout.view_marker, this);

        final double latitudeDegrees = Koordinaatit.dmsToDegrees(marker.getLatitude());
        final double longitudeDegrees = Koordinaatit.dmsToDegrees(marker.getLongitude());

        final double[] latitudeDms = Koordinaatit.degreesToDms(latitudeDegrees);
        final double[] longitudeDms = Koordinaatit.degreesToDms(longitudeDegrees);

        SetEditTextContent(R.id.marker_wgs84_latitude_d, latitudeDms[0] );
        SetEditTextContent(R.id.marker_wgs84_latitude_dm, (int) latitudeDms[0], latitudeDms[1]);
        SetEditTextContent(R.id.marker_wgs84_latitude_dms, (int) latitudeDms[0], (int) latitudeDms[1], latitudeDms[2]);

        SetEditTextContent(R.id.marker_wgs84_longitude_d, longitudeDms[0]);
        SetEditTextContent(R.id.marker_wgs84_longitude_dm, (int) longitudeDms[0], longitudeDms[1]);
        SetEditTextContent(R.id.marker_wgs84_longitude_dms, (int) longitudeDms[0], (int) longitudeDms[1], longitudeDms[2]);

        String marker_utm_zone = marker.getZone();
        double marker_utm_north = marker.getNorthing();
        double marker_utm_east = marker.getEasting();

        SetEditTextContent(R.id.marker_utm_zone, marker_utm_zone);
        SetEditTextContent(R.id.marker_utm_north, marker_utm_north, "");
        SetEditTextContent(R.id.marker_utm_east, marker_utm_east, "");

        double marker_tm35fin_north = marker.getNorth();
        double marker_tm35fin_east = marker.getEast();

        SetEditTextContent(R.id.marker_etrs_tm35fin_north, marker_tm35fin_north, "");
        SetEditTextContent(R.id.marker_etrs_tm35fin_east, marker_tm35fin_east, "");

        IKarttamerkki location = erajorma.location;

        if (location != null) {
            double marker_azimuth = erajorma.location.getAzimuth(marker);
            double marker_distance = erajorma.location.getDistance(marker);

            SetEditTextContent(R.id.marker_azimuth, marker_azimuth, "°");
            SetEditTextContent(R.id.marker_distance, marker_distance, "km");
        }
    }

    private void SetEditTextContent(int layoutId, double deg)
    {
        String format = String.format("%.4f°", deg);
        SetEditTextContent(layoutId, format);
    }

    private void SetEditTextContent(int layoutId, int deg, double min)
    {
        String format = String.format("%d° %.4f'", deg, min);
        SetEditTextContent(layoutId, format);
    }

    private void SetEditTextContent(int layoutId, int deg, int min, double sec)
    {
        String format = String.format("%d° %d' %.4f\"", deg, min, sec);
        SetEditTextContent(layoutId, format);
    }

    private void SetEditTextContent(int layoutId, double value, String unit) {
        String format = String.format("%.4f %s", value, unit);
        SetEditTextContent(layoutId, format);
    }

    private void SetEditTextContent(int layoutId, String format) {
        EditText target = findViewById(layoutId);
        target.setText(format);
    }
}
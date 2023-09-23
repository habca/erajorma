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

        double marker_wgs84_latitude = Koordinaatit.dmsToDegrees(marker.getLatitude());
        double marker_wgs84_longitude = Koordinaatit.dmsToDegrees(marker.getLongitude());

        SetEditTextContent(R.id.marker_wgs84_latitude, marker_wgs84_latitude);
        SetEditTextContent(R.id.marker_wgs84_longitude, marker_wgs84_longitude);

        String marker_utm_zone = marker.getZone();
        double marker_utm_north = marker.getNorthing();
        double marker_utm_east = marker.getEasting();

        SetEditTextContent(R.id.marker_utm_zone, marker_utm_zone);
        SetEditTextContent(R.id.marker_utm_north, marker_utm_north);
        SetEditTextContent(R.id.marker_utm_east, marker_utm_east);

        double marker_tm35fin_north = marker.getNorth();
        double marker_tm35fin_east = marker.getEast();

        SetEditTextContent(R.id.marker_etrs_tm35fin_north, marker_tm35fin_north);
        SetEditTextContent(R.id.marker_etrs_tm35fin_east, marker_tm35fin_east);

        IKarttamerkki location = erajorma.location;

        if (location != null) {
            double marker_azimuth = erajorma.location.getAzimuth(marker);
            double marker_distance = erajorma.location.getDistance(marker);

            SetEditTextContent(R.id.marker_azimuth, marker_azimuth);
            SetEditTextContent(R.id.marker_distance, marker_distance);
        }
    }

    private void SetEditTextContent(int layoutId, double value) {
        String format = String.format("%.4f", value);
        SetEditTextContent(layoutId, format);
    }

    private void SetEditTextContent(int layoutId, String value) {
        EditText target = findViewById(layoutId);
        target.setText(value);
    }
}
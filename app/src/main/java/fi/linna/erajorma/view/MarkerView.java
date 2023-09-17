package fi.linna.erajorma.view;

import android.content.Context;
import android.widget.EditText;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;

import fi.linna.erajorma.R;
import fi.linna.erajorma.model.IKarttamerkki;
import fi.linna.erajorma.model.Koordinaatit;

public class MarkerView extends FrameLayout {

    public MarkerView(@NonNull Context context, IKarttamerkki marker) {
        super(context);
        init(marker);
    }

    private void init(IKarttamerkki marker) {
        inflate(getContext(), R.layout.view_marker, this);

        double marker_wgs84_latitude = Koordinaatit.dmsToDegrees(marker.getLatitude());
        double marker_wgs84_longitude = Koordinaatit.dmsToDegrees(marker.getLongitude());

        SetEditTextContent(R.id.marker_wgs84_latitude, marker_wgs84_latitude);
        SetEditTextContent(R.id.marker_wgs84_longitude, marker_wgs84_longitude);

        double marker_tm35fin_north = marker.getNorth();
        double marker_tm35fin_east = marker.getEast();

        SetEditTextContent(R.id.marker_etrs_tm35fin_north, marker_tm35fin_north);
        SetEditTextContent(R.id.marker_etrs_tm35fin_east, marker_tm35fin_east);
    }

    private void SetEditTextContent(int layoutId, double value) {
        EditText target = findViewById(layoutId);
        String format = String.format("%.4f", value);
        target.setText(format);
    }
}
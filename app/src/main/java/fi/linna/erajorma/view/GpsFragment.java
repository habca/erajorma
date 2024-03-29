package fi.linna.erajorma.view;

import static com.google.android.gms.location.LocationRequest.PRIORITY_HIGH_ACCURACY;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;

import java.util.Date;

import fi.linna.erajorma.R;
import fi.linna.erajorma.model.Erajorma;
import fi.linna.erajorma.model.IKarttamerkki;
import fi.linna.erajorma.model.Karttamerkki;
import fi.linna.erajorma.model.Koordinaatit;

public class GpsFragment extends Fragment {

    private Erajorma erajorma;
    private Fragment fragment;

    public GpsFragment(Erajorma erajorma) {
        this.erajorma = erajorma;
    }

    @Override
    @RequiresApi(api = Build.VERSION_CODES.N)
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_gps, container, false);
    }

    @Override
    @RequiresApi(api = Build.VERSION_CODES.O)
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        CreateMarkerFragment(fragment);
        CreateButton(view);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void CreateButton(@NonNull View root) {
        Button button = root.findViewById(R.id.navigaattori);
        button.setOnClickListener(view -> getLocation(root));
    }

    private void checkLocationAccess(@NonNull View root) throws IllegalAccessException {
        boolean accessFineLocationError = PackageManager.PERMISSION_GRANTED != ActivityCompat.checkSelfPermission(root.getContext(), Manifest.permission.ACCESS_FINE_LOCATION);
        boolean accessCoarseLocationError = PackageManager.PERMISSION_GRANTED != ActivityCompat.checkSelfPermission(root.getContext(), Manifest.permission.ACCESS_COARSE_LOCATION);

        if (accessFineLocationError || accessCoarseLocationError) {
            throw new IllegalAccessException("Location permission was not granted.");
        }
    }

    @SuppressLint("MissingPermission")
    @RequiresApi(api = Build.VERSION_CODES.O)
    private void getLocation(@NonNull View root) {

        try {
            checkLocationAccess(root);

            Button button = root.findViewById(R.id.navigaattori);
            button.setEnabled(false);

            TextView textView = root.findViewById(R.id.koordinaatit);
            textView.setText("Receiving GPS signal...");

        } catch (IllegalAccessException e) {

            Button button = root.findViewById(R.id.navigaattori);
            button.setEnabled(true);

            TextView textView = root.findViewById(R.id.koordinaatit);
            textView.setText(e.getMessage());

            return;
        }

        FusedLocationProviderClient fusedLocationClient = LocationServices.getFusedLocationProviderClient(root.getContext());
        fusedLocationClient.getCurrentLocation(PRIORITY_HIGH_ACCURACY, null)
                .addOnSuccessListener(getActivity(), location -> {
                    if (location != null) {

                        double latitude = location.getLatitude();
                        double longitude = location.getLongitude();

                        double[] latDms = Koordinaatit.degreesToDms(latitude);
                        double[] lonDms = Koordinaatit.degreesToDms(longitude);

                        IKarttamerkki marker = new Karttamerkki("GPS", latDms, lonDms);
                        Date date = new Date(location.getTime());

                        erajorma.location = marker;
                        erajorma.time = date;

                        Button button = root.findViewById(R.id.navigaattori);
                        button.setEnabled(true);

                        TextView textView = root.findViewById(R.id.koordinaatit);
                        textView.setText("");

                        CreateMarkerFragment(marker);
                    }
                });
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void CreateMarkerFragment(IKarttamerkki marker) {
        MarkerFragment fragment = MarkerFragment.newInstance(marker, erajorma);
        CreateMarkerFragment(fragment);
    }

    private void CreateMarkerFragment(Fragment fragment) {
        if (fragment != null) {
            getChildFragmentManager()
                    .beginTransaction()
                    .replace(R.id.marker_fragment_container_view, fragment)
                    .commit();
            this.fragment = fragment;
        }
    }
}

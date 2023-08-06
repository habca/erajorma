package fi.linna.erajorma.view;

import static com.google.android.gms.location.LocationRequest.PRIORITY_HIGH_ACCURACY;

import android.Manifest;

import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import fi.linna.erajorma.R;
import fi.linna.erajorma.data.Karhunkierros;
import fi.linna.erajorma.data.Lemmenjoki;
import fi.linna.erajorma.data.PyhaLuosto;
import fi.linna.erajorma.model.IKarttamerkki;
import fi.linna.erajorma.model.Information;
import fi.linna.erajorma.model.Karttamerkki;
import fi.linna.erajorma.model.Koordinaatit;

public class MainActivity extends AppCompatActivity {

    private FusedLocationProviderClient fusedLocationClient;

    private void checkLocationAccess() throws IllegalAccessException {
        boolean accessFineLocationError = PackageManager.PERMISSION_GRANTED != ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION);
        boolean accessCoarseLocationError = PackageManager.PERMISSION_GRANTED != ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION);

        if (accessFineLocationError || accessCoarseLocationError) {
            throw new IllegalAccessException("Location permission was not granted.");
        }
    }

    @SuppressLint("MissingPermission")
    @RequiresApi(api = Build.VERSION_CODES.N)
    public void getLocation(View view) {

        try {
            checkLocationAccess();

            Button button = findViewById(R.id.navigaattori);
            button.setEnabled(false);

            TextView textView = findViewById(R.id.koordinaatit);
            textView.setText("Receiving GPS signal...");

        } catch (IllegalAccessException e) {

            Button button = findViewById(R.id.navigaattori);
            button.setEnabled(true);

            TextView textView = findViewById(R.id.koordinaatit);
            textView.setText(e.getMessage());

            return;
        }

        FusedLocationProviderClient fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        fusedLocationClient.getCurrentLocation(PRIORITY_HIGH_ACCURACY, null)
                .addOnSuccessListener(this, new OnSuccessListener<Location>() {
                    @RequiresApi(api = Build.VERSION_CODES.O)
                    @Override
                    public void onSuccess(Location location) {
                        if (location != null) {

                            double latitude = location.getLatitude();
                            double longitude = location.getLongitude();

                            Date date = new Date(location.getTime());

                            double[] latDms = Koordinaatit.degreesToDms(latitude);
                            double[] lonDms = Koordinaatit.degreesToDms(longitude);

                            IKarttamerkki result = new Karttamerkki("GPS", latDms, lonDms);

                            Information information = result.getInformation();
                            information.addInformation(new String[] { "Time", date.toString() });

                            Button button = findViewById(R.id.navigaattori);
                            button.setEnabled(true);

                            TextView textView = findViewById(R.id.koordinaatit);
                            textView.setText("");

                            CreateKarttamerkkiFragment(information);
                        }
                    }
                });
    }

    @Override
    @RequiresApi(api = Build.VERSION_CODES.O)
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        CreateButton();
        CreateSpinner();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void CreateKarttamerkkiFragment(Information information) {
        KarttamerkkiFragment fragment = KarttamerkkiFragment.newInstance(information);
        getSupportFragmentManager()
                .beginTransaction()
                .setReorderingAllowed(false)
                .add(R.id.karttamerkki_fragment_container_view, fragment)
                .commit();
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void CreateSpinner() {
        List<String> maastokartat = new ArrayList<>();
        maastokartat.add(Karhunkierros.class.getCanonicalName());
        maastokartat.add(Lemmenjoki.class.getCanonicalName());
        maastokartat.add(PyhaLuosto.class.getCanonicalName());
        maastokartat.sort(Karttamerkki.comparator());

        Spinner menu = findViewById(R.id.maastokartat);
        menu.setAdapter(new ArrayAdapter<>(
                this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, maastokartat
        ));

        menu.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                CreateListView(maastokartat.get(i));
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                CreateListView("");
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void CreateButton() {
        Button button = findViewById(R.id.navigaattori);
        button.setOnClickListener(this::getLocation);
        button.setText("GPS");
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void CreateListView(String maastokartta) {
        List<IKarttamerkki> karttamerkit = new ArrayList<>();

        if (Karhunkierros.class.getCanonicalName().equals(maastokartta)) {
            karttamerkit.addAll(new Karhunkierros());
        } else if (Lemmenjoki.class.getCanonicalName().equals(maastokartta)) {
            karttamerkit.addAll(new Lemmenjoki());
        } else if (PyhaLuosto.class.getCanonicalName().equals(maastokartta)) {
            karttamerkit.addAll(new PyhaLuosto());
        }

        karttamerkit.sort(Comparator.naturalOrder());

        ArrayAdapter<IKarttamerkki> adapter = new ArrayAdapter<>(
                this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, karttamerkit
        );

        ListView view = findViewById(R.id.karttamerkit);
        view.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }
}
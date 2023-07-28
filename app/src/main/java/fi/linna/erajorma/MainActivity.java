package fi.linna.erajorma;

import static com.google.android.gms.location.LocationRequest.PRIORITY_HIGH_ACCURACY;

import android.Manifest;

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

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import maastokartat.Karhunkierros;
import maastokartat.Lemmenjoki;
import maastokartat.PyhaLuosto;

public class MainActivity extends AppCompatActivity {

    private FusedLocationProviderClient fusedLocationClient;

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void getLocation(View view) {
        requestLocationAccess();

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }

        Button button = (Button) findViewById(R.id.navigaattori);
        button.setEnabled(false);


        TextView textView = (TextView) findViewById(R.id.koordinaatit);
        textView.setText("Receiving GPS signal...");

        fusedLocationClient.getCurrentLocation(PRIORITY_HIGH_ACCURACY, null)
                .addOnSuccessListener(this, new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        // Got last known location. In some rare situations this can be null.
                        if (location != null) {
                            // Logic to handle location object

                            // Get the latitude in degrees.
                            double latitude = location.getLatitude();

                            // Get the longitude in degrees.
                            double longitude = location.getLongitude();

                            // Returns the estimated horizontal accuracy radius in meters of this location at the 68th percentile confidence level.
                            int accuracy = Math.round(location.getAccuracy());

                            long millis = location.getTime();
                            Date date = new Date(millis);

                            double[] result = Projektiokaavat.degreesToMeters(latitude, longitude);
                            double N = result[0];
                            double E = result[1];

                            String coordinates = "N " + latitude + ", E " + longitude + " (WGS84)" + "\n";
                            coordinates += "N " + N + ", E " + E + " (ETRS-TM35FIN)" + "\n";
                            coordinates += date.toString() + "\n";
                            coordinates += accuracy + " m accuracy with 68 % confidence";

                            textView.setText(coordinates);
                            button.setEnabled(true);
                        }
                    }
                });
    }

    @Override
    @RequiresApi(api = Build.VERSION_CODES.N)
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        CreateButton();
        CreateSpinner();
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

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void requestLocationAccess() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_DENIED) {
            ActivityResultLauncher<String[]> locationPermissionRequest =
                    registerForActivityResult(new ActivityResultContracts
                                    .RequestMultiplePermissions(), result -> {
                                Boolean fineLocationGranted = result.getOrDefault(
                                        Manifest.permission.ACCESS_FINE_LOCATION, false);
                                Boolean coarseLocationGranted = result.getOrDefault(
                                        Manifest.permission.ACCESS_COARSE_LOCATION,false);
                                if (fineLocationGranted != null && fineLocationGranted) {
                                    // Precise location access granted.
                                } else if (coarseLocationGranted != null && coarseLocationGranted) {
                                    // Only approximate location access granted.
                                } else {
                                    // No location access granted.
                                }
                            }
                    );

            // Before you perform the actual permission request, check whether your app
            // already has the permissions, and whether your app needs to show a permission
            // rationale dialog. For more details, see Request permissions.
            locationPermissionRequest.launch(new String[] {
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION
            });
        }
    }
}
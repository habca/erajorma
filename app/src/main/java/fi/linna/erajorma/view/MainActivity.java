package fi.linna.erajorma.view;

import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.time.chrono.Era;
import java.util.HashMap;
import java.util.Map;

import fi.linna.erajorma.R;
import fi.linna.erajorma.model.Erajorma;

public class MainActivity extends AppCompatActivity {

    private Erajorma erajorma = new Erajorma();
    private Map<Integer, Fragment> fragments = new HashMap<>();

    @Override
    @RequiresApi(api = Build.VERSION_CODES.O)
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fragments.put(R.id.navigation_menu_gps, new GpsFragment(erajorma));
        fragments.put(R.id.navigation_menu_map, new MapFragment(erajorma));

        BottomNavigationView navigationMenu = findViewById(R.id.bottomNavigationView);
        navigationMenu.setOnItemSelectedListener(this::onNavigationItemSelected);
        navigationMenu.setSelectedItemId(R.id.navigation_menu_gps);
    }

    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Fragment fragment = fragments.get(item.getItemId());
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container_view, fragment)
                .commit();
        return true;
    }
}
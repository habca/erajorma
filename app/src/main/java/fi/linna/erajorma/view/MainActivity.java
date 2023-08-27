package fi.linna.erajorma.view;

import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import fi.linna.erajorma.R;

public class MainActivity extends AppCompatActivity {

    @Override
    @RequiresApi(api = Build.VERSION_CODES.O)
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView navigationMenu = findViewById(R.id.bottomNavigationView);
        navigationMenu.setOnItemSelectedListener(this::onNavigationItemSelected);
    }

    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.navigation_menu_gps) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.karttamerkki_fragment_container_view, new GpsFragment())
                    .commit();
            return true;
        }

        if (id == R.id.navigation_menu_map) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.karttamerkki_fragment_container_view, new MapFragment())
                    .commit();
            return true;
        }

        return false;
    }
}
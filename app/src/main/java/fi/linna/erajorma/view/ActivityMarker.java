package fi.linna.erajorma.view;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;

import fi.linna.erajorma.R;
import fi.linna.erajorma.model.Information;

public class ActivityMarker extends AppCompatActivity {

    @Override
    @RequiresApi(api = Build.VERSION_CODES.O)
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_marker);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();
        String value = intent.getStringExtra("key");
        Information information = FragmentSerializer.Deserialize(value);

        KarttamerkkiFragment fragment = KarttamerkkiFragment.newInstance(information);
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container_view_marker, fragment)
                .addToBackStack(null)
                .commit();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }

        return super.onOptionsItemSelected(item);
    }
}
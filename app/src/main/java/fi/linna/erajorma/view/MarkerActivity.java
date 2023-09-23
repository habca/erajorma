package fi.linna.erajorma.view;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;

import fi.linna.erajorma.R;
import fi.linna.erajorma.model.Erajorma;
import fi.linna.erajorma.model.IKarttamerkki;

public class MarkerActivity extends AppCompatActivity {

    @Override
    @RequiresApi(api = Build.VERSION_CODES.O)
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_marker);

        Intent intent = getIntent();
        String value = intent.getStringExtra(MarkerFragment.ARG_MARKER);
        IKarttamerkki marker = FragmentSerializer.Deserialize(value);

        value = intent.getStringExtra(MarkerFragment.ARG_USER);
        Erajorma erajorma = FragmentSerializer.Deserialize(value);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(marker.getName());

        MarkerFragment fragment = MarkerFragment.newInstance(marker, erajorma);
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
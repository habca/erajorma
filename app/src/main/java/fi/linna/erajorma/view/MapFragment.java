package fi.linna.erajorma.view;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import fi.linna.erajorma.R;
import fi.linna.erajorma.data.Karhunkierros;
import fi.linna.erajorma.data.Lemmenjoki;
import fi.linna.erajorma.data.PallasHettaOlos;
import fi.linna.erajorma.data.PyhaLuosto;
import fi.linna.erajorma.model.IKarttamerkki;
import fi.linna.erajorma.model.Karttamerkki;

public class MapFragment extends Fragment {

    @Override
    @RequiresApi(api = Build.VERSION_CODES.N)
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_map, container, false);
    }

    @Override
    @RequiresApi(api = Build.VERSION_CODES.N)
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        CreateSpinner(view);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void CreateSpinner(@NonNull View root) {
        List<String> maps = new ArrayList<>();
        maps.add(Karhunkierros.class.getCanonicalName());
        maps.add(Lemmenjoki.class.getCanonicalName());
        maps.add(PyhaLuosto.class.getCanonicalName());
        maps.add(PallasHettaOlos.class.getCanonicalName());
        maps.sort(Karttamerkki.comparator());

        Spinner menu = root.findViewById(R.id.maps_spinner);
        menu.setAdapter(new ArrayAdapter<>(
                root.getContext(), androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, maps
        ));

        menu.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                CreateListView(root, maps.get(i));
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                CreateListView(root, "");
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void CreateListView(@NonNull View root, String maastokartta) {
        List<IKarttamerkki> markers = new ArrayList<>();

        if (Karhunkierros.class.getCanonicalName().equals(maastokartta)) {
            markers.addAll(new Karhunkierros());
        }
        if (Lemmenjoki.class.getCanonicalName().equals(maastokartta)) {
            markers.addAll(new Lemmenjoki());
        }
        if (PallasHettaOlos.class.getCanonicalName().equals(maastokartta)) {
            markers.addAll(new PallasHettaOlos());
        }
        if (PyhaLuosto.class.getCanonicalName().equals(maastokartta)) {
            markers.addAll(new PyhaLuosto());
        }

        markers.sort(Comparator.naturalOrder());

        ArrayAdapter<IKarttamerkki> adapter = new ArrayAdapter<>(
                root.getContext(), androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, markers
        );

        ListView markersView = root.findViewById(R.id.markers_list_view);
        markersView.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        markersView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            @RequiresApi(api = Build.VERSION_CODES.O)
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                IKarttamerkki marker = markers.get(i);
                String serializedObject = FragmentSerializer.Serialize(marker);

                Intent myIntent = new Intent(getActivity(), MarkerActivity.class);
                myIntent.putExtra(MarkerFragment.ARG_MARKER, serializedObject);
                getActivity().startActivity(myIntent);
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void CreateMarkerFragment(IKarttamerkki marker) {
        MarkerFragment fragment = MarkerFragment.newInstance(marker);
        CreateMarkerFragment(fragment);
    }

    private void CreateMarkerFragment(Fragment fragment) {
        if (fragment != null) {
            getChildFragmentManager()
                    .beginTransaction()
                    .replace(R.id.marker_fragment_container_view, fragment)
                    .commit();
        }
    }
}

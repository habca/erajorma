package fi.linna.erajorma.view;

import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import fi.linna.erajorma.R;
import fi.linna.erajorma.model.IKarttamerkki;

public class MarkerFragment extends Fragment {

    public static final String ARG_MARKER = IKarttamerkki.class.getName();

    private IKarttamerkki marker;

    private MarkerFragment() {
        // Prevent default constructor.
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public static MarkerFragment newInstance(IKarttamerkki marker) {
        MarkerFragment fragment = new MarkerFragment();
        Bundle args = new Bundle();

        String serializedObject = FragmentSerializer.Serialize(marker);
        args.putSerializable(ARG_MARKER, serializedObject);

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    @RequiresApi(api = Build.VERSION_CODES.O)
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        String serializedObject = getArguments().getString(ARG_MARKER);
        marker = FragmentSerializer.Deserialize(serializedObject);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return Initialize(inflater.inflate(R.layout.fragment_marker, container, false));
    }

    private View Initialize(View view) {
        LinearLayout layout = view.findViewById(R.id.marker_fragment_list);

        View markerView = new MarkerView(view.getContext(), marker);
        layout.addView(markerView);

        return view;
    }
}
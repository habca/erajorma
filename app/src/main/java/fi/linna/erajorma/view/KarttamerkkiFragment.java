package fi.linna.erajorma.view;

import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import fi.linna.erajorma.R;
import fi.linna.erajorma.model.Information;

public class KarttamerkkiFragment extends Fragment {

    private static final String ARG_KARTTAMERKKI = Information.class.getName();

    private Information information;

    @RequiresApi(api = Build.VERSION_CODES.O)
    public static KarttamerkkiFragment newInstance(Information information) {
        KarttamerkkiFragment fragment = new KarttamerkkiFragment();
        FragmentSerializer.Serialize(fragment, ARG_KARTTAMERKKI, information);
        return fragment;
    }

    @Override
    @RequiresApi(api = Build.VERSION_CODES.O)
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        information = FragmentSerializer.Deserialize(this, ARG_KARTTAMERKKI);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return Initialize(inflater.inflate(R.layout.fragment_karttamerkki, container, false));
    }

    private View Initialize(View view) {
        LinearLayout layout = view.findViewById(R.id.karttamerkki_fragment_list);

        for (String[] info : information.information) {
            String text = String.format("%s: %s", info[0], info[1]);

            TextView row = new TextView(view.getContext());
            row.setText(text);
            layout.addView(row);
        }

        return view;
    }
}
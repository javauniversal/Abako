package co.dito.abako.abako.Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import co.dito.abako.abako.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentInformacion extends Fragment {


    public FragmentInformacion() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_informacion, container, false);

        return view;
    }

}

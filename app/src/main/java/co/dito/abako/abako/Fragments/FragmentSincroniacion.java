package co.dito.abako.abako.Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import co.dito.abako.abako.R;


public class FragmentSincroniacion extends Fragment {


    public FragmentSincroniacion() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_sincroniacion, container, false);

        return view;
    }

}

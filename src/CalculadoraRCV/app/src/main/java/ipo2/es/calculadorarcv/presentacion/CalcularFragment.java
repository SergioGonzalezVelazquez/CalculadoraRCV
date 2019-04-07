package ipo2.es.calculadorarcv.presentacion;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import java.util.ArrayList;

import ipo2.es.calculadorarcv.R;
import ipo2.es.calculadorarcv.dominio.CalculoRCV;


public class CalcularFragment extends Fragment {


    private OnFragmentInteractionListener mListener;
    private Spinner spinnerTAS;
    private String[] valoresTAS;
    private Spinner spinnerTAD;
    private String[] valoresTAD;
    private Spinner spinnerFisica;
    private String[] valoresFisica;
    private Spinner spinnerLDL;
    private String[] valoresLDL;
    private Spinner spinnerHDL;
    private String[] valoresHDL;

    public CalcularFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment CalcularFragment.
     */
    /* TODO: Rename and change types and number of parameters
    public static CalcularFragment newInstance(String param1, String param2) {
        CalcularFragment fragment = new CalcularFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }
    */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
           // this.calculoRCV = getArguments().getBinder("calculo");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_calcular, container, false);

        spinnerTAS = view.findViewById(R.id.spinner_TAS);
        valoresTAS = getValores(90,190, "mmHg");
        ArrayAdapter<String> adapterTAS = new ArrayAdapter<>(this.getActivity(),
                android.R.layout.simple_spinner_item, valoresTAS);
        spinnerTAS.setAdapter(adapterTAS);


        spinnerTAD = view.findViewById(R.id.spinner_TAD);
        valoresTAD = getValores(46,105, "mmHg");
        ArrayAdapter<String> adapterTAD = new ArrayAdapter<>(this.getActivity(),
                android.R.layout.simple_spinner_item, valoresTAD);
        spinnerTAD.setAdapter(adapterTAD);

        spinnerFisica = view.findViewById(R.id.spinner_Fisica);
        valoresFisica = new String[]{"Seleccione un valor", "No, no práctico ningún deporte",
                "Sí, mínimo una vez al mes", "Sí, minimo una vez por semana",
                "Sí, mínimo dos veces por semana"};
        ArrayAdapter<String> adapterFisica = new ArrayAdapter<>(this.getActivity(),
                android.R.layout.simple_spinner_item, valoresFisica);
        spinnerFisica.setAdapter(adapterFisica);

        spinnerLDL = view.findViewById(R.id.spinner_LDL);
        valoresLDL = getValores(20,200, "mg/dl");
        ArrayAdapter<String> adapterLDL = new ArrayAdapter<>(this.getActivity(),
                android.R.layout.simple_spinner_item, valoresLDL);
        spinnerLDL.setAdapter(adapterLDL);

        spinnerHDL = view.findViewById(R.id.spinner_HDL);
        valoresHDL = getValores(25,200, "mg/dl");
        ArrayAdapter<String> adapterHDL = new ArrayAdapter<>(this.getActivity(),
                android.R.layout.simple_spinner_item, valoresHDL);
        spinnerHDL.setAdapter(adapterHDL);


        return view;
    }

    private String [] getValores(int min, int max, String unidad){
        ArrayList<String> valores = new ArrayList<String>();
        valores.add("Seleccione un valor");

        for(int i = min;i<=max;i++){
            valores.add(i + " "+ unidad);
        }

        return valores.toArray(new String[valores.size()]);
    }



    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}

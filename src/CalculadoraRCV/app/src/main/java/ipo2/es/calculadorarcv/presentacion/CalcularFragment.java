package ipo2.es.calculadorarcv.presentacion;

import android.content.Context;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;

import java.util.ArrayList;

import ipo2.es.calculadorarcv.R;
import ipo2.es.calculadorarcv.dominio.CalculoRCV;
import ipo2.es.calculadorarcv.dominio.Usuario;
import ipo2.es.calculadorarcv.persistencia.ConectorBD;


public class CalcularFragment extends Fragment {


    private OnFragmentInteractionListener mListener;

    private Usuario usuario;
    private ConectorBD conectorBD;

    //Spinners
    private Spinner spinnerTAS;
    private Spinner spinnerTAD;
    private Spinner spinnerFisica;
    private Spinner spinnerColesterol;
    private Spinner spinnerHDL;

    //EditText
    private EditText lblAltura;
    private EditText lblPeso;

    //Switch
    private Switch swTabaco;
    private Switch swDiabetes;
    private Switch swHipertension;
    private Switch swHipertrofia;

    //BtnCalcular
    private Button btnCalcular;



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
            this.usuario = (Usuario) getArguments().getSerializable("usuario");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_calcular, container, false);

        initViews(view);
        initListeners();

        return view;
    }

    private void initListeners() {
        btnCalcular.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final CalculoRCV calculoRCV = getValores();
                if(calculoRCV != null){
                    conectorBD.insertarCalculo(calculoRCV);

                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                    builder.setTitle("Completado");
                    builder.setMessage("Se ha calculado el Riesgo Cardiovascular en función de los" +
                            " parámetros introducidos");

                    // Set click listener for alert dialog buttons
                    DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            usuario.nuevoCalculo(calculoRCV);
                            EstadoFragment fr=new EstadoFragment();
                            Bundle bundle = new Bundle();
                            bundle.putSerializable("calculo", calculoRCV);
                            fr.setArguments(bundle);
                            getActivity().getSupportFragmentManager().beginTransaction()
                                    .replace(R.id.frame_container,fr)
                                    .addToBackStack(null)
                                    .commit();
                        }
                    };
                    builder.setPositiveButton("Ver resultado",dialogClickListener);
                    builder.setIcon(android.R.drawable.ic_menu_save);
                    builder.create();
                    builder.show();


                }
            }
        });


    }

    private void initViews(View view) {
        conectorBD = new ConectorBD(getActivity());
        lblAltura= view.findViewById(R.id.lblAltura);
        lblPeso = view.findViewById(R.id.lblPeso);
        swTabaco=view.findViewById(R.id.swTabaco);
        swDiabetes = view.findViewById(R.id.swDiabetes);
        swHipertension = view.findViewById(R.id.swHipertension);
        swHipertrofia = view.findViewById(R.id.swHipertrofia);

        //SPINNERS
        spinnerTAS = view.findViewById(R.id.spinner_TAS);
        String [] valoresTAS = setValoresSpinner(90,190, "mmHg");
        ArrayAdapter<String> adapterTAS = new ArrayAdapter<>(this.getActivity(),
                android.R.layout.simple_spinner_item, valoresTAS);
        spinnerTAS.setAdapter(adapterTAS);


        spinnerTAD = view.findViewById(R.id.spinner_TAD);
        String [] valoresTAD = setValoresSpinner(46,105, "mmHg");
        ArrayAdapter<String> adapterTAD = new ArrayAdapter<>(this.getActivity(),
                android.R.layout.simple_spinner_item, valoresTAD);
        spinnerTAD.setAdapter(adapterTAD);

        spinnerFisica = view.findViewById(R.id.spinner_Fisica);
        String [] valoresFisica = new String[]{"Seleccione un valor", "No, no práctico ningún deporte",
                "Sí, mínimo una vez al mes", "Sí, minimo una vez por semana",
                "Sí, mínimo dos veces por semana"};
        ArrayAdapter<String> adapterFisica = new ArrayAdapter<>(this.getActivity(),
                android.R.layout.simple_spinner_item, valoresFisica);
        spinnerFisica.setAdapter(adapterFisica);

        spinnerColesterol = view.findViewById(R.id.spinner_colTotal);
        String [] valoresColTotal = setValoresSpinner(139,330, "mg/dl");
        ArrayAdapter<String> adapterLDL = new ArrayAdapter<>(this.getActivity(),
                android.R.layout.simple_spinner_item, valoresColTotal);
        spinnerColesterol.setAdapter(adapterLDL);

        spinnerHDL = view.findViewById(R.id.spinner_HDL);
        String [] valoresHDL = setValoresSpinner(25,100, "mg/dl");
        ArrayAdapter<String> adapterHDL = new ArrayAdapter<>(this.getActivity(),
                android.R.layout.simple_spinner_item, valoresHDL);
        spinnerHDL.setAdapter(adapterHDL);

        btnCalcular = view.findViewById(R.id.btnCalcular);
    }

    private CalculoRCV getValores(){
        boolean fumador, diabetes, hvi, hipertension;
        double peso;
        int altura; //CAMBIAR EN CLASE CALCULO
        int actividadFisica, tensionDiastolica, tensionSiastolica, colHDL,  colTotal;

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Atención");
        builder.setPositiveButton("OK",null);

        //PESO
        if (lblPeso.getText().toString().trim().length() > 0) {
            peso = Double.valueOf(lblPeso.getText().toString());
            Log.d("Debug_SCORE","Peso: " + peso);
        } else {
            builder.setMessage("No ha introducido el peso");
            builder.create();
            builder.show();
            return null;
        }

        //ALTURA
        if (lblAltura.getText().toString().trim().length() > 0) {
            altura = Integer.valueOf(lblAltura.getText().toString());
            Log.d("Debug_SCORE","Peso: " + altura);
        } else {
            builder.setMessage("No ha introducido la altura");
            builder.create();
            builder.show();
            return null;
        }

        //ACTIVIDAD FÍSICA
        if(spinnerFisica.getSelectedItemPosition() == 0){
            builder.setMessage("No ha seleccionado su actividad física");
            builder.create();
            builder.show();
            return null;
        }
        else{
            actividadFisica = spinnerFisica.getSelectedItemPosition();
        }

        //TAS
        if(spinnerTAS.getSelectedItemPosition() == 0){
            builder.setMessage("No ha introducido su Tensión Arterial Siastólica (TAS)");
            builder.create();
            builder.show();
            return null;
        }
        else{
            String numCadena = spinnerTAS.getSelectedItem().toString().split(" ")[0];
            tensionSiastolica = Integer.parseInt(numCadena);
            Log.d("Debug_SCORE","TAS: " + tensionSiastolica);
        }

        //TAD
        if(spinnerTAD.getSelectedItemPosition() == 0){
            builder.setMessage("No ha introducido su Tensión Arterial Diastólica (TAD)");
            builder.create();
            builder.show();
            return null;
        }
        else{
            String numCadena = spinnerTAD.getSelectedItem().toString().split(" ")[0];
            tensionDiastolica = Integer.parseInt(numCadena);
            Log.d("Debug_SCORE","TAD: " + tensionDiastolica);
        }
        //HDL
        if(spinnerHDL.getSelectedItemPosition() == 0){
            builder.setMessage("No ha introducido su colesterol HDL");
            builder.create();
            builder.show();
            return null;
        }
        else {
            String numCadena = spinnerHDL.getSelectedItem().toString().split(" ")[0];
            colHDL = Integer.parseInt(numCadena);
            Log.d("Debug_SCORE","HDL: " + colHDL);
        }
        //LDL
        if(spinnerColesterol.getSelectedItemPosition() == 0){
            builder.setMessage("No ha introducido su colesterol total");
            builder.create();
            builder.show();
            return null;
        }
        else {
            String numCadena = spinnerColesterol.getSelectedItem().toString().split(" ")[0];
            colTotal = Integer.parseInt(numCadena);
            Log.d("Debug_SCORE","Col Total: " + colTotal);
        }

        //Tabaquismo
        fumador = swTabaco.isChecked();
        //Diabetes
        diabetes = swDiabetes.isChecked();
        //Hipertensión
        hipertension = swHipertension.isChecked();
        //Hipertrofia
        hvi = swHipertrofia.isChecked();


        CalculoRCV calculo = new CalculoRCV(this.usuario, fumador, diabetes, hvi, hipertension,  peso, altura,
                actividadFisica, tensionDiastolica, tensionSiastolica, colHDL, colTotal, null);

        return calculo;
    }

    private String [] setValoresSpinner(int min, int max, String unidad){
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

package ipo2.es.calculadorarcv.presentacion;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import ipo2.es.calculadorarcv.R;
import ipo2.es.calculadorarcv.dominio.CalculoRCV;
import ipo2.es.calculadorarcv.dominio.FactorRiesgo;
import ipo2.es.calculadorarcv.dominio.Usuario;
import lecho.lib.hellocharts.model.PieChartData;
import lecho.lib.hellocharts.model.SliceValue;
import lecho.lib.hellocharts.view.PieChartView;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link EstadoFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {EstadoFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class EstadoFragment extends Fragment implements AdaptadorListaFactor.OnItemSelectedListener{
    // TODO: Rename parameter arguments, choose names that match

    private OnFragmentInteractionListener mListener;

    private RecyclerView lstFactores;
    private AdaptadorListaFactor adaptador;
    private Usuario usuario;
    PieChartView pieChartView;

    private CalculoRCV ultimoRCV;
    private ArrayList<FactorRiesgo> factores;
    private TextView lblScore;
    private TextView lblRisk;
    private TextView txtFechaCalculo;
    private TextView lblValoracion;

    private int factorSeleccionado;



    public EstadoFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
           this.ultimoRCV = (CalculoRCV) getArguments().getSerializable("calculo");

        }


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_estado, container, false);
        initViews(view);
        setValores();


        return view;
    }

    private void setValores() {

        if(this.ultimoRCV != null){
            //LISTADO FACTORES
            factores = ultimoRCV.getFactores();
            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
            lstFactores.addItemDecoration(new DividerItemDecoration(getActivity(), LinearLayoutManager.VERTICAL));
            lstFactores.setLayoutManager(mLayoutManager);
            adaptador = new AdaptadorListaFactor(factores, this);
            lstFactores.setAdapter(adaptador);

            //PIE CHART
            //https://www.codingdemos.com/android-pie-chart-tutorial/
            List<SliceValue> pieData = new ArrayList<>();
            pieData.add(new SliceValue((float)this.ultimoRCV.getScore(),
                    Color.parseColor("#b71c1c")));
            pieData.add(new SliceValue(100-(float)this.ultimoRCV.getScore(), Color.LTGRAY));
            PieChartData pieChartData = new PieChartData(pieData);
            pieChartData.setHasCenterCircle(true).setCenterText1("").setCenterText1FontSize(20).
                    setCenterText1Color(Color.parseColor("#0097A7"));
            pieChartView.setPieChartData(pieChartData);

            //LABELS
            //lblScore.setText(String.format("%.2f%",this.ultimoRCV.getScore()));
            lblScore.setText(String.valueOf(this.ultimoRCV.getScore())+" puntos");
            lblRisk.setText(String.valueOf(this.ultimoRCV.getRisk())+"%");
            txtFechaCalculo.setText("Cálculo realizado el "+this.ultimoRCV.getFecha());
            lblValoracion.setText(this.ultimoRCV.getValoracion());

            Log.d("DEBUG_Calculo","Calculo: " + ultimoRCV.toString());

        }

    }

    private void initViews(View view) {
        // Inflate the layout for this fragment
        lstFactores = view.findViewById(R.id.lstFactores);
        txtFechaCalculo = view.findViewById(R.id.txtFechaCalculo);
        lblValoracion = view.findViewById(R.id.lblValoracion);
        pieChartView = view.findViewById(R.id.chartScore);
        lblRisk = view.findViewById(R.id.lblRisk);
        lblScore = view.findViewById(R.id.lblScore);

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

    @Override
    public void onFactorSeleccionado(int posicion) {
        Log.d("Debug_ESTADO", "Factor seleccionado: "+
                factores.get(posicion).getNombre());
        Intent j = new Intent(getActivity(), RecomendacionesActivity.class);
        switch (posicion){

            case 0: //Tabaquismo
                j.putExtra("titulo1",getText(R.string.factorTabaco));
                j.putExtra("info1",getText(R.string.efectoTabaco));
                j.putExtra("titulo2",getText(R.string.consejosFumar));
                j.putExtra("info2",getText(R.string.dejarFumar));
                startActivity(j);
                break;

            case 1://Actividad Física
                j.putExtra("titulo1",getText(R.string.factorActividadFisica));
                j.putExtra("info1",getText(R.string.efectoActividadFisica));
                j.putExtra("titulo2",getText(R.string.trucosActividadFisica));
                j.putExtra("info2",getText(R.string.infoActividadFisica));
                startActivity(j);
                break;

            case 2://Tensión Arterial
                j.putExtra("titulo1",getText(R.string.factorTA));
                j.putExtra("info1",getText(R.string.taInfo1));
                j.putExtra("titulo2",getText(R.string.trucosTA));
                j.putExtra("info2",getText(R.string.taInfo2));
                startActivity(j);
                break;

            case 3://Colesterol
                j.putExtra("titulo1",getText(R.string.factorColesterol));
                j.putExtra("info1",getText(R.string.colesterolInfo1));
                j.putExtra("titulo2",getText(R.string.recomendacionesColesterol));
                j.putExtra("info2",getText(R.string.colesterolInfo2));
                startActivity(j);
                break;

            case 4://Peso Ideal
                j.putExtra("titulo1",getText(R.string.IMC));
                j.putExtra("info1",getText(R.string.IMCinfo1));
                j.putExtra("titulo2",getText(R.string.infoPesoIdeal));
                j.putExtra("info2",getText(R.string.IMCinfo2));
                startActivity(j);
                break;
        }

    }

    @Override
    public void onMenuContextualContacto(int posicion, MenuItem menu) {

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

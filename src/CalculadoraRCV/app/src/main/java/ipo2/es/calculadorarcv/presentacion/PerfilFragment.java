package ipo2.es.calculadorarcv.presentacion;

import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import ipo2.es.calculadorarcv.R;
import ipo2.es.calculadorarcv.dominio.CalculoRCV;
import ipo2.es.calculadorarcv.dominio.Usuario;
import lecho.lib.hellocharts.model.Axis;
import lecho.lib.hellocharts.model.AxisValue;
import lecho.lib.hellocharts.model.Line;
import lecho.lib.hellocharts.model.LineChartData;
import lecho.lib.hellocharts.model.PointValue;
import lecho.lib.hellocharts.model.Viewport;
import lecho.lib.hellocharts.view.LineChartView;

public class PerfilFragment extends Fragment {

    private Usuario usuario;

    private TextView lbl_user_name;
    private TextView lblCorreo;
    private TextView lblFechaNacimiento;
    private TextView lblUltimoAcceso;
    private ImageButton btnEditar;
    private CircleImageView profile_image;
    private LineChartView chartHistorial;


    private OnFragmentInteractionListener mListener;

    public PerfilFragment() {
        // Required empty public constructor
    }


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
        View view = inflater.inflate(R.layout.fragment_perfil, container, false);

        initViews(view);
        initListeners();
        setValores();

        return view;
    }

    private void initListeners() {
        btnEditar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(),
                        "Funcionalidad no implementada",Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setValores() {
        //LABELS
        this.lbl_user_name.setText(this.usuario.getNombre() + " " + this.usuario.getApellidos());
        this.lblCorreo.setText(this.usuario.getEmail());
        this.lblUltimoAcceso.setText(this.usuario.getUltimoAcceso());
        this.lblFechaNacimiento.setText(this.usuario.getFechaNacimiento() +
                "\t(" + this.usuario.getEdad() +  " años)");

        //Foto
        if (!usuario.getFoto().equals("")){
            this.profile_image.setImageResource(R.drawable.uclm);
        }

        //GRAFICO CALCULOS
        drawChart();
    }

    private void initViews(View view) {
        // Inflate the layout for this fragment
        btnEditar = view.findViewById(R.id.btnEditar);
        chartHistorial = view.findViewById(R.id.chartHistorial);
        lbl_user_name= view.findViewById(R.id.lbl_user_name);
        lblCorreo= view.findViewById(R.id.lblCorreo);
        lblFechaNacimiento = view.findViewById(R.id.lblFechaNacimiento);
        lblUltimoAcceso = view.findViewById(R.id.lblUltimoAcceso);
        profile_image = view.findViewById(R.id.profile_image);
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    private void drawChart(){
        //https://www.codingdemos.com/draw-android-line-chart/
        ArrayList<CalculoRCV> calculos = usuario.getCalculosRCV();
        Log.d("DEBUG_Calculo", calculos.size()+" cálculos");

        int[] yAxisData = {0, 5, 10, 15, 20, 25, 30};

        List yAxisValues = new ArrayList();
        List axisValues = new ArrayList();


        Line line = new Line(yAxisValues).setColor(getResources().getColor(R.color.colorPrimary));

        //add Axis and Y-Axis data inside yAxisValues and axisValues lists
        for (int i = 0; i < calculos.size(); i++) {
            axisValues.add(i, new AxisValue(i).setLabel(calculos.get(i).getFecha()));
        }

        for (int i = 0; i < calculos.size(); i++) {
            Log.d("DEBUG_Calculo", "Calculo "+i+": "+calculos.get(i).getScore());

            yAxisValues.add(new PointValue(i, calculos.get(i).getScore()));
        }

        List lines = new ArrayList();
        lines.add(line);

        LineChartData data = new LineChartData();
        data.setLines(lines);

        Axis axis = new Axis();
        axis.setValues(axisValues);
        axis.setTextSize(13);
        axis.setTextColor(getResources().getColor(R.color.colorAccent));
        data.setAxisXBottom(axis);

        Axis yAxis = new Axis();
        yAxis.setName("Score");
        yAxis.setTextColor(getResources().getColor(R.color.colorAccent));
        yAxis.setTextSize(13);
        data.setAxisYLeft(yAxis);

        chartHistorial.setLineChartData(data);
        Viewport viewport = new Viewport(chartHistorial.getMaximumViewport());
        viewport.top = 30;
        chartHistorial.setMaximumViewport(viewport);
        chartHistorial.setCurrentViewport(viewport);

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

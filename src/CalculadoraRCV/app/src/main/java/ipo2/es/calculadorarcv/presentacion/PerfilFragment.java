package ipo2.es.calculadorarcv.presentacion;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import ipo2.es.calculadorarcv.R;
import ipo2.es.calculadorarcv.dominio.Usuario;

public class PerfilFragment extends Fragment {

    private Usuario usuario;

    private TextView lbl_user_name;
    private TextView lblCorreo;
    private TextView lblFechaNacimiento;
    private TextView lblUltimoAcceso;
    private ImageButton btnEditar;


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

        // Inflate the layout for this fragment
        btnEditar = view.findViewById(R.id.btnEditar);
        lbl_user_name= view.findViewById(R.id.lbl_user_name);
        lblCorreo= view.findViewById(R.id.lblCorreo);
        lblFechaNacimiento = view.findViewById(R.id.lblFechaNacimiento);
        lblUltimoAcceso = view.findViewById(R.id.lblUltimoAcceso);

        //LABELS
        this.lbl_user_name.setText(this.usuario.getNombre() + " " + this.usuario.getApellidos());
        this.lblCorreo.setText(this.usuario.getEmail());
        this.lblUltimoAcceso.setText(this.usuario.getUltimoAcceso());
        this.lblFechaNacimiento.setText(this.usuario.getFechaNacimiento());


        return view;
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

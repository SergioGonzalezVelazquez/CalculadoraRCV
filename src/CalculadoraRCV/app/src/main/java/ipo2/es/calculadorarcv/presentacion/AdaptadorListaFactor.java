package ipo2.es.calculadorarcv.presentacion;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import ipo2.es.calculadorarcv.R;
import ipo2.es.calculadorarcv.dominio.FactorRiesgo;


public class AdaptadorListaFactor extends RecyclerView.Adapter<AdaptadorListaFactor.ViewHolder>{

    //Agenda
    private OnItemSelectedListener listener;

    //
    public interface OnItemSelectedListener {
        void onFactorSeleccionado(int posicion);
        void onMenuContextualContacto(int posicion, MenuItem menu);
    }

    private ArrayList<FactorRiesgo> factores;
    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private TextView txtNombre;
        private TextView txtValor;
        private ImageView iconEstado;
        ViewHolder(View view) {
            super(view);
            txtNombre = view.findViewById(R.id.txtNombre);
            txtValor = view.findViewById(R.id.txtValor);
            iconEstado = view.findViewById(R.id.iconEstado);

            //asociar el oyente a la vista
            view.setOnClickListener(this);


        }

        @Override
        public void onClick(View v) {
            int posicion = getAdapterPosition();
            if (listener != null) {
                listener.onFactorSeleccionado(posicion);
            }
        }
    }
    public AdaptadorListaFactor(ArrayList<FactorRiesgo> factores, OnItemSelectedListener listener){
        this.factores =  factores;
        this.listener = listener;
    }
    @Override
    public  AdaptadorListaFactor.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.factor_item, parent, false);
        return new ViewHolder(v);
    }


    @Override
    public void onBindViewHolder(AdaptadorListaFactor.ViewHolder holder, int position) {
        FactorRiesgo factorRiesgo = factores.get(position);
        holder.txtNombre.setText(factores.get(position).getNombre());
        holder.txtValor.setText(factores.get(position).getValor());
        if(factores.get(position).isEstado()){
            //Cargar icono check
            holder.iconEstado.setImageResource(R.drawable.ic_done_tick);
        }else{
            //Cargar icono advertencia
            holder.iconEstado.setImageResource(R.drawable.ic_warning_sign);

        }
    }
    @Override
    public int getItemCount() {
        return factores.size();
    }
}

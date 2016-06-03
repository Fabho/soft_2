package com.proyecto.petdroid.adaptadores;

import java.text.DateFormat;
import java.util.Date;
import java.util.Vector;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.proyecto.petdroid.R;
import com.proyecto.petdroid.vo.ConsultaVo;

public class AdaptadorConsultas extends BaseAdapter{

	private Activity actividad;
	private Vector<ConsultaVo> lista;
	
	public AdaptadorConsultas(Activity actividad, Vector<ConsultaVo> lista){
		this.actividad = actividad;
		this.lista = lista;
	}
	
	@Override
	public int getCount() {
		
		return lista.size();
	}

	@Override
	public Object getItem(int position) {

		return lista.elementAt(position);
	}

	@Override
	public long getItemId(int position) {
		
		return lista.elementAt(position).getConsulta_id();
	}

	@Override
	public View getView(int posicion, View convertView, ViewGroup parent) {
		
		LayoutInflater inflater = actividad.getLayoutInflater();
		View view = inflater.inflate(R.layout.elemento_lista_consultas, null,true);
		
		TextView fecha = (TextView) view.findViewById(R.id.txtFechaListaConsultas);
		Date fec = new Date(lista.elementAt(posicion).getFecha());
		DateFormat df = DateFormat.getDateInstance(DateFormat.SHORT);
		
		fecha.setText(df.format(fec));
		
		TextView tipo = (TextView) view.findViewById(R.id.txtTipoListaConsultas);
		switch(lista.elementAt(posicion).getTipo()){
		
		case 1:
			tipo.setText("Revisión");
			break;
		case 2:
			tipo.setText("Cita cliente");
			break;
		case 3:
			tipo.setText("Vacunación");
			break;
		case 4:
			tipo.setText("Cirugía");
			break;
		case 5:
			tipo.setText("Urgencia");
			break;
		}
		
		
		
		
		return view;
	}



}

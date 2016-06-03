package com.proyecto.petdroid.adaptadores;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.proyecto.petdroid.R;

public class AdaptadorListaGenerica extends BaseAdapter{

	private Activity actividad;
	private String[] opciones;
	
	public AdaptadorListaGenerica(Activity actividad, String[] opciones){
		this.actividad = actividad;
		this.opciones = opciones;
	}
	
	@Override
	public int getCount() {
		
		return opciones.length;
	}

	@Override
	public Object getItem(int arg0) {
		
		return opciones[arg0];
	}

	@Override
	public long getItemId(int arg0) {
		
		return arg0;
	}

	@Override
	public View getView(int arg0, View arg1, ViewGroup arg2) {
		
		LayoutInflater inflater = actividad.getLayoutInflater();
		View view = inflater.inflate(R.layout.elemento_lista_generica, null,true);
		
		TextView txtOpcion = (TextView) view.findViewById(R.id.txtOpcionListaGeneric);
		txtOpcion.setText(opciones[arg0]);
		
		return view;
	}

	
}

package com.proyecto.petdroid.adaptadores;

import java.util.Vector;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.proyecto.petdroid.R;

public class AdaptadorDrawer extends BaseAdapter {

	private Vector<String> lista;
	private Activity actividad;
	
	public AdaptadorDrawer (Vector<String> lista, Activity actividad){
		this.lista = lista;
		this.actividad = actividad;
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
		
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		
		LayoutInflater inflater = actividad.getLayoutInflater();
		View view = inflater.inflate(R.layout.elemento_lista_drawer, null,true);
		
		TextView txtOpcion = (TextView) view.findViewById(R.id.txtDrawer);
		txtOpcion.setText(lista.elementAt(position));
		
		ImageView imgDrawer = (ImageView) view.findViewById(R.id.imgDrawer);
		switch(position){
		
		case 0:
			imgDrawer.setImageResource(R.drawable.ic_collections_labels);
			break;
		case 1:
			imgDrawer.setImageResource(R.drawable.ic_collections_new_label);
			break;
		case 2:
			imgDrawer.setImageResource(R.drawable.ic_social_cc_bcc);
			break;
		case 3:
			imgDrawer.setImageResource(R.drawable.ic_social_add_person);
			break;
		case 4:
			imgDrawer.setImageResource(R.drawable.ic_action_about);
		}
		
		return view;
	}

	
}

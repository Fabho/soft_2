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
import com.proyecto.petdroid.vo.MascotaVo;

public class AdaptadorMascotasFichaProp extends BaseAdapter{

	private Activity actividad;
	private Vector<MascotaVo> lista;
	
	public AdaptadorMascotasFichaProp(Activity actividad, Vector<MascotaVo> lista){
		this.actividad = actividad;
		this.lista = lista;
	}
	
	@Override
	public int getCount() {
	
		return lista.size();
	}

	@Override
	public Object getItem(int arg0) {
		// 
		return lista.elementAt(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		
		return lista.elementAt(arg0).getId();
	}

	@Override
	public View getView(int posicion, View arg1, ViewGroup arg2) {
		
		LayoutInflater inflater = actividad.getLayoutInflater();
		View view = inflater.inflate(R.layout.elemento_lista_mascotas_ficha_prop, null,true);
		
		TextView nombreMascota = (TextView) view.findViewById(R.id.txtNombreMascotaFichaProp);
		nombreMascota.setText(lista.elementAt(posicion).getNombre());
		//Faltaria implmentar que se ponga la foto de cada animal según el tipo
		ImageView imgMascota = (ImageView) view.findViewById(R.id.imgMascotaFichaProp);
		switch (lista.elementAt(posicion).getTipo()) {
		case 1:	
			imgMascota.setImageResource(R.drawable.ic_perro);
			break;
		case 2:	
			imgMascota.setImageResource(R.drawable.ic_gato);
			break;
		case 3:	
			imgMascota.setImageResource(R.drawable.ic_conejo);
			break;
		case 4:	
			imgMascota.setImageResource(R.drawable.ic_huron);
			break;
		case 5:	
			imgMascota.setImageResource(R.drawable.ic_ave);
			break;
		case 6:	
			imgMascota.setImageResource(R.drawable.ic_hamster);
			break;
		default:
			imgMascota.setImageResource(R.drawable.ic_tortuga);
			break;
		}
		
		return view;
	}

	
}

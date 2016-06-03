package com.proyecto.petdroid.adaptadores;

import java.util.Vector;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.proyecto.petdroid.EditarMascota;
import com.proyecto.petdroid.MainActivity;
import com.proyecto.petdroid.R;
import com.proyecto.petdroid.dialog.DialogoPersonalizado;
import com.proyecto.petdroid.vo.MascotaVo;

public class AdaptadorMascotas extends BaseAdapter{

	private final Activity actividadMascotas;
	private Vector<MascotaVo> listaMascota;
	
	public final static int ACTIVIDAD_EDITAR_MASCOTA = 8;
	
	public AdaptadorMascotas (Activity actividadMascotas,Vector<MascotaVo> listaMascota){
		super();
		this.actividadMascotas = actividadMascotas;
		this.listaMascota = listaMascota;
	}
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return listaMascota.size();
	}

	@Override
	public Object getItem(int arg0) {
		
		return listaMascota.elementAt(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		
		return listaMascota.elementAt(arg0).getId();
	}

	@Override
	public View getView(int posicion, View arg1, ViewGroup arg2) {
		
		MascotaVo mascota = MainActivity.almacen.obtenerMascota((int)getItemId(posicion));
		LayoutInflater inflater = actividadMascotas.getLayoutInflater();
		View view = inflater.inflate(R.layout.elemento_lista_mascotas, null,true);
		
		TextView nombreMascota = (TextView) view.findViewById(R.id.txtNombreMascota);
		nombreMascota.setText(listaMascota.elementAt(posicion).getNombre());
		TextView nombrePropietario = (TextView) view.findViewById(R.id.txtNombrePropMascota);
		nombrePropietario.setText(listaMascota.elementAt(posicion).getPropietario());
		
		 ImageView imgMascota = (ImageView) view.findViewById(R.id.imgListaMascota);
		switch (mascota.getTipo()) {
		
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
		
		ImageView imgEditar = (ImageView) view.findViewById(R.id.imgEditarListaMascota);
		imgEditar.setOnClickListener(new listenerEditar(posicion));
		
		ImageView imgBorrar = (ImageView) view.findViewById(R.id.imgBorrarListaMascota);
		imgBorrar.setOnClickListener(new listenerBorrar(posicion));
		
		return view;
	}

	
	@Override
	public void notifyDataSetChanged() {
		super.notifyDataSetChanged();
	}


	class listenerEditar implements OnClickListener{

		private int posicion;
		
		public listenerEditar(int posicion){
			super();
			this.posicion = posicion;
		}
		
		@Override
		public void onClick(View v) {

			Intent i = new Intent(v.getContext(), EditarMascota.class);
			i.putExtra("id", getItemId(posicion));
			actividadMascotas.startActivityForResult(i, ACTIVIDAD_EDITAR_MASCOTA);
		}
		
	}
	
	class listenerBorrar implements OnClickListener{
		
		private int posicion;
		
		public listenerBorrar(int posicion){
			super();
			this.posicion = posicion;
		}

		@Override
		public void onClick(View v) {
			
			LayoutInflater inflater = actividadMascotas.getLayoutInflater();
			View view = inflater.inflate(R.layout.dialog_borrar_mascota, null,true);
			TextView txtMensaje = (TextView) view.findViewById(R.id.txtMensajeDialogBorrarMascota);
			txtMensaje.setText("¿Está seguro de que desea eliminar? Se eliminarán los datos de las consultas asociadas a esta mascota.");
			Button btnAceptar = (Button) view.findViewById(R.id.btnAceptarDialogBorrarMascota);
			Button btnCancelar = (Button) view.findViewById(R.id.btnCancelarDialogBorrarMascota);
					
			DialogoPersonalizado builder = new DialogoPersonalizado(actividadMascotas);
			builder.setTitle("Eliminar mascota");
			builder.setTitleColor(actividadMascotas.getResources().getColor(R.color.tono_verde));
			builder.setIcon(R.drawable.ic_action_discard);
			builder.setDividerColor(actividadMascotas.getResources().getColor(R.color.tono_verde));
			
			builder.setCustomView(view);
			
			AlertDialog dialog = builder.create();
			dialog.show();
			
			btnAceptar.setOnClickListener(new listenerBotonAceptar(dialog, posicion));
			btnCancelar.setOnClickListener(new listenerBotonCancelar(dialog));	
		}

	}
	
	class listenerBotonAceptar implements OnClickListener{

		private AlertDialog dialogo;
		private int posicion;
		
		public listenerBotonAceptar(AlertDialog dialogo,int posicion){
			this.dialogo = dialogo;
			this.posicion = posicion;
		}
		
		@Override
		public void onClick(View v) {
			
			MainActivity.almacen.eliminaMascota(listaMascota.elementAt(posicion));
			listaMascota = MainActivity.almacen.listaMascotas();
			notifyDataSetChanged();
			dialogo.cancel();
		}
		
	}
	
	class listenerBotonCancelar implements OnClickListener{
		
		private AlertDialog dialogo;
		
		public listenerBotonCancelar(AlertDialog dialogo){
			this.dialogo = dialogo;
		}

		@Override
		public void onClick(View v) {
			dialogo.cancel();
			
		}
	}
}

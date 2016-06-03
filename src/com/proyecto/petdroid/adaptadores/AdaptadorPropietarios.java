package com.proyecto.petdroid.adaptadores;

import java.util.Vector;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.proyecto.petdroid.EditarPropietario;
import com.proyecto.petdroid.MainActivity;
import com.proyecto.petdroid.R;
import com.proyecto.petdroid.dialog.DialogoPersonalizado;
import com.proyecto.petdroid.vo.PropietarioVo;

public class AdaptadorPropietarios extends BaseAdapter{

	private final Activity actividadPropietario;
	private Vector<PropietarioVo> lista;
	
	private static final int EDITAR_PROPIETARIO = 11;
	
	public AdaptadorPropietarios(Activity actividadPropietario, Vector<PropietarioVo> lista){
		this.actividadPropietario = actividadPropietario;
		this.lista = lista;
	}
	
	@Override
	public int getCount() {
		
		return lista.size();
	}

	@Override
	public Object getItem(int arg0) {
		
		return lista.elementAt(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		
		return lista.elementAt(arg0).getProp_id();
	}

	@Override
	public View getView(int arg0, View arg1, ViewGroup arg2) {
		
		LayoutInflater inflater = actividadPropietario.getLayoutInflater();
		View view = inflater.inflate(R.layout.elemento_lista_propietarios, null,true);
		
		TextView txtNombre = (TextView) view.findViewById(R.id.txtNombrePropLista);
		txtNombre.setText(lista.elementAt(arg0).getNombre()+" "+
		lista.elementAt(arg0).getApellido1()+" "+lista.elementAt(arg0).getApellido2());
		
		TextView txtCantidad = (TextView) view.findViewById(R.id.txtCantidadMascotasLista);
		txtCantidad.setText("Mascotas: "+MainActivity.almacen.cantidadMascotasPorPropietario((int)getItemId(arg0)));
		
		ImageView imgOpciones = (ImageView) view.findViewById(R.id.imgOpcionesProp);
		imgOpciones.setOnClickListener(new listenerOpciones(arg0));
		
		return view;
		
	}


	class listenerOpciones implements OnClickListener{

		private int posicion;
		
		public listenerOpciones (int posicion){
			
			this.posicion = posicion;
		}
		@Override
		public void onClick(View v) {
		
			DialogoPersonalizado builder = new DialogoPersonalizado(actividadPropietario);
			builder.setTitle("Acciones");
			builder.setTitleColor(actividadPropietario.getResources().getColor(R.color.tono_verde));
			builder.setDividerColor(actividadPropietario.getResources().getColor(R.color.tono_verde));
			builder.setIcon(R.drawable.ic_action_core_overflow);
			
			ListView lista = new ListView(actividadPropietario);
			String[] cadena = {"Editar propietario","Eliminar propietario"};
			lista.setAdapter(new AdaptadorListaGenerica(actividadPropietario, cadena));
			
			builder.setCustomView(lista);
			AlertDialog dialogo = builder.create();
			lista.setOnItemClickListener(new listernerElementoLista(dialogo,posicion));
			dialogo.show();
		}
		
		
	}
		
	class listernerElementoLista implements OnItemClickListener{
		
		private AlertDialog dialogo;
		private int posicion;
		
		public listernerElementoLista(AlertDialog dialogo,int posicion){
			this.dialogo = dialogo;
			this.posicion = posicion;
		}

		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
				long arg3) {
			switch (arg2) {
			case 0:
				
				Intent i = new Intent(actividadPropietario, EditarPropietario.class);
				i.putExtra("id", getItemId(posicion));
				actividadPropietario.startActivityForResult(i, EDITAR_PROPIETARIO);
				dialogo.cancel();
				break;

			case 1:
				dialogo.cancel();
				
				DialogoPersonalizado builder = new DialogoPersonalizado(actividadPropietario);
				builder.setTitle("Eliminar propietario");
				builder.setTitleColor(actividadPropietario.getResources().getColor(R.color.tono_verde));
				builder.setDividerColor(actividadPropietario.getResources().getColor(R.color.tono_verde));
				builder.setIcon(R.drawable.ic_action_discard);
				
				LayoutInflater inflater = actividadPropietario.getLayoutInflater();
				View view = inflater.inflate(R.layout.dialog_borrar_mascota, null,true);
				
				TextView txtMensaje = (TextView) view.findViewById(R.id.txtMensajeDialogBorrarMascota);
				txtMensaje.setText("¿Está seguro de que desea eliminar? Se eliminarán todas las mascotas y consultas asociadas al propietario.");
				
				Button btnAceptar = (Button) view.findViewById(R.id.btnAceptarDialogBorrarMascota);
				Button btnCancelar = (Button) view.findViewById(R.id.btnCancelarDialogBorrarMascota);
				
				builder.setCustomView(view);
				
				AlertDialog dialog = builder.create();
				dialog.show();
				
				btnAceptar.setOnClickListener(new listenerBotonAceptar(dialog, posicion));
				btnCancelar.setOnClickListener(new listenerBotonCancelar(dialog));	
				
				break;
			}
			
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
			
			MainActivity.almacen.eliminaPropietario(lista.elementAt(posicion));
			lista = MainActivity.almacen.listaPropietarios();
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

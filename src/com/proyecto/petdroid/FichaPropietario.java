package com.proyecto.petdroid;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.proyecto.petdroid.adaptadores.AdaptadorListaGenerica;
import com.proyecto.petdroid.adaptadores.AdaptadorMascotasFichaProp;
import com.proyecto.petdroid.dialog.DialogoPersonalizado;
import com.proyecto.petdroid.vo.PropietarioVo;

public class FichaPropietario extends ListActivity {

	//Declaramos el DAO de propietario y las vistas
	private PropietarioVo propietario;
	private TextView txtNombre,txtDni,txtTelefono,txtDireccion,txtEmail;
	private ImageView imgOpciones;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_ficha_propietario);
		
		//Recojo los datos del propietario
		Bundle bundle = getIntent().getExtras();
		
		propietario = MainActivity.almacen.obtenerPropietario((int)bundle.getLong("id"));
		
		txtNombre = (TextView) findViewById(R.id.txtNombreFichaProp);
		txtNombre.setText(propietario.getNombre()+" "+propietario.getApellido1()+
				" "+propietario.getApellido2());
		
		txtDni = (TextView) findViewById(R.id.txtDniFichaProp);
		txtDni.setText(propietario.getDni());
		
		txtTelefono = (TextView) findViewById(R.id.txtTelefonoFichaProp);
		txtTelefono.setText(propietario.getTelefono());
		
		txtEmail = (TextView) findViewById(R.id.txtEmailFichaProp);
		txtEmail.setText(propietario.getEmail());
		
		txtDireccion = (TextView) findViewById(R.id.txtDireccionFichaProp);
		txtDireccion.setText(propietario.getDireccion());
		
		imgOpciones = (ImageView) findViewById(R.id.imgOpcionesFichaProp);
		imgOpciones.setOnClickListener(new listenerOpciones(this));
		
		//Recojo los datos de las mascotas
		
		setListAdapter(new AdaptadorMascotasFichaProp(this, 
				MainActivity.almacen.obtenerMascotasPorPropietario((int)bundle.getLong("id"))));
	}

	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		
		super.onListItemClick(l, v, position, id);
		
		Intent i = new Intent(this, FichaMascota.class);
		
		i.putExtra("id", id);
		startActivity(i);
	}
	
	class listenerOpciones implements OnClickListener{

		Activity actividad;
		public listenerOpciones(Activity actividad){
			this.actividad = actividad;
		}
		@Override
		public void onClick(View v) {
			
			DialogoPersonalizado builder = new DialogoPersonalizado(actividad);
			String[] cadena = {"Llamar","Mandar email"}; 
			
			builder.setTitle(txtNombre.getText());
			builder.setTitleColor(actividad.getResources().getColor(R.color.tono_verde));
			builder.setDividerColor(actividad.getResources().getColor(R.color.tono_verde));
			builder.setIcon(R.drawable.ic_action_core_overflow);
			
			ListView listView = new ListView(actividad);
			listView.setAdapter(new AdaptadorListaGenerica(actividad, cadena));
			
			builder.setCustomView(listView);
			
			AlertDialog dialogo = builder.create();
			listView.setOnItemClickListener(new listernerElementoLista());
			dialogo.show();
			
		}
	}
	
	class listernerElementoLista implements OnItemClickListener{

		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
				long arg3) {
			
			switch (arg2) {
			case 0:
				startActivity(new Intent(Intent.ACTION_CALL,Uri.parse("tel:"+propietario.getTelefono())));
				break;

			case 1:
				
				 Intent intent = new Intent(Intent.ACTION_SEND);
				 intent.setData(Uri.parse("mailto:"));
				 intent.putExtra(Intent.EXTRA_EMAIL, new String[]{propietario.getEmail()});
				 intent.putExtra(Intent.EXTRA_SUBJECT, "Correo de la clinica veterinaria");
				 intent.setType("message/rfc822");

				 startActivity(Intent.createChooser(intent, "Mandar Email"));
				break;
			}
		}
	}
}

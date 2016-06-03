package com.proyecto.petdroid;

import java.text.DateFormat;
import java.util.Date;

import android.app.Activity;
import android.app.AlertDialog;
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
import com.proyecto.petdroid.dialog.DialogoPersonalizado;
import com.proyecto.petdroid.vo.ConsultaVo;
import com.proyecto.petdroid.vo.PropietarioVo;

public class FichaConsulta extends Activity {

	private ConsultaVo consulta;
	
	private TextView txtTipoConsulta, txtPatologia,
		txtFecha,txtTratamiento,txtObservaciones,txtNombreMascota;
	
	private ImageView imgOpciones;
	
	private int EDITAR_CONSULTA = 7;
	
	private Date fec;
	private DateFormat df;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_ficha_consulta);
		
				
		txtNombreMascota = (TextView) findViewById(R.id.txtNombreMascotaFichaConsulta);
		txtFecha = (TextView) findViewById(R.id.txtFechaFichaConsulta);
		txtTipoConsulta = (TextView) findViewById(R.id.txtTipoFichaConsulta);
		txtPatologia = (TextView) findViewById(R.id.txtPatologiaFichaConsulta);
		txtTratamiento = (TextView) findViewById(R.id.txtTratamientoFichaConsulta);
		txtObservaciones = (TextView) findViewById(R.id.txtObservacionesFichaConsulta);
		
		pintarDatos();
		imgOpciones = (ImageView) findViewById(R.id.imgOpcionesFichaConsulta);
		imgOpciones.setOnClickListener(new listenerDialogo(this));
	}
	
	class listenerDialogo implements OnClickListener{

		private Activity actividad;
		
		public listenerDialogo(Activity actividad){
			this.actividad = actividad;
		}
		
		@Override
		public void onClick(View v) {
			
			String[] cadena = {"Editar consulta", "Remitir por email"};
			
			DialogoPersonalizado builder = new DialogoPersonalizado(actividad);
			builder.setTitle("Acciones");
			builder.setTitleColor(actividad.getResources().getColor(R.color.tono_verde));
			builder.setDividerColor(actividad.getResources().getColor(R.color.tono_verde));
			builder.setIcon(R.drawable.ic_action_core_overflow);
			
			ListView lista = new ListView(actividad);
			lista.setAdapter(new AdaptadorListaGenerica(actividad, cadena));
			
			builder.setCustomView(lista);
			
			AlertDialog dialog = builder.create();
			lista.setOnItemClickListener(new listenerElementoLista(dialog,actividad));
			dialog.show();
		}
	}
	
	class listenerElementoLista implements OnItemClickListener{
		
		private AlertDialog dialogo;
		private Activity actividad;
		
		public listenerElementoLista(AlertDialog dialogo,Activity actividad){
			this.dialogo = dialogo;
			this.actividad = actividad;
		}

		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
				long arg3) {
			int id_prop = MainActivity.almacen.obtenerIdPropietario(consulta.getMascota());
			PropietarioVo propietario = MainActivity.almacen.obtenerPropietario(id_prop);
			Intent intent;
			switch (arg2) {
			case 0:	
				intent = new Intent(actividad, EditarConsulta.class);
				intent.putExtra("id", consulta.getConsulta_id());
				startActivityForResult(intent, EDITAR_CONSULTA);
				break;

			case 1:
				 intent = new Intent(Intent.ACTION_SEND);
				 intent.setData(Uri.parse("mailto:"));
				 intent.putExtra(Intent.EXTRA_EMAIL, new String[]{propietario.getEmail()});
				 intent.putExtra(Intent.EXTRA_SUBJECT, "Consulta del día "+df.format(fec));
				 intent.putExtra(Intent.EXTRA_TEXT, "Mascota: "+txtNombreMascota.getText()+
						 "\n\nTipo de Consulta: "+txtTipoConsulta.getText()+
						 "\n\nPatología diagnosticada: "
						 +txtPatologia.getText()+"\n\nTratamiento preescrito: "
						 +txtTratamiento.getText()+"\n\nObservaciones del tratamiento: "
						 +txtObservaciones.getText()+"\n\nUn saludo");
				 intent.setType("message/rfc822");

				 startActivity(Intent.createChooser(intent, "Mandar Email"));
				break;
			}
			dialogo.cancel();
			
		}
		
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		
		super.onActivityResult(requestCode, resultCode, data);
		
		if(requestCode == EDITAR_CONSULTA && resultCode == RESULT_OK){
			
			pintarDatos();
		}
	}
	
	private void pintarDatos(){
		
		Bundle bundle = getIntent().getExtras();
		
		consulta = MainActivity.almacen.obtenerConsulta((int)bundle.getLong("id"));

		txtNombreMascota.setText(bundle.getString("nombre"));
		fec = new Date(consulta.getFecha());
		df = DateFormat.getDateInstance(DateFormat.SHORT);
		
		txtFecha.setText(df.format(fec));
		
		switch(consulta.getTipo()){
		
		case 1:
			txtTipoConsulta.setText("Revisión");
			break;
			
		case 2:
			txtTipoConsulta.setText("Fijada por el cliente");
			break;
			
		case 3:
			txtTipoConsulta.setText("Vacunación");
			break;
			
		case 4:
			txtTipoConsulta.setText("Cirugía");
			break;
			
		case 5:
			txtTipoConsulta.setText("Urgencia");
			break;
		}
		
		txtPatologia.setText(consulta.getPatologia());
		txtTratamiento.setText(consulta.getTratamiento());
		txtObservaciones.setText(consulta.getObservaciones());
	}
	
}


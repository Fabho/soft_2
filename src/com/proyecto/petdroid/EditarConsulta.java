package com.proyecto.petdroid;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.proyecto.petdroid.adaptadores.AdaptadorListaGenerica;
import com.proyecto.petdroid.dialog.DialogoPersonalizado;
import com.proyecto.petdroid.vo.ConsultaVo;

public class EditarConsulta extends Activity {

	private EditText editFecha, editTipo, editPatologia, editTratamiento, editObservaciones;
	private Button btnAceptar;
	
	private ConsultaVo consulta = new ConsultaVo();
	
	private int year;
	private int month;
	private int day;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_editar_consulta);
		
		editFecha = (EditText) findViewById(R.id.editTxtFechaEditarConsulta);
		editTipo = (EditText) findViewById(R.id.editTxtTipoEditarConsulta);
		editPatologia = (EditText) findViewById(R.id.editTxtPatologiaEditarConsulta);
		editTratamiento = (EditText) findViewById(R.id.editTxtTratamientoEditarConsulta);
		editObservaciones = (EditText) findViewById(R.id.editTxtObservacionesEditarConsulta);
		btnAceptar = (Button) findViewById(R.id.btnAceptarEditarConsulta);
		
		pintarDatos();
		
		editFecha.setInputType(0);
		editFecha.setOnFocusChangeListener(new OnFocusChangeListener() {
			
			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				if(hasFocus){
					showDialog(999);
				}
				
			}
		});
		
		editTipo.setInputType(0);
		editTipo.setOnFocusChangeListener(new OnFocusChangeListener() {
			
			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				if(hasFocus){
					eligeTipo(v);
				}
				
			}
		});
		
		btnAceptar.setOnClickListener(new listenerAceptar());
	}
	
	//Onclick para elegir la fecha
	public void eligeFecha(View view){
	
		showDialog(999);
	        
		}
	
	//Onclick para elegir el tipo
		public void eligeTipo(View view){
			String[] cadena = {"Revisión","Solicitada por cliente","Vacunación","Urgencia","Cirugía"};
			DialogoPersonalizado builder = new DialogoPersonalizado(this);
			builder.setTitle("Elige el tipo de consulta:");
			builder.setTitleColor(getResources().getColor(R.color.tono_verde));
			builder.setDividerColor(getResources().getColor(R.color.tono_verde));
			
			ListView lista = new ListView(this);
			lista.setAdapter(new AdaptadorListaGenerica(this, cadena));
			builder.setCustomView(lista);
			AlertDialog dialogo = builder.create();
			lista.setOnItemClickListener(new listenerElementoTipo(dialogo));
			dialogo.show();
		}
	
	//Método y atributo para el DatePickerDialog
		@Override
		protected Dialog onCreateDialog(int id) {
			final Calendar cal = Calendar.getInstance();
	        year = cal.get(Calendar.YEAR);
	        month = cal.get(Calendar.MONTH);
	        day = cal.get(Calendar.DAY_OF_MONTH);
	        
			switch (id) {
			case 999:
			   // set date picker as current date
			   return new DatePickerDialog(this, datePickerListener, 
	                         year, month,day);
			}
			return null;
		}
		
	private DatePickerDialog.OnDateSetListener datePickerListener 
    = new DatePickerDialog.OnDateSetListener() {

			// when dialog box is closed, below method will be called.
		public void onDateSet(DatePicker view, int selectedYear,
			int selectedMonth, int selectedDay) {
				year = selectedYear;
				month = selectedMonth;
				day = selectedDay;

				// set selected date into textview
				editFecha.setText(new StringBuilder().append(day)
						.append("-").append(month + 1).append("-").append(year)
						.append(" "));
				Calendar cal = Calendar.getInstance();
				cal.set(selectedYear, selectedMonth, selectedDay);
				consulta.setFecha(cal.getTimeInMillis());
				editTipo.requestFocus();
			}
	};

	class listenerAceptar implements OnClickListener{

		@Override
		public void onClick(View v) {
			
			//Comprobar si los campos están rellenos
			boolean flag = true;
			
			if(editFecha.getText().toString().equals("")){
				flag=false;
			}
			if(editTipo.getText().toString().equals("")){
				flag=false;
			}
			if(editTratamiento.getText().toString().equals("")){
				flag=false;
			}
			if(editPatologia.getText().toString().equals("")){
				flag=false;
			}
			if(editObservaciones.getText().toString().equals("")){
				flag=false;
			}
			
			if(flag){
				consulta.setPatologia(editPatologia.getText().toString());
				consulta.setTratamiento(editTratamiento.getText().toString());
				consulta.setObservaciones(editObservaciones.getText().toString());
				MainActivity.almacen.actualizaConsulta(consulta);
				Toast.makeText(getApplicationContext(), "Datos de la consulta actualizados", Toast.LENGTH_SHORT).show();
				setResult(RESULT_OK);
				finish();
			}else{
				Toast.makeText(getApplicationContext(), "Todos los campos son obligatorios", Toast.LENGTH_LONG).show();
			}
			
		}
		
	}
	
	private void pintarDatos(){
		
		Bundle bundle = getIntent().getExtras();
		consulta = MainActivity.almacen.obtenerConsulta(bundle.getInt("id"));
		Date date = new Date(consulta.getFecha());
		DateFormat df = DateFormat.getDateInstance(DateFormat.SHORT);
		editFecha.setText(df.format(date));
		
		switch(consulta.getTipo()){
		
		case 1:
			editTipo.setText("Revisión");
			break;
		case 2:
			editTipo.setText("Solicitada por cliente");
			break;
		case 3:
			editTipo.setText("Vacunación");
			break;
		case 4:
			editTipo.setText("Cirugía");
			break;
		case 5:
			editTipo.setText("Urgencia");
			break;
		}
		editPatologia.setText(consulta.getPatologia());
		editTratamiento.setText(consulta.getTratamiento());
		editObservaciones.setText(consulta.getObservaciones());
	}
	
	class listenerElementoTipo implements OnItemClickListener{
		
		private AlertDialog dialogo;
		
		public listenerElementoTipo(AlertDialog dialogo){
			this.dialogo = dialogo;
		}

		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
				long arg3) {
			
			switch(arg2){
			
			case 0:
				editTipo.setText("Revisión");
				break;
			case 1:
				editTipo.setText("Solicitada por cliente");
				break;
			case 2:
				editTipo.setText("Vacunación");
				break;
			case 3:
				editTipo.setText("Urgencia");
				break;
			case 4:
				editTipo.setText("Cirugía");
				break;
		}
		consulta.setTipo(arg2+1);
		dialogo.cancel();
		editPatologia.setInputType(1);
		editPatologia.requestFocus();
		}
	}
}
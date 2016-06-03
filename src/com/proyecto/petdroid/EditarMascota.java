package com.proyecto.petdroid;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnFocusChangeListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.proyecto.petdroid.adaptadores.AdaptadorListaGenerica;
import com.proyecto.petdroid.dialog.DialogoPersonalizado;
import com.proyecto.petdroid.vo.MascotaVo;
import com.proyecto.petdroid.vo.PropietarioVo;

public class EditarMascota extends Activity {

	private MascotaVo mascota = new MascotaVo();
	private int id_prop;
	
	//Declaros las vistas
	private EditText editNombre,editTipo,editRaza,editFecha,editPropietario,editSexo;
	private int year;
	private int month;
	private int day;
	
	private static final int ID_INTENT_PROPIETARIO_EXISTENTE = 1;
	private static final int ID_INTENT_NUEVO_PROPIETARIO = 2;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_editar_mascota);
		
		//Código de los Edit
		editNombre = (EditText) findViewById(R.id.editTxtNombreEditarMascota);
		editTipo = (EditText) findViewById(R.id.editTxtTipoEditarMascota);
		editRaza = (EditText) findViewById(R.id.editTxtRazaEditarMascota);
		editSexo = (EditText) findViewById(R.id.editTxtSexoEditarMascota);
		editFecha = (EditText) findViewById(R.id.editTxtFechaEditarMascota);
		editPropietario = (EditText) findViewById(R.id.editTxtPropietarioEditarMascota);

		//Recupero los datos y los muestro
		
		Bundle bundle = getIntent().getExtras();
		mascota = MainActivity.almacen.obtenerMascota((int)bundle.getLong("id"));
		id_prop = MainActivity.almacen.obtenerIdPropietario(mascota.getId());
		
		editNombre.setText(mascota.getNombre());
		switch (mascota.getTipo()) {
		case 1:
			editTipo.setText("Perro");
			break;
		case 2:
			editTipo.setText("Gato");
			break;
		case 3:
			editTipo.setText("Conejo");
			break;
		case 4:
			editTipo.setText("Hurón");
			break;
		case 5:
			editTipo.setText("Ave");
			break;
		case 6:
			editTipo.setText("Hamster");
			break;
		default:
			editTipo.setText("Otro");
			break;
		}
		
		editRaza.setText(mascota.getRaza());
		
		switch (mascota.getSexo()) {
		case 1:
			editSexo.setText("Masculino");
			break;
		case 2:
			editSexo.setText("Femenino");
			break;
		}
		Date date = new Date(mascota.getFecha());
		DateFormat df = DateFormat.getDateInstance(DateFormat.SHORT);
		editFecha.setText(df.format(date));
		
		editPropietario.setText(mascota.getPropietario());
		
		editTipo.setInputType(0);
		editTipo.setOnFocusChangeListener(new OnFocusChangeListener() {
			
			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				if(hasFocus){
					eligeTipo(v);
				}
				
			}
		});
		
		editRaza.setOnFocusChangeListener(new OnFocusChangeListener() {
			
			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				InputMethodManager imm = (InputMethodManager)getSystemService(
					      Context.INPUT_METHOD_SERVICE);
				if(!hasFocus){
					imm.hideSoftInputFromWindow(editRaza.getWindowToken(), 0);
				}
			}
		});
		
		editSexo.setInputType(0);
		editSexo.setOnFocusChangeListener(new OnFocusChangeListener() {
			
			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				if(hasFocus)
					eligeSexo(v);
			}
		});
		
		editFecha.setInputType(0);
		editFecha.setOnFocusChangeListener(new OnFocusChangeListener() {
			
			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				if(hasFocus){
					showDialog(999);
					InputMethodManager imm = (InputMethodManager)getSystemService(
						      Context.INPUT_METHOD_SERVICE);
					imm.hideSoftInputFromWindow(editRaza.getWindowToken(), 0);
				}
				
			}
		});
		
		editPropietario.setInputType(0);
		editPropietario.setOnFocusChangeListener(new OnFocusChangeListener() {
			
			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				if(hasFocus){
					eligePropietario(v);
				}
			}
		});
	}	

	//Onclick para elegir el tipo
	public void eligeTipo(View view){
		String[] cadena = {"Perro","Gato","Conejo","Hurón","Ave","Hamster","Otro"};
		
		DialogoPersonalizado builder = new DialogoPersonalizado(this);
		builder.setTitle("Elige el tipo de animal:");
		builder.setTitleColor(getResources().getColor(R.color.tono_verde));
		builder.setDividerColor(getResources().getColor(R.color.tono_verde));
		
		ListView lista = new ListView(this);
		lista.setAdapter(new AdaptadorListaGenerica(this, cadena));
		
		builder.setCustomView(lista);
		
		AlertDialog dialogo = builder.create();
		lista.setOnItemClickListener(new listenerElementoTipo(dialogo));
		dialogo.show();
		
	}

	//Onclick para elegir la fecha
	public void eligeFecha(View view){
        
        showDialog(999);
        InputMethodManager imm = (InputMethodManager)getSystemService(
			      Context.INPUT_METHOD_SERVICE);
		imm.hideSoftInputFromWindow(editRaza.getWindowToken(), 0);
        
	}
	
	//OnClick para elegir el propietario
	public void eligePropietario(View view){
	
		String[] cadena = {"Nuevo","Existente"};
		DialogoPersonalizado builder = new DialogoPersonalizado(this);
		builder.setTitle("Elige un propietario:");
		builder.setTitleColor(getResources().getColor(R.color.tono_verde));
		builder.setDividerColor(getResources().getColor(R.color.tono_verde));
		
		ListView lista = new ListView(this);
		lista.setAdapter(new AdaptadorListaGenerica(this, cadena));
		
		builder.setCustomView(lista);
		
		AlertDialog dialog = builder.create();
		lista.setOnItemClickListener(new listenerElementoPropietario(dialog));
		dialog.show();
	}
	
	//Onclick para elegir el sexo
	
	public void eligeSexo(View view){
		
String[] cadena = {"Masculino","Femenino"};
		
		DialogoPersonalizado builder = new DialogoPersonalizado(this);
		builder.setTitleColor(getResources().getColor(R.color.tono_verde));
		builder.setDividerColor(getResources().getColor(R.color.tono_verde));
		builder.setTitle("Elige el sexo:");
		
		ListView lista = new ListView(this);
		lista.setAdapter(new AdaptadorListaGenerica(this, cadena));
		
		builder.setCustomView(lista);
		
		AlertDialog dialog = builder.create();
		lista.setOnItemClickListener(new listenerElementoSexo(dialog));
		dialog.show();
	}
	//Metodo botón aceptar
	public void pulsaAceptar(View view){
		
		//Comprobamos que todos los campos estén rellenos
		boolean flag= true;
		if(editNombre.getText().toString().equals("")){
			flag=false;
		}
		if(editRaza.getText().toString().equals("")){
			flag=false;
		}
		if(editFecha.getText().toString().equals("")){
			flag=false;
		}
		if(editSexo.getText().toString().equals("")){
			flag=false;
		}
		if(editTipo.getText().toString().equals("")){
			flag=false;
		}
		if(editPropietario.getText().toString().equals("")){
			flag = false;
		}
		
		//Guardamos los datos
		if(flag){
			mascota.setNombre(editNombre.getText().toString());
			mascota.setRaza(editRaza.getText().toString());
			
			MainActivity.almacen.actualizaMascota(mascota, id_prop);
			Toast.makeText(this, "Datos de la mascota modificados", Toast.LENGTH_SHORT).show();
			setResult(RESULT_OK);
			finish();
		}
		else{
			Toast.makeText(this, "Todos los campos son obligatorios", Toast.LENGTH_SHORT).show();
		}
		
		
	}
	
	//Métodos para el DatePickerDialog
	@Override
	protected Dialog onCreateDialog(int id) {
		final Calendar cal = Calendar.getInstance();
        year = cal.get(Calendar.YEAR);
        month = cal.get(Calendar.MONTH);
        day = cal.get(Calendar.DAY_OF_MONTH);
        
		switch (id) {
		case 999:
		   // set date picker as current date
			DatePickerDialog dp = new DatePickerDialog(this, datePickerListener, 
                    year, month,day);
			dp.setTitle("");
		   return dp;
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
				mascota.setFecha(cal.getTimeInMillis());
				editPropietario.requestFocus();
			}
	};

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		
		//Recogemos cuando venimos de la seleccion de propietario
		if(requestCode==ID_INTENT_PROPIETARIO_EXISTENTE && resultCode == RESULT_OK){
			
			id_prop = (int)data.getExtras().getLong("id");
			PropietarioVo propietario = MainActivity.almacen.obtenerPropietario(id_prop);
			editPropietario.setText(propietario.getNombre()+" "+propietario.getApellido1()+" "+propietario.getApellido2());
			InputMethodManager imm = (InputMethodManager)getSystemService(
				      Context.INPUT_METHOD_SERVICE);
			imm.hideSoftInputFromWindow(editPropietario.getWindowToken(), 0);
		}
		
		//Recogemos cuando venimos de crear un nuevo propietario
		if(requestCode==ID_INTENT_NUEVO_PROPIETARIO && resultCode == RESULT_OK){
			 
			id_prop = data.getExtras().getInt("id");
			PropietarioVo propietario = MainActivity.almacen.obtenerPropietario(id_prop);
			editPropietario.setText(propietario.getNombre()+" "+propietario.getApellido1()+" "+propietario.getApellido2());
			InputMethodManager imm = (InputMethodManager)getSystemService(
				      Context.INPUT_METHOD_SERVICE);
			imm.hideSoftInputFromWindow(editPropietario.getWindowToken(), 0);
		}
		
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
				editTipo.setText("Perro");
				break;
			case 1:
				editTipo.setText("Gato");
				break;
			case 2:
				editTipo.setText("Conejo");
				break;
			case 3:
				editTipo.setText("Hurón");
				break;
			case 4:
				editTipo.setText("Ave");
				break;
			case 5:
				editTipo.setText("Hamster");
				break;
			case 6:
				editTipo.setText("Otro");
				break;
		}
		mascota.setTipo(arg2+1);
		dialogo.cancel();
		editRaza.requestFocus();
		}
		
	}
	
class listenerElementoPropietario implements OnItemClickListener{
		
		private AlertDialog dialogo;
		
		public listenerElementoPropietario(AlertDialog dialogo){
			this.dialogo = dialogo;
		}

		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
				long arg3) {
			
			Intent i;
			switch (arg2) {
			case 0:
				i = new Intent(getApplicationContext(),NuevoPropietario.class);
				i.putExtra("altaNueva", false);
				startActivityForResult(i, ID_INTENT_NUEVO_PROPIETARIO);
				break;

			case 1:
				i = new Intent(getApplicationContext(),Propietarios.class);
				i.putExtra("alta", true);
				startActivityForResult(i, ID_INTENT_PROPIETARIO_EXISTENTE);
				break;
			}
			dialogo.cancel();
		}
		
	}
	class listenerElementoSexo implements OnItemClickListener{
	
	private AlertDialog dialogo;
	
	public listenerElementoSexo(AlertDialog dialogo){
		this.dialogo = dialogo;
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
			long arg3) {
		switch (arg2) {
		case 0:
			editSexo.setText("Masculino");
			break;

		case 1:
			editSexo.setText("Femenino");
			break;
		}
		mascota.setSexo(arg2+1);
		dialogo.cancel();
		editFecha.requestFocus();
	}
}
}


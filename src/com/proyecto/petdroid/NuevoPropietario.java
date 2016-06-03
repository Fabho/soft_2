package com.proyecto.petdroid;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.proyecto.petdroid.vo.PropietarioVo;

public class NuevoPropietario extends Activity {

	private EditText editNombre, editApellido1,editApellido2,editDni,
					editTelefono,editDireccion,editEmail;
	
	private PropietarioVo propietario = new PropietarioVo();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_nuevo_propietario);
		
		editNombre = (EditText) findViewById(R.id.editTxtNombreNuevoProp);
		editApellido1 = (EditText) findViewById(R.id.editTxtApellido1NuevoProp);
		editApellido2 = (EditText) findViewById(R.id.editTxtApellido2NuevoProp);
		editDni = (EditText) findViewById(R.id.editTxtDniNuevoProp);
		editTelefono = (EditText) findViewById(R.id.editTxtTelefonoNuevoProp);
		editDireccion = (EditText) findViewById(R.id.editTxtDireccionNuevoProp);
		editEmail = (EditText) findViewById(R.id.editTxtEmailNuevoProp);
	}

	public void pulsaAceptar(View view){
		
		boolean flag = true;
		
		if(editNombre.getText().toString().equals("")){
			flag=false;
		}
		if(editApellido1.getText().toString().equals("")){
			flag=false;
		}
		if(editApellido2.getText().toString().equals("")){
			flag=false;
		}
		if(editDni.getText().toString().equals("")){
			flag=false;
		}
		if(editTelefono.getText().toString().equals("")){
			flag=false;
		}
		if(editDireccion.getText().toString().equals("")){
			flag=false;
		}
		if(editEmail.getText().toString().equals("")){
			flag=false;
		}
		
		if(flag){
			propietario.setNombre(editNombre.getText().toString());
			propietario.setApellido1(editApellido1.getText().toString());
			propietario.setApellido2(editApellido2.getText().toString());
			propietario.setDni(editDni.getText().toString());
			propietario.setTelefono(editTelefono.getText().toString());
			propietario.setDireccion(editDireccion.getText().toString());
			propietario.setEmail(editEmail.getText().toString());
			
			int id = MainActivity.almacen.nuevoPropietario(propietario);
			
			Bundle bundle = getIntent().getExtras();
			flag = bundle.getBoolean("altaNueva", true);
			
			if(flag){
				Toast.makeText(this, "Nuevo propietario dado de alta", Toast.LENGTH_SHORT).show();
				finish();
			}
			else{
				//Pillamos el id de la nueva alta y lo retornamos
				Intent i = new Intent();
				i.putExtra("id", id);
				setResult(RESULT_OK, i);
				finish();
			}
		}else{
			Toast.makeText(this, "Todos los datos son obligatorios", Toast.LENGTH_SHORT).show();
		}
	}
}

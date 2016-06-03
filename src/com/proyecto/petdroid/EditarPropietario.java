package com.proyecto.petdroid;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.proyecto.petdroid.vo.PropietarioVo;

public class EditarPropietario extends Activity {

	private EditText editNombre, editApellido1,editApellido2,editDni,
	editTelefono,editDireccion,editEmail;

	private PropietarioVo propietario = new PropietarioVo();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_editar_propietario);
		
		editNombre = (EditText) findViewById(R.id.editTxtNombreEditarProp);
		editApellido1 = (EditText) findViewById(R.id.editTxtApellido1EditarProp);
		editApellido2 = (EditText) findViewById(R.id.editTxtApellido2EditarProp);
		editDni = (EditText) findViewById(R.id.editTxtDniEditarProp);
		editTelefono = (EditText) findViewById(R.id.editTxtTelefonoEditarProp);
		editDireccion = (EditText) findViewById(R.id.editTxtDireccionEditarProp);
		editEmail = (EditText) findViewById(R.id.editTxtEmailEditarProp);
		
		Bundle bundle = getIntent().getExtras();
		propietario = MainActivity.almacen.obtenerPropietario((int)bundle.getLong("id"));
		
		editNombre.setText(propietario.getNombre());
		editApellido1.setText(propietario.getApellido1());
		editApellido2.setText(propietario.getApellido2());
		editDni.setText(propietario.getDni());
		editTelefono.setText(propietario.getTelefono());
		editDireccion.setText(propietario.getDireccion());
		editEmail.setText(propietario.getEmail());
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
			
			MainActivity.almacen.actualizaPropietario(propietario);
			
			Toast.makeText(this, "Datos del propietario modificados", Toast.LENGTH_SHORT).show();
			setResult(RESULT_OK);
			finish();
		}else{
			Toast.makeText(this, "Todos los datos son obligatorios", Toast.LENGTH_SHORT).show();
		}
	}
}

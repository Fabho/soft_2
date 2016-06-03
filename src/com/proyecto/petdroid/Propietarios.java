package com.proyecto.petdroid;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import com.proyecto.petdroid.adaptadores.AdaptadorPropietarios;

public class Propietarios extends ListActivity {
	
	private static final int EDITAR_PROPIETARIO = 11;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_propietarios);
		
		setListAdapter(new AdaptadorPropietarios(this,MainActivity.almacen.listaPropietarios()));
	}

	@Override
	protected void onListItemClick(ListView listView, View view, int position, long id) {
		// TODO Auto-generated method stub
		super.onListItemClick(listView, view, position, id);
		Bundle bundle = getIntent().getExtras();
		boolean flag = bundle.getBoolean("alta", false);
		if(flag){
			Intent i = new Intent();
			i.putExtra("id", id);
			setResult(RESULT_OK,i);
			finish();
			
		}else{
			Intent i2 = new Intent(this,FichaPropietario.class);
			i2.putExtra("id", id);
			startActivity(i2);
		}
		
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		
		super.onActivityResult(requestCode, resultCode, data);
		
		if(resultCode == RESULT_OK && requestCode == EDITAR_PROPIETARIO){
		
			setListAdapter(new AdaptadorPropietarios(this,MainActivity.almacen.listaPropietarios()));
		}
	}
	
	

}

package com.proyecto.petdroid;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import com.proyecto.petdroid.adaptadores.AdaptadorConsultas;

public class Consultas extends ListActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_consultas);
		
		Bundle bundle = getIntent().getExtras();
		
		setListAdapter(new AdaptadorConsultas(this,
				MainActivity.almacen.listaConsultas(bundle.getInt("id"))));
	}

	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		
		super.onListItemClick(l, v, position, id);
		
		Bundle bundle = getIntent().getExtras();
		
		Intent i = new Intent(this, FichaConsulta.class);
		i.putExtra("id", id);
		i.putExtra("nombre", bundle.getString("nombre"));
		startActivity(i);
	}

	
}

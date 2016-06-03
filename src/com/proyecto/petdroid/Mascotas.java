package com.proyecto.petdroid;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import com.proyecto.petdroid.adaptadores.AdaptadorMascotas;

public class Mascotas extends ListActivity{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_mascotas);
		
		setListAdapter(new AdaptadorMascotas(this, MainActivity.almacen.listaMascotas()));
		
	}

	@Override
	protected void onListItemClick(ListView listView, View view, int position, long id) {
		
		super.onListItemClick(listView, view, position, id);
		
		Intent i = new Intent(this,FichaMascota.class);
		i.putExtra("id", id);

		startActivity(i);
	}

	@Override
	protected void onResume() {
		
		super.onResume();
		setListAdapter(new AdaptadorMascotas(this, MainActivity.almacen.listaMascotas()));
	}	

	
}

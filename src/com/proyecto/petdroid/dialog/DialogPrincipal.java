package com.proyecto.petdroid.dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.proyecto.petdroid.Mascotas;
import com.proyecto.petdroid.NuevaMascota;
import com.proyecto.petdroid.NuevoPropietario;
import com.proyecto.petdroid.Propietarios;
import com.proyecto.petdroid.R;
import com.proyecto.petdroid.adaptadores.AdaptadorListaGenerica;

public class DialogPrincipal extends DialogFragment{

	
	private View mDialogView;
	
	private TextView mTitle;
	private ImageView mIcon;
	private View mDivider;
	
	public DialogPrincipal (){
		super();
	}

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
	
		mDialogView = View.inflate(getActivity(), R.layout.qustom_dialog_layout, null);

        mTitle = (TextView) mDialogView.findViewById(R.id.alertTitle);
        mIcon = (ImageView) mDialogView.findViewById(R.id.icon);
        mDivider = mDialogView.findViewById(R.id.titleDivider);
        
		String titulo = getArguments().getString("titulo");
		String[] cadenaPropietarios = {"Listado", "Crear nuevo"};
		String[] cadenaMascotas = {"Listado", "Crear nueva"};
		
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		builder.setView(mDialogView);
		
		mTitle.setText(titulo);
		mTitle.setTextColor(getResources().getColor(R.color.tono_verde));
		if(getArguments().getInt("opcion")==1){
			mIcon.setImageResource(R.drawable.ic_collections_labels);
		}else{
			mIcon.setImageResource(R.drawable.ic_social_cc_bcc);
		}
		mDivider.setBackgroundColor(getResources().getColor(R.color.tono_verde));
		
		ListView listView = new ListView(getActivity());
		if(getArguments().getInt("opcion")==1){
			listView.setAdapter(new AdaptadorListaGenerica(getActivity(), cadenaMascotas));
		}
		else{
			listView.setAdapter(new AdaptadorListaGenerica(getActivity(), cadenaPropietarios));
		}
		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
			
				Intent i;
				switch(arg2){
				
					case 0:
						
						if(getArguments().getInt("opcion")==1){
							i = new Intent(getActivity(), Mascotas.class);
						}
						else{
							i = new Intent(getActivity(), Propietarios.class);
							i.putExtra("alta", false);
						}
						
						startActivity(i);
						getDialog().cancel();
						break;
					case 1:
						
						if(getArguments().getInt("opcion")==1){
							i = new Intent(getActivity(), NuevaMascota.class);
						}
						else{
							i = new Intent(getActivity(), NuevoPropietario.class);
							i.putExtra("altaNueva", true);
						}
						
						startActivity(i);
						getDialog().cancel();
						break;
				}
			}
			
		});
		
		
    	((FrameLayout)mDialogView.findViewById(R.id.customPanel)).addView(listView);
    	
		return builder.create();
		}
}


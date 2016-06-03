package com.proyecto.petdroid;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.proyecto.petdroid.dialog.DialogPrincipal;

public class FragmentPortadaPropietarios extends Fragment {

	private RelativeLayout layout;
	
	public FragmentPortadaPropietarios() {
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		View rootView = inflater.inflate(R.layout.principal_propietarios_layout,container, false);
		return rootView;
	}
	
	@Override
	public void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
	
		RelativeLayout layout = (RelativeLayout) getActivity().findViewById(R.id.RelativeLayoutPropietario);
		layout.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {

				DialogPrincipal dialogo = new DialogPrincipal();
				Bundle args = new Bundle();
				args.putString("titulo", "Propietarios");
				args.putInt("opcion", 2);
				dialogo.setArguments(args);
				dialogo.show(getFragmentManager(), "dialogoPropietario");
			}
		});
	}
}

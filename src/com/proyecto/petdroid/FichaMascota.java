package com.proyecto.petdroid;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.text.DateFormat;
import java.util.Date;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.proyecto.petdroid.adaptadores.AdaptadorListaGenerica;
import com.proyecto.petdroid.dialog.DialogoPersonalizado;
import com.proyecto.petdroid.vo.MascotaVo;


public class FichaMascota extends Activity {

	//Declaramos las vistas
	private TextView txtNombre,txtTipo,txtRaza,txtFecha,txtSexo,txtPropietario;
	private ImageView imgFoto, imgOpciones;
	
	private MascotaVo mascota;
	
	private boolean flag_foto = false;
	
	private static final int EDITAR_MASCOTA_DESDE_FICHA = 6;
	private static int TOMA_FOTO = 1;
	
	private Uri outputFileUri;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_ficha_mascota);
		
		//Recogemos los datos de la mascota
		Bundle bundle = getIntent().getExtras();
		
		mascota = MainActivity.almacen.obtenerMascota((int)bundle.getLong("id"));
		
		imgFoto = (ImageView) findViewById(R.id.imgFichaMascota);
		imgOpciones = (ImageView) findViewById(R.id.imgOpcionesFichaMascota);
		
		//Asignamos el contenido a los textViews
		txtNombre = (TextView) findViewById(R.id.txtNombreFichaMascota);
		txtTipo = (TextView) findViewById(R.id.txtTipoFichaMascota);
		txtRaza = (TextView) findViewById(R.id.txtRazaFichaMascota);
		txtFecha = (TextView) findViewById(R.id.txtFechaFichaMascota);
		txtSexo = (TextView) findViewById(R.id.txtSexoFichaMascota);
		txtPropietario = (TextView) findViewById(R.id.txtPropietarioFichaMascota);
		
		pintaDatos(mascota);
		
		imgFoto.setOnClickListener(new listenerCamara(this));
				
		imgOpciones.setOnClickListener(new listenerBoton(this));
	}

	
	@Override
	protected void onResume() {
		
		super.onResume();
		
		File sdcard = Environment.getExternalStorageDirectory();
		String str=sdcard.getAbsolutePath()+"/PetDroid/Fotos/"+mascota.getId()+".jpg";
		
		FileInputStream in;
		try {
			in = new FileInputStream(str);
			
			Bitmap bm = decodeSampledBitmapFromFile(str, 100, 100);
			flag_foto = true;
			imgFoto.setImageBitmap(bm);
			
		} catch (FileNotFoundException e) {
			
		}
		
	}


	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
	    super.onActivityResult(requestCode, resultCode, data);
	    
	    if(requestCode== EDITAR_MASCOTA_DESDE_FICHA && resultCode==RESULT_OK){
	    	
	    	mascota = MainActivity.almacen.obtenerMascota(mascota.getId());
	    	pintaDatos(mascota);
	    }
	}
	 
	 @Override
	 protected void onSaveInstanceState(Bundle outState) {
	     super.onSaveInstanceState(outState);
	     if (outputFileUri != null)
	         outState.putString("Uri", outputFileUri.toString());
	 }
	  
	 @Override
	 protected void onRestoreInstanceState(Bundle savedInstanceState) {
	     super.onRestoreInstanceState(savedInstanceState);
	     if (savedInstanceState.containsKey("Uri")) {
	    	 outputFileUri = Uri.parse(savedInstanceState.getString("Uri"));
	     }
	 }
	 
	 class listenerBoton implements OnClickListener{

		private Activity actividadFichaMascota;
		 
		public listenerBoton(Activity actividad){
			actividadFichaMascota = actividad;
		}
		
		@Override
		public void onClick(View v) {
			
			String[] cadena = {"Ver consultas", "Añadir consulta","Editar Mascota"};
			DialogoPersonalizado builder = new DialogoPersonalizado(actividadFichaMascota);
			builder.setTitle("Acciones");
			builder.setTitleColor(actividadFichaMascota.getResources().getColor(R.color.tono_verde));
			builder.setDividerColor(actividadFichaMascota.getResources().getColor(R.color.tono_verde));
			builder.setIcon(R.drawable.ic_action_core_overflow);
			
			ListView lista = new ListView(actividadFichaMascota);
			lista.setAdapter(new AdaptadorListaGenerica(actividadFichaMascota, cadena));
			
			builder.setCustomView(lista);
			AlertDialog dialog = builder.create();
			lista.setOnItemClickListener(new listenerElementoLista(dialog));
			dialog.show();
		}
		 
		 
	 }
	 
	 class listenerElementoLista implements OnItemClickListener{

		private AlertDialog dialogo;
		
		public listenerElementoLista(AlertDialog dialogo){
			this.dialogo = dialogo;
		}
		
		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
				long arg3) {
			
			Intent i;
			switch(arg2){
			
			case 0:
				i = new Intent(getApplicationContext(), Consultas.class);
				i.putExtra("id", mascota.getId());
				i.putExtra("nombre", mascota.getNombre());
				startActivity(i);
				break;
				
			case 1:
				i= new Intent(getApplicationContext(), NuevaConsulta.class);
				i.putExtra("id", mascota.getId());
				startActivity(i);
				break;
			case 2:
				i = new Intent(getApplicationContext(),EditarMascota.class);
				i.putExtra("id", (long)mascota.getId());
				startActivityForResult(i, EDITAR_MASCOTA_DESDE_FICHA);
				break;
			}
			
			dialogo.cancel();
		}
	}
		 
	 public void pintaDatos(MascotaVo mascota){
		 
		 txtNombre.setText(mascota.getNombre());
		 
		 switch(mascota.getTipo()){
			case 1:
				txtTipo.setText(" Perro");
				break;
				
			case 2:
				txtTipo.setText(" Gato");
				break;
				
			case 3:
				txtTipo.setText(" Conejo");
				break;
				
			case 4:
				txtTipo.setText(" Hurón");
				break;
				
			case 5:
				txtTipo.setText(" Ave");
				break;
				
			case 6:
				txtTipo.setText(" Hamster");
				break;
				
			case 7:
				txtTipo.setText(" Otro");
				break;
		}
		
		txtRaza.setText(mascota.getRaza());
		
		Date fecha = new Date(mascota.getFecha());
		DateFormat df = DateFormat.getDateInstance(DateFormat.SHORT);
		
		txtFecha.setText(df.format(fecha));
		
		if(mascota.getSexo() == 1){
			txtSexo.setText("Masculino");
		}
		else{
			txtSexo.setText("Femenino");
		}
		txtPropietario.setText(" "+mascota.getPropietario());
	 }
	 
	 private static int calculateInSampleSize(
             BitmapFactory.Options options, int reqWidth, int reqHeight) {
     // Raw height and width of image
     final int height = options.outHeight;
     final int width = options.outWidth;
     int inSampleSize = 1;

     if (height > reqHeight || width > reqWidth) {

         final int halfHeight = height / 2;
         final int halfWidth = width / 2;

         // Calculate the largest inSampleSize value that is a power of 2 and keeps both
         // height and width larger than the requested height and width.
         while ((halfHeight / inSampleSize) > reqHeight
                 && (halfWidth / inSampleSize) > reqWidth) {
             inSampleSize *= 2;
         }
     }

     return inSampleSize;
 }
	 
	 private static Bitmap decodeSampledBitmapFromFile(String in,
			 int reqWidth, int reqHeight) {
			
		    // First decode with inJustDecodeBounds=true to check dimensions
		    final BitmapFactory.Options options = new BitmapFactory.Options();
		    options.inJustDecodeBounds = true;
		    BitmapFactory.decodeFile(in, options);

		    // Calculate inSampleSize
		    options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);

		    // Decode bitmap with inSampleSize set
		    options.inJustDecodeBounds = false;
		   
		    return BitmapFactory.decodeFile(in, options);
		}
	 
	 private void lanzarCamara(){
		 
		 String state = Environment.getExternalStorageState();
         if (Environment.MEDIA_MOUNTED.equals(state)) 
         {	
         	File sdcard = Environment.getExternalStorageDirectory();
     		String str=sdcard.getAbsolutePath()+"/PetDroid/Fotos";
         	
         	File imgFile = new File(str, String.valueOf(mascota.getId()+".jpg"));
         	outputFileUri = Uri.fromFile(imgFile);
         	Intent intent = new Intent();
         	intent.setAction(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
         	intent.putExtra(android.provider.MediaStore.EXTRA_OUTPUT, outputFileUri);
             startActivityForResult(intent, TOMA_FOTO);
         }
         else{
         	Toast.makeText(getApplicationContext(), "No existe tarjeta SD para guardar la foto", Toast.LENGTH_SHORT).show();
         }
	}
	 
	 class listenerCamara implements OnClickListener{

		private Activity actividad;
		
		public listenerCamara(Activity actividad){
			this.actividad = actividad;
		}
		@Override
		public void onClick(View v) {
			if(!flag_foto){
				
				lanzarCamara();
			}else{
				String[] cadena = {"Ver foto","Cambiar foto"};
				DialogoPersonalizado builder = new DialogoPersonalizado(actividad);
				builder.setTitle("Acciones");
				builder.setTitleColor(actividad.getResources().getColor(R.color.tono_verde));
				builder.setDividerColor(actividad.getResources().getColor(R.color.tono_verde));
				builder.setIcon(R.drawable.ic_action_core_overflow);
				
				ListView lista = new ListView(actividad);
				lista.setAdapter(new AdaptadorListaGenerica(actividad, cadena));
				
				builder.setCustomView(lista);
				AlertDialog dialogo = builder.create();
				lista.setOnItemClickListener(new listenerElementoFoto(dialogo));
				dialogo.show();
			}
			
		}
	}
		
	 class listenerElementoFoto implements OnItemClickListener{
			
			private AlertDialog dialogo;
			
			public listenerElementoFoto(AlertDialog dialogo){
				this.dialogo = dialogo;
			}

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
			
				switch (arg2) {
				case 0:
					  
					File sdcard = Environment.getExternalStorageDirectory();
			     	String str=sdcard.getAbsolutePath()+"/PetDroid/Fotos/"+mascota.getId()+".jpg";
			     	File foto = new File(str);
			     	Intent i = new Intent();
			     	i.setAction(android.content.Intent.ACTION_VIEW);
			     	i.setDataAndType(Uri.fromFile(foto), "image/*");
			     	startActivity(i);
					break;

				case 1:
					lanzarCamara();
					break;
				}
				dialogo.cancel();
			}
			
			
		}
}

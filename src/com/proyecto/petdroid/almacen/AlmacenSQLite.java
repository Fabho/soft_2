package com.proyecto.petdroid.almacen;

import java.io.File;
import java.io.FileInputStream;
import java.util.Vector;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Environment;

import com.proyecto.petdroid.vo.ConsultaVo;
import com.proyecto.petdroid.vo.MascotaVo;
import com.proyecto.petdroid.vo.PropietarioVo;

public class AlmacenSQLite  extends SQLiteOpenHelper implements AlmacenMascotas{
	
	//Constructor
	public AlmacenSQLite(Context ctx){
		super(ctx, "vetdroid", null, 1);
	}

	@Override
	public Vector<MascotaVo> listaMascotas() {
		
		Vector<MascotaVo> almacen = new Vector<MascotaVo>();
		
		SQLiteDatabase db = getReadableDatabase();
		Cursor cursor = db.rawQuery("SELECT * FROM mascotas ORDER BY nombre", null);
		
		while(cursor.moveToNext()){
			Cursor cursor2 = db.rawQuery("SELECT nombre, apellido1,apellido2 " +
					"FROM propietarios WHERE prop_id='"+cursor.getInt(6)+"'",null);
			cursor2.moveToNext();
			MascotaVo aux = new MascotaVo(cursor.getInt(0), cursor.getString(1), 
					cursor.getInt(2),cursor.getLong(3),cursor.getInt(4),cursor.getString(5),
					cursor2.getString(0)+" "+cursor2.getString(1)+" "+cursor2.getString(2));
			almacen.add(aux);
		}
		
		return almacen;
	}
	
	public Vector<PropietarioVo> listaPropietarios(){
		
		Vector<PropietarioVo> almacen = new Vector<PropietarioVo>();
		
		SQLiteDatabase db = getReadableDatabase();
		Cursor cursor = db.rawQuery("SELECT * FROM propietarios ORDER BY apellido1", null);
		
		while(cursor.moveToNext()){
			PropietarioVo aux = new PropietarioVo(cursor.getInt(0), cursor.getString(1), 
					cursor.getString(2), cursor.getString(3), cursor.getString(4), 
					cursor.getString(5), cursor.getString(6),cursor.getString(7));
			almacen.add(aux);
		}
		
		return almacen;
	}

	
	@Override
	public Vector<ConsultaVo> listaConsultas(int mascota_id) {
		
		Vector<ConsultaVo> almacen = new Vector<ConsultaVo>();
		
		SQLiteDatabase db = getReadableDatabase();
		Cursor cursor = db.rawQuery("SELECT * FROM consultas WHERE mascota = "+
				mascota_id+" ORDER BY fecha", null);
		
		while(cursor.moveToNext()){
			ConsultaVo consulta = new ConsultaVo(cursor.getInt(0), cursor.getInt(1), 
					cursor.getString(2), cursor.getLong(3), cursor.getString(4), 
					cursor.getString(5), cursor.getInt(6));
			almacen.add(consulta);
		}
		
		return almacen;
	}

	@Override
	public MascotaVo obtenerMascota(int id) {
		
		MascotaVo mascota;
		SQLiteDatabase db = getReadableDatabase();
		Cursor cursor = db.rawQuery("SELECT * FROM mascotas WHERE mascota_id="+id+"", null);
		if(cursor.moveToNext()){
			
			Cursor cursor2 = db.rawQuery("SELECT nombre, apellido1,apellido2 " +
					"FROM propietarios WHERE prop_id='"+cursor.getInt(6)+"'",null);
			cursor2.moveToNext();
			mascota = new MascotaVo(cursor.getInt(0), cursor.getString(1), 
					cursor.getInt(2),cursor.getLong(3),cursor.getInt(4),cursor.getString(5),
					cursor2.getString(0)+" "+cursor2.getString(1)+" "+cursor2.getString(2));
		}else{
			mascota = null;
		}
		
		return mascota;
	}

	@Override
	public PropietarioVo obtenerPropietario(int id) {
		
		PropietarioVo propietario;
		SQLiteDatabase db = getReadableDatabase();
		Cursor cursor = db.rawQuery("SELECT * FROM propietarios WHERE prop_id="+id+"", null);
		
		if(cursor.moveToNext()){
			propietario = new PropietarioVo(cursor.getInt(0), cursor.getString(1),
					cursor.getString(2),cursor.getString(3), cursor.getString(4),
					cursor.getString(5), cursor.getString(6),cursor.getString(7));
			return propietario;
		}
		else
			return null;
	}

	@Override
	public int obtenerIdPropietario(int id_mascota) {
		
		SQLiteDatabase db = getReadableDatabase();
		Cursor cursor = db.rawQuery("SELECT propietario FROM mascotas WHERE mascota_id = "+id_mascota+"", null);
		cursor.moveToNext();
		return cursor.getInt(0);
	}
	
	@Override
	public ConsultaVo obtenerConsulta(int id) {
		
		ConsultaVo consulta;
		SQLiteDatabase db = getReadableDatabase();
		Cursor cursor = db.rawQuery("SELECT * FROM consultas WHERE consulta_id="+id+"", null);
		
		if(cursor.moveToNext()){
			consulta = new ConsultaVo(cursor.getInt(0), cursor.getInt(1), cursor.getString(2), 
					cursor.getLong(3), cursor.getString(4), cursor.getString(5), cursor.getInt(6));
			return consulta;
		}
		else
			return null;
	}

	@Override
	public int cantidadMascotasPorPropietario(int id) {
		
		SQLiteDatabase db = getReadableDatabase();
		Cursor cursor = db.rawQuery("SELECT * FROM mascotas WHERE propietario="+id+"", null);
		
			return cursor.getCount();
	}

	
	@Override
	public Vector<MascotaVo> obtenerMascotasPorPropietario(int id) {
		
		Vector<MascotaVo> almacen = new Vector<MascotaVo>();
		
		SQLiteDatabase db = getReadableDatabase();
		Cursor cursor = db.rawQuery("SELECT * FROM mascotas WHERE propietario="+id+"", null);
		while(cursor.moveToNext()){
			
			MascotaVo aux = new MascotaVo(cursor.getInt(0), cursor.getString(1), 
					cursor.getInt(2),cursor.getLong(3),cursor.getInt(4),cursor.getString(5),
					null);
			almacen.add(aux);
		}
		
		return almacen;
	}

	
	@Override
	public int nuevoPropietario(PropietarioVo propietario) {
		
		//Damos de alta el propietario
		SQLiteDatabase db = getWritableDatabase();
		db.execSQL("INSERT INTO propietarios VALUES(null,'"+propietario.getNombre()+
				"','"+propietario.getApellido1()+"','"+propietario.getApellido2()+"'," +
				"'"+propietario.getDni()+"','"+propietario.getTelefono()+
				"','"+propietario.getDireccion()+"','"+propietario.getEmail()+"')");
		
		//Recuperamos el id
		Cursor cursor = db.rawQuery("SELECT * FROM propietarios", null);
		cursor.moveToLast();
		
		
		return cursor.getInt(0);
	}

	@Override
	public void nuevaMascota(MascotaVo mascota,int id_prop) {

		//Damos de alta el propietario
		SQLiteDatabase db = getWritableDatabase();
		db.execSQL("INSERT INTO mascotas VALUES(null,'"+mascota.getNombre()+
				"',"+mascota.getTipo()+","+mascota.getFecha()+","+mascota.getSexo()+
				",'"+mascota.getRaza()+"',"+id_prop+")");
	}
	

	@Override
	public void nuevaConsulta(ConsultaVo consulta) {
		SQLiteDatabase db = getWritableDatabase();
		db.execSQL("INSERT INTO consultas VALUES(null,"+consulta.getTipo()+
				",'"+consulta.getPatologia()+"',"+consulta.getFecha()+
				",'"+consulta.getTratamiento()+"','"+consulta.getObservaciones()+
				"',"+consulta.getMascota()+")");
	}
	//Updates
	
	@Override
	public void actualizaMascota(MascotaVo mascota, int id_prop) {
		
		SQLiteDatabase db = getWritableDatabase();
		db.execSQL("UPDATE mascotas SET nombre = '"+mascota.getNombre()+"', tipo = "+
				mascota.getTipo()+", fechaNac = "+mascota.getFecha()+", sexo = "
				+mascota.getSexo()+", raza = '"+mascota.getRaza()+"', propietario = "
				+id_prop+" WHERE mascota_id = "+mascota.getId()+"");
	}

	
	@Override
	public void actualizaPropietario(PropietarioVo propietario) {
		
		SQLiteDatabase db = getWritableDatabase();
		db.execSQL("UPDATE propietarios SET nombre = '"+propietario.getNombre()+
				"', apellido1 = '"+propietario.getApellido1()+"', apellido2='"+
				propietario.getApellido2()+"', dni='"+propietario.getDni()+
				"',telefono='"+propietario.getTelefono()+"',direccion='"+
				propietario.getDireccion()+"',email='"+propietario.getEmail()+
				"' WHERE prop_id="+propietario.getProp_id()+"");
		
	}

	@Override
	public void actualizaConsulta(ConsultaVo consulta) {
		
		SQLiteDatabase db = getWritableDatabase();
		db.execSQL("UPDATE consultas SET tipo="+consulta.getTipo()+", patologia='"+
				consulta.getPatologia()+"',fecha="+consulta.getFecha()+", tratamiento='"+
				consulta.getTratamiento()+"', observaciones='"+
				consulta.getObservaciones()+"',mascota="+consulta.getMascota()+
				" WHERE consulta_id="+consulta.getConsulta_id()+"");
	}

	//Deletes
	@Override
	public void eliminaMascota(MascotaVo mascota) {
		
		SQLiteDatabase db = getWritableDatabase();
		db.execSQL("DELETE FROM mascotas WHERE mascota_id = "+mascota.getId()+"");
		db.execSQL("DELETE FROM consultas WHERE mascota = "+mascota.getId()+"");
		
		File sdcard = Environment.getExternalStorageDirectory();
		String str=sdcard.getAbsolutePath()+"/PetDroid/Fotos/"+mascota.getId()+".jpg";
		
		File file = new File(str);
		file.delete();
	}
	
	@Override
	public void eliminaPropietario(PropietarioVo propietario) {
		
		Vector<MascotaVo> mascotas = new Vector<MascotaVo>();
		SQLiteDatabase db = getWritableDatabase();
		mascotas = obtenerMascotasPorPropietario(propietario.getProp_id());
		for(MascotaVo aux : mascotas){
			db.execSQL("DELETE FROM mascotas WHERE mascota_id = "+aux.getId()+"");
			db.execSQL("DELETE FROM consultas WHERE mascota = "+aux.getId()+"");
			
			File sdcard = Environment.getExternalStorageDirectory();
			String str=sdcard.getAbsolutePath()+"/PetDroid/Fotos/"+aux.getId()+".jpg";
			
			File file = new File(str);
			file.delete();
		}
		db.execSQL("DELETE FROM propietarios WHERE prop_id ="+propietario.getProp_id()+"");
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
	
	db.execSQL("CREATE TABLE propietarios (prop_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
			"nombre TEXT, apellido1 TEXT, apellido2 TEXT, dni TEXT, telefono TEXT, " +
			"direccion TEXT,email TEXT)");
	db.execSQL("CREATE TABLE mascotas (mascota_id INTEGER PRIMARY KEY AUTOINCREMENT,"+
				"nombre TEXT, tipo INTEGER,fechaNac LONG, sexo INTEGER, raza TEXT, " +
				"propietario INTEGER, FOREIGN KEY (propietario) " +
				"REFERENCES propietarios (prop_id))");	
	db.execSQL("CREATE TABLE consultas (consulta_id INTEGER PRIMARY KEY AUTOINCREMENT,"+
			"tipo INTEGER, patologia TEXT,fecha LONG, tratamiento TEXT, observaciones TEXT, " +
			"mascota INTEGER, FOREIGN KEY (mascota) " +
			"REFERENCES mascotas (mascota_id))");
	
	//Introducimos los datos por defecto
	db.execSQL("INSERT INTO propietarios VALUES(null,'Jose Antonio','Polo','Barrero'," +
			"'76032545J','645182483','Plazuela Fuente Carmelas 11 6C','jose@yahoo.es')");
	db.execSQL("INSERT INTO mascotas VALUES(null,'Nala',1,1337785208596,2,'Beagle',1)");
	
	db.execSQL("INSERT INTO propietarios VALUES(null,'Francisco','Pérez','Arribas'," +
			"'98025524F','626548527','Calle Hernandez Pujalte 14 1C','fran@gmail.es')");
	db.execSQL("INSERT INTO mascotas VALUES(null,'Trini',1,1382009913613,2,'Podenco',2)");
	
	db.execSQL("INSERT INTO propietarios VALUES(null,'Manuel','Ledesma','Acosta'," +
			"'65044430M','644231456','Calle Volta 14 4A','manu@yahoo.es')");
	db.execSQL("INSERT INTO mascotas VALUES(null,'Simba',5,1382009913613,1,'Agaporni',3)");
	
	db.execSQL("INSERT INTO propietarios VALUES(null,'Andrea','Montiel','Moreno'," +
			"'78922276A','607821586','Plaza Mayor 15 2B','andrea@gmail.com')");
	db.execSQL("INSERT INTO mascotas VALUES(null,'Curro',1,1281537612439,1,'Podenco',4)");
	
	db.execSQL("INSERT INTO propietarios VALUES(null,'Álvaro','Corral','Carabias'," +
			"'19023743F','609715216','Calle El encinar S/N ','alvaro@insa.com')");
	db.execSQL("INSERT INTO mascotas VALUES(null,'Kiko',6,1382009913613,1,'Hamster afgano',5)");
	
	//Metos dos mascotas mas para dos dueños existentes
	db.execSQL("INSERT INTO mascotas VALUES(null,'Pichi',4,1262185255737,1,'Común',2)");
	db.execSQL("INSERT INTO mascotas VALUES(null,'Garras',2,1376924311286,2,'Siamés',2)");
	db.execSQL("INSERT INTO mascotas VALUES(null,'Manchas',3,1339081154965,2,'Común',3)");
	db.execSQL("INSERT INTO mascotas VALUES(null,'Thor',7,1344697366195,1,'Iguana',3)");
	
	//Meto consultas
	db.execSQL("INSERT INTO consultas VALUES(null,1,'Dolor abdominal',1344697366195,'Analgésicos','3 veces al día',1)");
	db.execSQL("INSERT INTO consultas VALUES(null,3,'No hay patología',1382009913613,'Tetravírica','Reposo 2 días',4)");
	db.execSQL("INSERT INTO consultas VALUES(null,5,'Fractura de hueso',1344697366195,'Sedante y escayola','Ingreso durante 2 días',2)");
	db.execSQL("INSERT INTO consultas VALUES(null,1,'Dolor abdominal',1382009913613,'Analgésicos','Después de la comida',4)");
	db.execSQL("INSERT INTO consultas VALUES(null,2,'Vómitos',1281537612439,'Dieta blanda','Una lata al día',3)");
	
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		
	}
	
	


}

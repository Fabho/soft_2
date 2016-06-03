package com.proyecto.petdroid.almacen;

import java.util.Vector;

import com.proyecto.petdroid.vo.ConsultaVo;
import com.proyecto.petdroid.vo.MascotaVo;
import com.proyecto.petdroid.vo.PropietarioVo;

public interface AlmacenMascotas {

	//Consultas
	public Vector<MascotaVo> listaMascotas();
	public Vector<PropietarioVo> listaPropietarios();
	public Vector<ConsultaVo> listaConsultas(int mascota_id);
	
	public MascotaVo obtenerMascota(int id);
	public PropietarioVo obtenerPropietario(int id);
	public int obtenerIdPropietario(int id_mascota);
	public ConsultaVo obtenerConsulta(int id);
	
	public int cantidadMascotasPorPropietario(int id);
	public Vector<MascotaVo> obtenerMascotasPorPropietario(int id);
	
	//Altas
	public int nuevoPropietario(PropietarioVo propietario);
	public void nuevaMascota(MascotaVo mascota,int id_prop);
	public void nuevaConsulta(ConsultaVo consulta);
	
	//Actualizaciones
	public void actualizaMascota(MascotaVo mascota,int id_prop);
	public void actualizaPropietario(PropietarioVo propietario);
	public void actualizaConsulta(ConsultaVo consulta);
	
	//Eliminaciones
	public void eliminaMascota(MascotaVo mascota);
	public void eliminaPropietario(PropietarioVo propietario);
}

package com.proyecto.petdroid.vo;

public class ConsultaVo {

	private int consulta_id;
	private int tipo;
	private String patologia;
	private long fecha;
	private String tratamiento;
	private String observaciones;
	private int mascota;
	
	public ConsultaVo(){}
	
	public ConsultaVo(int consulta_id, int tipo, String patologia, 
			long fecha, String tratamiento,String observaciones,int mascota){
		
		this.consulta_id = consulta_id;
		this.tipo = tipo;
		this.patologia = patologia;
		this.fecha = fecha;
		this.tratamiento = tratamiento;
		this.observaciones = observaciones;
		this.mascota = mascota;
	}

	public int getConsulta_id() {
		return consulta_id;
	}

	public void setConsulta_id(int consulta_id) {
		this.consulta_id = consulta_id;
	}

	public int getTipo() {
		return tipo;
	}

	public void setTipo(int tipo) {
		this.tipo = tipo;
	}

	public String getPatologia() {
		return patologia;
	}

	public void setPatologia(String patologia) {
		this.patologia = patologia;
	}

	public long getFecha() {
		return fecha;
	}

	public void setFecha(long fecha) {
		this.fecha = fecha;
	}

	public String getTratamiento() {
		return tratamiento;
	}

	public void setTratamiento(String tratamiento) {
		this.tratamiento = tratamiento;
	}

	public String getObservaciones() {
		return observaciones;
	}

	public void setObservaciones(String observaciones) {
		this.observaciones = observaciones;
	}

	public int getMascota() {
		return mascota;
	}

	public void setMascota(int mascota) {
		this.mascota = mascota;
	}

}

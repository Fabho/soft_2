package com.proyecto.petdroid.vo;

public class MascotaVo {

	private int id;
	private String nombre;
	private int tipo;
	private long fecha;
	private int sexo;
	private String raza;
	private String propietario;
	

	//Constructor
	public MascotaVo(int id,String nombre,int tipo,long fecha,int sexo,String raza,String propietario){
		this.id=id;
		this.nombre=nombre;
		this.tipo = tipo;
		this.fecha = fecha;
		this.sexo = sexo;
		this.raza = raza;
		this.propietario = propietario;
	}
	
	//Constructor con los atributos vacios
	public MascotaVo(){}

	//Getters y setters
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public int getTipo() {
		return tipo;
	}

	public void setTipo(int tipo) {
		this.tipo = tipo;
	}

	public long getFecha() {
		return fecha;
	}

	public void setFecha(long fecha) {
		this.fecha = fecha;
	}

	public int getSexo() {
		return sexo;
	}

	public void setSexo(int sexo) {
		this.sexo = sexo;
	}

	public String getRaza() {
		return raza;
	}

	public void setRaza(String raza) {
		this.raza = raza;
	}

	public String getPropietario() {
		return propietario;
	}

	public void setPropietario(String propietario) {
		this.propietario = propietario;
	}

	
	
}

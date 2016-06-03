package com.proyecto.petdroid.vo;

public class PropietarioVo {

	private int prop_id;
	private String nombre;
	private String apellido1;
	private String apellido2;
	private String dni;
	private String telefono;
	private String direccion;
	private String email;

	public PropietarioVo(int prop_id,String nombre,String apellido1, 
			String apellido2,String dni,String telefono,String direccion,String email){
		this.prop_id = prop_id;
		this.nombre = nombre;
		this.apellido1 = apellido1;
		this.apellido2 = apellido2;
		this.dni = dni;
		this.telefono = telefono;
		this.direccion = direccion;
		this.email = email;
	}
	
	public PropietarioVo(){}

	//Getters y setters
	
	public int getProp_id() {
		return prop_id;
	}

	public void setProp_id(int prop_id) {
		this.prop_id = prop_id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApellido1() {
		return apellido1;
	}

	public void setApellido1(String apellido1) {
		this.apellido1 = apellido1;
	}

	public String getApellido2() {
		return apellido2;
	}

	public void setApellido2(String apellido2) {
		this.apellido2 = apellido2;
	}

	public String getDni() {
		return dni;
	}

	public void setDni(String dni) {
		this.dni = dni;
	}

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public String getDireccion() {
		return direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	
}

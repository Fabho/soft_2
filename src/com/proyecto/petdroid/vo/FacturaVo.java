package com.proyecto.petdroid.vo;

public class FacturaVo {

	private int factura_id;
	private String concepto;
	private float cantidad;
	private float iva;
	private float cantidadtotal;
	private boolean pagada;
	private String observaciones;
	private int consulta;
	
	public FacturaVo(int factura_id, String concepto,float cantidad,float iva,
			float cantidadtotal, boolean pagada,String observaciones, int consulta){
		
		this.factura_id = factura_id;
		this.concepto = concepto;
		this.cantidad = cantidad;
		this.iva = iva;
		this.cantidadtotal = cantidadtotal;
		this.pagada = pagada;
		this.observaciones = observaciones;
	}

	public int getFactura_id() {
		return factura_id;
	}

	public void setFactura_id(int factura_id) {
		this.factura_id = factura_id;
	}

	public String getConcepto() {
		return concepto;
	}

	public void setConcepto(String concepto) {
		this.concepto = concepto;
	}

	public float getCantidad() {
		return cantidad;
	}

	public void setCantidad(float cantidad) {
		this.cantidad = cantidad;
	}

	public float getIva() {
		return iva;
	}

	public void setIva(float iva) {
		this.iva = iva;
	}

	public float getCantidadtotal() {
		return cantidadtotal;
	}

	public void setCantidadtotal(float cantidadtotal) {
		this.cantidadtotal = cantidadtotal;
	}

	public boolean isPagada() {
		return pagada;
	}

	public void setPagada(boolean pagada) {
		this.pagada = pagada;
	}

	public String getObservaciones() {
		return observaciones;
	}

	public void setObservaciones(String observaciones) {
		this.observaciones = observaciones;
	}

	public int getConsulta() {
		return consulta;
	}

	public void setConsulta(int consulta) {
		this.consulta = consulta;
	}

}

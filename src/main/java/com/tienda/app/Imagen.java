package com.tienda.app;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Imagen {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	private int producto;
	private String url;
	
	public Imagen(){};
	
	public Imagen(int producto, String url){
		this.producto = producto;
		this. url = url;
	}
	
	public int getProducto() {
		return producto;
	}
	public void setProducto(int producto) {
		this.producto = producto;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
}

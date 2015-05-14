package com.tienda.app;

import java.util.Date;
import java.util.HashMap;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Pedido {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	private int user;
	private HashMap<Integer,Integer> productos;
	private Estado estado;
	private Date fecha;
	private String name;

	public Pedido(){
		
	};
	
	public Pedido( int user,HashMap<Integer,Integer> productos, String name){
		this.setUser(user);
		this.setProductos(productos);
		this.setFecha(new Date());
		this.setEstado(Estado.Preparando);
		this.name = name;
	}//Constructor

	public HashMap<Integer,Integer> getProductos() {
		return productos;
	}

	public void setProductos(HashMap<Integer,Integer> productos) {
		this.productos = productos;
	}

	public int getUser() {
		return user;
	}

	public void setUser(int user) {
		this.user = user;
	}
	
	public int getId(){
		return id;
	}

	public Estado getEstado() {
		return estado;
	}

	public void setEstado(Estado estado) {
		this.estado = estado;
	}

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public void setName(String name){
		this.name = name;
	}
	
	public String getName(){
		return name;
	}
}

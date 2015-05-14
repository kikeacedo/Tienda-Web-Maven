package com.tienda.app;

import java.util.HashMap;
import java.util.Set;

import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;


@Component

@Scope(value = WebApplicationContext.SCOPE_SESSION, proxyMode = ScopedProxyMode.TARGET_CLASS)
public class UserCookie {

	private HashMap<Integer,Integer> lista_productos;
	private boolean sesion_iniciada;

	public UserCookie(){
		lista_productos = new HashMap<Integer,Integer>();
		setSesion_iniciada(false);
	}

	public HashMap<Integer,Integer> getLista_productos() {
		return lista_productos;
	}

	public void addItem(int item){
		if(lista_productos.containsKey(item))
			lista_productos.replace(item, lista_productos.get(item), lista_productos.get(item)+1);
		else
			lista_productos.put(item, 1);
	}

	public void removeItem(int item){
		if(lista_productos.get(item) > 1)
			lista_productos.replace(item, lista_productos.get(item), lista_productos.get(item)-1);
		else
			lista_productos.remove(item);	
	}
	
	public void vaciar(){
		lista_productos.clear();
	}

	public int size(){
		int size = 0;

		Set<Integer> keys = lista_productos.keySet();
		for(int i : keys){
			size +=lista_productos.get(i);
		}//for		return lista_productos.size();

		return size;
	}//size()

	public boolean isSesion_iniciada() {
		return sesion_iniciada;
	}

	public void setSesion_iniciada(boolean sesion_iniciada) {
		this.sesion_iniciada = sesion_iniciada;
	}

}

package com.tienda.app;

import java.util.List;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Entity
public class User {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	private String username = null;
	private String password;
	private String name;
	private String apellidos;
	private String direccion;
	private String mail;
	private String telefono;
	
	@ElementCollection(fetch = FetchType.EAGER) 
	private List<GrantedAuthority> roles;
	
	public User(){};
	
	public User(String user, String password, List<GrantedAuthority> roles, String name, String apellidos, String direccion, String mail, String telefono) {
		this.username = user;
		this.password = new BCryptPasswordEncoder().encode(password);
		this.roles = roles;
		this.name = name;
		this.apellidos = apellidos;
		this.direccion = direccion;
		this.mail = mail;
		this.telefono = telefono;
		
	}
	
	public void setUserName(String name) {
		this.username = name;
	}

	public String getUserName() {
		return username;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	public List<GrantedAuthority> getRoles() {
		return roles;
	}

	public void setRoles(List<GrantedAuthority> roles) {
		this.roles = roles;
	}

	public String getPasswordHash() {
		return password;
	}

	public void setPasswordHash(String password) {
		this.password = password;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getDireccion() {
		return direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	public String getMail() {
		return mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getApellidos() {
		return apellidos;
	}

	public void setApellidos(String apellidos) {
		this.apellidos = apellidos;
	}
}
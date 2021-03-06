package pe.edu.pucp.proyectorh.model;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Clase mapeada contra Usuario DBObject
 * 
 * @author Cesar
 * 
 */
public class Usuario {

	private String ID;
	private String username;
	private String password;
	private ArrayList<ColaboradorEquipoTrabajo> padres;
	private ArrayList<ArrayList<ArrayList<ColaboradorEquipoTrabajo>>> hijos;
	private HashMap<String, Rol> roles;
	private ColaboradorEquipoTrabajo jefe;
	private boolean seTrajoContactos = false;

	public Usuario(String iD, String username, String password) {
		super();
		ID = iD;
		this.username = username;
		this.password = password;
	}

	public String getID() {
		return ID;
	}

	public void setID(String iD) {
		ID = iD;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public ArrayList<ColaboradorEquipoTrabajo> getPadres() {
		return padres;
	}

	public void setPadres(ArrayList<ColaboradorEquipoTrabajo> padres) {
		this.padres = padres;
	}

	public ArrayList<ArrayList<ArrayList<ColaboradorEquipoTrabajo>>> getHijos() {
		return hijos;
	}

	public void setHijos(
			ArrayList<ArrayList<ArrayList<ColaboradorEquipoTrabajo>>> hijos) {
		this.hijos = hijos;
	}

	public ColaboradorEquipoTrabajo getJefe() {
		return jefe;
	}

	public void setJefe(ColaboradorEquipoTrabajo jefe) {
		this.jefe = jefe;
	}

	public boolean isSeTrajoContactos() {
		return seTrajoContactos;
	}

	public void setSeTrajoContactos(boolean seTrajoContactos) {
		this.seTrajoContactos = seTrajoContactos;
	}

	public HashMap<String, Rol> getRoles() {
		return roles;
	}

	public void setRoles(HashMap<String, Rol> roles) {
		this.roles = roles;
	}

}

package pe.edu.pucp.proyectorh.model;

import java.util.Date;

import pe.edu.pucp.proyectorh.utils.Constante;

/**
 * Clase Colaborador mapeada contra ColaboradorDTO de la aplicacion web
 * 
 * @author Cesar
 * 
 */
public class Colaborador {

	private String nombres;
	private String apellidos;
	private String area;
	private String puesto;
	private String anexo;
	private Date fechaIngreso;
	private Date fechaNacimiento;
	private String correoElectronico;
	private String telefono;
	private String centroEstudios;

	public Colaborador() {
	}

	public Colaborador(String nombres, String apellidos, String area,
			String puesto) {
		super();
		this.nombres = nombres;
		this.apellidos = apellidos;
		this.area = area;
		this.puesto = puesto;
	}

	public Colaborador(String nombres, String apellidos, String area,
			String puesto, Date fechaIngreso, Date fechaNacimiento,
			String correoElectronico, String telefono) {
		super();
		this.nombres = nombres;
		this.apellidos = apellidos;
		this.area = area;
		this.puesto = puesto;
		this.fechaIngreso = fechaIngreso;
		this.fechaNacimiento = fechaNacimiento;
		this.correoElectronico = correoElectronico;
		this.telefono = telefono;
	}

	public String getCorreoElectronico() {
		return correoElectronico;
	}

	public void setCorreoElectronico(String correoElectronico) {
		this.correoElectronico = correoElectronico;
	}

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public String getNombres() {
		return nombres;
	}

	public void setNombres(String nombres) {
		this.nombres = nombres;
	}

	public String getApellidos() {
		return apellidos;
	}

	public void setApellidos(String apellidos) {
		this.apellidos = apellidos;
	}

	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}

	public String getPuesto() {
		return puesto;
	}

	public void setPuesto(String puesto) {
		this.puesto = puesto;
	}

	public String getAnexo() {
		return anexo;
	}

	public void setAnexo(String anexo) {
		this.anexo = anexo;
	}

	public Date getFechaIngreso() {
		return fechaIngreso;
	}

	public void setFechaIngreso(Date fechaIngreso) {
		this.fechaIngreso = fechaIngreso;
	}

	public Date getFechaNacimiento() {
		return fechaNacimiento;
	}

	public void setFechaNacimiento(Date fechaNacimiento) {
		this.fechaNacimiento = fechaNacimiento;
	}

	public String getCentroEstudios() {
		return centroEstudios;
	}

	public void setCentroEstudios(String centroEstudios) {
		this.centroEstudios = centroEstudios;
	}

	@Override
	public String toString() {
		return nombres + Constante.ESPACIO_VACIO + apellidos;
	}
}
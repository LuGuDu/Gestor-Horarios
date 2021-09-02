package lugudu.gestorHorarios.dominio;

import java.util.UUID;

public class RegistroHorario {

	private UUID idRegistro;
	private UUID idAuxiliar;
	private String horaInicio;
	private String horaFinal;
	private String nombreUsuario;
	private String direccion;
	private String telefono;
	private String dependiente;
	private String dia;

	public RegistroHorario(String horaInicio, String horaFinal, String nombreUsuario) {
		this.horaInicio = horaInicio;
		this.horaFinal = horaFinal;
		this.nombreUsuario = nombreUsuario;
	}

	public RegistroHorario() {
		this.idRegistro = UUID.randomUUID();
	}

	public UUID getIdRegistro() {
		return idRegistro;
	}

	public void setIdRegistro(UUID idRegistro) {
		this.idRegistro = idRegistro;
	}

	public UUID getIdAuxiliar() {
		return idAuxiliar;
	}

	public void setIdAuxiliar(UUID idAuxiliar) {
		this.idAuxiliar = idAuxiliar;
	}

	public String getHoraInicio() {
		return horaInicio;
	}

	public void setHoraInicio(String horaInicio) {
		this.horaInicio = horaInicio;
	}

	public String getHoraFinal() {
		return horaFinal;
	}

	public void setHoraFinal(String horaFinal) {
		this.horaFinal = horaFinal;
	}

	public String getNombreUsuario() {
		return nombreUsuario;
	}

	public void setNombreUsuario(String nombreUsuario) {
		this.nombreUsuario = nombreUsuario;
	}

	public String getDireccion() {
		return direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public String getDependiente() {
		return dependiente;
	}

	public void setDependiente(String dependiente) {
		this.dependiente = dependiente;
	}

	public String getDia() {
		return dia;
	}

	public void setDia(String dia) {
		this.dia = dia;
	}
}

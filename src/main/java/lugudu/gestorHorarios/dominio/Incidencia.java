package lugudu.gestorHorarios.dominio;

import java.util.UUID;

public class Incidencia {

	private UUID id;
	private UUID idAuxiliar;
	private String dia;
	private String mes;
	private String year;
	private double horasNormales;
	private double horasFestivas;
	private double kilometraje;
	private Double horasFueraConvenio;

	public Incidencia(String dia, String mes, String year) {
		this.id = UUID.randomUUID();
		this.dia = dia;
		this.mes = mes;
		this.year = year;
	}

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	public UUID getIdAuxiliar() {
		return idAuxiliar;
	}

	public void setIdAuxiliar(UUID idAuxiliar) {
		this.idAuxiliar = idAuxiliar;
	}

	public String getDia() {
		return dia;
	}

	public void setDia(String dia) {
		this.dia = dia;
	}

	public String getMes() {
		return mes;
	}

	public void setMes(String mes) {
		this.mes = mes;
	}

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public double getHorasNormales() {
		return horasNormales;
	}

	public void setHorasNormales(double horasNormales) {
		this.horasNormales = horasNormales;
	}

	public double getHorasFestivas() {
		return horasFestivas;
	}

	public void setHorasFestivas(double horasFestivas) {
		this.horasFestivas = horasFestivas;
	}

	public double getKilometraje() {
		return kilometraje;
	}

	public void setKilometraje(double kilometraje) {
		this.kilometraje = kilometraje;
	}

	public Double getHorasFueraConvenio() {
		return horasFueraConvenio;
	}

	public void setHorasFueraConvenio(Double horasFueraConvenio) {
		this.horasFueraConvenio = horasFueraConvenio;
	}
}

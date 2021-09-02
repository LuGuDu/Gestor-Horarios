package lugudu.gestorHorarios.persistencia;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;

import lugudu.gestorHorarios.dominio.Incidencia;

public class IncidenciaDAO implements Serializable {

	private static final long serialVersionUID = -3557816689629536655L;

	public void insertar(Incidencia incidencia) throws SQLException {
		String strFecha = incidencia.getYear() + "-" + incidencia.getMes() + "-" + incidencia.getDia();

		String sentencia = "INSERT IGNORE INTO horariosaux.incidencias (id, idauxiliar,"
				+ "fecha, horasNormales, horasFestivas, kilometraje, horasFueraConvenio) " + "VALUES (" + "'"
				+ incidencia.getId().toString() + "'," + "'" + incidencia.getIdAuxiliar() + "'," + "'" + strFecha + "',"
				+ "'" + incidencia.getHorasNormales() + "'," + "'" + incidencia.getHorasFestivas() + "'," + "'"
				+ incidencia.getKilometraje() + "', '" + incidencia.getHorasFueraConvenio() + "');";

		AgenteDB.getAgenteDB().execute(sentencia);
	}

	public ResultSet selectByIdMesYear(String idAuxiliar, String mes, String year) throws SQLException {
		String sentencia = "SELECT * FROM horariosaux.incidencias WHERE idAuxiliar LIKE '" + idAuxiliar + "' "
				+ "AND fecha LIKE '%" + year + "-" + mes + "%';";
		return AgenteDB.getAgenteDB().select(sentencia);
	}

	public void updateIncidencia(Incidencia incidencia) throws SQLException {
		String sentencia = "UPDATE horariosaux.incidencias SET" + "`horasNormales` = '" + incidencia.getHorasNormales()
				+ "'," + "`horasFestivas` = '" + incidencia.getHorasFestivas() + "'," + "`kilometraje` = '"
				+ incidencia.getKilometraje() + "', `horasFueraConvenio` = '" + incidencia.getHorasFueraConvenio() + "'"
				+ "WHERE id = '" + incidencia.getId().toString() + "';";
		
		System.out.println(incidencia.getId().toString());
		
		AgenteDB.getAgenteDB().execute(sentencia);
	}

	public double[] calcularByIdMesYear(String id, String mes, String year) throws SQLException {
		String sentencia = "SELECT horasNormales, horasFestivas, kilometraje, horasFueraConvenio "
				+ "FROM horariosaux.incidencias "
				+ "WHERE idAuxiliar LIKE '" + id + "' " + "AND fecha LIKE '%" + year + "-" + mes + "%';";

		ResultSet rs = AgenteDB.getAgenteDB().select(sentencia);

		double horasNormalesMes = 0.0;
		double horasFestivasMes = 0.0;
		double kilometrajeMes = 0.0;
		double horasFueraConvenioMes = 0.0;

		while (rs.next()) {
			horasNormalesMes = Double.valueOf(rs.getString("horasNormales")) + horasNormalesMes;
			horasFestivasMes = Double.valueOf(rs.getString("horasFestivas")) + horasFestivasMes;
			kilometrajeMes = Double.valueOf(rs.getString("kilometraje")) + kilometrajeMes;
			horasFueraConvenioMes = Double.valueOf(rs.getString("horasFueraConvenio")) + horasFueraConvenioMes;
		}

		double[] valores = { horasNormalesMes, horasFestivasMes, kilometrajeMes, horasFueraConvenioMes };

		return valores;
	}

	public String getIdAuxiliarByIdMesYear(String id, String mes, String year) throws SQLException {
		String sentencia = "SELECT idauxiliar FROM horariosaux.incidencias " + "WHERE idAuxiliar LIKE '" + id + "' "
				+ "AND fecha LIKE '%" + year + "-" + mes + "%';";
		ResultSet rs = AgenteDB.getAgenteDB().select(sentencia);

		if (!rs.next()) {
			id = null;
		}
		return id;
	}
}

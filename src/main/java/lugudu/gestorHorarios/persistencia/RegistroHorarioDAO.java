package lugudu.gestorHorarios.persistencia;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import lugudu.gestorHorarios.dominio.BDConstantes;
import lugudu.gestorHorarios.dominio.RegistroHorario;

public class RegistroHorarioDAO implements Serializable{
	
	private static final long serialVersionUID = -1139257426889402566L;
	private static final Log LOG = new Log();

	public void insertar(RegistroHorario r) throws SQLException {

		String sentencia = "INSERT IGNORE INTO horariosaux.registrohorarios (id, idauxiliar,"
				+ "desde, hasta, nombre, direccion, telefono, dependiente, dia) " + 
				"VALUES (" 
				+ "'" + r.getIdRegistro() + "'," 
				+ "'" + r.getIdAuxiliar() + "'," 
				+ "'" + r.getHoraInicio() + "',"
				+ "'" + r.getHoraFinal() + "'," 
				+ "HEX(AES_ENCRYPT('"+r.getNombreUsuario()+"','"+BDConstantes.ENCRYPTPASS+"'))," 
				+ "HEX(AES_ENCRYPT('"+r.getDireccion() +"','"+BDConstantes.ENCRYPTPASS+"')),"
				+ "HEX(AES_ENCRYPT('"+r.getTelefono() +"','"+BDConstantes.ENCRYPTPASS+"')),"
				+ "HEX(AES_ENCRYPT('"+r.getDependiente() +"','"+BDConstantes.ENCRYPTPASS+"')),"
				+ "'" + r.getDia() +"');";

		AgenteDB.getAgenteDB().execute(sentencia);
		LOG.setMessage("Registro agregado con exito");
	}

	public ResultSet selectByDia(String idAuxiliar, String dia) throws SQLException {
		String sentencia = "SELECT id, idauxiliar, desde, hasta, "
				+ "AES_DECRYPT(UNHEX(nombre), '"+BDConstantes.ENCRYPTPASS+"') AS nombre, "
				+ "AES_DECRYPT(UNHEX(direccion), '"+BDConstantes.ENCRYPTPASS+"') AS direccion, "
				+ "AES_DECRYPT(UNHEX(telefono), '"+BDConstantes.ENCRYPTPASS+"') AS telefono, "
				+ "AES_DECRYPT(UNHEX(dependiente), '"+BDConstantes.ENCRYPTPASS+"') AS dependiente, "
				+ "dia "
				+ "FROM horariosaux.registrohorarios WHERE idAuxiliar LIKE '" + idAuxiliar + "' "
				+ "AND dia LIKE '" + dia + "' ORDER BY desde ASC;";
		return AgenteDB.getAgenteDB().select(sentencia);
	}
	
	public void deleteById(String id) throws SQLException {
		String sentencia = "DELETE FROM horariosaux.registrohorarios WHERE id LIKE '"+id+"';";
		AgenteDB.getAgenteDB().execute(sentencia);
		LOG.setMessage("Registro borrado con exito");
	}

	public void update(RegistroHorario r) throws SQLException {
		String sentencia = "UPDATE horariosaux.registrohorarios SET"
			+ "`desde` = '" + r.getHoraInicio() + "'," 
			+ "`hasta` = '" + r.getHoraFinal() + "'," 
			+ "`nombre` = HEX(AES_ENCRYPT('"+r.getNombreUsuario()+"','"+BDConstantes.ENCRYPTPASS+"'))," 
			+ "`direccion` = HEX(AES_ENCRYPT('"+r.getDireccion() +"','"+BDConstantes.ENCRYPTPASS+"'))," 
			+ "`telefono` = HEX(AES_ENCRYPT('"+r.getTelefono() +"','"+BDConstantes.ENCRYPTPASS+"'))," 
			+ "`dependiente` = HEX(AES_ENCRYPT('"+r.getDependiente() +"','"+BDConstantes.ENCRYPTPASS+"'))" 
			+ "WHERE id = '" + r.getIdRegistro().toString() + "';";
		AgenteDB.getAgenteDB().execute(sentencia);
		LOG.setMessage("Registro modificado con exito");
	}

	public boolean buscarPorId(String id) throws SQLException {
		boolean encontrado = false;

		String sentencia = "SELECT id FROM horariosaux.registrohorarios WHERE id = '" + id + "';";
		ResultSet set = AgenteDB.getAgenteDB().select(sentencia);
		if (!set.next())
			encontrado = false;
		else
			encontrado = true;
		return encontrado;
	}

	public double calcularHoras(String idAuxiliar, String dia) throws SQLException, ParseException {
		
		String sentencia = "SELECT desde, hasta FROM horariosaux.registrohorarios WHERE idAuxiliar LIKE '" + idAuxiliar + "' "
				+ "AND dia LIKE '" + dia + "';";
		ResultSet rs = AgenteDB.getAgenteDB().select(sentencia);
		
		SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm");
		double horasTotalesDia = 0.0;	 

		while(rs.next()) {
			String desde = rs.getString("desde");
			String hasta = rs.getString("hasta");

			Date fechaInicial = dateFormat.parse(desde);
			Date fechaFinal = dateFormat.parse(hasta);
			 			
			double minutos=(double) ((fechaFinal.getTime()-fechaInicial.getTime())/60000);
			
			horasTotalesDia = horasTotalesDia + (minutos/60);
		}
		
		return horasTotalesDia;
	}
}

package lugudu.gestorHorarios.persistencia;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

import javax.swing.JOptionPane;

import lugudu.gestorHorarios.dominio.Auxiliar;
import lugudu.gestorHorarios.dominio.BDConstantes;

public class AuxiliarDAO implements Serializable{
	
	private static final long serialVersionUID = 3068474215244858042L;
	private static final Log LOG = new Log();

	public void insertar(Auxiliar auxiliar) throws SQLException {
		
		String strDate = "0000/00/00";
		String activo = "1";
		
		if(auxiliar.getFechaInicioContrato() != null) {
			DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
			strDate = dateFormat.format(auxiliar.getFechaInicioContrato());
		}
		
		if(!auxiliar.isActivo())
			activo = "0";
		
		String sentencia = "INSERT IGNORE INTO horariosaux.auxiliares (id, dni, nombre, apellido1,"
				+ "apellido2, telefono, fechainiciocontrato, correo, activo) "
				+ "VALUES ("
				+ "'"+auxiliar.getId().toString()+"',"
				+ "HEX(AES_ENCRYPT('"+auxiliar.getDni()+"','"+BDConstantes.ENCRYPTPASS+"')),"
				+ "HEX(AES_ENCRYPT('"+auxiliar.getNombre()+"','"+BDConstantes.ENCRYPTPASS+"')),"
				+ "HEX(AES_ENCRYPT('"+auxiliar.getApellido1()+"','"+BDConstantes.ENCRYPTPASS+"')),"
				+ "HEX(AES_ENCRYPT('"+auxiliar.getApellido2()+"','"+BDConstantes.ENCRYPTPASS+"')),"
				+ "HEX(AES_ENCRYPT('"+auxiliar.getTelefono()+"','"+BDConstantes.ENCRYPTPASS+"')),"
				+ "'"+strDate+"',"
				+ "HEX(AES_ENCRYPT('"+auxiliar.getCorreo()+"','"+BDConstantes.ENCRYPTPASS+"')),"
				+ "'"+activo+"');";
		
		if (!buscarPorDni(auxiliar.getDni())) {
			AgenteDB.getAgenteDB().execute(sentencia);
			LOG.setMessage("Auxiliar insertado con exito");
		} else { 
			JOptionPane.showMessageDialog(null, "Este DNI ya estaba registrado, vuelva a crearlo", "Error", 0);
			LOG.setMessage("El DNI registrado previamente");
		}
	}

	public boolean buscarPorDni(String dni) throws SQLException {
		boolean encontrado = false;

		String sentencia = "SELECT id FROM horariosaux.auxiliares "
				+ "WHERE dni = HEX(AES_ENCRYPT('"+dni+"','"+BDConstantes.ENCRYPTPASS+"'));";
		ResultSet set = AgenteDB.getAgenteDB().select(sentencia);
		if (!set.next())
			encontrado = false;
		else
			encontrado = true;
		return encontrado;
	}

	public ResultSet selectAll() throws SQLException {
		String sentencia = "SELECT id,"
				+ "AES_DECRYPT(UNHEX(dni), '"+BDConstantes.ENCRYPTPASS+"') AS dni,"
				+ "AES_DECRYPT(UNHEX(nombre), '"+BDConstantes.ENCRYPTPASS+"') AS nombre,"
				+ "AES_DECRYPT(UNHEX(apellido1), '"+BDConstantes.ENCRYPTPASS+"') AS apellido1,"
				+ "AES_DECRYPT(UNHEX(apellido2), '"+BDConstantes.ENCRYPTPASS+"') AS apellido2,"
				+ "AES_DECRYPT(UNHEX(telefono), '"+BDConstantes.ENCRYPTPASS+"') AS telefono,"
				+ "fechainiciocontrato,"
				+ "AES_DECRYPT(UNHEX(correo), '"+BDConstantes.ENCRYPTPASS+"') AS correo,"
				+ "activo" 
				+ " FROM horariosaux.auxiliares;";
		return AgenteDB.getAgenteDB().select(sentencia);
	}

	public ResultSet selectById(String idModificar) throws SQLException {
		String sentencia = "SELECT id,"
				+ "AES_DECRYPT(UNHEX(dni), '"+BDConstantes.ENCRYPTPASS+"') AS dni,"
				+ "AES_DECRYPT(UNHEX(nombre), '"+BDConstantes.ENCRYPTPASS+"') AS nombre,"
				+ "AES_DECRYPT(UNHEX(apellido1), '"+BDConstantes.ENCRYPTPASS+"') AS apellido1,"
				+ "AES_DECRYPT(UNHEX(apellido2), '"+BDConstantes.ENCRYPTPASS+"') AS apellido2,"
				+ "AES_DECRYPT(UNHEX(telefono), '"+BDConstantes.ENCRYPTPASS+"') AS telefono,"
				+ "fechainiciocontrato,"
				+ "AES_DECRYPT(UNHEX(correo), '"+BDConstantes.ENCRYPTPASS+"') AS correo,"
				+ "activo" 
				+ " FROM horariosaux.auxiliares WHERE id LIKE '"+idModificar+"';";
		return AgenteDB.getAgenteDB().select(sentencia);
	}
	
	public void deleteById(String id) throws SQLException {
		String sentencia = "DELETE FROM horariosaux.auxiliares WHERE id LIKE '"+id+"';";
		AgenteDB.getAgenteDB().execute(sentencia);
		LOG.setMessage("Auxiliar borrado con exito");
	}

	public void updateAuxiliar(Auxiliar auxiliar) throws SQLException {
		
		String strDate = "0000/00/00";
		String activo = "1";
		
		if(auxiliar.getFechaInicioContrato() != null) {
			DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
			strDate = dateFormat.format(auxiliar.getFechaInicioContrato());
		}
		
		if(!auxiliar.isActivo())
			activo = "0";
		
		String sentencia = "UPDATE horariosaux.auxiliares SET"
				+ "`dni` = HEX(AES_ENCRYPT('"+auxiliar.getDni()+"','"+BDConstantes.ENCRYPTPASS+"')),"
				+ "`nombre` = HEX(AES_ENCRYPT('"+auxiliar.getNombre()+"','"+BDConstantes.ENCRYPTPASS+"')),"
				+ "`apellido1` = HEX(AES_ENCRYPT('"+auxiliar.getApellido1()+"','"+BDConstantes.ENCRYPTPASS+"')),"
				+ "`apellido2` = HEX(AES_ENCRYPT('"+auxiliar.getApellido2()+"','"+BDConstantes.ENCRYPTPASS+"')),"
				+ "`telefono` = HEX(AES_ENCRYPT('"+auxiliar.getTelefono()+"','"+BDConstantes.ENCRYPTPASS+"')),"
				+ "`fechainiciocontrato` = '"+strDate+"',"
				+ "`correo` = HEX(AES_ENCRYPT('"+auxiliar.getCorreo()+"','"+BDConstantes.ENCRYPTPASS+"')),"
				+ "`activo` = '"+activo+"'"
					+ "WHERE id = '"+auxiliar.getId().toString()+"';";
		AgenteDB.getAgenteDB().execute(sentencia);
		LOG.setMessage("Auxiliar modificado con exito");
	}
}

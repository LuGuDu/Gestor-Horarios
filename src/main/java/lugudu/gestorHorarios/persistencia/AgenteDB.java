package lugudu.gestorHorarios.persistencia;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import lugudu.gestorHorarios.dominio.BDConstantes;

public final class AgenteDB {

	private static AgenteDB mInstancia;
	private Connection mBD;

	private AgenteDB() throws SQLException {
		mBD = DriverManager.getConnection(BDConstantes.CONNECTION_STRING, BDConstantes.DBUSER, BDConstantes.DBPASS);
	}

	public static AgenteDB getAgenteDB() throws SQLException {
		if (mInstancia == null)
			mInstancia = new AgenteDB();
		return mInstancia;
	}

	public int execute(String instruccion) throws SQLException {
		int res = 0;
		Statement stmt = mBD.createStatement();
		try {
			res = stmt.executeUpdate(instruccion);
			stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			stmt.close();
		}
		return res;
	}

	public ResultSet select(String instruccion) throws SQLException {
		Statement stmt = mBD.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
		return stmt.executeQuery(instruccion);
	}
}

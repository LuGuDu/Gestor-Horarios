package lugudu.gestorHorarios.dominio;

public final class BDConstantes {

	private static final String CONNECTION_IP = "127.0.0.1";
	private static final String DBPORT = "3306";
	private static final String DBNAME = "horariosaux";
	private static final String ZONA_HORARIA = "?serverTimezone=UTC";
	public static final String CONNECTION_STRING = "jdbc:mysql://" + CONNECTION_IP + ":" + DBPORT + "/" + DBNAME
			+ ZONA_HORARIA;
	public static final String DBUSER = "root";
	public static final String DBPASS = "root";
	
	public static final String ENCRYPTPASS = "horariosAuxiliares";

	private BDConstantes() {
		throw new IllegalStateException("Utility class");
	}
}

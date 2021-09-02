package lugudu.gestorHorarios.persistencia;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFileChooser;

public class ExportarDatos {

	private static final String USER_HOME = "user.home";
	private static final Log LOG = new Log();

	private RegistroHorarioDAO registroHorarioDao = new RegistroHorarioDAO();
	private IncidenciaDAO incidenciaDao = new IncidenciaDAO();
	private AuxiliarDAO auxiliarDao = new AuxiliarDAO();

	private boolean archivoSeleccionado = true;

	public String seleciconarDirectorioGuardar(String path) {
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setDialogTitle("Seleccione donde guardar el documento");
		fileChooser.setSelectedFile(new File(path));

		path = getPath(path, fileChooser);

		return path;
	}

	private String getPath(String path, JFileChooser fileChooser) {
		int userSelection = fileChooser.showSaveDialog(null);

		if (userSelection == JFileChooser.APPROVE_OPTION) {
			path = fileChooser.getSelectedFile().getAbsolutePath();
			archivoSeleccionado = true;
		} else if (userSelection == JFileChooser.CANCEL_OPTION) {
			archivoSeleccionado = false;
		} else {
			archivoSeleccionado = false;
		}
		return path;
	}

	public void descargarHorario(String idAuxiliar) throws IOException {

		String path = System.getProperty(USER_HOME) + "/Desktop/" + "/HorarioAuxiliar.csv";
		path = seleciconarDirectorioGuardar(path);

		if (!archivoSeleccionado)
			return;

		FileWriter csvWriter = new FileWriter(path);
		csvWriter.append("DIA; DESDE; HASTA; NOMBRE USUARIO; DIRECCION; TELEFONO; DEPENDIENTE;\n");

		String[] dia = { "lunes", "martes", "miercoles", "jueves", "viernes", "sabado", "domingo" };

		for (String elemento : dia) {
			try {
				ResultSet rs = registroHorarioDao.selectByDia(idAuxiliar, elemento);
				while (rs.next()) {
					csvWriter.append(elemento + "; " + rs.getString("desde") + "; " + rs.getString("hasta") + "; "
							+ rs.getString("nombre") + "; " + rs.getString("direccion") + "; "
							+ rs.getString("telefono") + "; " + rs.getString("dependiente") + "\n");
				}

			} catch (SQLException e) {
				e.printStackTrace();
			}
			csvWriter.append("\n");
		}
		csvWriter.flush();
		csvWriter.close();
		LOG.setMessage("Documento creado con exito");
	}

	public void descargarTotalMes(String mes, String year) throws IOException {

		String path = System.getProperty(USER_HOME) + "/Desktop/" + "/Total_Mes_" + mes + "_" + year + ".csv";
		path = seleciconarDirectorioGuardar(path);

		if (!archivoSeleccionado)
			return;

		FileWriter csvWriter = new FileWriter(path);
		csvWriter.append("No; NOMBRE AUXILIAR; HORAS NORMALES; HORAS FESTIVAS; HORAS FUERA CONVENIO; KILOMETRAJE\n");

		try {

			ResultSet rs = auxiliarDao.selectAll();
			List<String[]> listaCompleta = new ArrayList<>();

			while (rs.next()) {
				String id = rs.getString("id");
				String nombre = rs.getString("nombre") + " " + rs.getString("apellido1") + " "
						+ rs.getString("apellido2");
				String[] stringIdNombre = { id, nombre };
				listaCompleta.add(stringIdNombre);
			}
			int contador = 1;
			for (String[] registro : listaCompleta) {
				double[] valores = incidenciaDao.calcularByIdMesYear(registro[0], mes, year);
				String id = incidenciaDao.getIdAuxiliarByIdMesYear(registro[0], mes, year);

				if (id == null)
					continue;

				csvWriter.append(contador + ";" + registro[1] + "; " + valores[0] + "; " + valores[1] + "; " + valores[3] + "; "
						+ valores[2] + "\n");
				contador++;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		csvWriter.flush();
		csvWriter.close();
		LOG.setMessage("Documento creado con exito");
	}
}

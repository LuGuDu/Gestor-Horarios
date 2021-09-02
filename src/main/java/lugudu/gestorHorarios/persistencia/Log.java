package lugudu.gestorHorarios.persistencia;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import lugudu.gestorHorarios.presentacion.VentanaPrincipal;

public class Log implements Serializable {

	private static final long serialVersionUID = 3068474215244858042L;

	public void setMessage(String mensaje) {

		DateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
		String strDate = formatter.format(new Date());

		VentanaPrincipal vp = VentanaPrincipal.getInterfaz();
		vp.setBarraEstado(mensaje + " - " + strDate);

		escribirEnArchivoLog(">>> " + mensaje + " - " + strDate);
	}

	private static void escribirEnArchivoLog(String mensaje) {
		try (FileWriter fw = new FileWriter("log.log", true);
				BufferedWriter bw = new BufferedWriter(fw);
				PrintWriter out = new PrintWriter(bw)) {
			out.println(mensaje);

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}

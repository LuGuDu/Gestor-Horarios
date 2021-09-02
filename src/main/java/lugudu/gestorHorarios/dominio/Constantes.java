package lugudu.gestorHorarios.dominio;

import javax.swing.UIManager;
import javax.swing.border.Border;

public final class Constantes {

	public static final Border DEFAULT_BORDER = UIManager.getLookAndFeel().getDefaults().getBorder("TextField.border");

	private Constantes() {
		throw new IllegalStateException("Utility class");
	}

}

package lugudu.gestorHorarios.presentacion;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.SQLException;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.border.EmptyBorder;

public final class HorarioAuxiliar extends JFrame {

	private static final long serialVersionUID = 3856247940447670642L;
	private static HorarioAuxiliar mInstancia;

	private String idAuxiliar;

	private JPanel contentPane;
	private JTabbedPane tabbedPane;
	private JPanel pLunes;
	private JPanel pMartes;
	private JPanel pMiercoles;
	private JPanel pJueves;
	private JPanel pViernes;
	private JPanel pSabado;
	private JPanel pDomingo;

	private HorarioAuxiliar() {
		addWindowListener(new ThisWindowListener());

		setTitle("Horario de Auxiliar");

		setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
		setBounds(100, 100, 984, 367);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		GridBagLayout gbl_contentPane = new GridBagLayout();
		gbl_contentPane.columnWidths = new int[] { 0, 0, 0, 0 };
		gbl_contentPane.rowHeights = new int[] { 0, 0, 0, 0 };
		gbl_contentPane.columnWeights = new double[] { 0.0, 1.0, 0.0, Double.MIN_VALUE };
		gbl_contentPane.rowWeights = new double[] { 0.0, 1.0, 0.0, Double.MIN_VALUE };
		contentPane.setLayout(gbl_contentPane);

		tabbedPane = new JTabbedPane(javax.swing.SwingConstants.TOP);
		GridBagConstraints gbc_tabbedPane = new GridBagConstraints();
		gbc_tabbedPane.insets = new Insets(0, 0, 5, 5);
		gbc_tabbedPane.fill = GridBagConstraints.BOTH;
		gbc_tabbedPane.gridx = 1;
		gbc_tabbedPane.gridy = 1;
		contentPane.add(tabbedPane, gbc_tabbedPane);

		pLunes = new JPanel();
		pLunes.addComponentListener(new PComponentListener("lunes"));
		tabbedPane.addTab("Lunes", null, pLunes, null);
		pLunes.setLayout(new BorderLayout(0, 0));

		pMartes = new JPanel();
		pMartes.addComponentListener(new PComponentListener("martes"));
		tabbedPane.addTab("Martes", null, pMartes, null);
		pMartes.setLayout(new BorderLayout(0, 0));

		pMiercoles = new JPanel();
		pMiercoles.addComponentListener(new PComponentListener("miercoles"));
		tabbedPane.addTab("Miercoles", null, pMiercoles, null);
		pMiercoles.setLayout(new BorderLayout(0, 0));

		pJueves = new JPanel();
		pJueves.addComponentListener(new PComponentListener("jueves"));
		tabbedPane.addTab("Jueves", null, pJueves, null);
		pJueves.setLayout(new BorderLayout(0, 0));

		pViernes = new JPanel();
		pViernes.addComponentListener(new PComponentListener("viernes"));
		tabbedPane.addTab("Viernes", null, pViernes, null);
		pViernes.setLayout(new BorderLayout(0, 0));

		pSabado = new JPanel();
		pSabado.addComponentListener(new PComponentListener("sabado"));
		tabbedPane.addTab("Sabado", null, pSabado, null);
		pSabado.setLayout(new BorderLayout(0, 0));

		pDomingo = new JPanel();
		pDomingo.addComponentListener(new PComponentListener("domingo"));
		tabbedPane.addTab("Domingo", null, pDomingo, null);
		pDomingo.setLayout(new BorderLayout(0, 0));
	}

	public static HorarioAuxiliar getInstance() {
		if (mInstancia == null) {
			mInstancia = new HorarioAuxiliar();
		}
		return mInstancia;
	}
	
	public void confirmarCerrado() {
		int confirmado = JOptionPane.showOptionDialog(null,
				"¿Seguro que quiere salir, se perderan los cambios no guardados?", "Saliendo del programa",
				JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, new String[] { "Si", "No" },
				"default");

		if (JOptionPane.OK_OPTION == confirmado) {
			dispose();
		}
	}

	public void setIdAuxiliar(String idAuxiliar) {
		this.idAuxiliar = idAuxiliar;
	}

	private class PComponentListener extends ComponentAdapter {

		private String dia = "";

		public PComponentListener(String dia) {
			this.dia = dia;
		}

		@Override
		public void componentShown(ComponentEvent e) {
			PanelTablaHorarioDia panel = PanelTablaHorarioDia.getInstance();

			panel.resetModelTable();
			panel.setDia(dia);
			panel.setIdAuxiliar(idAuxiliar);

			try {
				panel.cargarTabla();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}

			switch (dia) {
			case "lunes":
				pLunes.add(panel);
				break;
			case "martes":
				pMartes.add(panel);
				break;
			case "miercoles":
				pMiercoles.add(panel);
				break;
			case "jueves":
				pJueves.add(panel);
				break;
			case "viernes":
				pViernes.add(panel);
				break;
			case "sabado":
				pSabado.add(panel);
				break;
			default:
				pDomingo.add(panel);
				break;
			}
			panel.repaint();
		}
	}

	private class ThisWindowListener extends WindowAdapter {
		@Override
		public void windowClosed(WindowEvent e) {
			mInstancia = null;
		}

		@Override
		public void windowClosing(WindowEvent e) {
			confirmarCerrado();
		}
	}
}

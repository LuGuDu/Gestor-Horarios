package lugudu.gestorHorarios.presentacion;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;

public final class VentanaPrincipal {

	private static VentanaPrincipal mInstancia;

	private JPanel panelFormularioAuxiliares = FormularioAuxiliares.getInstance();
	private JPanel panelRegistroAuxiliares = RegistroAuxiliares.getInstance();
	private JPanel panelInspeccionarAuxiliar = InspeccionarAuxiliar.getInstance();
	
	private JFrame frmHorariosAuxiliares;
	private JPanel panelPrincipal;
	private JLabel lblReloj;
	private JLabel lblBarraEstado;
	
	public static void main(String[] args) {
		VentanaPrincipal frame = VentanaPrincipal.getInterfaz();
		frame.frmHorariosAuxiliares.setVisible(true);
	}

	private VentanaPrincipal() {

		frmHorariosAuxiliares = new JFrame();
		frmHorariosAuxiliares.addWindowListener(new FrmHorariosAuxiliaresWindowListener());
		frmHorariosAuxiliares.setTitle("Horarios Auxiliares");
		frmHorariosAuxiliares.setBounds(100, 100, 1025, 738);
		frmHorariosAuxiliares.setMinimumSize(new Dimension(1024, 740));
		frmHorariosAuxiliares.setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
		frmHorariosAuxiliares.setLocationRelativeTo(null);

		JPanel panelMenu = new JPanel();
		panelMenu.setBackground(new Color(176, 224, 230));
		frmHorariosAuxiliares.getContentPane().add(panelMenu, BorderLayout.NORTH);
		panelMenu.setLayout(new BorderLayout(0, 0));

		lblReloj = new JLabel("New label");
		panelMenu.add(lblReloj, BorderLayout.EAST);

		setReloj();

		JLabel lblTitulo = new JLabel("Horarios Auxiliares");
		panelMenu.add(lblTitulo, BorderLayout.WEST);

		JPanel panelBarraEstado = new JPanel();
		frmHorariosAuxiliares.getContentPane().add(panelBarraEstado, BorderLayout.SOUTH);
		panelBarraEstado.setLayout(new BorderLayout(0, 0));

		lblBarraEstado = new JLabel("Barra de Estado");
		lblBarraEstado.setHorizontalTextPosition(SwingConstants.CENTER);
		lblBarraEstado.setHorizontalAlignment(SwingConstants.CENTER);
		panelBarraEstado.add(lblBarraEstado, BorderLayout.WEST);

		JScrollPane scrollPane = new JScrollPane();
		frmHorariosAuxiliares.getContentPane().add(scrollPane, BorderLayout.CENTER);

		panelPrincipal = new JPanel();
		panelPrincipal.setAutoscrolls(true);
		scrollPane.setViewportView(panelPrincipal);
		panelPrincipal.setLayout(new CardLayout(0, 0));
		panelPrincipal.setLayout(new CardLayout(0, 0));

		panelPrincipal.add(panelRegistroAuxiliares, "RA");
		panelPrincipal.add(panelFormularioAuxiliares, "FA");
		panelPrincipal.add(panelInspeccionarAuxiliar, "IA");

		JMenuBar menuBar = new JMenuBar();
		frmHorariosAuxiliares.setJMenuBar(menuBar);

		JMenu mnArchivo = new JMenu("Archivo");
		menuBar.add(mnArchivo);

		JMenuItem mntmCerrar = new JMenuItem("Cerrar");
		mntmCerrar.addActionListener(new MntmCerrarActionListener());
		mnArchivo.add(mntmCerrar);

		JMenu mnAyuda = new JMenu("Ayuda");
		menuBar.add(mnAyuda);

		JMenuItem mntmAcercaDe = new JMenuItem("Acerca de...");
		mntmAcercaDe.addActionListener(new MntmAcercaDeActionListener());
		mnAyuda.add(mntmAcercaDe);
	}

	public static VentanaPrincipal getInterfaz() {
		if (mInstancia == null) {
			mInstancia = new VentanaPrincipal();
		}
		return mInstancia;
	}
	
	public void setBarraEstado(String mensaje) {
		lblBarraEstado.setText(mensaje);
	}

	public JFrame getFrmGestorDeCitas() {
		return frmHorariosAuxiliares;
	}

	public void cambiarPanel(String identificador) {
		((CardLayout) panelPrincipal.getLayout()).show(panelPrincipal, identificador);
	}

	private void setReloj() {
		final DateTimeFormatter formateador = DateTimeFormatter.ofPattern("dd/MM/yyyy -- HH:mm:ss   ");
		final boolean CONDICION = true;

		Runnable runnable = new Runnable() {

			public void run() {
				while (CONDICION)
					try {
						Thread.sleep(500);
						lblReloj.setText("  " + formateador.format(LocalDateTime.now()));
					} catch (InterruptedException e) {
						Thread.currentThread().interrupt();
						e.printStackTrace();
					}
			}
		};
		Thread hilo = new Thread(runnable);
		hilo.start();
	}

	private static void confirmarCerradoPrograma() {
		int confirmado = JOptionPane.showOptionDialog(null, "¿Seguro que quiere salir?", "Saliendo del programa",
				JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, new String[] { "Si", "No" },
				"default");

		if (JOptionPane.OK_OPTION == confirmado) {
			System.exit(0);
		}
	}

	private static class FrmHorariosAuxiliaresWindowListener extends WindowAdapter {
		@Override
		public void windowClosing(WindowEvent e) {
			confirmarCerradoPrograma();
		}
	}

	private static class MntmCerrarActionListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			confirmarCerradoPrograma();
		}
	}

	private static class MntmAcercaDeActionListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			AcercaDe a = AcercaDe.getInterfaz();
			a.setVisible(true);
		}
	}
}

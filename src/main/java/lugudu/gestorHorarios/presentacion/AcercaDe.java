package lugudu.gestorHorarios.presentacion;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;

public final class AcercaDe extends JFrame {

	private static final long serialVersionUID = 6470260106542072768L;

	private static AcercaDe mInstancia;

	private JPanel contentPane;
	private JPanel panel;
	private JPanel panelCuerpo;
	private JButton btnOk;

	private AcercaDe() {
		setResizable(false);
		setTitle("Acerca de HorariosAuxiliares");
		setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 450, 350);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);

		panel = new JPanel();
		contentPane.add(panel, BorderLayout.CENTER);
		panel.setLayout(new BorderLayout(0, 0));

		crearElementosPanelTitulo();

		panelCuerpo = new JPanel();
		panel.add(panelCuerpo, BorderLayout.CENTER);
		GridBagLayout gblpanelCuerpo = new GridBagLayout();
		gblpanelCuerpo.columnWidths = new int[] { 0, 0, 0, 0 };
		gblpanelCuerpo.rowHeights = new int[] { 0, 0, 0, 0 };
		gblpanelCuerpo.columnWeights = new double[] { 0.0, 1.0, 0.0, Double.MIN_VALUE };
		gblpanelCuerpo.rowWeights = new double[] { 0.0, 1.0, 0.0, Double.MIN_VALUE };
		panelCuerpo.setLayout(gblpanelCuerpo);

		crearElementosLicencia();

		btnOk = new JButton("Ok");
		btnOk.setFont(new Font("Microsoft JhengHei UI", Font.PLAIN, 11));
		btnOk.addActionListener(new BtnOkActionListener());
		btnOk.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		GridBagConstraints gbcbtnOk = new GridBagConstraints();
		gbcbtnOk.insets = new Insets(0, 0, 0, 5);
		gbcbtnOk.gridx = 1;
		gbcbtnOk.gridy = 2;
		panelCuerpo.add(btnOk, gbcbtnOk);
	}

	private void crearElementosPanelTitulo() {
		JPanel panelTitulo = new JPanel();
		panel.add(panelTitulo, BorderLayout.NORTH);
		GridBagLayout gblpanelTitulo = new GridBagLayout();
		gblpanelTitulo.columnWidths = new int[] { 36, 0, 30, 0, 0 };
		gblpanelTitulo.rowHeights = new int[] { 30, 0, 0, 35, 0 };
		gblpanelTitulo.columnWeights = new double[] { 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE };
		gblpanelTitulo.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE };
		panelTitulo.setLayout(gblpanelTitulo);

		JLabel lblNombre = new JLabel("Horarios Auxiliares (Versión 1.1.0 - 23/08/2021)");
		GridBagConstraints gbclblNombre = new GridBagConstraints();
		gbclblNombre.anchor = GridBagConstraints.WEST;
		gbclblNombre.insets = new Insets(0, 0, 5, 0);
		gbclblNombre.gridx = 3;
		gbclblNombre.gridy = 1;
		panelTitulo.add(lblNombre, gbclblNombre);

		JLabel lblAutor = new JLabel("Acerca del autor");
		lblAutor.addMouseListener(new LblAutorMouseListener());
		lblAutor.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		lblAutor.setForeground(new Color(0, 0, 255));
		GridBagConstraints gbclblAutor = new GridBagConstraints();
		gbclblAutor.anchor = GridBagConstraints.WEST;
		gbclblAutor.insets = new Insets(0, 0, 5, 0);
		gbclblAutor.gridx = 3;
		gbclblAutor.gridy = 2;
		panelTitulo.add(lblAutor, gbclblAutor);
	}

	private void crearElementosLicencia() {
		JPanel panel1 = new JPanel();
		panel1.setBorder(
				new TitledBorder(null, "Licencia del Programa", TitledBorder.CENTER, TitledBorder.TOP, null, null));
		GridBagConstraints gbcpanel1 = new GridBagConstraints();
		gbcpanel1.insets = new Insets(0, 0, 5, 5);
		gbcpanel1.fill = GridBagConstraints.BOTH;
		gbcpanel1.gridx = 1;
		gbcpanel1.gridy = 1;
		panelCuerpo.add(panel1, gbcpanel1);
		GridBagLayout gblpanel1 = new GridBagLayout();
		gblpanel1.columnWidths = new int[] { 182, 0 };
		gblpanel1.rowHeights = new int[] { 22, 0 };
		gblpanel1.columnWeights = new double[] { 1.0, Double.MIN_VALUE };
		gblpanel1.rowWeights = new double[] { 1.0, Double.MIN_VALUE };
		panel1.setLayout(gblpanel1);

		JTextArea txtrEsteProgramaBla = new JTextArea();
		txtrEsteProgramaBla.setLineWrap(true);
		txtrEsteProgramaBla.setWrapStyleWord(true);
		txtrEsteProgramaBla.setTabSize(20);
		txtrEsteProgramaBla.setFont(new Font("Monospaced", Font.PLAIN, 11));
		txtrEsteProgramaBla.setText("Este programa es software libre; puede redistribuirse y/o modificarse bajo los "
				+ "terminos de la licencia GNU General Public License.\r\n\r\nEste software ha sido desarrollado y "
				+ "distribuido con el propósito y el animo de poder ser util, sin ningún tipo de garantia. Cualquier "
				+ "uso indebido del mismo no esta contemplado.\r\n\r\nQueda prohibida su venta. Para más información "
				+ "contacte con el autor.");

		txtrEsteProgramaBla.setBackground(new Color(240, 240, 240));
		txtrEsteProgramaBla.setEditable(false);
		GridBagConstraints gbctxtrEsteProgramaBla = new GridBagConstraints();
		gbctxtrEsteProgramaBla.fill = GridBagConstraints.BOTH;
		gbctxtrEsteProgramaBla.gridx = 0;
		gbctxtrEsteProgramaBla.gridy = 0;
		panel1.add(txtrEsteProgramaBla, gbctxtrEsteProgramaBla);
	}

	public static AcercaDe getInterfaz() {
		if (mInstancia == null) {
			mInstancia = new AcercaDe();
		}
		return mInstancia;
	}

	private class BtnOkActionListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			dispose();
		}
	}

	private static class LblAutorMouseListener extends MouseAdapter {
		@Override
		public void mouseClicked(MouseEvent e) {
			String urlOpen = "https://github.com/lugudu";
			try {
				java.awt.Desktop.getDesktop().browse(java.net.URI.create(urlOpen));
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
	}
}

package lugudu.gestorHorarios.presentacion;

import java.awt.Cursor;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.Calendar;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import lugudu.gestorHorarios.persistencia.ExportarDatos;

public final class SeleccionarFecha extends JFrame {

	private static final long serialVersionUID = -8650577738181474203L;
	private static SeleccionarFecha mInstancia;

	private JPanel contentPane;
	private JLabel lblYear;
	private JTextField txtYear;
	private JLabel lblMes;
	private JComboBox<String> cbMes;
	private JLabel lblTitulo;
	private JButton btnGenerar;

	private SeleccionarFecha() {
		setResizable(false);
		setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 244, 240);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		GridBagLayout gbl_contentPane = new GridBagLayout();
		gbl_contentPane.columnWidths = new int[] { 30, 1, 0, 30, 0 };
		gbl_contentPane.rowHeights = new int[] { 30, 0, 15, 1, 0, 30, 0, 0, 0 };
		gbl_contentPane.columnWeights = new double[] { 1.0, 0.0, 0.0, 1.0, Double.MIN_VALUE };
		gbl_contentPane.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE };
		contentPane.setLayout(gbl_contentPane);

		lblTitulo = new JLabel("Introduzca una fecha");
		lblTitulo.setFont(new Font("Tahoma", Font.BOLD, 11));
		GridBagConstraints gbc_lblTitulo = new GridBagConstraints();
		gbc_lblTitulo.gridwidth = 2;
		gbc_lblTitulo.insets = new Insets(0, 0, 5, 5);
		gbc_lblTitulo.gridx = 1;
		gbc_lblTitulo.gridy = 1;
		contentPane.add(lblTitulo, gbc_lblTitulo);

		lblYear = new JLabel("Año");
		GridBagConstraints gbc_lblYear = new GridBagConstraints();
		gbc_lblYear.fill = GridBagConstraints.BOTH;
		gbc_lblYear.insets = new Insets(0, 0, 5, 5);
		gbc_lblYear.gridx = 1;
		gbc_lblYear.gridy = 3;
		contentPane.add(lblYear, gbc_lblYear);

		txtYear = new JTextField();
		GridBagConstraints gbc_txtYear = new GridBagConstraints();
		gbc_txtYear.fill = GridBagConstraints.BOTH;
		gbc_txtYear.insets = new Insets(0, 0, 5, 5);
		gbc_txtYear.gridx = 2;
		gbc_txtYear.gridy = 3;
		contentPane.add(txtYear, gbc_txtYear);
		txtYear.setColumns(10);

		lblMes = new JLabel("Mes");
		GridBagConstraints gbc_lblMes = new GridBagConstraints();
		gbc_lblMes.fill = GridBagConstraints.BOTH;
		gbc_lblMes.insets = new Insets(0, 0, 5, 5);
		gbc_lblMes.gridx = 1;
		gbc_lblMes.gridy = 4;
		contentPane.add(lblMes, gbc_lblMes);

		cbMes = new JComboBox<>();
		GridBagConstraints gbc_cbMes = new GridBagConstraints();
		gbc_cbMes.insets = new Insets(0, 0, 5, 5);
		gbc_cbMes.gridx = 2;
		gbc_cbMes.gridy = 4;
		contentPane.add(cbMes, gbc_cbMes);
		cbMes.setModel(new DefaultComboBoxModel<>(
				new String[] { "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12" }));

		cbMes.setSelectedIndex(Calendar.getInstance().get(Calendar.MONTH));

		btnGenerar = new JButton("Generar");
		btnGenerar.addActionListener(new BtnGenerarActionListener());
		btnGenerar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		GridBagConstraints gbc_btnGenerar = new GridBagConstraints();
		gbc_btnGenerar.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnGenerar.gridwidth = 2;
		gbc_btnGenerar.insets = new Insets(0, 0, 5, 5);
		gbc_btnGenerar.gridx = 1;
		gbc_btnGenerar.gridy = 6;
		contentPane.add(btnGenerar, gbc_btnGenerar);
	}

	public static SeleccionarFecha getInstance() {
		if (mInstancia == null) {
			mInstancia = new SeleccionarFecha();
		}
		return mInstancia;
	}

	private class BtnGenerarActionListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			for (char c : txtYear.getText().toCharArray()) {
				if (!Character.isDigit(c)) {
					JOptionPane.showMessageDialog(null, "No ha introducido un año valido", "Error", 0);
					return;
				}
			}

			ExportarDatos ex = new ExportarDatos();
			try {
				ex.descargarTotalMes((String) cbMes.getSelectedItem(), txtYear.getText());
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			dispose();
		}
	}
}

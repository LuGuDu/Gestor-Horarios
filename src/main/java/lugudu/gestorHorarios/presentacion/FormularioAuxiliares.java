package lugudu.gestorHorarios.presentacion;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.Date;
import java.util.UUID;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;
import javax.swing.text.MaskFormatter;

import lugudu.gestorHorarios.dominio.Auxiliar;
import lugudu.gestorHorarios.dominio.Constantes;
import lugudu.gestorHorarios.persistencia.AuxiliarDAO;
import net.sourceforge.jdatepicker.impl.JDatePanelImpl;
import net.sourceforge.jdatepicker.impl.JDatePickerImpl;
import net.sourceforge.jdatepicker.impl.UtilDateModel;

public final class FormularioAuxiliares extends JPanel {

	private static final long serialVersionUID = 7367835526284088430L;
	private static FormularioAuxiliares mInstancia;

	private String modo;
	private String idModificar;

	private AuxiliarDAO auxiliarDao = new AuxiliarDAO();

	private JPanel panelDatos;
	private JButton btnCancelar;
	private JButton btnCrearModificar;
	private JTextField txtNombre;
	private JTextField txtApellido1;
	private JTextField txtApellido2;
	private JTextField txtCorreo;
	private JFormattedTextField ftxtDni;
	private JFormattedTextField ftxtTelefono;
	private JDatePickerImpl datePicker;
	private JCheckBox chbActivo;

	private FormularioAuxiliares() {
		addComponentListener(new ThisComponentListener());

		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[] { 30, 0, 15, 0, 30, 0 };
		gridBagLayout.rowHeights = new int[] { 30, 0, 0, 250, 56, 30, 0 };
		gridBagLayout.columnWeights = new double[] { 0.0, 1.0, 0.0, 0.0, 0.0, Double.MIN_VALUE };
		gridBagLayout.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE };
		setLayout(gridBagLayout);

		crearElementoFormulario();

		btnCrearModificar = new JButton("CrearModificar");
		btnCrearModificar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnCrearModificar.addActionListener(new BtnCrearModificarActionListener());
		GridBagConstraints gbc_btnCrearModificar = new GridBagConstraints();
		gbc_btnCrearModificar.anchor = GridBagConstraints.SOUTH;
		gbc_btnCrearModificar.insets = new Insets(0, 0, 5, 5);
		gbc_btnCrearModificar.gridx = 3;
		gbc_btnCrearModificar.gridy = 3;
		add(btnCrearModificar, gbc_btnCrearModificar);

		btnCancelar = new JButton("Cancelar");
		btnCancelar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnCancelar.addActionListener(new BtnCancelarActionListener());
		GridBagConstraints gbc_btnCancelar = new GridBagConstraints();
		gbc_btnCancelar.anchor = GridBagConstraints.SOUTH;
		gbc_btnCancelar.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnCancelar.insets = new Insets(0, 0, 5, 5);
		gbc_btnCancelar.gridx = 3;
		gbc_btnCancelar.gridy = 4;
		add(btnCancelar, gbc_btnCancelar);

	}

	private void crearElementoFormulario() {
		panelDatos = new JPanel();
		panelDatos.setBackground(new Color(255, 228, 196));
		panelDatos.setBorder(
				new TitledBorder(null, "Datos del Auxiliar", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		GridBagConstraints gbc_panelDatos = new GridBagConstraints();
		gbc_panelDatos.gridheight = 4;
		gbc_panelDatos.insets = new Insets(0, 0, 5, 5);
		gbc_panelDatos.fill = GridBagConstraints.BOTH;
		gbc_panelDatos.gridx = 1;
		gbc_panelDatos.gridy = 1;
		add(panelDatos, gbc_panelDatos);
		GridBagLayout gbl_panelDatos = new GridBagLayout();
		gbl_panelDatos.columnWidths = new int[] { 25, 0, 10, 15, 0, 15, 0, 25, 0 };
		gbl_panelDatos.rowHeights = new int[] { 30, 0, 0, 25, 0, 0, 25, 0, 0, 25, 0, 0, 25, 0 };
		gbl_panelDatos.columnWeights = new double[] { 0.0, 1.0, 0.0, 0.0, 1.0, 0.0, 1.0, 0.0, Double.MIN_VALUE };
		gbl_panelDatos.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0,
				Double.MIN_VALUE };
		panelDatos.setLayout(gbl_panelDatos);

		crearElementoDni();
		crearElementoNombreApellidos();
		crearElementoTelefono();
		crearElementoCorreo();
		crearElementoFechaInicioContrato();
		crearElementoActivo();
	}

	private void crearElementoDni() {
		JLabel lblDni = new JLabel("DNI");
		GridBagConstraints gbc_lblDni = new GridBagConstraints();
		gbc_lblDni.anchor = GridBagConstraints.WEST;
		gbc_lblDni.insets = new Insets(0, 0, 5, 5);
		gbc_lblDni.gridx = 1;
		gbc_lblDni.gridy = 1;
		panelDatos.add(lblDni, gbc_lblDni);

		MaskFormatter formatoDni;
		try {
			formatoDni = new MaskFormatter("########U");
			formatoDni.setPlaceholderCharacter('#');
			ftxtDni = new JFormattedTextField(formatoDni);
		} catch (ParseException e) {
			e.printStackTrace();
		}

		GridBagConstraints gbc_ftxtDni = new GridBagConstraints();
		gbc_ftxtDni.insets = new Insets(0, 0, 5, 5);
		gbc_ftxtDni.fill = GridBagConstraints.HORIZONTAL;
		gbc_ftxtDni.gridx = 1;
		gbc_ftxtDni.gridy = 2;
		panelDatos.add(ftxtDni, gbc_ftxtDni);

		JButton btnLimpiarDni = new JButton("");
		btnLimpiarDni.addActionListener(new BtnLimpiarDniActionListener());
		btnLimpiarDni.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnLimpiarDni.setIcon(new ImageIcon(
				FormularioAuxiliares.class.getResource("/lugudu/gestorHorarios/recursos/borrar16.png")));
		GridBagConstraints gbc_btnLimpiarDni = new GridBagConstraints();
		gbc_btnLimpiarDni.anchor = GridBagConstraints.WEST;
		gbc_btnLimpiarDni.insets = new Insets(0, 0, 5, 5);
		gbc_btnLimpiarDni.gridx = 2;
		gbc_btnLimpiarDni.gridy = 2;
		panelDatos.add(btnLimpiarDni, gbc_btnLimpiarDni);
	}

	private void crearElementoNombreApellidos() {
		JLabel lblNombre = new JLabel("Nombre");
		GridBagConstraints gbc_lblNombre = new GridBagConstraints();
		gbc_lblNombre.anchor = GridBagConstraints.WEST;
		gbc_lblNombre.insets = new Insets(0, 0, 5, 5);
		gbc_lblNombre.gridx = 1;
		gbc_lblNombre.gridy = 4;
		panelDatos.add(lblNombre, gbc_lblNombre);

		JLabel lblApellido1 = new JLabel("Primer Apellido");
		GridBagConstraints gbc_lblApellido1 = new GridBagConstraints();
		gbc_lblApellido1.anchor = GridBagConstraints.WEST;
		gbc_lblApellido1.insets = new Insets(0, 0, 5, 5);
		gbc_lblApellido1.gridx = 4;
		gbc_lblApellido1.gridy = 4;
		panelDatos.add(lblApellido1, gbc_lblApellido1);

		JLabel lbApellido2 = new JLabel("Segundo Apellido");
		GridBagConstraints gbc_lbApellido2 = new GridBagConstraints();
		gbc_lbApellido2.anchor = GridBagConstraints.WEST;
		gbc_lbApellido2.insets = new Insets(0, 0, 5, 5);
		gbc_lbApellido2.gridx = 6;
		gbc_lbApellido2.gridy = 4;
		panelDatos.add(lbApellido2, gbc_lbApellido2);
		
		txtNombre = new JTextField();
		GridBagConstraints gbc_txtNombre = new GridBagConstraints();
		gbc_txtNombre.gridwidth = 2;
		gbc_txtNombre.insets = new Insets(0, 0, 5, 5);
		gbc_txtNombre.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtNombre.gridx = 1;
		gbc_txtNombre.gridy = 5;
		panelDatos.add(txtNombre, gbc_txtNombre);
		txtNombre.setColumns(10);

		txtApellido1 = new JTextField();
		GridBagConstraints gbc_txtApellido1 = new GridBagConstraints();
		gbc_txtApellido1.insets = new Insets(0, 0, 5, 5);
		gbc_txtApellido1.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtApellido1.gridx = 4;
		gbc_txtApellido1.gridy = 5;
		panelDatos.add(txtApellido1, gbc_txtApellido1);
		txtApellido1.setColumns(10);

		txtApellido2 = new JTextField();
		GridBagConstraints gbc_txtApellido2 = new GridBagConstraints();
		gbc_txtApellido2.insets = new Insets(0, 0, 5, 5);
		gbc_txtApellido2.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtApellido2.gridx = 6;
		gbc_txtApellido2.gridy = 5;
		panelDatos.add(txtApellido2, gbc_txtApellido2);
		txtApellido2.setColumns(10);
	}

	private void crearElementoTelefono() {
		JLabel lblTelefono = new JLabel("Telefono");
		GridBagConstraints gbc_lblTelefono = new GridBagConstraints();
		gbc_lblTelefono.anchor = GridBagConstraints.WEST;
		gbc_lblTelefono.insets = new Insets(0, 0, 5, 5);
		gbc_lblTelefono.gridx = 1;
		gbc_lblTelefono.gridy = 7;
		panelDatos.add(lblTelefono, gbc_lblTelefono);

		JButton btnLimpiarTelefono = new JButton("");
		btnLimpiarTelefono.addActionListener(new BtnLimpiarTelefonoActionListener());
		btnLimpiarTelefono.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnLimpiarTelefono.setIcon(new ImageIcon(
				FormularioAuxiliares.class.getResource("/lugudu/gestorHorarios/recursos/borrar16.png")));
		GridBagConstraints gbc_btnLimpiarTelefono = new GridBagConstraints();
		gbc_btnLimpiarTelefono.anchor = GridBagConstraints.WEST;
		gbc_btnLimpiarTelefono.insets = new Insets(0, 0, 5, 5);
		gbc_btnLimpiarTelefono.gridx = 2;
		gbc_btnLimpiarTelefono.gridy = 8;
		panelDatos.add(btnLimpiarTelefono, gbc_btnLimpiarTelefono);

		MaskFormatter formatoTelefono;
		try {
			formatoTelefono = new MaskFormatter("#########");
			formatoTelefono.setPlaceholderCharacter('#');
			ftxtTelefono = new JFormattedTextField(formatoTelefono);
		} catch (ParseException e) {
			e.printStackTrace();
		}

		GridBagConstraints gbc_ftxtTelefono = new GridBagConstraints();
		gbc_ftxtTelefono.insets = new Insets(0, 0, 5, 5);
		gbc_ftxtTelefono.fill = GridBagConstraints.HORIZONTAL;
		gbc_ftxtTelefono.gridx = 1;
		gbc_ftxtTelefono.gridy = 8;
		panelDatos.add(ftxtTelefono, gbc_ftxtTelefono);
	}

	private void crearElementoCorreo() {
		JLabel lblCorreo = new JLabel("Correo Electronico");
		GridBagConstraints gbc_lblCorreo = new GridBagConstraints();
		gbc_lblCorreo.anchor = GridBagConstraints.WEST;
		gbc_lblCorreo.insets = new Insets(0, 0, 5, 5);
		gbc_lblCorreo.gridx = 4;
		gbc_lblCorreo.gridy = 7;
		panelDatos.add(lblCorreo, gbc_lblCorreo);
		
		txtCorreo = new JTextField();
		GridBagConstraints gbc_txtCorreo = new GridBagConstraints();
		gbc_txtCorreo.insets = new Insets(0, 0, 5, 5);
		gbc_txtCorreo.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtCorreo.gridx = 4;
		gbc_txtCorreo.gridy = 8;
		panelDatos.add(txtCorreo, gbc_txtCorreo);
		txtCorreo.setColumns(10);
	}

	private void crearElementoFechaInicioContrato() {
		JLabel lblFechaInicioContrato = new JLabel("Fecha Inicio Contrato");
		GridBagConstraints gbc_lblFechaInicioContrato = new GridBagConstraints();
		gbc_lblFechaInicioContrato.anchor = GridBagConstraints.WEST;
		gbc_lblFechaInicioContrato.insets = new Insets(0, 0, 5, 5);
		gbc_lblFechaInicioContrato.gridx = 1;
		gbc_lblFechaInicioContrato.gridy = 10;
		panelDatos.add(lblFechaInicioContrato, gbc_lblFechaInicioContrato);
		
		UtilDateModel model = new UtilDateModel();
		JDatePanelImpl datePanel = new JDatePanelImpl(model);

		datePicker = new JDatePickerImpl(datePanel);
		GridBagConstraints gbcdatePicker = new GridBagConstraints();
		gbcdatePicker.gridwidth = 2;
		gbcdatePicker.fill = GridBagConstraints.HORIZONTAL;
		gbcdatePicker.insets = new Insets(0, 0, 5, 5);
		gbcdatePicker.gridx = 1;
		gbcdatePicker.gridy = 11;
		panelDatos.add(datePicker, gbcdatePicker);
	}

	private void crearElementoActivo() {
		chbActivo = new JCheckBox("Activo");
		chbActivo.setBackground(new Color(255, 228, 196));
		chbActivo.setSelected(true);
		GridBagConstraints gbc_chbActivo = new GridBagConstraints();
		gbc_chbActivo.fill = GridBagConstraints.HORIZONTAL;
		gbc_chbActivo.insets = new Insets(0, 0, 5, 5);
		gbc_chbActivo.gridx = 4;
		gbc_chbActivo.gridy = 11;
		panelDatos.add(chbActivo, gbc_chbActivo);
	}

	public static FormularioAuxiliares getInstance() {
		if (mInstancia == null) {
			mInstancia = new FormularioAuxiliares();
		}
		return mInstancia;
	}

	public void setModo(String modo) {
		this.modo = modo;
	}

	public void setIdModificar(String id) {
		this.idModificar = id;
	}

	private class ThisComponentListener extends ComponentAdapter {
		@Override
		public void componentShown(ComponentEvent e) {
			if ("agregar".equals(modo))
				btnCrearModificar.setText("Agregar");
			else {
				btnCrearModificar.setText("Modificar");
				try {
					rellenarCampos();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
		}

		private void rellenarCampos() throws SQLException {
			ResultSet rs = auxiliarDao.selectById(idModificar);
			String fecha = "";

			while (rs.next()) {
				ftxtDni.setText(rs.getString("dni"));
				txtNombre.setText(rs.getString("nombre"));
				txtApellido1.setText(rs.getString("apellido1"));
				txtApellido2.setText(rs.getString("apellido2"));
				ftxtTelefono.setText(rs.getString("telefono"));
				txtCorreo.setText(rs.getString("correo"));

				chbActivo.setSelected(false);

				if ("1".equals(rs.getString("activo")))
					chbActivo.setSelected(true);

				fecha = rs.getString("fechainiciocontrato");

				int day = Integer.parseInt(fecha.split("-", 0)[2]);
				int month = Integer.parseInt(fecha.split("-", 0)[1]);
				int year = Integer.parseInt(fecha.split("-", 0)[0]);

				datePicker.getModel().setDate(year, month - 1, day);
				datePicker.getModel().setSelected(true);
			}
		}

		@Override
		public void componentHidden(ComponentEvent e) {
			restoreBorders();
			setTexts();
		}

		private void setTexts() {
			ftxtDni.setText("");
			txtNombre.setText("");
			txtApellido1.setText("");
			txtApellido2.setText("");
			ftxtTelefono.setText("");
			datePicker.getModel().setValue(null);
			txtCorreo.setText("");
			chbActivo.setSelected(true);
		}

		private void restoreBorders() {
			ftxtDni.setBorder(Constantes.DEFAULT_BORDER);
			txtNombre.setBorder(Constantes.DEFAULT_BORDER);
			txtApellido1.setBorder(Constantes.DEFAULT_BORDER);
			txtApellido2.setBorder(Constantes.DEFAULT_BORDER);
			ftxtTelefono.setBorder(Constantes.DEFAULT_BORDER);
			datePicker.setBorder(Constantes.DEFAULT_BORDER);
		}
	}

	private class BtnCrearModificarActionListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {

			boolean comprobaciones = comprobarCampos();

			if (!comprobaciones) {
				JOptionPane.showMessageDialog(null, "Error. Revise todos los campos", "Error...", 0);
				return;
			}

			String dni = ftxtDni.getText();
			String nombre = txtNombre.getText();
			String apellido1 = txtApellido1.getText();
			String apellido2 = txtApellido2.getText();
			String telefono = ftxtTelefono.getText();
			Date fechaInicioContrato = (Date) datePicker.getModel().getValue();
			String correo = txtCorreo.getText();
			boolean activo = chbActivo.isSelected();

			Auxiliar auxiliar = new Auxiliar(dni, nombre, apellido1, apellido2, telefono);
			auxiliar.setFechaInicioContrato(fechaInicioContrato);
			auxiliar.setCorreo(correo);
			auxiliar.setActivo(activo);

			if ("modificar".equals(modo))
				auxiliar.setId(UUID.fromString(idModificar));

			try {
				if ("agregar".equals(modo))
					auxiliarDao.insertar(auxiliar);
				else {
					int confirmado = JOptionPane.showOptionDialog(null, "¿Quieres modificar al auxiliar?",
							"Ventana de confirmacion", JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null,
							new String[] { "Si", "No" }, "default");

					if (JOptionPane.OK_OPTION == confirmado)
						auxiliarDao.updateAuxiliar(auxiliar);
				}
			} catch (SQLException e1) {
				e1.printStackTrace();
			}

			VentanaPrincipal vp = VentanaPrincipal.getInterfaz();
			vp.cambiarPanel("RA");
		}

		private boolean comprobarCampos() {
			boolean ok = false;

			boolean dniEmpty = "#########".equals(ftxtDni.getText());
			boolean nombreEmpty = "".equals(txtNombre.getText());
			boolean apellido1Empty = "".equals(txtApellido1.getText());
			boolean apellido2Empty = "".equals(txtApellido2.getText());
			boolean telefonoEmpty = "#########".equals(ftxtTelefono.getText());

			boolean fechaEmpty = false;
			if ((Date) datePicker.getModel().getValue() == null)
				fechaEmpty = true;

			setBorders(dniEmpty, nombreEmpty, apellido1Empty, apellido2Empty, fechaEmpty, telefonoEmpty);

			if (dniEmpty || nombreEmpty || apellido1Empty)
				ok = false;
			else if (apellido2Empty || fechaEmpty || telefonoEmpty)
				ok = false;
			else
				ok = true;

			return ok;
		}

		private void setBorders(boolean dniEmpty, boolean nombreEmpty, boolean apellido1Empty, boolean apellido2Empty,
				boolean fechaEmpty, boolean telefonoEmpty) {

			Border border = BorderFactory.createLineBorder(Color.RED, 2);

			if (dniEmpty)
				ftxtDni.setBorder(border);
			else
				ftxtDni.setBorder(Constantes.DEFAULT_BORDER);

			if (nombreEmpty)
				txtNombre.setBorder(border);
			else
				txtNombre.setBorder(Constantes.DEFAULT_BORDER);

			if (apellido1Empty)
				txtApellido1.setBorder(border);
			else
				txtApellido1.setBorder(Constantes.DEFAULT_BORDER);

			if (apellido2Empty)
				txtApellido2.setBorder(border);
			else
				txtApellido2.setBorder(Constantes.DEFAULT_BORDER);

			if (fechaEmpty)
				datePicker.setBorder(border);
			else
				datePicker.setBorder(Constantes.DEFAULT_BORDER);

			if (telefonoEmpty)
				ftxtTelefono.setBorder(border);
			else
				ftxtTelefono.setBorder(Constantes.DEFAULT_BORDER);
		}
	}

	private static class BtnCancelarActionListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			VentanaPrincipal vp = VentanaPrincipal.getInterfaz();
			vp.cambiarPanel("RA");
		}
	}

	private class BtnLimpiarDniActionListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			ftxtDni.setText("");
		}
	}

	private class BtnLimpiarTelefonoActionListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			ftxtTelefono.setText("");
		}
	}
}

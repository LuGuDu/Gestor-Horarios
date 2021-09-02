package lugudu.gestorHorarios.presentacion;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.YearMonth;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.TimeZone;
import java.util.UUID;

import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;

import lugudu.gestorHorarios.dominio.Incidencia;
import lugudu.gestorHorarios.persistencia.AuxiliarDAO;
import lugudu.gestorHorarios.persistencia.ExportarDatos;
import lugudu.gestorHorarios.persistencia.IncidenciaDAO;

public final class InspeccionarAuxiliar extends JPanel {

	private static final long serialVersionUID = 7037504702880274994L;
	private static InspeccionarAuxiliar mInstancia;

	private String idAuxiliar;

	private IncidenciaDAO incidenciaDao = new IncidenciaDAO();
	private AuxiliarDAO auxiliarDao = new AuxiliarDAO();

	private JPanel panelDatos;
	private JLabel lblSumatorios;
	private JTextField txtNombreCompleto;
	private JTextField txtCorreo;
	private JTextField txtDni;
	private JTextField txtTelefono;
	private JTextField txtFecha;
	private JTextField txtYear;
	private JCheckBox chbActivo;
	private JTable tablaIncidencias;
	private DefaultTableModel model;
	private JButton btnVolver;
	private JComboBox<String> cbMes;

	private InspeccionarAuxiliar() {
		addComponentListener(new ThisComponentListener());

		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[] { 30, 0, 15, 0 };
		gridBagLayout.rowHeights = new int[] { 30, 30, 0, 250, 56, 30, 0 };
		gridBagLayout.columnWeights = new double[] { 0.0, 1.0, 0.0, Double.MIN_VALUE };
		gridBagLayout.rowWeights = new double[] { 0.0, 0.0, 0.0, 1.0, 0.0, 0.0, Double.MIN_VALUE };
		setLayout(gridBagLayout);

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
		gbl_panelDatos.columnWidths = new int[] { 25, 109, 15, 0, 141, 15, 0, 15, 0 };
		gbl_panelDatos.rowHeights = new int[] { 30, 0, 0, 25, 0, 0, 25, 30, 20, 10, 0, 50, 0, 15, 25, 30, 0 };
		gbl_panelDatos.columnWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0, Double.MIN_VALUE };
		gbl_panelDatos.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0, 0.0,
				0.0, 0.0, Double.MIN_VALUE };
		panelDatos.setLayout(gbl_panelDatos);

		crearElementoFormulario();
		crearElementoBotonesHorario();

		btnVolver = new JButton("Volver");
		GridBagConstraints gbc_btnVolver = new GridBagConstraints();
		gbc_btnVolver.anchor = GridBagConstraints.WEST;
		gbc_btnVolver.insets = new Insets(0, 0, 5, 5);
		gbc_btnVolver.gridx = 1;
		gbc_btnVolver.gridy = 14;
		panelDatos.add(btnVolver, gbc_btnVolver);
		btnVolver.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnVolver.addActionListener(new BtnVolverActionListener());

		crearElementoTablaIncidencias();
	}

	private void crearElementoFormulario() {
		crearElementoDniNombre();
		crearElementoCorreoTelefono();
		crearElementoFechaActivo();
	}

	private void crearElementoDniNombre() {
		JLabel lblDni = new JLabel("DNI");
		GridBagConstraints gbc_lblDni = new GridBagConstraints();
		gbc_lblDni.anchor = GridBagConstraints.WEST;
		gbc_lblDni.insets = new Insets(0, 0, 5, 5);
		gbc_lblDni.gridx = 1;
		gbc_lblDni.gridy = 1;
		panelDatos.add(lblDni, gbc_lblDni);

		JLabel lblNombreCompleto = new JLabel("Nombre Completo");
		GridBagConstraints gbc_lblNombreCompleto = new GridBagConstraints();
		gbc_lblNombreCompleto.anchor = GridBagConstraints.WEST;
		gbc_lblNombreCompleto.insets = new Insets(0, 0, 5, 5);
		gbc_lblNombreCompleto.gridx = 3;
		gbc_lblNombreCompleto.gridy = 1;
		panelDatos.add(lblNombreCompleto, gbc_lblNombreCompleto);

		txtDni = new JTextField();
		txtDni.setEditable(false);
		GridBagConstraints gbc_txtDni = new GridBagConstraints();
		gbc_txtDni.insets = new Insets(0, 0, 5, 5);
		gbc_txtDni.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtDni.gridx = 1;
		gbc_txtDni.gridy = 2;
		panelDatos.add(txtDni, gbc_txtDni);
		txtDni.setColumns(10);

		txtNombreCompleto = new JTextField();
		txtNombreCompleto.setEditable(false);
		GridBagConstraints gbc_txtNombreCompleto = new GridBagConstraints();
		gbc_txtNombreCompleto.gridwidth = 2;
		gbc_txtNombreCompleto.insets = new Insets(0, 0, 5, 5);
		gbc_txtNombreCompleto.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtNombreCompleto.gridx = 3;
		gbc_txtNombreCompleto.gridy = 2;
		panelDatos.add(txtNombreCompleto, gbc_txtNombreCompleto);
		txtNombreCompleto.setColumns(10);
	}

	private void crearElementoCorreoTelefono() {
		JLabel lblTelefono = new JLabel("Telefono");
		GridBagConstraints gbc_lblTelefono = new GridBagConstraints();
		gbc_lblTelefono.anchor = GridBagConstraints.WEST;
		gbc_lblTelefono.insets = new Insets(0, 0, 5, 5);
		gbc_lblTelefono.gridx = 1;
		gbc_lblTelefono.gridy = 3;
		panelDatos.add(lblTelefono, gbc_lblTelefono);

		JLabel lblCorreo = new JLabel("Correo Electronico");
		GridBagConstraints gbc_lblCorreo = new GridBagConstraints();
		gbc_lblCorreo.anchor = GridBagConstraints.WEST;
		gbc_lblCorreo.insets = new Insets(0, 0, 5, 5);
		gbc_lblCorreo.gridx = 3;
		gbc_lblCorreo.gridy = 3;
		panelDatos.add(lblCorreo, gbc_lblCorreo);

		txtTelefono = new JTextField();
		txtTelefono.setEditable(false);
		GridBagConstraints gbc_txtTelefono = new GridBagConstraints();
		gbc_txtTelefono.insets = new Insets(0, 0, 5, 5);
		gbc_txtTelefono.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtTelefono.gridx = 1;
		gbc_txtTelefono.gridy = 4;
		panelDatos.add(txtTelefono, gbc_txtTelefono);
		txtTelefono.setColumns(10);

		txtCorreo = new JTextField();
		txtCorreo.setEditable(false);
		GridBagConstraints gbc_txtCorreo = new GridBagConstraints();
		gbc_txtCorreo.gridwidth = 2;
		gbc_txtCorreo.insets = new Insets(0, 0, 5, 5);
		gbc_txtCorreo.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtCorreo.gridx = 3;
		gbc_txtCorreo.gridy = 4;
		panelDatos.add(txtCorreo, gbc_txtCorreo);
		txtCorreo.setColumns(10);
	}

	private void crearElementoFechaActivo() {
		JLabel lblFechaInicioContrato = new JLabel("Fecha Inicio Contrato");
		GridBagConstraints gbc_lblFechaInicioContrato = new GridBagConstraints();
		gbc_lblFechaInicioContrato.anchor = GridBagConstraints.WEST;
		gbc_lblFechaInicioContrato.insets = new Insets(0, 0, 5, 5);
		gbc_lblFechaInicioContrato.gridx = 1;
		gbc_lblFechaInicioContrato.gridy = 5;
		panelDatos.add(lblFechaInicioContrato, gbc_lblFechaInicioContrato);

		txtFecha = new JTextField();
		txtFecha.setEditable(false);
		GridBagConstraints gbc_txtFecha = new GridBagConstraints();
		gbc_txtFecha.insets = new Insets(0, 0, 5, 5);
		gbc_txtFecha.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtFecha.gridx = 1;
		gbc_txtFecha.gridy = 6;
		panelDatos.add(txtFecha, gbc_txtFecha);
		txtFecha.setColumns(10);

		chbActivo = new JCheckBox("Activo");
		chbActivo.setBackground(new Color(255, 228, 196));
		chbActivo.setEnabled(false);
		chbActivo.setSelected(true);
		GridBagConstraints gbc_chbActivo = new GridBagConstraints();
		gbc_chbActivo.fill = GridBagConstraints.HORIZONTAL;
		gbc_chbActivo.insets = new Insets(0, 0, 5, 5);
		gbc_chbActivo.gridx = 3;
		gbc_chbActivo.gridy = 6;
		panelDatos.add(chbActivo, gbc_chbActivo);
	}

	private void crearElementoBotonesHorario() {
		JButton btnVerHorario = new JButton("Ver Horario");
		btnVerHorario.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnVerHorario.addActionListener(new BtnVerHorarioActionListener());
		GridBagConstraints gbc_btnVerHorario = new GridBagConstraints();
		gbc_btnVerHorario.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnVerHorario.gridwidth = 3;
		gbc_btnVerHorario.insets = new Insets(0, 0, 5, 5);
		gbc_btnVerHorario.gridx = 1;
		gbc_btnVerHorario.gridy = 8;
		panelDatos.add(btnVerHorario, gbc_btnVerHorario);

		JButton btnDescargarHorario = new JButton("Descargar Horario");
		btnDescargarHorario.addActionListener(new BtnDescargarHorarioActionListener());
		btnDescargarHorario.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		GridBagConstraints gbc_btnDescargarHorario = new GridBagConstraints();
		gbc_btnDescargarHorario.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnDescargarHorario.gridwidth = 3;
		gbc_btnDescargarHorario.insets = new Insets(0, 0, 5, 5);
		gbc_btnDescargarHorario.gridx = 1;
		gbc_btnDescargarHorario.gridy = 10;
		panelDatos.add(btnDescargarHorario, gbc_btnDescargarHorario);
	}

	private void crearElementoTablaIncidencias() {
		JScrollPane spTablaIncidencias = new JScrollPane();
		GridBagConstraints gbc_spTablaIncidencias = new GridBagConstraints();
		gbc_spTablaIncidencias.gridheight = 10;
		gbc_spTablaIncidencias.insets = new Insets(0, 0, 5, 5);
		gbc_spTablaIncidencias.fill = GridBagConstraints.BOTH;
		gbc_spTablaIncidencias.gridx = 6;
		gbc_spTablaIncidencias.gridy = 2;
		panelDatos.add(spTablaIncidencias, gbc_spTablaIncidencias);

		crearModelTabla();

		tablaIncidencias = new JTable();
		tablaIncidencias.setPreferredScrollableViewportSize(new Dimension(450, 150));
		tablaIncidencias.setFillsViewportHeight(true);
		tablaIncidencias.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		spTablaIncidencias.setViewportView(tablaIncidencias);
		tablaIncidencias.setModel(model);

		setSizeColumnasTablaIncidencias();

		JPanel panelSeleccionFecha = new JPanel();
		panelSeleccionFecha.setBackground(new Color(255, 228, 196));
		GridBagConstraints gbc_panelSeleccionFecha = new GridBagConstraints();
		gbc_panelSeleccionFecha.anchor = GridBagConstraints.EAST;
		gbc_panelSeleccionFecha.insets = new Insets(0, 0, 5, 5);
		gbc_panelSeleccionFecha.gridx = 6;
		gbc_panelSeleccionFecha.gridy = 1;
		panelDatos.add(panelSeleccionFecha, gbc_panelSeleccionFecha);

		JLabel lblYear = new JLabel("Año");
		panelSeleccionFecha.add(lblYear);

		txtYear = new JTextField();
		panelSeleccionFecha.add(txtYear);
		txtYear.setColumns(10);

		JLabel lblNewLabel = new JLabel("Mes");
		panelSeleccionFecha.add(lblNewLabel);

		cbMes = new JComboBox<>();
		panelSeleccionFecha.add(cbMes);
		cbMes.addItemListener(new CbMesItemListener());
		cbMes.setModel(new DefaultComboBoxModel<>(
				new String[] { "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12" }));

		cbMes.setSelectedIndex(Calendar.getInstance().get(Calendar.MONTH));

		JButton btnCargarTabla = new JButton("Cargar Tabla");
		panelSeleccionFecha.add(btnCargarTabla);
		btnCargarTabla.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnCargarTabla.addActionListener(new BtnCargarTablaActionListener());

		lblSumatorios = new JLabel("New label");
		GridBagConstraints gbc_lblSumatorios = new GridBagConstraints();
		gbc_lblSumatorios.anchor = GridBagConstraints.WEST;
		gbc_lblSumatorios.insets = new Insets(0, 0, 5, 5);
		gbc_lblSumatorios.gridx = 6;
		gbc_lblSumatorios.gridy = 12;
		panelDatos.add(lblSumatorios, gbc_lblSumatorios);

		JButton btnGuardarCambios = new JButton("Guardar Cambios");
		btnGuardarCambios.setIcon(
				new ImageIcon(InspeccionarAuxiliar.class.getResource("/lugudu/gestorHorarios/recursos/guardar.png")));
		btnGuardarCambios.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnGuardarCambios.addActionListener(new BtnGuardarCambiosActionListener());
		GridBagConstraints gbc_btnGuardarCambios = new GridBagConstraints();
		gbc_btnGuardarCambios.anchor = GridBagConstraints.EAST;
		gbc_btnGuardarCambios.insets = new Insets(0, 0, 5, 5);
		gbc_btnGuardarCambios.gridx = 6;
		gbc_btnGuardarCambios.gridy = 14;
		panelDatos.add(btnGuardarCambios, gbc_btnGuardarCambios);
	}

	private void setSizeColumnasTablaIncidencias() {
		tablaIncidencias.getColumnModel().getColumn(0).setMaxWidth(30);
		tablaIncidencias.getColumnModel().getColumn(0).setMinWidth(30);

		tablaIncidencias.getColumnModel().getColumn(1).setMaxWidth(20);
		tablaIncidencias.getColumnModel().getColumn(1).setMinWidth(20);

		tablaIncidencias.getColumnModel().getColumn(6).setMaxWidth(0);
		tablaIncidencias.getColumnModel().getColumn(6).setMinWidth(0);
		tablaIncidencias.getColumnModel().getColumn(6).setMaxWidth(0);
		tablaIncidencias.getColumnModel().getColumn(6).setMinWidth(0);

		tablaIncidencias.getColumnModel().getColumn(7).setMaxWidth(0);
		tablaIncidencias.getColumnModel().getColumn(7).setMinWidth(0);
		tablaIncidencias.getColumnModel().getColumn(7).setMaxWidth(0);
		tablaIncidencias.getColumnModel().getColumn(7).setMinWidth(0);

		tablaIncidencias.getColumnModel().getColumn(8).setMaxWidth(0);
		tablaIncidencias.getColumnModel().getColumn(8).setMinWidth(0);
		tablaIncidencias.getColumnModel().getColumn(8).setMaxWidth(0);
		tablaIncidencias.getColumnModel().getColumn(8).setMinWidth(0);

		tablaIncidencias.getColumnModel().getColumn(9).setMaxWidth(0);
		tablaIncidencias.getColumnModel().getColumn(9).setMinWidth(0);
		tablaIncidencias.getColumnModel().getColumn(9).setMaxWidth(0);
		tablaIncidencias.getColumnModel().getColumn(9).setMinWidth(0);
	}

	public static InspeccionarAuxiliar getInstance() {
		if (mInstancia == null) {
			mInstancia = new InspeccionarAuxiliar();
		}
		return mInstancia;
	}

	public void setIdModificar(String id) {
		this.idAuxiliar = id;
	}

	private void crearModelTabla() {
		model = new DefaultTableModel(new Object[][] {}, new String[] { "Dia", "L", "H. Normales", "H. Festivas",
				"H. Fuera Convenio", "Kilometraje", "idAuxiliar", "id", "mes", "year" }) {

			private static final long serialVersionUID = 6078883996706723703L;
		};
	}

	private void cargarTabla() throws SQLException {
		resetModelTable();

		rellenarDias();
		ResultSet rs = incidenciaDao.selectByIdMesYear(idAuxiliar, (String) cbMes.getSelectedItem(), txtYear.getText());
		rellenarDatosDias(rs);
		calcularSumatorios();
	}

	private void calcularSumatorios() {
		double contadorHorasNormales = 0;
		double contadorHorasFestivas = 0;
		double contadorKilometraje = 0;
		double contadorHorasFueraConvenio = 0;

		for (int i = 0; i < tablaIncidencias.getRowCount(); i++) {
			try {
				contadorHorasNormales = contadorHorasNormales
						+ Double.parseDouble(tablaIncidencias.getModel().getValueAt(i, 2).toString());
				contadorHorasFestivas = contadorHorasFestivas
						+ Double.parseDouble(tablaIncidencias.getModel().getValueAt(i, 3).toString());
				contadorHorasFueraConvenio = contadorHorasFueraConvenio
						+ Double.parseDouble(tablaIncidencias.getModel().getValueAt(i, 4).toString());
				contadorKilometraje = contadorKilometraje
						+ Double.parseDouble(tablaIncidencias.getModel().getValueAt(i, 5).toString());

			} catch (NumberFormatException e2) {
				continue;
			}
		}

		lblSumatorios.setText("Horas Normales: " + contadorHorasNormales + " | Horas Festivas: " + contadorHorasFestivas
				+ " | Horas Fuera Convenio: " + contadorHorasFueraConvenio + " | Kilometraje: " + contadorKilometraje);
	}

	private void rellenarDatosDias(ResultSet rs) throws SQLException {
		while (rs.next()) {

			String fecha = rs.getString("fecha");
			String[] parts = fecha.split("-");
			int dia = Integer.parseInt(parts[2]);

			String id = rs.getString("id");
			String idAux = rs.getString("idauxiliar");
			double horasNormales = rs.getDouble("horasNormales");
			double horasFestivas = rs.getDouble("horasFestivas");
			double horasFueraConvenio = rs.getDouble("horasFueraConvenio");
			double kilometraje = rs.getDouble("kilometraje");

			tablaIncidencias.getModel().setValueAt(horasNormales, dia - 1, 2);
			tablaIncidencias.getModel().setValueAt(horasFestivas, dia - 1, 3);
			tablaIncidencias.getModel().setValueAt(horasFueraConvenio, dia - 1, 4);

			tablaIncidencias.getModel().setValueAt(kilometraje, dia - 1, 5);
			tablaIncidencias.getModel().setValueAt(idAux, dia - 1, 6);
			tablaIncidencias.getModel().setValueAt(id, dia - 1, 7);
		}
	}

	private void resetModelTable() {
		model.setRowCount(0);
		tablaIncidencias.setModel(model);
	}

	private void rellenarDias() {

		int year = Integer.parseInt(txtYear.getText());
		int month = Integer.parseInt((String) cbMes.getSelectedItem());

		YearMonth yearMonthObject = YearMonth.of(year, month);
		int daysInMonth = yearMonthObject.lengthOfMonth();

		for (int i = 1; i <= daysInMonth; i++) {
			String letraDia = diaSemana(i, month, year);
			model.addRow(new Object[] { i, letraDia, "", "", "", "", "", "", month, year });
		}
		tablaIncidencias.setModel(model);
	}

	private static String diaSemana(int dia, int mes, int ano) {
		String letraD = "";

		Calendar calendar = new GregorianCalendar(TimeZone.getDefault());
		calendar.set(ano, mes - 1, dia);
		int nD = calendar.get(Calendar.DAY_OF_WEEK);

		switch (nD) {
		case 1:
			letraD = "D";
			break;
		case 2:
			letraD = "L";
			break;
		case 3:
			letraD = "M";
			break;
		case 4:
			letraD = "X";
			break;
		case 5:
			letraD = "J";
			break;
		case 6:
			letraD = "V";
			break;
		default:
			letraD = "S";
			break;
		}
		return letraD;
	}

	private class ThisComponentListener extends ComponentAdapter {
		@Override
		public void componentShown(ComponentEvent e) {
			try {
				rellenarCampos();
				txtYear.setText(String.valueOf(Calendar.getInstance().get(Calendar.YEAR)));
				cbMes.setSelectedIndex(Calendar.getInstance().get(Calendar.MONTH));
				cargarTabla();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}

		private void rellenarCampos() throws SQLException {
			ResultSet rs = auxiliarDao.selectById(idAuxiliar);

			while (rs.next()) {
				txtDni.setText(rs.getString("dni"));
				txtNombreCompleto.setText(
						rs.getString("nombre") + " " + rs.getString("apellido1") + " " + rs.getString("apellido2"));
				txtTelefono.setText(rs.getString("telefono"));
				txtCorreo.setText(rs.getString("correo"));

				chbActivo.setSelected(false);

				if ("1".equals(rs.getString("activo")))
					chbActivo.setSelected(true);

				txtFecha.setText(rs.getString("fechainiciocontrato"));
			}
		}
	}

	private class BtnVolverActionListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			VentanaPrincipal vp = VentanaPrincipal.getInterfaz();
			vp.cambiarPanel("RA");
			resetModelTable();
		}
	}

	private class BtnCargarTablaActionListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			for (char c : txtYear.getText().toCharArray()) {
				if (!Character.isDigit(c)) {
					JOptionPane.showMessageDialog(null, "No ha introducido un año valido", "Error", 0);
					return;
				}
			}

			try {
				cargarTabla();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}
	}

	private class BtnGuardarCambiosActionListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {

			int confirmado = JOptionPane.showOptionDialog(null, "¿Quieres guardar los cambios?",
					"Ventana de confirmacion", JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null,
					new String[] { "Si", "No" }, "default");

			if (JOptionPane.OK_OPTION == confirmado) {
				for (int i = 0; i < tablaIncidencias.getRowCount(); i++) {
					String id = tablaIncidencias.getModel().getValueAt(i, 7).toString();
					System.out.println(id);
					if (!"".equals(id)) {
						actualizarIncidencia(i, id);
					} else {
						insertarIncidencia(i);
					}
				}
				try {
					cargarTabla();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
		}

		private void insertarIncidencia(int i) {
			String dia = String.valueOf(i + 1);
			String mes = tablaIncidencias.getModel().getValueAt(i, 8).toString();
			String year = tablaIncidencias.getModel().getValueAt(i, 9).toString();

			Incidencia incidencia = new Incidencia(dia, mes, year);
			incidencia.setIdAuxiliar(UUID.fromString(idAuxiliar));

			try {
				double horasNormales = Double.parseDouble(tablaIncidencias.getModel().getValueAt(i, 2).toString());
				double horasFestivas = Double.parseDouble(tablaIncidencias.getModel().getValueAt(i, 3).toString());
				double horasFueraConvenio = Double.parseDouble(tablaIncidencias.getModel().getValueAt(i, 4).toString());
				double kilometraje = Double.parseDouble(tablaIncidencias.getModel().getValueAt(i, 5).toString());

				incidencia.setHorasNormales(horasNormales);
				incidencia.setHorasFestivas(horasFestivas);
				incidencia.setHorasFueraConvenio(horasFueraConvenio);
				incidencia.setKilometraje(kilometraje);

			} catch (NumberFormatException e2) {
				return;
			}

			try {
				incidenciaDao.insertar(incidencia);
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}

		private void actualizarIncidencia(int i, String id) {
			String dia = String.valueOf(i + 1);
			String mes = tablaIncidencias.getModel().getValueAt(i, 8).toString();
			String year = tablaIncidencias.getModel().getValueAt(i, 9).toString();

			Incidencia incidencia = new Incidencia(dia, mes, year);
			incidencia.setId(UUID.fromString(id));
			incidencia.setIdAuxiliar(UUID.fromString(idAuxiliar));

			try {
				double horasNormales = Double.parseDouble(tablaIncidencias.getModel().getValueAt(i, 2).toString());
				double horasFestivas = Double.parseDouble(tablaIncidencias.getModel().getValueAt(i, 3).toString());
				double horasFueraConvenio = Double.parseDouble(tablaIncidencias.getModel().getValueAt(i, 4).toString());
				double kilometraje = Double.parseDouble(tablaIncidencias.getModel().getValueAt(i, 5).toString());

				incidencia.setHorasNormales(horasNormales);
				incidencia.setHorasFestivas(horasFestivas);
				incidencia.setHorasFueraConvenio(horasFueraConvenio);
				incidencia.setKilometraje(kilometraje);
				

			} catch (NumberFormatException e1) {
				JOptionPane.showMessageDialog(null, "Al actualizar un registro ha habido un error, "
						+ "ha introducido datos invalidos en la fila: " + (i + 1), "Error", 0);
				return;
			}

			try {
				incidenciaDao.updateIncidencia(incidencia);
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}
	}

	private class CbMesItemListener implements ItemListener {
		public void itemStateChanged(ItemEvent e) {
			for (char c : txtYear.getText().toCharArray()) {
				if (!Character.isDigit(c)) {
					JOptionPane.showMessageDialog(null, "No ha introducido un año valido", "Error", 0);
					return;
				}
			}

			if (!"".equals(txtYear.getText())) {
				try {
					cargarTabla();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			} else
				resetModelTable();
		}
	}

	private class BtnVerHorarioActionListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			HorarioAuxiliar h = HorarioAuxiliar.getInstance();
			h.setIdAuxiliar(idAuxiliar);
			h.setVisible(true);
		}
	}

	private class BtnDescargarHorarioActionListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			ExportarDatos ex = new ExportarDatos();
			try {
				ex.descargarHorario(idAuxiliar);
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
	}
}

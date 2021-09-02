package lugudu.gestorHorarios.presentacion;

import java.awt.BorderLayout;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.RowSorter;
import javax.swing.SortOrder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

import lugudu.gestorHorarios.dominio.RegistroHorario;
import lugudu.gestorHorarios.persistencia.RegistroHorarioDAO;

public final class PanelTablaHorarioDia extends JPanel {

	private static final long serialVersionUID = 8242925425332675567L;
	private static PanelTablaHorarioDia mInstancia;

	private RegistroHorarioDAO registroHorarioDao = new RegistroHorarioDAO();

	private String idAuxiliar;
	private String dia;

	private JScrollPane scrollPane;
	private JTable table;
	private DefaultTableModel model;
	private JPanel panelBotones;
	private JButton btnAgregar;
	private JButton btnBorrar;
	private JButton btnGuardarCambios;
	private JPanel panel;
	private JLabel lblSumatorio;
	private JButton btnCerrar;

	private PanelTablaHorarioDia() {
		setLayout(new BorderLayout(0, 0));

		crearModelTabla();

		panel = new JPanel();
		add(panel, BorderLayout.CENTER);
		panel.setLayout(new BorderLayout(0, 0));

		scrollPane = new JScrollPane();
		panel.add(scrollPane, BorderLayout.CENTER);

		table = new JTable();
		scrollPane.setViewportView(table);
		table.setModel(model);

		table.getColumnModel().getColumn(6).setMaxWidth(0);
		table.getColumnModel().getColumn(6).setMinWidth(0);
		table.getColumnModel().getColumn(6).setMaxWidth(0);
		table.getColumnModel().getColumn(6).setMinWidth(0);

		table.getColumnModel().getColumn(7).setMaxWidth(0);
		table.getColumnModel().getColumn(7).setMinWidth(0);
		table.getColumnModel().getColumn(7).setMaxWidth(0);
		table.getColumnModel().getColumn(7).setMinWidth(0);

		table.getColumnModel().getColumn(8).setMaxWidth(0);
		table.getColumnModel().getColumn(8).setMinWidth(0);
		table.getColumnModel().getColumn(8).setMaxWidth(0);
		table.getColumnModel().getColumn(8).setMinWidth(0);

		panelBotones = new JPanel();
		panel.add(panelBotones, BorderLayout.SOUTH);

		btnAgregar = new JButton("Agregar");
		btnAgregar.setIcon(
				new ImageIcon(PanelTablaHorarioDia.class.getResource("/lugudu/gestorHorarios/recursos/agregar.png")));
		btnAgregar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnAgregar.addActionListener(new BtnAgregarActionListener());
		panelBotones.add(btnAgregar);

		btnBorrar = new JButton("Borrar");
		btnBorrar.setIcon(
				new ImageIcon(PanelTablaHorarioDia.class.getResource("/lugudu/gestorHorarios/recursos/borrar.png")));
		btnBorrar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnBorrar.addActionListener(new BtnBorrarActionListener());
		panelBotones.add(btnBorrar);

		btnGuardarCambios = new JButton("Guardar Cambios");
		btnGuardarCambios.setIcon(new ImageIcon(
				PanelTablaHorarioDia.class.getResource("/lugudu/gestorHorarios/recursos/guardar24.png")));
		btnGuardarCambios.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnGuardarCambios.addActionListener(new BtnGuardarCambiosActionListener());
		panelBotones.add(btnGuardarCambios);

		btnCerrar = new JButton("Cerrar");
		btnCerrar.setPreferredSize(new Dimension(100, 33));
		btnCerrar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnCerrar.addActionListener(new BtnCerrarActionListener());
		panelBotones.add(btnCerrar);

		lblSumatorio = new JLabel("New label");
		add(lblSumatorio, BorderLayout.SOUTH);
	}

	public static PanelTablaHorarioDia getInstance() {
		if (mInstancia == null) {
			mInstancia = new PanelTablaHorarioDia();
		}
		return mInstancia;
	}

	private void crearModelTabla() {
		model = new DefaultTableModel(new Object[][] {}, new String[] { "Desde", "Hasta", "Nombre Usuario", "Direccion",
				"Telefono", "Dependiente", "Dia", "IdRegistro", "IdAuxiliar" }) {

			private static final long serialVersionUID = 6078883996706723703L;

		};
	}

	public void cargarTabla() throws SQLException {

		TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(model);
		table.setRowSorter(sorter);

		List<RowSorter.SortKey> sortKeys = new ArrayList<>(25);
		sortKeys.add(new RowSorter.SortKey(0, SortOrder.ASCENDING));

		sorter.setSortKeys(sortKeys);

		resetModelTable();

		ResultSet rs = registroHorarioDao.selectByDia(idAuxiliar, dia);
		rellenarTabla(rs);
		calcularHorasTotales();

	}

	public void resetModelTable() {
		model.setRowCount(0);
		table.setModel(model);
	}

	private void rellenarTabla(ResultSet rs) throws SQLException {
		while (rs.next()) {
			String id = rs.getString("id");
			String desde = rs.getString("desde");
			String hasta = rs.getString("hasta");
			String nombre = rs.getString("nombre");
			String direccion = rs.getString("direccion");
			String telefono = rs.getString("telefono");
			String dependiente = rs.getString("dependiente");

			model.addRow(new Object[] { desde, hasta, nombre, direccion, telefono, dependiente, dia, id, idAuxiliar });
		}
		table.setModel(model);
	}

	public void setDia(String dia) {
		this.dia = dia;
	}

	public void setIdAuxiliar(String idAuxiliar) {
		this.idAuxiliar = idAuxiliar;
	}

	private void calcularHorasTotales() {
		double horasLunes = 0.0;
		double horasMartes = 0.0;
		double horasMiercoles = 0.0;
		double horasJueves = 0.0;
		double horasViernes = 0.0;
		double horasSabado = 0.0;
		double horasDomingo = 0.0;
		double horasSemana;

		try {
			horasLunes = registroHorarioDao.calcularHoras(idAuxiliar, "lunes");
			horasMartes = registroHorarioDao.calcularHoras(idAuxiliar, "martes");
			horasMiercoles = registroHorarioDao.calcularHoras(idAuxiliar, "miercoles");
			horasJueves = registroHorarioDao.calcularHoras(idAuxiliar, "jueves");
			horasViernes = registroHorarioDao.calcularHoras(idAuxiliar, "viernes");
			horasSabado = registroHorarioDao.calcularHoras(idAuxiliar, "sabado");
			horasDomingo = registroHorarioDao.calcularHoras(idAuxiliar, "domingo");
		} catch (SQLException | ParseException e) {
			e.printStackTrace();
		}

		horasSemana = horasLunes + horasMartes + horasMiercoles + horasJueves + horasViernes + horasSabado
				+ horasDomingo;

		lblSumatorio.setText("Horas a la semana: " + truncate(horasSemana, 2) + "h, lunes: " + truncate(horasLunes, 2)
				+ "h, martes: " + truncate(horasMartes, 2) + "h, " + "miercoles: " + truncate(horasMiercoles, 2)
				+ "h, jueves: " + truncate(horasJueves, 2) + "h, viernes: " + truncate(horasViernes, 2) + "h, sabado: "
				+ truncate(horasSabado, 2) + "h, domingo: " + truncate(horasDomingo, 2) + "h");
	}

	private static double truncate(double numero, int nDecimales) {
		return Math.round(numero * Math.pow(10, nDecimales)) / Math.pow(10, nDecimales);
	}

	private class BtnAgregarActionListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			RegistroHorario r = new RegistroHorario();
			model.addRow(new Object[] { "", "", "", "", "", "", dia, r.getIdRegistro().toString(), idAuxiliar });
		}
	}

	private class BtnGuardarCambiosActionListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {

			int confirmado = JOptionPane.showOptionDialog(null, "¿Quieres guardar los cambios?",
					"Ventana de confirmacion", JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null,
					new String[] { "Si", "No" }, "default");

			if (JOptionPane.OK_OPTION == confirmado) {
				for (int i = 0; i < table.getRowCount(); i++) {
					String id = table.getModel().getValueAt(i, 7).toString();

					boolean existe = false;
					try {
						existe = registroHorarioDao.buscarPorId(id);

						RegistroHorario r = crearRegistro(i);
						if (r == null) {
							continue;
						}

						if (existe) {
							registroHorarioDao.update(r);
						} else {
							registroHorarioDao.insertar(r);
						}

					} catch (SQLException e1) {
						e1.printStackTrace();
					}
				}

				TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(model);
				table.setRowSorter(sorter);

				List<RowSorter.SortKey> sortKeys = new ArrayList<>(25);
				sortKeys.add(new RowSorter.SortKey(0, SortOrder.ASCENDING));

				sorter.setSortKeys(sortKeys);
				calcularHorasTotales();

			}
		}

		private RegistroHorario crearRegistro(int i) {
			SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm");

			String desde = table.getModel().getValueAt(i, 0).toString();
			String hasta = table.getModel().getValueAt(i, 1).toString();

			try {
				dateFormat.parse(desde);
				dateFormat.parse(hasta);
			} catch (ParseException e) {
				JOptionPane.showMessageDialog(null, "Error al introducir una hora (hh:mm) en la fila: " + (i + 1),
						"Error", 0);
				return null;
			}

			String nombre = table.getModel().getValueAt(i, 2).toString();
			String direccion = table.getModel().getValueAt(i, 3).toString();
			String telefono = table.getModel().getValueAt(i, 4).toString();
			String dependiente = table.getModel().getValueAt(i, 5).toString();
			String idRegistro = table.getModel().getValueAt(i, 7).toString();

			RegistroHorario r = new RegistroHorario();

			r.setIdRegistro(UUID.fromString(idRegistro));
			r.setIdAuxiliar(UUID.fromString(idAuxiliar));
			r.setHoraInicio(desde);
			r.setHoraFinal(hasta);
			r.setNombreUsuario(nombre);
			r.setDireccion(direccion);
			r.setTelefono(telefono);
			r.setDependiente(dependiente);
			r.setDia(dia);

			return r;
		}
	}

	private class BtnBorrarActionListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			if (!table.getSelectionModel().isSelectionEmpty()) {
				int row = table.convertRowIndexToModel(table.getSelectedRow());
				String id = table.getModel().getValueAt(row, 7).toString();

				int confirmado = JOptionPane.showOptionDialog(null, "¿Quieres borrar el registro?",
						"Ventana de confirmacion", JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null,
						new String[] { "Si", "No" }, "default");

				if (JOptionPane.OK_OPTION == confirmado) {
					try {
						registroHorarioDao.deleteById(id);
						cargarTabla();
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
				}
			} else
				JOptionPane.showMessageDialog(null, "No ha seleccionado un registro", "Error", 0);
		}
	}

	private static class BtnCerrarActionListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			HorarioAuxiliar h = HorarioAuxiliar.getInstance();
			h.confirmarCerrado();
		}
	}
}

package lugudu.gestorHorarios.presentacion;

import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.RowFilter;
import javax.swing.RowSorter;
import javax.swing.SortOrder;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

import lugudu.gestorHorarios.persistencia.AuxiliarDAO;

public final class RegistroAuxiliares extends JPanel {

	private static final long serialVersionUID = -8513494805526438555L;
	private static RegistroAuxiliares mInstancia;

	private AuxiliarDAO auxiliarDao = new AuxiliarDAO();

	private JTable tablaAuxiliares;
	private JTextField txtBuscarPorDni;
	private JTextField txtBuscarPorNombre;
	private JTextField txtBuscarPorApellido1;
	private JTextField txtBuscarPorApellido2;
	private JLabel lblContador;
	private DefaultTableModel model;

	private RegistroAuxiliares() {
		addComponentListener(new ThisComponentListener());
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[] { 30, 0, 0, 30 };
		gridBagLayout.rowHeights = new int[] { 30, 0, 30, 0, 30, 350, 0, 30, 0, 30 };
		gridBagLayout.columnWeights = new double[] { 0.0, 1.0, 0.0, 0.0 };
		gridBagLayout.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0, 0.0, Double.MIN_VALUE, 0.0 };
		setLayout(gridBagLayout);

		JLabel lblTitulo = new JLabel("Registro de Auxiliares");
		lblTitulo.setFont(new Font("Tahoma", Font.BOLD, 16));
		GridBagConstraints gbc_lblTitulo = new GridBagConstraints();
		gbc_lblTitulo.anchor = GridBagConstraints.WEST;
		gbc_lblTitulo.insets = new Insets(0, 0, 5, 5);
		gbc_lblTitulo.gridx = 1;
		gbc_lblTitulo.gridy = 1;
		add(lblTitulo, gbc_lblTitulo);

		crearElementoFiltrosTabla();
		crearElementoTabla();
		crearElementoBotones();
	}

	private void crearElementoTabla() {
		JScrollPane spTablaAuxiliares = new JScrollPane();
		GridBagConstraints gbc_spTablaAuxiliares = new GridBagConstraints();
		gbc_spTablaAuxiliares.gridwidth = 2;
		gbc_spTablaAuxiliares.insets = new Insets(0, 0, 5, 5);
		gbc_spTablaAuxiliares.fill = GridBagConstraints.BOTH;
		gbc_spTablaAuxiliares.gridx = 1;
		gbc_spTablaAuxiliares.gridy = 5;
		add(spTablaAuxiliares, gbc_spTablaAuxiliares);

		tablaAuxiliares = new JTable();
		tablaAuxiliares.setPreferredScrollableViewportSize(new Dimension(450, 200));
		tablaAuxiliares.setFillsViewportHeight(true);
		tablaAuxiliares.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		spTablaAuxiliares.setViewportView(tablaAuxiliares);

		crearModelTabla();
		tablaAuxiliares.setModel(model);

		tablaAuxiliares.getColumnModel().getColumn(4).setMaxWidth(0);
		tablaAuxiliares.getColumnModel().getColumn(4).setMinWidth(0);
		tablaAuxiliares.getColumnModel().getColumn(4).setMaxWidth(0);
		tablaAuxiliares.getColumnModel().getColumn(4).setMinWidth(0);

		lblContador = new JLabel("elementos encontrados");
		GridBagConstraints gbc_lblContador = new GridBagConstraints();
		gbc_lblContador.anchor = GridBagConstraints.WEST;
		gbc_lblContador.insets = new Insets(0, 0, 5, 5);
		gbc_lblContador.gridx = 1;
		gbc_lblContador.gridy = 6;
		add(lblContador, gbc_lblContador);

		try {
			cargarTabla();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		lblContador.setText(tablaAuxiliares.getRowCount() + " elementos encontrados");
	}

	private void crearElementoBotones() {
		JPanel panelBotones = new JPanel();
		GridBagConstraints gbcpanelBotones = new GridBagConstraints();
		gbcpanelBotones.gridwidth = 2;
		gbcpanelBotones.insets = new Insets(0, 0, 5, 5);
		gbcpanelBotones.fill = GridBagConstraints.BOTH;
		gbcpanelBotones.gridx = 1;
		gbcpanelBotones.gridy = 8;
		add(panelBotones, gbcpanelBotones);

		JButton btnAgregar = new JButton("Agregar");
		btnAgregar.setIcon(
				new ImageIcon(RegistroAuxiliares.class.getResource("/lugudu/gestorHorarios/recursos/agregar.png")));
		btnAgregar.addActionListener(new BtnAgregarActionListener());
		btnAgregar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		panelBotones.add(btnAgregar);

		JButton btnModificar = new JButton("Modificar");
		btnModificar.setIcon(
				new ImageIcon(RegistroAuxiliares.class.getResource("/lugudu/gestorHorarios/recursos/editar.png")));
		btnModificar.addActionListener(new BtnModificarActionListener());
		btnModificar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		panelBotones.add(btnModificar);

		JButton btnBorrar = new JButton("Borrar");
		btnBorrar.setIcon(
				new ImageIcon(RegistroAuxiliares.class.getResource("/lugudu/gestorHorarios/recursos/borrar.png")));
		btnBorrar.addActionListener(new BtnBorrarActionListener());
		btnBorrar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		panelBotones.add(btnBorrar);

		JButton btnInspeccionar = new JButton("Inspeccionar");
		btnInspeccionar.setIcon(new ImageIcon(
				RegistroAuxiliares.class.getResource("/lugudu/gestorHorarios/recursos/Inspeccionar.png")));
		btnInspeccionar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnInspeccionar.addActionListener(new BtnInspeccionarActionListener());
		panelBotones.add(btnInspeccionar);

		JButton btnDescargarTotal = new JButton("Descargar Total_Mes");
		btnDescargarTotal.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnDescargarTotal.setIcon(
				new ImageIcon(RegistroAuxiliares.class.getResource("/lugudu/gestorHorarios/recursos/download.png")));
		btnDescargarTotal.addActionListener(new BtnDescargarTotalActionListener());
		panelBotones.add(btnDescargarTotal);
	}

	private void crearElementoFiltrosTabla() {
		JPanel panel = new JPanel();
		GridBagConstraints gbc_panel = new GridBagConstraints();
		gbc_panel.insets = new Insets(0, 0, 5, 5);
		gbc_panel.anchor = GridBagConstraints.WEST;
		gbc_panel.fill = GridBagConstraints.VERTICAL;
		gbc_panel.gridx = 1;
		gbc_panel.gridy = 3;
		add(panel, gbc_panel);

		txtBuscarPorDni = new JTextField();
		txtBuscarPorDni.addMouseListener(new TxtBuscarPorDniMouseListener());
		txtBuscarPorDni.setPreferredSize(new Dimension(7, 30));
		txtBuscarPorDni.setFont(new Font("Tahoma", Font.PLAIN, 11));
		panel.add(txtBuscarPorDni);
		txtBuscarPorDni.setHorizontalAlignment(SwingConstants.RIGHT);
		txtBuscarPorDni.setText("DNI");
		txtBuscarPorDni.setColumns(10);

		txtBuscarPorNombre = new JTextField();
		txtBuscarPorNombre.addMouseListener(new TxtBuscarPorNombreMouseListener());
		txtBuscarPorNombre.setPreferredSize(new Dimension(7, 30));
		txtBuscarPorNombre.setFont(new Font("Tahoma", Font.PLAIN, 11));
		txtBuscarPorNombre.setHorizontalAlignment(SwingConstants.RIGHT);
		txtBuscarPorNombre.setText("Nombre");
		panel.add(txtBuscarPorNombre);
		txtBuscarPorNombre.setColumns(10);

		txtBuscarPorApellido1 = new JTextField();
		txtBuscarPorApellido1.addMouseListener(new TxtBuscarPorApellido1MouseListener());
		txtBuscarPorApellido1.setPreferredSize(new Dimension(7, 30));
		txtBuscarPorApellido1.setFont(new Font("Tahoma", Font.PLAIN, 11));
		txtBuscarPorApellido1.setText("Primer Apellido");
		txtBuscarPorApellido1.setHorizontalAlignment(SwingConstants.RIGHT);
		panel.add(txtBuscarPorApellido1);
		txtBuscarPorApellido1.setColumns(10);

		txtBuscarPorApellido2 = new JTextField();
		txtBuscarPorApellido2.addMouseListener(new TxtBuscarPorApellido2MouseListener());
		txtBuscarPorApellido2.setPreferredSize(new Dimension(33, 30));
		txtBuscarPorApellido2.setFont(new Font("Tahoma", Font.PLAIN, 11));
		txtBuscarPorApellido2.setText("Segundo Apellido");
		txtBuscarPorApellido2.setHorizontalAlignment(SwingConstants.RIGHT);
		panel.add(txtBuscarPorApellido2);
		txtBuscarPorApellido2.setColumns(10);

		JButton btnRestaurarCamposBusqueda = new JButton("");
		btnRestaurarCamposBusqueda.setPreferredSize(new Dimension(35, 30));
		btnRestaurarCamposBusqueda.setIcon(
				new ImageIcon(RegistroAuxiliares.class.getResource("/lugudu/gestorHorarios/recursos/borrar16.png")));
		btnRestaurarCamposBusqueda.addActionListener(new BtnRestaurarCamposBusquedaActionListener());
		btnRestaurarCamposBusqueda.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		panel.add(btnRestaurarCamposBusqueda);

		JButton btnBuscar = new JButton("");
		btnBuscar.setPreferredSize(new Dimension(35, 30));
		btnBuscar.setIcon(
				new ImageIcon(RegistroAuxiliares.class.getResource("/lugudu/gestorHorarios/recursos/buscar.png")));
		btnBuscar.addActionListener(new BtnBuscarActionListener());
		panel.add(btnBuscar);
		btnBuscar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

		JButton btnMostrarTodosLosVecinos = new JButton("Actualizar Tabla");
		btnMostrarTodosLosVecinos.addActionListener(new BtnMostrarTodosLosVecinosActionListener());
		btnMostrarTodosLosVecinos.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

		GridBagConstraints gbcbtnMostrarTodosLosVecinos = new GridBagConstraints();
		gbcbtnMostrarTodosLosVecinos.anchor = GridBagConstraints.EAST;
		gbcbtnMostrarTodosLosVecinos.fill = GridBagConstraints.VERTICAL;
		gbcbtnMostrarTodosLosVecinos.insets = new Insets(0, 0, 5, 5);
		gbcbtnMostrarTodosLosVecinos.gridx = 2;
		gbcbtnMostrarTodosLosVecinos.gridy = 3;
		add(btnMostrarTodosLosVecinos, gbcbtnMostrarTodosLosVecinos);
	}

	public static RegistroAuxiliares getInstance() {
		if (mInstancia == null) {
			mInstancia = new RegistroAuxiliares();
		}
		return mInstancia;
	}

	private void crearModelTabla() {
		model = new DefaultTableModel(new Object[][] {},
				new String[] { "DNI", "Nombre", "Primer apellido", "Segundo apellido", "id" }) {

			private static final long serialVersionUID = 6078883996706723703L;

			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
	}

	private void cargarTabla() throws SQLException {

		TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(model);
		tablaAuxiliares.setRowSorter(sorter);

		List<RowSorter.SortKey> sortKeys = new ArrayList<>(25);
		sortKeys.add(new RowSorter.SortKey(1, SortOrder.ASCENDING));
		sortKeys.add(new RowSorter.SortKey(2, SortOrder.ASCENDING));
		sortKeys.add(new RowSorter.SortKey(3, SortOrder.ASCENDING));

		sorter.setSortKeys(sortKeys);

		resetModelTable();

		ResultSet rs = auxiliarDao.selectAll();
		rellenarTabla(rs);

		lblContador.setText(tablaAuxiliares.getRowCount() + " elementos encontrados");
	}

	private void resetModelTable() {
		model.setRowCount(0);
		tablaAuxiliares.setModel(model);
	}

	private void rellenarTabla(ResultSet rs) throws SQLException {
		while (rs.next()) {
			String dni = rs.getString("dni");
			String nombre = rs.getString("nombre");
			String apellido1 = rs.getString("apellido1");
			String apellido2 = rs.getString("apellido2");
			String id = rs.getString("id");
			model.addRow(new Object[] { dni, nombre, apellido1, apellido2, id });
		}
		tablaAuxiliares.setModel(model);
	}

	private void restaurarCamposBusqueda() {
		txtBuscarPorDni.setText("DNI");
		txtBuscarPorNombre.setText("Nombre");
		txtBuscarPorApellido1.setText("Primer Apellido");
		txtBuscarPorApellido2.setText("Segundo Apellido");
	}

	private static class BtnAgregarActionListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			FormularioAuxiliares fa = FormularioAuxiliares.getInstance();
			fa.setModo("agregar");

			VentanaPrincipal vp = VentanaPrincipal.getInterfaz();
			vp.cambiarPanel("FA");
		}
	}

	private class BtnModificarActionListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			if (!tablaAuxiliares.getSelectionModel().isSelectionEmpty()) {
				int row = tablaAuxiliares.convertRowIndexToModel(tablaAuxiliares.getSelectedRow());

				FormularioAuxiliares fa = FormularioAuxiliares.getInstance();
				fa.setModo("modificar");
				fa.setIdModificar(tablaAuxiliares.getModel().getValueAt(row, 4).toString());

				VentanaPrincipal vp = VentanaPrincipal.getInterfaz();
				vp.cambiarPanel("FA");
			} else
				JOptionPane.showMessageDialog(null, "No ha seleccionado un auxiliar", "Error", 0);
		}
	}

	private class BtnBorrarActionListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			if (!tablaAuxiliares.getSelectionModel().isSelectionEmpty()) {
				int row = tablaAuxiliares.convertRowIndexToModel(tablaAuxiliares.getSelectedRow());
				String id = tablaAuxiliares.getModel().getValueAt(row, 4).toString();

				int confirmado = JOptionPane.showOptionDialog(null,
						"¿Quieres borrar al auxiliar? \nSe borrarán todas sus incidencias", "Ventana de confirmacion",
						JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, new String[] { "Si", "No" },
						"default");

				if (JOptionPane.OK_OPTION == confirmado) {
					try {
						auxiliarDao.deleteById(id);
						cargarTabla();
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
				}
			} else
				JOptionPane.showMessageDialog(null, "No ha seleccionado un auxiliar", "Error", 0);
		}
	}

	private class ThisComponentListener extends ComponentAdapter {
		@Override
		public void componentShown(ComponentEvent e) {
			try {
				cargarTabla();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}
	}

	private class BtnRestaurarCamposBusquedaActionListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			restaurarCamposBusqueda();
		}
	}

	private class BtnBuscarActionListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {

			TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(model);
			tablaAuxiliares.setRowSorter(sorter);

			boolean busquedaDniValida = comprobarBusquedaDni();
			boolean busquedaNombreValida = comprobarBusquedaNombre();
			boolean busquedaApellido1Valida = comprobarBusquedaApellido1();
			boolean busquedaApellido2Valida = comprobarBusquedaApellido2();

			agregarFiltros(sorter, busquedaDniValida, busquedaNombreValida, busquedaApellido1Valida,
					busquedaApellido2Valida);

			lblContador.setText(tablaAuxiliares.getRowCount() + " elementos encontrados");
		}

		private void agregarFiltros(TableRowSorter<DefaultTableModel> sorter, boolean busquedaDniValida,
				boolean busquedaNombreValida, boolean busquedaApellido1Valida, boolean busquedaApellido2Valida) {
			List<RowFilter<Object, Object>> filters = new ArrayList<>(3);

			if (busquedaDniValida) {
				filters.add(RowFilter.regexFilter("^" + txtBuscarPorDni.getText(), 0));
			}

			if (busquedaNombreValida) {
				filters.add(RowFilter.regexFilter("^" + txtBuscarPorNombre.getText(), 1));
			}

			if (busquedaApellido1Valida) {
				filters.add(RowFilter.regexFilter("^" + txtBuscarPorApellido1.getText(), 2));
			}

			if (busquedaApellido2Valida) {
				filters.add(RowFilter.regexFilter("^" + txtBuscarPorApellido2.getText(), 3));
			}

			RowFilter<Object, Object> serviceFilter = RowFilter.andFilter(filters);
			sorter.setRowFilter(serviceFilter);
		}

		private boolean comprobarBusquedaApellido2() {
			boolean valido = true;
			if ("Segundo Apellido".equals(txtBuscarPorApellido2.getText()))
				valido = false;
			return valido;
		}

		private boolean comprobarBusquedaApellido1() {
			boolean valido = true;
			if ("Primer Apellido".equals(txtBuscarPorApellido1.getText()))
				valido = false;
			return valido;
		}

		private boolean comprobarBusquedaNombre() {
			boolean valido = true;
			if ("Nombre".equals(txtBuscarPorNombre.getText()))
				valido = false;
			return valido;
		}

		private boolean comprobarBusquedaDni() {
			boolean valido = true;
			if ("DNI".equals(txtBuscarPorDni.getText()))
				valido = false;
			return valido;
		}
	}

	private class BtnMostrarTodosLosVecinosActionListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			tablaAuxiliares.setRowSorter(null);
			try {
				cargarTabla();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			restaurarCamposBusqueda();
		}
	}

	private class TxtBuscarPorDniMouseListener extends MouseAdapter {
		@Override
		public void mouseClicked(MouseEvent e) {
			txtBuscarPorDni.setText("");
		}
	}

	private class TxtBuscarPorNombreMouseListener extends MouseAdapter {
		@Override
		public void mouseClicked(MouseEvent e) {
			txtBuscarPorNombre.setText("");
		}
	}

	private class TxtBuscarPorApellido1MouseListener extends MouseAdapter {
		@Override
		public void mouseClicked(MouseEvent e) {
			txtBuscarPorApellido1.setText("");
		}
	}

	private class TxtBuscarPorApellido2MouseListener extends MouseAdapter {
		@Override
		public void mouseClicked(MouseEvent e) {
			txtBuscarPorApellido2.setText("");
		}
	}

	private class BtnInspeccionarActionListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			if (!tablaAuxiliares.getSelectionModel().isSelectionEmpty()) {
				int row = tablaAuxiliares.convertRowIndexToModel(tablaAuxiliares.getSelectedRow());

				InspeccionarAuxiliar ia = InspeccionarAuxiliar.getInstance();
				ia.setIdModificar(tablaAuxiliares.getModel().getValueAt(row, 4).toString());

				VentanaPrincipal vp = VentanaPrincipal.getInterfaz();
				vp.cambiarPanel("IA");
			} else
				JOptionPane.showMessageDialog(null, "No ha seleccionado un auxiliar", "Error", 0);
		}
	}

	private static class BtnDescargarTotalActionListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			SeleccionarFecha sf = SeleccionarFecha.getInstance();
			sf.setVisible(true);
		}
	}
}

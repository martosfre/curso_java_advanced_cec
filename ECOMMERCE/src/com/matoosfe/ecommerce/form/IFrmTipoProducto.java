package com.matoosfe.ecommerce.form;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.HeadlessException;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import com.matoosfe.ecommerce.form.util.TableModelTipoProducto;
import com.matoosfe.ecommerce.modelo.TipoProducto;
import com.matoosfe.ecommerce.negocio.TipoProductoTrs;
import javax.swing.ListSelectionModel;

public class IFrmTipoProducto extends JInternalFrame {
	private JTextField txtNomTipPro;
	private JTextArea txaDesTipPro;
	private JTable tabTipPro;
	private JTextField txtValBus;
	private TableModelTipoProducto myModeloTipPro;
	private TipoProducto tipProSel;
	private JTabbedPane tabbedPane;
	private JButton btnEdiTipPro;
	private JButton btnEliTipPro;

	/**
	 * Create the frame.
	 */
	public IFrmTipoProducto() {
		inicializar();
		setClosable(true);
		setMaximizable(true);
		setIconifiable(true);
		setTitle("::.Administraci\u00F3n Tipo Producto.::");
		setBounds(100, 100, 450, 300);

		JToolBar toolBar = new JToolBar();
		toolBar.setMargin(new Insets(10, 0, 10, 0));
		getContentPane().add(toolBar, BorderLayout.NORTH);

		JButton btnNueTipPro = new JButton("Nuevo");
		btnNueTipPro.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				txtNomTipPro.setText("");
				txaDesTipPro.setText("");
			}
		});
		btnNueTipPro.setIcon(new ImageIcon(
				IFrmTipoProducto.class.getResource("/com/matoosfe/ecommerce/resources/iconoNuevo32x32.png")));
		toolBar.add(btnNueTipPro);

		JButton btnGuaTipPro = new JButton("Guardar");
		btnGuaTipPro.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					// 1.Verificar que no tenga campos nulos en base a la definición de la bdd
					if (txtNomTipPro.getText().equals("")) {
						JOptionPane.showMessageDialog(null, "Nombre requerido", "Errores", JOptionPane.ERROR_MESSAGE);
					} else {
						// 2. Recuperar los valores y armar el objeto
						TipoProducto tipProducto = new TipoProducto();
						tipProducto.setNombreTipPro(txtNomTipPro.getText());
						tipProducto.setDescripcionTipPro(txaDesTipPro.getText());
						TipoProductoTrs admTipPro = new TipoProductoTrs();
						String mensaje = null;
						if (tipProSel == null) {// Guardar
							// 3. Llamar al controlador
							mensaje = admTipPro.guardar(tipProducto);
						}else {
							// 2.1 Setear el id para actualizar
							tipProducto.setIdTipPro(tipProSel.getIdTipPro());
							mensaje = admTipPro.actualizar(tipProducto);
						}
						JOptionPane.showMessageDialog(null, mensaje, "Información", JOptionPane.INFORMATION_MESSAGE);
						// 4. Limpiar el formulario
						txtNomTipPro.setText("");
						txaDesTipPro.setText("");
						//5. Encerar la selección y actualizar la tabla
						tipProSel = null; // Encero la selección
						inicializar(); // Actualizan el modelo
						tabTipPro.setModel(myModeloTipPro);// Actualizan el componente gráfico
						btnEdiTipPro.setEnabled(false);
						btnEliTipPro.setEnabled(false);
					}
				} catch (Exception e1) {
					JOptionPane.showMessageDialog(null, "Error al guardar", "Errores", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		btnGuaTipPro.setIcon(new ImageIcon(
				IFrmTipoProducto.class.getResource("/com/matoosfe/ecommerce/resources/iconoGuardar32x32.png")));
		toolBar.add(btnGuaTipPro);

		btnEliTipPro = new JButton("Eliminar");
		btnEliTipPro.setEnabled(false);
		btnEliTipPro.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					if (tipProSel != null) { // Esta seleccionado el registro
						int valCon = JOptionPane.showConfirmDialog(null, "¿Está seguro de eliminar el registro?",
								"Confirmación", JOptionPane.YES_NO_OPTION);
						if (valCon == 0) {
							TipoProductoTrs admTipPro = new TipoProductoTrs();
							String mensaje = admTipPro.eliminar(tipProSel);
							JOptionPane.showMessageDialog(null, mensaje, "Información",
									JOptionPane.INFORMATION_MESSAGE);
							tipProSel = null; // Encero la selección
							inicializar(); // Actualizan el modelo
							tabTipPro.setModel(myModeloTipPro);// Actualizan el componente gráfico
							btnEdiTipPro.setEnabled(false);
							btnEliTipPro.setEnabled(false);
						}

					} else {
						JOptionPane.showMessageDialog(null, "No se ha seleccionado ningún registro", "Errores",
								JOptionPane.ERROR_MESSAGE);
					}
				} catch (Exception e1) {
					JOptionPane.showMessageDialog(null, "Error al eliminar", "Errores", JOptionPane.ERROR_MESSAGE);
				}
			}
		});

		btnEdiTipPro = new JButton("Editar");
		btnEdiTipPro.setEnabled(false);
		btnEdiTipPro.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (tipProSel != null) {// Verificar que este seleccionado
					tabbedPane.setSelectedIndex(0);// Cambiar de tab
					txtNomTipPro.setText(tipProSel.getNombreTipPro());
					txaDesTipPro.setText(tipProSel.getDescripcionTipPro());
				}else {
					JOptionPane.showMessageDialog(null, "No se ha seleccionado ningún registro", "Errores",
							JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		btnEdiTipPro.setIcon(new ImageIcon(
				IFrmTipoProducto.class.getResource("/com/matoosfe/ecommerce/resources/iconoEditar32x32.png")));
		toolBar.add(btnEdiTipPro);
		btnEliTipPro.setIcon(new ImageIcon(
				IFrmTipoProducto.class.getResource("/com/matoosfe/ecommerce/resources/iconoBorrar32x32.png")));
		toolBar.add(btnEliTipPro);

		tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		getContentPane().add(tabbedPane, BorderLayout.CENTER);

		JPanel tabIngTipPro = new JPanel();
		tabbedPane.addTab("Registrar", null, tabIngTipPro, null);
		GridBagLayout gbl_tabIngTipPro = new GridBagLayout();
		gbl_tabIngTipPro.columnWidths = new int[] { 0, 0, 0, 0 };
		gbl_tabIngTipPro.rowHeights = new int[] { 0, 0, 0, 0 };
		gbl_tabIngTipPro.columnWeights = new double[] { 0.0, 0.0, 1.0, Double.MIN_VALUE };
		gbl_tabIngTipPro.rowWeights = new double[] { 0.0, 0.0, 1.0, Double.MIN_VALUE };
		tabIngTipPro.setLayout(gbl_tabIngTipPro);

		JLabel lblNomTipPro = new JLabel("Nombre:");
		GridBagConstraints gbc_lblNomTipPro = new GridBagConstraints();
		gbc_lblNomTipPro.anchor = GridBagConstraints.EAST;
		gbc_lblNomTipPro.insets = new Insets(0, 0, 5, 5);
		gbc_lblNomTipPro.gridx = 1;
		gbc_lblNomTipPro.gridy = 1;
		tabIngTipPro.add(lblNomTipPro, gbc_lblNomTipPro);

		txtNomTipPro = new JTextField();
		GridBagConstraints gbc_txtNomTipPro = new GridBagConstraints();
		gbc_txtNomTipPro.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtNomTipPro.insets = new Insets(0, 0, 5, 0);
		gbc_txtNomTipPro.gridx = 2;
		gbc_txtNomTipPro.gridy = 1;
		tabIngTipPro.add(txtNomTipPro, gbc_txtNomTipPro);
		txtNomTipPro.setColumns(10);

		JLabel lblDesTipPro = new JLabel("Descripci\u00F3n:");
		GridBagConstraints gbc_lblDesTipPro = new GridBagConstraints();
		gbc_lblDesTipPro.insets = new Insets(0, 0, 0, 5);
		gbc_lblDesTipPro.gridx = 1;
		gbc_lblDesTipPro.gridy = 2;
		tabIngTipPro.add(lblDesTipPro, gbc_lblDesTipPro);

		txaDesTipPro = new JTextArea();
		GridBagConstraints gbc_txaDesTipPro = new GridBagConstraints();
		gbc_txaDesTipPro.fill = GridBagConstraints.BOTH;
		gbc_txaDesTipPro.gridx = 2;
		gbc_txaDesTipPro.gridy = 2;
		tabIngTipPro.add(txaDesTipPro, gbc_txaDesTipPro);

		JPanel tabLisTipPro = new JPanel();
		tabbedPane.addTab("Listar", null, tabLisTipPro, null);
		tabLisTipPro.setLayout(new BorderLayout(0, 0));

		JPanel pnlBusTipPro = new JPanel();
		tabLisTipPro.add(pnlBusTipPro, BorderLayout.NORTH);

		JLabel lblBusPorTipPro = new JLabel("Nombre/Descripci\u00F3n:");
		pnlBusTipPro.add(lblBusPorTipPro);

		txtValBus = new JTextField();
		pnlBusTipPro.add(txtValBus);
		txtValBus.setColumns(10);

		JButton btnConTipPro = new JButton("Buscar");
		btnConTipPro.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					TipoProductoTrs admTipPro = new TipoProductoTrs();
					
					List<String> columnas = new ArrayList<>();
					columnas.add("Id");
					columnas.add("Nombre");
					columnas.add("Descripción");
					List<TipoProducto> filas = admTipPro.consultarPorNombreDescripcion(txtValBus.getText());

					myModeloTipPro = new TableModelTipoProducto(columnas, filas);
					tabTipPro.setModel(myModeloTipPro);
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		});
		btnConTipPro.setIcon(new ImageIcon(
				IFrmTipoProducto.class.getResource("/com/matoosfe/ecommerce/resources/iconoBuscar32x32.png")));
		pnlBusTipPro.add(btnConTipPro);

		tabTipPro = new JTable();
		tabTipPro.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tabTipPro.setModel(myModeloTipPro);
		JScrollPane spTipPro = new JScrollPane(tabTipPro);
		tabLisTipPro.add(spTipPro, BorderLayout.CENTER);

		//Implementar la selección de la tabla
		tabTipPro.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent e) {
				// Recuperar el modelo de Seleccion
				ListSelectionModel lsm = (ListSelectionModel) e.getSource();
				// Recuperar el indice del registro seleccionado
				int indSel = lsm.getMaxSelectionIndex();
				// Verificar que se haya seleccionado un valor
				if (indSel >= 0) {
					TableModelTipoProducto modelo = (TableModelTipoProducto) tabTipPro.getModel();
					tipProSel = modelo.obtenerFilaSeleccionada(indSel);
					btnEdiTipPro.setEnabled(true);
					btnEliTipPro.setEnabled(true);
				}

			}
		});

	}

	private void inicializar() {
		try {
			List<String> columnas = new ArrayList<>();
			columnas.add("Id");
			columnas.add("Nombre");
			columnas.add("Descripción");

			List<TipoProducto> filas = new ArrayList<TipoProducto>();
			TipoProductoTrs admTipPro = new TipoProductoTrs();
			filas = admTipPro.consultarTodos();

			myModeloTipPro = new TableModelTipoProducto(columnas, filas);
		} catch (Exception e) {

		}

	}

}

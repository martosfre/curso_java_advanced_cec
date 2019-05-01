package com.matoosfe.ecommerce.form;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import com.matoosfe.ecommerce.form.util.ComboBoxModelTipoProducto;
import com.matoosfe.ecommerce.form.util.TableModelProducto;
import com.matoosfe.ecommerce.modelo.Producto;
import com.matoosfe.ecommerce.modelo.TipoProducto;
import com.matoosfe.ecommerce.negocio.ProductoTrs;
import com.matoosfe.ecommerce.negocio.TipoProductoTrs;
import com.toedter.calendar.JDateChooser;

public class IFrmProducto extends JInternalFrame {
	private JTextField txtNomPro;
	private ComboBoxModelTipoProducto modeloComboTipPro;
	private JTextArea txaDesPro;
	private JFormattedTextField fxtPrePro;
	private JDateChooser datChoFecCadPro;
	private TipoProducto tipProSel; // Selección Combo
	private Producto proSel; // Selección Tabla
	private TableModelProducto myModeloPro;
	private JTable tabPro;
	private JTextField txtBusPorPro;
	private JTabbedPane tabbedPane;
	private JComboBox<TipoProducto> cmbTipPro;
	private JButton btnEdiPro;
	private JButton btnEliPro;

	/**
	 * Create the frame.
	 */
	public IFrmProducto() {
		setMaximizable(true);
		setIconifiable(true);
		setClosable(true);
		inicializar();
		setTitle("::.Administraci\u00F3n Producto.::");
		setBounds(100, 100, 450, 300);

		JToolBar toolBar = new JToolBar();
		getContentPane().add(toolBar, BorderLayout.NORTH);

		JButton btnNuePro = new JButton("Nuevo");
		btnNuePro.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

			}
		});
		btnNuePro.setIcon(
				new ImageIcon(IFrmProducto.class.getResource("/com/matoosfe/ecommerce/resources/iconoNuevo32x32.png")));
		toolBar.add(btnNuePro);

		JButton btnGuaPro = new JButton("Guardar");
		btnGuaPro.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					Producto producto = new Producto();
					producto.setNombrePro(txtNomPro.getText());
					producto.setDescripcionPro(txaDesPro.getText());
					producto.setPrecioPro(new BigDecimal(fxtPrePro.getText()));
					producto.setFechaCadPro(datChoFecCadPro.getDate());
					/******************************************************
					 * Bloque para relacionar el objeto
					 ********************************************************/
					producto.setTipoProducto(tipProSel);
					/********************************************************/

					ProductoTrs admProTrs = new ProductoTrs();
					String mensaje = null;
					if (proSel == null) {// Guardar
						// 3. Llamar al controlador
						mensaje = admProTrs.guardar(producto);
					} else {
						// 2.1 Setear el id para actualizar
						producto.setIdPro(proSel.getIdPro());
						mensaje = admProTrs.actualizar(producto);
					}

					JOptionPane.showMessageDialog(null, mensaje, "Información", JOptionPane.INFORMATION_MESSAGE);
					// 4. Limpiar el formulario
					txtNomPro.setText("");
					txaDesPro.setText("");
					fxtPrePro.setText("");
					datChoFecCadPro.setDate(null);
					// 5. Encerar la selección y actualizar la tabla
					proSel = null; // Encero la selección
					tipProSel = null;
					inicializar(); // Actualizan el modelo
					tabPro.setModel(myModeloPro);// Actualizan el componente gráfico
					btnEdiPro.setEnabled(false);
					btnEliPro.setEnabled(false);
				} catch (Exception e1) {
					JOptionPane.showMessageDialog(null, "Error al guardar:" + e1.getMessage(), "Errores",
							JOptionPane.ERROR_MESSAGE);
				}

			}
		});
		btnGuaPro.setIcon(new ImageIcon(
				IFrmProducto.class.getResource("/com/matoosfe/ecommerce/resources/iconoGuardar32x32.png")));
		toolBar.add(btnGuaPro);

		btnEdiPro = new JButton("Editar");
		btnEdiPro.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (proSel != null) {// Verificar que este seleccionado
					tabbedPane.setSelectedIndex(0);// Cambiar de tab
					txtNomPro.setText(proSel.getNombrePro());
					txaDesPro.setText(proSel.getDescripcionPro());
					fxtPrePro.setText(proSel.getPrecioPro().toString());
					datChoFecCadPro.setDate(proSel.getFechaCadPro());
					//Selección del Combo
					modeloComboTipPro.setSelectedItem(proSel.getTipoProducto());
					cmbTipPro.setSelectedItem(modeloComboTipPro.getSelectedItem());
					
				} else {
					JOptionPane.showMessageDialog(null, "No se ha seleccionado ningún registro", "Errores",
							JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		btnEdiPro.setIcon(new ImageIcon(
				IFrmProducto.class.getResource("/com/matoosfe/ecommerce/resources/iconoEditar32x32.png")));
		btnEdiPro.setEnabled(false);
		toolBar.add(btnEdiPro);

		btnEliPro = new JButton("Eliminar");
		btnEliPro.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					if (proSel != null) { // Esta seleccionado el registro
						int valCon = JOptionPane.showConfirmDialog(null, "¿Está seguro de eliminar el registro?",
								"Confirmación", JOptionPane.YES_NO_OPTION);
						if (valCon == 0) {
							ProductoTrs admPro = new ProductoTrs();
							String mensaje = admPro.eliminar(proSel);
							JOptionPane.showMessageDialog(null, mensaje, "Información",
									JOptionPane.INFORMATION_MESSAGE);
							proSel = null; // Encero la selección
							tipProSel = null;
							inicializar(); // Actualizan el modelo
							tabPro.setModel(myModeloPro);// Actualizan el componente gráfico
							btnEdiPro.setEnabled(false);
							btnEliPro.setEnabled(false);
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
		btnEliPro.setIcon(new ImageIcon(
				IFrmProducto.class.getResource("/com/matoosfe/ecommerce/resources/iconoBorrar32x32.png")));
		btnEliPro.setEnabled(false);
		toolBar.add(btnEliPro);

		tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		getContentPane().add(tabbedPane, BorderLayout.CENTER);

		JPanel pnlIngPro = new JPanel();
		tabbedPane.addTab("Ingresar", null, pnlIngPro, null);
		GridBagLayout gbl_pnlIngPro = new GridBagLayout();
		gbl_pnlIngPro.columnWidths = new int[] { 0, 0, 0, 0, 0, 0 };
		gbl_pnlIngPro.rowHeights = new int[] { 0, 0, 0, 0, 0, 0, 0 };
		gbl_pnlIngPro.columnWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 1.0, Double.MIN_VALUE };
		gbl_pnlIngPro.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE };
		pnlIngPro.setLayout(gbl_pnlIngPro);

		JLabel lblTipPro = new JLabel("Tipo Producto:");
		GridBagConstraints gbc_lblTipPro = new GridBagConstraints();
		gbc_lblTipPro.insets = new Insets(0, 0, 5, 5);
		gbc_lblTipPro.gridx = 2;
		gbc_lblTipPro.gridy = 1;
		pnlIngPro.add(lblTipPro, gbc_lblTipPro);

		cmbTipPro = new JComboBox<>();
		cmbTipPro.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				tipProSel = (TipoProducto) modeloComboTipPro.getSelectedItem();
				System.out.println(tipProSel);
			}
		});

		cmbTipPro.setModel(modeloComboTipPro);
		GridBagConstraints gbc_cmbTipPro = new GridBagConstraints();
		gbc_cmbTipPro.insets = new Insets(0, 0, 5, 0);
		gbc_cmbTipPro.fill = GridBagConstraints.HORIZONTAL;
		gbc_cmbTipPro.gridx = 4;
		gbc_cmbTipPro.gridy = 1;
		pnlIngPro.add(cmbTipPro, gbc_cmbTipPro);

		JLabel lblNomPro = new JLabel("Nombre:");
		GridBagConstraints gbc_lblNomPro = new GridBagConstraints();
		gbc_lblNomPro.insets = new Insets(0, 0, 5, 5);
		gbc_lblNomPro.gridx = 2;
		gbc_lblNomPro.gridy = 2;
		pnlIngPro.add(lblNomPro, gbc_lblNomPro);

		txtNomPro = new JTextField();
		GridBagConstraints gbc_txtNomPro = new GridBagConstraints();
		gbc_txtNomPro.insets = new Insets(0, 0, 5, 0);
		gbc_txtNomPro.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtNomPro.gridx = 4;
		gbc_txtNomPro.gridy = 2;
		pnlIngPro.add(txtNomPro, gbc_txtNomPro);
		txtNomPro.setColumns(10);

		JLabel lblDesPro = new JLabel("Descripci\u00F3n:");
		GridBagConstraints gbc_lblDesPro = new GridBagConstraints();
		gbc_lblDesPro.insets = new Insets(0, 0, 5, 5);
		gbc_lblDesPro.gridx = 2;
		gbc_lblDesPro.gridy = 3;
		pnlIngPro.add(lblDesPro, gbc_lblDesPro);

		txaDesPro = new JTextArea();
		GridBagConstraints gbc_txaDesPro = new GridBagConstraints();
		gbc_txaDesPro.insets = new Insets(0, 0, 5, 0);
		gbc_txaDesPro.fill = GridBagConstraints.BOTH;
		gbc_txaDesPro.gridx = 4;
		gbc_txaDesPro.gridy = 3;
		pnlIngPro.add(txaDesPro, gbc_txaDesPro);

		JLabel lblPrePro = new JLabel("Precio:");
		GridBagConstraints gbc_lblPrePro = new GridBagConstraints();
		gbc_lblPrePro.insets = new Insets(0, 0, 5, 5);
		gbc_lblPrePro.gridx = 2;
		gbc_lblPrePro.gridy = 4;
		pnlIngPro.add(lblPrePro, gbc_lblPrePro);

		fxtPrePro = new JFormattedTextField();
		GridBagConstraints gbc_fxtPrePro = new GridBagConstraints();
		gbc_fxtPrePro.insets = new Insets(0, 0, 5, 0);
		gbc_fxtPrePro.fill = GridBagConstraints.HORIZONTAL;
		gbc_fxtPrePro.gridx = 4;
		gbc_fxtPrePro.gridy = 4;
		pnlIngPro.add(fxtPrePro, gbc_fxtPrePro);

		JLabel lblFecCadPro = new JLabel("Fecha Caducidad:");
		GridBagConstraints gbc_lblFecCadPro = new GridBagConstraints();
		gbc_lblFecCadPro.insets = new Insets(0, 0, 0, 5);
		gbc_lblFecCadPro.gridx = 2;
		gbc_lblFecCadPro.gridy = 5;
		pnlIngPro.add(lblFecCadPro, gbc_lblFecCadPro);

		datChoFecCadPro = new JDateChooser();
		GridBagConstraints gbc_datChoFecCadPro = new GridBagConstraints();
		gbc_datChoFecCadPro.fill = GridBagConstraints.BOTH;
		gbc_datChoFecCadPro.gridx = 4;
		gbc_datChoFecCadPro.gridy = 5;
		pnlIngPro.add(datChoFecCadPro, gbc_datChoFecCadPro);

		JPanel pnlLisPro = new JPanel();
		tabbedPane.addTab("Listar", null, pnlLisPro, null);
		pnlLisPro.setLayout(new BorderLayout(0, 0));
		
		JPanel pnlBusPro = new JPanel();
		pnlLisPro.add(pnlBusPro, BorderLayout.NORTH);
		
		JLabel lblBusPorPro = new JLabel("Nombre/Descripci\u00F3n:");
		pnlBusPro.add(lblBusPorPro);
		
		txtBusPorPro = new JTextField();
		pnlBusPro.add(txtBusPorPro);
		txtBusPorPro.setColumns(10);
		
		JButton btnBusPorPro = new JButton("Buscar");
		btnBusPorPro.setIcon(new ImageIcon(IFrmProducto.class.getResource("/com/matoosfe/ecommerce/resources/iconoBuscar32x32.png")));
		pnlBusPro.add(btnBusPorPro);
		
		tabPro = new JTable();
		tabPro.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tabPro.setModel(myModeloPro);
		JScrollPane scrPanTabPro = new JScrollPane(tabPro);
		pnlLisPro.add(scrPanTabPro, BorderLayout.CENTER);

		//Implementar la selección de la tabla
		tabPro.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent e) {
				// Recuperar el modelo de Seleccion
				ListSelectionModel lsm = (ListSelectionModel) e.getSource();
				// Recuperar el indice del registro seleccionado
				int indSel = lsm.getMaxSelectionIndex();
				// Verificar que se haya seleccionado un valor
				if (indSel >= 0) {
					TableModelProducto modelo = (TableModelProducto) tabPro.getModel();
					proSel = modelo.obtenerFilaSeleccionada(indSel);
					btnEdiPro.setEnabled(true);
					btnEliPro.setEnabled(true);
				}

			}
		});

	}

	private void inicializar() {
		try {
			List<TipoProducto> filas = new ArrayList<TipoProducto>();
			TipoProductoTrs admTipPro = new TipoProductoTrs();
			filas = admTipPro.consultarTodos();
			modeloComboTipPro = new ComboBoxModelTipoProducto(filas);
			
			//Inicializar la tabla
			List<String> columnas = new ArrayList<>();
			columnas.add("Id");
			columnas.add("Nombre");
			columnas.add("Descripción");
			columnas.add("Precio");
			columnas.add("Fecha Caducidad");
			columnas.add("Tipo Producto");

			List<Producto> filasTabla = new ArrayList<Producto>();
			ProductoTrs admPro = new ProductoTrs();
			filasTabla = admPro.consultarTodos();

			myModeloPro = new TableModelProducto(columnas, filasTabla);
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "No se pudo inicializar las estructuras de datos", "Errores",
					JOptionPane.ERROR_MESSAGE);
		}

	}

}

package com.matoosfe.ecommerce.form;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.HeadlessException;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JToolBar;

import com.matoosfe.ecommerce.form.util.ComboBoxModelTipoProducto;
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
	private TipoProducto tipProSel; //Selección Combo
	private Producto proSel; //Selección Tabla

	/**
	 * Create the frame.
	 */
	public IFrmProducto() {
		inicializar();
		setTitle("::.Administraci\u00F3n Producto.::");
		setBounds(100, 100, 450, 300);

		JToolBar toolBar = new JToolBar();
		getContentPane().add(toolBar, BorderLayout.NORTH);

		JButton btnNuePro = new JButton("Nuevo");
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
					producto.setTipoProducto(tipProSel);
					
					ProductoTrs admProTrs = new ProductoTrs();
					String mensaje = null;
					if (proSel == null) {// Guardar
						// 3. Llamar al controlador
						mensaje = admProTrs.guardar(producto);
					}else {
						// 2.1 Setear el id para actualizar
						producto.setIdPro(proSel.getIdPro());
						mensaje = admProTrs.actualizar(producto);
					}
					
					JOptionPane.showMessageDialog(null, mensaje, "Información", JOptionPane.INFORMATION_MESSAGE);
				}  catch (Exception e1) {
					JOptionPane.showMessageDialog(null, "Error al guardar", "Errores", JOptionPane.ERROR_MESSAGE);
				}
				
			}
		});
		btnGuaPro.setIcon(new ImageIcon(
				IFrmProducto.class.getResource("/com/matoosfe/ecommerce/resources/iconoGuardar32x32.png")));
		toolBar.add(btnGuaPro);

		JButton btnEdiPro = new JButton("Editar");
		btnEdiPro.setIcon(new ImageIcon(
				IFrmProducto.class.getResource("/com/matoosfe/ecommerce/resources/iconoEditar32x32.png")));
		btnEdiPro.setEnabled(false);
		toolBar.add(btnEdiPro);

		JButton btnEliPro = new JButton("Eliminar");
		btnEliPro.setIcon(new ImageIcon(
				IFrmProducto.class.getResource("/com/matoosfe/ecommerce/resources/iconoBorrar32x32.png")));
		btnEliPro.setEnabled(false);
		toolBar.add(btnEliPro);

		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
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

		JComboBox<TipoProducto> cmbTipPro = new JComboBox<>();
		
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

	}

	private void inicializar() {
		try {
			List<TipoProducto> filas = new ArrayList<TipoProducto>();
			TipoProductoTrs admTipPro = new TipoProductoTrs();
			filas = admTipPro.consultarTodos();
			modeloComboTipPro = new ComboBoxModelTipoProducto(filas);
		} catch (Exception e) {

		}

	}

}

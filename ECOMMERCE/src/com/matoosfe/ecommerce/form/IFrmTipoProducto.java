package com.matoosfe.ecommerce.form;

import javax.swing.JInternalFrame;
import javax.swing.JTabbedPane;
import java.awt.BorderLayout;
import javax.swing.JPanel;
import java.awt.GridBagLayout;
import java.awt.HeadlessException;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.JTextField;
import javax.swing.JTextArea;
import javax.swing.JToolBar;

import com.matoosfe.ecommerce.modelo.TipoProducto;
import com.matoosfe.ecommerce.negocio.TipoProductoTrs;

import javax.swing.JButton;
import javax.swing.ImageIcon;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class IFrmTipoProducto extends JInternalFrame {
	private JTextField txtNomTipPro;
	private JTextArea txaDesTipPro;

	/**
	 * Create the frame.
	 */
	public IFrmTipoProducto() {
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
						JOptionPane.showMessageDialog(null, "Nombre requerido", "Errores", 
								JOptionPane.ERROR_MESSAGE);
					} else {
						//2. Recuperar los valores y armar el objeto
						TipoProducto tipProducto = new TipoProducto();
						tipProducto.setNombreTipPro(txtNomTipPro.getText());
						tipProducto.setDescripcionTipPro(txaDesTipPro.getText());
						
						//3. Llamar al controlador
						TipoProductoTrs admTipPro = new TipoProductoTrs();
						String mensaje = admTipPro.guardar(tipProducto);
						JOptionPane.showMessageDialog(null, mensaje, "Errores", 
								JOptionPane.INFORMATION_MESSAGE);
						//4. Limpiar el formulario
						txtNomTipPro.setText("");
						txaDesTipPro.setText("");
					}
				} catch (Exception e1) {
					JOptionPane.showMessageDialog(null, "Error al guardar", "Errores", 
							JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		btnGuaTipPro.setIcon(new ImageIcon(
				IFrmTipoProducto.class.getResource("/com/matoosfe/ecommerce/resources/iconoGuardar32x32.png")));
		toolBar.add(btnGuaTipPro);

		JButton btnEliTipPro = new JButton("Eliminar");
		btnEliTipPro.setIcon(new ImageIcon(
				IFrmTipoProducto.class.getResource("/com/matoosfe/ecommerce/resources/iconoBorrar32x32.png")));
		toolBar.add(btnEliTipPro);

		JButton btnConTipPro = new JButton("Consultar");
		btnConTipPro.setIcon(new ImageIcon(
				IFrmTipoProducto.class.getResource("/com/matoosfe/ecommerce/resources/iconoBuscar32x32.png")));
		toolBar.add(btnConTipPro);

		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
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

	}

}

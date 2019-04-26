package com.matoosfe.ecommerce.form;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JDesktopPane;
import java.awt.SystemColor;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class FrmPrincipal extends JFrame{

	private JPanel contentPane;
	private JDesktopPane desPanPri;

	/**
	 * Create the frame.
	 */
	public FrmPrincipal() {
		setTitle("::.ECOMMERCE:: Men\u00FA General");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 703, 435);
		
		JMenuBar menBarPri = new JMenuBar();
		setJMenuBar(menBarPri);
		
		JMenu menAdm = new JMenu("Administraci\u00F3n");
		menBarPri.add(menAdm);
		
		JMenuItem menIteSal = new JMenuItem("Salir");
		menAdm.add(menIteSal);
		
		JMenu menSeg = new JMenu("Seguridad");
		menBarPri.add(menSeg);
		
		JMenuItem menIteUsu = new JMenuItem("Usuario");
		menSeg.add(menIteUsu);
		
		JMenu menInv = new JMenu("Inventario");
		menBarPri.add(menInv);
		
		JMenuItem menIteTipPro = new JMenuItem("Tipo Producto");
		menIteTipPro.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//Crear Pantalla 
				IFrmTipoProducto iFrmTipoProducto = new IFrmTipoProducto();
				iFrmTipoProducto.setVisible(true);
				//Añadirle al Contenedor
				desPanPri.add(iFrmTipoProducto);
			}
		});
		menInv.add(menIteTipPro);
		
		JMenuItem menItePro = new JMenuItem("Producto");
		menItePro.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//Crear Pantalla 
				IFrmProducto iFrmProducto = new IFrmProducto();
				iFrmProducto.setVisible(true);
				//Añadirle al Contenedor
				desPanPri.add(iFrmProducto);
			}
		});
		menInv.add(menItePro);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		desPanPri = new JDesktopPane();
		desPanPri.setBackground(SystemColor.activeCaption);
		contentPane.add(desPanPri, BorderLayout.CENTER);
	}

}

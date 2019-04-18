package com.matoosfe.ecommerce.form;

import java.awt.EventQueue;

import javax.swing.JInternalFrame;

public class IFrmProducto extends JInternalFrame {

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					IFrmProducto frame = new IFrmProducto();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public IFrmProducto() {
		setBounds(100, 100, 450, 300);

	}

}

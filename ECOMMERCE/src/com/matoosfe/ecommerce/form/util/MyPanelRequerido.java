package com.matoosfe.ecommerce.form.util;

import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JLabel;

import java.awt.Color;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

public class MyPanelRequerido extends JPanel {
	private JTextField txtCampo;
	private boolean requerido;
	private JLabel lblImgReq;
	private JLabel lblEt;

	public MyPanelRequerido() {

		txtCampo = new JTextField();
		txtCampo.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				if (requerido && txtCampo.getText().equals("")) {
					lblImgReq.setText("REQ");
					lblImgReq.setForeground(Color.RED);
				} else {
					lblImgReq.setText("");
				}
			}
		});

		lblEt = new JLabel("");
		add(lblEt);
		add(txtCampo);
		txtCampo.setColumns(10);

		lblImgReq = new JLabel("");
		add(lblImgReq);
	}

	/**
	 * @return the txtCampo
	 */
	public JTextField getTxtCampo() {
		return txtCampo;
	}

	/**
	 * @param txtCampo the txtCampo to set
	 */
	public void setTxtCampo(JTextField txtCampo) {
		this.txtCampo = txtCampo;
	}

	/**
	 * @return the requerido
	 */
	public boolean isRequerido() {
		return requerido;
	}

	/**
	 * @param requerido the requerido to set
	 */
	public void setRequerido(boolean requerido) {
		this.requerido = requerido;
	}

	/**
	 * @return the lblImgReq
	 */
	public JLabel getLblImgReq() {
		return lblImgReq;
	}

	/**
	 * @param lblImgReq the lblImgReq to set
	 */
	public void setLblImgReq(JLabel lblImgReq) {
		this.lblImgReq = lblImgReq;
	}

	/**
	 * @return the lblEt
	 */
	public JLabel getLblEt() {
		return lblEt;
	}

	/**
	 * @param lblEt the lblEt to set
	 */
	public void setLblEt(JLabel lblEt) {
		this.lblEt = lblEt;
	}

}

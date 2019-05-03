/**
 * 
 */
package com.matoosfe.ecommerce.form.util;

import javax.swing.JTextField;

/**
 * Clase para construir un campo de texto personalizado
 * 
 * @author martosfre
 *
 */
public class MyTextField extends JTextField {
	private boolean requerido;
	private String mensajeRequerido;

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
	 * @return the mensajeRequerido
	 */
	public String getMensajeRequerido() {
		return mensajeRequerido;
	}

	/**
	 * @param mensajeRequerido the mensajeRequerido to set
	 */
	public void setMensajeRequerido(String mensajeRequerido) {
		this.mensajeRequerido = mensajeRequerido;
	}

}
